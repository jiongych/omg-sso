package cn.uce.omg.portal.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.core.db.IBaseDao;
import cn.uce.omg.portal.entity.PortalBrowserInfo;
import cn.uce.omg.portal.vo.PortalBrowserInfoVo;
@Repository("browserInfoDao")
public interface IBrowserInfoDao extends IBaseDao<PortalBrowserInfo,Long>{

	List<Map<String, Object>> findMonthLoginNums();

	List<Map<String, Object>> findMonthBrowserNums();

	List<String> findBrowserName(Map<String, String> params);

	List<Map<String, Object>> findLoginPeopleNums();

	List<Map<String, Object>> findDaysLoginTimes();

	List<Map<String, Object>> findResolutionRatio();

	List<Map<String, Object>> findLoginRankingInfo(Integer interval);

	List<Map<String, Object>> findLoginStatisticsInfo(Map<String, String> params);

	Pagination<PortalBrowserInfoVo> findAllBrowserInfo(
			PortalBrowserInfoVo browserInfoVo, Page page);
}
