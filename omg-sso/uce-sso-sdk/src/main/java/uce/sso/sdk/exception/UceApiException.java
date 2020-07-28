/** 
 * @项目名称: FSP
 * @文件名称: UceApiException extends Exception 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.exception;

/**
 * UceApiException extends Exception  
 * @Description: UceApiException extends Exception  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class UceApiException extends Exception {

	/**  */
	private static final long serialVersionUID = 1L;

	private String errCode;
	private String errMsg;

	public UceApiException() {}

	public UceApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public UceApiException(String message) {
		super(message);
	}

	public UceApiException(Throwable cause) {
		super(cause);
	}

	public UceApiException(String errCode, String errMsg) {
		super(errCode + ":" + errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return this.errCode;
	}

	public String getErrMsg() {
		return this.errMsg;
	}
}
