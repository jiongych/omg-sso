package cn.uce.omg.portal.control;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.biz.ILogBiz;
import cn.uce.omg.portal.vo.LogVo;
import cn.uce.web.common.base.BaseController;
/**
 * 
 * @Description:(UserMenuController)
 * @author liyi
 * @date 2018年3月27日 上午9:40:41
 */
@Controller
@RequestMapping("/log")
public class LogController  extends BaseController {

	@Resource
	private ILogBiz logBiz;
	
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
		return "portal/operatorLog";
	}
	
	/**
	 * @Description: (分页查询操作日志信息) 
	 * @param empUserVo
	 * @param page
	 * @return
     * @author liyi
     * @date 2018年3月27日 上午9:40:41
	 */
	@RequestMapping(value = "findAllLogs")
	@ResponseBody
	public Map<String,Object> findAllLogs(LogVo logVo, Page page) {
		Pagination<LogVo> pagination = null;
		if (logVo.getOperatorMenu() != null) {
			pagination = logBiz.findAllLogs(logVo, page);
		}
		return returnSuccess(pagination); 
	}
}
