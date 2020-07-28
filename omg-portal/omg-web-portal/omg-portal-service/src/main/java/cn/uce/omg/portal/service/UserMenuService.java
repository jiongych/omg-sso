package cn.uce.omg.portal.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.uce.omg.portal.dao.IUserMenuDao;
import cn.uce.omg.portal.entity.PortalUserMenu;

/**
 * 
 * @Description:(UserMenuService)
 * @author Ji Yongchao
 * @date 2017年8月4日 上午10:03:54
 */
@Service("fspUserMenuService")
public class UserMenuService {

	@Resource
	private IUserMenuDao fspUserMenuDao;

	/**
	 * @Description:批量保存常用菜单数据
	 * @author Ji Yongchao
	 * @param userMenuList
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	public int saveUserMenu(List<PortalUserMenu> userMenuList) {
		return fspUserMenuDao.insert(userMenuList);
	}

	/**
	 * @Description:保存常用菜单数据
	 * @author Ji Yongchao
	 * @param userMenu
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	public int addUserMenu(PortalUserMenu userMenu) {
		return fspUserMenuDao.addUserMenu(userMenu);
	}

	/**
	 * @Description:通过UserMenu的empCode和orgCode条件查询UserMenur的permissionId集合
	 * @author Ji Yongchao
	 * @param userMenu
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	public List<Long> findUserMenuByEmpCodeAndOrgCode(PortalUserMenu userMenu) {
		return fspUserMenuDao.findUserMenuByEmpCodeAndOrgCode(userMenu);
	}

	/**
	 * @Description:通过permissionId集合、empCode、orgCode删除该UserMenu
	 * @author Ji Yongchao
	 * @param ids：permissionId
	 * @param empCode
	 * @param orgCode
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	public int deleteUserMenuByEmpAndOrgAndPerId(String empCode, String orgCode, List<Long> permissionIds) {
		return fspUserMenuDao.deleteUserMenuByEmpAndOrgAndPerId(empCode, orgCode, permissionIds);
	}

	/**
	 * @Description:empCode、orgCode删除该UserMenu
	 * @author Ji Yongchao
	 * @param empCode
	 * @param orgCode
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	public int deleteUserMenuByEmpAndOrg(String empCode, String orgCode) {
		return fspUserMenuDao.deleteUserMenuByEmpAndOrg(empCode, orgCode);
	}
}
