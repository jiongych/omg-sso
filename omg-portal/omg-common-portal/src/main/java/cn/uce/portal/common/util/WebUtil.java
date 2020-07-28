package cn.uce.portal.common.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.util.WebUtils;

import cn.uce.portal.common.base.CurrentUser;
import cn.uce.portal.common.constants.Constants;


/**
 * 
 * <p>Description: Web层辅助类 </p>
 * @author mshi
 * @date 2017年3月8日
 */
public final class WebUtil {

	private WebUtil() {

	}
	
	/**
	 * 
	 * @Description:保存当前用户到shiro session
	 * @param user
	 */
	public static final void saveCurrentUser(Object user) {
		setSession(Constants.CURRENT_USER, user);
	}
	
	/**
	 * 
	 * @Description:移除当前用户
	 * @param request 
	 */
	public static final void removeCurrentUser(HttpServletRequest request) {
		request.getSession().removeAttribute(Constants.CURRENT_USER);
	}
	
	/**
	 * 
	 * @Description: 获取当前用户
	 * @return
	 * @throws InvalidSessionException
	 */
	public static final CurrentUser getCurrentUser() throws InvalidSessionException {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			if (null != session) {
				return  (CurrentUser)session.getAttribute(Constants.CURRENT_USER);
			}
		}
		return null;
	}
	
	/**
	 * 获得参数Map
	 * @param request
	 * @return
	 */
	public static final Map<String, Object> getParameterMap(HttpServletRequest request) {
		return WebUtils.getParametersStartingWith(request, null);
	}
	
	/**
	 * 
	 * @Description: 将一些数据放在ShiroSession中
	 * @param key
	 * @param value
	 */
	public static final void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}
	
}
