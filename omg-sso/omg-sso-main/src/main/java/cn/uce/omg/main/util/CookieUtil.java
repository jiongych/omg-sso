package cn.uce.omg.main.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * <p>Description: cookie相关操作工具类 </p>
 * @author mshi
 * @date 2017年3月4日
 */
public class CookieUtil {
    
	/**
	 * @Description: 设置无生命周期cookie(既随浏览器关闭消失)
	 * @param response
	 * @param name cookie名称
	 * @param value cookie值
	 */
	public static void addCookie(HttpServletResponse response,String name,String value) {
		if(StringUtils.isEmpty(name)) {
			return;
		}
	    Cookie cookie = new Cookie(name, value);
	    response.addCookie(cookie);
	}
	
	/**
	 * 
	 * @Description: 设置带生命周期的cookie
	 * @param response
	 * @param name
	 * @param value
	 * @param age 单位秒  注：age > 0,表示cookie将会保存在磁盘中，数值表示保存时间
	 *                      age < 0,表示cookie保存在浏览器内存中，关闭既丢失
	 *                      age == 0,表示删除同名的cookie数据    
	 */
	public static void addCookie(HttpServletResponse response,String name,String value,int age) {
		if(StringUtils.isEmpty(name)) {
			return;
		}
	    Cookie cookie = new Cookie(name, value);
	    cookie.setMaxAge(age);
	    response.addCookie(cookie);
	}
	
	/**
	 * 
	 * @Description: 设置带生命周期的cookie
	 * @param response
	 * @param name cookie 名称
	 * @param value
	 * @param age 单位秒  注：age > 0,表示cookie将会保存在磁盘中，数值表示保存时间
	 *                      age < 0,表示cookie保存在浏览器内存中，关闭既丢失
	 *                      age == 0,表示删除同名的cookie数据
	 * @param path 设置cookie有效访问路径(为了避免与其他cookie同名，最好传入)                      
	 */
	public static void addCookie(HttpServletResponse response,String name,String value,int age,String path) {
		if(StringUtils.isEmpty(name)) {
			return;
		}
	    Cookie cookie = new Cookie(name, value);
	    cookie.setMaxAge(age);
	    cookie.setPath(path);
	    response.addCookie(cookie);
	}
	
	/**
	 * 
	 * @Description: 根据名称获取cookie Value值
	 * @param request 
	 * @param name cookie 名称
	 * @return cookie value值
	 */
	public static String getCookieValueByName(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			Map<String, Cookie> cookiesMap = getCookieMap(request);
			if (cookiesMap != null && cookiesMap.containsKey(name)) {
				return cookiesMap.get(name).getValue();
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @Description: 根据名称获取cookie
	 * @param request
	 * @param name cookie名称
	 * @return cookie
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookiesMap = getCookieMap(request);
		if (cookiesMap != null && cookiesMap.containsKey(name)) {
			return cookiesMap.get(name);
		}
		return null;
	}
	
	private static Map<String, Cookie> getCookieMap(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			Map<String, Cookie> cookiesMap = new HashMap<String, Cookie>();
			for (Cookie cookie : cookies) {
				cookiesMap.put(cookie.getName(), cookie);
			}
			return cookiesMap;
		}
		return null;
	}
}
