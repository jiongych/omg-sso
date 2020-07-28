package cn.uce.omg.portal.biz;

import java.util.List;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.vo.LogVo;

public interface ILogBiz {

	void saveLog(LogVo log);

	void saveLogList(List<Object> msg);

	Pagination<LogVo> findAllLogs(LogVo logVo, Page page);
}
