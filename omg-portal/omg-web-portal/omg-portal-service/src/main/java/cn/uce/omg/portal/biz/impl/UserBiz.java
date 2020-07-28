package cn.uce.omg.portal.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.core.cache.CacheManager;
import cn.uce.core.cache.base.ICache;
import cn.uce.omg.portal.biz.IUserBiz;
import cn.uce.omg.portal.entity.PortalUserOrg;
import cn.uce.omg.portal.entity.PortalUserOrgManager;
import cn.uce.omg.portal.service.PermissionService;
import cn.uce.omg.portal.service.UserService;
import cn.uce.omg.portal.vo.OrgVo;
import cn.uce.omg.portal.vo.PermissionVo;
import cn.uce.omg.portal.vo.PortalDataPushVo;
import cn.uce.omg.portal.vo.PortalMenuTreeVo;
import cn.uce.omg.portal.vo.PortalUserRoleRelVo;
import cn.uce.omg.portal.vo.UserSearchVo;
import cn.uce.omg.portal.vo.UserVo;
import cn.uce.web.common.base.CurrentUser;
import cn.uce.web.common.util.WebUtil;

import com.alibaba.fastjson.JSON;

/**
 * @Description: (UserBiz) 
 * @author XJ
 * @date 2017年7月29日 下午4:46:10
 */
@Service("userBiz")
@Transactional(readOnly = true,propagation=Propagation.SUPPORTS)
public class UserBiz implements IUserBiz {

	@Resource
	private UserService userService;
	
	@Resource
	private PermissionService fspPermissionService;
	
	/**
	 * (非 Javadoc) 
	* <p>Title: saveUserRoleRel</p> 
	* <p>Description: 保存用户角色关系</p> 
	* @param userRoleRel
	* @param roleCodeListStr
	* @return
	* @throws Exception 
	* @see cn.uce.web.authorg.biz.IUserBiz#saveUserRoleRel(cn.uce.web.authorg.entity.UserRoleRel, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int saveUserRoleRel(PortalUserRoleRelVo userRoleRelVo) {
		return userService.saveUserRoleRel(userRoleRelVo);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findEmpUser</p> 
	* <p>Description: 分页查询用户员工信息</p> 
	* @param empUserVo
	* @param page
	* @return 
	* @see cn.uce.web.authorg.biz.IUserBiz#findEmpUser(cn.uce.PortalEmpUserVo.authorg.vo.EmpUserVo, cn.uce.base.page.Page)
	 */
	@Override
	public Pagination<UserVo> findUserByPage(UserVo userVo, Integer currentPage , Integer pageSize) {
		Integer startIndex = (currentPage - 1)*pageSize;
		
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		page.setTotal(userService.findUserCountByPage(userVo, startIndex, pageSize));
		
		Pagination<UserVo> pagination = new Pagination<UserVo>();
		pagination.setPage(page);
		pagination.setData(userService.findUserByPage(userVo, startIndex, pageSize));
		
		return pagination;
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findEmpUser</p> 
	* <p>Description: 分页查询用户员工信息</p> 
	* @param empUserVo
	* @param page
	* @return 
	* @see cn.uce.web.authorg.biz.IUserBiz#findEmpUser(cn.uce.PortalEmpUserVo.authorg.vo.EmpUserVo, cn.uce.base.page.Page)
	 */
	@Override
	public Pagination<UserVo> findUserByPageNew(UserSearchVo userVo, Page page) {
		
		return userService.findUserByPageNew(userVo, page);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: getMenuTree</p> 
	* <p>Description: 查询菜单树</p> 
	* @param roles
	* @return
	* @throws Exception 
	* @see cn.uce.web.authorg.biz.IUserBiz#getMenuTree(java.util.List)
	 */
	@Override
	public List<PortalMenuTreeVo> getMenuTree(List<String> roles){
		ICache<String, List<PortalMenuTreeVo>> menuTreeCache = CacheManager.getInstance().getCache("MenuTreeCache");
		return menuTreeCache.get(JSON.toJSONString(roles));
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findMenuTree</p> 
	* <p>Description: 查询菜单树</p> 
	* @param roles
	* @return 
	* @see cn.uce.web.authorg.biz.IUserBiz#findMenuTree(java.util.List)
	 */
	public List<PortalMenuTreeVo> findMenuTree(List<String> roles) {
		List<PermissionVo> permissionList = fspPermissionService.findUserAllPermission(roles);
		List<PortalMenuTreeVo> menuTreeList = new ArrayList<PortalMenuTreeVo>();
		List<PortalMenuTreeVo> treeNodeList = new ArrayList<PortalMenuTreeVo>();
		Map<Long,List<PortalMenuTreeVo>> ref = new HashMap<Long,List<PortalMenuTreeVo>>();
		if (permissionList != null && !permissionList.isEmpty()) {
			for (PermissionVo permission : permissionList) {
				PortalMenuTreeVo treeNodeVo = new PortalMenuTreeVo();
				treeNodeVo.setId(permission.getId());
				treeNodeVo.setText(permission.getPermissionName());
				treeNodeVo.setPermissionIcon(permission.getPermissionIcon());
				
				if(permission.getPermissionUrl()==null || "".equals(permission.getPermissionUrl())){
					treeNodeVo.setAttributes("");
				}else{
					treeNodeVo.setAttributes((permission.getSysUrl()==null||"".equals(permission.getSysUrl())) ? permission.getPermissionUrl():permission.getSysUrl()+permission.getPermissionUrl());
				}
				if(permission.getParentPermission() == null){
					menuTreeList.add(treeNodeVo);
				}
				treeNodeList.add(treeNodeVo);
				if(ref.containsKey(permission.getParentPermission())){
					ref.get(permission.getParentPermission()).add(treeNodeVo);
				}else{
					List<PortalMenuTreeVo> childNodes = new ArrayList<PortalMenuTreeVo>();
					childNodes.add(treeNodeVo);
					ref.put(permission.getParentPermission(), childNodes);
				}
			}
			for(PortalMenuTreeVo menuTreeVo : treeNodeList){
				menuTreeVo.setChildren(ref.get(menuTreeVo.getId()));
			}
		}
		return menuTreeList;
	}
	
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findUserAllPermissionCode</p> 
	* <p>Description: 查询用户所有权限码</p> 
	* @param roles
	* @return 
	* @see cn.uce.web.authorg.biz.IUserBiz#findUserAllPermissionCode(java.util.List)
	 */
	@Override
	public List<String> findUserAllPermissionCode(List<String> roles) {
		return fspPermissionService.findUserAllPermissionCode(roles);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findUserBindOrg</p> 
	* <p>Description:查询用户绑定的机构 </p> 
	* @param empId
	* @return 
	* @see cn.uce.web.authorg.biz.IUserBiz#findUserBindOrg(java.lang.String)
	 */
	@Override
	public List<OrgVo> findUserBindOrg(String empId) {
		return userService.findUserBindOrg(empId);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findUserByEmpCode</p> 
	* <p>Description: 根据empCode查询用户</p> 
	* @param empCode
	* @return 
	* @see cn.uce.web.authorg.biz.IUserBiz#findUserByEmpCode(java.lang.String)
	 */
	@Override
	public UserVo findUserByEmpCode(String empCode) {
		return userService.findUserByEmpCode(empCode);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findRoleByUserName</p> 
	* <p>Description: 根据用户名查询角色</p> 
	* @param username
	* @return 
	* @see cn.uce.web.authorg.biz.IUserBiz#findRoleByUserName(java.lang.String)
	 */
	@Override
	public List<String> findRoleByUserName(String username) {
		return userService.findRoleByUserName(username);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: updateUserOrg</p> 
	* <p>Description: 更新用户机构绑定关系</p> 
	* @param userVo
	* @return 
	* @see cn.uce.web.authorg.biz.IUserBiz#updateUserOrg(cn.uce.UserVo.authorg.vo.UserVo)
	 */
	@Override
	public int updateUserOrg(UserVo userVo,String zTreeOrgCode) {
		//删除旧的关系
		userService.deleteUserOrgByEmpCode(userVo.getEmpCode());
		if(zTreeOrgCode != null){
			//插入新的关系
			CurrentUser userInfo = WebUtil.getCurrentUser();
			PortalUserOrg userOrg = new PortalUserOrg();
			userOrg.setEmpCode(userVo.getEmpCode());
			userOrg.setOrgCode(zTreeOrgCode);
			userOrg.setCreateEmp(userInfo.getEmpCode());
			userOrg.setCreateOrg(userInfo.getCmsOrgId());
			userOrg.setCreateTime(new Date());
			userOrg.setUpdateEmp(userInfo.getEmpCode());
			userOrg.setUpdateOrg(userInfo.getCmsOrgId());
			userOrg.setUpdateTime(new Date());
			return userService.addUserOrg(userOrg);
		}
		return 0;
	}
	
	
	/**
	 * (非 Javadoc) 
	* <p>Title: updateUserOrg</p> 
	* <p>Description: 更新用户机构绑定关系</p> 
	* @param userVo
	* @return 
	* @see cn.uce.web.authorg.biz.IUserBiz#updateUserOrg(cn.uce.UserVo.authorg.vo.UserVo)
	 */
	@Override
	public int updateUserOrgManager(UserVo userVo,String zTreeOrgCode) {
		//删除旧的关系
		userService.deleteUserOrgManagerByEmpCode(userVo.getEmpCode());
		if(zTreeOrgCode != null){
			//插入新的关系
			CurrentUser userInfo = WebUtil.getCurrentUser();
			PortalUserOrgManager userOrg = new PortalUserOrgManager();
			userOrg.setEmpCode(userVo.getEmpCode());
			userOrg.setOrgCode(zTreeOrgCode);
			userOrg.setCreateEmp(userInfo.getEmpCode());
			userOrg.setCreateOrg(userInfo.getCmsOrgId());
			userOrg.setCreateTime(new Date());
			userOrg.setUpdateEmp(userInfo.getEmpCode());
			userOrg.setUpdateOrg(userInfo.getCmsOrgId());
			userOrg.setUpdateTime(new Date());
			return userService.addUserOrgManager(userOrg);
		}
		return 0;
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findDataManagerByEmpCode</p> 
	* <p>Description: 创造找</p> 
	* @param empCode
	* @return 
	* @see cn.uce.web.authorg.biz.IUserBiz#findDataManagerByEmpCode(java.lang.String)
	 */
	@Override
	public String findDataManagerByEmpCode(String empCode) {
		return userService.findDataManagerByEmpCode(empCode);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findOrgCodeByEmpCode</p> 
	* <p>Description: 删除用户绑定的机构码</p> 
	* @param empCode
	* @return 
	* @see cn.uce.web.authorg.biz.IUserBiz#findOrgCodeByEmpCode(java.lang.String)
	 */
	@Override
	public String findDataAuthByEmpCode(String empCode) {
		return userService.findDataAuthByEmpCode(empCode);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findCurrentUser</p> 
	* <p>Description: 查询当前登陆用户</p> 
	* @return 
	* @see cn.uce.web.authorg.biz.IUserBiz#findCurrentUser()
	 */
	@Override
	public CurrentUser findCurrentUser(String empCode) {
		return userService.findCurrentUser(empCode);
	}

	/**
	 * @Description: 查询时间范围内更新的用户
	 * @return
	 * @author liyi
	 * @date 2018年5月31日 下午7:16:54
	 */
	@Override
	public List<String> findUserByDate(PortalDataPushVo dataPushVo) {
		return userService.findUserByDate(dataPushVo);
	}
	
}
