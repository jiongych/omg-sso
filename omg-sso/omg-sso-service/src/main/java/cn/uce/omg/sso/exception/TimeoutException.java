package cn.uce.omg.sso.exception;

import cn.uce.base.exception.BusinessException;

/**
 * add by zhagnfh 2017-09-14 
 * @Description: 锁超时异常
 * @author user
 * @date 2017年9月14日 下午3:18:05
 */
public class TimeoutException extends BusinessException {

	  /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 异常code
     */
    private static String timeout = "lock.timeout";

    public TimeoutException() {
        super(timeout);
    }

}
