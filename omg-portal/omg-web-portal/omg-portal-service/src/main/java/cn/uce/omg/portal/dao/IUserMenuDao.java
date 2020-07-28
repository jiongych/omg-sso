package cn.uce.omg.portal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.portal.entity.PortalUserMenu;
/**
 * 
 * @Description:(IUserMenuDao)
 * @author Ji Yongchao
 * @date 2017年8月4日 上午10:03:09
 */
@Repository("fspUserMenuDao")
public interface IUserMenuDao extends IBaseDao<PortalUserMenu, Long>{
	
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
	 * @date 2017年8月4日 上午9:44:47
	 */
	List<Long> findUserMenuByEmpCodeAndOrgCode(PortalUserMenu userMenu);
	
	/**
	 * @Description:通过permissionId集合、empCode、orgCode删除该UserMenu
	 * @author Ji Yongchao
	 * @param ids：permissionId
	 * @param empCode
	 * @param orgCode
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	int deleteUserMenuByEmpAndOrgAndPerId(@Param("empCode") String empCode, @Param("orgCode") String orgCode, @Param("permissionIds") List<Long> permissionIds);
	
	/**
	 * @Description:empCode、orgCode删除该UserMenu
	 * @author Ji Yongchao
	 * @param empCode
	 * @param orgCode
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	int deleteUserMenuByEmpAndOrg(@Param("empCode") String empCode, @Param("orgCode") String orgCode);
	
}
