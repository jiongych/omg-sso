/** 
 * @项目名称: FSP
 * @文件名称: OmgErrorCode 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.constant;

/**
 * OmgErrorCode  
 * @Description: OmgErrorCode  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public interface OmgErrorCode {

	/** 非法请求参数 */
	String ILLEGAL_PARAM = "S0001";
	String ILLEGAL_PARAM_MSG = "非法请求参数";
	/** 数据已存在 */
	String DATA_EXIST = "B1000";
	
	
	/** 手机号码已经存在 */
	String PHONE_EXIST = "B1009";
	/** 数据未找到 */
	String DATA_NOT_FOUND = "B1010";
	/** 其他系统员工已绑定 */
	String OTHER_EMP_BIND = "B1012";
	/** 机构不存在 */
	String ORG_NOT_EXIST = "B1101";
	/** 父机构不存在 */
	String PARTENTORG_NOT_EXIST = "B1103";
	/** 机构被员工使用 */
	String ORG_IS_USE = "B1104";
	/** 存在下级机构 */
	String ORG_NEXT_EXIST = "B1105";
	/** 员工已绑定 */
	String OMG_EMP_BIND = "B1106";
	/** 机构已存在 */
	String ORG_EXIST = "B1108";
	
	/** 员工不存在 */
	String EMP_NOT_EXIST = "B1006";
	
	/** 机构已修改 */
	String EMP_ORG_UPDATE = "B1007";
	
	/**保存成功，同步失败*/
	String SAVE_EMP_SUCCESS_SYNC_FAIL = "B1204";
	/**保存成功，同步失败*/
	String DELETE_EMP_SUCCESS_SYNC_FAIL = "B1205";
	/**删除员工失败*/
	String DELETE_DING_EMP_FAIL = "B1204";
	
	/** OMG员工编号在对应乾坤机构下已有绑定的员工在使用  */
	String OMG_ORG_CODE_AT_CMS_EXIST = "B1208";
	
	/** 乾坤员工姓名重复 */
	String CMS_EMP_NAME_REPEAT = "B1210";
	
	/**保存成功，同步失败*/
	String SAVE_ORG_SUCCESS_SYNC_FAIL = "B1301";
	/**删除成功，同步失败*/
	String DELETE_ORG_SUCCESS_SYNC_FAIL = "B1302";
	/**机构已经绑定*/
	String ORG_BIND = "B1306";
	
	/** 用户错误编码 */
	/** 校验通过*/
	String USER_PASS = "B2000";
	/** 用户不存在 */
	String USER_NONENTITY = "B2001";
	/** 用户被禁用 */
	String USER_DISABLE = "B2002";
	/** 用户密码错误 */
	String USER_PWD_ERROR = "B2003";
	
	/** 员工不存在*/
	String OMG_EMP_NOT_EXIST = "B1401";
	String OMG_EMP_NOT_EXIST_MSG = "钉钉工号不存在";
	
	/** 员工已删除 */
	String OMG_EMP_DELETE = "B1402";
	String OMG_EMP_DELETE_MSG = "钉钉工号对应的员工已删除";
	
	/** 乾坤员工已跟OMG员工绑定 */
	String CMS_EMP_BIND = "B1403";
	String CMS_EMP_BIND_MSG = "乾坤员工已被钉钉工号{0}绑定";
	
	/** 钉钉工号对应的员工的所有机构都已存在绑定关系 */
	String OMG_EMP_ALL_ORG_BIND = "B1404";
	String OMG_EMP_ALL_ORG_BIND_MSG = "钉钉工号对应的员工的所有机构都已存在绑定关系";
	
	/** 乾坤机构未绑定omg机构 */
	String CMS_ORG_NOT_BIND_OMG_ORG = "B1405";
	String CMS_ORG_NOT_BIND_OMG_ORG_MSG = "乾坤员工机构未绑定OMG机构";
	
	/** 乾坤机构绑定的omg机构不存在 */
	String OMG_ORG_NOT_EXIST = "B1406";
	String OMG_ORG_NOT_EXIST_MSG = "乾坤员工机构绑定的OMG机构不存在";

	/** OMG员工已绑定 */
	String OMG_EMP_BOUND  = "B1407";
	String OMG_EMP_BOUND_MSG = "钉钉工号已被乾坤员工{0}(员工编号)绑定";
	String OMG_EMP_BIND_MSG = "钉钉工号已被乾坤员工{0}(员工编号)绑定";
	
	/** 乾坤员工机构绑定的OMG机构不在员工的机构列表内 	 */
	String CMS_BIND_OMG_ORG_NO_AGREEMENT_OMG= "B1408";
	String CMS_BIND_OMG_ORG_NO_AGREEMENT_OMG_MSG = "乾坤员工机构绑定的OMG机构不在员工的机构列表内";
	
	/** 乾坤员工所属结算网点不在员工的机构列表内*/
	String CMS_CONTRACT_NO_AGREEMENT_OMG= "B1409";
	String CMS_CONTRACT_NO_AGREEMENT_OMG_MSG = "乾坤员工所属结算网点绑定的OMG机构不在员工的机构列表内";
	
	/** 乾坤员工机构绑定的OMG隶属机构下已有承包区绑定乾坤员工 */
	String OMG_EMP_CONTRACT_AFFILIATED_ORG_BIND = "B1410";
	String OMG_EMP_CONTRACT_AFFILIATED_ORG_BIND_MSG = "乾坤员工机构绑定的OMG隶属机构下已有承包区绑定乾坤员工";
	
	/** 乾坤员工机构绑定的OMG隶属机构下已有机构绑定乾坤员工 */
	String OMG_EMP_AFFILIATED_ORG_BIND = "B1411";
	String OMG_EMP_AFFILIATED_ORG_BIND_MSG = "乾坤员工机构绑定的OMG隶属机构下已有机构绑定乾坤员工";
	
	/** 钉钉工号的机构列表中不存在承包区机构 */
	String OMG_EMP_ORG_NO_CONTAINS_CONTRACT = "B1412";
	String OMG_EMP_ORG_NO_CONTAINS_CONTRACT_MSG = "钉钉工号的机构列表中不存在【{0}】(乾坤员工的机构绑定的OMG机构)的承包区";
}
