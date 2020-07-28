package cn.uce.portal.common.interceptor;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import cn.uce.base.page.Page;
/**
 * 
 * <p>Description: 自定义参数装配</p>
 * @author mshi
 * @date 2017年3月9日
 */
public class CustomMethodArgumentsResolver implements
		HandlerMethodArgumentResolver {

	@SuppressWarnings("rawtypes")
	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer modelandviewcontainer,
			NativeWebRequest webRequest,
			WebDataBinderFactory webdatabinderfactory) throws Exception {
		Class paramType = parameter.getParameterType();
		if (Page.class.isAssignableFrom(paramType)) {
			return webRequest.getAttribute(EasyUIPageInterceptor.CUR_PAGE, 0);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class paramType = parameter.getParameterType();
		if (Page.class.isAssignableFrom(paramType)) {
			return true;
		}
		return false;
	}
	
}