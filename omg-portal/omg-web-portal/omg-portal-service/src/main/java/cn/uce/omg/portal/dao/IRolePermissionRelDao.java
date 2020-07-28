package cn.uce.omg.portal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.portal.entity.RolePermissionRel;
import cn.uce.omg.portal.vo.PortalRolePermissionRelVo;

@Repository("rolePermissionRelDao")
public interface IRolePermissionRelDao extends IBaseDao<RolePermissionRel,Long> {

	int deleteByRoleCode(String roleCode);
	
	void deleteByRoleCode(List<String> roleCode);
	
	int findCountByPermissionCode(@Param("permissionCode") String permissionCode, @Param("systemCode") String systemCode);
	
	List<RolePermissionRel> findRolePermissionRelByRoleCode(String roleCode);
	
	/**
	 * @Description: (deleteByRoleLevelAndPermissionCode) 
	 * @param roleLevelList
	 * @param permissionCodeList
	 * @return
	 * @author XJ
	 * @date 2018年4月10日 下午7:20:16
	 */
	int deleteByRoleLevelAndPermissionCode(@Param("roleLevelList")List<String> roleLevelList, @Param("permissionCodeList")List<String> permissionCodeList);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据角色编号查询权限编码
	 * </pre>
	 * @param roleCode
	 * @return
	 * @return List<String>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年8月13日下午5:39:44
	 */
	List<PortalRolePermissionRelVo> findRolePermissionCodeByRoleCode(String roleCode);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据角色编号查询权限编码集合
	 * </pre>
	 * @param roleCode
	 * @return
	 * @return List<String>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年8月13日下午6:47:34
	 */
	List<String> findRolePermissionSCodeByRoleCode(String roleCode);
}
