package cn.uce.omg.portal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.dao.ILogDao;
import cn.uce.omg.portal.vo.LogVo;
/**
 * 操作日志服务处理层
 * @author uce
 */
@Service("logService")
public class LogService {
	@Autowired
	private ILogDao logDao;
	
	/**
	 * 保存单条日志记录
	 * @param log
	 */
	public void saveLog(LogVo log) {
		if(null != log) {
			logDao.saveLog(log);
		}
	}

	/**
	 * 保存批量日志记录
	 * @param log
	 */
	public void saveLogList(List<Object> list) {
		if(null != list && !list.isEmpty()) {
			logDao.saveLogList(list);
		}
	}

	/**
	 * 根据条件查询日志信息
	 * @param logVo
	 * @param page
	 * @return
	 */
	public Pagination<LogVo> findAllLogs(LogVo logVo, Page page) {
		return logDao.findAllLogs(logVo,page);
	}

	public ILogDao getLogDao() {
		return logDao;
	}

	public void setLogDao(ILogDao logDao) {
		this.logDao = logDao;
	}
}
