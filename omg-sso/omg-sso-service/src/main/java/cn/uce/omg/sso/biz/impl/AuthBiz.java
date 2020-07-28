/** 
 * @项目名称: FSP
 * @文件名称: AuthBiz implements IAuthBiz, IAuthApi 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.biz.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.uce.mcs.caller.api.vo.MessageRespVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.uce.core.mq.api.MqTemplate;
import cn.uce.omg.sso.api.IAuthApi;
import cn.uce.omg.sso.biz.IAuthBiz;
import cn.uce.omg.sso.biz.IEmpBiz;
import cn.uce.omg.sso.biz.IOrgBiz;
import cn.uce.omg.sso.biz.IPortalBiz;
import cn.uce.omg.sso.biz.IRoleBiz;
import cn.uce.omg.sso.biz.ISysTypeBiz;
import cn.uce.omg.sso.biz.ISystemInfoBiz;
import cn.uce.omg.sso.biz.IUserBiz;
import cn.uce.omg.sso.cache.HashRedisWithFieldExpireCache;
import cn.uce.omg.sso.constant.AuthConstants;
import cn.uce.omg.sso.constant.ErrorCode;
import cn.uce.omg.sso.constant.GatewayConstants;
import cn.uce.omg.sso.constant.LoginType;
import cn.uce.omg.sso.constant.OmgConstants;
import cn.uce.omg.sso.constant.SmsConstants;
import cn.uce.omg.sso.constant.UserAccountType;
import cn.uce.omg.sso.entity.OmgWeakPassword;
import cn.uce.omg.sso.entity.UpdPwdItem;
import cn.uce.omg.sso.exception.GatewayException;
import cn.uce.omg.sso.service.CmsOrgService;
import cn.uce.omg.sso.service.OmgWeakPasswordService;
import cn.uce.omg.sso.util.CodeUtil;
import cn.uce.omg.sso.util.DateUtil;
import cn.uce.omg.sso.util.EmpInfoUtil;
import cn.uce.omg.sso.util.LoginCodeUtil;
import cn.uce.omg.sso.util.SignUtils;
import cn.uce.omg.sso.util.SmsUtil;
import cn.uce.omg.sso.util.redis.BoundHashRedisSupport;
import cn.uce.omg.sso.vo.AuthCheckVo;
import cn.uce.omg.sso.vo.AuthResultVo;
import cn.uce.omg.sso.vo.EmpInfoVo;
import cn.uce.omg.sso.vo.ExpCodeVo;
import cn.uce.omg.sso.vo.LoginCodeVo;
import cn.uce.omg.sso.vo.LoginFailVo;
import cn.uce.omg.sso.vo.LoginVo;
import cn.uce.omg.sso.vo.LogoutVo;
import cn.uce.omg.sso.vo.ResetPwdVo;
import cn.uce.omg.sso.vo.SmsVo;
import cn.uce.omg.sso.vo.TokenVo;
import cn.uce.omg.sso.vo.UpdPwdItemVo;
import cn.uce.omg.sso.vo.UpdPwdVo;
import cn.uce.omg.sso.vo.UserPasswordVo;
import cn.uce.omg.sso.vo.UserRoleVo;
import cn.uce.omg.sso.vo.portal.PortalCurrentUser;
import cn.uce.omg.vo.CmsOrgVo;
import cn.uce.omg.vo.EmpVo;
import cn.uce.omg.vo.LoginItemVo;
import cn.uce.omg.vo.OrgVo;
import cn.uce.omg.vo.OtherEmpRelationVo;
import cn.uce.omg.vo.SystemInfoVo;
import cn.uce.omg.vo.UserRoleRelVo;
import cn.uce.omg.vo.UserVo;
import cn.uce.smsp.base.HttpResponseVo;
import cn.uce.utils.DesUtil;
import cn.uce.utils.FastJsonUtil;
import cn.uce.utils.StringUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

/**
 * 认证服务
 * @author tanchong
 *
 */
public class AuthBiz implements IAuthBiz, IAuthApi {

	private Log log = LogFactory.getLog(this.getClass());
	private ISystemInfoBiz systemInfoBiz;
	private IUserBiz userBiz;
	/** 角色biz */
	private IRoleBiz roleBiz;
	/** token系统信息缓存 */
	private HashRedisWithFieldExpireCache<TokenVo> tokenSystemCache;
	/** 用户token信息缓存 */
	private BoundHashRedisSupport<List<String>> userTokenCache;
	//add by xj
	private BoundHashRedisSupport<PortalCurrentUser> tokenUserInfoCache;
	//add by xj
	/** 认证系统配置超时时间，单位：秒 */
	private Integer defaultLoginTimeout;
	/** 认证系统默认比子系统多多少分钟的超时时间，单位：秒 */
	private Integer reservedLoginTimeout;
	/** 认证工具类 */
	private EmpInfoUtil empInfoUtil;
	/** 登录超时时间：毫秒，默认15分钟 */
	private Long loginExpressTime = 900000L;
	private CodeUtil codeUtil;
	private SmsUtil smsUtil;
	private LoginCodeUtil loginCodeUtil;
	public LoginCodeUtil getLoginCodeUtil() {
		return loginCodeUtil;
	}

	public void setLoginCodeUtil(LoginCodeUtil loginCodeUtil) {
		this.loginCodeUtil = loginCodeUtil;
	}

	private String smsTemplate;
	// add by majun 20190622 增加手机验证码登录 begin
	/** 登录短信模板。 */
	private String smsLoginTemplate;
	
	public String getSmsLoginTemplate() {
		return smsLoginTemplate;
	}

	public void setSmsLoginTemplate(String smsLoginTemplate) {
		this.smsLoginTemplate = smsLoginTemplate;
	}
	// add by majun 20190622 增加手机验证码登录 begin

	/** 登录每次允许失败的次数:3次 */
	private int failLoginNum = 3;
	/** 强制修改密码间隔时间,单位:天 */
	private long updPwdIntervalTime = 30L;
	private IEmpBiz empBiz;
	private MqTemplate loginSuccessTemplate;
	/** 数据字典 */
	private ISysTypeBiz sysTypeBiz;
	/**
	 * 行政机构
	 */
	private IOrgBiz orgBiz;
	
	private DefaultMQProducer mqProducer;
	
	public void setMqProducer(DefaultMQProducer mqProducer) {
		this.mqProducer = mqProducer;
	}
	
	/**
	 * 校验弱密码
	 * 
	 */
	private OmgWeakPasswordService omgWeakPasswordService;
	//查询乾坤机构信息
	private CmsOrgService cmsOrgService;
	
    public void setCmsOrgService(CmsOrgService cmsOrgService) {
		this.cmsOrgService = cmsOrgService;
	}

	/**
     * 校验弱密码
     * @param omgWeakPasswordService
     */
	public void setOmgWeakPasswordService(
			OmgWeakPasswordService omgWeakPasswordService) {
		this.omgWeakPasswordService = omgWeakPasswordService;
	}
	
	//add by xj
	private IPortalBiz portalBiz;
	//add by xj
	
	private int historyTotal;

	public void setOrgBiz(IOrgBiz orgBiz) {
		this.orgBiz = orgBiz;
	}

	/**
	 * 根据systemCode获取系统信息
	 * @return
	 * @throws Exception
	 */
	public SystemInfoVo findSystemBySystemCode(String systemCode) throws Exception {
		return systemInfoBiz.findSystemBySystemCode(systemCode);
	}
	/**
	 * 根据userName获取EmpInfoVo
	 * @return
	 * @throws Exception
	 */
	public EmpInfoVo findEmpInfoByUserName(String userName) throws Exception {
		return empInfoUtil.getEmpInfoByKey(userName, AuthConstants.LOGIN_TYPE_EMP_CODE);
	}
	/**
	 * 根据条件获取List<SystemInfoVo> 列表
	 * @return
	 * @throws Exception
	 */
	public List<SystemInfoVo> findSystemByCondition(SystemInfoVo searchSystemInfo) throws Exception {
		List<SystemInfoVo> systemInfoList = systemInfoBiz.findByCondition(searchSystemInfo);
		return systemInfoList;
	}

  /**
	* 登录 
	* @param loginVo
	* @return
	* @throws GatewayException
	* @throws Exception 
	* @see cn.uce.omg.sso.biz.IAuthBiz#login(cn.uce.omg.sso.vo.LoginVo)
	 */
	@Override
	public AuthResultVo login(LoginVo loginVo) throws GatewayException, Exception {
		if(log.isInfoEnabled()){
			log.info(Thread.currentThread().getName()+ "登录dubbo开始时间" + System.currentTimeMillis());
		}
		AuthResultVo authResultVo = new AuthResultVo();
		// 校验参数是否正常
		if (loginVo == null || StringUtil.isBlank(loginVo.getSystemCode()) || loginVo.getLoginTime() == null || StringUtil.isBlank(loginVo.getUserName())
				|| StringUtil.isBlank(loginVo.getPassword())) {
			throw new GatewayException(ErrorCode.SYSTEM_PARAM_ERROR, ErrorCode.SYSTEM_PARAM_ERROR_MESSGE);
		}
		log.warn("准备登录系统："+loginVo.getSystemCode()+"--登录工号："+loginVo.getUserName()+"--登录账套："+loginVo.getCompCode()+"--登录密码："+loginVo.getPassword()+"--ip地址："+loginVo.getIpAddr()+"--登录时间："+System.currentTimeMillis());
		//add by zhangRb 20180905 支持手机号登录需求   输入的账号转换为工号
		//add by huangting 20190919 增加快运账号登录方式 begin
		String empCode = null;
		if (StringUtils.isEmpty(loginVo.getUserAccountType())) {
			loginVo.setUserAccountType(UserAccountType.Portal.name());
		}
		if (StringUtils.equals(UserAccountType.Portal.name(), loginVo.getUserAccountType())) {
			empCode = empInfoUtil.getLoginEmpCode(loginVo);
		} else if (StringUtils.equals(UserAccountType.Yinhe.name(), loginVo.getUserAccountType())) {
			loginVo.setYhUserName(loginVo.getUserName());
			empCode = empInfoUtil.findEmpCodeByYinhe(loginVo.getUserName(),loginVo.getCompCode());
		} 
		// 用户不存在
		if (empCode == null) {
			throw new GatewayException(ErrorCode.USER_NONENTITY, ErrorCode.USER_NONENTITY_MESSGE);
		}
		//add by huangting 20190919 增加快运账号登录方式 end
		loginVo.setUserName(empCode);
		// 校验系统是否存活
		SystemInfoVo systemInfo = authCheckSystem(loginVo.getSystemCode(), loginVo.getMachineCode(), loginVo.getLoginTime());
		// 获取超时时间
		Date expireTime = getExpireTime(systemInfo);
		if (expireTime == null) {
			throw new GatewayException(ErrorCode.SYSTEM_CODE_ERROR, ErrorCode.SYSTEM_CODE_ERROR_MESSGE);
		}
		if(log.isInfoEnabled()){
			log.info(Thread.currentThread().getName()+ "登录dubbo登录校验开始时间" + System.currentTimeMillis());
		}
		// 参数校验完成，进行验证登录校验
		//modify by zhangRb 20180904 添加系统信息参数
		boolean isLoginFlag = validateLoginAuth(loginVo, authResultVo, systemInfo,true);
		
		if(log.isInfoEnabled()){
			log.info(Thread.currentThread().getName()+ "登录dubbo登录校验结束时间" + System.currentTimeMillis());
		}
		if (!isLoginFlag) {
			return authResultVo;
		}
		
		if(log.isInfoEnabled()){
			log.info(Thread.currentThread().getName()+ "登录dubbo登录获取token开始时间" + System.currentTimeMillis());
		}
		// 根据系统信息是否使用统一角色配置设置权限
		if (systemInfo != null ) {
			if (systemInfo.getUnifyRoleFlag() != null && systemInfo.getUnifyRoleFlag()) {
				setUserRole(authResultVo);
			}
			// 2、获取token
			//add by xj
			//String encryptTokenId = putTokenToCache(loginVo,authResultVo.getEmpCode(), systemInfo.getSecretKey(), expireTime);
			String encryptTokenId = putTokenToCache(loginVo,authResultVo.getEmpCode(),authResultVo.getOrgId(), systemInfo.getSecretKey(), expireTime);
			//add by xj
			authResultVo.setTokenId(encryptTokenId);
		}
		authResultVo.setExpireTime(expireTime);
		if(log.isInfoEnabled()){
			log.info(Thread.currentThread().getName()+ "登录dubbo登录获取token结束时间" + System.currentTimeMillis());
		}
		log.warn("登录系统：登录成功"+loginVo.getSystemCode()+"--登录工号："+loginVo.getUserName()+"--登录账套："+loginVo.getCompCode()+"--登录密码："+loginVo.getPassword()+"--ip地址："+loginVo.getIpAddr()+"--登录时间："+System.currentTimeMillis());
		// 登录成功发送消息
		sendLoginMqNotice(authResultVo, loginVo);
		
		UserVo user = new UserVo();
		user.setPassword(loginVo.getPassword());
		user.setUpdateTime(new Date());
		if(log.isInfoEnabled()){
			log.info(Thread.currentThread().getName()+ "登录dubbo登录发送消息时间结束时间" + System.currentTimeMillis());
		}
		return authResultVo;
	}

	/**
	 * 登出
	 * @param logoutVo
	 * @throws GatewayException
	 * @throws Exception
	 */
	@Override
	public AuthResultVo logout(LogoutVo logoutVo) throws Exception {
		//校验请求参数
		if (logoutVo == null || StringUtil.isEmpty(logoutVo.getSystemCode())
				|| StringUtil.isEmpty(logoutVo.getEmpCode()) || logoutVo.getLogoutTime() == null) {
			throw new GatewayException(ErrorCode.SYSTEM_PARAM_ERROR, ErrorCode.SYSTEM_PARAM_ERROR_MESSGE);
		}
		AuthResultVo authResultVo = new AuthResultVo();
		String empCode = logoutVo.getEmpCode();
		String tokenId = logoutVo.getTokenId();
		EmpInfoVo empInfoVo = checkUserExists(empCode, AuthConstants.LOGIN_TYPE_EMP_CODE);
		//删除缓存
		TokenVo tokenVo = tokenSystemCache.get(tokenId);
		if (tokenVo != null) {
			// 校验用户名是否正确
			checkEmpCodeIsCorrect(logoutVo.getEmpCode(),tokenVo.getEmpCode());
			authResultVo.setTokenId(tokenId);
			tokenSystemCache.delete(tokenId);
		} 
		//根据用户名获取tooken信息
		List<String> tokenList = userTokenCache.get(empCode);
		if (tokenList != null) {
			tokenList.remove(tokenId);
			userTokenCache.put(empCode, tokenList);
		}
		//add by xj
		tokenUserInfoCache.delete(tokenId);
		//add by xj
		authResultVo.setEmpId(empInfoVo.getUserVo().getEmpId());
		authResultVo.setUserName(empCode);
		return authResultVo;
	}
	
	/**
	 * 检测参数是否正常
	 * @param authCheckVo
	 * @param authResultVo
	 * @return
	 * @throws GatewayException
	 * @throws Exception
	 */
	private boolean validateAuthCheckParam(AuthCheckVo authCheckVo, AuthResultVo authResultVo) throws GatewayException, Exception {
		// 校验系统是否存活
		SystemInfoVo systemInfo = findSystemBySystemCode(authCheckVo.getSystemCode());
		if (systemInfo == null) {
			throw new GatewayException(ErrorCode.SYSTEM_CODE_ERROR, ErrorCode.SYSTEM_CODE_ERROR_MESSGE);
		} else if (systemInfo.getEnable() == null && systemInfo.getEnable() == AuthConstants.SYSTEM_ENABLE_NO) {
			// 系统被禁用
			throw new GatewayException(ErrorCode.SYSTEM_DISABLE, ErrorCode.SYSTEM_DISABLE_MESSGE);
		}
		// 校验用户是否存在
		EmpInfoVo empInfoVo = authCheckUser(authCheckVo.getEmpCode(), AuthConstants.LOGIN_TYPE_EMP_CODE);
		UserVo empPwd = empInfoVo.getUserVo();
		authResultVo.setEmpId(empPwd.getEmpId());
		authResultVo.setEmpCode(empPwd.getEmpCode());
		// 设置权限
		setUserRole(authResultVo);
		if (empInfoVo.getEmpVo() != null) {
			authResultVo.setOrgId(empInfoVo.getEmpVo().getOrgId());
		}
		authResultVo.setEmpCode(empInfoVo.getEmpVo().getEmpCode());
		return true;
	}

	/**
	 * 修改密码
	 * @param updPwdVo
	 * @throws GatewayException
	 * @throws Exception
	 */
	@Override
	public AuthResultVo updPwd(UpdPwdVo updPwdVo) throws Exception {
		// 校验参数是否正常
		if (updPwdVo == null || updPwdVo.getUpdateTime() == null || StringUtil.isBlank(updPwdVo.getEmpCode()) || StringUtil.isBlank(updPwdVo.getNewPassword())
				|| StringUtil.isBlank(updPwdVo.getOldPassword())) {
			throw new GatewayException(ErrorCode.SYSTEM_PARAM_ERROR, ErrorCode.SYSTEM_PARAM_ERROR_MESSGE);
		}
		AuthResultVo resultVo = new AuthResultVo();
		resultVo.setEmpCode(updPwdVo.getEmpCode());
		
		// 校验token
		AuthCheckVo authCheckVo = new AuthCheckVo();
		authCheckVo.setSystemCode(updPwdVo.getSystemCode());
		authCheckVo.setCurrentTime(new Date());
		authCheckVo.setTokenId(updPwdVo.getTokenId());
		authCheckVo.setEmpCode(updPwdVo.getEmpCode());
		authCheck(authCheckVo);
		// 校验系统是否存活
		SystemInfoVo systemInfo = authCheckSystem(updPwdVo.getSystemCode(), updPwdVo.getMachineCode(), updPwdVo.getUpdateTime());
		// 参数校验完成，进行业务校验
		validateUpdPwdAuth(updPwdVo, resultVo, systemInfo);
		// 校验成功后进行修改密码业务操作
		EmpInfoVo empInfoVo = empInfoUtil.getEmpInfoByKey(updPwdVo.getEmpCode(), AuthConstants.LOGIN_TYPE_EMP_CODE);
		UserVo userVo = empInfoVo.getUserVo();
		userVo.setOldPassword(updPwdVo.getOldPassword());
		userVo.setPassword(updPwdVo.getNewPassword());
		userVo.setUpdateEmp(empInfoVo.getEmpVo().getEmpCode());
		userVo.setUpdateOrg(empInfoVo.getEmpVo().getOrgId());
		userVo.setUpdateTime(updPwdVo.getUpdateTime());
		//2017-07-06 huangting add
		userVo.setOperatorIP(updPwdVo.getIpAddr());
		userVo.setOperatorMacAddress(updPwdVo.getMacAddr());
		userVo.setUpdateSource(AuthConstants.UPD_PWD_USER);
		userVo.setSystemCode(updPwdVo.getSystemCode());
		userVo.setPasswordStrength(updPwdVo.getPasswordStrength());
		//2017-07-06 huangting end
		boolean falg = userBiz.handleUpdateMyUserPassword(userVo);
		if (!falg) {
			throw new GatewayException(ErrorCode.UPDPWD_UPDATE_ERROR, ErrorCode.UPDPWD_UPDATE_ERROR_MESSGE);
		}
		// 删除tokenid信息
		deleteTokenId(userTokenCache.get(updPwdVo.getEmpCode()));
		// 修改密码删除用户的token
		userTokenCache.delete(updPwdVo.getEmpCode());
		
		// 修改密码成功发送消息
		sendPwdUpdateNotice(userVo,empInfoVo.getEmpVo().getYmEmpCode());
		return resultVo;
	}
	
	/**
	 * @Description: 发送修改密码消息到快运mq
	 * @param userVo
	 * @throws Exception
	 * @author huangting
	 * @date 2019年9月20日 下午2:17:47
	 */
	public void sendPwdUpdateNotice(UserVo userVo,String ymEmpCode) throws Exception {
		if (userVo == null || StringUtil.isBlank(userVo.getPassword())) {
			log.warn("没有HR工号或壹米账套的员工不推送修改密码消息" + JSONObject.toJSONString(userVo));
			return;
		}
		UserPasswordVo userPasswordVo = new UserPasswordVo();
		try {
			userPasswordVo.setUpdatePasswordTime(userVo.getUpdateTime());
			userPasswordVo.setPasswordStr(DigestUtils.md5Hex(userVo.getPassword()));
			userPasswordVo.setWorkNum(userVo.getHrEmpCode());
			//是否根据集团工号更新密码
			boolean flag = empInfoUtil.findUpPwdFlag();
			if(flag) {
				if(StringUtil.isBlank(ymEmpCode)) {
					log.warn("集团工号为空:" + JSONObject.toJSONString(userVo));
					return;
				}
				userPasswordVo.setWorkNum(ymEmpCode);
			} else {
				if(StringUtil.isBlank(userVo.getHrEmpCode())) {
					log.warn("hr工号为空:" + JSONObject.toJSONString(userVo));
					return;
				}
			}
			userPasswordVo.setCompCode(userVo.getCompCode());
			if (userVo.getEmpCode().indexOf(OmgConstants.YM_EMP_CODE_MARK) != -1) {
				userPasswordVo.setModifier(userVo.getEmpCode());
			} else {
				userPasswordVo.setModifier(userVo.getHrEmpCode() + OmgConstants.YM_EMP_CODE_MARK + userPasswordVo.getCompCode());
			}
			Map<String, Object> requestMap = new HashMap<>();
			requestMap.put("payload", userPasswordVo);
			Message msg = new Message("ucUserPasswordUpdate",JSON.toJSONString(requestMap).getBytes());
			SendResult result = mqProducer.send(msg);
			if (result == null || result.getMsgId() == null ) {
				log.error("发送修改密码消息失败,消息内容：" + FastJsonUtil.toJsonString(userPasswordVo) + "消息结果:" + FastJsonUtil.toJsonString(result));
			} else {
				if(log.isInfoEnabled()){
					log.info("发送修改密码消息,消息内容" + JSON.toJSONString(userPasswordVo) + "消息结果:" + FastJsonUtil.toJsonString(result) + ",mq生产者：" + JSON.toJSONString(mqProducer));
				}
			}
			//pwdUpdateTemplate.send(JSON.toJSONString(requestMap));
		} catch (Exception e) {
			log.error("发送修改密码消息失败,消息内容：" + FastJsonUtil.toJsonString(userPasswordVo), e);
		}
	}
	

	/**
	 * @Description: 接收快运mq消息修改密码
	 * @param userPasswordVo
	 * @throws Exception
	 * @author huangting
	 * @date 2019年9月19日 下午5:19:44
	 */
	@Override
	public boolean updPwdByMq(UserPasswordVo resetPwdVo) throws Exception {
		// 校验参数是否正常
		if (resetPwdVo == null || StringUtil.isBlank(resetPwdVo.getWorkNum())
				|| StringUtil.isBlank(resetPwdVo.getPasswordStr())) {
			log.error("更新密码失败,参数:" + JSONObject.toJSONString(resetPwdVo));
			throw new GatewayException(ErrorCode.SYSTEM_PARAM_ERROR, ErrorCode.SYSTEM_PARAM_ERROR_MESSGE);
		}
        String empCode=null;
        boolean flag = empInfoUtil.findUpPwdFlag();
        if(flag) {
            empCode = empInfoUtil.findEmpCodeByYmEmpCode(resetPwdVo.getWorkNum());
        } else {
            //根据hr工号与账套转换为empCode;
            empCode=empInfoUtil.findEmpCodeByHrEmpCode(resetPwdVo.getWorkNum(), resetPwdVo.getCompCode());
        }
		if (StringUtil.isBlank(empCode)) {
			log.error("更新密码失败,参数:" + JSONObject.toJSONString(resetPwdVo));
			throw new GatewayException(ErrorCode.USER_NONENTITY, ErrorCode.USER_NONENTITY_MESSGE);
		}
		// 验证用户是否存在
		EmpInfoVo empInfoVo = authCheckUser(empCode, AuthConstants.LOGIN_TYPE_EMP_CODE);
		UserVo user = empInfoVo.getUserVo();
		user.setPassword(resetPwdVo.getPasswordStr());
		user.setUpdateEmp(empCode);
		user.setUpdateOrg(empInfoVo.getEmpVo().getOrgId());
		user.setUpdateTime(resetPwdVo.getUpdatePasswordTime());
		user.setUpdateSource(AuthConstants.UPD_PWD_YM);
		user.setSystemCode("YinHe");
		boolean upPwdByYmEmpCodeFlag = false;
		try {
            upPwdByYmEmpCodeFlag = userBiz.handleUpdateUserPassword(user, false);
			if (!upPwdByYmEmpCodeFlag) {
				throw new GatewayException(ErrorCode.UPDPWD_UPDATE_ERROR, ErrorCode.UPDPWD_UPDATE_ERROR_MESSGE);
			}
		} catch (Exception e) {
			log.error("修改密码异常,PrimevalId:" + resetPwdVo.getPrimevalId(), e);
			throw new GatewayException(ErrorCode.UPDPWD_UPDATE_ERROR, ErrorCode.UPDPWD_UPDATE_ERROR_MESSGE);
		}
		//删除失败信息 add by zhangRb 修改密码解除锁定状态，删除失败信息 20180329
		if (empInfoUtil.findLoginFailByUsername(empCode) != null) {
			empInfoUtil.deleteLoginFail(empCode);
		}
		// 删除tokenid信息
		deleteTokenId(userTokenCache.get(empCode));
		// 找回密码删除用户的token
		userTokenCache.delete(empCode);
		return flag;
	}

	/**
	 * 找回密码
	 * @param resetPwdVo
	 * @throws GatewayException
	 * @throws Exception
	 */
	@Override
	public AuthResultVo resetPwd(ResetPwdVo resetPwdVo) throws Exception {
		// 校验参数是否正常
		if (resetPwdVo == null || resetPwdVo.getRestTime() == null 
				|| StringUtil.isBlank(resetPwdVo.getEmpCode()) 
				|| StringUtil.isBlank(resetPwdVo.getResetPwdKey()) 
				|| StringUtil.isBlank(resetPwdVo.getNewPassword())) {
			throw new GatewayException(ErrorCode.SYSTEM_PARAM_ERROR, ErrorCode.SYSTEM_PARAM_ERROR_MESSGE);
		}
		AuthResultVo resultVo = new AuthResultVo();
		// 验证用户是否存在
		EmpInfoVo empInfoVo = authCheckUser(resetPwdVo.getEmpCode(), AuthConstants.LOGIN_TYPE_MOBILE);
		UserVo user = empInfoVo.getUserVo();
		EmpVo empVo = empInfoVo.getEmpVo();
		// 校验系统是否存活
		SystemInfoVo systemInfo = null;
		if (resetPwdVo.getSystemCode() != null) {
			systemInfo = authCheckSystem(resetPwdVo.getSystemCode(), resetPwdVo.getMachineCode(), resetPwdVo.getRestTime());
		}
		// 校验重置密码唯一key
		boolean resetPwdKey = codeUtil.checkResetPwdKey(resetPwdVo.getResetPwdKey(), empVo.getEmpCode());
		if (!resetPwdKey) {
			throw new GatewayException(ErrorCode.RESET_PWD_KEY_ERROR, ErrorCode.RESET_PWD_KEY_ERROR_MESSGE);
		}
		// 新旧密码不能相同
		String newPassword = SignUtils.md5(resetPwdVo.getNewPassword(), GatewayConstants.DEFAULT_CHARSET);
		
		/**
		 *  add by 2018-05-28    crj   验证密码是否在弱密码范围    start
		 */
		OmgWeakPassword omgWeakPassword = omgWeakPasswordService.findByWeakEncryptPassword(newPassword);
		
		if(omgWeakPassword != null){
			throw new GatewayException(ErrorCode.UPDPWD_WEAK_ERROR, ErrorCode.UPDPWD_WEAK_ERROR_MESSGE);
		}
		
		/**
		 *  add by 2018-05-28    crj   验证密码是否在弱密码范围    end
		 */
		
		if (newPassword.equals(user.getPassword())) {
			throw new GatewayException(ErrorCode.UPDPWD_NEWOLD_ERROR, ErrorCode.UPDPWD_NEWOLD_ERROR_MESSGE);
		}
		
		//崔仁军  2018-03-06   FSP-1561 【SSO】关于取消SSO密码校验与前三次不同的规则     2019-03-01增加密码校验与前N次不同的规则 add by LH
		// 校验新密码是否与前N次相同
		if(historyTotal>0){
			UpdPwdItemVo updPwdItemVo = new UpdPwdItemVo();
			updPwdItemVo.setEmpId(user.getEmpId());
			updPwdItemVo.setHistoryTotal(historyTotal);
			List<UpdPwdItem> items = userBiz.findByThrDay(updPwdItemVo);
			if (items != null && items.size() > 0) {
				for (UpdPwdItem item : items) {
					if (newPassword.equals(item.getPassword())) {
						throw new GatewayException(ErrorCode.UPDPWD_THR_ERROR,ErrorCode.UPDPWD_THR_ERROR_MESSGE.replace("{}", String.valueOf(historyTotal)));
					}
				}
			}
		}
		//end  崔仁军  2018-03-06   FSP-1561 【SSO】关于取消SSO密码校验与前三次不同的规则     2019-03-01增加密码校验与前N次不同的规则 add by LH
		// 校验密码强度
		if (systemInfo != null && systemInfo.getPwForceIntensity()) {
			if (resetPwdVo.getPasswordStrength() == null || !resetPwdVo.getPasswordStrength().equals(AuthConstants.PASSWORD_STRENGTH_STRONG)) {
				throw new GatewayException(ErrorCode.UPDPWD_STRENGTH_ERROR, ErrorCode.UPDPWD_STRENGTH_ERROR_MESSGE);
			}
		}
		// 修改密码
		user.setPassword(resetPwdVo.getNewPassword());
		user.setUpdateEmp(empVo.getEmpCode());
		user.setUpdateOrg(empVo.getOrgId());
		user.setUpdateTime(resetPwdVo.getRestTime());
		//2017-07-06 huangting add
		user.setOperatorIP(resetPwdVo.getIpAddr());
		user.setOperatorMacAddress(resetPwdVo.getMacAddr());
		user.setUpdateSource(AuthConstants.UPD_PWD_RESET);
		user.setSystemCode(resetPwdVo.getSystemCode());
		user.setPasswordStrength(resetPwdVo.getPasswordStrength());
		//2017-07-06 huangting end
		boolean flag;
		try {
			flag = userBiz.handleUpdateUserPassword(user, true);
			if (!flag) {
				throw new GatewayException(ErrorCode.UPDPWD_UPDATE_ERROR, ErrorCode.UPDPWD_UPDATE_ERROR_MESSGE);
			}
		} catch (Exception e) {
			log.error("修改密码异常,员工code:" + empVo.getEmpCode(), e);
			throw new GatewayException(ErrorCode.UPDPWD_UPDATE_ERROR, ErrorCode.UPDPWD_UPDATE_ERROR_MESSGE);
		}
		//删除失败信息 add by zhangRb 修改密码解除锁定状态，删除失败信息 20180329
		if (empInfoUtil.findLoginFailByUsername(empVo.getEmpCode()) != null) {
			empInfoUtil.deleteLoginFail(empVo.getEmpCode());
		}
		// 删除tokenid信息
		deleteTokenId(userTokenCache.get(empVo.getEmpCode()));
		// 找回密码删除用户的token
		userTokenCache.delete(empVo.getEmpCode());
		// 删除验证码cache
		codeUtil.deleteCode(empVo.getEmpCode() + AuthConstants.CODE_TYPE_RTEPWD);
		// 删除重置密码唯一key cache
		codeUtil.deleteCode(CodeUtil.RESET_PWD_KEY + empVo.getEmpCode());
		resultVo.setEmpCode(empVo.getEmpCode());
		if(log.isInfoEnabled()){
			log.info("进入修改密码发送消息方法,消息内容" + JSON.toJSONString(resultVo));
		}
		// 修改密码成功发送消息
		sendPwdUpdateNotice(user,empVo.getYmEmpCode());
		return resultVo;
	}

	/**
	 * 修改密码、找回密码后，删除用户的token信息
	 * 
	 * @param strList
	 */
	private void deleteTokenId(List<String> strList) {
		if (strList == null || strList.isEmpty()) {
			return;
		}
		for (String str : strList) {
			tokenSystemCache.delete(str);
			//add by xj
			tokenUserInfoCache.delete(str);
			//add by xj
		}
	}

	/**
	 * 校验用户是否异常
	 * @param userName
	 * @param type 1=empCode;2=mobile;3=email
	 * @throws Exception
	 */
	public EmpInfoVo authCheckUser(String key, int type) throws Exception {
		EmpInfoVo empInfoVo = checkUserExists(key, type);
		EmpVo empVo = empInfoVo.getEmpVo();
		UserVo empPwd = empInfoVo.getUserVo();
		String empCode = empVo.getEmpCode();
		// 员工状态为离职
		if ((empVo.getStatus() != null && empVo.getStatus() == AuthConstants.OMG_EMP_STATUS_LEAVE_OFFICE)
				|| (empVo.getDeleteFlag() != null && empVo.getDeleteFlag())) {
			//删除用户的token
			userTokenCache.delete(empCode);
			throw new GatewayException(ErrorCode.USER_LEAVE_OFFICE, ErrorCode.USER_LEAVE_OFFICE_MESSGE);
		}
		// 用户是否被禁用
		if (!empPwd.getEnabled()) {
			//删除用户的token
			userTokenCache.delete(empCode);
			throw new GatewayException(ErrorCode.USER_DISABLE, ErrorCode.USER_DISABLE_MESSGE);
		}
		
		return empInfoVo;
	}
	
	/**
	 * 
	 * @Description:校验用户是否存在
	 * @param key
	 * @param type 1=empCode;2=mobile;3=email
	 * @return
	 * @throws Exception
	 * @author huangting
	 * @date 2017年4月11日 上午9:56:13
	 */
	public EmpInfoVo checkUserExists(String key, int type) throws Exception {
		EmpInfoVo empInfoVo = empInfoUtil.getEmpInfoByKey(key, type);
		// 用户不存在
		if (empInfoVo == null) {
			throw new GatewayException(ErrorCode.USER_NONENTITY, ErrorCode.USER_NONENTITY_MESSGE);
		}
		EmpVo empVo = empInfoVo.getEmpVo();
		UserVo empPwd = empInfoVo.getUserVo();
		if (empVo == null || empPwd == null) {
			throw new GatewayException(ErrorCode.USER_NONENTITY, ErrorCode.USER_NONENTITY_MESSGE);
		}
		return empInfoVo;
	}

	/**
	 * 校验系统是否异常
	 * @param systemCode
	 * @param machineCode
	 * @param expressTime
	 * @throws Exception
	 */
	private SystemInfoVo authCheckSystem(String systemCode, String machineCode, Date expressTime) throws Exception {
		// 校验系统是否存活
		SystemInfoVo systemInfo = systemInfoBiz.findSystemBySystemCode(systemCode);
		if (!systemCode.equals(AuthConstants.DEFAULT_SYSTEMCODE)) {
			if (systemInfo == null) {
				throw new GatewayException(ErrorCode.SYSTEM_CODE_ERROR, ErrorCode.SYSTEM_CODE_ERROR_MESSGE);
			} else if (systemInfo.getEnable() == null || systemInfo.getEnable() == AuthConstants.SYSTEM_ENABLE_NO) {
				// 系统被禁用
				throw new GatewayException(ErrorCode.SYSTEM_DISABLE, ErrorCode.SYSTEM_DISABLE_MESSGE);
			}
			// 检查系统是否必须填写机器码
			if (systemInfo.getBoundMachine() == AuthConstants.SYSTEM_BOUND_MACHINE_YES) {
				if (StringUtil.isEmpty(machineCode)) {
					// 未携带机器码
					throw new GatewayException(ErrorCode.SYSTEM_NOT_MACHINE_CODE, ErrorCode.SYSTEM_NOT_MACHINE_CODE_MESSGE);
				}
			}
			// 检查修改时间是否和系统时间相差15分钟，相差15分钟，返回提示信息
			if (expressTime != null) {
				long loginTime = expressTime.getTime();
				long nowTime = Calendar.getInstance().getTime().getTime();
				if (nowTime > (loginTime + loginExpressTime)) {
					log.info("loginTime：" + DateUtil.formatToLongDate(loginTime));
					log.info("nowTime：" + DateUtil.formatToLongDate(nowTime));
					// 登录时间不正确
					throw new GatewayException(ErrorCode.SYSTEM_UPDPWD_TIME_ERROR, ErrorCode.SYSTEM_UPDPWD_TIME_ERROR_MESSGE);
				}
			}
		}
		return systemInfo;
	}

	/**
	 * 校验修改密码参数
	 * @param updPwdVo
	 * @param authResultVo
	 * @param systemInfo
	 * @return
	 * @throws GatewayException
	 * @throws Exception
	 */
	private boolean validateUpdPwdAuth(UpdPwdVo updPwdVo, AuthResultVo resultVo, SystemInfoVo systemInfo) throws GatewayException, Exception {
		// 验证用户是否存在
		String empCode = updPwdVo.getEmpCode();
		EmpInfoVo empInfoVo = empInfoUtil.getEmpInfoByEmpCode(empCode);
		UserVo empPwd = empInfoVo.getUserVo();
		// 密码不正确
		String password = SignUtils.md5(updPwdVo.getOldPassword(), GatewayConstants.DEFAULT_CHARSET);
		if (!empPwd.getPassword().equals(password)) {
			throw new GatewayException(ErrorCode.USER_PWD_ERROR, ErrorCode.USER_PWD_ERROR_MESSGE);
		}
		// 新旧密码不能相同
		String newPassword = SignUtils.md5(updPwdVo.getNewPassword(), GatewayConstants.DEFAULT_CHARSET);
		
		/**
		 *  add by 2018-05-28    crj   验证密码是否在弱密码范围    start
		 */
		OmgWeakPassword omgWeakPassword = omgWeakPasswordService.findByWeakEncryptPassword(newPassword);
		
		if(omgWeakPassword != null){
			throw new GatewayException(ErrorCode.UPDPWD_WEAK_ERROR, ErrorCode.UPDPWD_WEAK_ERROR_MESSGE);
		}
		
		/**
		 *  add by 2018-05-28    crj   验证密码是否在弱密码范围    end
		 */
		
		if (newPassword.equals(empPwd.getPassword())) {
			throw new GatewayException(ErrorCode.UPDPWD_NEWOLD_ERROR, ErrorCode.UPDPWD_NEWOLD_ERROR_MESSGE);
		}
		//崔仁军  2018-03-06   FSP-1561 【SSO】关于取消SSO密码校验与前三次不同的规则    2019-03-01增加密码校验与前N次不同的规则 add by LH
		// 校验新密码是否与前N次相同
		if(historyTotal>0){
			UpdPwdItemVo updPwdItemVo = new UpdPwdItemVo();
			updPwdItemVo.setEmpId(empPwd.getEmpId());
			updPwdItemVo.setHistoryTotal(historyTotal);
			List<UpdPwdItem> items = userBiz.findByThrDay(updPwdItemVo);
			if (items != null && items.size() > 0) {
				for (UpdPwdItem item : items) {
					if (newPassword.equals(item.getPassword())) {
						throw new GatewayException(ErrorCode.UPDPWD_THR_ERROR,ErrorCode.UPDPWD_THR_ERROR_MESSGE.replace("{}", String.valueOf(historyTotal)));
					}
				}
			}
		}
		//end  崔仁军  2018-03-06   FSP-1561 【SSO】关于取消SSO密码校验与前三次不同的规则    2019-03-01增加密码校验与前N次不同的规则 add by LH
		// 校验密码强度
		if (systemInfo != null && systemInfo.getPwForceIntensity()) {
			if (updPwdVo.getPasswordStrength() == null || !updPwdVo.getPasswordStrength().equals(AuthConstants.PASSWORD_STRENGTH_STRONG)) {
				throw new GatewayException(ErrorCode.UPDPWD_STRENGTH_ERROR, ErrorCode.UPDPWD_STRENGTH_ERROR_MESSGE);
			}
		}
		resultVo.setEmpCode(empCode);
		return true;
	}

  /**
	* 权限检查
	* @param authCheckVo
	* @return
	* @throws GatewayException
	* @throws Exception 
	* @see cn.uce.omg.sso.biz.IAuthBiz#authCheck(cn.uce.omg.sso.vo.AuthCheckVo)
	 */
	@Override
	public AuthResultVo authCheck(AuthCheckVo authCheckVo) throws GatewayException, Exception {
		//校验请求参数
		if (authCheckVo == null || StringUtil.isBlank(authCheckVo.getTokenId())
				|| StringUtil.isBlank(authCheckVo.getEmpCode()) || StringUtil.isBlank(authCheckVo.getSystemCode())) {
			throw new GatewayException(ErrorCode.SYSTEM_PARAM_ERROR, ErrorCode.SYSTEM_PARAM_ERROR_MESSGE);
		} 
		// 校验token信息
		TokenVo tokenVo = tokenSystemCache.get(authCheckVo.getTokenId());
		if (tokenVo == null) {
			throw new GatewayException(ErrorCode.ILLEGAL_TOKEN, ErrorCode.ILLEGAL_TOKEN_MESSGE);
		}
		
		AuthResultVo authResultVo = new AuthResultVo();
		// 校验参数是否正常
		boolean isFlag = validateAuthCheckParam(authCheckVo, authResultVo);
		// 参数校验不通过
		if (!isFlag) {
			return authResultVo;
		}
		
		// 校验用户名是否正确
		checkEmpCodeIsCorrect(authCheckVo.getEmpCode(),tokenVo.getEmpCode());
		
		// 更新token系统信息
		updateTokenSystemInfo(tokenVo, authCheckVo);
		authResultVo.setTokenId(authCheckVo.getTokenId());
		authResultVo.setEmpCode(tokenVo.getEmpCode());
		if (tokenVo.getExpireTime() != null) {
			authResultVo.setExpireTime(new Date(tokenVo.getExpireTime()));
		}
		return authResultVo;
	}
	

	/**
	 * 方法的描述：
	 * <pre>
	 * 扫码登录补全数据
	 * </pre>
	 * @param loginVo
	 * @param authResultVo
	 * @throws Exception
	 * @author LH
	 * @email luhenguce@uce.cn
	 * @date 2019年7月9日下午2:47:37
	 */
	@Override
	public void subParamForLoginCode(LoginVo loginVo,AuthResultVo authResultVo) throws Exception{
		// 校验参数是否正常
		if (loginVo == null || StringUtil.isBlank(loginVo.getSystemCode()) || loginVo.getLoginTime() == null) {
			throw new GatewayException(ErrorCode.SYSTEM_PARAM_ERROR, ErrorCode.SYSTEM_PARAM_ERROR_MESSGE);
		}
		// 校验系统是否存活
		SystemInfoVo systemInfo = authCheckSystem(loginVo.getSystemCode(), loginVo.getMachineCode(), loginVo.getLoginTime());
		// 获取超时时间
		Date expireTime = getExpireTime(systemInfo);
		if (expireTime == null) {
			throw new GatewayException(ErrorCode.SYSTEM_CODE_ERROR, ErrorCode.SYSTEM_CODE_ERROR_MESSGE);
		}
		//补充empName, orgId, UpdPwdFlag, UpdPwdIntervalDay
		validateLoginAuthForLoginCode(loginVo, authResultVo, systemInfo);
		// 根据系统信息是否使用统一角色配置设置权限
		if (systemInfo != null ) {
			if (systemInfo.getUnifyRoleFlag() != null && systemInfo.getUnifyRoleFlag()) {
				setUserRole(authResultVo);
			}
			// 2、获取token
			//add by xj
			//String encryptTokenId = putTokenToCache(loginVo,authResultVo.getEmpCode(), systemInfo.getSecretKey(), expireTime);
			String encryptTokenId = putTokenToCache(loginVo,authResultVo.getEmpCode(),authResultVo.getOrgId(), systemInfo.getSecretKey(), expireTime);
			//add by xj
			authResultVo.setTokenId(encryptTokenId);
		}
		authResultVo.setExpireTime(expireTime);
	}
	
	/**
	 * 验证登录参数是否正确
	 * @param loginVo
	 * @param authResultVo
	 * @param systemInfoVo
	 * @return
	 * @throws Exception
	 */
	private boolean validateLoginAuthForLoginCode(LoginVo loginVo, AuthResultVo authResultVo, SystemInfoVo systemInfoVo) throws GatewayException, Exception {
		// 验证用户是否存在
		String userName = loginVo.getUserName();
		EmpInfoVo empInfoVo = authCheckUser(userName, AuthConstants.LOGIN_TYPE_EMP_CODE);
		UserVo user = empInfoVo.getUserVo();
		EmpVo emp = empInfoVo.getEmpVo();
		String empCode = emp.getEmpCode();
		
		Integer orgId = null;
		Integer cmsOrgId = null;
		//add by BaoJingyu 201803012 begin
		//FSP-1564【SSO】关于login接口返回参数增加三个字段：机构类型、机构名称，是否为负责人
		authResultVo.setPrincipalFlag(emp.getPrincipalFlag());
		OrgVo orgVo = orgBiz.findOrgById(emp.getOrgId());
		if (orgVo != null) {
			authResultVo.setOrgName(orgVo.getOrgName());
			authResultVo.setOrgType(orgVo.getOrgType());
		}
		loginVo.setCompCode(user.getCompCode());
		loginVo.setYhUserName(user.getYhEmpCode());
		//add by BaoJingyu 201803012 end
		//校验用户是否绑定乾坤员工,没有绑定不允许登录
		List<OtherEmpRelationVo> otherEmpRelationList = empBiz.findQkEmpRelationByEmpId(emp.getEmpId());

		//判断是否需要判断该系统是否需要验证绑定乾坤ID --add by 崔仁军  2017-10-16
        if((otherEmpRelationList == null || otherEmpRelationList.size() == 0) && systemInfoVo.getRelCheckFlag()
        		&& empCode.indexOf(OmgConstants.YM_EMP_CODE_MARK) <= 0){
        	throw new GatewayException(ErrorCode.USER_UNBOUND_CMS_EMP, ErrorCode.USER_UNBOUND_CMS_EMP_MESSGE);
		}
		//绑定的乾坤机构（包括主机构和兼岗机构）
        List<Integer> otherOrgs = new ArrayList<Integer>();
		StringBuffer otherOrgIdStr = new StringBuffer();
		//sonar扫描阻碍BUG修正 zhangRb 20170904
		if (otherEmpRelationList != null && otherEmpRelationList.size() > 0) {
			for (int i = 0; i < otherEmpRelationList.size(); i++) {
				if (i == otherEmpRelationList.size() - 1) {
					otherOrgIdStr = otherOrgIdStr.append(otherEmpRelationList.get(i).getOtherOrgId());
				} else {
					otherOrgIdStr = otherOrgIdStr.append(otherEmpRelationList.get(i).getOtherOrgId()).append("#");
				}
				otherOrgs.add(otherEmpRelationList.get(i).getOtherOrgId());
			}
			//开启'是否校验员工绑定'才校验乾坤机构的状态
			if (systemInfoVo.getRelCheckFlag()) {
				//是否是停用机构标记
				boolean disableFlag = true;
				//查询绑定的乾坤机构（主机构以及兼岗机构）
				List<CmsOrgVo> cmsOrgIds = cmsOrgService.findByCmsOrgIds(otherOrgs);
				/**
				 * 1，验证该登录员工的机构（主机构及兼岗机构）是否都是停用状态，
				 * 2，如果所有的机构（主机构及兼岗机构）都是停用状态则不改员工不让登录，
				 * 3，只要有一个机构不是停用状态都可以登录
				 */
				for(int i = 0 ,j = cmsOrgIds.size() ; i < j ; i++){
					if(OmgConstants.CMS_ORG_STATUS_ENABLE == cmsOrgIds.get(i).getStatus().intValue() ||
							OmgConstants.CMS_ORG_STATUS_PAUSE == cmsOrgIds.get(i).getStatus().intValue()){
						disableFlag = false;
					}
				}
				//如果所有机构都是停用状态，则不允许登录返回错误信息与编码
				if(disableFlag){
					throw new GatewayException(ErrorCode.USER_DISABLE_CMS_EMP, ErrorCode.USER_DISABLE_CMS_EMP_MESSGE);
				}
			}
		}
		
		//如果员工主机构未绑定乾坤机构，则取绑定关系中第一个乾坤机构id
		if (!CollectionUtils.isEmpty(otherEmpRelationList)) {
			//cmsOrgId = otherEmpRelationList.get(0).getOtherOrgId();
			//add by xj
			for(OtherEmpRelationVo otherEmpRelationVo : otherEmpRelationList){
				if(otherEmpRelationVo.getOrgId().equals(emp.getOrgId())){
					orgId = otherEmpRelationVo.getOrgId();
					cmsOrgId = otherEmpRelationVo.getOtherOrgId();
				}
			}
			if(cmsOrgId == null){
				orgId = otherEmpRelationList.get(0).getOrgId();
				cmsOrgId = otherEmpRelationList.get(0).getOtherOrgId();
			}
		}
		if(orgId == null) {
			authResultVo.setOrgId(emp.getOrgId());
		} else {
			authResultVo.setOrgId(orgId);
		}
		authResultVo.setOtherOrgId(cmsOrgId);
		authResultVo.setOtherOrgIdStr(otherOrgIdStr.toString());
		authResultVo.setUpdPwdFlag(false);
		authResultVo.setUpdPwdIntervalDay(-1);
		//初次登录提示修改密码
		if (user.getPwdUpdateTime() == null) {
			authResultVo.setUpdPwdFlag(true);
			authResultVo.setUpdPwdIntervalDay(0);
		} else {
			//校验当前日期是否超过强制修改密码间隔天数
			long day = DateUtil.getNaturalDayBetween(user.getPwdUpdateTime(), new Date());
			if (day > updPwdIntervalTime) {
				authResultVo.setUpdPwdFlag(true);
				authResultVo.setUpdPwdIntervalDay((int)day);
			}
		}

		authResultVo.setEmpId(emp.getEmpId());
		authResultVo.setEmpName(emp.getEmpName());
		authResultVo.setEmpCode(empCode);
		//modify by zhangRb 20180828 返回结果设置userName为工号
		authResultVo.setUserName(empCode);
		authResultVo.setLastLoginTime(user.getLastLoginTime());
		return true;
	}
	
	/**
	 * 校验用户名是否正确
	 * @param empCode
	 * @param tokenEmpCode
	 * @throws GatewayException
	 * @author huangting
	 * @date 2017年6月8日 下午2:36:30
	 */
	private void checkEmpCodeIsCorrect(String empCode, String tokenEmpCode) throws GatewayException {
		// 校验用户名是否正确
		if (!empCode.equals(tokenEmpCode)) {
			throw new GatewayException(ErrorCode.TOKEN_UNMATCH_USER, ErrorCode.TOKEN_UNMATCH_USER_MESSGE);
		}
	}

	/**
	 * 验证登录参数是否正确
	 * @param loginVo
	 * @param authResultVo
	 * @param systemInfoVo
	 * @return
	 * @throws Exception
	 */
	private boolean validateLoginAuth(LoginVo loginVo, AuthResultVo authResultVo, SystemInfoVo systemInfoVo,Boolean checkFlag) throws GatewayException, Exception {
		// 验证用户是否存在
		String userName = loginVo.getUserName();
		EmpInfoVo empInfoVo = authCheckUser(userName, AuthConstants.LOGIN_TYPE_EMP_CODE);
		UserVo user = empInfoVo.getUserVo();
		EmpVo emp = empInfoVo.getEmpVo();
		String empCode = emp.getEmpCode();
		//根据系统中的标记来判断该系统是否需要验证绑定乾坤ID，以前通过开关来判断验证绑定乾坤ID的方式取消   --add by 崔仁军  2017-10-16
		//add by zhangRb 20170829 登录校验员工绑定关系开关
		//List<SysTypeVo> sysTypeVos = sysTypeBiz.findSysTypeByClassCode(OmgConstants.SYS_TYPE_EMP_REL_CHECK_SWITCH);
		//根据SystemCode查询系统信息  add by 崔仁军  2017-10-16
		//delete by zhangRb 系统信息不重新再取一次
		//SystemInfoVo systemInfoVo = systemInfoBiz.findSystemBySystemCode(loginVo.getSystemCode());
		Integer orgId = null;
		Integer cmsOrgId = null;
		//add by BaoJingyu 201803012 begin
		//FSP-1564【SSO】关于login接口返回参数增加三个字段：机构类型、机构名称，是否为负责人
		authResultVo.setPrincipalFlag(emp.getPrincipalFlag());
		OrgVo orgVo = orgBiz.findOrgById(emp.getOrgId());
		if (orgVo != null) {
			authResultVo.setOrgName(orgVo.getOrgName());
			authResultVo.setOrgType(orgVo.getOrgType());
		}
		loginVo.setYhUserName(user.getYhEmpCode());
		loginVo.setCompCode(user.getCompCode());
		//add by BaoJingyu 201803012 end
		//校验用户是否绑定乾坤员工,没有绑定不允许登录
		List<OtherEmpRelationVo> otherEmpRelationList = empBiz.findQkEmpRelationByEmpId(emp.getEmpId());
		//根据系统中的标记来判断该系统是否需要验证绑定乾坤ID，以前通过开关来判断验证绑定乾坤ID的方式取消   --add by 崔仁军  2017-10-16
		//modify by zhangRb 20170829  添加登录校验员工绑定关系开关
		/*if ((otherEmpRelationList == null || otherEmpRelationList.size() == 0) && 
			(!CollectionUtils.isEmpty(sysTypeVos) && "ON".equals(sysTypeVos.get(0).getTypeName()))) {
			throw new GatewayException(ErrorCode.USER_UNBOUND_CMS_EMP, ErrorCode.USER_UNBOUND_CMS_EMP_MESSGE);
		}*/
		//判断是否需要判断该系统是否需要验证绑定乾坤ID --add by 崔仁军  2017-10-16
        if((otherEmpRelationList == null || otherEmpRelationList.size() == 0) && systemInfoVo.getRelCheckFlag() 
        		&& StringUtils.equals(UserAccountType.Portal.name(), loginVo.getUserAccountType())){
        	throw new GatewayException(ErrorCode.USER_UNBOUND_CMS_EMP, ErrorCode.USER_UNBOUND_CMS_EMP_MESSGE);
		}
		//绑定的乾坤机构（包括主机构和兼岗机构）
        List<Integer> otherOrgs = new ArrayList<Integer>();
		StringBuffer otherOrgIdStr = new StringBuffer();
		//sonar扫描阻碍BUG修正 zhangRb 20170904
		if (otherEmpRelationList != null && otherEmpRelationList.size() > 0) {
			for (int i = 0; i < otherEmpRelationList.size(); i++) {
				if (i == otherEmpRelationList.size() - 1) {
					otherOrgIdStr = otherOrgIdStr.append(otherEmpRelationList.get(i).getOtherOrgId());
				} else {
					otherOrgIdStr = otherOrgIdStr.append(otherEmpRelationList.get(i).getOtherOrgId()).append("#");
				}
				otherOrgs.add(otherEmpRelationList.get(i).getOtherOrgId());
			}
			//开启'是否校验员工绑定'才校验乾坤机构的状态
			if (systemInfoVo.getRelCheckFlag()) {
				//是否是停用机构标记
				boolean disableFlag = true;
				//查询绑定的乾坤机构（主机构以及兼岗机构）
				List<CmsOrgVo> cmsOrgIds = cmsOrgService.findByCmsOrgIds(otherOrgs);
				/**
				 * 1，验证该登录员工的机构（主机构及兼岗机构）是否都是停用状态，
				 * 2，如果所有的机构（主机构及兼岗机构）都是停用状态则不改员工不让登录，
				 * 3，只要有一个机构不是停用状态都可以登录
				 */
				for(int i = 0 ,j = cmsOrgIds.size() ; i < j ; i++){
					if(OmgConstants.CMS_ORG_STATUS_ENABLE == cmsOrgIds.get(i).getStatus().intValue() ||
							OmgConstants.CMS_ORG_STATUS_PAUSE == cmsOrgIds.get(i).getStatus().intValue()){
						disableFlag = false;
					}
				}
				//如果所有机构都是停用状态，则不允许登录返回错误信息与编码
				if(disableFlag){
					throw new GatewayException(ErrorCode.USER_DISABLE_CMS_EMP, ErrorCode.USER_DISABLE_CMS_EMP_MESSGE);
				}
			}
		}
		
		//如果员工主机构未绑定乾坤机构，则取绑定关系中第一个乾坤机构id
		if (!CollectionUtils.isEmpty(otherEmpRelationList)) {
			//cmsOrgId = otherEmpRelationList.get(0).getOtherOrgId();
			//add by xj
			for(OtherEmpRelationVo otherEmpRelationVo : otherEmpRelationList){
				if(otherEmpRelationVo.getOrgId().equals(emp.getOrgId())){
					orgId = otherEmpRelationVo.getOrgId();
					cmsOrgId = otherEmpRelationVo.getOtherOrgId();
				}
			}
			if(cmsOrgId == null){
				orgId = otherEmpRelationList.get(0).getOrgId();
				cmsOrgId = otherEmpRelationList.get(0).getOtherOrgId();
			}
			//add by xj
		}
		
		//add by xj
		if(orgId == null) {
			authResultVo.setOrgId(emp.getOrgId());
		} else {
			authResultVo.setOrgId(orgId);
		}
		//add by xj
		authResultVo.setOtherOrgId(cmsOrgId);
		authResultVo.setOtherOrgIdStr(otherOrgIdStr.toString());
		// modify by majun 20190622 增加手机验证码登录 begin
		LoginFailVo loginFail = null;
		if (checkFlag != null) {
			if (StringUtils.isEmpty(loginVo.getLoginType())) {
				loginVo.setLoginType(LoginType.Password.name());
			}
			if (StringUtils.equals(LoginType.Password.name(), loginVo.getLoginType())) {
				// 密码登录验证
				loginFail = empInfoUtil.checkUsrPwd(loginVo, empInfoVo,checkFlag);
			} else if (StringUtils.equals(LoginType.Captcha.name(), loginVo.getLoginType())) {
				// 验证码登录
				loginFail = empInfoUtil.checkCaptcha(loginVo, empInfoVo);
			} else {
				throw new GatewayException(ErrorCode.LOGIN_TYPE_NOT_EXISTS, ErrorCode.LOGIN_TYPE_NOT_EXISTS_MESSGE);
			}
			// modify by majun 20190622 增加手机验证码登录 end
		}
		
				
		authResultVo.setUpdPwdFlag(false);
		authResultVo.setUpdPwdIntervalDay(-1);
		//初次登录提示修改密码
		if (user.getPwdUpdateTime() == null) {
			authResultVo.setUpdPwdFlag(true);
			authResultVo.setUpdPwdIntervalDay(0);
		} else {
			//校验当前日期是否超过强制修改密码间隔天数
			long day = DateUtil.getNaturalDayBetween(user.getPwdUpdateTime(), new Date());
			if (day > updPwdIntervalTime) {
				authResultVo.setUpdPwdFlag(true);
				authResultVo.setUpdPwdIntervalDay((int)day);
			}
		}
		//add by xj
		//authResultVo.setOrgId(emp.getOrgId());
		//add by xj
		authResultVo.setEmpId(emp.getEmpId());
		authResultVo.setEmpName(emp.getEmpName());
		authResultVo.setEmpCode(empCode);
		//modify by zhangRb 20180828 返回结果设置userName为工号
		//authResultVo.setUserName(userName);
		authResultVo.setUserName(empCode);
		authResultVo.setLastLoginTime(user.getLastLoginTime());
		if (loginFail != null) {
			// 验证通过，删除失败信息
			empInfoUtil.deleteLoginFail(empCode);
			//如果存在登陆失败信息，并且登陆失败次数 > 最多密码输错次数，则表示用户是锁定状态
			if (loginFail.getFailCount() >= failLoginNum) {
				//add by zhangRb 20170713 登录成功，删除用户锁定状态
				userBiz.updateUserLockState(empCode, false);
			}
		}  
		return true;
	}

	/**
	 * 获取token信息并设置到redis
	 * @param loginVo
	 * @param expireTime
	 * @return
	 * @throws Exception
	 */
	private String putTokenToCache(LoginVo loginVo,String empCode,Integer orgId, String systemKey, Date expireTime) throws Exception {
		UUID uuid = UUID.randomUUID();
		String charset = GatewayConstants.DEFAULT_CHARSET;
		String tokenId = DigestUtils.md5Hex(uuid.toString().getBytes(charset));
		// 对tokenId加密
		String encyptTokenId = encryptTokenIdBySystemKey(tokenId, systemKey);
		// 保存token--系统信息
		TokenVo tokenVo = new TokenVo();
		tokenVo.setEmpCode(empCode);
		tokenVo.setExpireTime(addReservedExpireTime(expireTime));
		tokenVo.setSystemMap(new HashMap<String, SystemInfoVo>());
		SystemInfoVo systemInfo = new SystemInfoVo();
		systemInfo.setSystemCode(loginVo.getSystemCode());
		systemInfo.setCreateTime(new Date());
		tokenVo.getSystemMap().put(loginVo.getSystemCode(), systemInfo);
		tokenSystemCache.put(encyptTokenId, tokenVo);
		// 保存用户--token信息
		List<String> tokenList = userTokenCache.get(empCode);
		if (tokenList == null) {
			tokenList = new ArrayList<String>();
		}
		tokenList.add(encyptTokenId);
		userTokenCache.put(empCode, tokenList);
		PortalCurrentUser userInfo =  portalBiz.findCurrentUser(empCode);
		//没有绑定关系用户信息查询
		if (userInfo == null) {
			userInfo =  portalBiz.findCurrentUserNoEmpRelation(empCode);
		}
		if(userInfo != null){
			userInfo.setYhEmpCode(loginVo.getYhUserName());
			userInfo.setCompCode(loginVo.getCompCode());
			userInfo.setToken(tokenId);
			if (userInfo.getOrgRefRel() != null && userInfo.getOrgRefRel().size() > 0) {
				boolean isMainOrg = false;
				for(Map<String,Object> loginEmpOrgRel : userInfo.getOrgRefRel()){
					if(orgId.equals((Integer)loginEmpOrgRel.get("orgId"))){
						isMainOrg = true;
//						userInfo.setExpireTime(addReservedExpireTime(expireTime));
						userInfo.setOrgId((Integer) loginEmpOrgRel.get("orgId"));
						userInfo.setOrgCode((String) loginEmpOrgRel.get("orgCode"));
						userInfo.setOrgType((Integer) loginEmpOrgRel.get("orgType"));
						userInfo.setOrgName((String) loginEmpOrgRel.get("orgName"));
						userInfo.setCmsOrgId(loginEmpOrgRel.get("otherOrgId").toString());
						userInfo.setCmsBaseOrgCode((String) loginEmpOrgRel.get("otherBaseOrgCode"));
						userInfo.setCmsOrgType((Integer) loginEmpOrgRel.get("otherOrgType"));
						userInfo.setCmsOrgName((String) loginEmpOrgRel.get("otherOrgName"));
					}
				}
				if(!isMainOrg){
					Map<String,Object> loginEmpOrgRel = userInfo.getOrgRefRel().get(0);
					userInfo.setOrgId((Integer) loginEmpOrgRel.get("orgId"));
					userInfo.setOrgCode((String) loginEmpOrgRel.get("orgCode"));
					userInfo.setOrgType((Integer) loginEmpOrgRel.get("orgType"));
					userInfo.setOrgName((String) loginEmpOrgRel.get("orgName"));
					userInfo.setCmsOrgId(loginEmpOrgRel.get("otherOrgId").toString());
					userInfo.setCmsBaseOrgCode((String) loginEmpOrgRel.get("otherBaseOrgCode"));
					userInfo.setCmsOrgType((Integer) loginEmpOrgRel.get("otherOrgType"));
					userInfo.setCmsOrgName((String) loginEmpOrgRel.get("otherOrgName"));
				}
			}
			tokenUserInfoCache.put(encyptTokenId, userInfo);
		} else {
			log.error("未找到用户信息,empCode:" + empCode);
			throw new GatewayException(ErrorCode.USER_NONENTITY, ErrorCode.USER_NONENTITY_MESSGE);
		}
		return encyptTokenId;
	}

	/**
	 * 对tokenid进行加密
	 * @param tokenId
	 * @param systemKey
	 * @return
	 * @throws Exception
	 */
	private String encryptTokenIdBySystemKey(String tokenId, String systemKey) throws Exception {
		DesUtil desUtil = new DesUtil(systemKey);
		String encryptTokenId = desUtil.encypt(tokenId);
		return encryptTokenId;
	}

	/**
	 * 解密token
	 * @param encryptTokenId 加密token
	 * @param systemCode 系统编码
	 * @return
	 */
	public String decryptTokenIdBySystemCode(String encryptTokenId, String systemCode) throws Exception {
		SystemInfoVo systemInfo = systemInfoBiz.findSystemBySystemCode(systemCode);
		if (systemInfo == null) {
			return null;
		}
		DesUtil desUtil = new DesUtil(systemInfo.getSecretKey());
		String sourceTokenId = desUtil.decypt(encryptTokenId);
		return sourceTokenId;
	}

	/**
	 * 更新令牌超时时间
	 * @param tokenVo
	 * @param authCheckVo
	 * @throws Exception 
	 */
	private void updateTokenSystemInfo(TokenVo tokenVo, AuthCheckVo authCheckVo) throws Exception {
		// 更新系统信息
		Map<String, SystemInfoVo> systemMap = tokenVo.getSystemMap();
		SystemInfoVo systemInfoVo = systemMap.get(authCheckVo.getSystemCode());
		if (systemInfoVo != null) {
			systemInfoVo.setUpdateTime(new Date());
		} else {
			systemInfoVo = new SystemInfoVo();
			systemInfoVo.setSystemCode(authCheckVo.getSystemCode());
			systemInfoVo.setCreateTime(new Date());			
			systemMap.put(authCheckVo.getSystemCode(), systemInfoVo);
		}
		tokenVo.setExpireTime(getExpireTime(systemInfoVo, new Date()));
		tokenSystemCache.put(authCheckVo.getTokenId(), tokenVo);
	}


	/**
	 * 获取系统超时时间
	 * @return
	 * @throws Exception
	 */
	private Date getExpireTime(SystemInfoVo systemInfo) throws Exception {
		// 单位：秒
		int timeout;
		if (systemInfo == null) {
			return null;
		}
		// 超时时间没有配置，使用配置的默认超时时间
		if (systemInfo.getTimeOut() == null) {
			timeout = defaultLoginTimeout;
		} else {
			timeout = systemInfo.getTimeOut();
		}
		Date now = Calendar.getInstance().getTime();
		Date expireTime = DateUtil.addSecond(now, timeout);
		return expireTime;
	}
	
	/**
	 * 获取系统超时时间
	 * @return
	 * @throws Exception
	 */
	private Long getExpireTime(SystemInfoVo systemInfo,Date operateTime) throws Exception {
		// 单位：秒
		int timeout;
		if (systemInfo == null || operateTime == null) {
			return null;
		}
		// 超时时间没有配置，使用配置的默认超时时间
		if (systemInfo.getTimeOut() == null) {
			timeout = defaultLoginTimeout;
		} else {
			timeout = systemInfo.getTimeOut();
		}
		Date expireTime = DateUtil.addSecond(operateTime, timeout);
		return expireTime.getTime();
	}

  /**
	*  发送验证码
	* @param expCodeVo
	* @throws Exception 
	* @see cn.uce.omg.sso.biz.IAuthBiz#getCode(cn.uce.omg.sso.vo.ExpCodeVo)
	*/
	@Override
	public void getCode(ExpCodeVo expCodeVo) throws Exception {
		// 校验参数是否正常
		if (expCodeVo == null || expCodeVo.getSendTime() == null || StringUtil.isBlank(expCodeVo.getMobile())) {
			throw new GatewayException(ErrorCode.SYSTEM_PARAM_ERROR, ErrorCode.SYSTEM_PARAM_ERROR_MESSGE);
		}
		if (expCodeVo.getSystemCode() != null) {
			authCheckSystem(expCodeVo.getSystemCode(), expCodeVo.getMachineCode(), expCodeVo.getSendTime());
		}
		// 校验用户
		EmpInfoVo empInfoVo = authCheckUser(expCodeVo.getMobile(), AuthConstants.LOGIN_TYPE_MOBILE);
		EmpVo empVo = empInfoVo.getEmpVo();
		String code = codeUtil.createCode();
		if (log.isInfoEnabled()) {
			log.info("验证码是：" + code);
		}
		// 调用短信平台发送验证码
	    SmsVo smsVo = new SmsVo();
        smsVo.setEmpCode(empVo.getEmpCode());
        smsVo.setEmpOrg(empVo.getOrgId());
		smsVo.setMobile(empVo.getMobile());
		// modify by majun 20190622 增加手机验证码登录 begin
//		smsVo.setContent(smsTemplate.replace("{0}", code));
		String content = null;
		if (AuthConstants.CODE_TYPE_LOGIN.equals(expCodeVo.getCodeType())) {
			content = smsLoginTemplate.replace("{0}", code);
			if (!StringUtils.isEmpty(expCodeVo.getSystemCode())) {
				content = content.replace("{1}", expCodeVo.getSystemCode());
			} else {
				content = content.replace("{1}", "");
			}
			smsVo.setContent(content);
		} else {
			content = smsTemplate.replace("{0}", code);
			/** add by zhangRb 20171020 首次登录设置登录密码  Start*/
			if (expCodeVo.getInitPw() != null && expCodeVo.getInitPw()) {
				smsVo.setContent(content.replace("{1}", SmsConstants.SMS_SET_LOGIN_PASSWORD));
			}else {
				smsVo.setContent(content.replace("{1}", SmsConstants.SMS_GET_BACK_LOGIN_PASSWORD));
			}
		}
		// modify by majun 20190622 增加手机验证码登录 end
		MessageRespVo resp =  smsUtil.sendMessage(smsVo);
		if (resp == null || (StringUtil.isNotBlank(resp.getReturnCode()) && !resp.getReturnCode().equals("0"))) {
			if (resp != null) {
				log.error("调用发送短信接口异常,请求参数:"+JSON.toJSONString(smsVo) + "，返回参数:" + JSON.toJSONString(resp));
			}
			throw new GatewayException(ErrorCode.CODE_SENDCODE_ERROR, ErrorCode.CODE_SENDCODE_ERROR_MESSGE);
		}
		/*if (httpResponse != null &&
				((httpResponse.getIsSuccess() != null && !httpResponse.getIsSuccess()) 
						|| (httpResponse.getRmList() != null && httpResponse.getRmList().size() > 0 && !"success".equals(httpResponse.getRmList().get(0).getIsSuccess())))) {
			throw new GatewayException(ErrorCode.CODE_SENDCODE_ERROR, ErrorCode.CODE_SENDCODE_ERROR_MESSGE);
		}*/
		codeUtil.saveCode(code, empVo.getEmpCode(), expCodeVo.getCodeType());
	}

  /**
	* 校验验证码
	* @param expCodeVo
	* @return
	* @throws Exception 
	* @see cn.uce.omg.sso.biz.IAuthBiz#checkCode(cn.uce.omg.sso.vo.ExpCodeVo)
	 */
	@Override
	public String checkCode(ExpCodeVo expCodeVo) throws Exception {
		// 校验参数是否正常
		if (expCodeVo == null || expCodeVo.getSendTime() == null || StringUtil.isBlank(expCodeVo.getEmpCode())) {
			throw new GatewayException(ErrorCode.SYSTEM_PARAM_ERROR, ErrorCode.SYSTEM_PARAM_ERROR_MESSGE);
		}
		if (expCodeVo.getSystemCode() != null) {
			authCheckSystem(expCodeVo.getSystemCode(), expCodeVo.getMachineCode(), expCodeVo.getSendTime());
		}
		// 校验用户
		EmpInfoVo empInfoVo = authCheckUser(expCodeVo.getEmpCode(), AuthConstants.LOGIN_TYPE_MOBILE);
		String empCode = empInfoVo.getEmpVo().getEmpCode();
		boolean result = codeUtil.checkCode(expCodeVo.getCode() ,empCode, expCodeVo.getCodeType());
		String resetPwdKey = null;
		if (result){
			resetPwdKey = codeUtil.saveResetPwdKey(empCode);
		}
		return resetPwdKey;
	}

	/**
	 * 添加预留超时时间
	 * @return
	 * @throws Exception
	 */
	private Long addReservedExpireTime(Date expireTime) throws Exception {
		// 单位：秒
		long reservedExpireTime = expireTime.getTime() + reservedLoginTimeout;
		return reservedExpireTime;
	}

	/**
	 * 设置用户权限
	 * @param authResultVo
	 * @throws Exception
	 */
	private void setUserRole(AuthResultVo authResultVo) throws Exception {
		if (authResultVo == null || authResultVo.getEmpCode() == null) {
			return;
		}
		UserRoleRelVo userRoleRelVo = new UserRoleRelVo();
		userRoleRelVo.setEmpCode(authResultVo.getEmpCode());
		List<UserRoleRelVo> userRoleRelList = roleBiz.findUserRoleRelByCondition(userRoleRelVo);
		if (userRoleRelList != null && !userRoleRelList.isEmpty()) {
			authResultVo.setUserRoleList(getUserRoleRel(userRoleRelList));
		}
	}

	/**
	 * 获取需要发送给第三方系统的角色关系信息
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private List<UserRoleVo> getUserRoleRel(List<UserRoleRelVo> userRoleRelList) throws Exception {
		if (userRoleRelList == null || userRoleRelList.isEmpty()) {
			return null;
		}
		List<UserRoleVo> userRoleList = new ArrayList<UserRoleVo>();
		for (UserRoleRelVo userRoleRelVo : userRoleRelList) {
			UserRoleVo userRoleVo = new UserRoleVo();
			userRoleVo.setRoleCode(userRoleRelVo.getRoleCode());
			userRoleList.add(userRoleVo);
		}
		return userRoleList;
	}

	/**
	 * 发送登陆成功信息
	 * @throws Exception
	 */
	public void sendLoginMqNotice(AuthResultVo authResultVo, LoginVo loginVo) throws Exception {
		try {
			// 验证通过，记录登录日志
			LoginItemVo loginItem = new LoginItemVo();
			// 设置ip地址
			loginItem.setIpAddr(loginVo.getIpAddr());
			// 设置机器码
			loginItem.setMachineCode(loginVo.getMachineCode());
			//设置操作时间
			loginItem.setOperateTime(new Date());
			//设置操作类型
			loginItem.setOperateType(AuthConstants.OPERATE_TYPE_LOGIN);
			//设置系统编码
			loginItem.setSystemCode(loginVo.getSystemCode());
			//设置创建时间
			loginItem.setCreateTime(new Date());
			//设置员工id
			loginItem.setEmpId(authResultVo.getEmpId());
			//设置用户名
			loginItem.setUserName(authResultVo.getUserName());
			loginItem.setEmpCode(authResultVo.getEmpCode());
			loginSuccessTemplate.send(FastJsonUtil.toJsonString(loginItem));
		} catch (Exception e) {
			log.error("发送登陆成功消息失败", e);
		}
	}

	/**
	 * 发送登陆异常信息：预留（只有锁定账号才进行发送）
	 * @throws Exception
	 */
	public void sendLoginErrorNotice() throws Exception {
		// sonar检查需要方法体有代码或者注释
	}

	/**
	 * 解锁用户登陆锁定状态
	 * @param empCode
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean unLock(String empCode) throws Exception {
		// 验证通过，删除失败信息
		empInfoUtil.deleteLoginFail(empCode);
		//更新数据库中对应用户的登陆锁定状态
		Boolean result = userBiz.updateUserLockState(empCode,false);
		return result;
	}
	
	/**
	 * @param defaultLoginTimeout the defaultLoginTimeout to set
	 */
	public void setDefaultLoginTimeout(Integer defaultLoginTimeout) {
		this.defaultLoginTimeout = defaultLoginTimeout;
	}

	/**
	 * @param reservedLoginTimeout the reservedLoginTimeout to set
	 */
	public void setReservedLoginTimeout(Integer reservedLoginTimeout) {
		this.reservedLoginTimeout = reservedLoginTimeout;
	}

	/**
	 * @param tokenSystemCache the tokenSystemCache to set
	 */
	public void setTokenSystemCache(
			HashRedisWithFieldExpireCache<TokenVo> tokenSystemCache) {
		this.tokenSystemCache = tokenSystemCache;
	}

	/**
	 * @param userTokenCache the userTokenCache to set
	 */
	public void setUserTokenCache(BoundHashRedisSupport<List<String>> userTokenCache) {
		this.userTokenCache = userTokenCache;
	}

	/**
	 * @param systemInfoBiz the systemInfoBiz to set
	 */
	public void setSystemInfoBiz(ISystemInfoBiz systemInfoBiz) {
		this.systemInfoBiz = systemInfoBiz;
	}

	/**
	 * @param empInfoUtil the empInfoUtil to set
	 */
	public void setEmpInfoUtil(EmpInfoUtil empInfoUtil) {
		this.empInfoUtil = empInfoUtil;
	}

	/**
	 * @param userBiz the userBiz to set
	 */
	public void setUserBiz(IUserBiz userBiz) {
		this.userBiz = userBiz;
	}

	/**
	 * @param codeUtil the codeUtil to set
	 */
	public void setCodeUtil(CodeUtil codeUtil) {
		this.codeUtil = codeUtil;
	}
	/**
	 * @param roleBiz the roleBiz to set
	 */
	public void setRoleBiz(IRoleBiz roleBiz) {
		this.roleBiz = roleBiz;
	}

	/**
	 * @param smsUtil the smsUtil to set
	 */
	public void setSmsUtil(SmsUtil smsUtil) {
		this.smsUtil = smsUtil;
	}
	/**
	 * @param smsTemplate the smsTemplate to set
	 */
	public void setSmsTemplate(String smsTemplate) {
		this.smsTemplate = smsTemplate;
	}
	
	/**
	 * @param failLoginNum the failLoginNum to set
	 */
	public void setFailLoginNum(int failLoginNum) {
		this.failLoginNum = failLoginNum;
	}
	
	/**
	 * @param empBiz the empBiz to set
	 */
	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
	}
	
	/**
	 * @param updPwdIntervalTime the updPwdIntervalTime to set
	 */
	public void setUpdPwdIntervalTime(long updPwdIntervalTime) {
		this.updPwdIntervalTime = updPwdIntervalTime;
	}
	
	/**
	 * @param loginSuccessTemplate the loginSuccessTemplate to set
	 */
	public void setLoginSuccessTemplate(MqTemplate loginSuccessTemplate) {
		this.loginSuccessTemplate = loginSuccessTemplate;
	}
	/**
	 * @param sysTypeBiz the sysTypeBiz to set
	 */
	public void setSysTypeBiz(ISysTypeBiz sysTypeBiz) {
		this.sysTypeBiz = sysTypeBiz;
	}
	
	//add by xj
	public void setPortalBiz(IPortalBiz portalBiz) {
		this.portalBiz = portalBiz;
	}

	public void setTokenUserInfoCache(BoundHashRedisSupport<PortalCurrentUser> tokenUserInfoCache) {
		this.tokenUserInfoCache = tokenUserInfoCache;
	}

	//add by xj
	public void setHistoryTotal(int historyTotal) {
		this.historyTotal = historyTotal;
	}

	@Override
	public boolean confirmLoginCode(AuthCheckVo authCheck) throws Exception {
		if(log.isInfoEnabled()){
			log.info("手机端扫码登录,入参:"+ JSON.toJSONString(authCheck));
		}
		//校验请求参数
		if (authCheck == null || authCheck.getEmpCode() == null || authCheck.getSystemCode() == null) {
			throw new GatewayException(ErrorCode.SYSTEM_PARAM_ERROR, ErrorCode.SYSTEM_PARAM_ERROR_MESSGE);
		}
		LoginCodeVo loginCode = new LoginCodeVo();
		loginCode.setCode(authCheck.getLoginCode());
		loginCode.setEmpCode(authCheck.getEmpCode());
		loginCode.setScanSystemCode(authCheck.getSystemCode());
		loginCode.setTokenId(authCheck.getTokenId());
		loginCode.setFailureFlag(authCheck.getFailureFlag());
		return loginCodeUtil.updateLoginCode(loginCode);
	}
	
	public String convertEmpCode(LoginVo loginVo) throws Exception{
		String empCode = null;
		//将快运账号转换为快递工号
		empCode = empInfoUtil.findEmpCodeByUserName(loginVo.getUserName(), loginVo.getCompCode());
		//1、如果不是09开头账号为快运员工，根据银河工号+账套找到对应快递旧empCode
		/*if (!loginVo.getUserName().startsWith("09")) {
			empCode = empInfoUtil.findEmpCodeByYinhe(loginVo.getUserName(),loginVo.getCompCode());
		} else {
			//2、如果是09开头账号为快递员工，根据hrEmpCode找到对应快递旧empCode
			empCode = empInfoUtil.findEmpCodeByHrEmpCode(loginVo.getUserName(),loginVo.getCompCode());
		}*/
		// 用户不存在
		if (empCode == null) {
			throw new GatewayException(ErrorCode.USER_NONENTITY, ErrorCode.USER_NONENTITY_MESSGE);
		}
		loginVo.setYhUserName(loginVo.getUserName());
		return empCode;
	}

	/**
	 * @Description: 免密登录 
	 * @param loginVo
	 * @return
	 * @throws Exception
	 * @author huangting
	 * @date 2019年9月18日 下午4:24:38
	 */
	@Override
	public AuthResultVo secretFreeLogin(LoginVo loginVo) throws Exception {
		if(log.isInfoEnabled()){
			log.info(Thread.currentThread().getName()+ "登录dubbo开始时间" + System.currentTimeMillis());
		}
		AuthResultVo authResultVo = new AuthResultVo();
		// 1、校验必需参数是否正常
		if (loginVo == null || StringUtil.isBlank(loginVo.getSystemCode()) 
				|| loginVo.getLoginTime() == null || StringUtil.isBlank(loginVo.getUserName())) {
			throw new GatewayException(ErrorCode.SYSTEM_PARAM_ERROR, ErrorCode.SYSTEM_PARAM_ERROR_MESSGE);
		}
		log.warn("免密登录系统："+loginVo.getSystemCode()+"--登录工号："+loginVo.getUserName()+"--登录账套："+loginVo.getCompCode()+"--ip地址："+loginVo.getIpAddr()+"--登录时间："+System.currentTimeMillis());
		//String empCode = convertEmpCode(loginVo);
		//loginVo.setUserName(empCode);
		// 校验系统是否存活
		SystemInfoVo systemInfo = authCheckSystem(loginVo.getSystemCode(), loginVo.getMachineCode(), loginVo.getLoginTime());
		// 获取超时时间
		Date expireTime = getExpireTime(systemInfo);
		if (expireTime == null) {
			throw new GatewayException(ErrorCode.SYSTEM_CODE_ERROR, ErrorCode.SYSTEM_CODE_ERROR_MESSGE);
		}
		if(log.isInfoEnabled()){
			log.info(Thread.currentThread().getName()+ "登录dubbo登录校验开始时间" + System.currentTimeMillis());
		}
		// 参数校验完成，进行验证登录校验
		//modify by zhangRb 20180904 添加系统信息参数
		boolean isLoginFlag = validateLoginAuth(loginVo, authResultVo, systemInfo,false);
		
		if(log.isInfoEnabled()){
			log.info(Thread.currentThread().getName()+ "登录dubbo登录校验结束时间" + System.currentTimeMillis());
		}
		if (!isLoginFlag) {
			return authResultVo;
		}
		
		if(log.isInfoEnabled()){
			log.info(Thread.currentThread().getName()+ "登录dubbo登录获取token开始时间" + System.currentTimeMillis());
		}
		// 根据系统信息是否使用统一角色配置设置权限
		if (systemInfo != null ) {
			if (systemInfo.getUnifyRoleFlag() != null && systemInfo.getUnifyRoleFlag()) {
				setUserRole(authResultVo);
			}
			// 2、获取token
			String encryptTokenId = putTokenToCache(loginVo,authResultVo.getEmpCode(),authResultVo.getOrgId(), systemInfo.getSecretKey(), expireTime);
			//add by xj
			authResultVo.setTokenId(encryptTokenId);
		}
		authResultVo.setExpireTime(expireTime);
		if(log.isInfoEnabled()){
			log.info(Thread.currentThread().getName()+ "登录dubbo登录获取token结束时间" + System.currentTimeMillis());
		}
		// 登录成功发送消息
		sendLoginMqNotice(authResultVo, loginVo);
		if(log.isInfoEnabled()){
			log.info(Thread.currentThread().getName()+ "登录dubbo登录发送消息时间结束时间" + System.currentTimeMillis());
		}
		return authResultVo;
	}
	
}
