package cn.uce.omg.login.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.uce.web.common.base.BaseController;


/**
 * @Description: (CheckActionController) 
 * @author XJ
 * @date 2017年11月13日 上午6:15:34
 */
@Controller
@RequestMapping(value = "/check")
public class CheckController extends BaseController{
	/**
	 * @return
	 */
	@RequestMapping(value = "/checkAction")
	public String checkAction(){
		return "login/checkAction";
	}
}
