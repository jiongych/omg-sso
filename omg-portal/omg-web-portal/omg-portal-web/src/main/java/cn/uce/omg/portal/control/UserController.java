package cn.uce.omg.portal.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.uce.base.page.Page;
import cn.uce.omg.portal.biz.IOrgBiz;
import cn.uce.omg.portal.biz.IOtherEmpRelationBiz;
import cn.uce.omg.portal.biz.IRoleBiz;
import cn.uce.omg.portal.biz.IUserBiz;
import cn.uce.omg.portal.vo.OrgVo;
import cn.uce.omg.portal.vo.OtherEmpRelationVo;
import cn.uce.omg.portal.vo.OtherOrgRelationVo;
import cn.uce.omg.portal.vo.PortalUserRoleRelVo;
import cn.uce.omg.portal.vo.UserSearchVo;
import cn.uce.omg.portal.vo.UserVo;
import cn.uce.utils.StringUtil;
import cn.uce.web.common.base.BaseController;
import cn.uce.web.common.base.CurrentUser;
import cn.uce.web.common.i18n.Resources;
import cn.uce.web.common.util.WebUtil;

/**
 * @Description: (UserController) 
 * @author XJ
 * @date 2017年7月28日 上午9:42:43
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	@Resource
	private IUserBiz userBiz;
	@Resource
	private IRoleBiz fspRoleBiz;
	@Resource
	private IOrgBiz fspOrgBiz;
	@Resource
	private IOtherEmpRelationBiz otherEmpRelationBiz;
	
	/**
	 * @Description: (user.jsp) 
	 * @return
	 * @author XJ
	 * @date 2017年7月28日 上午9:43:25
	 */
	@RequestMapping(value = "/forward")
	public String emp() {
		return "portal/user";
	}
	
	/**
	 * @Description: (分页查询用户信息) 
	 * @param empUserVo
	 * @param page
	 * @return
	 * @author XJ
	 * @date 2017年7月28日 上午9:47:00
	 */
	@RequestMapping(value = "findUserByPage")
	@ResponseBody
	public Map<String,Object> findUserByPage(UserVo userVo,OrgVo orgVo, Integer page, Integer rows) {
		userVo.setOrgVo(orgVo);
		return returnSuccess(userBiz.findUserByPage(userVo, page, rows)); 
	}
	
	/**
	 * @Description: (分页查询用户信息) 
	 * @param empUserVo
	 * @param page
	 * @return
	 * @author XJ
	 * @date 2017年7月28日 上午9:47:00
	 */
	@RequestMapping(value = "findUserByPageNew")
	@ResponseBody
	public Map<String,Object> findUserByPageNew(UserSearchVo userVo,Page page) {
		return returnSuccess(userBiz.findUserByPageNew(userVo,page)); 
	}
	
	/**
	 * @Description: (查询用户的机构绑定关系) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2018年3月29日 下午8:05:11
	 */
	@RequestMapping(value = "findOtherOrgRelationByEmpCode")
	@ResponseBody
	public List<OtherOrgRelationVo> findOtherOrgRelationByEmpCode(String empCode){
		OtherEmpRelationVo otherEmpRelationVo = otherEmpRelationBiz.findOtherEmpRelationByEmpCode(empCode);
		if(otherEmpRelationVo != null && otherEmpRelationVo.getOtherOrgRelationList() != null){
			return otherEmpRelationBiz.findOtherEmpRelationByEmpCode(empCode).getOtherOrgRelationList();
		}
		return new ArrayList<OtherOrgRelationVo>();
	}
	
	
	
	/**
	 * @Description: (保存用户角色关系) 
	 * @param empCode
	 * @param roleCodeListStr
	 * @return
	 * @author XJ
	 * @throws Exception 
	 * @date 2017年7月28日 上午9:50:32
	 */
	@RequestMapping(value = "saveUserRoleRel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveUserRoleRel(PortalUserRoleRelVo userRoleRelVo, String roleCodeListStr) throws Exception {
		userRoleRelVo.setCreateEmp(WebUtil.getCurrentUser().getEmpCode());
		//userRoleRelVo.setUpdateEmp(WebUtil.getCurrentUser().getEmpCode());
		userRoleRelVo.setCreateOrg(WebUtil.getCurrentUser().getCmsOrgId());
		userRoleRelVo.setCreateOrg(WebUtil.getCurrentUser().getCmsOrgId());
		userRoleRelVo.setCreateTime(new Date());
		userRoleRelVo.setUpdateTime(new Date());
		userRoleRelVo.setVersion(1);
		if(StringUtil.isNotBlank(roleCodeListStr)){
			List<String> roleCodeList = Arrays.asList(roleCodeListStr.split(","));
			userRoleRelVo.setRoleCodeList(roleCodeList);
		}
		int i = userBiz.saveUserRoleRel(userRoleRelVo);
		if(i>=0){
			return returnSuccess(Resources.getMessage("common.update.success"));
		}
		return returnError(Resources.getMessage("common.update.fail"));
	}
	
	/**
	 * @Description: (findUserBindOrg) 
	 * @param empId
	 * @return
	 * @author XJ
	 * @date 2017年7月26日 下午5:58:46
	 */
	@RequestMapping(value = "findUserBindOrg")
	@ResponseBody
	public List<OrgVo> findUserBindOrg(String empId,String empCode) {
		List<OrgVo> resultList = new ArrayList<OrgVo>();
		if(empId != null || !"".equals(empId)){
			//绑定关系存在，优先取绑定关系
			resultList =  userBiz.findUserBindOrg(empId);
			if(resultList != null && !resultList.isEmpty()){
				return resultList;
			}
			
		}
		//无绑定关系，不是白名单用户，返回空集合
		return resultList;
	}
	
	/**
	 * @Description: (更新用户组织绑定关系) 
	 * @param userVo
	 * @return
	 * @author XJ
	 * @date 2017年8月1日 下午4:10:51
	 */
	@RequestMapping(value = "/updateUserOrg")
	@ResponseBody
	public Map<String,Object> updateUserOrg(UserVo userVo,String zTreeOrgCode){
		//setEmpName、setEmpOrg、setCreateTime是为了数据同步使用
		CurrentUser userInfo = WebUtil.getCurrentUser();
		userVo.setEmpName(userInfo.getEmpCode());
		userVo.setEmpOrg(userInfo.getCmsOrgId());
		userVo.setCreateTime(new Date());
		
		int i = userBiz.updateUserOrg(userVo,zTreeOrgCode);
		if(i>=0){
			return returnSuccess(Resources.getMessage("common.update.success"));
		};
		return returnError(Resources.getMessage("common.update.fail"));
	}
	
	/**
	 * @Description: (findDataAuthByEmpCode) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2018年3月29日 下午10:12:39
	 */
	@RequestMapping(value = "/findDataAuthByEmpCode")
	@ResponseBody
	public String findDataAuthByEmpCode(String empCode){
		String dataAuth =  userBiz.findDataAuthByEmpCode(empCode);
		return dataAuth==null ? "" : dataAuth;
	}
	
	
	
	/**
	 * @Description: (更新用户机构范围) 
	 * @param userVo
	 * @return
	 * @author XJ
	 * @date 2017年8月1日 下午4:10:51
	 */
	@RequestMapping(value = "/updateUserOrgManager")
	@ResponseBody
	public Map<String,Object> updateUserOrgManager(UserVo userVo,String zTreeOrgCode){
		//setEmpName、setEmpOrg、setCreateTime是为了数据同步使用
		CurrentUser userInfo = WebUtil.getCurrentUser();
		userVo.setEmpName(userInfo.getEmpCode());
		userVo.setEmpOrg(userInfo.getCmsOrgId());
		userVo.setCreateTime(new Date());
		
		int i = userBiz.updateUserOrgManager(userVo,zTreeOrgCode);
		if(i>=0){
			return returnSuccess(Resources.getMessage("common.update.success"));
		};
		return returnError(Resources.getMessage("common.update.fail"));
	}
	
	/**
	 * @Description: (findDataManagerByEmpCode) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2018年3月29日 下午10:12:39
	 */
	@RequestMapping(value = "/findDataManagerByEmpCode")
	@ResponseBody
	public String findDataManagerByEmpCode(String empCode){
		String dataAuth =  userBiz.findDataManagerByEmpCode(empCode);
		return dataAuth==null ? "" : dataAuth;
	}
}
