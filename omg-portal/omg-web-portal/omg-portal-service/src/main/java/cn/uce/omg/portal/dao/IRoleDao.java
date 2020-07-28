package cn.uce.omg.portal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.portal.entity.Role;
import cn.uce.omg.portal.vo.PortalDataPushVo;
import cn.uce.omg.portal.vo.RoleVo;

/**
 * @Description: (IRoleDao) 
 * @author XJ
 * @date 2017年7月29日 下午4:23:13
 */
@Repository("fspRoleDao")
public interface IRoleDao extends IBaseDao<Role, Long> {

	/**
	 * @Description: (分页查询角色) 
	 * @param roleVo
	 * @param page
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:23:38
	 */
	List<RoleVo> findRoleByPage(@Param("roleVo")RoleVo roleVo, @Param("startIndex")Integer startIndex,@Param("pageSize")Integer pageSize);
	Integer findRoleCountByPage(@Param("roleVo")RoleVo roleVo, @Param("startIndex")Integer startIndex,@Param("pageSize")Integer pageSize);
	
	/**
	 * @Description: (查询用户已分配的角色) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:24:02
	 */
	List<RoleVo> findRoleByUser(@Param("empCode")String empCode);

	/**
	 * @Description: (查询当前用户可分配但还未分配的角色) 
	 * @param roleVo
	 * @return
	 * @author XJ
	 * @date 2017年6月22日 下午2:56:22
	 */
	List<RoleVo> findNotRoleByUser(@Param("empCode")String empCode, @Param("baseOrgCode")String baseOrgCode, 
			@Param("roleLevel")Integer roleLevel, @Param("cmsOrgType") Integer cmsOrgType);
	
	/**
	 * @Description: (根据角色码查询角色) 
	 * @param roleCode
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:24:29
	 */
	RoleVo findRoleByCode(String roleCode);
	
	/**
	 * @Description: (根据角色码删除角色) 
	 * @param roleCode
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:25:14
	 */
	int deleteByRoleCode(String roleCode);
	
	/**
	 * @Description: (根据角色码更新角色) 
	 * @param roleVo
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:25:34
	 */
	int updateRoleByCode(RoleVo roleVo);
	
	/**
	 * @Description: (查询时间范围内变更的角色) 
	 * @param userVo
	 * @return
	 * @author liyi
	 * @date 2018年04月19日 上午9:47:52
	 */
	List<String> findRoleByDate(PortalDataPushVo dataPushVo);
	
	/**
	 * 同步重推使用
	 * @param roleCode
	 * @return
	 */
	RoleVo findRoleSyncByCode(String roleCode);
} 
