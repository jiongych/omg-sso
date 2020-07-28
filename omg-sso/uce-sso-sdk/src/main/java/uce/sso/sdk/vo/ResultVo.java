/** 
 * @项目名称: FSP
 * @文件名称: ResultVo implements Serializable
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.vo;

import java.io.Serializable;


/**
 * ResultVo implements Serializable 
 * @Description: ResultVo implements Serializable 
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class ResultVo implements Serializable{

	/**  */
	private static final long serialVersionUID = 1L;
	/** 是否成功 */
	private String isSuccess;
	/** 异常码 */
	private String errorCode;

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
