package cn.uce.portal.common.base;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import cn.uce.base.exception.BusinessException;
import cn.uce.base.exception.DataIntegrityViolationException;
import cn.uce.base.exception.DuplicateKeyException;
import cn.uce.base.exception.MissingParameterException;
import cn.uce.base.exception.TypeMismatchException;
import cn.uce.base.exception.UnknownException;
import cn.uce.base.page.Pagination;
import cn.uce.portal.common.convert.DateConvertEditor;
import cn.uce.portal.common.i18n.Resources;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * <p>Description: 控制器基类 </p>
 * @author fsp
 * @date 2017年3月7日
 */
public abstract class BaseController {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 返回成功不带msg
	 * @return
	 */
	protected Map<String, Object> returnSuccess() {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        return map;
	}
    	
	/**
	 * 返回成功,可带msg
	 * @return
	 */
	protected Map<String, Object> returnSuccess(String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("message",msg);
        return map;
    }
	
	/**
	 * 返回成功,带返回参数
	 * @return
	 */
	protected Map<String, Object> returnSuccess(Object data) {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        if(data != null) {
        	if(data instanceof Pagination<?>) {
        		Pagination<?> page = (Pagination<?>) data;
        		map.put("rows", page.getData());
        		if(null!=page.getPage()){
        			map.put("total", page.getPage().getTotal());
        		}
        	}else if(data instanceof List<?>) {
        		map.put("data", data);
        	}else {
        		map.put("data", data);
        	}
        }
        return map;
    }
	
	/**
	 * 返回成功,带返回参数
	 * @return
	 */
	protected Map<String, Object> returnSuccess(Object data,String message) {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        if(data != null) {
        	if(data instanceof Pagination<?>) {
        		Pagination<?> page = (Pagination<?>) data;
        		map.put("rows", page.getData());
        		map.put("total", page.getPage().getTotal());
        	}else if(data instanceof List<?>) {
        		map.put("data", data);
        	}else {
        		map.put("data", data);
        	}
        }
        map.put("message",message);
        return map;
    }
	
	/**
	 * 返回失败msg
	 * @return
	 */
	protected Map<String, Object> returnError(String meg) {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", false);
        map.put("message", meg);
        return map;
    }
	
	protected Map<String, Object> returnError(String code, String meg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		map.put("code", code);
		map.put("message", meg);
		return map;
	}
	
	/**
	 * 通过传入业务异常，从异常中得到异常类型，得到异常类的国际化信息，并返回"error"
	 * @param BusinessException 业务异常
	 * @return String ERROR
	 */
	protected Map<String, Object> returnError(BusinessException e) {
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("success", false);
	     map.put("message", e.getMessage());
	     return map;
	}
	
	protected void printStackTraceAsCause(Exception ex,StringBuffer buffer) {
		if(ex == null) {
			return ;
		}
		String cause =ex.getCause()!=null ? ex.getCause().getMessage() : ex.getMessage();
		if(StringUtils.isNotBlank(cause)) {
			if(cause.length() > 500) {
				buffer.append(cause.substring(0, 500));
			}else {
				buffer.append(cause);
			}
		}
	}
	
	/**
	 * 前提 String 日期 转换为 javaBean 对应 Date 
	 * @author zhangd
	 * @date 2015-02-07
	 * @param binder
	 */
	@InitBinder
	private void dateBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
	}
	
	/**
	 * 
	 * @Description:统一异常处理
	 * @param request
	 * @param response
	 * @param ex
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	public void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
		logger.error("框架捕获到异常",ex);
		Map<String, Object> map = new HashMap<String, Object>();
		String message = null;
		StringBuffer stackTrace = null;
		String errorCode=null;
		try {
			if(ex instanceof UnauthorizedException) {
				message = Resources.getMessage("error.system.unauthorized");
			}else if (ex instanceof BusinessException) {
				errorCode = ((BusinessException) ex).getCode();
				message = ((BusinessException) ex).getMessage();
				message = (message == null) ? Resources.getMessage(errorCode) : Resources.getMessage(message);
			} else if (ex instanceof DataIntegrityViolationException) {
				message = Resources
						.getMessage(DataIntegrityViolationException.ERROR_CODE);
			} else if (ex instanceof DuplicateKeyException) {
				message = Resources
						.getMessage(DuplicateKeyException.ERROR_CODE);
			} else if (ex instanceof MissingParameterException) {
				message = Resources
						.getMessage(MissingParameterException.ERROR_CODE);
			} else if (ex instanceof TypeMismatchException) {
				message = Resources
						.getMessage(TypeMismatchException.ERROR_CODE);
			} else if (ex instanceof UnknownException) {
				message = Resources.getMessage(UnknownException.ERROR_CODE);
			} else {
				//未定义的异常类型，增加输出异常堆栈信息
				stackTrace = new StringBuffer(2000);
				printStackTraceAsCause(ex, stackTrace);
				message = Resources.getMessage(UnknownException.ERROR_CODE);
			}
		}catch(Exception e) {
			//执行到这则属于未拿到国际化信息，提示message信息原样输出
			if(errorCode!=null)
			message = ex.getMessage() != null ? ex.getMessage() : errorCode;
		}
		map = errorCode == null ? returnError(message) : returnError(errorCode, message);
		if(stackTrace != null) {
			map.put("stackTrace", stackTrace.toString());
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=utf-8");
		String str = JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}
	
}
