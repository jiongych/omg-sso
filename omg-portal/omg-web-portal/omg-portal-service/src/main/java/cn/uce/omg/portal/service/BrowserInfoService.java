package cn.uce.omg.portal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.dao.IBrowserInfoDao;
import cn.uce.omg.portal.vo.PortalBrowserInfoVo;
import cn.uce.utils.StringUtil;

@Service("browserInfoService")
public class BrowserInfoService {
	@Resource
	private IBrowserInfoDao browserInfoDao;
	/**
	 * 加载浏览器使用情况统计数据
	 * @return
	 * liyi
	 * 2018-04-28 15:18:233
	 */
	public Map<String, Object> loadBrowserInfo() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("statisticsType", "2");
		params.put("statisticsCategory", "1");
		List<Map<String, Object>> loginNums = browserInfoDao.findLoginStatisticsInfo(params);
		
		params.put("statisticsCategory", "2");
		List<Map<String, Object>> browserNums = browserInfoDao.findLoginStatisticsInfo(params);
		List<String> browserNames = browserInfoDao.findBrowserName(params);
		Map<String, List<Long>> browserInfo = new HashMap<String, List<Long>>();
		for (String browserName : browserNames) {
			List<Long> browserList = new ArrayList<Long>();
			for (Map<String, Object> month : loginNums) {
				String monthNum = String.valueOf(month.get("statisticsDate"));
				boolean flag = true;
				for (int i = browserNums.size() - 1; i >= 0; i--) {
					if (StringUtil.equals(monthNum,String.valueOf(browserNums.get(i).get("statisticsDate")))
							&& StringUtil.equals(browserName,String.valueOf(browserNums.get(i).get("statisticsName")))) {
						browserList.add(Long.valueOf(String.valueOf(browserNums.get(i).get("loginTimes"))));
						flag = false;
						break;
					}
				}
				/*for (Map<String, Object> map : browserNums) {
					if (StringUtil.equals(monthNum,String.valueOf(map.get("statisticsDate")))
							&& StringUtil.equals(browserName,String.valueOf(map.get("statisticsName")))) {
						browserList.add(Long.valueOf(String.valueOf(map.get("loginTimes"))));
						flag = false;
						break;
					}
				}*/
				if (flag) {
					browserList.add(Long.valueOf(0));
				}
			}
			browserInfo.put(browserName, browserList);
		}
		List<Map<String, Object>> resultNums = new ArrayList<Map<String,Object>>();
		if(null != loginNums && !loginNums.isEmpty()) {
			for (int i = loginNums.size() - 1; i >= 0; i--) {
				Map<String,Object> result = new HashMap<String,Object>();
				result.put("loginNums", loginNums.get(i).get("loginTimes"));
				result.put("monthNum", loginNums.get(i).get("statisticsDate"));
				resultNums.add(result);
			}
			/*for(Map<String,Object> map : loginNums) {
				Map<String,Object> result = new HashMap<String,Object>();
				result.put("loginNums", map.get("loginTimes"));
				result.put("monthNum", map.get("statisticsDate"));
				resultNums.add(result);
			}*/
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("loginNums", resultNums);//[{loginNums=21, monthNum=17-01}, {loginNums=9, monthNum=17-04}, {loginNums=21, monthNum=18-01}, {loginNums=23, monthNum=18-02}, {loginNums=24, monthNum=18-03}, {loginNums=87, monthNum=18-04}, {loginNums=490, monthNum=18-05}, {loginNums=64, monthNum=18-06}]

		result.put("browserInfo", browserInfo);//{Firefox=[0, 0, 0, 0, 0, 0, 1, 0], Edge=[0, 0, 0, 0, 0, 3, 5, 0], Chrome=[21, 9, 21, 23, 24, 84, 482, 59], 360浏览器=[0, 0, 0, 0, 0, 0, 2, 0], IE=[0, 0, 0, 0, 0, 0, 0, 2], 360=[0, 0, 0, 0, 0, 0, 0, 3]}
		return result;
	}
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);
		list.add(7);
		list.add(8);
		list.add(9);
		for (int i = list.size() - 1; i >= 0; i--) {
			System.out.println(list.get(i));
		}
	}

	/**
	 * 统计每日登陆人数及登陆次数
	 * @return
	 */
	public Map<String, Object> loadLoginPepoleInfo() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("statisticsType", "1");
		params.put("statistics_date", "60");
		List<Map<String, Object>> loginPepoles = browserInfoDao.findLoginStatisticsInfo(params);
		// 处理每日登陆人数
		Map<String, List<Map<String, Object>>> daysNums = new HashMap<String, List<Map<String, Object>>>();
		return buildResults(daysNums,loginPepoles);
	}

	private Map<String, Object> buildResults(
			Map<String, List<Map<String, Object>>> daysNums,List<Map<String, Object>> loginPepoles) {
		Map<String, Object> loginNums = new HashMap<String,Object>();
		if (null != loginPepoles && !loginPepoles.isEmpty()) {
			List<String> daysList = new ArrayList<String>();
			List<String> loginList = new ArrayList<String>();
			List<String> loginTime = new ArrayList<String>();
			for (Map<String,Object> map : loginPepoles) {
				// 处理登录时间
				daysList.add(String.valueOf(map.get("statisticsDate")));
				loginList.add(String.valueOf(map.get("loginPeoples")));
				loginTime.add(String.valueOf(map.get("loginTimes")));
			}
			loginNums.put("loginTimes", loginTime);
			loginNums.put("loginNums", loginList);
			loginNums.put("days", daysList);
		}
		return loginNums;
	}

	/**
	 * 统计查询分辨率
	 * @return
	 */
	public Map<String, Long> loadResolutionRatio() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("statisticsType", "5");
		List<Map<String, Object>> loginPepoles = browserInfoDao.findLoginStatisticsInfo(params);
		Map<String, Long> result = new HashMap<String,Long>();
		Long times = 0L;
		for(int i = 0; i < loginPepoles.size(); i++) {
			Map<String,Object> map = loginPepoles.get(i);
			if(i < 7) {
				String resolutionRatio = String.valueOf(map.get("statisticsName"));
				result.put(resolutionRatio, Long.valueOf(map.get("loginTimes").toString()));
			}else {
				times += Long.valueOf(map.get("loginTimes").toString());
			}
		}
//		if(times != 0) {
//			result.put("其他", times);
//		}
		return result;
	}

	/**
	 * 加载登录人次排名统计数据
	 * @return
	 * liyi
	 * 2018-04-28 15:18:233
	 */
	public Map<String, Object> loadLoginRankingInfo(String value) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("statisticsType", "4");
		if(StringUtil.isNotBlank(value) && StringUtil.equals(value, "0")) {
			params.put("statisticsType", "3");
		}
		List<Map<String, Object>> list = browserInfoDao.findLoginStatisticsInfo(params);
		List<Map<String, Object>> rankingInfos = new ArrayList<Map<String,Object>>();
		if(null != list && !list.isEmpty()) {
			for(Map<String,Object> map : list) {
				Map<String,Object> resultMap = new HashMap<String,Object>();
				resultMap.put("createEmp", map.get("statisticsName"));
				resultMap.put("ranking", map.get("loginTimes"));
				resultMap.put("empName", map.get("remark"));
				rankingInfos.add(resultMap);
			}
		}
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("result", rankingInfos);
		return result;
	}

	/**
	 * 加载浏览器详细信息
	 * @param browserInfoVo
	 * @param page
	 * @return
	 */
	public Pagination<PortalBrowserInfoVo> findAllBrowserInfo(
			PortalBrowserInfoVo browserInfoVo, Page page) {
		return browserInfoDao.findAllBrowserInfo(browserInfoVo, page);
	}
}
