package cn.uce.omg.portal.job;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.uce.omg.portal.biz.IDataPushBiz;
import cn.uce.omg.portal.biz.IPushErrorLogBiz;
import cn.uce.omg.portal.util.ExceptionUtil;
import cn.uce.omg.portal.util.PushErrorLogConstants;
import cn.uce.omg.portal.vo.InvokeExceptionInfoVo;
import cn.uce.omg.portal.vo.PushErrorLogVo;
import cn.uce.utils.FastJsonUtil;
import cn.uce.utils.StringUtil;

public class PushErrorLogTask implements Runnable {

	private IPushErrorLogBiz pushErrorLogBiz;
	private IDataPushBiz dataPushBiz;
	private PushErrorLogVo pushErrorLogVo;
	private Integer maxProcessNum;
	private Integer whenFailProcessInterval;
	private final static Logger LOG = LoggerFactory.getLogger(PushErrorLogTask.class);
	
	public PushErrorLogTask(PushErrorLogVo pushErrorLogVo, IPushErrorLogBiz pushErrorLogBiz, 
			IDataPushBiz dataPushBiz, Integer maxProcessNum, Integer whenFailProcessInterval) {
		this.pushErrorLogVo = pushErrorLogVo;
		this.dataPushBiz = dataPushBiz;
		this.pushErrorLogBiz = pushErrorLogBiz;
		this.maxProcessNum = maxProcessNum;
		this.whenFailProcessInterval = whenFailProcessInterval;
	}
	
	/**
	 * job处理推送数据线程
	 */
	@Override
	public void run() {
		// 判断待推送数据是否为空，如果为则不推送
		if (convertProductErrorLogContent(pushErrorLogVo)) {
			invoke(pushErrorLogVo);
		}
	}
	
	/**
	 * 解析待推送数据，调用推送方法，推送数据到MQ
	 * @param pushErrorLogVo
	 */
	private void invoke(PushErrorLogVo pushErrorLogVo) {
		try {
			dataPushBiz.resend(pushErrorLogVo.getSyncContent(), pushErrorLogVo.getOperatorType(), pushErrorLogVo.getMqTemplete());
			whenInvokeSuccess(pushErrorLogVo);
		} catch (Exception e) {
			//不再重试的异常
			LOG.error("处理uop错误日志记录失败， 请求标识：" + pushErrorLogVo.getReqId(), e);
			whenInvokeException(e, pushErrorLogVo);
		}
	}
	/**
	 * 如果推送成功则删除记录
	 * @param pushErrorLogVo
	 */
	private void whenInvokeSuccess(PushErrorLogVo pushErrorLogVo) {
		//成功
		pushErrorLogBiz.deleteLog(pushErrorLogVo.getId());
	}
	
	/**
	 * 推送数据异常处理，判断是否需要重试，作不同的处理
	 * @param e
	 * @param pushErrorLogVo
	 */
	private void whenInvokeException(Exception e, PushErrorLogVo pushErrorLogVo) {
		String remark = e.getMessage();
		if (remark != null && remark.getBytes().length > 512) {
			pushErrorLogVo.setRemark(remark.substring(0, 216));
		} else {
			pushErrorLogVo.setRemark(remark);
		}
		InvokeExceptionInfoVo errorVo = ExceptionUtil.getException(e);
		pushErrorLogVo.setTotalProcessNum(pushErrorLogVo.getTotalProcessNum() + 1);
		if (errorVo.getRetry() != null && errorVo.getRetry()) {
			whenInvokeThrowsRetryException(pushErrorLogVo);
		} else {
			whenInvokeThrowsNotRetryException(pushErrorLogVo);
		}
	}	
	
	/**
	 * 推送异常，需要重试处理
	 * @param pushErrorLogVo
	 */
	private void whenInvokeThrowsRetryException(PushErrorLogVo pushErrorLogVo) {
		//重试达到次数后，通知回调
		updateToProcessFail(pushErrorLogVo);
	}
	/**
	 * 推送异常，不需要重试处理
	 * @param pushErrorLogVo
	 */
	private void whenInvokeThrowsNotRetryException(PushErrorLogVo pushErrorLogVo) {
		//处理失败
		pushErrorLogVo.setProcessNum(-1);
		pushErrorLogVo.setNextProcessTime(null);
		pushErrorLogVo.setProcessStatus(PushErrorLogConstants.PROCESS_STATUS_PROCESS_FAIL);
		pushErrorLogBiz.updateToProcessEnd(pushErrorLogVo);
	}
	
	/**
	 * 获取推送异常失败具体失败信息
	 * @param e
	 * @return
	 */
	private String getExceptionRemark(Exception e) {
		String remark = e.getMessage();
		if (remark != null && remark.getBytes().length > 512) {
			remark = remark.substring(0, 512);
		}
		return remark;
	}
	
	/**
	 * 推送失败，更新数据状态信息
	 * @param pushErrorLogVo
	 */
	private void updateToProcessFail(PushErrorLogVo pushErrorLogVo) {
		try {
			PushErrorLogVo updateLog = new PushErrorLogVo();
			updateLog.setId(pushErrorLogVo.getId());
			updateLog.setProcessStatus(PushErrorLogConstants.PROCESS_STATUS_PROCESS_FAIL);
			updateLog.setUpdateTime(new Date());
			updateLog.setRemark(pushErrorLogVo.getRemark());
			updateLog.setProcessNum(pushErrorLogVo.getProcessNum() + 1);
			updateLog.setTotalProcessNum(pushErrorLogVo.getTotalProcessNum());
			if (pushErrorLogVo.getSyncContent() != null) {
				updateLog.setSyncContent(pushErrorLogVo.getSyncContent());
			} 
			if (updateLog.getProcessNum() >= maxProcessNum) {
				updateLog.setProcessNum(-1);
				updateLog.setNextProcessTime(null);
			} else {
				updateLog.setNextProcessTime(new Date(System.currentTimeMillis() + (updateLog.getProcessNum() + 1) * whenFailProcessInterval * 1000));
			}
			pushErrorLogBiz.updateToProcessEnd(updateLog);
		} catch (Exception e) {
			LOG.error("修改日志记录为回调失败", e);
		}
	}
	
	
	/**
	 * 更新为处理结束
	 * @param productErrorLogVo 日志
	 * @return 是否更新成功
	 */
	private void updateToProcessEnd(PushErrorLogVo pushErrorLogVo) {
		try {
			PushErrorLogVo updateLog = new PushErrorLogVo();
			updateLog.setId(pushErrorLogVo.getId());
			updateLog.setProcessStatus(PushErrorLogConstants.PROCESS_STATUS_PROCESS_FAIL);
			updateLog.setUpdateTime(new Date());
			updateLog.setRemark(pushErrorLogVo.getRemark());
			if (pushErrorLogVo.getSyncContent() != null) {
				updateLog.setSyncContent(FastJsonUtil.toJsonString(pushErrorLogVo.getSyncContent(), true));
			} else {
				updateLog.setSyncContent("");
			}
			updateLog.setProcessNum(pushErrorLogVo.getProcessNum() + 1);
			updateLog.setTotalProcessNum(pushErrorLogVo.getTotalProcessNum());
			updateLog.setNextProcessTime(null);
			pushErrorLogBiz.updateToProcessEnd(updateLog);
		} catch (Exception e) {
			LOG.error("修改日志记录为回调失败", e);
		}
	}
	/**
	 * 转换为调用接口的数据
	 * @param productErrorLogVo
	 * @return
	 * @throws ClassNotFoundException
	 */
	private boolean convertProductErrorLogContent(PushErrorLogVo pushErrorLogVo) {
		try {
			//调用
			if(StringUtil.isEmpty(pushErrorLogVo.getSyncContent())) {
				return false;
			}
		} catch (Exception e) {
			LOG.error("异步处理失败", e);
			pushErrorLogVo.setRemark(getExceptionRemark(e));
			pushErrorLogVo.setProcessStatus(PushErrorLogConstants.PROCESS_STATUS_PROCESS_FAIL);
			updateToProcessEnd(pushErrorLogVo);
			return false;
		}
		return true;
	}
}
