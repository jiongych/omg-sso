package cn.uce.omg.login.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.uce.omg.login.biz.ICasBiz;
import cn.uce.omg.login.realm.PermissionsRealm;
import cn.uce.omg.login.util.DomainUtil;
import cn.uce.omg.portal.biz.IMenuBiz;
import cn.uce.omg.portal.biz.IPermissionBiz;
import cn.uce.omg.portal.biz.IUserBiz;
import cn.uce.omg.portal.vo.PortalMenuTreeVo;
import cn.uce.omg.sso.vo.LogoutVo;
import cn.uce.web.common.base.BaseController;
import cn.uce.web.common.base.CurrentUser;
import cn.uce.web.common.util.CookieUtil;
import cn.uce.web.common.util.WebUtil;

/**
 * @Description: (LoginController) 
 * @author XJ
 * @date 2017年7月27日 下午4:26:50
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
	public Log log = LogFactory.getLog(LoginController.class);
	@Resource
	private PermissionsRealm permissionRealm;
	@Resource
	private ICasBiz fspCasBiz;
	@Resource
	private IUserBiz fspUserBiz;
	@Resource
	private IPermissionBiz permissionBiz;
	@Resource
	private IMenuBiz menuBiz;
	
	private final String[] cookieNames = new String[] {"token", "userName", "empCode","empName","cmsOrgId","cmsOrgIdStr","omgOrgId","updPwdFlag","updPwdIntervalDay","lastLoginTime"};
	
	
	/**
	 * @Description: (默认首页的视图跳转) 
	 * @return
	 * @author XJ
	 * @date 2017年7月27日 下午4:24:20
	 */
	@RequestMapping(value = "/loginIndex")
	public String loginIndex(){
		return "login/index";
	}
	
	/**
	 * @Description: (切换部门的ajax请求路径) 
	 * @return
	 * @author XJ
	 * @date 2017年7月27日 下午4:24:57
	 */
	@RequestMapping(value = "/changeOrg")
	@ResponseBody
	public String changeOrg(){
		return "success";
	}
	
	/**
	 * @Description: (home.jsp的Tab标签页) 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @author XJ
	 * @date 2017年7月27日 下午4:25:31
	 */
	@RequestMapping(value = "/home")
	public String errorDetail()
			throws IOException {
		return "common/home";
	}
	
	/**
	 * @Description: (logout：1、调用sso退出 2、删除cookie 3、删session 4、清理Realm缓存 5、跳转登录页) 
	 * @param request
	 * @param response
	 * @return
	 * @author XJ
	 * @throws Exception 
	 * @date 2017年4月25日 下午4:52:15
	 */
	@RequestMapping(value="/logout")
	@ResponseBody
	public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		LogoutVo logoutVo = new LogoutVo();
		logoutVo.setTokenId((String) request.getSession().getAttribute("token"));
		logoutVo.setIpAddr(InetAddress.getLocalHost().getHostAddress());
		logoutVo.setSystemCode(fspCasBiz.getSystemCode());
		logoutVo.setLogoutTime(new Date());
		logoutVo.setEmpCode((String) request.getSession().getAttribute("empCode"));
		fspCasBiz.logout(logoutVo);
		//删除cookie信息
		for (int i = 0; i < cookieNames.length; i++) {
			String name = cookieNames[i];
			Cookie cookie = CookieUtil.getCookieByName(request, name);
			if(cookie != null){
				cookie.setMaxAge(0);
				cookie.setPath("/");
				cookie.setDomain(DomainUtil.getTopDomain(request.getServerName()));
				response.addCookie(cookie);
			}
		}
		//session失效
		session.invalidate();
		//清除shiro授权信息
		permissionRealm.clearCache();
		String relUrl = request.getRequestURL()
				+ (request.getQueryString() == null ? "" : "?" + request.getQueryString());
		String requestUrl = fspCasBiz.getSsoUrl() + "&refUrl=" + relUrl;
		requestUrl = requestUrl.replaceAll("login/logout.do", "login/loginAuthc.do");
		redirectLoginSso(response, requestUrl);
	}
	
	/**
	 * @Description: (跳转登录页) 
	 * @param httpRequest
	 * @param httpResponse
	 * @return
	 * @throws Exception
	 * @author XJ
	 * @date 2017年7月28日 上午8:49:46
	 */
	@RequestMapping(value = "loginAuthc")
	public String loginAuthc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//session中没有，或者cookie中没有，或者cookie中有但是校验token失败,调sso登录
		String loginSuccess = (String) request.getAttribute("loginSuccess");
		if("success".equals(loginSuccess)){
			return "login/index";
		}
		String relUrl = request.getRequestURL()
				+ (request.getQueryString() == null ? "" : "?" + request.getQueryString());
		String requestUrl = fspCasBiz.getSsoUrl() + "&refUrl=" + relUrl;
		redirectLoginSso(response, requestUrl);
		return null;
	}
	
	/**
	 * @Description: (输出登录页的HTML) 
	 * @param response
	 * @param requestUrl
	 * @throws IOException
	 * @author XJ
	 * @date 2017年7月28日 上午8:50:20
	 */
	private void redirectLoginSso(HttpServletResponse response,String requestUrl) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<script>");
		out.println("window.open ('" + requestUrl + "','_parent')"); // 作为父窗口打开
		out.println("</script>");
		out.println("</html>");
	}
	
	/**
	 * @Description: (getExpandMenuTree) 
	 * @param request
	 * @return
	 * @throws Exception
	 * @author XJ
	 * @date 2017年7月28日 上午8:51:03
	 */
	/*@RequestMapping(value="getExpandMenuTree")
	@ResponseBody
	public List<PortalMenuTreeVo> getExpandMenuTree(HttpServletRequest request){
		if(WebUtil.getCurrentUser() != null){
			List<String> roles = WebUtil.getCurrentUser().getRoleCodeList();
			if (roles == null || roles.isEmpty()) {
				return new ArrayList<PortalMenuTreeVo>();
			}
			return fspUserBiz.getMenuTree(roles);
		}
		return null;
	};*/
	
	/**
	 * @Description: (调用sso修改密码) 
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 * @author XJ
	 * @throws UnknownHostException 
	 * @date 2017年4月26日 上午10:12:58
	 */
	@RequestMapping(value="updatePwd")
	@ResponseBody
	public Map<String,Object> updatePwd(HttpServletRequest request, cn.uce.omg.sso.vo.UpdPwdVo updPwdVo) throws UnknownHostException {
		ShiroHttpSession httpSession = (ShiroHttpSession) request.getSession();
		String token = (String) httpSession.getAttribute("token");
		String empCode = (String) httpSession.getAttribute("empCode");
		updPwdVo.setEmpCode(empCode);
		updPwdVo.setIpAddr(InetAddress.getLocalHost().getHostAddress());
		updPwdVo.setTokenId(token);
		updPwdVo.setUpdateTime(new Date());
		try{
			fspCasBiz.updatePwd(updPwdVo);
		}catch(Exception e){
			//用户原密码错误
			if("B1003".equals(e.getMessage())){
				return returnError("用户密码错误");
			}
			//本次修改的密码与之前修改的密码相同
			else if("B2008".equals(e.getMessage())){
				return returnError("新密码与前3次的密码相同");
			//新旧密码不能相同
			}else if("B2013".equals(e.getMessage())){
				return returnError("新旧密码不能相同");
			}
			//其他错误
			else{
				log.error(e.getMessage(), e);
				return returnError("修改密码错误,错误: " + e.getMessage());
			}
		}
		return returnSuccess("修改密码成功");
	};
	
	/**
	 * @Description: (获取当前用户信息) 
	 * @return
	 * @author XJ
	 * @date 2017年7月28日 上午8:51:44
	 */
	@RequestMapping(value="getCurrentUser")
	@ResponseBody
	public CurrentUser getCurrentUser() {
		return WebUtil.getCurrentUser();
	};
	
	/**
	 * @Description: (获取当前登录用户的所以权限码) 
	 * @param request
	 * @return
	 * @throws Exception
	 * @author XJ
	 * @date 2017年7月28日 上午8:52:06
	 */
	@RequestMapping(value="findPermissionCodeByCurrentUser")
	@ResponseBody
	public List<String> findPermissionCodeByCurrentUser() throws Exception {
		return permissionBiz.findPermissionCodeByUser(WebUtil.getCurrentUser().getEmpCode(), null);
	};
	
	/**
	 * @Description: (getExpandMenuTree) 
	 * @param request
	 * @return
	 * @throws Exception
	 * @author XJ
	 * @date 2017年7月28日 上午8:51:03
	 */
	@RequestMapping(value="getExpandMenuTree")
	@ResponseBody
	public List<PortalMenuTreeVo> getExpandMenuTree(){
		return menuBiz.getExpandMenuTree(WebUtil.getCurrentUser());
	};
	
	/**
	 * @Description: (getMenuTree) 
	 * @param request
	 * @return
	 * @throws Exception
	 * @author XJ
	 * @date 2017年7月28日 上午8:51:03
	 */
	@RequestMapping(value="getMenuTree")
	@ResponseBody
	public List<PortalMenuTreeVo> getMenuTree(){
		return menuBiz.getExpandMenuTree(WebUtil.getCurrentUser());
	};
	
	/**
	 * @Description: (getSession) 
	 * @return
	 * @author XJ
	 * @date 2017年7月28日 上午8:51:44
	 */
	@RequestMapping(value="getSession")
	@ResponseBody
	public String getSession(HttpServletRequest request) {
		String callback =request.getParameter("callback");
        return callback+"("+JSON.toJSONString(SecurityUtils.getSubject().getSession())+")";
	};
}
