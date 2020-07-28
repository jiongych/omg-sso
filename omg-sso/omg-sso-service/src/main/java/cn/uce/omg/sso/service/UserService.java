/**
 * 
 */
package cn.uce.omg.sso.service;

import cn.uce.omg.sso.cache.UserCache;
import cn.uce.omg.sso.dao.IUserDao;
import cn.uce.omg.sso.entity.User;
import cn.uce.omg.sso.util.ObjectConvertUtil;
import cn.uce.omg.vo.UserVo;
import cn.uce.utils.StringUtil;


public class UserService {

	private IUserDao userDao;
	private UserCache userCache; 
	
	/**
	 * 根据快运工号+账套查询用户信息
	 * @param ymEmpCode
	 * @param compCode
	 * @return
	 * @author huangting
	 * @date 2019年9月17日 上午9:30:21
	 */
	public UserVo findUserByUniqueKey(String ymEmpCode,String compCode) {
		if (StringUtil.isBlank(ymEmpCode) || StringUtil.isBlank(compCode)) {
			return null;
		}
		return userDao.findUserByUniqueKey(ymEmpCode, compCode);
	}
	
	/**
	 * 更新用户登陆锁定状态
	 * @param userVo
	 * @return
	 */
	public int updateUserLockState(UserVo userVo){
		return userDao.updateUserLockState(userVo);
	}
	
	/**
	 * 根据empId查询用户信息，包括与它对应的员工信息
	 * @param empId
	 * @return
	 */
	public UserVo findUserByEmpId(Integer empId){
		return this.userDao.findUserByEmpId(empId);
	}
	
	/**
	 * 根据empCode查询用户信息
	 * @param empCode
	 * @return
	 */
	public UserVo findUserByEmpCode(String empCode){
		return this.userDao.findUserByEmpCode(empCode);
	}
	
	/**
	 * @Description: 根据hr工号查询用户信息
	 * @param empCode
	 * @return
	 * @author huangting
	 * @date 2019年9月18日 下午4:06:23
	 */
	public UserVo findUserByHrEmpCode(String hrEmpCode,String compCode) {
		return userDao.findByHrEmpCodeAndCompCode(hrEmpCode,compCode);
	}
	/**
	 * @Description: 根据hr工号查询用户信息
	 * @param empCode
	 * @return
	 * @author huangting
	 * @date 2019年9月18日 下午4:06:23
	 */
	public UserVo findByYmEmpCode(String ymEmpCode) {
		return userDao.findByYmEmpCode(ymEmpCode);
	}

	public UserVo findUserByHrEmpCode(String hrEmpCode) {
		return userDao.findByHrEmpCode(hrEmpCode);
	}

	public UserVo findUserByYmEmpCode(String hrEmpCode) {
		return userDao.findByYmEmpCode(hrEmpCode);
	}
	
	/**
	 * @Description: 根据银河工号查询用户信息
	 * @param yhEmpCode
	 * @param compCode
	 * @return
	 * @author huangting
	 * @date 2019年10月17日 下午7:52:49
	 */
	public UserVo findUserByYhEmpCode(String yhEmpCode,String compCode) {
		return userDao.findUserByYhEmpCode(yhEmpCode,compCode);
	}

	
	/**
	 * 根据phone查询用户信息
	 * @param mobile
	 * @return
	 */
	public UserVo findUserByMobile(String mobile){
		return this.userDao.findUserByMobile(mobile);
	}
	
	/**
	 * 根据email查询用户信息
	 * @param email
	 * @return
	 */
	public UserVo findUserByEmail(String email){
		return this.userDao.findUserByEmail(email);
	}
	
	public int updateUserById(UserVo userVo){
		if (userVo.getId() == null) {
			return 0;
		}
		User user = this.userDao.findById(userVo.getId().longValue());
		if (user == null) {
			return 0;
		}
		User userEntity = ObjectConvertUtil.convertObject(userVo, User.class);
		userEntity.setId(userVo.getId().longValue());
		int count = this.userDao.updateById(userEntity);
		userCache.invalid(user.getEmpCode());
		return count;
	}
	
	/**
	 * 刷新用户最后更新时间
	 * @param userVo
	 * @return
	 * @author user
	 * @date 2017年8月1日 下午5:30:59
	 */
	public int refreshUserLastLoginTime(UserVo userVo) {
		int count = this.userDao.updateLastLoginTimeByEmpId(userVo);
		userCache.refresh(userVo.getEmpCode());
		return count;
	}
	
	public void setUserDao(IUserDao userDao){
		this.userDao = userDao;
	}

	/**
	 * @param userCache the userCache to set
	 */
	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}
}
