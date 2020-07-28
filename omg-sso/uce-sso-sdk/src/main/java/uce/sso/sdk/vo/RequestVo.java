/** 
 * @项目名称: FSP
 * @文件名称: RequestVo 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.vo;

/**
 * HTTP请求参数DTO
 * 
 * @author xiezhh
 * 
 */
public class RequestVo {

	/** request参数：请求/返回数据编码 */
	private String charset;

	/** request参数：接口名称 */
	private String serviceName;

	/** request参数：合作者身份ID */
	private String systemCode;

	/** request参数：签名 */
	private String sign;

	/** request参数：业务参数与返回结果的内容格式: XML/JSON，默认为XML */
	private String contentType;

	/** request参数：业务参数内容 */
	private String content;

	/** 合作伙伴密钥，不是从HttpRequest里获取的，是CMS系统获取的 */
	private String secrtKey;

	/** 客户编号 */
	private String customerId;

	/** request参数：签名方式 */
	private String signType;

	/** request参数：时间戳 */
	private String timestamp;

	/**
	 * @return the charset
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * @param charset the charset to set
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	/**
	 * @return the signType
	 */
	public String getSignType() {
		return signType;
	}

	/**
	 * @param signType the signType to set
	 */
	public void setSignType(String signType) {
		this.signType = signType;
	}

	/**
	 * @return the sign
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * @param sign the sign to set
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the secrtKey
	 */
	public String getSecrtKey() {
		return secrtKey;
	}

	/**
	 * @param secrtKey the secrtKey to set
	 */
	public void setSecrtKey(String secrtKey) {
		this.secrtKey = secrtKey;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

}
