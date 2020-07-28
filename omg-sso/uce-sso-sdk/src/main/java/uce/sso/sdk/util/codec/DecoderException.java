/** 
 * @项目名称: FSP
 * @文件名称: DecoderException extends Exception 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.util.codec;

public class DecoderException extends Exception {
	private static final long serialVersionUID = -8524444543640582985L;

	public DecoderException(String message, Throwable cause) {
		super(message, cause);
	}

	public DecoderException(String pMessage) {
		super(pMessage);
	}
}
