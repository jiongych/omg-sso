/** 
 * @项目名称: FSP
 * @文件名称: ErrorCode 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.constant;

/**
 * 异常码
 * @author tanchong
 */
public interface ErrorCode {

	/** 非法请求参数 */
	String ILLEGAL_PARAM = "S0001";

	/** 数字验签失败 */
	String ILLEGAL_SIGN = "S0002";

	/** 非法的服务名称 */
	String ILLEGAL_SERVICE = "S0003";

	/** 非法的数据类型 */
	String ILLEGAL_DATATYPE = "S0004";

	/** 系统编码信息不存在 */
	String ILLEGAL_PARTNER = "S0005";

	/** 非法的请求方法 */
	String ILLEGAL_REQUEST_WAY = "S0006";

	/** 编码不正确 */
	String ILLEGAL_ENCODING = "S0007";

	/** 系统错误 */
	String SYSTEM_ERROR = "S9999";

	/** 业务参数start */

	/** 用户不存在 */
	String USER_NONENTITY = "C1001";

	/** 用户被禁用 */
	String USER_DISABLE = "C1002";

	/** 用户密码错误 */
	String USER_PWD_ERROR = "C1003";

	/** 用户错误连续输入3次密码，需等待N分钟 */
	String USER_PWD_ERROR_WAIT = "C1004";
	
	/** TOKEN失效或不存在 */
	String ILLEGAL_TOKEN = "C1005";
	
	/** 用户名和token不匹配 */
	String TOKEN_UNMATCH_USER = "C1006";
	
	/** 员工已离职 */
	String USER_LEAVE_OFFICE = "C1007";

	/** 系统参数错误，必填参数未携带 */
	String SYSTEM_PARAM_ERROR = "C2001";

	/** 系统被禁用，无法使用认证系统 */
	String SYSTEM_DISABLE = "C2002";

	/** 系统传输未携带机器码 */
	String SYSTEM_NOT_MACHINE_CODE = "C2003";

	/** 系统未携带登录凭证 */
	String SYSTEM_NOT_TOKEN = "C2004";
	
	/** 系统参数错误，系统信息不存在 */
	String SYSTEM_CODE_ERROR = "C2005";
	
	/** 系统参数错误，登录时间和系统时间相差15分钟以上 */
	String SYSTEM_LOGIN_TIME_ERROR = "C2006";
	

	/** 业务参数end */
}
