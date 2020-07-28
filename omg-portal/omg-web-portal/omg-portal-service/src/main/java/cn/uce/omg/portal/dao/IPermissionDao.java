package cn.uce.omg.portal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.core.db.IBaseDao;
import cn.uce.omg.portal.entity.PortalPermission;
import cn.uce.omg.portal.vo.PermissionTreeNewVo;
import cn.uce.omg.portal.vo.PermissionTreeVo;
import cn.uce.omg.portal.vo.PermissionVo;
import cn.uce.omg.portal.vo.PortalDataPushVo;

/**
 * @Description: (IPermissionDao) 
 * @author XJ
 * @date 2017年7月29日 上午9:34:27
 */
@Repository("fspPermissionDao")
public interface IPermissionDao extends IBaseDao<PortalPermission,Long> {
	
	/**
	 * @Description: (分页查询权限) 
	 * @param permissionVo
	 * @param page
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午9:34:43
	 */
	Pagination<PermissionVo> findByCondition(PermissionVo permissionVo, Page page);
	
	/**
	 * @Description: (按条件查询权限) 
	 * @param permissionVo
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午9:35:04
	 */
	List<PermissionVo> findByCondition(PermissionVo permissionVo);
	
	
	/**
	 * @Description: (按条件查询权限) 
	 * @param permissionVo
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午9:35:04
	 */
	List<PermissionVo> findByConditionForTree(PermissionVo permissionVo);
	
	
	
	/**
	 * @Description: (按权限码查询权限) 
	 * @param permissionCode
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午9:35:22
	 */
    PermissionVo findByPermissionCode(String permissionCode);
    
    /**
     * @Description: (按权限码删除权限) 
     * @param permissionCode
     * @return
     * @author XJ
     * @date 2017年7月29日 上午9:35:41
     */
    int deleteByPermissionCode(@Param("permissionCode") String permissionCode, @Param("systemCode") String systemCode);
    
    /**
     * @Description: (查询用户所有权限) 
     * @param roles
     * @return
     * @throws Exception
     * @author XJ
     * @date 2017年7月29日 上午9:35:58
     */
    List<PermissionVo> findUserAllPermission(List<String> roles);
    
    /**
     * @Description: (查询用户所有权限码) 
     * @param roles
     * @return
     * @author XJ
     * @date 2017年7月29日 上午9:36:18
     */
    List<String> findUserAllPermissionCode(List<String> roles);
    
    /**
     * @Description: (按级别查询所有权限) 
     * @param userVo
     * @return
     * @author XJ
     * @date 2017年7月29日 上午9:36:36
     */
    List<PermissionTreeVo> findPermissionTreeByLevel(@Param("empCode")String empCode,@Param("roleLevel")Integer roleLevel);
    
    /**
     * @Description: (按ID更新权限级别) 
     * @param permissionVo
     * @return
     * @author XJ
     * @date 2017年7月29日 上午9:37:06
     */
    int updateLevelsById(PermissionVo permissionVo);
    
    /**
     * @Description: (findPermissionByUser) 
     * @param userVo
     * @return
     * @author XJ
     * @date 2018年3月26日 下午3:08:54
     */
    List<PermissionVo> findPermissionByUser(@Param("empCode")String empCode,@Param("controlType")Integer controlType);
    
    /**
	 * @Description: (根据角色码查询权限ID) 
	 * @param roleCode
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:24:47
	 */
	List<PermissionVo> findPermissionByRoleCode(String roleCode);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 新增权限，并返回插入的数据
	 * </pre>
	 * @param permissionVo
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年5月25日下午4:57:56
	 */
	int insert(PermissionVo permissionVo);

	/**
	 * @Description: (查询时间范围内更新的权限) 
	 * @param roleCode
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:05:02
	 */
	List<String> findPermissionCodeByDate(PortalDataPushVo dataPushVo);

	List<PermissionTreeNewVo> findPermissionTreeByLevelNew(@Param("empCode")String empCode,@Param("roleLevel")Integer roleLevel);
	
	/**
	 * 根据级别查询portal权限
	 * @param empCode
	 * @param roleLevel
	 * @return
	 * @author huangting
	 * @date 2018年8月21日 下午3:33:48
	 */
	List<PermissionTreeNewVo> findPortalMenuTreeByLevel(@Param("empCode")String empCode,@Param("roleLevel")Integer roleLevel);
	
	/**
	 * @Description: (根据角色码查询权限ID) 
	 * @param roleCode
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:24:47
	 */
	List<PermissionVo> findPortalMenuByRoleCode(String roleCode);
	
	/**
	 * 根据权限id查询权限编码
	 * @param id
	 * @return
	 * @author huangting
	 * @date 2018年8月21日 下午6:45:19
	 */
	List<PermissionVo> findParentPermissionById(List<Long> id);
	
	List<PermissionVo> findPermissionByIds(List<Long> ids);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据用户、权限code、systemCode查询该用户是否有此权限
	 * </pre>
	 * @param empCode
	 * @param permissionCode
	 * @param systemCode
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年3月20日上午10:54:42
	 */
	int findCountByUserAndPerCodeAndSysCode(@Param("empCode")String empCode, 
			@Param("permissionCode")String permissionCode, 
			@Param("systemCode")String systemCode);
}
