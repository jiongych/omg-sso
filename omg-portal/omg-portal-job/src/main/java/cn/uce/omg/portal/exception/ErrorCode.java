/** 
 * @项目名称: FSP
 * @文件名称: ErrorCode 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.portal.exception;


/**
 * 错误编码
 */
public enum ErrorCode {
	/**非法请求参数**/
	ILLEGAL_PARAM("S0001", "非法请求参数"),
	/**数字验签失败**/
	ILLEGAL_SIGN("S0002","数字验签失败"),
	/**非法的服务名称**/
	ILLEGAL_SERVICE("S0003", "非法的服务名称"),
	/**非法的数据类型**/
	ILLEGAL_DATATYPE("S0004", "非法的数据类型"),
	/**合作伙伴信息不存在**/
	ILLEGAL_PARTNER("S0005", "合作伙伴信息不存在"),
	/**非法的请求方法**/
	ILLEGAL_REQUEST_WAY("S0006", "非法的请求方法"),
	/**编码不正确**/
	ILLEGAL_ENCODING("S0007", "编码不正确"),
	/**文件未找到**/
	FILE_NOT_FOUND("S1101", "文件未找到"),
	/**获取文件异常**/
	GET_FILE_ERROR("S1102", "获取文件异常"),
	/**系统配置错误**/
	SYSTEM_CONF_ERROR("S9998", "系统配置错误"),
	/**系统错误**/
	SYSTEM_ERROR("S9999", "系统错误"),
	
	//回调时，给业务模块返回的错误编码
	/**连接失败**/
	CONNECTION_ERROR("E2001", "连接失败"),
	/**连接失败**/
	DUBBO_SERVICE_ERROR("E2002", "服务异常"),
	/**连接失败**/
	MQ_ERROR("E3001", "MQ异常"),
	/**连接失败**/
	ORDER_CODE_RET_ILLEGAL_PARAM("B0101", "产品服务未订购"),
	/**连接失败**/
	EMP_NOT_EXIST("B1006", "员工不存在"),
	/**机构不存在**/
	ORG_NOT_EXIST ("B1101", "机构不存在"),
	/**父机构不存在**/
	PARTENTORG_NOT_EXIST("B1103", "父机构不存在"),
	/**没有设置员工机构岗位**/
	NO_EMP_ORG_POSITION("B1201", "没有设置员工机构岗位"),
	/**机构没有同步**/
	ORG_NOT_SYNC_DING ("B1202", "机构没有同步"),
	/**岗位不存在**/
	POSITION_NOT_EXIST ("B1203", "岗位不存在"),
	/**上级机构没有同步*/
	PARENT_ORG_NOT_SYNC_DING ("B1307", "上级机构没有同步"),
	/**员工绑定关系不存在**/
	EMP_RELATION_NOT_EXIST("B1308", "员工绑定关系不存在"),
	/**机构绑定关系不存在**/
	ORG_RELATION_NOT_EXIST("B1309", "机构绑定关系不存在"),
	/**手机号码已经存在**/
	PHONE_EXIST("B1009", "手机号码已经存在"),
	/**数据未找到**/
	DATA_NOT_FOUND("B1010", "数据未找到"),
	//add by zhangRb 20170817 员工绑定一对多提示修改 Start
	/** 乾坤员工没有和OMG员工主机构绑定 */
	MAIN_ORG_EMP_RELATION_NOT_EXIST("B1500","当前用户绑定非钉钉主架构，请切换为主架构绑定员工登录");
	//add by zhangRb 20170817 员工绑定一对多提示修改 End
	/**失败编码**/
	String errorCode;
	/**失败信息**/
	String errorMsg;
	
	/**
	 * ErrorCode构造器
	 * @param errorCode
	 * @param errorMsg
	 */
	private ErrorCode(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	/**
	 * 
	 * 获取errorCode
	 */
	public String code() {
		return errorCode;
	}
	
	/**
	 * 
	 * 获取errorMsg
	 */
	public String message() {
		return errorMsg;
	}
	
	/**
	 * 
	 * 根据errorCode获取errorMsg
	 * @param code
	 */
	public static String message(String code) {
		ErrorCode[] errorCodes = ErrorCode.values();
		for (ErrorCode errorCode : errorCodes) {
			if (errorCode.code().equals(code)) {
				return errorCode.message();
			}
		}
		return null;
	}
}
