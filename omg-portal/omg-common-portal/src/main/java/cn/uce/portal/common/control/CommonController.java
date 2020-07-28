package cn.uce.portal.common.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.uce.portal.common.base.BaseController;
import cn.uce.portal.common.base.CurrentUser;
import cn.uce.portal.common.biz.ICommonBiz;

/**
 * @Description: (CommonController) 
 * @author XJ
 * @date 2018年4月8日 下午2:08:27
 */
@Controller
@RequestMapping("common")
public class CommonController extends BaseController {
	
	@Autowired
	private ICommonBiz commonBiz;
	
	/**
	 * @Description: (findPermissionCodeByUser) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2018年4月8日 下午2:08:16
	 */
	@RequestMapping("getPermissionCodeByUser")
	@ResponseBody
	public List<String> getPermissionCodeByUser(String empCode){
		return commonBiz.getPermissionCodeByUser(empCode);
	}
	
	/**
	 * @Description: (findCurrentUser) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2018年4月8日 下午2:08:16
	 */
	@RequestMapping("findCurrentUser")
	@ResponseBody
	public CurrentUser findCurrentUser(String empCode){
		return commonBiz.findCurrentUser(empCode);
	}

}
