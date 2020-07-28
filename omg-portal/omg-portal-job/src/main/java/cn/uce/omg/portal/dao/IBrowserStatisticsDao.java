package cn.uce.omg.portal.dao;

import java.util.List;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.portal.entity.PortalPushErrorLog;
import cn.uce.omg.portal.vo.BrowserStatisticsInfoVo;

public interface IBrowserStatisticsDao extends IBaseDao<PortalPushErrorLog, Long>{

	BrowserStatisticsInfoVo findDaysLoginTimes();

	BrowserStatisticsInfoVo findDaysLoginPeoples();

	void insertBrowerStatiscsResult(BrowserStatisticsInfoVo loginTimes);

	Integer findStatisticsInfoByDate(BrowserStatisticsInfoVo loginTimes);

	void deleteStatisticsInfoByDate(BrowserStatisticsInfoVo loginTimes);

	List<BrowserStatisticsInfoVo> findBrowserLoginTimes();

	List<BrowserStatisticsInfoVo> findScreenPeoples();

	List<BrowserStatisticsInfoVo> findRankingInfo(Integer param);

}
