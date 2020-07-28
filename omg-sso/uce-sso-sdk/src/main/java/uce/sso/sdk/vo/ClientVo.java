/** 
 * @项目名称: FSP
 * @文件名称: ClientVo implements Serializable 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.vo;

import java.io.Serializable;

import uce.sso.sdk.constant.AuthConstants;

/**
 * 发送消息VO
 * @author tanchong
 *
 */
public class ClientVo implements Serializable {

	/**  */
	private static final long serialVersionUID = 1L;

	/** 连接地址 */
	private String sendUrl;
	/** 字符集 */
	private String charset;
	/** 签名方式 */
	private String signType;
	/** 数据格式 */
	private String dataType;
	/** 密钥 */
	private String securityKey;
	/** 系统编码 */
	private String systemCode;
	/** 调用的服务(映射的方法名) */
	private String serviceName;
	/** 连接时间：默认2分钟 */
	private int connectTimeout;
	/** 读取时间：默认2分钟 */
	private int readTimeout;

	public String getSendUrl() {
		return sendUrl;
	}

	public void setSendUrl(String sendUrl) {
		this.sendUrl = sendUrl;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getSecurityKey() {
		return securityKey;
	}

	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public int getConnectTimeout() {
		if (connectTimeout == 0) {
			connectTimeout = AuthConstants.SSO_CLIENT_CONNECT_TIME_OUT;
		}
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getReadTimeout() {
		if (readTimeout == 0) {
			readTimeout = AuthConstants.SSO_CLIENT_READ_TIME_OUT;
		}
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

}
