package cn.uce.omg.sso.dao.portal;

import org.apache.ibatis.annotations.Param;

import cn.uce.omg.sso.vo.portal.PortalCurrentUser;

/**
 * 
 * @Description: (IPortalCurrentUserDao) 
 * @author XJ
 * @date 2018年4月2日 下午2:49:48
 */
public interface IPortalCurrentUserDao {
	
	/**
	 * @Description: (findCurrentUser) 
	 * @param empId
	 * @return
	 * @author XJ
	 * @date 2018年4月2日 下午2:15:18
	 */
	PortalCurrentUser findCurrentUser(@Param("empCode")String empCode);
	
	/**
	 * 
	 * @Description: 查询员工信息
	 * @param empCode
	 * @return
	 * @author huangting
	 * @date 2019年9月24日 下午5:58:02
	 */
	PortalCurrentUser findCurrentUserNoEmpRelation(@Param("empCode")String empCode);
	
	
	/**
	 * 
	 * @Description: 查询员工信息
	 * @param empCode
	 * @return
	 * @author huangting
	 * @date 2019年9月24日 下午5:58:02
	 */
	PortalCurrentUser findCurrentUserInfo(@Param("empCode")String empCode);
}
