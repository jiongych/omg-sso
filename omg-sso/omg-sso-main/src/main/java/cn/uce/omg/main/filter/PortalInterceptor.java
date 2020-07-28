package cn.uce.omg.main.filter;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.uce.omg.sso.biz.IAuthBiz;
import cn.uce.omg.sso.vo.AuthCheckVo;
import cn.uce.omg.sso.vo.AuthResultVo;

/**
 * 
  * <p>Title : IllegalCharacterFilter</p>
  * <p>Description :验证token失效过滤器， </p>
  * @author : crj
  * @date : 2018年1月10日下午6:56:42
 */
public class PortalInterceptor implements HandlerInterceptor{
	private Log log = LogFactory.getLog(this.getClass());
//	private PortalBiz portalBiz;
	@Autowired
	private IAuthBiz authBiz;	
	@Override
	public void afterCompletion(HttpServletRequest req,
			HttpServletResponse res, Object arg2, Exception arg3)
			throws Exception {
		// sonar检查需要方法体有代码或者注释
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res,
			Object arg2, ModelAndView arg3) throws Exception {
		// sonar检查需要方法体有代码或者注释
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: preHandle</p> 
	* <p>Description: 请求预处理器</p> 
	* @param req
	* @param res
	* @param arg2
	* @return
	* @throws Exception 
	
	* @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
			Object arg2) throws Exception {
		String empCode = "";
		String token = "";
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookies == null) {
					continue;
				}
				if ("token".equals(cookie.getName())) {
					token = cookie.getValue();
				} else if ("empCode".equals(cookie.getName())) {
					empCode = cookie.getValue();
				}
		}
		}
		if(log.isInfoEnabled()){
			log.info("登录用户信息 empCode="+empCode + "token="+token);
		}
		/*if(token == null || "".equals(token) || empCode == null || "".equals(empCode)){
			//如果用户登录失效返回。
			ResponseModel responseModel = new ResponseModel();
			responseModel.setErrorMsg(ErrorMsg.EXPIRED);
			returnJson(res,JSON.toJSONString(responseModel));
			return false;
		}*/
		//检查用户是否失效
		boolean flag = authCheck(token, empCode);
		boolean loginRequest = req.getServletPath().indexOf("toLogin.action") > 0 ? true : false;
		
		if(flag){//校验成功
			if(loginRequest){//登陆请求，直接跳转portal主页
				res.sendRedirect(req.getContextPath()+"/portal/forward.action");
				return false;
			}
			req.setAttribute("empCode", empCode);
			req.setAttribute("token", token);
			return true;//业务请求直接放过
		}else{//校验失败
			if(loginRequest){//登陆请求，放过
				return true;
			}else{//业务请求返回超时错误码
				//如果用户登录失效返回。
                /*ResponseModel responseModel = new ResponseModel();
                responseModel.setErrorMsg(ErrorMsg.EXPIRED);
                returnJson(res,JSON.toJSONString(responseModel));*/

                //session失效后刷新页面会从https跳到http，取消重定向的方式跳转  kanghuaigang 2019-12-18修改
//                res.sendRedirect(req.getContextPath()+"/toLogin.action");
                res.setStatus(302);
                res.setHeader("location",req.getContextPath()+"/toLogin.action");
				return false;
			}
		}
	}

	public Boolean authCheck(String token,String empCode){
		AuthCheckVo authCheckVo = new AuthCheckVo();
		//设置当前时间
		authCheckVo.setCurrentTime(new Date());
		//设置系统码
		authCheckVo.setSystemCode("SSO");
		//设置当前用户tokenId
		authCheckVo.setTokenId(token);
		//设置用户编码
		authCheckVo.setEmpCode(empCode);
		AuthResultVo authResultVo = null;
		try {
			//权限检查（系统切换接口）
			authResultVo = authBiz.authCheck(authCheckVo);
		} catch (Exception e) {
			log.error("检查登录用户是否失效失败：",e);
			return false;
		}
		//返回数据不为空，则当前用户登录未失效
		if (authResultVo != null && !StringUtils.isEmpty(authResultVo.getTokenId())) {
			return true;
		}
		return false;
	}

	/**
	 * 返回参数
	 * @param response
	 * @param json
	 * @throws Exception
	 */
	/*private void returnJson(HttpServletResponse response, String json) throws Exception{
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
        	log.error("response error",e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }*/
//	
//	public void setPortalBiz(PortalBiz portalBiz) {
//		this.portalBiz = portalBiz;
//	}

}
