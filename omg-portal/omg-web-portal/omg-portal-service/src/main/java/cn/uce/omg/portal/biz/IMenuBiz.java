package cn.uce.omg.portal.biz;

import java.util.List;

import cn.uce.omg.portal.entity.PortalUserMenu;
import cn.uce.omg.portal.vo.PortalMenuTreeVo;
import cn.uce.web.common.base.CurrentUser;

/**
 * 
 * @Description:(IUserMenuBiz)
 * @author Ji Yongchao
 * @date 2017年8月4日 上午9:44:47
 */
public interface IMenuBiz {
	
	/**
	 * @Description: (getExpandMenuTree) 
	 * @param loginUser
	 * @return
	 * @author XJ
	 * @date 2018年3月26日 下午3:00:42
	 */
	List<PortalMenuTreeVo> getExpandMenuTree(CurrentUser currentUser);
	
	/**
	 * @Description: (getMenuTree) 
	 * @param currentUser
	 * @return
	 * @author XJ
	 * @date 2018年4月26日 下午1:58:06
	 */
	List<PortalMenuTreeVo> getMenuTree(CurrentUser currentUser);
	
	/**
	 * @Description:(findExpandMenuTree) 
	 * @param loginUser
	 * @return
	 * @author XJ
	 * @date 2018年3月26日 下午3:27:29
	 */
	List<PortalMenuTreeVo> findExpandMenuTree(CurrentUser currentUser);
	
	/**
	 * @Description: (findMenuTree) 
	 * @param currentUser
	 * @return
	 * @author XJ
	 * @date 2018年4月26日 下午1:58:31
	 */
	List<PortalMenuTreeVo> findMenuTree(CurrentUser currentUser);
	/**
	 * @Description:批量保存常用菜单数据
	 * @author Ji Yongchao
	 * @param userMenuList
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	int saveUserMenu(List<PortalUserMenu> userMenuList);
	
	/**
	 * @Description:保存常用菜单数据
	 * @author Ji Yongchao
	 * @param userMenu
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	int addUserMenu(PortalUserMenu userMenu);
	
	/**
	 * @Description:通过UserMenu的empCode和orgCode条件查询UserMenur的permissionId集合
	 * @author Ji Yongchao
	 * @param userMenu
	 * @return
	 * 2017年8月4日 上午9:44:47
	 */
	List<Long> findUserMenuByEmpCodeAndOrgCode(PortalUserMenu userMenu);
	
	/**
	 * @Description:通过permissionId集合、empCode、orgCode、sorts集合加工成List<UserMenu>
	 * @author Ji Yongchao
	 * @param ids：permissionId
	 * @param empCode
	 * @param orgCode
	 * @param sorts
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	List<PortalUserMenu> processByParam(Long[] ids, String empCode, String orgCode, Long[] sorts);
	
	/**
	 * @Description:通过permissionId集合、empCode、orgCode删除该UserMenu
	 * @author Ji Yongchao
	 * @param ids：permissionId
	 * @param empCode
	 * @param orgCode
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	int deleteUserMenuByEmpAndOrgAndPerId(String empCode, String orgCode, Long[] ids);
	
	/**
	 * @Description:empCode、orgCode删除该UserMenu
	 * @author Ji Yongchao
	 * @param empCode
	 * @param orgCode
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	int deleteUserMenuByEmpAndOrg(String empCode, String orgCode);
}
