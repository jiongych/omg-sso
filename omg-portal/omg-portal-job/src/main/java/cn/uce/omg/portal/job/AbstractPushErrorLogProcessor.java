package cn.uce.omg.portal.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.uce.omg.portal.biz.IDataPushBiz;
import cn.uce.omg.portal.biz.IPushErrorLogBiz;
import cn.uce.omg.portal.util.PushErrorLogConstants;
import cn.uce.omg.portal.vo.PushErrorLogVo;
import cn.uce.utils.StringUtil;
/**
 * 异步调用异常处理类
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author uce
 * @date 2018年4月25日 上午9:55:03
 */
@Component("abstractPushErrorLogProcessor")
public class AbstractPushErrorLogProcessor implements InitializingBean{
	private Log LOG = LogFactory.getLog(this.getClass());
	@Value("${pushErrorLog.maxProcessNum}")
	private Integer maxProcessNum = 3;
    @Value("${pushErrorLog.fetchSize}")
	private Integer fetchSize = 1000;
	/** 失败的时候重新消费的间隔基数 */
    @Value("${pushErrorLog.whenFailProcessIntervalBase}")
	private Integer whenFailProcessIntervalBase =  5 * 60;
	@Resource(name="pushErrorLogBiz")
	private IPushErrorLogBiz pushErrorLogBiz;
	@Resource(name="dataPushBiz")
	private IDataPushBiz dataPushBiz;
	@Value("${omgPortalRolePush.threadNums}")
	private String roleThreadNums;
	@Value("${omgPortalPermissionPush.threadNums}")
	private String permissionThreadNums;
	@Value("${omgPortalUserRoleRelPush.threadNums}")
	private String userThreadNums;
	@Value("${portalDictPush.threadNums}")
	private String dictThreadNums;
	@Value("${omgPortalRolePush.queueCapacity}")
	private String roleQueueCapacity;
	@Value("${omgPortalPermissionPush.queueCapacity}")
	private String permissionQueueCapacity;
	@Value("${omgPortalUserRoleRelPush.queueCapacity}")
	private String userQueueCapacity;
	@Value("${portalDictPush.queueCapacity}")
	private String dictQueueCapacity;
	
	private Map<String, ThreadPoolExecutor> taskExecutorMap;
	private int defaultTopicThreadNums = 10;
	private int defaultQueueCapacity = 1000;

	@Override
	public void afterPropertiesSet() throws Exception {
		taskExecutorMap = new HashMap<String, ThreadPoolExecutor>();
		List<String> mqTemplateList = new ArrayList<String>();
		mqTemplateList.add(PushErrorLogConstants.DICT_DATA_MQ_TEMPLATE);
		mqTemplateList.add(PushErrorLogConstants.ROLE_MQ_TEMPLATE);
		mqTemplateList.add(PushErrorLogConstants.USER_MQ_TEMPLATE);
		mqTemplateList.add(PushErrorLogConstants.PERMISSION_MQ_TEMPLATE);
		for (String mqTemplate : mqTemplateList) {
			getTaskExecutor(mqTemplate);
		}
		processDoingLog();
	}
	
	public ThreadPoolExecutor getTaskExecutor(String mqTemplate) {
		if (StringUtil.isBlank(mqTemplate)) {
			return null;
		}
		ThreadPoolExecutor taskExecutor = taskExecutorMap.get(mqTemplate);
		if (taskExecutor == null) {
			synchronized (this) {
				taskExecutor = taskExecutorMap.get(mqTemplate);
				if (taskExecutor == null) {
					int processThreadNums = getThreadNums(mqTemplate);
					int queueCapacity = getQueueCapacity(mqTemplate);
					taskExecutor = new ThreadPoolExecutor(processThreadNums, processThreadNums, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(queueCapacity));
					taskExecutorMap.put(mqTemplate, taskExecutor);
				}
			}
		}
		
		return taskExecutor;
	}
	
	public int getThreadNums(String mqTemplate) {
		if (StringUtil.equals(mqTemplate, PushErrorLogConstants.DICT_DATA_MQ_TEMPLATE)) {
			return Integer.valueOf(dictThreadNums);
		} else if(StringUtil.equals(mqTemplate, PushErrorLogConstants.PERMISSION_MQ_TEMPLATE)) {
			return Integer.valueOf(permissionThreadNums);
		} else if(StringUtil.equals(mqTemplate, PushErrorLogConstants.ROLE_MQ_TEMPLATE)) {
			return Integer.valueOf(roleThreadNums);
		} else if(StringUtil.equals(mqTemplate, PushErrorLogConstants.USER_MQ_TEMPLATE)) {
			return Integer.valueOf(userThreadNums);
		}else {
			return defaultTopicThreadNums;
		}
	}
	
	public int getQueueCapacity(String mqTemplate) {
		if (StringUtil.equals(mqTemplate, PushErrorLogConstants.DICT_DATA_MQ_TEMPLATE)) {
			return Integer.valueOf(dictQueueCapacity);
		} else if(StringUtil.equals(mqTemplate, PushErrorLogConstants.PERMISSION_MQ_TEMPLATE)) {
			return Integer.valueOf(permissionQueueCapacity);
		} else if(StringUtil.equals(mqTemplate, PushErrorLogConstants.ROLE_MQ_TEMPLATE)) {
			return Integer.valueOf(roleQueueCapacity);
		} else if(StringUtil.equals(mqTemplate, PushErrorLogConstants.USER_MQ_TEMPLATE)) {
			return Integer.valueOf(userQueueCapacity);
		} else {
			return defaultQueueCapacity;
		}
	}
	
	/**
	 * 处理处理中的任务
	 */
	public void processDoingLog() {
		try {
			PushErrorLogVo searchVo = new PushErrorLogVo();
			searchVo.setProcessStatus(PushErrorLogConstants.PROCESS_STATUS_PROCESSING);
			List<PushErrorLogVo> processingLogs = pushErrorLogBiz.findProcessingLog(searchVo);
			if (processingLogs != null && processingLogs.size() > 0) {
				for (PushErrorLogVo pushErrorLogVo : processingLogs) {
					dispatcher(pushErrorLogVo);
				}
			}
		} catch (Exception e) {
			LOG.error("启动时加载处理中的错误日志任务失败", e);
		}
	}
	
	public void dispatcher(PushErrorLogVo pushErrorLogVo) throws InterruptedException {
		try {
			ThreadPoolExecutor executor = getTaskExecutor(pushErrorLogVo.getMqTemplete());
			executor.execute(new PushErrorLogTask(pushErrorLogVo, pushErrorLogBiz, dataPushBiz, maxProcessNum, whenFailProcessIntervalBase));
		} catch (RejectedExecutionException e) {
			synchronized (this) {
				this.wait(120 * 1000);
			}
			dispatcher(pushErrorLogVo);
		}
	}
	
	/**
	 * 更新为发送中
	 * @param productErrorLogVo 日志
	 * @return 是否更新成功
	 */
	public boolean updateToProcessing(PushErrorLogVo pushErrorLogVo) {
		PushErrorLogVo updateLog = new PushErrorLogVo();
		updateLog.setId(pushErrorLogVo.getId());
		updateLog.setProcessStatus(PushErrorLogConstants.PROCESS_STATUS_PROCESSING);
		updateLog.setUpdateTime(new Date());
		try {
			int count = pushErrorLogBiz.updateById(updateLog);
			if (count > 0) {
				return true;
			}
		} catch (Exception e) {
			LOG.error("更新为处理中失败", e);
		}
		return false;
	}
	
	public void setMaxProcessNum(Integer maxProcessNum) {
		this.maxProcessNum = maxProcessNum;
	}
	public void setWhenFailProcessIntervalBase(Integer whenFailProcessIntervalBase) {
		this.whenFailProcessIntervalBase = whenFailProcessIntervalBase;
	}

	public void setDefaultTopicThreadNums(int defaultTopicThreadNums) {
		this.defaultTopicThreadNums = defaultTopicThreadNums;
	}

	public void setDefaultQueueCapacity(int defaultQueueCapacity) {
		this.defaultQueueCapacity = defaultQueueCapacity;
	}

	public void setFetchSize(Integer fetchSize) {
		this.fetchSize = fetchSize;
	}

	public Integer getFetchSize() {
		return fetchSize;
	}

	public Integer getMaxProcessNum() {
		return maxProcessNum;
	}

	public Integer getWhenFailProcessIntervalBase() {
		return whenFailProcessIntervalBase;
	}

	public int getDefaultTopicThreadNums() {
		return defaultTopicThreadNums;
	}

	public int getDefaultQueueCapacity() {
		return defaultQueueCapacity;
	}

	public IPushErrorLogBiz getPushErrorLogBiz() {
		return pushErrorLogBiz;
	}

	public void setPushErrorLogBiz(IPushErrorLogBiz pushErrorLogBiz) {
		this.pushErrorLogBiz = pushErrorLogBiz;
	}

	public IDataPushBiz getDataPushBiz() {
		return dataPushBiz;
	}

	public void setDataPushBiz(IDataPushBiz dataPushBiz) {
		this.dataPushBiz = dataPushBiz;
	}
}
