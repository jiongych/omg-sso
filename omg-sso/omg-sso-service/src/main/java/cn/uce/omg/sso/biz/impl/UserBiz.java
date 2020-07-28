/** 
 * @项目名称: FSP
 * @文件名称: UserBiz implements IUserBiz, IUserApi 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.biz.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.framework.AopContext;

import cn.uce.base.exception.BusinessException;
import cn.uce.core.mq.api.MqTemplate;
import cn.uce.ols.log.vo.AccessLogVo;
import cn.uce.omg.gateway.constant.OmgProductConstants;
import cn.uce.omg.gateway.constant.OmgTrackConstants.PushSubscribe;
import cn.uce.omg.sso.biz.IUserBiz;
import cn.uce.omg.sso.constant.OmgErrorCode;
import cn.uce.omg.sso.entity.UpdPwdItem;
import cn.uce.omg.sso.service.LoginItemService;
import cn.uce.omg.sso.service.PushService;
import cn.uce.omg.sso.service.UpdPwdItemService;
import cn.uce.omg.sso.service.UserService;
import cn.uce.omg.sso.vo.UpdPwdItemVo;
import cn.uce.omg.vo.LoginItemVo;
import cn.uce.omg.vo.UserVo;
import cn.uce.utils.StringUtil;

/**
 * 用户BIZ实现类
 * @author huangting
 * @date 2017年6月9日 下午4:42:50
 */
public class UserBiz implements IUserBiz {
	private Log LOG = LogFactory.getLog(this.getClass());
	
	private UpdPwdItemService updPwdItemService;
	private LoginItemService loginItemService;
	private PushService pushService;
	private UserService userService;

	//记录登录（含失败）登出状态发送到mq
	private MqTemplate loginLogoutTemplate;
	
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
		return userService.findUserByUniqueKey(ymEmpCode, compCode);
	}
	
	/**
	 * 根据empId查询用户信息，包括与它对应的员工信息
	 * @param empId
	 * @return
	 */
	@Override
	public UserVo findUserByEmpId(Integer empId) {
		if (empId == null) {
			LOG.warn("根据用户ID查找用户信息失败，参数为空。");
			return null;
		}
		return this.userService.findUserByEmpId(empId);
	}

	
	/**
	 * 根据empCode查询用户信息
	 * @param empCode
	 * @return
	 */
	@Override
	public UserVo findUserByEmpCode(String empCode) {
		if (StringUtil.isBlank(empCode)) {
			LOG.warn("根据用户编号查找用户信息失败，参数为空。");
			return null;
		}
		return this.userService.findUserByEmpCode(empCode);
	}
	
	/**
	 * @Description: 根据hr工号查询用户信息
	 * @param empCode
	 * @return
	 * @author huangting
	 * @date 2019年9月18日 下午4:06:23
	 */
	public UserVo findUserByHrEmpCode(String hrEmpCode,String compCode) {
		if (StringUtil.isBlank(hrEmpCode)) {
			LOG.warn("根据hr员工编码查询用户信息失败，参数为空");
			return null;
		}
		return userService.findUserByHrEmpCode(hrEmpCode, compCode);
	}
	
	
	public UserVo findUserByYmEmpCode(String ymEmpCode) {
		return userService.findUserByYmEmpCode(ymEmpCode);
	}

	public UserVo findUserByHrEmpCode(String hrEmpCode) {
		return userService.findUserByHrEmpCode(hrEmpCode);
	}
	
	/**
	 * @Description: 根据银河工号查询
	 * @param yhEmpCode
	 * @param compCode
	 * @return
	 * @author huangting
	 * @date 2019年10月17日 下午7:53:53
	 */
	public UserVo findUserByYhEmpCode(String yhEmpCode,String compCode) {
		if (StringUtil.isBlank(yhEmpCode) || StringUtil.isBlank(compCode)) {
			LOG.warn("根据银河员工编码查询用户信息失败，参数为空");
			return null;
		}
		return userService.findUserByYhEmpCode(yhEmpCode,compCode);
	}


	/**
	 * 根据mobile查询用户信息
	 * @param mobile
	 * @return
	 */
	@Override
	public UserVo findUserByMobile(String mobile) {
		if (StringUtil.isBlank(mobile)) {
			LOG.warn("根据用户手机号查找用户信息失败，参数为空。");
			return null;
		}
		return this.userService.findUserByMobile(mobile);
	}


	/**
	 * 根据email查询用户信息
	 * @param email
	 * @return
	 */
	@Override
	public UserVo findUserByEmail(String email) {
		if (StringUtil.isBlank(email)) {
			LOG.warn("根据用户邮箱查找用户信息失败，参数为空。");
			return null;
		}
		return this.userService.findUserByEmail(email);
	}
	
	/**
	 * 修改用户密码
	 * @param userVo
	 * @return
	 */
	@Override
	public boolean updateUserPassword(UserVo userVo,boolean md5HexFlag) {
		if (userVo == null) {
			LOG.warn("更新用户密码失败，userVo = null");
			return false;
		}
		if ((userVo.getEmpId() == null && StringUtil.isBlank(userVo.getEmpCode()))
				|| StringUtil.isBlank(userVo.getPassword()) || userVo.getUpdateEmp() == null || userVo.getUpdateOrg() == null) {
			LOG.warn("更新用户密码失败，参数为空。");
			return false;
		}
		UserVo oldUserVo = null;
		if (userVo.getEmpId() != null) {
			oldUserVo = this.userService.findUserByEmpId(userVo.getEmpId());
		}else {
			oldUserVo = this.userService.findUserByEmpCode(userVo.getEmpCode());
		}
		if (oldUserVo == null) {
			//用户不存在
			LOG.warn("更新用户密码失败，用户不存在。");
			throw new BusinessException(OmgErrorCode.USER_NONENTITY, "用户不存在。");
			
		}
		UserVo updateUser = new UserVo();
		updateUser.setId(oldUserVo.getId());
		//密码用MD5加密
		//如果是接收快运mq消息则不需要再加密
		if (md5HexFlag) {
			updateUser.setPassword(DigestUtils.md5Hex(userVo.getPassword()));
		} else {
			updateUser.setPassword(userVo.getPassword());
		}
		//add by zhangRb 20180316 修改密码同时解除账户登录锁定状态 start
		updateUser.setLoginLockState(false);
		//add by zhangRb 20180316 修改密码同时解除账户登录锁定状态 end
		updateUser.setUpdateEmp(userVo.getUpdateEmp());
		updateUser.setUpdateOrg(userVo.getUpdateOrg());
		updateUser.setPwdUpdateTime(new Date());
		int updateResult = this.userService.updateUserById(updateUser);
		if (updateResult > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 修改自己的密码
	 * @return
	 */
	public boolean updateMyUserPassword(UserVo userVo){
		if (userVo == null || StringUtil.isBlank(userVo.getOldPassword()) || StringUtil.isBlank(userVo.getPassword()) ||
				(StringUtil.isBlank(userVo.getEmpCode()) && userVo.getEmpId()== null) 
				|| userVo.getUpdateEmp() == null || userVo.getUpdateOrg() == null) {
			LOG.warn("更新密码失败，参数为空。");
			return false;
		}
		UserVo oldUser = null;
		if (userVo.getEmpId() != null) {
			oldUser = this.userService.findUserByEmpId(userVo.getEmpId());
		}else {
			oldUser = this.userService.findUserByEmpCode(userVo.getEmpCode());
		}
		if (oldUser == null) {
			throw new BusinessException(OmgErrorCode.USER_NONENTITY,"用户不存在。");
		}
		String md5OldPasswordParam = DigestUtils.md5Hex(userVo.getOldPassword());
		//校验旧密码
		if (md5OldPasswordParam.equals(oldUser.getPassword())) {
			UserVo updateUserVo = new UserVo();
			updateUserVo.setId(oldUser.getId());
			//密码用MD5加密
			updateUserVo.setPassword(DigestUtils.md5Hex(userVo.getPassword()));
			updateUserVo.setUpdateEmp(userVo.getUpdateEmp());
			updateUserVo.setUpdateOrg(userVo.getUpdateOrg());
			updateUserVo.setPwdUpdateTime(new Date());
			int updateResult = this.userService.updateUserById(updateUserVo);
			if (updateResult > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 更新用户最后更新时间
	 * @param userDto
	 * @return
	 * @throws Exception
	 * @author huangting
	 * @date 2017年5月10日 下午9:04:52
	 */
	@Override
	public int handleUpdateUserLastLoginTime(UserVo userVo) {
		int count = ((IUserBiz) AopContext.currentProxy()).updateUserLastLoginTime(userVo);
		if (count > 0) {
			pushService.pushToAllPartner(userVo.getEmpId(), OmgProductConstants.PARTNER_PRODUCT_UPDATE_USER, PushSubscribe.USER);
		}
		return count;
	}
	
	/**
	 * 更新用户信息
	 * @param userVo
	 * @return
	 */
	@Override
	public int updateUserLastLoginTime(UserVo userVo){
		if (userVo == null || userVo.getEmpId() == null || StringUtil.isBlank(userVo.getEmpCode())) {
			LOG.warn("updateUserLastLoginTime error，ID为空。");
			return 0;
		}
		return this.userService.refreshUserLastLoginTime(userVo);
	}
	
	/**
	 * 解锁用户登陆锁定状态
	 * @param empCode,lockState
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean updateUserLockState(String empCode, Boolean lockState) throws Exception {
		UserVo userVo = new UserVo();
		userVo.setEmpCode(empCode);
		userVo.setLoginLockState(lockState);
		int result = userService.updateUserLockState(userVo);
		return result > 0 ? true : false;
	}

	/**
	 * 保存修改密码明细
	 * @param updPwdItem
	 * @return
	 */
	@Override
	public int insertUpdPwdItem(UpdPwdItem updPwdItem) {
		int result = updPwdItemService.insertUpdPwdItem(updPwdItem);
		return result;
	}
	
	/**
	 * 查询密码历史明细
	 * @param updPwdItem
	 * @return
	 */
	public List<UpdPwdItem> findByThrDay(UpdPwdItemVo updPwdItemVo){
		return updPwdItemService.findByThrDay(updPwdItemVo);
	}

	/**
	 * 修改用户密码
	 * @param userVo
	 * @return
	 */
	@Override
	public boolean handleUpdateUserPassword(UserVo userVo,boolean md5HexFlag) {
		boolean result = ((IUserBiz)AopContext.currentProxy()).updateUserPassword(userVo, md5HexFlag);
		if (result) {
			pushService.pushToAllPartner(userVo.getEmpId(), OmgProductConstants.PARTNER_PRODUCT_UPDATE_USER, PushSubscribe.USER);
		}
		return result;
	}


	/**
	 * 修改用户密码
	 * @param userVo
	 * @return
	 */
	@Override
	public boolean handleUpdateMyUserPassword(UserVo userVo) {
		boolean result = ((IUserBiz)AopContext.currentProxy()).updateMyUserPassword(userVo);
		if (result) {
			pushService.pushToAllPartner(userVo.getEmpId(), OmgProductConstants.PARTNER_PRODUCT_UPDATE_USER, PushSubscribe.USER);
		}
		return result;
	}

	/**
	 * 登录插入登录明细
	 * @param loginItem
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insertLoginItem(LoginItemVo loginItemVo) throws Exception {
		return loginItemService.insertLoginItem(loginItemVo);
	}
	
	public void setUpdPwdItemService(UpdPwdItemService updPwdItemService) {
		this.updPwdItemService = updPwdItemService;
	}
	
	public void setLoginItemService(LoginItemService loginItemService) {
		this.loginItemService = loginItemService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setPushService(PushService pushService) {
		this.pushService = pushService;
	}


	public void setLoginLogoutTemplate(MqTemplate loginLogoutTemplate) {
		this.loginLogoutTemplate = loginLogoutTemplate;
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 向mq中发送登录（含失败）和登出消息
	 * </pre>
	 * @param accessLogVo
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月18日下午1:52:21
	 */
	@Override
	public void sendLoginLogoutMq(AccessLogVo accessLogVo) {
		loginLogoutTemplate.send(accessLogVo);
	}

}
