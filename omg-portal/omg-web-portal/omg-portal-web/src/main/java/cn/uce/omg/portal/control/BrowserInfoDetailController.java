package cn.uce.omg.portal.control;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.biz.IBrowserInfoBiz;
import cn.uce.omg.portal.vo.PortalBrowserInfoVo;
import cn.uce.web.common.base.BaseController;
/**
 * 
 * @Description:(UserMenuController)
 * @author liyi
 * @date 2018年3月27日 上午9:40:41
 */
@Controller
@RequestMapping("/browserInfo")
public class BrowserInfoDetailController  extends BaseController {

	@Autowired
    private IBrowserInfoBiz browserInfoBiz;
	
	/**
	 * @Description: (operatorLog.jsp) 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
     * @author liyi
     * @date 2018年3月27日 上午9:40:41
	 */
	@RequestMapping(value = "/forward")
	public String get()
			throws IOException {
		return "portal/browserInfoDetail";
	}
	
	/**
	 * @Description: (分页查询浏览器日志信息) 
	 * @param empUserVo
	 * @param page
	 * @return
     * @author liyi
     * @date 2018年3月27日 上午9:40:41
	 */
	@RequestMapping(value = "findAllBrowserInfo")
	@ResponseBody
	public Map<String,Object> findAllBrowserInfo(PortalBrowserInfoVo browserInfoVo, Page page) {
		Pagination<PortalBrowserInfoVo> pagination = browserInfoBiz.findAllBrowserInfo(browserInfoVo, page);
		return returnSuccess(pagination); 
	}
}
