/** 
 * @项目名称: FSP
 * @文件名称: ResponseVo 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.vo;

import java.util.List;

/**
 * 请求返回数据对象
 * @author xiezhh
 *
 */
public class ResponseVo {
	/**
	 * 是否成功：是
	 */
	public static final String TRUE = "T";
	/**
	 * 是否成功：否
	 */
	public static final String FALSE = "F";
	/**
	 * 是否成功
	 */
	private String isSuccess = FALSE;
	/**
	 * 错误编码
	 */
	private String errorCode;
	/**
	 * 错误消息
	 */
	private String errorMessage;
	/**
	 * 请求数据
	 */
	private Object data;
	/**
	 * 请求数据集合
	 */
	private List<Object> dataList;

	public ResponseVo() {

	}

	/**
	 * 
	 * @param isSuccess
	 */
	public ResponseVo(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	/**
	 * 
	 * @param isSuccess
	 * @param errorCode
	 */
	public ResponseVo(String isSuccess, String errorCode) {
		this.isSuccess = isSuccess;
		this.setErrorCode(errorCode);
	}

	/**
	 * 
	 * @param isSuccess
	 * @param errorCode
	 * @param data
	 */
	public ResponseVo(String isSuccess, String errorCode, String data) {
		this.isSuccess = isSuccess;
		this.setErrorCode(errorCode);
		this.data = data;
	}

	/**
	 * @return the isSuccess
	 */
	public String getIsSuccess() {
		return isSuccess;
	}

	/**
	 * @param isSuccess the isSuccess to set
	 */
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * @return the dataList
	 */
	public List<Object> getDataList() {
		return dataList;
	}

	/**
	 * @param dataList the dataList to set
	 */
	public void setDataList(List<Object> dataList) {
		this.dataList = dataList;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
