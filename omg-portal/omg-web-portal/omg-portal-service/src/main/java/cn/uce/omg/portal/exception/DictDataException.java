package cn.uce.omg.portal.exception;

import cn.uce.base.exception.BusinessException;

/**
 * 系统类型异常定义
 * @author huangting
 */
public class DictDataException extends BusinessException {

	private static final long serialVersionUID = 590525254182760551L;
	
	/**
	  * 异常的构造方法
	  * @param errCode
	  * @since
	 */
	public DictDataException(String code){
		super();
		super.code = code;
	}
	
	public DictDataException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	public DictDataException(String code, String msg) {
		super(code, msg);
	}
}
