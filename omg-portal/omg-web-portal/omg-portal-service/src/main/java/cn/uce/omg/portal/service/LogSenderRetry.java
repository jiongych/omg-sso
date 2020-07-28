package cn.uce.omg.portal.service;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.uce.omg.portal.vo.LogVo;
/**
 * 使用多线程异步保存日志处理类
 * @author uce
 * 2018-03-26 10:00:00
 */
public class LogSenderRetry extends Thread {
	private final static Logger LOG = LoggerFactory.getLogger(LogSenderRetry.class);
	LinkedBlockingQueue<List<Object>> retryQueue = new LinkedBlockingQueue<List<Object>>();
	private boolean exitFlag = false;
	private LogService logService;
	
	@Override
	public void run() {
		while (!exitFlag) {
			List<Object> logList;
			try {
				logList = retryQueue.take();
			} catch (InterruptedException e) {
				continue;
			}
			if (logList != null) {
				for (Object log : logList) {
					if (log instanceof LogVo) {
						try {
							logService.saveLog((LogVo) log);
						} catch (Exception e) {
							LOG.error("日志入库失败", e);
						}
					}
				}
			}
		}
	}

	public void addToRetry(List<Object> orderTrackList) {
		try {
			retryQueue.put(orderTrackList);
		} catch (InterruptedException e) {
			LOG.error("add log to retry error", e);
		}
	}
	

	public void setExitFlag(boolean exitFlag) {
		this.exitFlag = exitFlag;
	}

	public LogService getLogService() {
		return logService;
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}
