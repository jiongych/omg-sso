package cn.uce.omg.portal.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.uce.omg.portal.biz.IBrowerStatisticsBiz;
import cn.uce.omg.portal.service.BrowerStatisticsService;
import cn.uce.omg.portal.vo.PortalStatisticsTaskVo;
@Service("browerStatisticsBiz")
public class BrowerStatisticsBiz implements IBrowerStatisticsBiz {
	@Autowired
    private BrowerStatisticsService browerStatisticsService;
	
	/**
	 * 统计每天登陆人次
	 */
	@Override
	public void loginStatistics(PortalStatisticsTaskVo pstv) {
		browerStatisticsService.loginStatistics(pstv);
	}

	/**
	 * 每月浏览器使用统计
	 */
	@Override
	public void browserStatistics(PortalStatisticsTaskVo bsiv) {
		browerStatisticsService.browserStatistics(bsiv);
	}

	/**
	 * 最近30天使用统计
	 */
	@Override
	public void screenStatistics(PortalStatisticsTaskVo bsiv) {
		browerStatisticsService.screenStatistics(bsiv);
	}

	/**
	 * 统计最近7天与最近30天登陆排名
	 */
	@Override
	public void rankingStatistics(PortalStatisticsTaskVo bsiv) {
		browerStatisticsService.rankingStatistics(bsiv);
	}
}
