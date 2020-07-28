package cn.uce.omg.portal.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.portal.entity.PortalUserRoleRel;
import cn.uce.omg.portal.vo.PortalUserRoleRelVo;
import cn.uce.omg.portal.vo.UserVo;
 
@Repository("fspUserRoleRelDao")
public interface IUserRoleRelDao extends IBaseDao<PortalUserRoleRel, Long> {

	/**
	 * @Description: (findCountByRoleCode) 
	 * @param roleCode
	 * @return
	 * @author XJ
	 * @date 2017年8月31日 下午7:25:52
	 */
	int findCountByRoleCode(String roleCode);
	
	/**
	 * @Description: (deleteRelByEmpCode) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2017年8月31日 下午6:30:33
	 */
	int deleteRelByEmpCode(String empCode);
	
	/**
	 * @Description: (insertRel) 
	 * @param userRoleRelVo
	 * @return
	 * @author XJ
	 * @date 2017年8月31日 下午6:58:27
	 */
	int insertRel(PortalUserRoleRelVo userRoleRelVo);
	
	List<Object> findRoleRelByEmpCode(String empCode);

	/**
	 * @Description: (查询使用此角色的用户) 
	 * @param userVo
	 * @return
	 * @author liyi
	 * @date 2018年04月19日 上午9:47:52
	 */
	List<UserVo> findUserByRole(@Param("roleCode")String roleCode, @Param("startIndex")Integer startIndex, @Param("pageSize")Integer pageSize);
	Integer findUserCountByRole(@Param("roleCode")String roleCode, @Param("startIndex")Integer startIndex, @Param("pageSize")Integer pageSize);

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据员工工号查询信息
	 * </pre>
	 * @param empCode
	 * @return
	 * @return List<String>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月23日下午4:53:32
	 */
	List<String> findRoleByUser(String empCode);

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据角色编号查询用户
	 * </pre>
	 * @param empCode
	 * @return
	 * @return List<String>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月23日下午4:53:32
	 */
	List<String> findUserListByRole(String roleCode);
} 
