package cn.uce.omg.portal.vo;

import cn.uce.base.vo.BaseVo;

public class InvokeExceptionInfoVo extends BaseVo {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/** 异常编码*/
	private String exceptionCode;
	/**异常消息*/
	private String exceptionMsg;
	/** 是否需要重试 */
	private Boolean retry;
	/**
	 * @return the exceptionCode
	 */
	public String getExceptionCode() {
		return exceptionCode;
	}
	/**
	 * @return the exceptionMsg
	 */
	public String getExceptionMsg() {
		return exceptionMsg;
	}
	/**
	 * @return the retry
	 */
	public Boolean getRetry() {
		return retry;
	}
	/**
	 * @param exceptionCode the exceptionCode to set
	 */
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	/**
	 * @param exceptionMsg the exceptionMsg to set
	 */
	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
	/**
	 * @param retry the retry to set
	 */
	public void setRetry(Boolean retry) {
		this.retry = retry;
	}
}

