package cn.uce.omg.portal.biz.impl;

import java.util.List;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.biz.ILogBiz;
import cn.uce.omg.portal.service.LogService;
import cn.uce.omg.portal.vo.LogVo;
import cn.uce.utils.JsonUtil;
/**
 * 日志业务处理类
 * @author uce
 *
 */
@Service("logBiz")
public class LogBiz implements ILogBiz {
	private Logger LOG = LoggerFactory.getLogger(LogBiz.class);
	@Autowired
	private LogService logService;
	
	/**
	 * 日志具体处理类
	 */
	@Override
	public void saveLog(LogVo log) {
		if(LOG.isInfoEnabled()) {
			LOG.info("LogBiz saveLog start:" + JsonUtil.toJson(log));
		}
		try {
			logService.saveLog(log);
		} catch (Exception e) {
			LOG.error("LogBiz saveLog exception: " + e.getMessage());
		}
	}

	/**
	 * 日志批量具体处理类
	 */
	@Override
	public void saveLogList(List<Object> list) {
		if(LOG.isInfoEnabled()) {
			LOG.info("LogBiz saveLogList start:" + JsonUtil.toJson(list));
		}
		try {
			logService.saveLogList(list);
		} catch (Exception e) {
			LOG.error("LogBiz saveLogList exception: " + e.getMessage());
		}
	}
	
	/**
	 * 根据条件查询操作日志信息
	 */
	@Override
	public Pagination<LogVo> findAllLogs(LogVo logVo, Page page) {
		return logService.findAllLogs(logVo,page);
	}

	public LogService getLogService() {
		return logService;
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}
