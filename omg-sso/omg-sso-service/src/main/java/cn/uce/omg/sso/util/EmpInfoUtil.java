/** 
 * @项目名称: FSP
 * @文件名称: EmpInfoUtil 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.util;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.uce.omg.sso.biz.IUserBiz;
import cn.uce.omg.sso.cache.EmpCache;
import cn.uce.omg.sso.cache.EmpMobileCache;
import cn.uce.omg.sso.cache.FspDictDataCache;
import cn.uce.omg.sso.cache.HashRedisWithFieldExpireCache;
import cn.uce.omg.sso.cache.UserCache;
import cn.uce.omg.sso.constant.AuthConstants;
import cn.uce.omg.sso.constant.ErrorCode;
import cn.uce.omg.sso.constant.GatewayConstants;
import cn.uce.omg.sso.constant.OmgConstants;
import cn.uce.omg.sso.exception.GatewayException;
import cn.uce.omg.sso.exception.TimeoutException;
import cn.uce.omg.sso.vo.EmpInfoVo;
import cn.uce.omg.sso.vo.LoginFailVo;
import cn.uce.omg.sso.vo.LoginVo;
import cn.uce.omg.vo.EmpVo;
import cn.uce.omg.vo.SysTypeVo;
import cn.uce.omg.vo.UserVo;
import cn.uce.utils.StringUtil;

/**
 * 员工信息缓存工具类
 * @author tanchong
 *
 */
public class EmpInfoUtil {
	/** 用户登录失败缓存 */
	private HashRedisWithFieldExpireCache<LoginFailVo> loginFailCache;

	/** 手机号码校验规则 */
	private String phoneRegex;
	/** 员工缓存 */
	private EmpCache empCache;
	/** 用户缓存 */
	private UserCache userCache;
	/** 用户biz */
	private IUserBiz userBiz;
	/** 锁管理对象 */
	private LockManager lockManager;
	/** 登录每次允许失败的次数:3次 */
	private int failLoginNum = 3;
	
	/** 员工工号与手机号对应关系缓存 */
	private EmpMobileCache empMobileCache;
	/** 数据字典缓存 */
	private FspDictDataCache fspDictDataCache;

	// add by majun 20190622 增加手机验证码登录 begin
	private CodeUtil codeUtil;
	
	
	public CodeUtil getCodeUtil() {
		return codeUtil;
	}

	public void setCodeUtil(CodeUtil codeUtil) {
		this.codeUtil = codeUtil;
	}
	// add by majun 20190622 增加手机验证码登录 end

	private Logger log = LoggerFactory.getLogger(this.getClass());
	/**
	 * 获取到员工的empid
	 * @param key
	 * @param type 1=empCode;2=mobile;3=email
	 * @return
	 */
	public EmpInfoVo getEmpInfoByKey(String key,int type ) throws RuntimeException {
		// 查询用户的密码信息
		UserVo empPasswordVo = null;
		//mobile
		if (type == AuthConstants.LOGIN_TYPE_MOBILE) {
			empPasswordVo = userBiz.findUserByMobile(key);
		} else if (type == AuthConstants.LOGIN_TYPE_EMAIL) {
			// 邮箱查找
			empPasswordVo = userBiz.findUserByEmail(key);
		} else if (type == AuthConstants.LOGIN_TYPE_EMP_CODE) {
			// 工号查找
			empPasswordVo = userBiz.findUserByEmpCode(key);
		} else { // 否则进行查找用户名
			empPasswordVo = userCache.get(key);
		}
		EmpInfoVo empInfoVo = null;
		if (empPasswordVo != null) {
			empInfoVo = new EmpInfoVo();
			EmpVo empVo = empCache.get(empPasswordVo.getEmpId());
			if(empVo == null){
				return null;
			}
			empInfoVo.setEmpVo(empVo);
			empInfoVo.setUserVo(empPasswordVo);
		}
		return empInfoVo;
	}
	
	/**
	 * 根据empCode获取到用户的信息
	 * @param empId
	 * @return
	 * @throws Exception
	 */
	public EmpInfoVo getEmpInfoByEmpCode(String empCode) throws Exception {
		UserVo userVo = userCache.get(empCode);
		EmpInfoVo empInfoVo = null;
		if (userVo != null) {
			empInfoVo = new EmpInfoVo();
			EmpVo empVo = empCache.get(userVo.getEmpId());
			if(empVo == null){
				return null;
			}
			empInfoVo.setEmpVo(empVo);
			empInfoVo.setUserVo(userVo);
		}
		return empInfoVo;
	}

	/**
	 * 根据empid获取到用户的信息
	 * @param empId
	 * @return
	 * @throws Exception
	 */
	public EmpVo findEmpInfoByEmpId(Integer empId) throws Exception {
		if (empId == null) {
			return null;
		}
		// 判断当前用户是否存在密码信息
		EmpVo empVo = empCache.get(empId);;
		return empVo;
	}

	/**
	 * 根据key 获取登录失败的信息
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public LoginFailVo findLoginFailByUsername(String key) throws Exception {
		if (key == null) {
			return null;
		}
		LoginFailVo loginFail = null;
		loginFail = loginFailCache.get(key);
		return loginFail;
	}

	/**
	 * 登录失败后，保存失败的信息
	 * @param key
	 * @param loginFailVo
	 * @throws Exception
	 */
	public void saveLoginFail(String key, LoginFailVo loginFailVo) throws Exception {
		if (key == null || loginFailVo == null) {
			return;
		}						
		 loginFailCache.put(key, loginFailVo);	
	}

	/**
	 * 删除登录失败的信息
	 * @param key
	 * @throws Exception
	 */
	public void deleteLoginFail(String key) throws Exception {
		if (key == null) {
			return;
		}
		loginFailCache.delete(key);
	}

	/**
	 * 判定是否满足手机号的正则表达式
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private Boolean isPhoneFlag(String key) throws Exception {
		Pattern pattern = Pattern.compile(phoneRegex);
		Matcher matcher = pattern.matcher(key);
		return matcher.matches();
	}

	/**
	 * 判定是否满足邮箱的正则表达式
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private Boolean isEmailFlag(String key) throws Exception {
		Pattern pattern = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
		Matcher matcher = pattern.matcher(key);
		return matcher.matches();
	}
	
	/**
	 * 
		 * 方法的描述：
		 * @param loginVo,empInfoVo
		 * @author yangjun
		 * @date 2017-09-14
	 */
	public LoginFailVo checkUsrPwd(LoginVo loginVo,EmpInfoVo empInfoVo,boolean checkFlag) throws Exception{
		UserVo user = empInfoVo.getUserVo();
		String empCode = empInfoVo.getEmpVo().getEmpCode();
		//addBy yangjun 2017-09-14 增加并发锁管理机制 防止失败密码次数和账号锁定次数并发====begin===========
		try {
			lockManager.getLock(AuthConstants.LOGIN_FAIL, empCode, 5000);
		} catch (TimeoutException e) {
			  log.error("LockManager: "+e.getMessage(),e);
	    }
		LoginFailVo loginFail = null;
		try {		
			// 在缓存中检查当前用户是否密码输入多次失败，导致账户锁定
			loginFail = findLoginFailByUsername(empCode);
			long nowTime = Calendar.getInstance().getTimeInMillis();
			if (loginFail != null) {
				// 存在多次失败记录，判断已经达到允许操作时间
				if (loginFail.getUserExpireTime() != null && nowTime < loginFail.getUserExpireTime()) {
					// 未达到解锁条件，进行提示
					String remark = getLoginFailInfo(loginFail.getFailCount(),loginFail.getUserExpireTime());
					throw new GatewayException(ErrorCode.USER_PWD_ERROR_WAIT, remark);
				}
			}
			if (checkFlag == false) {
				return loginFail;
			}
			// 密码不正确
			String password = SignUtils.md5(loginVo.getPassword(), GatewayConstants.DEFAULT_CHARSET);
			if (!user.getPassword().equals(password)) {
				if (loginFail == null) {
					loginFail = new LoginFailVo();
					loginFail.setFailCount(0);;
				}
				log.warn("登录工号:"+empCode+",失败：" + loginFail.getFailCount()+"，过期时间："+ loginFail.getUserExpireTime());
				loginFail.setFailCount(loginFail.getFailCount() == null ? 1 : loginFail.getFailCount() + 1);
				if (loginFail.getFailCount() % failLoginNum == 0) {
					//输入错误3次就将用户登陆锁定状态设置为锁定
					if(loginFail.getFailCount() == failLoginNum) {
						userBiz.updateUserLockState(empCode, true);
					}
	                loginFail.setLockCount(loginFail.getLockCount() == null ? 1 : loginFail.getLockCount() + 1);												
					Date expireTime = AuthUtil.getExpireTime(loginFail.getLockCount());
					// 每3次输入错误，时间往后递增
					loginFail.setUserExpireTime(expireTime.getTime());
					String remark = getLoginFailInfo(loginFail.getFailCount(),loginFail.getUserExpireTime());
					// 失败进行保存记录
					saveLoginFail(empCode, loginFail);
					// 发送消息，进行记录用户登录异常
					//sendLoginErrorNotice();空方法体的方法，暂时注释  modifyBy yangjun 2017-09-15
					throw new GatewayException(ErrorCode.USER_PWD_ERROR_WAIT, remark);
				}
				// 失败进行保存记录
				saveLoginFail(empCode, loginFail);
				throw new GatewayException(loginFail.getFailCount(),ErrorCode.USER_PWD_ERROR, "你输入的密码和用户名不匹配");
			}			
		} finally {
			lockManager.releaseLock(AuthConstants.LOGIN_FAIL, empCode);
	    }
		// =================end=========================
		return loginFail;
	}
	
	/**
	 * 检验手机验证码。
	 * add by majun 20190622 增加手机验证码登录
	 * @param loginVo
	 * @param empInfoVo
	 * @return
	 * @throws Exception
	 */
	public LoginFailVo checkCaptcha(LoginVo loginVo,EmpInfoVo empInfoVo) throws Exception{
		String empCode = empInfoVo.getEmpVo().getEmpCode();
		try {
			lockManager.getLock(AuthConstants.LOGIN_FAIL, empCode, 5000);
		} catch (TimeoutException e) {
			  log.error("LockManager: "+e.getMessage(),e);
	    }
		LoginFailVo loginFail = null;
		try {		
			// 在缓存中检查当前用户是否密码输入多次失败，导致账户锁定
			loginFail = findLoginFailByUsername(empCode);
			long nowTime = Calendar.getInstance().getTimeInMillis();
			if (loginFail != null) {
				// 存在多次失败记录，判断已经达到允许操作时间
				if (loginFail.getUserExpireTime() != null && nowTime < loginFail.getUserExpireTime()) {
					// 未达到解锁条件，进行提示
					String remark = getLoginFailInfo(loginFail.getFailCount(),loginFail.getUserExpireTime());
					throw new GatewayException(ErrorCode.USER_PWD_ERROR_WAIT, remark);
				}
			}
			String captchaCode = loginVo.getPassword();
			
			boolean result = false;
			try {
				result = codeUtil.checkCode(captchaCode ,empCode, AuthConstants.CODE_TYPE_LOGIN);
			} catch (GatewayException e) {
				result = false;
				// 如果验证码错误，是要记录错误次数的。
				if (!ErrorCode.RTEPWD_CODE_ERROR.equals(e.getErrorCode())) {
					throw e;
				}
			}
			// 验证码不正确
			if (!result) {
				if (loginFail == null) {
					loginFail = new LoginFailVo();
					loginFail.setFailCount(0);;
				}
				log.warn("登录工号:"+empCode+",失败：" + loginFail.getFailCount()+"，过期时间："+ loginFail.getUserExpireTime());
				loginFail.setFailCount(loginFail.getFailCount() == null ? 1 : loginFail.getFailCount() + 1);
				if (loginFail.getFailCount() % failLoginNum == 0) {
					//输入错误3次就将用户登陆锁定状态设置为锁定
					if(loginFail.getFailCount() == failLoginNum) {
						userBiz.updateUserLockState(empCode, true);
					}
	                loginFail.setLockCount(loginFail.getLockCount() == null ? 1 : loginFail.getLockCount() + 1);												
					Date expireTime = AuthUtil.getExpireTime(loginFail.getLockCount());
					// 每3次输入错误，时间往后递增
					loginFail.setUserExpireTime(expireTime.getTime());
					String remark = getLoginFailInfo(loginFail.getFailCount(),loginFail.getUserExpireTime());
					// 失败进行保存记录
					saveLoginFail(empCode, loginFail);
					// 发送消息，进行记录用户登录异常
					//sendLoginErrorNotice();空方法体的方法，暂时注释  modifyBy yangjun 2017-09-15
					throw new GatewayException(ErrorCode.USER_PWD_ERROR_WAIT, remark);
				}
				// 失败进行保存记录
				saveLoginFail(empCode, loginFail);
				throw new GatewayException(loginFail.getFailCount(),ErrorCode.USER_PWD_ERROR, "您已输入错误手机验证码" + loginFail.getFailCount() + "次,还可以输入"
						+ (failLoginNum - (loginFail.getFailCount() % failLoginNum )) + "次");
			}			
		} finally {
			lockManager.releaseLock(AuthConstants.LOGIN_FAIL, empCode);
	    }
		return loginFail;
	}
	
	/**
	 * Description: 根据登录帐号获取对应员工工号
	 * @param loginVo
	 * @return
	 * @author zhangRenbing
	 * @date 2018年9月5日
	 */
	public String getLoginEmpCode(LoginVo loginVo) {
		List<SysTypeVo> sysTypeVos = fspDictDataCache.get(OmgConstants.SYSTEM_SETTING);
		List<String> phoneLoginDisableSystem = null;
		for (SysTypeVo sysTypeVo : sysTypeVos) {
			if (OmgConstants.PHONE_LOGIN_DISABLE_SYSTEM.equals(sysTypeVo.getTypeCode())) {
				phoneLoginDisableSystem = Arrays.asList(sysTypeVo.getTypeName().split(","));
				break;
			}
		}
		if (phoneLoginDisableSystem != null 
				&& phoneLoginDisableSystem.size() > 0 
				&& !phoneLoginDisableSystem.contains(loginVo.getSystemCode())) {
			return empMobileCache.get(loginVo.getUserName().trim());
		}else {
			return loginVo.getUserName();
		}
	}

	public boolean findUpPwdFlag() {
		List<SysTypeVo> sysTypeVos = fspDictDataCache.get(OmgConstants.SYSTEM_SETTING);
		boolean flag = false;
		for (SysTypeVo sysTypeVo : sysTypeVos) {
			if (OmgConstants.UPPWD_BY_YMEMPCODE_FLAG.equals(sysTypeVo.getTypeCode())) {
				flag = "true".equals(sysTypeVo.getTypeName());
				break;
			}
		}
		return flag;
	}
	
	/**
	 * 根据hr工号查找用户empCode
	 * @param userName
	 * @param compCode
	 * @return
	 * @author huangting
	 * @date 2019年10月22日 下午2:37:30
	 */
	public String findEmpCodeByUserName(String userName,String compCode) {
		if (StringUtil.isBlank(userName)) {
			log.error("参数为空");
			return null;
		}
		if (StringUtil.isNotBlank(userName) && StringUtil.isNotBlank(compCode)) {
			List<SysTypeVo> sysTypeVos = fspDictDataCache.get(OmgConstants.SYSTEM_SETTING);
			List<String> ucHrEmpCodeList = null;
			for (SysTypeVo sysTypeVo : sysTypeVos) {
				if (OmgConstants.UC_HR_EMPCODE_FLAG.equals(sysTypeVo.getTypeCode())) {
					ucHrEmpCodeList = Arrays.asList(sysTypeVo.getTypeName().split(","));
					break;
				}
			}
			boolean ucUserFlag = false;
			if (ucHrEmpCodeList != null && ucHrEmpCodeList.size() > 0 ){
				for (String code : ucHrEmpCodeList) {
					if (userName.startsWith(code)) {
						ucUserFlag = true;
						break;
					}
				}
			}
			//1、如果不是09开头账号为快运员工，根据银河工号+账套找到对应快递旧empCode
			if (!ucUserFlag) {
				return this.findEmpCodeByYinhe(userName,compCode);
			}
			//2、如果是09开头账号为快递员工，根据hrEmpCode找到对应快递旧empCode
			return this.findEmpCodeByHrEmpCode(userName,compCode);
		}
		if (userName.indexOf(OmgConstants.OA_EMP_CODE_MARK) != -1) {
			userName= userName.substring(userName.indexOf(OmgConstants.OA_EMP_CODE_MARK) + OmgConstants.OA_EMP_CODE_MARK.length(),userName.length());
		}
		UserVo user = userBiz.findUserByHrEmpCode(userName);
		if (user == null || StringUtil.isBlank(user.getEmpCode())) {
			return null;
		}
		return user.getEmpCode();
	}
	
	/**
	 * @Description: 根据hr员工编码查找快递empCode
	 * @param hrEmpCode
	 * @return
	 * @author huangting
	 * @date 2019年9月18日 下午4:36:24
	 */
	public String findEmpCodeByHrEmpCode(String hrEmpCode,String compCode) {
		UserVo user = userBiz.findUserByHrEmpCode(hrEmpCode, compCode);
		if (user == null || StringUtil.isBlank(user.getEmpCode())) {
			return null;
		}
		return user.getEmpCode();
	}

	/***
	 * 
	 *根据集团工号查询员工
	 *@return {@link String}
	 *@throws
	 *@author 004425
	 *@date 2020/5/14 18:30
	 */
	public String findEmpCodeByYmEmpCode(String ymEmpCode) {
		UserVo user = userBiz.findUserByYmEmpCode(ymEmpCode);
		if (user == null || StringUtil.isBlank(user.getEmpCode())) {
			return null;
		}
		return user.getEmpCode();
	}
	
	/**
	 * @Description: 根据银河账号+账套查找快递empCode
	 * @param empCode
	 * @param compCode
	 * @return
	 * @author huangting
	 * @date 2019年9月18日 下午4:40:03
	 */
	public String findEmpCodeByYinhe(String yhEmpCode,String compCode) {
		UserVo user = userBiz.findUserByYhEmpCode(yhEmpCode, compCode);
		if (user == null || StringUtil.isBlank(user.getEmpCode())) {
			return null;
		}
		return user.getEmpCode();
	}
	/**
	 * 构造登录失败提示信息
	 * @param failCount。
	 * @param expireTime
	 * @return
	 * @author huangting
	 * @date 2017年6月8日 下午2:37:03
	 */
	private String getLoginFailInfo(Integer failCount, Long expireTime) {
		return "您已连续输错" + failCount + "次，请在" + DateUtil.formatToLongDate(expireTime) + "再试";
	}
	

	public void setLoginFailCache(HashRedisWithFieldExpireCache<LoginFailVo> loginFailCache) {
		this.loginFailCache = loginFailCache;
	}

	public void setPhoneRegex(String phoneRegex) {
		this.phoneRegex = phoneRegex;
	}

	/**
	 * @param empCache the empCache to set
	 */
	public void setEmpCache(EmpCache empCache) {
		this.empCache = empCache;
	}

	/**
	 * @param userCache the userCache to set
	 */
	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

	/**
	 * @param userBiz the userBiz to set
	 */
	public void setUserBiz(IUserBiz userBiz) {
		this.userBiz = userBiz;
	}

	/**
	 * @param lockManager the lockManager to set
	 */
	public void setLockManager(LockManager lockManager) {
		this.lockManager = lockManager;
	}

	/**
	 * @param failLoginNum the failLoginNum to set
	 */
	public void setFailLoginNum(int failLoginNum) {
		this.failLoginNum = failLoginNum;
	}

	/**
	 * 设置 员工工号与手机号对应关系缓存
	 * @param empMobileCache 员工工号与手机号对应关系缓存
	 */
	public void setEmpMobileCache(EmpMobileCache empMobileCache) {
		this.empMobileCache = empMobileCache;
	}

	/**
	 * 设置 数据字典缓存
	 * @param fspDictDataCache 数据字典缓存
	 */
	public void setFspDictDataCache(FspDictDataCache fspDictDataCache) {
		this.fspDictDataCache = fspDictDataCache;
	}
}
