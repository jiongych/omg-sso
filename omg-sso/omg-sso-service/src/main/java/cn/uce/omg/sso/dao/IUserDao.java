/** 
 * @项目名称: FSP
 * @文件名称: IUserDao extends IBaseDao<User, Long> 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.dao;

import org.apache.ibatis.annotations.Param;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.sso.entity.User;
import cn.uce.omg.vo.UserVo;

/**
 * IUserDao extends IBaseDao<User, Long>  
 * @Description: IUserDao extends IBaseDao<User, Long>  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public interface IUserDao extends IBaseDao<User, Long> {
	
	/**
	 * 根据empId查询用户信息，包括与它对应的员工信息
	 * @param empId
	 * @return
	 */
	public UserVo findUserByEmpId(Integer empId);
	
	/**
	 * 根据empCode查询用户信息
	 * @param empCode
	 * @return
	 */
	public UserVo findUserByEmpCode(String empCode);
	
	/**
	 * @Description: 根据hr工号查询用户信息
	 * @param empCode
	 * @return
	 * @author huangting
	 * @date 2019年9月18日 下午4:06:23
	 */
	public UserVo findByHrEmpCodeAndCompCode(@Param("hrEmpCode") String hrEmpCode,@Param("compCode") String compCode);
	
	/**
	 * @Description: 根据hr工号查询用户信息
	 * @param empCode
	 * @return
	 * @author huangting
	 * @date 2019年9月18日 下午4:06:23
	 */
	public UserVo findByHrEmpCode(@Param("hrEmpCode") String hrEmpCode);

	/**
	 * @Description: 根据集团工号查询用户信息
	 * @param empCode
	 * @return
	 * @author huangting
	 * @date 2019年9月18日 下午4:06:23
	 */
	public UserVo findByYmEmpCode(@Param("ymEmpCode") String ymEmpCode);
	
	/**
	 * @Description: 根据hr工号查询用户信息
	 * @param empCode
	 * @return
	 * @author huangting
	 * @date 2019年9月18日 下午4:06:23
	 */
	public UserVo findUserByYhEmpCode(@Param("yhEmpCode") String yhEmpCode,@Param("compCode") String compCode);
	
	/**
	 * 根据mobile查询用户信息
	 * @param mobile
	 * @return
	 */
	public UserVo findUserByMobile(String mobile);
	
	/**
	 * 根据快运工号+账套查询用户信息
	 * @param ymEmpCode
	 * @param compCode
	 * @return
	 * @author huangting
	 * @date 2019年9月17日 上午9:30:21
	 */
	public UserVo findUserByUniqueKey(@Param("ymEmpCode") String ymEmpCode,@Param("compCode") String compCode);
	
	/**
	 * 根据email查询用户信息
	 * @param email
	 * @return
	 */
	public UserVo findUserByEmail(String email);
	
	/**
	 * 更新用户最后更新时间
	 * @param userVo
	 * @return
	 */
	public int updateLastLoginTimeByEmpId(UserVo userVo);

	/**
	 * 更新用户登陆锁定状态
	 * @param userVo
	 * @return
	 */
	public int updateUserLockState(UserVo userVo);
	
}
