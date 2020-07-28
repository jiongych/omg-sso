package cn.uce.omg.portal.control;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.biz.IPortalHomePageInfoBiz;
import cn.uce.omg.portal.vo.PortalHomePageInfoVo;
import cn.uce.web.common.base.BaseController;
import cn.uce.web.common.i18n.Resources;
import cn.uce.web.common.util.WebUtil;

/**
 * 各系统首页跳转信息控制类
 *<pre>
 * PortalHomePageInfoController
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年4月2日下午2:07:15
 */
@Controller
@RequestMapping("/sysHome")
public class PortalHomePageInfoController extends BaseController {

	@Resource
	private IPortalHomePageInfoBiz fspPortalHomePageInfoBiz;
	
	@RequestMapping(value ="/forward")
	public String forward() throws Exception {
		return "portal/sysHome";
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 分页查询各系统首页跳转信息
	 * </pre>
	 * @param portalHomePageInfoVo
	 * @param page
	 * @return
	 * @return Map<String,Object>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年4月2日下午2:11:21
	 */
	@RequestMapping(value ="/findHomeInfoByPage")
	@ResponseBody
	public Map<String, Object> findHomeInfoByPage(PortalHomePageInfoVo portalHomePageInfoVo, Page page) {
		Pagination<PortalHomePageInfoVo> paginations = fspPortalHomePageInfoBiz.findHomeInfoByPage(portalHomePageInfoVo, page);
		return returnSuccess(paginations);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 保存各系统首页跳转信息
	 * </pre>
	 * @param portalHomePageInfo
	 * @return
	 * @return Map<String,Object>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年4月2日下午2:13:46
	 */
	@RequestMapping(value="insertHomePageInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertHomePageInfo(PortalHomePageInfoVo portalHomePageInfoVo) {
		portalHomePageInfoVo.setCreateEmp(WebUtil.getCurrentUser().getEmpCode());
		portalHomePageInfoVo.setCreateOrg(WebUtil.getCurrentUser().getCmsOrgId());
		portalHomePageInfoVo.setUpdateEmp(WebUtil.getCurrentUser().getEmpCode());
		portalHomePageInfoVo.setUpdateOrg(WebUtil.getCurrentUser().getCmsOrgId());
		portalHomePageInfoVo.setUpdateTime(new Date());
		int result = fspPortalHomePageInfoBiz.insertHomePageInfo(portalHomePageInfoVo);
		if (result > 0) {
			return returnSuccess(Resources.getMessage("common.save.success"));
	    } else {
	    	return returnError(Resources.getMessage("common.save.fail"));
	    }
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 更新各系统首页跳转信息
	 * </pre>
	 * @param portalHomePageInfo
	 * @return
	 * @return Map<String,Object>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年4月2日下午2:14:48
	 */
	@RequestMapping(value="updateHomePageInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHomePageInfo(PortalHomePageInfoVo portalHomePageInfoVo) {
		portalHomePageInfoVo.setUpdateEmp(WebUtil.getCurrentUser().getEmpCode());
		portalHomePageInfoVo.setUpdateOrg(WebUtil.getCurrentUser().getCmsOrgId());
		portalHomePageInfoVo.setUpdateTime(new Date());
		int result = fspPortalHomePageInfoBiz.updateHomePageInfo(portalHomePageInfoVo);
		if (result > 0) {
			return returnSuccess(Resources.getMessage("common.update.success"));
	    } else {
	    	return returnError(Resources.getMessage("common.update.fail"));
	    }
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 删除各系统首页跳转信息
	 * </pre>
	 * @param id
	 * @return
	 * @return Map<String,Object>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年4月2日下午2:16:06
	 */
	@RequestMapping(value="deleteHomePageInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHomePageInfo(Long id) {
		int result = fspPortalHomePageInfoBiz.deleteHomePageInfo(id);
		if (result > 0) {
			return returnSuccess(Resources.getMessage("common.delete.success"));
	    } else {
	    	return returnError(Resources.getMessage("common.delete.fail"));
	    }
	}
}
