package cn.uce.omg.portal.exception;

import cn.uce.base.exception.BusinessException;

/**
 * 角色异常定义
 * @author zhangdeng
 */
public class RoleException extends BusinessException {

	private static final long serialVersionUID = 590525254182760551L;
	
	/**
	  * 异常的构造方法
	  * @param errCode
	  * @since
	 */
	public RoleException(String code){
		super();
		super.code = code;
	}
	
	public RoleException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	public RoleException(String code, String msg) {
		super(code, msg);
	}
}
