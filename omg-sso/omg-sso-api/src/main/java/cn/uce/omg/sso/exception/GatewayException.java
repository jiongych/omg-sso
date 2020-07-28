/** 
 * @项目名称: FSP
 * @文件名称: GatewayException extends Exception 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.exception;

/**
 * 认证异常
 * @author tanchong
 *
 */
public class GatewayException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3373069500415662199L;
	/**
	 * 错误编码
	 */
	private String errorCode;
	/**
	 * 错误消息
	 */
	private String errorMessage;

	/**
	 * 错误次数
	 */
	private Integer errorCount;

	/**
	 * 
	 * @param errorCode
	 */
	public GatewayException(String errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}

	/**
	 * 
	 * @param errorCode
	 * @param errorMessage
	 */
	public GatewayException(String errorCode, String errorMessage) {
		super(errorCode);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * 
	 * @param cause
	 * @param errorCode
	 */
	public GatewayException(Throwable cause, String errorCode) {
		super(cause);
		this.errorCode = errorCode;
	}

	/**
	 * 
	 * @param cause
	 * @param errorCode
	 * @param errorMessage
	 */
	public GatewayException(Throwable cause, String errorCode, String errorMessage) {
		super(cause);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 *
	 * @param errorCount
	 * @param errorCode
	 * @param errorMessage
	 */
	public GatewayException(Integer errorCount, String errorCode, String errorMessage) {
		this.errorCount = errorCount;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}


	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @return the errorCount
	 */
	public Integer getErrorCount() {
		return errorCount;
	}
	/**
	 * @param errorCount the errorCount to set
	 */
	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}

}
