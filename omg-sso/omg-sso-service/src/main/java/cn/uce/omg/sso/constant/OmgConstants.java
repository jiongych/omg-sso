/** 
 * @项目名称: FSP
 * @文件名称: OmgConstants 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.constant;

/**
 * #5727
 * @author laizd
 * @since 2016-11-24
 */
public interface OmgConstants {

	/** 绑定关系，乾坤系统ID*/
	int SYSTEM_ID_QIANKUN = 1;
	/** 乾坤机构状态停用（9903） **/
	int CMS_ORG_STATUS_DISABLE = 9903;
	/** 乾坤机构状态暂停（9902） **/
	int CMS_ORG_STATUS_PAUSE = 9902;
	/** 乾坤机构状态启用（9901） **/
	int CMS_ORG_STATUS_ENABLE = 9901;
	/** 乾坤机构状态启用（9901） **/
	int CMS_ORG_STATUS_UNREVIEWED = 1021;
	/**
	 * 获取系统设置
	 */
	String SYSTEM_SETTING = "SYSTEM_SETTING";
	
	/**
	 * 获取系统设置
	 */
	String COMP_CODE = "COMP_CODE";
	
	String SYSTEM_CODE_SSO="SSO";
	
	String SYSTEM_CODE_YM_SSO="Heimdall";

	String SYSTEM_CODE_PMS="PMS";

	String SYSTEM_CODE_YH="yinhe";

	String SYSTEM_CODE_OA="OA";
	
	/**
	 * 手机号登录禁用系统列表
	 */
	String PHONE_LOGIN_DISABLE_SYSTEM = "PHONE_LOGIN_DISABLE_SYSTEM";
	
	String YM_EMP_CODE_MARK="@";
	
	String OA_EMP_CODE_MARK="ym";
	
	/**
	 * 优速用户工号开头标识
	 */
	String UC_HR_EMPCODE_FLAG  = "UC_HR_EMPCODE_FLAG";

	/**
	 * 是否根据集团工号更新密码
	 */
	String UPPWD_BY_YMEMPCODE_FLAG  = "UPPWD_BY_YMEMPCODE_FLAG";

	String LOGIN_DOMAIN = "LOGIN_DOMAIN";
}
