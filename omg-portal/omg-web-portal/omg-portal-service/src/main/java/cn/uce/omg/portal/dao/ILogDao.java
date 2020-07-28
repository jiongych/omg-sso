package cn.uce.omg.portal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.core.db.IBaseDao;
import cn.uce.omg.portal.vo.LogVo;
/**
 * 操作日志DAO层
 * @author uce
 */
@Repository("logDao")
public interface ILogDao extends IBaseDao<LogVo, Long> {
	/**
	 * 保存单条日志记录
	 * @param log
	 */
	void saveLog(LogVo log);

	/**
	 * 保存批量日志记录
	 * @param log
	 */
	void saveLogList(@Param("list") List<Object> list);

	/**
	 * 根据条件查询日志信息
	 * @param logVo
	 * @param page
	 * @return
	 */
	Pagination<LogVo> findAllLogs(LogVo logVo, Page page);

}
