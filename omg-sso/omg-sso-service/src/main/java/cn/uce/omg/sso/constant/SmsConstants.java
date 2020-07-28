/** 
 * @项目名称: FSP
 * @文件名称: SmsConstants 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.constant;


/**
 * SmsConstants  
 * @Description: SmsConstants  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public interface SmsConstants {

	/** 扣费网点Code */
	static final String SMS_COST_ORG_CODE = "smspCostOrgCode";
	
	/** 模板id */
	static final String SMS_TEMP_ID= "tempId";

	/** 业务参数内容 */
	static final String SMS_CONTENT = "content";
	
	/** 模板id:1.通用模板 */
	static final String DEFAULT_TEMP_ID = "1";
	
	/** 扣费网点Code:总部*/
	static final String DEFAULT_COST_ORG_CODE = "uc";
	
	/** add by zhangRb 20171020 首次登录设置登录密码  Start*/
	/** 找加登录密码 */
	static final String SMS_GET_BACK_LOGIN_PASSWORD = "找回";
	
	/** 设置登录密码 */
	static final String SMS_SET_LOGIN_PASSWORD = "设置";
	/** add by zhangRb 20171020 首次登录设置登录密码  End*/
}
