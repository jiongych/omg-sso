package cn.uce.omg.portal.biz.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.biz.IBrowserInfoBiz;
import cn.uce.omg.portal.service.BrowserInfoService;
import cn.uce.omg.portal.vo.PortalBrowserInfoVo;
@Service("browserInfoBiz")
public class BrowserInfoBiz implements IBrowserInfoBiz {
	
    @Autowired
	private BrowserInfoService browserInfoService;
    /**
	 * 加载浏览器使用情况统计数据
	 * @return
	 * liyi
	 * 2018-04-28 15:18:233
	 */
	@Override
	public Map<String, Object> loadBrowserInfo() {
		return browserInfoService.loadBrowserInfo();
	}
	
	/**
	 * 加载登陆人数统计数据
	 * @return
	 * liyi
	 * 2018-04-28 15:18:233
	 */
	@Override
	public Map<String, Object> loadLoginPepoleInfo() {
		return browserInfoService.loadLoginPepoleInfo();
	}
	
	/**
	 * 加载分辨率统计数据
	 * @return
	 * liyi
	 * 2018-04-28 15:18:233
	 */
	@Override
	public Map<String, Long> loadResolutionRatio() {
		return browserInfoService.loadResolutionRatio();
	}
	
	/**
	 * 加载登录人次排名统计数据
	 * @return
	 * liyi
	 * 2018-04-28 15:18:233
	 */
	@Override
	public Map<String, Object> loadLoginRankingInfo(String value) {
		return browserInfoService.loadLoginRankingInfo(value);
	}

	@Override
	public Pagination<PortalBrowserInfoVo> findAllBrowserInfo(
			PortalBrowserInfoVo browserInfoVo, Page page) {
		return browserInfoService.findAllBrowserInfo(browserInfoVo, page);
	}

}
