package cn.uce.omg.portal.job;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.uce.base.page.Page;
import cn.uce.omg.portal.util.PushErrorLogConstants;
import cn.uce.omg.portal.vo.PushErrorLogVo;
import cn.uce.utils.JsonUtil;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
/**
 * 数据同步定时任务处理类
 * @author uce
 */
public class PushErrorLogProcessor implements DataflowJob<PushErrorLogVo> {
    private Logger LOG = LoggerFactory.getLogger(PushErrorLogProcessor.class);
    @Resource(name="abstractPushErrorLogProcessor")
    private AbstractPushErrorLogProcessor abstractPushErrorLogProcessor;
	
	@Override
	public List<PushErrorLogVo> fetchData(ShardingContext context) {
		if(LOG.isInfoEnabled()) {
			LOG.info("insert fetchData...");
		}
		if(LOG.isInfoEnabled()) {
			LOG.info("分片item：" + context.getShardingItem() + ",分片参数：" + context.getShardingParameter() + ",分片总数：" + context.getShardingTotalCount());
		}
		PushErrorLogVo searchVo = new PushErrorLogVo();
		searchVo.setProcessStatus(PushErrorLogConstants.PROCESS_STATUS_UNPROCESS);
		searchVo.setNextProcessTime(new Date());
		searchVo.setShardingItem(context.getShardingItem());
		searchVo.setShardingTotal(context.getShardingTotalCount());
		Page page = new Page();
		page.setPageSize(abstractPushErrorLogProcessor.getFetchSize());
		page.setCurrentPage(1);
		searchVo.setPage(page);
		List<PushErrorLogVo> errorLogList = abstractPushErrorLogProcessor.getPushErrorLogBiz().findWaitProcessLog(searchVo);
		if(LOG.isInfoEnabled()) {
			LOG.info("fetchData: " + JsonUtil.toJson(errorLogList));
		}
		return errorLogList;
	}

	@Override
	public void processData(ShardingContext shardingContext, List<PushErrorLogVo> data) {
		if(LOG.isInfoEnabled()) {
			LOG.info("processData: " + JsonUtil.toJson(data));
		}
		//由于处理完后，会更新处理状态，所以当数据比较多时，循环处理，直到该批次数据处理完成
		for (PushErrorLogVo pushErrorLogVo : data) {
			if (abstractPushErrorLogProcessor.updateToProcessing(pushErrorLogVo)) {
				try {
					abstractPushErrorLogProcessor.dispatcher(pushErrorLogVo);
				} catch (InterruptedException e) {
					LOG.error(e.getMessage());
				}
			}
		}
	}
}
