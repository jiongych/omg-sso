/** 
 * @项目名称: FSP
 * @文件名称: IUserBiz 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.biz;

import java.util.List;

import cn.uce.ols.log.vo.AccessLogVo;
import cn.uce.omg.sso.entity.UpdPwdItem;
import cn.uce.omg.sso.vo.UpdPwdItemVo;
import cn.uce.omg.vo.LoginItemVo;
import cn.uce.omg.vo.UserVo;

/**
 * 
 * @author laizd
 * @since 2016-11-24 
 */
public interface IUserBiz {
	/**
	 * 根据快运工号+账套查询用户信息
	 * @param ymEmpCode
	 * @param compCode
	 * @return
	 * @author huangting
	 * @date 2019年9月17日 上午9:30:21
	 */
	public UserVo findUserByUniqueKey(String ymEmpCode,String compCode);
	
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
	public UserVo findUserByHrEmpCode(String hrEmpCode,String compCode);
	
	UserVo findUserByHrEmpCode(String hrEmpCode);

	UserVo findUserByYmEmpCode(String ymEmpCode);
	
	UserVo findUserByYhEmpCode(String yhEmpCode,String compCode);
	
	/**
	 * 根据mobile查询用户信息
	 * @param mobile
	 * @return
	 */
	public UserVo findUserByMobile(String mobile);
	
	/**
	 * 根据email查询用户信息
	 * @param email
	 * @return
	 */
	public UserVo findUserByEmail(String email);
	
	/**
	 * 更新用户最后更新时间
	 * @param userDto
	 * @return
	 * @throws Exception
	 * @author huangting
	 * @date 2017年5月10日 下午9:04:52
	 */
	public int handleUpdateUserLastLoginTime(UserVo userVo);
	
	/**
	 * 更新用户最后更新时间
	 * @param userDto
	 * @return
	 * @throws Exception
	 * @author huangting
	 * @date 2017年5月10日 下午9:04:52
	 */
	public int updateUserLastLoginTime(UserVo userVo);
	
	/**
	 * 修改用户密码
	 * @param userVo
	 * @return
	 */
	public boolean handleUpdateUserPassword(UserVo userVo,boolean md5HexFlag);
	
	/**
	 * 修改用户密码
	 * @param userVo
	 * @return
	 */
	public boolean updateUserPassword(UserVo userVo,boolean md5HexFlag);
	
	/**
	 * 修改自己的密码
	 * @return
	 */
	public boolean updateMyUserPassword(UserVo userVo);
	
	/**
	 * 修改自己的密码
	 * @return
	 */
	public boolean handleUpdateMyUserPassword(UserVo userVo);
	
	/**
	 * 保存修改密码明细
	 * @param updPwdItem
	 * @return
	 */
	public int insertUpdPwdItem(UpdPwdItem updPwdItem);
	
	/**
	 * 查询密码明细
	 * @param updPwdItem
	 * @return
	 */
	public List<UpdPwdItem> findByThrDay(UpdPwdItemVo updPwdItemVo);
	
	/**
	 * 登录插入登录明细
	 * @param loginItem
	 * @return
	 * @throws Exception
	 */
	public int insertLoginItem(LoginItemVo loginItemVo) throws Exception;
	
	/**
	 * 解锁用户登陆锁定状态
	 * @param empCode,lockState
	 * @return
	 * @throws Exception
	 */
	public Boolean updateUserLockState(String empCode, Boolean lockState) throws Exception;
	
	/**
	 * 向mq中发送登录（含失败）和登出消息
	 * 方法的描述：
	 * <pre>
	 * 
	 * </pre>
	 * @param accessLogVo
	 * @return void
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月18日下午1:51:32
	 */
	public void sendLoginLogoutMq(AccessLogVo accessLogVo);
}
