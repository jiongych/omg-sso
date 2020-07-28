/** 
 * @项目名称: FSP
 * @文件名称: GatewayConstants 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.constant;

/**
 * GatewayConstants  
 * @Description: GatewayConstants  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public interface GatewayConstants {

	/** 应用名称 */
	String APP_NAME = "cas";

	/** 默认编码类型 */
	static final String DEFAULT_CHARSET = "utf-8";

	/** 内容格式 XML */
	static final String CONTENT_TYPE_XML = "xml";

	/** 内容格式 JSON */
	static final String CONTENT_TYPE_JSON = "json";

	/** 签名方式MD5 */
	static final String SIGN_TYPE_MD5 = "md5";

	/** request参数：请求/返回数据编码 */
	static final String PARAM_CHARSET = "charset";

	/** request参数：接口名称 */
	static final String PARAM_SERVICE = "serviceName";

	/** request参数：系统编号 */
	static final String SYSTEM_CODE = "systemCode";

	/** request参数：签名 */
	static final String PARAM_SIGN = "dataSign";

	/** request参数：业务参数与返回结果的内容格式: XML/JSON，默认为XML */
	static final String PARAM_CONTENT_TYPE = "dataType";

	/** request参数：业务参数内容 */
	static final String PARAM_CONTENT = "data";

	/** 客户编号 */
	static final String PARAM_CUSTOMERID = "customerId";

	/** request参数：时间戳 */
	static final String PARAM_TIMESTAMP = "timestamp";

	/** request参数：签名方式 */
	static final String PARAM_SIGN_TYPE = "signType";
}
