/** 
 * @项目名称: FSP
 * @文件名称: ClientException extends Exception 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.exception;

/**
 * 客户端异常类
 */
public class ClientException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2155382375819767054L;

	public ClientException(String errorCode) {
		super(errorCode);
	}

	public ClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientException(Throwable cause) {
		super(cause);
	}
}
