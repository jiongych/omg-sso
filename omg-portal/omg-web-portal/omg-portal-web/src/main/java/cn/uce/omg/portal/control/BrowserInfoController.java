package cn.uce.omg.portal.control;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.uce.omg.portal.biz.IBrowserInfoBiz;
import cn.uce.web.common.base.BaseController;
@Controller
@RequestMapping("/browser")
public class BrowserInfoController extends BaseController {
	@Autowired
    private IBrowserInfoBiz browserInfoBiz;
	@RequestMapping(value = "/forward")
	public String get()
			throws IOException {
		return "portal/browserInfo";
	}
	
	@RequestMapping(value = "/loginPeople")
	public String loginPeople()
			throws IOException {
		return "portal/loginPeople";
	}
	
	
	/**
	 * 加载浏览器使用情况统计数据
	 * @return
	 * liyi
	 * 2018-04-28 15:18:233
	 */
	@RequestMapping(value = "/loadBrowserInfo")
	@ResponseBody
	public Map<String,Object> loadBrowserInfo() {
		return browserInfoBiz.loadBrowserInfo();
	}
	
	/**
	 * 加载登陆人数统计数据
	 * @return
	 * liyi
	 * 2018-04-28 15:18:233
	 */
	@RequestMapping(value = "/loadLoginPepoleInfo")
	@ResponseBody
	public Map<String,Object> loadLoginPepoleInfo() {
		return browserInfoBiz.loadLoginPepoleInfo();
	}
	
	/**
	 * 加载分辨率统计数据
	 * @return
	 * liyi
	 * 2018-04-28 15:18:233
	 */
	@RequestMapping(value = "/loadResolutionRatio")
	@ResponseBody
	public Map<String,Long> loadResolutionRatio() {
		return browserInfoBiz.loadResolutionRatio();
	}
	
	/**
	 * 加载登录人次排名统计数据
	 * @return
	 * liyi
	 * 2018-04-28 15:18:233
	 */
	@RequestMapping(value = "/loadLoginRankingInfo")
	@ResponseBody
	public Map<String,Object> loadLoginRankingInfo(String value) {
		return browserInfoBiz.loadLoginRankingInfo(value);
	}
}
