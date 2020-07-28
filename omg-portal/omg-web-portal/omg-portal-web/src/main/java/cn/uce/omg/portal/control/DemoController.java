package cn.uce.omg.portal.control;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.uce.web.common.base.BaseController;




/**
 * @Description: (DemoController) 
 * @author XJ
 * @date 2017年8月2日 下午4:30:57
 */
@Controller
@RequestMapping("/demo")
public class DemoController extends BaseController {
	
	/**
	 * @Description: (demo.jsp) 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @author XJ
	 * @date 2017年8月2日 下午4:31:14
	 */
	@RequestMapping(value = "/demo")
	public String biz()
			throws IOException {
		return "authorg/demo/demo";
	}
	
}
