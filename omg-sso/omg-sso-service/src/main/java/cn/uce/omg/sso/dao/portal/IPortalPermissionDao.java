package cn.uce.omg.sso.dao.portal;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.sso.entity.portal.PortalPermission;
import cn.uce.omg.sso.vo.portal.PortalPermissionVo;
/**
 * @Description: (IPortalPermissionDao) 
 * @author XJ
 * @date 2018年3月28日 下午8:37:27
 */
public interface IPortalPermissionDao extends IBaseDao<PortalPermission, Long>{
	
	/**
	 * @Description: (findPortalPermissionByUser) 
	 * @param empCode
	 * @param controlType
	 * @return
	 * @author XJ
	 * @date 2018年3月28日 下午8:37:38
	 */
	List<PortalPermissionVo> findPortalPermissionByUser(@Param("empCode")String empCode,@Param("controlType")Integer controlType);

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 查询当前登录人的所有权限
	 * </pre>
	 * @param empCode
	 * @return
	 * @return List<String>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年12月24日下午1:13:03
	 */
	List<String> findPortalRolePermissionCodeByUser(String empCode);
	/**
	 * 根据permissionCode和systemCode查询菜单
	 * @param permissionCode
	 * @param systemCode
	 * @return
	 */
	PortalPermissionVo findPermissionAndSystemCode(@Param("permissionCode")String permissionCode, @Param("systemCode")String systemCode);
	
	/**
	 * 根据empCode和systemCode校验菜单权限
	 * @param empCode
	 * @param systemCode
	 * @return
	 */
	public int menuPermissionCheck(@Param("empCode")String empCode, @Param("systemCode")String systemCode);
	
	List<PortalPermissionVo> findPermissionByIds(List<Long> ids);
}
