package cn.uce.omg.portal.control;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.uce.base.page.Page;
import cn.uce.omg.portal.biz.IOrgBiz;
import cn.uce.omg.portal.biz.IRoleBiz;
import cn.uce.omg.portal.biz.IUserBiz;
import cn.uce.omg.portal.vo.CmsOrgVo;
import cn.uce.omg.portal.vo.PortalUserRoleRelVo;
import cn.uce.omg.portal.vo.RoleVo;
import cn.uce.utils.StringUtil;
import cn.uce.web.common.base.BaseController;
import cn.uce.web.common.base.CurrentUser;
import cn.uce.web.common.i18n.Resources;
import cn.uce.web.common.util.WebUtil;

/**
 * @Description: (RoleController) 
 * @author XJ
 * @date 2017年7月29日 下午3:13:15
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
	@Resource
	private IRoleBiz roleBiz;
	@Resource
	private IOrgBiz orgBiz;
	@Resource
	private IUserBiz userBiz;
	
	/**
	 * @Description: (role.jsp) 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @author XJ
	 * @date 2017年7月29日 下午3:13:28
	 */
	@RequestMapping(value = "/forward")
	public String get()
			throws IOException {
		return "portal/role";
	}
	
	/**
	 * @Description: (分页查询角色信息) 
	 * @param roleVo
	 * @param page
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午3:14:35
	 */
	@RequestMapping(value = "/findRoleByPage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findRoleByPage(RoleVo roleVo,Integer page, Integer rows) {
		return returnSuccess(roleBiz.findRoleByPage(roleVo, page, rows));
	}
	
	/**
	 * @Description: (新增角色) 
	 * @param role
	 * @param zTreeOrgCode
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午3:14:55
	 */
	@RequestMapping(value="addRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addRole(RoleVo role,String zTreeOrgCode) {
		role.setRoleScope(zTreeOrgCode);
		role.setDeleteFlag(false);
		role.setCreateEmp(WebUtil.getCurrentUser().getEmpCode());
		role.setCreateTime(new Date());
		role.setCreateOrg(WebUtil.getCurrentUser().getCmsOrgId());
		role.setUpdateEmp(WebUtil.getCurrentUser().getEmpCode());
		role.setUpdateTime(new Date());
		role.setUpdateOrg(WebUtil.getCurrentUser().getCmsOrgId());
		int res = roleBiz.addRole(role);
		if (res > 0) {
			return returnSuccess(Resources.getMessage("common.save.success"));
		} else {
			return returnError(Resources.getMessage("common.save.fail"));
		}
	}
	
	/**
	 * @Description: (修改角色) 
	 * @param role
	 * @param zTreeOrgCode
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午3:15:08
	 */
	@RequestMapping(value="updateRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateRole(RoleVo role,String zTreeOrgCode){
		role.setRoleScope(zTreeOrgCode);
		role.setUpdateEmp(WebUtil.getCurrentUser().getEmpCode());
		role.setUpdateTime(new Date());
		role.setUpdateOrg(WebUtil.getCurrentUser().getCmsOrgId());
		role.setDeleteFlag(false);
		int res = roleBiz.updateRoleByRoleCode(role);
		if (res >= 0) {
			return returnSuccess(Resources.getMessage("common.save.success"));
		} else {
			return returnError(Resources.getMessage("common.save.fail"));
		}
	}
	
	/**
	 * @Description: (删除角色) 
	 * @param roleCode
	 * @return
	 * @throws Exception
	 * @author XJ
	 * @date 2017年7月29日 下午3:15:24
	 */
	@RequestMapping(value = "deleteRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteRole(String roleCode) throws Exception{
		int res = roleBiz.deleteRole(roleCode);
		if (res > 0) {
			return returnSuccess(Resources.getMessage("common.delete.success"));
		} else {
			return returnError(Resources.getMessage("common.delete.fail"));
		}
	}
	
	/**
	 * @Description: (查询用户已分配的角色) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2017年7月28日 上午9:47:31
	 */
	@RequestMapping(value = "findRoleByUser")
	@ResponseBody
	public List<RoleVo> findRoleByUser(String empCode) {
		return roleBiz.findRoleByUser(empCode);
	}
	
	/**
	 * @Description: (查询用户未分配的角色) 
	 * @param userVo
	 * @return
	 * @author XJ
	 * @date 2017年7月28日 上午9:47:52
	 */
	@RequestMapping(value = "findNotRoleByUser")
	@ResponseBody
	public List<RoleVo> findNotRoleByUser(String empCode,String baseOrgCode,Integer roleLevel, HttpServletRequest request) {
		Integer cmsOrgType = null;
		
		Cookie[] cookies = request.getCookies();
		Integer cmsOrgId = null;
		for (Cookie cookie : cookies) {
			if (null != cookie && null != cookie.getName() && "cmsOrgId".equals(cookie.getName())) {
				String value = cookie.getValue();
				if (null != value && value.length() > 0) {
					cmsOrgId = Integer.parseInt(value);
				}
				break;
			}
		}
		if (null != cmsOrgId) {
			CmsOrgVo cmsOrgVo = orgBiz.findCmsOrgByBaseOrgCode(null, cmsOrgId);
			if (null != cmsOrgVo) {
				cmsOrgType = cmsOrgVo.getOrgType();
			}
		}
		if (null == cmsOrgType) {
			CurrentUser useInfo = WebUtil.getCurrentUser();
			cmsOrgType = useInfo.getCmsOrgType();
		}
		
		return roleBiz.findNotRoleByUser(empCode, baseOrgCode, roleLevel, cmsOrgType);
	}
	
	/**
	 * @Description: (查询使用此角色的用户) 
	 * @param userVo
	 * @return
	 * @author liyi
	 * @date 2018年04月19日 上午9:47:52
	 */
	@RequestMapping(value = "findUserByRole")
	@ResponseBody
	public Map<String, Object> findUserByRole(String roleCode,int page,int rows) {
		if(StringUtil.isBlank(roleCode)) {
			return null;
		}
		Page p = new Page();
		p.setCurrentPage(page);
		p.setPageSize(rows);
		return returnSuccess(roleBiz.findUserByRole(roleCode,p));
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据用户删除单条角色并同步
	 * </pre>
	 * @param roleCode
	 * @param empCode
	 * @return
	 * @throws Exception
	 * @return Map<String,Object>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月23日下午5:10:45
	 */
	@RequestMapping(value = "deleteUserRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteUserRole(String roleCode, String empCode) throws Exception{
		List<String> roleCodeList = roleBiz.findRoleCodeByUser(empCode);
		roleCodeList.remove(roleCode);
		PortalUserRoleRelVo userRoleRelVo = new PortalUserRoleRelVo();
		userRoleRelVo.setEmpCode(empCode);
		userRoleRelVo.setCreateEmp(WebUtil.getCurrentUser().getEmpCode());
		userRoleRelVo.setCreateOrg(WebUtil.getCurrentUser().getCmsOrgId());
		userRoleRelVo.setCreateOrg(WebUtil.getCurrentUser().getCmsOrgId());
		userRoleRelVo.setCreateTime(new Date());
		userRoleRelVo.setUpdateTime(new Date());
		userRoleRelVo.setVersion(1);
		userRoleRelVo.setRoleCodeList(roleCodeList);
		int i = userBiz.saveUserRoleRel(userRoleRelVo);
		if (i >= 0) {
			return returnSuccess(Resources.getMessage("common.delete.success"));
		} else {
			return returnError(Resources.getMessage("common.delete.fail"));
		}
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 删除角色下所有的用户
	 * </pre>
	 * @param empCode
	 * @return
	 * @throws Exception
	 * @return Map<String,Object>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月23日下午5:12:34
	 */
	@RequestMapping(value = "deleteAllUserRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAllUserRole(String roleCode) throws Exception{
		List<String> empCodeList = roleBiz.findUserListByRole(roleCode);
		if (null != empCodeList) {
			for (String empCode : empCodeList) {
				deleteUserRole(roleCode, empCode);
			}
		}
		return returnSuccess(Resources.getMessage("common.delete.success"));
	}

}
