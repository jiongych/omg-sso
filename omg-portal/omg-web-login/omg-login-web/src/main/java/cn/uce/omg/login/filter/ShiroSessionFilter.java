package cn.uce.omg.login.filter;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.AccessControlFilter;

import cn.uce.omg.login.biz.ICasBiz;
import cn.uce.omg.portal.biz.IOrgBiz;
import cn.uce.omg.portal.biz.IUserBiz;
import cn.uce.omg.portal.util.Constants;
import cn.uce.omg.sso.vo.AuthCheckVo;
import cn.uce.omg.sso.vo.AuthResultVo;
import cn.uce.web.common.base.CurrentUser;

/**
 * @Description:(ShiroSessionFilter) 
 * @author XJ
 * @date 2017年7月5日 下午6:34:08
 */
public class ShiroSessionFilter extends AccessControlFilter{
	public Log log = LogFactory.getLog(ShiroSessionFilter.class);
	@Resource
	private ICasBiz casBiz;
	@Resource
	private IUserBiz userBiz;
	@Resource
	private IOrgBiz orgBiz;
	
	/*private Date reshDate = new Date();
	
	*//**
	 * @return the reshDate
	 *//*
	public Date getReshDate() {
		return reshDate;
	}

	*//**
	 * @param reshDate the reshDate to set
	 *//*
	public void setReshDate(Date reshDate) {
		this.reshDate = reshDate;
	}*/

	class CookieVo{
		private String token ;
		private String userName ;
		private String empCode ;
		private String empName ;
		private Integer orgId;
		private String cmsOrgId ; 
		private String cmsOrgIdStr ;
		private Boolean updPwdFlag;
		private Integer updPwdIntervalDay;
		
	}
	/**
	 * (非 Javadoc) 
	* <p>Title: isAccessAllowed</p> 
	* <p>Description: isAccessAllowed</p> 
	* @param request
	* @param response
	* @param mappedValue
	* @return
	* @throws Exception 
	* @see org.apache.shiro.web.filter.AccessControlFilter#isAccessAllowed(javax.servlet.ServletRequest, javax.servlet.ServletResponse, java.lang.Object)
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		//操作标识
		String operateFlag = request.getParameter(Constants.OPERATE_FLAG);
		CookieVo cookieVo = this.new CookieVo();
		cookieVo.cmsOrgId = request.getParameter("cmsOrgId");
		
		if(validateSessionExist(request, response)){
			request.setAttribute("loginSuccess", "success");
			/*Date dt = new Date();
			if(dt.getTime()-reshDate.getTime()>55*60*1000){
				this.setReshDate(dt);
				requestSSO(request, response, cookieVo);
			}*/
			return true;
		}else if(validateCookieExist(request, response,cookieVo)){
			return requestSSO(request, response, cookieVo);
		}else{
			return false;
		}
		//cookie存在
		/*if(validateCookieExist(request, response, cookieVo)){
			//如果是切换部门请求，直接上sso验证cookie
			if(!validateSessionExist(request, response)){
				System.out.println("上sso");
				return requestSSO(request, response, cookieVo);
			}
			//session存在,刷session和cookie保持一致
			if(!getSubject(request, response).getSession().getAttribute("empCode").equals(cookieVo.empCode) || Constants.CHANGE_ORG.equals(operateFlag)){
				refreshSessionFromCookie(cookieVo, false);
			}
			request.setAttribute("loginSuccess", "success");
			return true;
		}
		*/
    	//验证session存在
		/*if(validateSessionExist(request, response)){
			SecurityUtils.getSubject().logout();
		}	
		return false;*/
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: onAccessDenied</p> 
	* <p>Description: onAccessDenied</p> 
	* @param request
	* @param response
	* @return
	* @throws Exception 
	* @see org.apache.shiro.web.filter.AccessControlFilter#onAccessDenied(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		//如果是ajax请求响应头会有，x-requested-with，不是ajax请求则继续后续拦截器
		HttpServletRequest httpRequest = (HttpServletRequest) request;
    	if (httpRequest.getHeader("x-requested-with") == null
                || !httpRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest") 
                || "GET".equals(httpRequest.getMethod())) {
    		return true;
    	}
		//如果是ajax请求，且cookies失效，则发送606错误，使前端跳转到登陆界面。
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String location = httpRequest.getRequestURL().toString();
		location = location.replaceAll(httpRequest.getServletPath().toString(), "");
		httpResponse.setHeader("Location", location + getLoginUrl());
		httpResponse.sendError(606);
		return false;
	}

	/**
	 * @Description: (findLoginUser) 
	 * @return
	 * @author XJ
	 * @throws UnsupportedEncodingException 
	 * @date 2017年7月5日 下午2:54:42
	 */
	private CurrentUser findCurrentUser(String empCode,Integer orgId) throws UnsupportedEncodingException{
		CurrentUser currentUser = userBiz.findCurrentUser(empCode);
		boolean isMainOrg = false;
		for(Map<String,Object> loginEmpOrgRel : currentUser.getOrgRefRel()){
			if(orgId.equals((Integer)loginEmpOrgRel.get("orgId"))){
				isMainOrg = true;
				currentUser.setOrgId((Integer) loginEmpOrgRel.get("orgId"));
				currentUser.setOrgCode((String) loginEmpOrgRel.get("orgCode"));
				currentUser.setOrgType((Integer) loginEmpOrgRel.get("orgType"));
				currentUser.setOrgName((String) loginEmpOrgRel.get("orgName"));
				currentUser.setCmsOrgId(loginEmpOrgRel.get("otherOrgId").toString());
				currentUser.setCmsBaseOrgCode((String) loginEmpOrgRel.get("otherBaseOrgCode"));
				currentUser.setCmsOrgType((Integer) loginEmpOrgRel.get("otherOrgType"));
				currentUser.setCmsOrgName((String) loginEmpOrgRel.get("otherOrgName"));
			}
		}
		if(!isMainOrg){
			Map<String,Object> loginEmpOrgRel = currentUser.getOrgRefRel().get(0);
			currentUser.setOrgId((Integer) loginEmpOrgRel.get("orgId"));
			currentUser.setOrgCode((String) loginEmpOrgRel.get("orgCode"));
			currentUser.setOrgType((Integer) loginEmpOrgRel.get("orgType"));
			currentUser.setOrgName((String) loginEmpOrgRel.get("orgName"));
			currentUser.setCmsOrgId(loginEmpOrgRel.get("otherOrgId").toString());
			currentUser.setCmsBaseOrgCode((String) loginEmpOrgRel.get("otherBaseOrgCode"));
			currentUser.setCmsOrgType((Integer) loginEmpOrgRel.get("otherOrgType"));
			currentUser.setCmsOrgName((String) loginEmpOrgRel.get("otherOrgName"));
		}
		currentUser.setSystemCode(casBiz.getSystemCode());
		//TODO 这个结算在用，暂时保留
		//currentUser.setRoleCodeList(fspRoleBiz.findRoleCodeByUser(empCode));
		//TODO
		return currentUser;
	}
	
	/**
	 * @Description: (validateSessionExist) 
	 * @param request
	 * @param response
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午10:11:59
	 */
	private boolean validateSessionExist(ServletRequest request, ServletResponse response){
		Session session = getSubject(request, response).getSession();
		String token = (String) session.getAttribute("token");
		String userName = (String) session.getAttribute("userName");
		String empCode = (String) session.getAttribute("empCode");
		//如果session存在
		if (!StringUtils.isBlank(token) && !StringUtils.isBlank(userName) && !StringUtils.isBlank(empCode)) {
			//sso切换结构，portal不切换，---------------start 2019.04.18
			Cookie[] cookies = ((HttpServletRequest)request).getCookies();
			if (cookies == null) {
				return true;
			}
			String cmsOrgId = (String) session.getAttribute("cmsOrgId");
			for (Cookie cookie : cookies) {
				if (cookie == null) {
					continue;
				}
				if ("cmsOrgId".equals(cookie.getName())) {
					if (StringUtils.equals(cmsOrgId, cookie.getValue())) {
						return true;
					} else {
						return false;
					}
				}
			}
			//sso切换结构，portal不切换，---------------end 2019.04.18
			return true;
		}
		return false;
	}
	
	/**
	 * @Description: (validateCookieExist) 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 * @author XJ
	 * @date 2017年7月29日 上午10:28:10
	 */
	private boolean validateCookieExist(ServletRequest request, ServletResponse response,CookieVo cookieVo) {
		//session不存在，获取cookie
		Cookie[] cookies = ((HttpServletRequest)request).getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie != null) {
					if ("token".equals(cookie.getName())) {
						cookieVo.token = cookie.getValue();
					} else if ("userName".equals(cookie.getName())) {
						cookieVo.userName = cookie.getValue();
					} else if ("empCode".equals(cookie.getName())) {
						cookieVo.empCode = cookie.getValue();
					} else if("empName".equals(cookie.getName())){
						cookieVo.empName = cookie.getValue();
					} else if("omgOrgId".equals(cookie.getName())){
						cookieVo.orgId = Integer.valueOf(cookie.getValue());
					} else if("encryptOrgId".equals(cookie.getName())){
						if (cookie.getValue().matches("\\d+")) {
							cookieVo.orgId = Integer.valueOf(cookie.getValue());
						} else {
							cookieVo.orgId = Integer.valueOf(new String(Base64.decodeBase64(cookie.getValue())));
						}
					}else if("cmsOrgId".equals(cookie.getName())){
						if(cookieVo.cmsOrgId == null || cookieVo.cmsOrgId.equals("")){
							cookieVo.cmsOrgId = cookie.getValue();
						}
					} else if("cmsOrgIdStr".equals(cookie.getName())){
						cookieVo.cmsOrgIdStr = cookie.getValue();
					} else if ("updPwdFlag".equals(cookie.getName())) {
						cookieVo.updPwdFlag = Boolean.valueOf( cookie.getValue());
					} else if ("updPwdIntervalDay".equals(cookie.getName())) {
						cookieVo.updPwdIntervalDay = Integer.valueOf(cookie.getValue());
					}
				}
			}
		}
		//cookie存在
		if (!StringUtils.isBlank(cookieVo.token) && !StringUtils.isBlank(cookieVo.userName) && !StringUtils.isBlank(cookieVo.empCode)) {
			return true;
		}
		return false;
	}
	
	/**
	 * @Description: (requestSSO) 
	 * @param request
	 * @param response
	 * @param cookieVo
	 * @return
	 * @throws UnsupportedEncodingException
	 * @author XJ
	 * @date 2017年8月31日 下午5:28:59
	 */
	private boolean requestSSO(ServletRequest request, ServletResponse response,CookieVo cookieVo) throws UnsupportedEncodingException{
		AuthCheckVo authCheckVo = new AuthCheckVo();
		authCheckVo.setSystemCode(casBiz.getSystemCode());
		authCheckVo.setTokenId(cookieVo.token);
		authCheckVo.setEmpCode(cookieVo.empCode);
		authCheckVo.setCurrentTime(new Date());
		AuthResultVo authResultVo = casBiz.authCheck(authCheckVo);
		if(authResultVo!=null && authResultVo.getTokenId()!=null){
			//创建session
			String loginIp = request.getRemoteAddr();
			refreshSessionFromCookie(cookieVo, true, loginIp);
			//shiro登录
			UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(cookieVo.userName, cookieVo.token, cookieVo.empCode);
			SecurityUtils.getSubject().login(usernamePasswordToken);
			request.setAttribute("loginSuccess", "success");
			return true;
		}
		return false;
	}
	
	/**
	 * @Description: (refreshSessionFromCookie) 
	 * @param cookieVo
	 * @param created
	 * @throws UnsupportedEncodingException
	 * @author XJ
	 * @date 2017年9月13日 下午5:23:51
	 */
	private void refreshSessionFromCookie(CookieVo cookieVo,boolean created, String loginIp) throws UnsupportedEncodingException{
		Session shiroSession = SecurityUtils.getSubject().getSession(created);
		shiroSession.setAttribute("empCode", cookieVo.empCode);
		shiroSession.setAttribute("userName", cookieVo.userName);
		shiroSession.setAttribute("token", cookieVo.token);
		shiroSession.setAttribute("updPwdFlag", cookieVo.updPwdFlag);
		shiroSession.setAttribute("updPwdIntervalDay", cookieVo.updPwdIntervalDay);
		shiroSession.setAttribute("cmsOrgId", cookieVo.cmsOrgId);
		//往session中放loginUser对象
		CurrentUser userInfo = findCurrentUser(cookieVo.empCode, cookieVo.orgId);
		userInfo.setLoginIp(loginIp);
		shiroSession.setAttribute(cn.uce.web.common.constants.Constants.CURRENT_USER, userInfo);
	}
}
