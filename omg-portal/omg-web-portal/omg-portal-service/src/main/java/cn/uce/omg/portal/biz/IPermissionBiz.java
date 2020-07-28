package cn.uce.omg.portal.biz;

import java.util.List;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.vo.PermissionTreeNewVo;
import cn.uce.omg.portal.vo.PermissionTreeVo;
import cn.uce.omg.portal.vo.PermissionVo;
import cn.uce.omg.portal.vo.PortalDataPushVo;
import cn.uce.omg.portal.vo.PortalRoleLevelVo;

/**
 * @Description: (IPermissionBiz) 
 * @author XJ
 * @date 2017年7月29日 上午9:18:53
 */
public interface IPermissionBiz {
	
	/**
	 * @Description: (新增权限) 
	 * @param permission
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午8:59:08
	 */
	int addPermission(PermissionVo permission);
	
	/**
	 * @Description: (修改权限) 
	 * @param permission
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午8:59:23
	 */
	int updatePermission(PermissionVo permission);
	
	/**
	 * @Description: (删除权限) 
	 * @param permissionCode
	 * @param parentPermission
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午8:59:37
	 */
	int deletePermission(String permissionCode, Long parentPermission, String systemCode, Long id);
	
	
	/**
	 * @Description: (查询权限树) 
	 * @param permissionVo
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午8:59:52
	 */
	public List<PermissionTreeVo> findPermissionTree(PermissionVo permissionVo);
	
	/**
	 * @Description: (分页查询权限) 
	 * @param permissionVo
	 * @param page
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午9:00:14
	 */
	Pagination<PermissionVo> findPermissionByCondition(PermissionVo permissionVo, Page page);
	
	
	/**
	 * @Description: (条件查询权限) 
	 * @param permissionVo
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午9:00:34
	 */
	List<PermissionVo> findByCondition(PermissionVo permissionVo);
	
	/**
	 * @Description: (按权限码查询权限) 
	 * @param permissionCode
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午9:00:54
	 */
	PermissionVo findByPermissionCode(String permissionCode);
	
	/**
	 * @Description: (查询父级权限的权限级别) 
	 * @param id
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午9:01:12
	 */
	List<PortalRoleLevelVo> findParentPermissionLevels(Long id);
	
	/**
	 * @Description: (按级别查询权限树) 
	 * @param userVo
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午9:01:31
	 */
	List<PermissionTreeVo> findPermissionTreeByLevel(Integer roleLevel);
	
	/**
	 * @Description: (findPermissionCodeByUser) 
	 * @param empCode
	 * @param controlType
	 * @return
	 * @author XJ
	 * @date 2018年3月29日 上午9:06:12
	 */
	List<String> findPermissionCodeByUser(String empCode,Integer controlType);
	
	/**
	 * @Description: (查询角色的权限) 
	 * @param roleCode
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:05:02
	 */
	List<PermissionVo> findPermissionByRoleCode(String roleCode);

	/**
	 * @Description: (查询时间范围内更新的权限) 
	 * @param roleCode
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:05:02
	 */
	List<String> findPermissionCodeByDate(PortalDataPushVo dataPushVo);

	List<PermissionTreeNewVo> findPermissionTreeByLevelNew(Integer roleLevel);
	
	/**
	 * 根据角色编码查询portal编码
	 * @param roleCode
	 * @return
	 * @author huangting
	 * @date 2018年8月21日 下午3:32:03
	 */
	List<PermissionVo> findPortalMenuByRoleCode(String roleCode);
	
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
	int findCountByUserAndPerCodeAndSysCode(String empCode, String permissionCode, String systemCode);
}
