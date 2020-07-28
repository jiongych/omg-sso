/** 
 * @项目名称: FSP
 * @文件名称: LogAspect 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.aop;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;

import cn.uce.core.mq.api.MqTemplate;
import cn.uce.ols.log.vo.AccessLogVo;
import cn.uce.omg.sso.biz.IEmpBiz;
import cn.uce.omg.sso.biz.IUserBiz;
import cn.uce.omg.sso.constant.AuthConstants;
import cn.uce.omg.sso.entity.UpdPwdItem;
import cn.uce.omg.sso.exception.GatewayException;
import cn.uce.omg.sso.vo.AuthResultVo;
import cn.uce.omg.sso.vo.EmpOrgInfoVo;
import cn.uce.omg.sso.vo.LoginVo;
import cn.uce.omg.sso.vo.LogoutVo;
import cn.uce.omg.vo.LoginItemVo;
import cn.uce.omg.vo.UserVo;
import cn.uce.utils.FastJsonUtil;
import cn.uce.utils.StringUtil;

/**
 * 日志
 * @author huangting
 * @date 2017年6月9日 下午2:45:17
 */
public class LogAspect {
	private final static Log log = LogFactory.getLog(LogAspect.class);
	/**
	 * 用户biz
	 */
	private IUserBiz userBiz;

	/**
	 * 员工biz
	 */
	private IEmpBiz empBiz;
	
	/**
	 * 登录失败日志，成功不在此发送
	 */
	private MqTemplate loginSuccessTemplate;
	/**
	 * 登录日志
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 * @author huangting
	 * @date 2017年6月8日 下午2:40:57
	 */
	public AuthResultVo loginLog(ProceedingJoinPoint joinPoint) throws Throwable {
		LoginVo param = null;
		if ((joinPoint.getArgs()[0]) instanceof LoginVo) {
			param = (LoginVo) joinPoint.getArgs()[0];
		}
		AuthResultVo result = null;
		
		try {
			//获取方法执行结果对象
			result = (AuthResultVo) joinPoint.proceed();
			if (result != null  && param != null) {
				try {
					//登录成功发送消息
					AccessLogVo accessLogVo = handleLoginVo(result, param, null, "success", "login", null);
					userBiz.sendLoginLogoutMq(accessLogVo);
				} catch(Exception e) {
					log.error("登录成功发送消息失败", e);
				}
				
			}
		} catch (GatewayException ge) {
			//登录失败发送消息
			AccessLogVo accessLogVo = handleLoginVo(result, param, null, "fail", "login", ge.getErrorMessage());
			userBiz.sendLoginLogoutMq(accessLogVo);
			LoginItemVo paramLoginItem = paramLoginItem(param);
			String errorCode = ge.getErrorCode() + ":"+ ge.getErrorMessage();
			if (null != errorCode && errorCode.length() > 64) {
				errorCode = errorCode.substring(0, 63);
			}
			paramLoginItem.setErrorCode(errorCode);
			log.warn("登录失败："+param.getSystemCode()+"--登录工号："+param.getUserName()+"--登录账套：null--失败原因：" + ge.getErrorCode() + ge.getErrorMessage() +  "--ip地址："+param.getIpAddr() +  "--登录时间："+System.currentTimeMillis());
			loginSuccessTemplate.send(FastJsonUtil.toJsonString(paramLoginItem));
            throw new GatewayException(ge.getErrorCode(), ge.getErrorMessage());
        } catch (Exception e) {
			//登录失败发送消息
			AccessLogVo accessLogVo = handleLoginVo(result, param, null, "fail", "login", "其它原因");
			userBiz.sendLoginLogoutMq(accessLogVo);
			log.error("保存登录日志失败", e);
			throw new Exception(e);
		}
		return result;
	}

	public LoginItemVo paramLoginItem(LoginVo param) {
		// 验证通过，记录登录日志
		LoginItemVo loginItem = new LoginItemVo();
		// 设置ip地址
		loginItem.setIpAddr(param.getIpAddr());
		// 设置机器码
		loginItem.setMachineCode(param.getMachineCode());
		//设置操作时间
		loginItem.setOperateTime(new Date());
		//设置操作类型
		loginItem.setOperateType(AuthConstants.OPERATE_TYPE_LOGIN);
		//设置系统编码
		loginItem.setSystemCode(param.getSystemCode());
		//设置创建时间
		loginItem.setCreateTime(new Date());
		//设置员工id
		loginItem.setEmpId(-1);
		//设置用户名
		loginItem.setUserName(param.getUserName());
		loginItem.setEmpCode(param.getUserName());
		return loginItem;
	}
	/**
	 * 登出日志
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 * @author huangting
	 * @date 2017年6月8日 下午2:41:09
	 */
	public AuthResultVo logoutLog(ProceedingJoinPoint joinPoint) throws Throwable {
		LogoutVo param = null;
		if ((joinPoint.getArgs()[0]) instanceof LogoutVo) {
			param = (LogoutVo) joinPoint.getArgs()[0];
		}
		//获取方法执行结果对象
		AuthResultVo result = (AuthResultVo) joinPoint.proceed();
		try {
			if (result != null && result.getTokenId() != null && param != null) {
				// 验证通过，记录登出日志
				LoginItemVo loginItem = new LoginItemVo();
				// 设置ip地址
				loginItem.setIpAddr(param.getIpAddr());
				// 设置机器码
				loginItem.setMachineCode(param.getMachineCode());
				//设置操作时间
				loginItem.setOperateTime(new Date());
				//设置操作类型
				loginItem.setOperateType(AuthConstants.OPERATE_TYPE_LOGOUT);
				//设置系统编码
				loginItem.setSystemCode(param.getSystemCode());
				//设置创建时间
				loginItem.setCreateTime(new Date());
				//设置员工id
				loginItem.setEmpId(result.getEmpId());
				//设置用户名
				loginItem.setUserName(result.getUserName());
				loginItem.setIpAddr(param.getIpAddr());
				/**
				 * 记录登出日志
				 */
				userBiz.insertLoginItem(loginItem);
				
				try {
					/**
					 * 退出成功发送消息
					 */
					AccessLogVo accessLogVo = handleLoginVo(result, null, param, "success", "logout", null);
					userBiz.sendLoginLogoutMq(accessLogVo);
				} catch(Exception e) {
					log.error("退出成功发送消息失败", e);
				}
				
			}
		} catch (GatewayException ge) {
			//登录失败发送消息
			AccessLogVo accessLogVo = handleLoginVo(result, null, param, "fail", "login", ge.getErrorMessage());
			userBiz.sendLoginLogoutMq(accessLogVo);
			throw new GatewayException(ge.getErrorCode(), ge.getErrorMessage());
        } catch (Exception e) {
			/**
			 * 退出失败发送消息
			 */
			AccessLogVo accessLogVo = handleLoginVo(result, null, param, "fail", "logout", "其它原因");
			userBiz.sendLoginLogoutMq(accessLogVo);
			log.error("保存登出日志失败", e);
			throw new Exception(e);
		}
		return result;
	}
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 登录登出日志加工VO
	 * </pre>
	 * @param result
	 * @param login
	 * @param operationResult:操作结果.success代表成功，fail代表失败	
	 * @param operationType:操作类型.login代表登录，logout代表登出
	 * @return
	 * @return AccessLogVo
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月18日下午4:49:00
	 */
	public AccessLogVo handleLoginVo(AuthResultVo result, LoginVo login, LogoutVo logout, String operationResult, String operationType, String reason) {
		AccessLogVo accessLogVo = new AccessLogVo();
		String empCode = "";
		if (null != login) {
			empCode = login.getUserName();
			accessLogVo.setAccessMac(login.getMacAddr());
			accessLogVo.setAccessSystem(login.getSystemCode());
			accessLogVo.setClientPublicIp(login.getIpAddr());
		} else {
			empCode = logout.getEmpCode();
			accessLogVo.setAccessMac(logout.getMacAddr());
			accessLogVo.setAccessSystem(logout.getSystemCode());
			accessLogVo.setClientPublicIp(logout.getIpAddr());
		}
		
		if (null == result) {
			accessLogVo.setAccessEmpCode(empCode);
			EmpOrgInfoVo empVo = empBiz.findEmpAndOrgByEmpCode(empCode);
			accessLogVo.setAccessEmpName(empVo == null ? "未知用户" : empVo.getEmpName());
			accessLogVo.setAccessOrg(empVo == null ? "未知用户" : empVo.getOrgId() + "");
			accessLogVo.setAccessOrgName(empVo == null ? "未知用户" : empVo.getOrgName());
		} else {
			empCode = result.getUserName();
			EmpOrgInfoVo empVo = empBiz.findEmpAndOrgByEmpCode(empCode);
			accessLogVo.setAccessEmpCode(result.getUserName());
			accessLogVo.setAccessEmpName(empVo == null ? "未知用户" : empVo.getEmpName());
			accessLogVo.setAccessOrg(empVo == null ? "未知用户" : empVo.getOrgId() + "");
			accessLogVo.setAccessOrgName(empVo == null ? "未知用户" : empVo.getOrgName());
		}

		accessLogVo.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
		accessLogVo.setOperationType(operationType);//登录
		accessLogVo.setAccessTime(new Date());

		accessLogVo.setSourceType("web");
		accessLogVo.setOperationResult(operationResult);
		accessLogVo.setReason(reason);
		return accessLogVo;
	}

	/**
	 * 管理员修改密码记录日志
	 * @param joinPoint
	 * @throws Throwable
	 */
	public boolean updatePwdLog(ProceedingJoinPoint joinPoint) throws Throwable {
		// 获取传入参数，判断密码是否为空，以此判断updateEmpPassword()是否为更新密码
		Object[] params = joinPoint.getArgs();
		UserVo updateUserVo = (UserVo) params[0];
		if (updateUserVo != null) {
			// 查询旧密码
			UserVo oldUserVo = null;
			if (updateUserVo.getEmpId() != null) {
				oldUserVo = this.userBiz.findUserByEmpId(updateUserVo.getEmpId());
			}else if (StringUtil.isNotBlank(updateUserVo.getEmpCode())){
				oldUserVo = this.userBiz.findUserByEmpCode(updateUserVo.getEmpCode());
			}
			if(oldUserVo == null){
				log.error("密码修改日志失败,未找到老用户");
				return false;
			}
			boolean result = (boolean) joinPoint.proceed();
			if (result) {
				try {
					// 记录修改密码日志操作
					UpdPwdItem updPwdItem = new UpdPwdItem();
					updPwdItem.setEmpId(oldUserVo.getEmpId());
					// 旧密码
					updPwdItem.setPassword(oldUserVo.getPassword());
					updPwdItem.setIpAddr(updateUserVo.getOperatorIP());
					updPwdItem.setMacAddr(updateUserVo.getOperatorMacAddress());
					updPwdItem.setOperateEmp(updateUserVo.getUpdateEmp());
					updPwdItem.setOperateOrg(updateUserVo.getUpdateOrg());
					if (updateUserVo.getEmpId() != null) {
						updPwdItem.setUserName(oldUserVo.getEmpVo().getEmpCode());
					}else {
						updPwdItem.setUserName(updateUserVo.getEmpCode());
					}
					updPwdItem.setOperateTime(new Date());
					updPwdItem.setCreateTime(new Date());
					//2017-07-06 huangting add
					updPwdItem.setUpdateSource(updateUserVo.getUpdateSource());
					updPwdItem.setSystemCode(updateUserVo.getSystemCode());
					updPwdItem.setPasswordStrength(updateUserVo.getPasswordStrength());
					//2017-07-06 huangting end
					userBiz.insertUpdPwdItem(updPwdItem);
				} catch (Exception e) {
					log.error("保存密码修改日志失败", e);
				}
			}
			return result;
		} else {
			return (boolean) joinPoint.proceed();
		}
	}
	
	/**
	 * 修改密码记录日志
	 * @param joinPoint
	 * @throws Throwable
	 */
	public boolean updateMyPwdLog(ProceedingJoinPoint joinPoint) throws Throwable {
		// 获取传入参数，判断密码是否为空，以此判断updateEmpPassword()是否为更新密码
		Object[] params = joinPoint.getArgs();
		UserVo updateUserVo = (UserVo) params[0];
		if (updateUserVo != null) {
			// 查询旧密码
			UserVo oldUserVo = null;
			if (updateUserVo.getEmpId() != null) {
				oldUserVo = this.userBiz.findUserByEmpId(updateUserVo.getEmpId());
			}else if (StringUtil.isNotBlank(updateUserVo.getEmpCode())){
				oldUserVo = this.userBiz.findUserByEmpCode(updateUserVo.getEmpCode());
			}
			if(oldUserVo == null){
				log.error("密码修改日志失败,未找到老用户");
				return false;
			}
			boolean result = (boolean) joinPoint.proceed();
			if (result) {
				try {
					// 记录修改密码日志操作
					UpdPwdItem updPwdItem = new UpdPwdItem();
					updPwdItem.setEmpId(oldUserVo.getEmpId());
					// 旧密码
					updPwdItem.setPassword(oldUserVo.getPassword());
					updPwdItem.setIpAddr(updateUserVo.getOperatorIP());
					updPwdItem.setMacAddr(updateUserVo.getOperatorMacAddress());
					updPwdItem.setOperateEmp(updateUserVo.getUpdateEmp());
					updPwdItem.setOperateOrg(updateUserVo.getUpdateOrg());
					if (updateUserVo.getEmpId() != null) {
						updPwdItem.setUserName(oldUserVo.getEmpVo().getEmpCode());
					}else {
						updPwdItem.setUserName(updateUserVo.getEmpCode());
					}
					updPwdItem.setOperateTime(new Date());
					updPwdItem.setCreateTime(new Date());
					//2017-07-06 huangting add
					updPwdItem.setUpdateSource(updateUserVo.getUpdateSource());
					updPwdItem.setSystemCode(updateUserVo.getSystemCode());
					updPwdItem.setPasswordStrength(updateUserVo.getPasswordStrength());
					//2017-07-06 huangting end
					userBiz.insertUpdPwdItem(updPwdItem);
				} catch (Exception e) {
					log.error("保存密码修改日志失败", e);
				}
			}
			return result;
		} else {
			return (boolean) joinPoint.proceed();
		}
	}
	
	

	/**
	 * @param userBiz the userBiz to set
	 */
	public void setUserBiz(IUserBiz userBiz) {
		this.userBiz = userBiz;
	}
	/**
	 * @param empBiz the empBiz to set
	 */
	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
	}

	public void setLoginSuccessTemplate(MqTemplate loginSuccessTemplate) {
		this.loginSuccessTemplate = loginSuccessTemplate;
	}
	
}
