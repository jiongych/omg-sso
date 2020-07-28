package cn.uce.omg.portal.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.uce.omg.portal.dao.IBrowserStatisticsDao;
import cn.uce.omg.portal.vo.BrowserStatisticsInfoVo;
import cn.uce.omg.portal.vo.PortalStatisticsTaskVo;

@Service("browerStatisticsService")
public class BrowerStatisticsService {
	private Logger log = LoggerFactory.getLogger(BrowerStatisticsService.class);
	@Autowired
	private IBrowserStatisticsDao browerStatisticsDao;
	
	/**
	 * 统计每日登陆人次
	 * @param pstv
	 */
	public void loginStatistics(PortalStatisticsTaskVo pstv) {
		try {
			BrowserStatisticsInfoVo loginTimes = browerStatisticsDao.findDaysLoginTimes();
			BrowserStatisticsInfoVo loginPeoples = browerStatisticsDao.findDaysLoginPeoples();
			loginTimes.setLoginPeoples(loginPeoples.getLoginPeoples());
			loginTimes.setStatisticsType("1");
			Integer count = browerStatisticsDao.findStatisticsInfoByDate(loginTimes);
			if(count > 0){
				browerStatisticsDao.deleteStatisticsInfoByDate(loginTimes);
			}
			browerStatisticsDao.insertBrowerStatiscsResult(loginTimes);
		} catch (Exception e) {
			log.error("统计每日登陆人次异常：" + e.getMessage());
		}
	}

	/**
	 * 每月浏览器使用统计
	 */
	public void browserStatistics(PortalStatisticsTaskVo bstv) {
		List<BrowserStatisticsInfoVo> list = browerStatisticsDao.findBrowserLoginTimes();
		if(null != list && !list.isEmpty()) {
			BrowserStatisticsInfoVo parent = new BrowserStatisticsInfoVo();
			parent.setStatisticsDate(list.get(0).getStatisticsDate());
			parent.setStatisticsName("每月浏览器使用总计");
			parent.setStatisticsType("2");
			parent.setStatisticsCategory("1");
			Integer oldCount = browerStatisticsDao.findStatisticsInfoByDate(parent);
			if(oldCount > 0){
				browerStatisticsDao.deleteStatisticsInfoByDate(parent);
			}
			int count = 0;
			for(BrowserStatisticsInfoVo bsiv : list) {
				bsiv.setStatisticsType("2");
				bsiv.setStatisticsCategory("2");
				count += bsiv.getLoginTimes();
				browerStatisticsDao.insertBrowerStatiscsResult(bsiv);
			}
			parent.setLoginTimes(count);
			browerStatisticsDao.insertBrowerStatiscsResult(parent);
		}
	}

	/**
	 * 最近30天分辨率使用统计
	 */
	public void screenStatistics(PortalStatisticsTaskVo bsiv) {
		List<BrowserStatisticsInfoVo> list = browerStatisticsDao.findScreenPeoples();
		Map<String, Integer> result = new HashMap<String,Integer>();
		if(null != list && !list.isEmpty()) {
			for(BrowserStatisticsInfoVo vo : list) {
				if(null == result.get(vo.getStatisticsName())){
					result.put(vo.getStatisticsName(), 1);
				}else {
					Integer temp = result.get(vo.getStatisticsName());
					temp += 1;
					result.put(vo.getStatisticsName(), temp);
				}
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			BrowserStatisticsInfoVo parent = new BrowserStatisticsInfoVo();
			parent.setStatisticsDate(sdf.format(new Date()));
			parent.setStatisticsType("5");
			Integer oldCount = browerStatisticsDao.findStatisticsInfoByDate(parent);
			if(oldCount > 0){
				browerStatisticsDao.deleteStatisticsInfoByDate(parent);
			}
			Set<String> keys = result.keySet();
			for (String key : keys) {
				BrowserStatisticsInfoVo vo = new BrowserStatisticsInfoVo();
				vo.setStatisticsName(key);
				vo.setStatisticsType("5");
				vo.setLoginTimes(result.get(key));
				vo.setStatisticsDate(sdf.format(new Date()));
				browerStatisticsDao.insertBrowerStatiscsResult(vo);
			}
		}
	}

	/**
	 * 统计最近7天与最近30天的登陆排名前10
	 * @param bsiv
	 */
	public void rankingStatistics(PortalStatisticsTaskVo bsiv) {
		Integer[] params = {7,30};
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		BrowserStatisticsInfoVo parent = new BrowserStatisticsInfoVo();
		parent.setStatisticsDate(sdf.format(new Date()));
		for(Integer param : params) {
			List<BrowserStatisticsInfoVo> list = browerStatisticsDao.findRankingInfo(param);
			parent.setStatisticsType(param == 7?"3" : "4");
			Integer oldCount = browerStatisticsDao.findStatisticsInfoByDate(parent);
			if(oldCount > 0){
				browerStatisticsDao.deleteStatisticsInfoByDate(parent);
			}
			if(null != list && !list.isEmpty()) {
				for(BrowserStatisticsInfoVo vo : list) {
					vo.setStatisticsDate(sdf.format(new Date()));
					vo.setStatisticsType(parent.getStatisticsType());
					browerStatisticsDao.insertBrowerStatiscsResult(vo);
				}
			}
     	}
	}
}
