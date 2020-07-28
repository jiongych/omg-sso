package cn.uce.omg.sso.log;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.uce.omg.sso.service.PortalService;
import cn.uce.omg.sso.vo.portal.PortalBrowserVo;
/**
 * 使用多线程异步保存日志处理类
 * @author uce
 * 2018-03-26 10:00:00
 */
@Component
public class LogSenderRetry extends Thread {
	private final static Logger LOG = LoggerFactory.getLogger(LogSenderRetry.class);
	LinkedBlockingQueue<List<Object>> retryQueue = new LinkedBlockingQueue<List<Object>>();
	private boolean exitFlag = false;
	@Resource
	private PortalService portalService;
	
	@Override
	public void run() {
		while (!exitFlag) {
			List<Object> list;
			try {
				list = retryQueue.take();
			} catch (InterruptedException e) {
				continue;
			}
			if (list != null) {
				for (Object obj : list) {
					if (obj instanceof PortalBrowserVo) {
						try {
							portalService.saveBrowserInfo((PortalBrowserVo)obj);
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

	public PortalService getPortalService() {
		return portalService;
	}

	public void setPortalService(PortalService portalService) {
		this.portalService = portalService;
	}
}
