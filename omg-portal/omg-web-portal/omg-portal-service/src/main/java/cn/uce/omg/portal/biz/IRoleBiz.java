package cn.uce.omg.portal.biz;

import java.util.List;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.vo.PortalDataPushVo;
import cn.uce.omg.portal.vo.RoleVo;
import cn.uce.omg.portal.vo.UserVo;

/**
 * @Description: (IRoleBiz) 
 * @author XJ
 * @date 2017年7月29日 下午3:50:39
 */
public interface IRoleBiz {
	/**
	 * @Description: (分页查询角色信息) 
	 * @param roleVo
	 * @param page
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午3:52:36
	 */
	Pagination<RoleVo> findRoleByPage(RoleVo roleVo, Integer currentPage,Integer pageSize);
	
	/**
	 * @Description: (按角色码查询角色) 
	 * @param roleCode
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:01:57
	 */
	RoleVo findRoleByCode(String roleCode);
	
	/**
	 * 同步重推使用
	 * @param roleCode
	 * @return
	 */
	RoleVo findRoleSyncByCode(String roleCode);
	
	/**
	 * @Description: (新增角色) 
	 * @param roleVo
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:02:32
	 */
	int addRole(RoleVo roleVo);
	
	/**
	 * @Description: (根据角色码修改角色) 
	 * @param roleVo
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:03:26
	 */
	int updateRoleByRoleCode(RoleVo roleVo);
	
	/**
	 * @Description: (根据角色码删除角色) 
	 * @param roleCode
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:03:52
	 */
	int deleteRole(String roleCode);
	
	/**
	 * @Description: (查询用户已分配的角色) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:07:05
	 */
	List<RoleVo> findRoleByUser(String empCode);
	
	/**
	 * @Description: (查询用户未分配的角色) 
	 * @param userVo
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:08:37
	 */
	List<RoleVo> findNotRoleByUser(String empCode,String baseOrgCode,Integer roleLevel, Integer cmsOrgType);

	/**
	 * @Description: (查询使用此角色的用户) 
	 * @param userVo
	 * @return
	 * @author liyi
	 * @param p 
	 * @date 2018年04月19日 上午9:47:52
	 */
	Pagination<UserVo> findUserByRole(String roleCode, Page p);

	/**
	 * @Description: (查询时间范围内变更的角色) 
	 * @param userVo
	 * @return
	 * @author liyi
	 * @date 2018年04月19日 上午9:47:52
	 */
	List<String> findRoleByDate(PortalDataPushVo dataPushVo);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据员工工号查询roleCode集合
	 * </pre>
	 * @param empCode
	 * @return
	 * @return List<String>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月23日下午4:58:10
	 */
	List<String> findRoleCodeByUser(String empCode);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据角色编号查询用户
	 * </pre>
	 * @param roleCode
	 * @return
	 * @return List<String>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月23日下午5:25:38
	 */
	List<String> findUserListByRole(String roleCode);
}
