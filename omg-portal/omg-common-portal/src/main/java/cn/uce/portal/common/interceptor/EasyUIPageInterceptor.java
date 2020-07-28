package cn.uce.portal.common.interceptor;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.uce.base.page.Page;
import cn.uce.base.page.Sort;
import cn.uce.portal.common.model.EasyUIPage;

/**
 * 
 * <p>Description: 对前端easyUI分页参数进行统一拦截封装</p>
 * @author mshi
 * @date 2017年3月9日
 */
public class EasyUIPageInterceptor extends HandlerInterceptorAdapter {
	
	public static final String CUR_PAGE = "page";
	public static final String PAGE_SIZE = "rows";
	public static final String ORDER_COL = "sort";
	public static final String ORDER = "order";
	public static final String TOTAL_ROWS = "total";
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if ((handler instanceof HandlerMethod)) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			if (Map.class.isAssignableFrom(handlerMethod.getReturnType()
					.getParameterType())) {
				MethodParameter[] parameters = handlerMethod.getMethodParameters();
				for (MethodParameter parameter : parameters) {
					if (parameter.getParameterType().isAssignableFrom(Page.class)) {
						before(request);
						break;
					}
				}
			}
		}
		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if ((handler instanceof HandlerMethod)) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			if (Collection.class.isAssignableFrom(handlerMethod.getReturnType()
					.getParameterType())) {
				MethodParameter[] parameters = handlerMethod
						.getMethodParameters();
				for (MethodParameter parameter : parameters)
					if (parameter.getParameterType().isAssignableFrom(
							EasyUIPage.class)) {
						after(request, modelAndView);
						break;
					}
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void before(HttpServletRequest request) {
		Integer currentPage = getInteger(request, CUR_PAGE);
		if (currentPage != null) {
			Map map = request.getParameterMap();
			setLocked(map, false);
			Page page = new Page();
			page.setQueryCount(true);
			page.setCurrentPage(currentPage);
			map.remove(CUR_PAGE);
			page.setPageSize(getInteger(request, PAGE_SIZE));
			map.remove(PAGE_SIZE);
			String order = getParameter(request, "order");
			if (order != null) {
				String sortColumn = getParameter(request, "sort");
				Sort sort = new Sort();
				sort.setField(sortColumn);
				map.remove("sort");
				if(order.equalsIgnoreCase(Sort.DESC)) {
					sort.setType(Sort.DESC);
				}else {
					sort.setType(Sort.ASC);
				}
				page.setSort(sort);
				map.remove("order");
			}
			setLocked(map, true);
			request.setAttribute(CUR_PAGE, page);
		}
	}

	private void after(HttpServletRequest request, ModelAndView modelAndView) {
		request.removeAttribute("page");
	}

	private Integer getInteger(HttpServletRequest request, String paramName) {
		try {
			return Integer.valueOf(getParameter(request, paramName));
		} catch (Exception e) {
		}
		return null;
	}

	private String getParameter(HttpServletRequest request, String paramName) {
		String value = request.getParameter(paramName);
		if (value == null)
			return null;
		return value.trim();
	}

	private void setLocked(Map<String, ?> map, boolean locked) {
		try {
			BeanUtils.getPropertyDescriptor(map.getClass(), "locked").getWriteMethod()
					.invoke(map, new Object[] { Boolean.valueOf(locked) });
		} catch (Exception e) {
		}
	}
}
