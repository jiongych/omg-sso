package cn.uce.omg.portal.service;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import cn.uce.core.log.server.sender.ILogSender;
public class LogSender implements ILogSender, InitializingBean,
		DisposableBean {
	protected final Logger LOG = LoggerFactory.getLogger(LogSender.class);
	private ExecutorService threadPool = null;
	private int threadSize = 10;
	private int queueSize = 5000;
	private LogService logService;
	private LogSenderRetry retryThread;

	@Override
	public void send(List<Object> msg) {
		threadPool.submit(new SendTask(msg));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		threadPool = new ThreadPoolExecutor(threadSize, threadSize, 60,
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize),
				new ThreadPoolExecutor.AbortPolicy());
		retryThread = new LogSenderRetry();
		retryThread.setLogService(logService);
		retryThread.start();
	}

	private class SendTask implements Runnable {

		private List<Object> msg;

		public SendTask(List<Object> list) {
			this.msg = list;
		}

		@Override
		public void run() {
			try {
				logService.saveLogList(msg);
			} catch (Exception e) {
				LOG.error("日志入库失败", e);
				retryThread.addToRetry(msg);
			}
		}
	}

	

	public LogService getLogService() {
		return logService;
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	public void setThreadSize(int threadSize) {
		this.threadSize = threadSize;
	}

	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}

	@Override
	public void destroy() throws Exception {
		if (retryThread != null) {
			retryThread.setExitFlag(true);
		}
	}

	public int getThreadSize() {
		return threadSize;
	}

	public int getQueueSize() {
		return queueSize;
	}
}
