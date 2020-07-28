/** 
 * @项目名称: FSP
 * @文件名称: AuthConstants 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.constant;

/**
 * 认证系统常量
 * @author tanchong
 *
 */
public interface AuthConstants {
	String SUCCESS = "T";
	String FAIL = "F";

	/** 
	 * 系统类型：B/S 
	 */
	String SYSTYPE_CLASS_SYSTEM_TYPE_B = "BS";
	/** 
	 * 系统类型：C/S 
	 */
	String SYSTYPE_CLASS_SYSTEM_TYPE_C = "CS";

	/** 
	 * 加密类型：MD5 
	 */
	String SYSTYPE_CLASS_ENCRYPT_TYPE_MD5 = "MD5";
	/** 
	 * 加密类型：RSA 
	 */
	String SYSTYPE_CLASS_ENCRYPT_TYPE_RSA = "RSA";
	/** 
	 * 加密类型：SHA 
	 */
	String SYSTYPE_CLASS_ENCRYPT_TYPE_SHA = "SHA";

	/** 
	 * 操作类型：登录 
	 */
	String OPERATE_TYPE_LOGIN = "login";
	/** 
	 * 操作类型：登出 
	 */
	String OPERATE_TYPE_LOGOUT = "logout";

	/** 
	 * 默认超时时间：30分钟，单位：秒
	 */
	int SYSTEM_DEFAULT_TIME_OUT = 1800;

	/** 
	 * 系统是否存活：是
	 */
	int SYSTEM_ENABLE_YES = 1;
	/** 
	 * 系统是否存活：否 
	 */
	int SYSTEM_ENABLE_NO = 0;
	/** 
	 * 系统是否绑定机器码：是 
	 */
	int SYSTEM_BOUND_MACHINE_YES = 1;
	/** 
	 * 系统是否绑定机器码：否
	 */
	int SYSTEM_BOUND_MACHINE_NO = 0;

	/** 
	 * 登录每次允许失败的次数 
	 */
	int SYSTEM_LOGIN_ERROR_COUNT = 3;

	/** 
	 * omg员工状态：离职 
	 */
	int OMG_EMP_STATUS_LEAVE_OFFICE = 1;
	
	/** 
	 * 密码强度：低 
	 */
	String PASSWORD_STRENGTH_WEAK = "WEAK";
	/** 
	 * 密码强度：中 
	 */
	String PASSWORD_STRENGTH_AVERAGE = "AVERAGE";
	/** 
	 * 密码强度：高
	 */
	String PASSWORD_STRENGTH_STRONG = "STRONG";
	
	/** 
	 * 密码修改方式：用户修改
	 */
	String UPD_PWD_USER = "1000";
	
	/** 
	 * 密码修改方式：管理员修改 
	 */
	String UPD_PWD_ADMIN = "2000";
	
	/** 
	 * 密码修改方式：找回密码 
	 */
	String UPD_PWD_RESET = "3000";
	
	/** 
	 * 密码修改方式：快运mq消息修改
	 */
	String UPD_PWD_YM = "4000";
	
	/** 
	 * 验证码类型：登陆
	 */
	String CODE_TYPE_LOGIN = "LOGIN";
	/** 
	 * 验证码类型：修改密码 
	 */
	String CODE_TYPE_UPDPWD = "UPDPWD";
	/** 
	 * 验证码类型：重置密码 
	 */
	String CODE_TYPE_RTEPWD = "RESETPWD";

	/** 
	 * ip地址是否更新
	 */
	String IS_UPDATE_IP = "isUpdateIp";
	
	/** 
	 * 本系统code
	 */
	String DEFAULT_SYSTEMCODE = "SSO";
	
	/**
	 * 登录类型：员工工号
	 */
	int LOGIN_TYPE_EMP_CODE = 1;
	
	/**
	 * 登录类型：手机号
	 */
	int LOGIN_TYPE_MOBILE = 2;
	
	/**
	 * 登录类型：邮箱
	 */
	int LOGIN_TYPE_EMAIL = 3;
	/**
	 * 安全锁类型lockType：手机号
	 */
	String MOBILE_CODE = "mobile_code";
	
	/**
	 * 安全锁类型lockType：登录失败
	 */
	String LOGIN_FAIL = "login_fail";
}
