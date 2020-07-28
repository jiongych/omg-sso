/** 
 * @项目名称: FSP
 * @文件名称: AuthConstants 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.constant;

/**
 * 认证系统常量
 * @author tanchong
 *
 */
public interface AuthConstants {
	String success = "T";
	String fail = "F";

	/** 系统类型：B/S */
	String SYSTYPE_CLASS_SYSTEM_TYPE_B = "B/S";
	/** 系统类型：C/S */
	String SYSTYPE_CLASS_SYSTEM_TYPE_C = "C/S";

	/** 加密类型：MD5 */
	String SYSTYPE_CLASS_ENCRYPT_TYPE_MD5 = "MD5";
	/** 加密类型：RSA */
	String SYSTYPE_CLASS_ENCRYPT_TYPE_RSA = "RSA";
	/** 加密类型：SHA */
	String SYSTYPE_CLASS_ENCRYPT_TYPE_SHA = "SHA";
	
	/** 操作类型：登录 */
	String OPERATE_TYPE_LOGIN = "login";
	/** 操作类型：登出 */
	String OPERATE_TYPE_LOGOUT = "logout";

	/** 默认超时时间：30分钟，单位：秒 */
	int SYSTEM_DEFAULT_TIME_OUT = 1800;

	/** 系统是否存活：是 */
	int SYSTEM_ENABLE_YES = 1;
	/** 系统是否存活：否 */
	int SYSTEM_ENABLE_NO = 0;
	/** 系统是否绑定机器码：是 */
	int SYSTEM_BOUND_MACHINE_YES = 1;
	/** 系统是否绑定机器码：否 */
	int SYSTEM_BOUND_MACHINE_NO = 0;
	
	/** 登录每次允许失败的次数 */
	int SYSTEM_LOGIN_ERROR_COUNT = 3;
	
	/** omg员工状态：离职 */
	int OMG_EMP_STATUS_LEAVE_OFFICE = 1;
	
	/** http请求SSO默认连接超时时间:2分钟*/
	int SSO_CLIENT_CONNECT_TIME_OUT = 120000; 
	/** http请求SSO默认读取时间:2分钟*/
    int SSO_CLIENT_READ_TIME_OUT = 120000;
}
