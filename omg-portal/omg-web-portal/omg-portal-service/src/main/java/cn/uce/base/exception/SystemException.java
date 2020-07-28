package cn.uce.base.exception;

import cn.uce.base.exception.BusinessException;

/**
 * 角色异常定义
 * @author zhangdeng
 */
public class SystemException extends BusinessException {

	private static final long serialVersionUID = 590525254182760551L;
	
	/**
	  * 异常的构造方法
	  * @param errCode
	  * @since
	 */
	public SystemException(String code){
		super();
		super.code = code;
	}
	
	public SystemException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	public SystemException(String code, String msg) {
		super(code, msg);
	}
}
