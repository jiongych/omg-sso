/** 
 * @项目名称: FSP
 * @文件名称: LoginAction 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.main.sso.action;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import cn.uce.omg.main.sso.constant.SsoMainConstant;
import cn.uce.omg.main.util.DomainUtil;
import cn.uce.omg.main.util.ImageCodeUtils;
import cn.uce.omg.sso.biz.IAuthBiz;
import cn.uce.omg.sso.biz.IEmpBiz;
import cn.uce.omg.sso.biz.IPortalBiz;
import cn.uce.omg.sso.biz.ISysTypeBiz;
import cn.uce.omg.sso.biz.ISystemInfoBiz;
import cn.uce.omg.sso.cache.FspDictDataCache;
import cn.uce.omg.sso.constant.AuthConstants;
import cn.uce.omg.sso.constant.ErrorCode;
import cn.uce.omg.sso.constant.OmgConstants;
import cn.uce.omg.sso.constant.UserAccountType;
import cn.uce.omg.sso.exception.GatewayException;
import cn.uce.omg.sso.util.Base64Util;
import cn.uce.omg.sso.util.CodeUtil;
import cn.uce.omg.sso.util.EmpInfoUtil;
import cn.uce.omg.sso.util.IPUtil;
import cn.uce.omg.sso.util.LoginCodeUtil;
import cn.uce.omg.sso.util.MobileUtil;
import cn.uce.omg.sso.util.YmHttpClientUtil;
import cn.uce.omg.sso.vo.AuthCheckVo;
import cn.uce.omg.sso.vo.AuthResultVo;
import cn.uce.omg.sso.vo.EmpInfoVo;
import cn.uce.omg.sso.vo.ExpCodeVo;
import cn.uce.omg.sso.vo.LoginCodeVo;
import cn.uce.omg.sso.vo.LoginFailVo;
import cn.uce.omg.sso.vo.LoginResultVo;
import cn.uce.omg.sso.vo.LoginVo;
import cn.uce.omg.sso.vo.ResetPwdVo;
import cn.uce.omg.sso.vo.SecretFreeLoginVo;
import cn.uce.omg.sso.vo.UpdPwdVo;
import cn.uce.omg.sso.vo.portal.PortalDictDataVo;
import cn.uce.omg.vo.EmpVo;
import cn.uce.omg.vo.SysTypeVo;
import cn.uce.omg.vo.SystemInfoVo;
import cn.uce.utils.FastJsonUtil;
import cn.uce.utils.StringUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 登录控制类
 * @author huangting
 * @date 2017年6月8日 下午2:06:41
 */
@Controller
public class LoginAction {
	private Log LOG = LogFactory.getLog(this.getClass());
	private IAuthBiz authBiz;
	
	@Resource  
	private CodeUtil codeUtil;

    private EmpInfoUtil empInfoUtil;
    
    /**发送手机短信验证*/
	private MobileUtil mobileUtil;

	private LoginCodeUtil loginCodeUtil;
	@Resource
	private ImageCodeUtils imageCodeUtils;
	@Autowired
	private IPortalBiz portalBiz;
	@Resource
	private ISystemInfoBiz systemInfoBiz;
	@Resource
	private IEmpBiz empBiz;
	@Resource
	private ISysTypeBiz sysTypeBiz;
	
	@Resource
    private YmHttpClientUtil ymHttpClientUtil;
	
	private FspDictDataCache fspDictDataCache;
	public void setFspDictDataCache(FspDictDataCache fspDictDataCache) {
		this.fspDictDataCache = fspDictDataCache;
	}
	
	
	/**
	 * 跳转到登录页面
	 * @param systemCode
	 * @param request
	 * @return
	 * @author huangting
	 * @date 2017年6月8日 下午1:55:04
	 */
	@RequestMapping("/toLogin")
	public String toLogin(String systemCode, HttpServletRequest request) {
		try {
			//登录请求url
 			String refUrl = HtmlUtils.htmlEscape(request.getParameter("refUrl"));
 			systemCode = HtmlUtils.htmlEscape(systemCode);
			String errorCode = null;
			if (!StringUtil.isBlank(systemCode)) {
				//查询系统编码是否存在
				SystemInfoVo system = authBiz.findSystemBySystemCode(systemCode);
				if (system != null) {
					//系统被禁用，无法使用认证系统 
					if (system.getEnable() != null && system.getEnable() == 0) {
						errorCode = ErrorCode.SYSTEM_DISABLE;
					}
					//获取请求地址
					if (StringUtil.isBlank(refUrl)) {
						refUrl = system.getWebIndexUrl();
					}
				} else {
					systemCode=null;
					//系统参数错误，系统信息不存在
					errorCode = ErrorCode.SYSTEM_CODE_ERROR;
				}
			} else {
				//无系统编码则默认使用本系统code
				systemCode = AuthConstants.DEFAULT_SYSTEMCODE;
			}
			request.setAttribute("loginResult", "");
			request.setAttribute("errorCode", errorCode);
			request.setAttribute("refUrl", refUrl);
			request.setAttribute("systemCode", systemCode);
		} catch (Exception e) {
			LOG.error("toLogin error", e);
			return "error.jsp";
		}
		return "login.jsp";
	}
	
	/**
	 * 跳转至发送验证码页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toSendCode")
	public ModelAndView toSendCode(HttpServletRequest request) {
		//跳转至发送验证码页面
		ModelAndView view = new ModelAndView("sendCode.jsp");
		//获取请求url
		String refUrl = HtmlUtils.htmlEscape(request.getParameter("refUrl"));
		request.setAttribute("refUrl", refUrl);
		return view;
	}
	
	@RequestMapping(value="findCompCode")
	@ResponseBody
	public List<SysTypeVo> findCompCode(HttpServletRequest request,HttpServletResponse response) {
		String head = request.getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin", head);
		response.setHeader("Access-Control-Allow-Credentials","true"); 
		return fspDictDataCache.get(OmgConstants.COMP_CODE);
	}


	@RequestMapping(value="findDomain")
	@ResponseBody
	public List<SysTypeVo> findDomain(HttpServletRequest request,HttpServletResponse response) {
		//return fspDictDataCache.get(OmgConstants.LOGIN_DOMAIN);
		return sysTypeBiz.findListByTypeClassCode(OmgConstants.LOGIN_DOMAIN);
	}
	
	/**
	 * 根据手机号校验用户是否存在
	 * @param systemCode
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkMobile")
	@ResponseBody
	public Map<String, Object> checkMobile(HttpServletRequest request,String checkCode, String sig, long t) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String ipAddr = IPUtil.getRemoteHost(request);
			//校验图片验证码
			checkImgCode(ipAddr, checkCode, sig, t);
			String mobile = request.getParameter("mobile");
			// 校验用户是否存在
			authBiz.authCheckUser(mobile, AuthConstants.LOGIN_TYPE_MOBILE);
			map.put("success", true);
			//map.put("empCode", empInfoVo.getEmpVo().getEmpCode());
		} catch (GatewayException ge) {
			LOG.error("校验手机号异常", ge);
			if(ErrorCode.CHECK_CODE_ERROR_CODE.equals(ge.getErrorCode())){
				map = returnError(ge.getErrorMessage());	 
				map.put("errorCode", ErrorCode.CHECK_CODE_ERROR_CODE);
			}else{
				map = returnError(ge.getErrorMessage());	 
			}
		} catch (Exception e) {
			LOG.error("checkMobile error", e);
			map = returnError("校验手机号异常");
		}
		return map;
	}
	
	/**
	 * 方法的描述：
	 * <pre>
	 * 发送验证码(基于手机号保密原则,只传工号)
	 * </pre>
	 * @param expCodeVo
	 * @param request
	 * @param checkCode
	 * @param sig
	 * @param t
	 * @return
	 * @return Map<String,Object>
	 * @author LH
	 * @email luhenguce@uce.cn
	 * @date 2019年10月9日下午2:04:36
	 */
	@RequestMapping("/sendCodeByEmpCode")
	@ResponseBody
	public Map<String, Object> sendCodeByEmpCode(ExpCodeVo expCodeVo,HttpServletRequest request,String checkCode, String sig, long t){
		Map<String, Object> map = new HashMap<String, Object>();
		// 校验用户是否存在
		EmpInfoVo empInfoVo;
		try {
			empInfoVo = authBiz.authCheckUser(expCodeVo.getEmpCode(), AuthConstants.LOGIN_TYPE_EMP_CODE);
			if(empInfoVo != null){
				EmpVo empVo = empInfoVo.getEmpVo();
				if(empVo == null){
					map = returnError("用户不存在!");
				}else if(StringUtils.isBlank(empVo.getMobile())){
					map = returnError("用户未绑定手机号!");
				}else{
					expCodeVo.setMobile(empVo.getMobile());
					map = sendCode(expCodeVo, request, checkCode, sig, t);
				}
			}else{
				map = returnError("用户不存在!");
			}
		} catch (GatewayException ge) {
			LOG.error("校验员工工号异常", ge);
			if(ErrorCode.CHECK_CODE_ERROR_CODE.equals(ge.getErrorCode())){
				map = returnError(ge.getErrorMessage());	 
				map.put("errorCode", ErrorCode.CHECK_CODE_ERROR_CODE);
			}else{
				map = returnError(ge.getErrorMessage());	 
			}
		} catch (Exception e) {
			LOG.error("checkEmpCode error", e);
			map = returnError("校验员工工号异常");
		}			
		return map;
	}
	
	/**
	 * 发送验证码
	 * @param expCodeVo
	 * @return
	 */
	@RequestMapping("/sendCode")
	@ResponseBody
	public Map<String, Object> sendCode(ExpCodeVo expCodeVo,HttpServletRequest request,String checkCode, String sig, long t) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//校验请求参数
			if (expCodeVo == null || StringUtil.isBlank(expCodeVo.getMobile())) {
				throw new GatewayException(ErrorCode.SYSTEM_PARAM_ERROR,
						ErrorCode.SYSTEM_PARAM_ERROR_MESSGE);
			}
			// add by majun 20190622 增加手机验证码登录 begin
			if (StringUtils.isEmpty(expCodeVo.getCodeType())) {
				//验证码类型：重置密码
				expCodeVo.setCodeType(AuthConstants.CODE_TYPE_RTEPWD);
			}
	        // 目前只有这几种场景发送手机验证码。
			if (!AuthConstants.CODE_TYPE_LOGIN.equals(expCodeVo.getCodeType())
					&& !AuthConstants.CODE_TYPE_UPDPWD.equals(expCodeVo.getCodeType())
					&& !AuthConstants.CODE_TYPE_RTEPWD.equals(expCodeVo.getCodeType())) {
				throw new GatewayException(ErrorCode.SYSTEM_PARAM_ERROR, ErrorCode.SYSTEM_PARAM_ERROR_MESSGE);
			}
			// add by majun 20190622 增加手机验证码登录 end
			if(expCodeVo.getIsSelfCall() == null || !expCodeVo.getIsSelfCall()){
				String ipAddr = IPUtil.getRemoteHost(request);
				//校验图片验证码
				checkImgCode(ipAddr, checkCode, sig, t);
			}
			//无系统编码则默认使用本系统code
			if (StringUtil.isBlank(expCodeVo.getSystemCode())) {
				expCodeVo.setSystemCode(AuthConstants.DEFAULT_SYSTEMCODE);
			}
			expCodeVo.setSendTime(new Date());
			
			// delete by majun 20190622 增加手机验证码登录 begin
//			//验证码类型：重置密码
//			expCodeVo.setCodeType(AuthConstants.CODE_TYPE_RTEPWD);
			// delete by majun 20190622 增加手机验证码登录 end

			// addby yangjun 2017-09-13 增加手机频繁重复发送短信验证 begin
			
			boolean result = mobileUtil.checkMobile(expCodeVo.getMobile());
			if (!result) {
				throw new GatewayException(ErrorCode.MOBILE_CHECKED_ERROR, ErrorCode.MOBILE_CHECKED_ERROR_MESSGE);
			}
			
			// end
			//发送验证码
			authBiz.getCode(expCodeVo);
			map.put("success", true);
		} catch (GatewayException ge) {
			LOG.error("获取验证码失败", ge);
			if(ErrorCode.CHECK_CODE_ERROR_CODE.equals(ge.getErrorCode())){
				map = returnError(ge.getErrorMessage());	 
				map.put("errorCode", ErrorCode.CHECK_CODE_ERROR_CODE);
			}else{
				map = returnError(ge.getErrorMessage());	 
			}
		} catch (Exception e) {
			LOG.error("sendCode error", e);
			map = returnError("获取验证码失败");
		}
		return map;
	}
	
	/**
	 * 封装异常结果
	 * @param msg
	 * @return
	 * @author huangting
	 * @date 2017年6月8日 下午1:59:18
	 */
	private Map<String, Object> returnError(String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", false);
        map.put("errorMsg", msg);
        return map;
    }
	
	/**
	 * 校验验证码
	 * @param expCodeVo
	 * @return
	 */
	@RequestMapping("/checkCodeByEmpCode")
	@ResponseBody
	public Map<String, Object> checkCodeByEmpCode(ExpCodeVo expCodeVo, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 校验用户是否存在
		EmpInfoVo empInfoVo;
		try {
			empInfoVo = authBiz.authCheckUser(expCodeVo.getEmpCode(), AuthConstants.LOGIN_TYPE_EMP_CODE);
			if(empInfoVo != null){
				EmpVo empVo = empInfoVo.getEmpVo();
				if(empVo == null){
					map = returnError("用户不存在!");
				}else if(StringUtils.isBlank(empVo.getMobile())){
					map = returnError("用户未绑定手机号!");
				}else{
					expCodeVo.setMobile(empVo.getMobile());
					expCodeVo.setEmpCode(empVo.getMobile());
					map = checkCode(expCodeVo, request);
				}
			}else{
				map = returnError("用户不存在!");
			}
		} catch (GatewayException ge) {
			LOG.error("校验员工工号异常", ge);
			if(ErrorCode.CHECK_CODE_ERROR_CODE.equals(ge.getErrorCode())){
				map = returnError(ge.getErrorMessage());	 
				map.put("errorCode", ErrorCode.CHECK_CODE_ERROR_CODE);
			}else{
				map = returnError(ge.getErrorMessage());	 
			}
		} catch (Exception e) {
			LOG.error("checkEmpCode error", e);
			map = returnError("校验员工工号异常");
		}			
		return map;
	}
	
	/**
	 * 校验验证码
	 * @param expCodeVo
	 * @return
	 */
	@RequestMapping("/checkCode")
	@ResponseBody
	public Map<String, Object> checkCode(ExpCodeVo expCodeVo, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//校验请求参数
			if (expCodeVo == null || StringUtil.isBlank(expCodeVo.getEmpCode())
					|| StringUtil.isBlank(expCodeVo.getCode())) {
				throw new GatewayException(ErrorCode.SYSTEM_PARAM_ERROR,
						ErrorCode.SYSTEM_PARAM_ERROR_MESSGE);
			}
			//无系统编码则默认使用本系统code
			if (StringUtil.isBlank(expCodeVo.getSystemCode())) {
				expCodeVo.setSystemCode(AuthConstants.DEFAULT_SYSTEMCODE);
			}
			// 校验验证码
			expCodeVo.setSendTime(new Date());
			//验证码类型：重置密码
			expCodeVo.setCodeType(AuthConstants.CODE_TYPE_RTEPWD);
			String result = authBiz.checkCode(expCodeVo);
			if (result != null) {
				map.put("resetPwdKey", result);
				map.put("success", true);
				map.put("empCode", expCodeVo.getEmpCode());
			}
		} catch (GatewayException ge) {
			LOG.error("校验验证码异常", ge);
			map = returnError(ge.getErrorMessage());
		} catch (Exception e) {
			LOG.error("checkCode error", e);
			map = returnError("验证码发送失败");
		}
		return map;
	}

	/**
	* @Description: 校验图片验证码
	* @author wangshangbing
	* @date 2017/9/13 11:03
	*/
	private void checkImgCode(String ipAdd,String checkCode, String sig, long t) throws GatewayException {
		//存在当前登录的校验码信息需要判断验证码
		/*LoginResultVo loginResultVo = imageCodeUtils.checkImageCode(checkCode, 0,
				SsoMainConstant.IMAGE_CODE_TYPE_PHONE, mobile, ipAddr);*/
		LoginResultVo loginResultVo = imageCodeUtils.checkImageCode(checkCode, 0, sig, ipAdd,t);
		//判断图片验证码是否校验通过，如果不通过则返回消息
		if(ErrorCode.CHECK_CODE_ERROR_CODE.equals(loginResultVo.getCheckCode())){
			throw new GatewayException(loginResultVo.getCheckCode(), loginResultVo.getErrorMsg());
		}
	}

	/**
	 * 跳转至重置密码页面
	 * @param request
	 * @return
	 * @author huangting
	 * @date 2017年6月8日 下午2:00:33
	 */
	@RequestMapping("/toResetPwd")
	public ModelAndView toResetPwd(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("resetPwd.jsp");
		try {
			//系统编码
			String systemCode = request.getParameter("systemCode");
			//请求url
			String refUrl = HtmlUtils.htmlEscape(request.getParameter("refUrl"));
			//员工编码
			String empCode = request.getParameter("empCode");
			//重置密码key
			String resetPwdKey = request.getParameter("resetPwdKey");
			//校验请求参数
			if (StringUtil.isBlank(empCode)
					|| StringUtil.isBlank(resetPwdKey)) {
				throw new GatewayException(ErrorCode.SYSTEM_PARAM_ERROR, ErrorCode.SYSTEM_PARAM_ERROR_MESSGE);
			}
			view.addObject("systemCode", systemCode);
			view.addObject("refUrl", refUrl);
			view.addObject("empCode", empCode);
			view.addObject("resetPwdKey", resetPwdKey);
		} catch (Exception e) {
			LOG.error("toResetPwd error", e);
			view.setViewName("error.jsp");
		}
		return view;
	}
	
	/**
	 * 重置密码
	 * @param rtePwdVo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/resetPwd")
	@ResponseBody
	public Map<String, Object> resetPwd(ResetPwdVo rtePwdVo, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//校验请求参数
			if (rtePwdVo == null 
					|| StringUtil.isBlank(rtePwdVo.getEmpCode()) 
					|| StringUtil.isBlank(rtePwdVo.getResetPwdKey())
					|| StringUtil.isBlank(rtePwdVo.getNewPassword())) {
				throw new GatewayException(ErrorCode.SYSTEM_PARAM_ERROR, ErrorCode.SYSTEM_PARAM_ERROR_MESSGE);
			}
			//获取远程主机
			String ipAddr = IPUtil.getRemoteHost(request);
			rtePwdVo.setIpAddr(ipAddr);
			rtePwdVo.setRestTime(new Date());
			//无系统编码则默认使用本系统code
			if(StringUtil.isEmpty(rtePwdVo.getSystemCode())){
				rtePwdVo.setSystemCode(AuthConstants.DEFAULT_SYSTEMCODE);
			}
			AuthResultVo resultVo = authBiz.resetPwd(rtePwdVo);
			if (resultVo != null) {
				map.put("success", true);
				map.put("refUrl", HtmlUtils.htmlEscape(rtePwdVo.getRefUrl()));
				map.put("empCode", resultVo.getEmpCode());
			}
			// 登录成功，设置COOKIE信息
		} catch (GatewayException ge) {
			LOG.error("重置密码失败", ge);
			map = returnError(ge.getErrorMessage());
		} catch (Exception e) {
			LOG.error("重置密码失败", e);
			map = returnError("重置密码失败");
		}
		return map;
	}

	/**
	 * 跳转至发送验证码页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toResetPwdSuccess")
	public ModelAndView resetPwdSuccess(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("resetPwdSuccess.jsp");
		String refUrl = HtmlUtils.htmlEscape(request.getParameter("refUrl"));
		request.setAttribute("refUrl", refUrl);
		return view;
	}	
	
	/**
	 * 跳转到修改密码界面
	 * @param systemCode
	 * @param request
	 * @return
	 * @author huangting
	 * @date 2017年6月12日 上午10:10:28
	 */
	@RequestMapping("/toUpdPwd")
	public String toUpdPwd(String systemCode, HttpServletRequest request) {
		try {
			String refUrl = HtmlUtils.htmlEscape(request.getParameter("refUrl"));
			String empCode = request.getParameter("empCode");
			String tokenId = request.getParameter("tokenId");
			String errorCode = null;
			if (!StringUtil.isBlank(systemCode)) {
				SystemInfoVo system = authBiz.findSystemBySystemCode(systemCode);
				if (system != null) {
					if (system.getEnable() != null && system.getEnable() == 0) {
						errorCode = ErrorCode.SYSTEM_DISABLE;
					}
					if (StringUtil.isBlank(refUrl)) {
						refUrl = system.getWebIndexUrl();
					}
				} else {
					errorCode = ErrorCode.SYSTEM_CODE_ERROR;
				}
			}
			// 校验token
			AuthCheckVo authCheckVo = new AuthCheckVo();
			authCheckVo.setSystemCode(systemCode);
			authCheckVo.setCurrentTime(new Date());
			authCheckVo.setTokenId(tokenId);
			authCheckVo.setEmpCode(empCode);
			authBiz.authCheck(authCheckVo);
			request.setAttribute("errorCode", errorCode);
			request.setAttribute("empCode", empCode);
			request.setAttribute("refUrl", refUrl);
			request.setAttribute("tokenId", tokenId);
			request.setAttribute("systemCode", systemCode);
		} catch (Exception e) {
			LOG.error("toUpdPwd error", e);
			return "error.jsp";
		}
		return "updPwd.jsp";
	}
	/**
	 * 登录
	 * @param loginVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author huangting
	 * @date 2017年6月8日 下午2:01:27
	 */
	@RequestMapping("/login")
	@ResponseBody
	public LoginResultVo login(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response, String sig,long t,String browserInfo) throws Exception {
		String head = request.getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin", head);
		response.setHeader("Access-Control-Allow-Credentials","true"); 
		LoginResultVo loginResultVo = new LoginResultVo();
		//@Description: 登录验证码校验 begin @author wangshangbing @date 2017/8/15 13:47
		String ipAddr = IPUtil.getRemoteHost(request);
		try {
			//add by zhangRb 20180905 支持手机号登录需求   输入的账号转换为工号
			String empCode = null;
			//add by huangting 20190919 增加快运账号登录方式 begin
			if (StringUtils.isEmpty(loginVo.getUserAccountType())) {
				loginVo.setUserAccountType(UserAccountType.Portal.name());
			}
			if (StringUtils.equals(UserAccountType.Portal.name(), loginVo.getUserAccountType())) {
				empCode = empInfoUtil.getLoginEmpCode(loginVo);
			} else if (StringUtils.equals(UserAccountType.Yinhe.name(), loginVo.getUserAccountType())) {
				empCode = loginVo.getUserName()+ "@" +loginVo.getCompCode();
			} 
			//add by huangting 20190919 增加快运账号登录方式 end
			LoginFailVo loginFailVo = empInfoUtil.findLoginFailByUsername(empCode);
			if(null!=loginFailVo && loginFailVo.getFailCount().compareTo(3)>=0){
				String checkCode = loginVo.getCheckCode();
				//存在当前登录的校验码信息需要判断验证码
				loginResultVo = imageCodeUtils.checkImageCode(checkCode, loginFailVo.getFailCount(),
						sig, ipAddr,t);
				//判断图片验证码是否校验通过，如果不通过则返回消息
				if(ErrorCode.CHECK_CODE_ERROR_CODE.equals(loginResultVo.getCheckCode())){
					return loginResultVo;
				}
			}
		} catch (Exception e) {
            LOG.info("获取用户登录失败信息失败,用户账号:"+loginVo.getUserName(),e);
		}
		//@Description: 登录验证码校验 end

		//校验请求参数,用户名、密码
		if (StringUtil.isBlank(loginVo.getUserName()) || StringUtil.isBlank(loginVo.getPassword())) {
			loginResultVo.setErrorCode(ErrorCode.ILLEGAL_PARAM);
			return loginResultVo;
			//校验请求参数,系统code
		} else if (StringUtil.isBlank(loginVo.getSystemCode())) {
			loginResultVo.setErrorCode(ErrorCode.SYSTEM_PARAM_ERROR);
			return loginResultVo;
		}
		//add by zhangRb 20170824 校验子系统域名 start
		Boolean domainValid = true;
		try {
			//查询系统编码信息
			SystemInfoVo system = authBiz.findSystemBySystemCode(loginVo.getSystemCode());
			//获取登录请求系统的域名
			URL url = new URL(loginVo.getRefUrl());
			String subDomain = url.getHost();
			//获取登录请求系统对应的系统信息中主页地址的域名
			url = new URL(system.getWebIndexUrl());
			String sysDomainInfo = url.getHost();
			if(LOG.isInfoEnabled()){
				LOG.info("跳转域名："+subDomain+"系统配置域名："+sysDomainInfo);
			}
			//校验两个系统域名是否相同
			if (!subDomain.equals(sysDomainInfo)) {
				domainValid = false;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			domainValid = false;
		}
		//add by zhangRb 20170824 校验子系统域名 End
		AuthResultVo authResultVo = null;
		try {
			loginVo.setLoginTime(new Date());
			loginVo.setIpAddr(ipAddr);
			//登录校验
			authResultVo = authBiz.login(loginVo);
			if (authResultVo != null) {
				addLoginCookie(loginVo,loginResultVo,authResultVo,request,response);
			} else {
				loginResultVo.setErrorCode("fail");
			}
			//搜集浏览器信息
			try {
				saveBrowserInfo(loginVo, request, browserInfo);
			} catch (Exception e) {
				LOG.error("浏览器信息搜集失败。");
			}
			// 登录成功，设置COOKIE信息
			//add by zhangRb 20170824 校验子系统域名 start
			if (!domainValid) {
				loginResultVo.setErrorCode(ErrorCode.SYSTEM_URL_ERROR);
				return loginResultVo;
			}
			//add by zhangRb 20170824 校验子系统域名 End
		} catch (GatewayException ge) {
			// modify by majun 20190624 当用户不存在，包装成用户密码错误。 begin
			if (StringUtils.equals(ge.getErrorCode(), ErrorCode.USER_NONENTITY)) {
	            loginResultVo.setErrorCount(ge.getErrorCount());
	            loginResultVo.setErrorCode(ErrorCode.USER_PWD_ERROR);
				loginResultVo.setErrorMsg("你输入的密码和用户名不匹配");
			} else {
				loginResultVo.setErrorCode(ge.getErrorCode());
				loginResultVo.setErrorMsg(ge.getErrorMessage());
	            loginResultVo.setErrorCount(ge.getErrorCount());
			}
			// modify by majun 20190624 当用户不存在，包装成用户密码错误。 end
		} catch (Exception e) {
			LOG.error("登录失败", e);
			loginResultVo.setErrorCode("fail");
			loginResultVo.setErrorMsg("系统异常");
		}
		return loginResultVo;
	}
	
	public void addLoginCookie(LoginVo loginVo,LoginResultVo loginResultVo,AuthResultVo authResultVo,HttpServletRequest request, HttpServletResponse response) throws Exception {
		loginResultVo.setErrorCode("success");
		String encryptToken = authResultVo.getTokenId();
//		String sourceToken = authBiz.decryptTokenIdBySystemCode(encryptToken, loginVo.getSystemCode());
		// 同域名直接设置cookie
		String topDomain = getTopDomainByServerName(request.getServerName());
		response.addCookie(getCookie("token", encryptToken, topDomain));
		//modify by zhangRb 20180828 返回结果设置userName为工号
		//response.addCookie(getCookie("userName", loginVo.getUserName(), topDomain));
		response.addCookie(getCookie("yhEmpCode", loginVo.getYhUserName(), topDomain));
		response.addCookie(getCookie("compCode", loginVo.getCompCode(), topDomain));
		response.addCookie(getCookie("userAccountType", loginVo.getUserAccountType(), topDomain));
		response.addCookie(getCookie("userName", authResultVo.getEmpCode(), topDomain));
		response.addCookie(getCookie("empCode", authResultVo.getEmpCode(), topDomain));
		response.addCookie(getCookie("empName",URLEncoder.encode(authResultVo.getEmpName(), "utf-8"), topDomain));
		response.addCookie(getCookie("updPwdFlag", authResultVo.getUpdPwdFlag() + "", topDomain));
		response.addCookie(getCookie("updPwdIntervalDay", authResultVo.getUpdPwdIntervalDay() + "", topDomain));
		response.addCookie(getCookie("omgOrgId", authResultVo.getOrgId() + "", topDomain));
		response.addCookie(getCookie("encryptOrgId", new String(Base64.encodeBase64((authResultVo.getOrgId() + "").getBytes())), topDomain));
		response.addCookie(getCookie("cmsOrgId", authResultVo.getOtherOrgId() + "", topDomain));
		response.addCookie(getCookie("cmsOrgIdStr", authResultVo.getOtherOrgIdStr(), topDomain));
		//判断是否登录portal系统，如果是，则在cookie加一个标识 by jiyongchao
			if (null == loginVo.getRefUrl() || loginVo.getRefUrl().length() == 0) {
				getTopDomainByServerName(request.getServerName());
				response.addCookie(getCookie("portalIndex", "portal", topDomain));
			}
		if (authResultVo.getLastLoginTime() == null) {
			response.addCookie(getCookie("lastLoginTime", "", topDomain));
		} else {
			response.addCookie(getCookie("lastLoginTime", String.valueOf(authResultVo.getLastLoginTime().getTime()), topDomain));
		}
		// 跨域名系统，通过iframe请求子系统保存到cookie
		List<String> crossDomainSystemList = getCrossDomainSystemCookie(topDomain);
		loginResultVo.setSystemCasUrlList(crossDomainSystemList);
		loginResultVo.setToken(encryptToken);
		//modify by zhangRb 20180828 返回结果设置userName为工号
		//loginResultVo.setUserName(loginVo.getUserName());
		loginResultVo.setUserName(authResultVo.getEmpCode());
		loginResultVo.setEmpCode(authResultVo.getEmpCode());
		loginResultVo.setUpdPwdFlag(authResultVo.getUpdPwdFlag());
		loginResultVo.setUpdPwdIntervalDay(authResultVo.getUpdPwdIntervalDay());
	}

	/**
	 * @Description: 校验token是否有效
	 * @param authCheckVo
	 * @return
	 * @throws Exception
	 * @author huangting
	 * @date 2019年9月16日 下午2:38:26
	 */
	@RequestMapping(value = "/authCheck", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject authCheck(AuthCheckVo authCheckVo) throws Exception {
		JSONObject jsonObject = new JSONObject();
		if (authCheckVo == null || StringUtil.isBlank(authCheckVo.getEmpCode()) 
				|| StringUtil.isBlank(authCheckVo.getSystemCode()) || StringUtil.isBlank(authCheckVo.getTokenId())) {
			LOG.error("token校验失败，必填参数为空：" + JSONObject.toJSONString(authCheckVo));
			jsonObject.put("error", ErrorCode.ILLEGAL_PARAM);
			jsonObject.put("errorMessge", ErrorCode.ILLEGAL_PARAM_MESSGE);
			return jsonObject;
		}
		try {
			String empCodePar = authCheckVo.getEmpCode();
			String workNum = null;
			String compCode = null;
			String empCode = null;
			//如果工号是xxxx@xxxx,根据@截取银河工号与账套信息
			if (empCodePar.indexOf(OmgConstants.YM_EMP_CODE_MARK) != -1) {
				workNum = empCodePar.substring(0,empCodePar.indexOf(OmgConstants.YM_EMP_CODE_MARK));
				compCode = empCodePar.substring(empCodePar.indexOf(OmgConstants.YM_EMP_CODE_MARK) + 1,empCodePar.length());
				empCode = empInfoUtil.findEmpCodeByUserName(workNum, compCode);
			} else {
				//如果没有账套则根据集团工号查找用户
				empCode = empInfoUtil.findEmpCodeByYmEmpCode(empCodePar);
			}
			if (StringUtil.isBlank(empCode)) {
				jsonObject.put("error", ErrorCode.USER_NONENTITY);
				jsonObject.put("errorMessge", ErrorCode.USER_NONENTITY_MESSGE);
				return jsonObject;
			}
			String tokenId=Base64Util.decode(authCheckVo.getTokenId());
			authCheckVo.setTokenId(tokenId);
			authCheckVo.setEmpCode(empCode);
			AuthResultVo resultVo= authBiz.authCheck(authCheckVo);
			if(LOG.isInfoEnabled()){
				LOG.info("token校验,请求参数" + JSON.toJSONString(authCheckVo) + "校验结果:" + FastJsonUtil.toJsonString(resultVo));
			}
			if (resultVo != null && StringUtil.isNotBlank(resultVo.getTokenId()) ){
				jsonObject.put("empCode", resultVo.getEmpCode());
				jsonObject.put("expireTime", resultVo.getExpireTime());
				return jsonObject;
			}
			jsonObject.put("error", "invalid_token");
			jsonObject.put("errorMessge", "token失效");
		} catch (GatewayException e) {
			LOG.error("token校验失败，请求参数：" + JSONObject.toJSONString(authCheckVo),e);
			jsonObject.put("error", e.getErrorCode());
			jsonObject.put("errorMessge", e.getErrorMessage());
		} catch (Exception e) {
			LOG.error("token校验失败，请求参数：" + JSONObject.toJSONString(authCheckVo),e);
			jsonObject.put("error", "invalid_token");
			jsonObject.put("errorMessge", "token失效");
		}
		return jsonObject;
	}


	/**
	 * @Description: 校验用户在快递是否已登录
	 * @param req
	 * @return
	 * @author huangting
	 * @date 2019年9月18日 上午11:29:49
	 */
	private AuthResultVo checkLoginStatus(HttpServletRequest req,String empCode) {
		AuthResultVo authResultVo = new AuthResultVo();
		String ckEmpCode = "";
		String token = "";
		Integer orgId = null;
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookies == null) {
					continue;
				}
				if ("token".equals(cookie.getName())) {
					token = cookie.getValue();
					authResultVo.setTokenId(token);
				} else if ("empCode".equals(cookie.getName())) {
					ckEmpCode = cookie.getValue();
				} else if ("omgOrgId".equals(cookie.getName())) {
					orgId = Integer.valueOf(cookie.getValue());
					authResultVo.setOrgId(orgId);
				}
			}
		}
		if (StringUtil.isNotBlank(ckEmpCode) && ckEmpCode.equals(empCode) && StringUtil.isNotBlank(token)) {
			Boolean result = authCheck(token, ckEmpCode);
			if (result != null && result == true) {
				return authResultVo;
			}
		}
		return null;
	}
	
	/**
	 * @Description: 验证cookie中的token是否有效
	 * @param token
	 * @param empCode
	 * @return
	 * @author huangting
	 * @date 2019年9月18日 上午11:30:05
	 */
	public Boolean authCheck(String token,String empCode){
		AuthCheckVo authCheckVo = new AuthCheckVo();
		authCheckVo.setCurrentTime(new Date());
		authCheckVo.setSystemCode(OmgConstants.SYSTEM_CODE_SSO);
		authCheckVo.setTokenId(token);
		authCheckVo.setEmpCode(empCode);
		AuthResultVo authResultVo = null;
		try {
			//权限检查（系统切换接口）
			authResultVo = authBiz.authCheck(authCheckVo);
		} catch (Exception e) {
			LOG.error("检查登录用户是否失效失败：",e);
			return false;
		}
		//返回数据不为空，则当前用户登录未失效
		if (authResultVo != null && !StringUtils.isEmpty(authResultVo.getTokenId())) {
			return true;
		}
		return false;
	}
	
	/**
	 * @Description: 跳转至登录界面
	 * @return
	 * @author huangting
	 * @date 2019年9月18日 上午11:30:27
	 */
	@RequestMapping(value="/forwardLogin")
	public String forwardLogin() {
		return "login.jsp";
	}
	
	/**
	 * @Description: 壹米免密登录
	 * @param loginVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author huangting
	 * @date 2019年9月9日 下午4:48:18
	 */
	@RequestMapping(value = "/secretFreeLogin", method = RequestMethod.GET)
	public String secretFreeLogin(SecretFreeLoginVo secretFreelogin,RedirectAttributes  redirectAtt, HttpServletRequest request, HttpServletResponse response,String browserInfo) throws Exception {
		if (LOG.isInfoEnabled()) {
			LOG.info("免密登录请求参数：" + JSONObject.toJSONString(secretFreelogin));
		}
		redirectAtt.addFlashAttribute("systemCode", OmgConstants.SYSTEM_CODE_SSO);
		LoginResultVo loginResultVo = new LoginResultVo();
		String redirectLogin = "redirect:forwardLogin.action";
		//校验请求参数,用户名、系统编码
		if (secretFreelogin == null || StringUtil.isBlank(secretFreelogin.getUserName())
				|| StringUtil.isBlank(secretFreelogin.getTokenId())
				|| StringUtil.isBlank(secretFreelogin.getSystemCode())
				|| StringUtil.isBlank(secretFreelogin.getPartnerSystemCode())) {
			LOG.error("免密登录失败，请求参数：" + JSONObject.toJSONString(secretFreelogin));
			loginResultVo.setErrorCode(ErrorCode.ILLEGAL_PARAM);
			loginResultVo.setErrorMsg(ErrorCode.ILLEGAL_PARAM_MESSGE);
			redirectAtt.addFlashAttribute("loginResult", JSONObject.toJSON(loginResultVo));
			return redirectLogin;
		}
		JSONObject result = null;
		if (secretFreelogin.getPartnerSystemCode().equals(OmgConstants.SYSTEM_CODE_YM_SSO)){
			//校验快运SSO token是否有效
		   result = ymHttpClientUtil.checkToken(secretFreelogin);
		} else if (secretFreelogin.getPartnerSystemCode().equals(OmgConstants.SYSTEM_CODE_YH)){
			//调用银河系统校验token接口
			result = ymHttpClientUtil.checkYhToken(secretFreelogin);
		} else if (secretFreelogin.getPartnerSystemCode().equals(OmgConstants.SYSTEM_CODE_OA)) {
			result = ymHttpClientUtil.checkOAToken(secretFreelogin.getUserName(),secretFreelogin.getTokenId());
		}
		if (result == null) {
			loginResultVo.setErrorCode(ErrorCode.ILLEGAL_TOKEN);
			loginResultVo.setErrorMsg(ErrorCode.ILLEGAL_TOKEN_MESSGE);
			redirectAtt.addFlashAttribute("loginResult", JSONObject.toJSON(loginResultVo));
			return redirectLogin;
		}
		String empCode = null;
		try {
			//将快运账号转换为快递工号
			empCode = empInfoUtil.findEmpCodeByUserName(secretFreelogin.getUserName(), secretFreelogin.getCompCode());
			if (StringUtil.isBlank(empCode)) {
				loginResultVo.setErrorCode(ErrorCode.USER_NONENTITY);
				loginResultVo.setErrorMsg(ErrorCode.USER_NONENTITY_MESSGE);
				redirectAtt.addFlashAttribute("loginResult", JSONObject.toJSON(loginResultVo));
				return redirectLogin;
			}
			LoginFailVo loginFailVo = empInfoUtil.findLoginFailByUsername(empCode);
			if(null!=loginFailVo && loginFailVo.getFailCount().compareTo(3)>=0){
				//如果验证码为空返回消息
				loginResultVo.setCheckCode(ErrorCode.CHECK_CODE_ERROR_CODE);
				//设置输入密码失败的次数
				loginResultVo.setErrorCount(loginFailVo.getFailCount());
				//错误消息
				loginResultVo.setErrorMsg("请输入图片验证码 ...");
				redirectAtt.addFlashAttribute("loginResult", JSONObject.toJSON(loginResultVo));
				return redirectLogin;
			}
		} catch (Exception e) {
            LOG.info("获取用户登录失败信息失败,用户账号:"+secretFreelogin.getUserName(),e);
		}

		//如果用户在快递已登录直接跳转
		AuthResultVo cookie = checkLoginStatus(request,empCode);
		if (cookie != null) {
			return "redirect:/portal/forward.action";
		}
		LoginVo loginVo = new LoginVo();
		BeanUtils.copyProperties(secretFreelogin, loginVo);
		String ipAddr = IPUtil.getRemoteHost(request);
		AuthResultVo authResultVo = null;
		try {
			loginVo.setLoginTime(new Date());
			loginVo.setIpAddr(ipAddr);
			loginVo.setUserName(empCode);
			//登录校验
			authResultVo = authBiz.secretFreeLogin(loginVo);
			if (authResultVo != null) {
				addLoginCookie(loginVo,loginResultVo,authResultVo,request,response);
			} else {
				loginResultVo.setErrorCode("fail");
				redirectAtt.addFlashAttribute("loginResult", JSONObject.toJSON(loginResultVo));
				response.sendRedirect(request.getContextPath()+"/login.jsp");
				return redirectLogin;
			}
		} catch (GatewayException ge) {
			LOG.error("免密登录失败,登录信息：" + JSONObject.toJSON(loginVo), ge);
			loginResultVo.setErrorCode(ge.getErrorCode());
			loginResultVo.setErrorMsg(ge.getErrorMessage());
            loginResultVo.setErrorCount(ge.getErrorCount());
			redirectAtt.addFlashAttribute("loginResult", JSONObject.toJSON(loginResultVo));
			return redirectLogin;
		} catch (Exception e) {
			LOG.error("免密登录失败,登录信息：" + JSONObject.toJSON(loginVo), e);
			loginResultVo.setErrorCode("fail");
			loginResultVo.setErrorMsg("系统异常");
			redirectAtt.addFlashAttribute("loginResult", JSONObject.toJSON(loginResultVo));
			return redirectLogin;
		}
		return "redirect:/portal/forward.action";
	}


	@RequestMapping(value = "/secretFreeLoginSSO", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject secretFreeLoginSSO(SecretFreeLoginVo secretFreelogin, HttpServletRequest request, HttpServletResponse response,String browserInfo) throws Exception {
		String head = request.getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin", head);
		response.setHeader("Access-Control-Allow-Credentials","true");
		if (LOG.isInfoEnabled()) {
			LOG.info("免密登录请求参数：" + JSONObject.toJSONString(secretFreelogin));
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success","false");
		//校验请求参数,用户名、系统编码
		if (secretFreelogin == null || StringUtil.isBlank(secretFreelogin.getUserName())
				|| StringUtil.isBlank(secretFreelogin.getTokenId())
				|| StringUtil.isBlank(secretFreelogin.getSystemCode())
				|| StringUtil.isBlank(secretFreelogin.getPartnerSystemCode())) {
			LOG.error("免密登录失败，请求参数：" + JSONObject.toJSONString(secretFreelogin));
			jsonObject.put("errorCode",ErrorCode.ILLEGAL_PARAM);
			jsonObject.put("errorMsg",ErrorCode.ILLEGAL_PARAM_MESSGE);
			return jsonObject;
		}
		JSONObject result = null;
		if (secretFreelogin.getPartnerSystemCode().equals(OmgConstants.SYSTEM_CODE_YM_SSO)){
			//校验快运SSO token是否有效
			result = ymHttpClientUtil.checkToken(secretFreelogin);
		} else if (secretFreelogin.getPartnerSystemCode().equals(OmgConstants.SYSTEM_CODE_PMS)){
			//调用银河系统校验token接口
			result = ymHttpClientUtil.checkPmsToken(secretFreelogin);
		}
		if (result == null) {
			jsonObject.put("errorCode",ErrorCode.ILLEGAL_TOKEN);
			jsonObject.put("errorMsg",ErrorCode.ILLEGAL_TOKEN_MESSGE);
			return jsonObject;
		}
		String empCode = null;
		try {
			//将快运账号转换为快递工号
			empCode = empInfoUtil.findEmpCodeByUserName(secretFreelogin.getUserName(), secretFreelogin.getCompCode());
			if (StringUtil.isBlank(empCode)) {
				jsonObject.put("errorCode",ErrorCode.USER_NONENTITY);
				jsonObject.put("errorMsg",ErrorCode.USER_NONENTITY);
				return jsonObject;
			}
			LoginFailVo loginFailVo = empInfoUtil.findLoginFailByUsername(empCode);
			if(null!=loginFailVo && loginFailVo.getFailCount().compareTo(3)>=0){
				jsonObject.put("errorCode",ErrorCode.CHECK_CODE_ERROR_CODE);
				jsonObject.put("errorMsg",ErrorCode.CHECK_CODE_ERROR_CODE);
				return jsonObject;
			}
		} catch (Exception e) {
			LOG.info("获取用户登录失败信息失败,用户账号:"+secretFreelogin.getUserName(),e);
		}

		//如果用户在快递已登录直接跳转
		AuthResultVo cookie = checkLoginStatus(request,empCode);
		if (cookie != null) {
			jsonObject.put("userName",empCode);
			jsonObject.put("empCode",empCode);
			jsonObject.put("token",cookie.getTokenId());
			jsonObject.put("omgOrgId",cookie.getOrgId());
			jsonObject.put("success","true");
			return jsonObject;
		}
		LoginVo loginVo = new LoginVo();
		BeanUtils.copyProperties(secretFreelogin, loginVo);
		String ipAddr = IPUtil.getRemoteHost(request);
		AuthResultVo authResultVo = null;
		try {
			loginVo.setLoginTime(new Date());
			loginVo.setIpAddr(ipAddr);
			loginVo.setUserName(empCode);
			//登录校验
			authResultVo = authBiz.secretFreeLogin(loginVo);
			if (authResultVo != null) {
				addLoginCookie(loginVo,new LoginResultVo(),authResultVo,request,response);
			} else {
				jsonObject.put("errorCode","fail");
				jsonObject.put("errorMsg","系统异常");
			}
		} catch (GatewayException ge) {
			LOG.error("免密登录失败,登录信息：" + JSONObject.toJSON(loginVo), ge);
			jsonObject.put("errorCode",ge.getErrorCode());
			jsonObject.put("errorMsg",ge.getErrorMessage());
			return jsonObject;
		} catch (Exception e) {
			LOG.error("免密登录失败,登录信息：" + JSONObject.toJSON(loginVo), e);
			jsonObject.put("errorCode","fail");
			jsonObject.put("errorMsg","系统异常");
			return jsonObject;
		}
		jsonObject.put("userName",empCode);
		jsonObject.put("empCode",empCode);
		jsonObject.put("token",authResultVo.getTokenId());
		jsonObject.put("omgOrgId",authResultVo.getOrgId());
		jsonObject.put("success","true");
		return jsonObject;
	}

	/**
	 * 生成登录验证码
	 * 
	 * @param response
	 * @param request
	 * @param systemCode
	 * @return
	 * @throws GatewayException
	 * @author majun
	 * @date 2019年6月24日 上午11:41:51
	 */
	@RequestMapping(value="/getLoginCode",method= RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getLoginCode(HttpServletResponse response,HttpServletRequest request, String systemCode) throws GatewayException {
/*		crossDomain(request, response);*/
		Map<String, Object> retParam = new HashMap<String, Object>();
		LoginCodeVo loginCode = loginCodeUtil.saveCode(systemCode );
		retParam.put("code", loginCode.getCode());
		retParam.put("t", loginCode.getExpireTime());
		//当前域名由数据字典配置
		List<SysTypeVo> sysTypeVos = sysTypeBiz.findListByTypeClassCode("SYS_PATH");
		if(sysTypeVos != null){
			String domain = "";
			for (SysTypeVo sysTypeVo : sysTypeVos) {
				if(StringUtils.equals("SSO", sysTypeVo.getTypeName())){
					domain = sysTypeVo.getTypeCode();
				}
			}
			retParam.put("httpUrl", domain + "/omg-sso-main/gateway/gateway.action");
		}else{
			throw new GatewayException(ErrorCode.FIND_DOMIAN_FAIL, ErrorCode.FIND_DOMIAN_FAIL_MESSGE);
		}
		retParam.put("serviceName", "confirm_login_code");
		return retParam;
	}
	
/*	private void crossDomain(HttpServletRequest request,HttpServletResponse response){
		String origin = request.getHeader("Origin");
		response.setHeader( "Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, PATCH, DELETE");
	    response.setHeader("Access-Control-Allow-Origin", origin);// 允许指定域访问跨域资源
	    response.setHeader("Access-Control-Allow-Credentials", "true"); // 允许跨域传递Cookie
	    response.setHeader("Access-Control-Max-Age", "86400"); // 浏览器缓存预检请求结果时间,单位:秒
	    //过预检
	    if(StringUtils.equals(request.getMethod(), "OPTIONS")){
	    	response.setStatus(200);
	    }
	}*/

	/**
	 * 登录
	 * @param loginVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author huangting
	 * @date 2017年6月8日 下午2:01:27
	 */
	@RequestMapping("/loginForLoginCode")
	@ResponseBody
	public LoginResultVo loginForLoginCode(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response, String browserInfo) throws Exception {
/*		crossDomain(request, response);*/
		LoginResultVo loginResultVo = new LoginResultVo();
		LoginCodeVo loginCode = new LoginCodeVo();
		loginCode.setCode(loginVo.getCheckCode());
		loginCode.setSystemCode(loginVo.getSystemCode());
		LoginCodeVo codeVo = loginCodeUtil.getLoginCode(loginCode);
		if (codeVo != null) {
			if (StringUtils.isEmpty(codeVo.getEmpCode())) {
				// 未扫码
				loginResultVo.setErrorCode(ErrorCode.NOT_SCAN_CODE);
				loginResultVo.setErrorMsg(ErrorCode.NOT_SCAN_CODE_MESSGE);
				return loginResultVo;
			} else if (codeVo.getFailureFlag() != null && codeVo.getFailureFlag()) {
				// 已扫码并取消
				loginResultVo.setErrorCode(ErrorCode.CODE_CANCEL_ERROR);
				loginResultVo.setErrorMsg(ErrorCode.CODE_CANCEL_ERROR_MESSGE);
				return loginResultVo;
			} else if (StringUtils.isEmpty(codeVo.getTokenId())) {
				// 已扫码未登录
				loginResultVo.setScanSystemCode(codeVo.getScanSystemCode());
				loginResultVo.setEmpCode(codeVo.getEmpCode());
				loginResultVo.setErrorCode(ErrorCode.SCAN_CODE_NOT_LOGIN);
				loginResultVo.setErrorMsg(ErrorCode.SCAN_CODE_NOT_LOGIN_MESSGE);
				return loginResultVo;
			}
		} else {
			// 没有扫码信息。
			loginResultVo.setErrorCode(ErrorCode.NOT_SCAN_INFO);
			loginResultVo.setErrorMsg(ErrorCode.NOT_SCAN_INFO_MESSGE);
			return loginResultVo;
		}
		//@Description: 登录验证码校验 begin @author wangshangbing @date 2017/8/15 13:47
		String ipAddr = IPUtil.getRemoteHost(request);
		//@Description: 登录验证码校验 end

		//校验请求参数,用户名、密码
		if (StringUtil.isBlank(loginVo.getSystemCode())) {
			loginResultVo.setErrorCode(ErrorCode.SYSTEM_PARAM_ERROR);
			return loginResultVo;
		}
		//add by zhangRb 20170824 校验子系统域名 start
		Boolean domainValid = true;
		try {
			//查询系统编码信息
			SystemInfoVo system = authBiz.findSystemBySystemCode(loginVo.getSystemCode());
			//获取登录请求系统的域名
			URL url = new URL(loginVo.getRefUrl());
			String subDomain = url.getHost();
			//获取登录请求系统对应的系统信息中主页地址的域名
			url = new URL(system.getWebIndexUrl());
			String sysDomainInfo = url.getHost();
			if(LOG.isInfoEnabled()){
				LOG.info("跳转域名："+subDomain+"系统配置域名："+sysDomainInfo);
			}
			//校验两个系统域名是否相同
			if (!subDomain.equals(sysDomainInfo)) {
				domainValid = false;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			domainValid = false;
		}
		//add by zhangRb 20170824 校验子系统域名 End
		AuthResultVo authResultVo = null;
		try {
			loginVo.setLoginTime(new Date());
			loginVo.setIpAddr(ipAddr);
			loginVo.setUserName(codeVo.getEmpCode());
			AuthCheckVo authCheckVo = new AuthCheckVo();
			authCheckVo.setEmpCode(codeVo.getEmpCode());
			authCheckVo.setSystemCode(loginVo.getSystemCode());
			authCheckVo.setTokenId(codeVo.getTokenId());
			//登录校验
			authResultVo = authBiz.authCheck(authCheckVo );
			//补全参数
			authBiz.subParamForLoginCode(loginVo, authResultVo);
			if (authResultVo != null) {
				addLoginCookie(loginVo,loginResultVo,authResultVo,request,response);
			} else {
				loginResultVo.setErrorCode("fail");
			}
			//搜集浏览器信息
			try {
				saveBrowserInfo(loginVo, request, browserInfo);
			} catch (Exception e) {
				LOG.error("浏览器信息搜集失败。");
			}
			// 登录成功，设置COOKIE信息
			//add by zhangRb 20170824 校验子系统域名 start
			if (!domainValid) {
				loginResultVo.setErrorCode(ErrorCode.SYSTEM_URL_ERROR);
				return loginResultVo;
			}
			//add by zhangRb 20170824 校验子系统域名 End
		} catch (GatewayException ge) {
			loginResultVo.setErrorCode(ge.getErrorCode());
			loginResultVo.setErrorMsg(ge.getErrorMessage());
            loginResultVo.setErrorCount(ge.getErrorCount());
		} catch (Exception e) {
			LOG.error("登录失败", e);
			loginResultVo.setErrorCode("fail");
			loginResultVo.setErrorMsg("系统异常");
		}
		return loginResultVo;
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 搜集浏览器信息
	 * </pre>
	 * @param loginVo
	 * @param request
	 * @param browserInfo
	 * @return void
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月9日上午10:20:52
	 */
	private void saveBrowserInfo(LoginVo loginVo, HttpServletRequest request, String browserInfo) {

		List<PortalDictDataVo> accessSwitch = portalBiz.getDictDataByTypeClassCode(SsoMainConstant.PORTAL_ACCESS_CONTROL.PORTAL_ACCESS_SWITCH);
		if(accessSwitch != null){
			for(PortalDictDataVo dict : accessSwitch){
				//如果收集浏览器信息打开，则收集浏览器信息
				if(dict != null && dict.getTypeCode() != null && dict.getTypeCode().equals(SsoMainConstant.PORTAL_ACCESS_CONTROL.PORTAL_ACCESS_SWITCHS.GATHER_BROWSER_INFO)){
					try {
						// 收集浏览器信息
						portalBiz.saveBrowserInfo(loginVo.getUserName(),request.getRemoteAddr(),null,browserInfo);
					} catch (Exception e) {
						LOG.error("记录浏览器信息异常： ", e);
					}
				}
			}
		}
	}
	/**
	 * 
	 * @param updPwdVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author huangting
	 * @date 2017年6月8日 下午2:03:05
	 */
	@RequestMapping("/updPwd")
	@ResponseBody
	public LoginResultVo updPwd(UpdPwdVo updPwdVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginResultVo loginResultVo = new LoginResultVo();
		if (updPwdVo == null || StringUtil.isBlank(updPwdVo.getEmpCode())
				|| StringUtil.isBlank(updPwdVo.getNewPassword()) || StringUtil.isBlank(updPwdVo.getOldPassword())) {
			loginResultVo.setErrorCode(ErrorCode.ILLEGAL_PARAM);
			return loginResultVo;
		} else if (StringUtil.isBlank(updPwdVo.getSystemCode())) {
			updPwdVo.setSystemCode(AuthConstants.DEFAULT_SYSTEMCODE);
		}
		try {
			updPwdVo.setUpdateTime(new Date());
			String ipAddr = IPUtil.getRemoteHost(request);
			updPwdVo.setIpAddr(ipAddr);
			AuthResultVo resultVo = authBiz.updPwd(updPwdVo);
			if (resultVo != null) {
				loginResultVo.setErrorCode("success");
				//loginResultVo.setUserName(updPwdVo.getUserName()); ht
			} else {
				loginResultVo.setErrorCode("fail");
			}
			// 登录成功，设置COOKIE信息
		} catch (GatewayException ge) {
			LOG.error("修改密码失败", ge);
			loginResultVo.setErrorCode(ge.getErrorCode());
			loginResultVo.setErrorMsg(ge.getErrorMessage());
		} catch (Exception e) {
			LOG.error("修改密码失败", e);
			loginResultVo.setErrorCode("fail");
		}
		return loginResultVo;
	}
	
	/**
	 * 认证系统设置cookie信息，子域名可以访问到
	 * @param request
	 * @param response
	 */
	public void setCookie(HttpServletRequest request, HttpServletResponse response) {
		String encryptToken = request.getParameter("token");
		String userName = request.getParameter("userName");
		Cookie tokenCookie = new Cookie("token", encryptToken);
		tokenCookie.setMaxAge(-1);
		tokenCookie.setPath("/");
		tokenCookie.setSecure(false);
		response.addCookie(tokenCookie);
		Cookie userNameCookie = new Cookie("userName", userName);
		userNameCookie.setMaxAge(-1);
		userNameCookie.setPath("/");
		userNameCookie.setSecure(false);
		response.addCookie(userNameCookie);
	}
	/**
	 * getTopDomainByServerName
	 * @param serverName
	 * @param 
	 */
	public String getTopDomainByServerName(String serverName) {
		String topDomain = null;
		try {
			topDomain = DomainUtil.getTopDomain(serverName);
		} catch (Exception e) {
			if (IPUtil.isIp(serverName) || "localhost".equals(serverName)) {
				return serverName;
			}
			LOG.error("getTopDomain error", e);
		}
		return topDomain;
	}
	/**
	 * getTopDomainByUrl
	 * @param url
	 * @param 
	 */
	public String getTopDomainByUrl(String url) {
		String topDomain = null;
		try {
			topDomain = DomainUtil.getTopDomain(url);
		} catch (Exception e) {
			try {
				String host = getHost(url);
				if (IPUtil.isIp(host) || "localhost".equals(host)) {
					return host;
				}
				LOG.error("getTopDomain error", e);
			} catch (MalformedURLException e1) {
				LOG.error("getTopDomain error", e1);
			}
		}
		return topDomain;
	}
	/**
	 * getCrossDomainSystemCookie
	 * @param currentTopDomain
	 * @param 
	 */
	public List<String> getCrossDomainSystemCookie(String currentTopDomain) throws Exception {
		List<String> subSystemSetCookieUrl = new ArrayList<String>();
		try {
			SystemInfoVo searchSystemInfo = new SystemInfoVo();
			searchSystemInfo.setEnable(1);
			Set<String> topDomainSet = new HashSet<String>();
			List<SystemInfoVo> systemList = authBiz.findSystemByCondition(searchSystemInfo);
			if (systemList != null) {
				for (SystemInfoVo system : systemList) {
					try {
						if (StringUtil.isNotBlank(system.getCasUrl())) {
							String topDomain = getTopDomainByUrl(system.getCasUrl());
							if (topDomainSet.add(topDomain)) {
								// 域名相同，或者子域名相同，直接通过cookie共享，不相等时，需要调用子系统一个设置cookie的地址
								if (StringUtil.isNotBlank(topDomain) && !topDomain.equals(currentTopDomain)) {
									subSystemSetCookieUrl.add(system.getCasUrl());
								}
							}
						}
					} catch (Exception e) {
						LOG.error("url error," + system.getCasUrl(), e);
					}
				}
			}
		} catch (Exception e) {
			LOG.error("获取系统列表失败", e);
		}
		return subSystemSetCookieUrl;
	}
	
	/**
	 * 获取主机名
	 * @param urlStr
	 * @return
	 * @throws MalformedURLException
	 * @author huangting
	 * @date 2017年6月8日 下午2:04:01
	 */
	private String getHost(String urlStr) throws MalformedURLException {
		if (StringUtil.isBlank(urlStr)) {
			return null;
		}
		java.net.URL url = new java.net.URL(urlStr);
		String host = url.getHost();// 获取主机名
		return host;
	}

	/**
	 * 获取cookie信息
	 * @param cookieName
	 * @param cookieValue
	 * @param topDomain
	 * @return
	 * @author huangting
	 * @date 2017年6月8日 下午2:05:41
	 */
	private Cookie getCookie(String cookieName, String cookieValue, String topDomain) {
		Cookie cookie = new Cookie(cookieName, cookieValue);
		// 设置domain
		cookie.setDomain(topDomain);
		// 设置path
		cookie.setPath("/");
		// 设置以后无法再前端document中看到该cookie，提高了安全性
		cookie.setHttpOnly(true);
		// 过期时间，秒为单位，
		// 小于0表示保存在内存中，关闭浏览器cookie就消失，
		cookie.setMaxAge(-1);
		// 创建的 cookie 只能在 HTTPS 连接中被浏览器传递到服务器端进行会话验证
		cookie.setSecure(false);
		return cookie;
	}

    /**
    * @Description: 生成验证码
    * @author wangshangbing
    * @date 2017/9/12 16:05
    */

	@RequestMapping(value="getJPGCode",method= RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getJPGCode(HttpServletResponse response,HttpServletRequest request,
						   @RequestParam(value = "key", required = false, defaultValue = "defaultValue") String key,
						   @RequestParam(value = "userName", required = false, defaultValue = "defaultValue") String userName) {
		try {
			//response.setHeader("Pragma", "No-cache");
			//response.setHeader("Cache-Control", "no-cache");
			//response.setDateHeader("Expires", 0);
			//response.setContentType("image/jpg");
			//获取IP地址
			//String sessionId = request.getSession().getId();
			//获取验证码保存redis并输出数据
			//imageCodeUtils.getImageCodeAndSaveRedis(response, key, userName, ipAddr);
			//获取远程主机
			String ipAddr = IPUtil.getRemoteHost(request);
			Map<String, Object> imageCode = imageCodeUtils.getImageCode(ipAddr);
			return imageCode;
		} catch (Exception e) {
			LOG.error("获取验证码异常", e);
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("resultCode", "error");
			map.put("resultMessage", "获取验证码失败");
			return map;
		}
	}
	
	/**
	 * 方法的描述：
	 * <pre>
	 * 根据员工编号查询员工手机号(手机号中4位转换为*)
	 * </pre>
	 * @param response
	 * @param request
	 * @param empCode
	 * @param checkCode
	 * @param sig
	 * @param t
	 * @return
	 * @return Map<String,Object>
	 * @author LH
	 * @email luhenguce@uce.cn
	 * @date 2019年10月9日下午2:36:52
	 */
	@RequestMapping(value="findMobileAndCheckEmp",method= RequestMethod.GET)
	@ResponseBody	
	public Map<String, Object> findMobileAndCheckEmp(HttpServletResponse response,HttpServletRequest request,String empCode, String checkCode, String sig, long t){
		Map<String, Object> map = findAndCheckEmp(response, request, empCode, checkCode, sig, t);
		Boolean success = (Boolean) map.get("success");
		if(success){
			StringBuffer mobile = new StringBuffer((String) map.get("mobile"));
			mobile.replace(3, 7,"****");
			map.put("mobile", mobile.toString());
		}
		return map;
	}
	
	/**
	 * 方法的描述：
	 * <pre>
	 * 根据员工编号查询员工手机号
	 * </pre>
	 * @param response
	 * @param request
	 * @param empCode
	 * @return
	 * @return Map<String,Object>
	 * @author LH
	 * @email luhenguce@uce.cn
	 * @date 2019年7月3日下午4:56:34
	 */
	@RequestMapping(value="findAndCheckEmp",method= RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findAndCheckEmp(HttpServletResponse response,HttpServletRequest request,String empCode, String checkCode, String sig, long t){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//校验图片验证码
			String ipAddr = IPUtil.getRemoteHost(request);
			checkImgCode(ipAddr, checkCode, sig, t);
			map.put("success", true);
			// 校验用户是否存在
			EmpInfoVo empInfoVo = authBiz.authCheckUser(empCode, AuthConstants.LOGIN_TYPE_EMP_CODE);
			if(empInfoVo != null){
				EmpVo empVo = empInfoVo.getEmpVo();
				if(empVo == null){
					map = returnError("用户不存在!");
				}else if(StringUtils.isBlank(empVo.getMobile())){
					map = returnError("用户未绑定手机号!");
				}else{
					map.put("mobile", empVo.getMobile());
				}
			}else{
				map = returnError("用户不存在!");
			}
		} catch (GatewayException ge) {
			LOG.error("校验员工工号异常", ge);
			if(ErrorCode.CHECK_CODE_ERROR_CODE.equals(ge.getErrorCode())){
				map = returnError(ge.getErrorMessage());	 
				map.put("errorCode", ErrorCode.CHECK_CODE_ERROR_CODE);
			}else{
				map = returnError(ge.getErrorMessage());	 
			}
		} catch (Exception e) {
			LOG.error("checkEmpCode error", e);
			map = returnError("校验员工工号异常");
		}
		return map;
	}
	/**
	 * @param authBiz the authBiz to set
	 */
	public void setAuthBiz(IAuthBiz authBiz) {
		this.authBiz = authBiz;
	}

	/**
	 * @param codeUtil the codeUtil to set
	 */
	public void setCodeUtil(CodeUtil codeUtil) {
		this.codeUtil = codeUtil;
	}

	/**
	 * @param empInfoUtil the empInfoUtil to set
	 */
	public void setEmpInfoUtil(EmpInfoUtil empInfoUtil) {
		this.empInfoUtil = empInfoUtil;
	}
	/**
	 * @param mobileUtil the mobileUtil to set
	 */
	public void setMobileUtil(MobileUtil mobileUtil) {
		this.mobileUtil = mobileUtil;
	}
	/**
	 * 
	  * <p>Description : 设置图片验证码类</p>
	  * @author : crj
	  * @date : 2017年10月27日下午12:50:30
	 */
	public void setImageCodeUtils(ImageCodeUtils imageCodeUtils) {
		this.imageCodeUtils = imageCodeUtils;
	}

	public void setLoginCodeUtil(LoginCodeUtil loginCodeUtil) {
		this.loginCodeUtil = loginCodeUtil;
	}
	public void setYmHttpClientUtil(YmHttpClientUtil ymHttpClientUtil) {
		this.ymHttpClientUtil = ymHttpClientUtil;
	}
	
	
	
}
