package cn.uce.omg.portal.exception;

import cn.uce.base.exception.BusinessException;

/**
 * 公司公告异常定义
 *<pre>
 * CompanyNoticeException
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年3月28日下午2:48:00
 */
public class CompanyNoticeException extends BusinessException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 异常构造方法
	 * @param code
	 */
	public CompanyNoticeException(String code) {
		super();
		super.code = code;
	}
	
	/**
	 * 异常构造方法
	 * @param code
	 * @param msg
	 */
	public CompanyNoticeException(String code, String msg) {
		super(code, msg);
	}
	
	/**
	 * 异常构造方法
	 * @param code
	 * @param msg
	 * @param cause
	 */
	public CompanyNoticeException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}
}
