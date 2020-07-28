/** 
 * @项目名称: FSP
 * @文件名称: ErrorCode 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.constant;

/**
 * 异常码
 * @author tanchong
 */
public interface ErrorCode {

	/** 
	 * 非法请求参数 
	 */
	String ILLEGAL_PARAM = "S0001";
	String ILLEGAL_PARAM_MESSGE = "非法请求参数";

	/** 
	 * 数字验签失败
	 */
	String ILLEGAL_SIGN = "S0002";

	/** 
	 * 非法的服务名称 
	 */
	String ILLEGAL_SERVICE = "S0003";

	/** 
	 * 非法的数据类型
	 */
	String ILLEGAL_DATATYPE = "S0004";

	/** 
	 * 系统编码信息不存在 
	 */
	String ILLEGAL_PARTNER = "S0005";

	/** 
	 * 非法的请求方法 
	 */
	String ILLEGAL_REQUEST_WAY = "S0006";

	/** 
	 * 编码不正确 
	 */
	String ILLEGAL_ENCODING = "S0007";

	/** 
	 * 系统错误 
	 */
	String SYSTEM_ERROR = "S9999";

	/** 业务参数start */

	/** 
	 * 用户不存在 
	 */
	String USER_NONENTITY = "B1001";
	String USER_NONENTITY_MESSGE = "用户不存在";

	/** 
	 * 用户被禁用  
	 */
	String USER_DISABLE = "B1002";
	String USER_DISABLE_MESSGE = "用户被禁用";

	/** 
	 * 用户密码错误 
	 */
	String USER_PWD_ERROR = "B1003";
	String USER_PWD_ERROR_MESSGE = "用户密码错误";

	/** 
	 * 用户错误连续输入3次密码，需等待N分钟 
	 */
	String USER_PWD_ERROR_WAIT = "B1004";

	/** 
	 *TOKEN失效或不存在 
	 */
	String ILLEGAL_TOKEN = "B1005";
	String ILLEGAL_TOKEN_MESSGE = "TOKEN失效或不存在";

	/** 
	 * 用户名和token不匹配 
	 */
	String TOKEN_UNMATCH_USER = "B1006";
	String TOKEN_UNMATCH_USER_MESSGE = "用户名和token不匹配";

	/** 
	 * 员工已离职
	 */
	String USER_LEAVE_OFFICE = "B1007";
	String USER_LEAVE_OFFICE_MESSGE = "员工已离职";
	
	/** 
	 * 用户未绑定乾坤员工 
	 */
	String USER_UNBOUND_CMS_EMP= "B1008";
	String USER_UNBOUND_CMS_EMP_MESSGE = "用户未绑定乾坤员工";

	/** 
	 * 用户的机构已被停用 
	 */
	String USER_DISABLE_CMS_EMP= "B1015";
	String USER_DISABLE_CMS_EMP_MESSGE = "员工所属机构已停用/未审核，不能登录";
	
	
	
	/** 
	 * 系统参数错误，必填参数未携带
	 */
	String SYSTEM_PARAM_ERROR = "B2001";
	String SYSTEM_PARAM_ERROR_MESSGE = "系统参数错误，必填参数未携带";

	/** 
	 * 系统被禁用，无法使用认证系统 
	 */
	String SYSTEM_DISABLE = "B2002";
	String SYSTEM_DISABLE_MESSGE = "系统被禁用，无法使用认证系统";

	/** 
	 * 系统传输未携带机器码 
	 */
	String SYSTEM_NOT_MACHINE_CODE = "B2003";
	String SYSTEM_NOT_MACHINE_CODE_MESSGE = "系统传输未携带机器码";

	/** 
	 * 系统未携带登录凭证 
	 */
	String SYSTEM_NOT_TOKEN = "B2004";
	String SYSTEM_NOT_TOKEN_MESSGE = "系统未携带登录凭证";

	/** 
	 * 系统参数错误，系统信息不存在 
	 */
	String SYSTEM_CODE_ERROR = "B2005";
	String SYSTEM_CODE_ERROR_MESSGE = "系统参数错误，系统信息不存在";

//	/** 系统参数错误，登录时间和系统时间相差15分钟以上 */
//	String SYSTEM_LOGIN_TIME_ERROR = "B2006";
//	String SYSTEM_LOGIN_TIME_ERROR_MESSGE = "系统参数错误，登录时间和系统时间相差15分钟以上";
	
	/** 
	 * 系统参数错误，修改时间和系统时间相差15分钟以上
	 */
	String SYSTEM_UPDPWD_TIME_ERROR = "B2007";
	String SYSTEM_UPDPWD_TIME_ERROR_MESSGE = "系统参数错误，操作时间和系统时间相差15分钟以上";
	
	/** 
	 * 新密码与前N次的密码相同
	 */
	String UPDPWD_THR_ERROR = "B2008";
	String UPDPWD_THR_ERROR_MESSGE = "新密码与前{}次的密码相同";
	
	/** 
	 * 新密码强度强制为高  
	 */
	String UPDPWD_STRENGTH_ERROR = "B2009";
	String UPDPWD_STRENGTH_ERROR_MESSGE  = "新密码强度强制为高,必须同时包含数字、字母和特殊字符";
	
	/** 
	 * 更新密码异常 
	 */
	String UPDPWD_UPDATE_ERROR = "B2010";
	String UPDPWD_UPDATE_ERROR_MESSGE  = "更新密码异常";
	
	/** 
	 * 绑定手机错误 
	 */
	String RTEPWD_MOBILE_ERROR = "B2011";
	String RTEPWD_MOBILE_ERROR_MESSGE  = "绑定手机错误";
	
	/** 
	 * 验证码错误 
	 */
	String RTEPWD_CODE_ERROR = "B2012";
	String RTEPWD_CODE_ERROR_MESSGE  = "验证码错误";
	
	/** 
	 * 新旧密码不能相同 
	 */
	String UPDPWD_NEWOLD_ERROR = "B2013";
	String UPDPWD_NEWOLD_ERROR_MESSGE  = "新旧密码不能相同";
	
	/** 
	 * 修改的密码属于弱密码范围，请重新设置密码
	 */
	String UPDPWD_WEAK_ERROR = "B2014";
	String UPDPWD_WEAK_ERROR_MESSGE  = "修改的密码属于弱密码范围，请重新设置密码";
	
	/** 
	 * 验证码错误 
	 */
	String CREATE_CODE_ERROR = "B2015";
	String CREATE_CODE_ERROR_MESSGE  = "生成验证码错误";

	/** 
	 * 验证码必填参数未填
	 */
	String CODE_PARAM_ERROR = "B3001";
	String CODE_PARAM_ERROR_MESSGE  = "验证码必填参数未填";
	
	/** 
	 * 验证码不存在  
	 */
	String CODE_NOTFIND_ERROR = "B3002";
	String CODE_NOTFIND_ERROR_MESSGE  = "验证码不存在或失效，请重新获取！";
	
	/** 
	 *  验证码验证次数超过有效次数  
	 */
	String CODE_VERIFYCOUNT_ERROR = "B3003";
	String CODE_VERIFYCOUNT_ERROR_MESSGE  = "验证码验证次数超过有效次数";
	
	/** 
	 * 验证码超时 
	 */
	String CODE_TIMEOUT_ERROR = "B3004";
	String CODE_TIMEOUT_ERROR_MESSGE  = "验证码超时";
	
	/** 
	 * 验证码发送过于频繁
	 */
	String CODE_INTERVALTIME_ERROR = "B3005";
	String CODE_INTERVALTIME_ERROR_MESSGE  = "验证码发送过于频繁";
	
	/** 
	 * 验证码发送失败 
	 */
	String CODE_SENDCODE_ERROR = "B3006";
	String CODE_SENDCODE_ERROR_MESSGE  = "验证码发送失败";
	
	/** 
	 * 重置密码唯一key失效
	 */
	String RESET_PWD_KEY_ERROR = "B3007";
	String RESET_PWD_KEY_ERROR_MESSGE  = "页面已失效，请重新获取验证码！";
	/** 业务参数end */
	
	/**
	 * 系统URL错误
	 */
	String SYSTEM_URL_ERROR = "B3008";
	String SYSTEM_URL_ERROR_MESSGE  = "系统参数错误，登录请求系统域名不正确!";

	/**
	 * 校验码校验失败Code
	 */
	String CHECK_CODE_ERROR_CODE = "CCEC";

	String CHECK_CODE_SUCCESS_CODE = "CCSC";
	
	/** 
	 * 手机号验证不存在  
	 */
	String MOBILE_NOTFIND_ERROR = "B3009";
	String MOBILE_NOTFIND_ERROR_MESSGE  = "手机号验证不存在或失效！";
	
	/** 
	 * 手机号验证发送过于频繁
	 */
	String MOBILE_INTERVALTIME_ERROR = "B3010";
	String MOBILE_INTERVALTIME_ERROR_MESSGE  = "手机号验证发送过于频繁";
	
	/** 
	 *  手机号验证次数超过有效次数  
	 */
	String MOBILE_VERIFYCOUNT_ERROR = "B3011";
	String MOBILE_VERIFYCOUNT_ERROR_MESSGE  = "手机号验证次数超过有效次数,请12个小时后再试";
	
	/** 
	 *  手机号验证次数超过有效次数  
	 */
	String MOBILE_CHECKED_ERROR = "B3012";
	String MOBILE_CHECKED_ERROR_MESSGE  = "发送手机短信验证失败";

	String CHECK_CODE_ERROR_MESSGE = "图片验证码错误";
	
	/** 
	 *    
	 */
	String LOCK_BUSINESS_ERROR = "B3013";
	String LOCK_BUSINESS_ERROR_MESSGE  = "LockManager导致系统超时";
	
	/** 
	 *    
	 */
	String LOGIN_TYPE_NOT_EXISTS = "B3014";
	String LOGIN_TYPE_NOT_EXISTS_MESSGE  = "登录类型不存在。";
	
	/**
	 * 未扫码
	 */
	String NOT_SCAN_CODE = "B3015";
	String NOT_SCAN_CODE_MESSGE  = "未扫码";
	
	/**
	 * 已扫码未登录
	 */
	String SCAN_CODE_NOT_LOGIN = "B3016";
	String SCAN_CODE_NOT_LOGIN_MESSGE  = "已扫码未登录";
	
	/**
	 * 没有扫码信息
	 */
	String NOT_SCAN_INFO = "B3017";
	String NOT_SCAN_INFO_MESSGE  = "没有扫码信息";
	
	/**
	 * 未找到域名配置信息
	 */
	String FIND_DOMIAN_FAIL = "B3018";
	String FIND_DOMIAN_FAIL_MESSGE  = "未找到域名配置信息";
	
	/** 
	 * 二维码已被占用
	 */
	String CODE_OCCUPIED_ERROR = "B3019";
	String CODE_OCCUPIED_ERROR_MESSGE  = "二维码已被占用，请重新获取！";
	
	/** 
	 * 二维码已被取消,请刷新二维码
	 */
	String CODE_CANCEL_ERROR = "B3020";
	String CODE_CANCEL_ERROR_MESSGE  = "二维码已被取消,请刷新二维码！";

}
