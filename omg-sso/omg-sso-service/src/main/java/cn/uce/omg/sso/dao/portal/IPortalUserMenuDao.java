package cn.uce.omg.sso.dao.portal;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.sso.entity.portal.PortalUserMenu;

/**
 * 常用菜单设置DAO
 *<pre>
 * IPortalUserMenuDao
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年3月31日上午9:36:38
 */
public interface IPortalUserMenuDao extends IBaseDao<PortalUserMenu, Long>{

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

	/**
	 * 根据三个条件删除常用菜单
	 * @param empCode
	 * @param orgCode
	 * @param permissionId
	 * @return
	 */
	int deleteUserMenuByParam(@Param("empCode")String empCode, @Param("orgCode")String orgCode, @Param("permissionId")Long permissionId);
}
