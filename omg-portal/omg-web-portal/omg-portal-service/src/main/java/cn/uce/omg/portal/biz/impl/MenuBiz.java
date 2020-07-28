package cn.uce.omg.portal.biz.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.uce.core.cache.CacheManager;
import cn.uce.core.cache.base.ICache;
import cn.uce.omg.portal.biz.IMenuBiz;
import cn.uce.omg.portal.entity.PortalUserMenu;
import cn.uce.omg.portal.service.PermissionService;
import cn.uce.omg.portal.service.UserMenuService;
import cn.uce.omg.portal.util.Constants;
import cn.uce.omg.portal.vo.PermissionVo;
import cn.uce.omg.portal.vo.PortalMenuTreeVo;
import cn.uce.web.common.base.CurrentUser;
import cn.uce.web.common.util.WebUtil;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Description:(fspUserMenuBiz)
 * @author Ji Yongchao
 * @date 2017年8月4日 上午9:53:34
 */
@Service("fspUserMenuBiz")
@Transactional(readOnly = true,propagation=Propagation.SUPPORTS)
public class MenuBiz implements IMenuBiz {

	@Resource
	private UserMenuService fspUserMenuService;
	
	@Resource
	private PermissionService fspPermissionService;
	
	/**
	 * (非 Javadoc) 
	 * <p>Title: saveUserRoleRel</p> 
	 * <p>Description: 批量保存常用菜单数据</p> 
	 * @author Ji Yongchao
	 * @param userMenuList
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int saveUserMenu(List<PortalUserMenu> userMenuList) {
		
		return fspUserMenuService.saveUserMenu(userMenuList);
	}
	
	/**
	 * (非 Javadoc) 
	 * <p>Title: addUserMenu</p> 
	 * <p>Description: 保存常用菜单数据</p> 
	 * @author Ji Yongchao
	 * @param userMenu
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int addUserMenu(PortalUserMenu userMenu){
		return fspUserMenuService.addUserMenu(userMenu);
	}

	/**
	 * (非 Javadoc) 
	 * <p>Title: findUserMenuByEmpCodeAndOrgCode</p> 
	 * <p>Description: 通过UserMenu的empCode和orgCode条件查询UserMenur的permissionId集合</p> 
	 * @author Ji Yongchao
	 * @param userMenu
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	@Override
	public List<Long> findUserMenuByEmpCodeAndOrgCode(PortalUserMenu userMenu) {
		
		return fspUserMenuService.findUserMenuByEmpCodeAndOrgCode(userMenu);
	}

	/**
	 * (非 Javadoc) 
	 * <p>Title: processByParam</p> 
	 * <p>Description: 通过permissionId集合、empCode、orgCode、sorts集合加工成List<UserMenu></p> 
	 * @author Ji Yongchao
	 * @param ids：permissionId
	 * @param empCode
	 * @param orgCode
	 * @param sorts
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	@Override
	public List<PortalUserMenu> processByParam(Long[] ids, String empCode, String orgCode, Long[] sorts) {
		List<PortalUserMenu> userMenuList = new ArrayList<>();
		if (null != ids && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				PortalUserMenu userMenu = new PortalUserMenu();
				userMenu.setPermissionId(ids[i]);
				userMenu.setEmpCode(empCode);
				userMenu.setOrgCode(orgCode);
				userMenu.setSort(sorts[i].intValue());
				userMenuList.add(userMenu);
			}
		}
		return userMenuList;
	}

	/**
	 * (非 Javadoc) 
	 * <p>Title: longArrToList</p> 
	 * <p>Description: 将Long[]转换为List<Long></p> 
	 * @author Ji Yongchao
	 * @param ids：permissionId
	 * @param empCode
	 * @param orgCode
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	public static List<Long> longArrToList(Long[] ids) {
		List<Long> longList = new ArrayList<>();
		if (null != ids && ids.length > 0) {
			for (Long id : ids) {
				longList.add(id);
			}
		} else {
			return null;
		}
		return longList;
	}
	
	/**
	 * (非 Javadoc) 
	 * <p>Title: deleteUserMenuByEmpAndOrgAndPerId</p> 
	 * <p>Description: 通过permissionId集合、empCode、orgCode删除该UserMenu</p> 
	 * @author Ji Yongchao
	 * @param ids：permissionId
	 * @param empCode
	 * @param orgCode
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int deleteUserMenuByEmpAndOrgAndPerId(String empCode, String orgCode, Long[] ids){
		
		return fspUserMenuService.deleteUserMenuByEmpAndOrgAndPerId(empCode, orgCode, longArrToList(ids));
	}

	/**
	 * (非 Javadoc) 
	 * <p>Title: deleteUserMenuByEmpAndOrg</p> 
	 * <p>Description: empCode、orgCode删除该UserMenu</p>
	 * @author Ji Yongchao
	 * @param empCode
	 * @param orgCode
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int deleteUserMenuByEmpAndOrg(String empCode, String orgCode) {
		
		return fspUserMenuService.deleteUserMenuByEmpAndOrg(empCode, orgCode);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: getExpandMenuTree</p> 
	* <p>Description: 从缓存重获取展开的菜单树</p> 
	* @param loginUser
	* @return 
	* @see cn.uce.omg.portal.biz.IMenuBiz#getExpandMenuTree(cn.uce.omg.portal.model.LoginUser)
	 */
	@Override
	public List<PortalMenuTreeVo> getExpandMenuTree(CurrentUser currentUser) {
		ICache<String, List<PortalMenuTreeVo>> menuTreeCache = CacheManager.getInstance().getCache("ExpandMenuTreeCache");
		return menuTreeCache.get(JSON.toJSONString(currentUser));
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: getMenuTree</p> 
	* <p>Description: 获取菜单树</p> 
	* @param currentUser
	* @return 
	* @see cn.uce.omg.portal.biz.IMenuBiz#getMenuTree(cn.uce.portal.common.base.CurrentUser)
	 */
	@Override
	public List<PortalMenuTreeVo> getMenuTree(CurrentUser currentUser) {
		ICache<String, List<PortalMenuTreeVo>> menuTreeCache = CacheManager.getInstance().getCache("MenuTreeCache");
		return menuTreeCache.get(JSON.toJSONString(currentUser));
	}
	
	/**
	 * @Description: (findMenuTree) 
	 * @param loginUser
	 * @return
	 * @author XJ
	 * @date 2018年3月26日 下午3:16:39
	 */
	public List<PortalMenuTreeVo> findExpandMenuTree(CurrentUser currentUser) {
		List<PermissionVo> permissionList = fspPermissionService.findPermissionByUser(currentUser.getEmpCode(),Constants.PERMISSION_CONTROL_TYPE_MENU);
		List<PortalMenuTreeVo> menuTree = new ArrayList<PortalMenuTreeVo>();
		Map<Long,PortalMenuTreeVo> idNodeRef = new HashMap<Long,PortalMenuTreeVo>();
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
					
				}
				idNodeRef.put(treeNodeVo.getId(), treeNodeVo);
			}
			for(PermissionVo permissionVo : permissionList){
				if(permissionVo.getParentPermission() != null){
					if(idNodeRef.get(permissionVo.getParentPermission()).getChildren() ==null){
						idNodeRef.get(permissionVo.getParentPermission()).setChildren(new ArrayList<PortalMenuTreeVo>());
					}
					idNodeRef.get(permissionVo.getParentPermission()).getChildren().add(idNodeRef.get(permissionVo.getId()));
				}else{
					menuTree.add(idNodeRef.get(permissionVo.getId()));
				}
			}
		}
		return menuTree;
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findMenuTree</p> 
	* <p>Description: 查询菜单树</p> 
	* @param currentUser
	* @return 
	* @see cn.uce.omg.portal.biz.IMenuBiz#findMenuTree(cn.uce.portal.common.base.CurrentUser)
	 */
	public List<PortalMenuTreeVo> findMenuTree(CurrentUser currentUser) {
		List<PermissionVo> permissionList = fspPermissionService.findPermissionByUser(currentUser.getEmpCode(),Constants.PERMISSION_CONTROL_TYPE_MENU);
		List<PortalMenuTreeVo> menuTree = new ArrayList<PortalMenuTreeVo>();
		if (permissionList != null && !permissionList.isEmpty()) {
			for (PermissionVo permission : permissionList) {
				PortalMenuTreeVo treeNodeVo = new PortalMenuTreeVo();
				//add by xj
				treeNodeVo.setSystemCode(WebUtil.getCurrentUser().getSystemCode());
				//add by xj
				treeNodeVo.setId(permission.getId());
				treeNodeVo.setText(permission.getPermissionName());
				treeNodeVo.setPermissionCode(permission.getPermissionCode());
				treeNodeVo.setPermissionIcon(permission.getPermissionIcon());
				
				if(permission.getPermissionUrl()==null || "".equals(permission.getPermissionUrl())){
					treeNodeVo.setAttributes("");
				}else{
					treeNodeVo.setAttributes((permission.getSysUrl()==null||"".equals(permission.getSysUrl())) ? permission.getPermissionUrl():permission.getSysUrl()+permission.getPermissionUrl());
				}
				if(permission.getParentPermission() == null){
					
				}
				menuTree.add(treeNodeVo);
			}
		}
		return menuTree;
	}

}
