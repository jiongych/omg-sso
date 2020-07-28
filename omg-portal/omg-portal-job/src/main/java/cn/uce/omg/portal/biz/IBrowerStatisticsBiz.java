package cn.uce.omg.portal.biz;

import cn.uce.omg.portal.vo.PortalStatisticsTaskVo;

public interface IBrowerStatisticsBiz {
	
	void loginStatistics(PortalStatisticsTaskVo pstv);

	void browserStatistics(PortalStatisticsTaskVo bsiv);

	void screenStatistics(PortalStatisticsTaskVo bsiv);

	void rankingStatistics(PortalStatisticsTaskVo bsiv);
}
