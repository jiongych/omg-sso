package cn.uce.omg.portal.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.uce.web.common.base.BaseController;


/**
 * @Description: (ErrorController) 
 * @author XJ
 * @date 2017年8月2日 下午3:59:01
 */
@Controller
@RequestMapping("/error")
public class ErrorController extends BaseController {
	
	/**
	 * @Description: (错误页面跳转) 
	 * @param request
	 * @return
	 * @throws Exception
	 * @author XJ
	 * @date 2017年8月2日 下午3:59:14
	 */
	@RequestMapping(value = "/toErrorDetail" , method = RequestMethod.GET)
	public String toErrorDetail()throws Exception{
		return "common/errorDetail";
	}
}
