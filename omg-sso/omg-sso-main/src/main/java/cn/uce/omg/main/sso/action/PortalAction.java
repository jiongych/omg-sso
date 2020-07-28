package cn.uce.omg.main.sso.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.uce.omg.sso.constant.OmgConstants;
import cn.uce.omg.sso.util.EmpInfoUtil;
import cn.uce.omg.sso.vo.*;
import cn.uce.omg.vo.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.uce.core.mq.api.MqTemplate;
import cn.uce.omg.main.constant.ErrorMsg;
import cn.uce.omg.main.sso.constant.SsoMainConstant;
import cn.uce.omg.main.util.CookieUtil;
import cn.uce.omg.main.util.DomainUtil;
import cn.uce.omg.main.util.ResponseModel;
import cn.uce.omg.sso.biz.IAuthBiz;
import cn.uce.omg.sso.biz.IEmpBiz;
import cn.uce.omg.sso.biz.IOrgBiz;
import cn.uce.omg.sso.biz.IPortalBiz;
import cn.uce.omg.sso.biz.ISystemInfoBiz;
import cn.uce.omg.sso.cache.HashRedisWithFieldExpireCache;
import cn.uce.omg.sso.constant.AuthConstants;
import cn.uce.omg.sso.constant.ErrorCode;
import cn.uce.omg.sso.entity.portal.PortalUserMenu;
import cn.uce.omg.sso.exception.GatewayException;
import cn.uce.omg.sso.util.IPUtil;
import cn.uce.omg.sso.util.redis.BoundHashRedisSupport;
import cn.uce.omg.sso.vo.portal.PortalCollectPermissionVo;
import cn.uce.omg.sso.vo.portal.PortalCompanyNoticeVo;
import cn.uce.omg.sso.vo.portal.PortalCurrentUser;
import cn.uce.omg.sso.vo.portal.PortalDictDataVo;
import cn.uce.omg.sso.vo.portal.PortalHomePageInfoVo;
import cn.uce.omg.sso.vo.portal.PortalMenuTreeVo;
import cn.uce.omg.sso.vo.portal.PortalMessageStatusVo;
import cn.uce.omg.sso.vo.portal.PortalPermissionVo;
import cn.uce.utils.StringUtil;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
/**
 * 门户控制类
 * @author crj
 * @date 2018年1月9日 下午2:06:41
 */
@Controller
@RequestMapping("/portal")
public class PortalAction {
	
	private Log LOG = LogFactory.getLog(this.getClass());
	@Autowired
	private IOrgBiz orgBiz;
	@Autowired
	private IEmpBiz empBiz;
	@Autowired
	private ISystemInfoBiz systemInfoBiz;
	@Autowired
	private IAuthBiz authBiz;
	@Autowired
	private IPortalBiz portalBiz;
	@Autowired
	private BoundHashRedisSupport<PortalCurrentUser> tokenUserInfoCache;
	@Autowired
	private BoundHashRedisSupport<List<String>> userTokenCache;
	@Autowired
	private HashRedisWithFieldExpireCache<TokenVo> tokenSystemCache;
	@Autowired
	private MqTemplate passwordUpdateTemplate;
	@Autowired
	private EmpInfoUtil empInfoUtil;
	
	private final String[] cookieNames = new String[] {"token", "userName", "empCode","empName","cmsOrgId","cmsOrgIdStr","omgOrgId","updPwdFlag","updPwdIntervalDay","lastLoginTime","encryptOrgId","portalIndex","sid","yhEmpCode","compCode"};
	
	//add by xj
    /**
	 * 跳转portal页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/forward")
	public String forward(HttpServletRequest request) {
		/*List<PortalDictDataVo> allOpenPortal = portalBiz.getDictDataByTypeClassCode(SsoMainConstant.PORTAL_ACCESS_CONTROL.ALLOW_EMPCODE);
		String empCode = (String) request.getAttribute("empCode");
		if (allOpenPortal != null && !allOpenPortal.isEmpty()) {
			boolean oldStyle = true;
			for (PortalDictDataVo dict : allOpenPortal) {
				if(dict != null && dict.getTypeCode() != null && dict.getTypeCode().equals(empCode)){
					oldStyle = false;
				}
			}
			if(oldStyle){
				return "/WEB-INF/pages/portal3.jsp";
			}
		}*/
		return "/WEB-INF/pages/portal.jsp";
	}
	
	/**
	 * 跳转home页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/home")
	public String home(HttpServletRequest request) {
		return "/WEB-INF/pages/home.jsp";
	}
	
	/**
	 * 跳转home页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/siteHome")
	public String siteHome(HttpServletRequest request) {
		return "/WEB-INF/pages/siteHome.jsp";
	}
	
	/**
	 * 跳转跨域中转页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/crossDomain")
	public String crossDomain(HttpServletRequest request) {
		return "/WEB-INF/pages/crossDomain.jsp";
	}
	//add by xj
	
	/**
	 * @Description: (获取扁平菜单树) 
	 * @param request
	 * @return
	 * @author XJ
	 * @date 2018年4月10日 上午9:49:24
	 */
	@RequestMapping("/getMenuTree")
	@ResponseBody
	public List<PortalMenuTreeVo> getMenuTree(HttpServletRequest request){
		String empCode = (String) request.getAttribute("empCode");
		return portalBiz.getMenuTree(empCode);
	}
	
	/**
	 * @Description: (获取结构化菜单树) 
	 * @param request
	 * @return
	 * @author XJ
	 * @date 2018年4月10日 上午9:49:50
	 */
	@RequestMapping("/getExpandMenuTree")
	@ResponseBody
	public List<PortalMenuTreeVo> getExpandMenuTree(HttpServletRequest request){
		String empCode = (String) request.getAttribute("empCode");
		return portalBiz.getExpandMenuTree(empCode);
	}
	
	/**
	 * @Description: (获取当前用户信息) 
	 * @return
	 * @author XJ
	 * @date 2017年7月28日 上午8:51:44
	 */
	@RequestMapping(value="getCurrentUser")
	@ResponseBody
	public PortalCurrentUser getCurrentUser(HttpServletRequest request) {
		String token = (String) request.getAttribute("token");
		PortalCurrentUser userInfo = tokenUserInfoCache.get(token);
		if (null != userInfo.getOrgId()) {
			String orgFullId = portalBiz.findFullOrgIdList(userInfo.getOrgId() + "");
			userInfo.setOrgFullId(orgFullId);
		}
		userInfo.setToken(token);
		return userInfo;
	};
	
	/**
	 * 获取员工token信息及机构信息
	 * @param request
	 * @return
	 * @author huangting
	 * @date 2019年2月19日 下午1:53:55
	 */
	@RequestMapping(value="getCurrentUserToken")
	@ResponseBody
	public PortalCurrentUser getCurrentUserToken(HttpServletRequest request) {
		String token = (String) request.getAttribute("token");
		PortalCurrentUser userInfo = tokenUserInfoCache.get(token);
		userInfo.setToken(token);
		return userInfo;
	};


	@RequestMapping(value="getLoginUserInfo")
	@ResponseBody
	public AuthResultVo getLoginUserInfo(HttpServletRequest request) {
		String token = (String) request.getAttribute("token");
		PortalCurrentUser userInfo = tokenUserInfoCache.get(token);
		AuthResultVo authResultVo = new AuthResultVo();
		if (userInfo != null) {
			BeanUtils.copyProperties(userInfo,authResultVo);
			authResultVo.setTokenId(token);
			EmpInfoVo empInfoVo = empInfoUtil.getEmpInfoByKey(userInfo.getEmpCode(),AuthConstants.LOGIN_TYPE_EMP_CODE);
			if (empInfoVo != null) {
				EmpVo emp = empInfoVo.getEmpVo();
				UserVo user = empInfoVo.getUserVo();
				authResultVo.setPrincipalFlag(emp.getPrincipalFlag());
				authResultVo.setLastLoginTime(user.getLastLoginTime());
			}
			TokenVo tokenVo = tokenSystemCache.get(token);
			if (tokenVo != null) {
				authResultVo.setExpireTime(new Date(tokenVo.getExpireTime()));
			}
			List<OtherEmpRelationVo> otherEmpRelationList = empBiz.findQkEmpRelationByEmpId(userInfo.getEmpId());
			StringBuffer otherOrgIdStr = new StringBuffer();
			if (otherEmpRelationList != null && otherEmpRelationList.size() > 0) {
				for (int i = 0; i < otherEmpRelationList.size(); i++) {
					if (i == otherEmpRelationList.size() - 1) {
						otherOrgIdStr = otherOrgIdStr.append(otherEmpRelationList.get(i).getOtherOrgId());
					} else {
						otherOrgIdStr = otherOrgIdStr.append(otherEmpRelationList.get(i).getOtherOrgId()).append("#");
					}
				}
				authResultVo.setOtherOrgIdStr(otherOrgIdStr.toString());
			}
		}
		return authResultVo;
	};
	
	/**
	 * @Description: (获取当前用户信息) 
	 * @return
	 * @author XJ
	 * @date 2017年7月28日 上午8:51:44
	 */
	@RequestMapping(value="getCurrentUserNew")
	@ResponseBody
	public PortalCurrentUser getCurrentUserNew(HttpServletRequest request) {
		String empCode = (String) request.getAttribute("empCode");
		PortalCurrentUser userInfo = portalBiz.findCurrentUser(empCode);
		return userInfo;
	};
	
	/**
	 * @Description: (findPermissionCodeByCurrentUser) 
	 * @param request
	 * @return
	 * @author XJ
	 * @date 2018年4月19日 下午4:21:26
	 */
	@RequestMapping(value="findPermissionCodeByCurrentUser")
	@ResponseBody
	public List<String> findPermissionCodeByCurrentUser(HttpServletRequest request){
		String empCode = (String) request.getAttribute("empCode");
		return portalBiz.findPermissionCodeByCurrentUser(empCode);
	}
	
	/**
	 * @Description: (切换部门) 
	 * @author XJ
	 * @date 2018年4月3日 下午3:47:02
	 */
	@RequestMapping(value="changeOrg")
	@ResponseBody
	public ResponseModel changeOrg(HttpServletRequest request,HttpServletResponse response){
		String token = (String) request.getAttribute("token");
		Integer orgId = Integer.parseInt(request.getParameter("orgId"));
		
		ResponseModel responseModel = new ResponseModel();
		String empCode = (String) request.getAttribute("empCode");
		PortalCurrentUser userInfo = portalBiz.findCurrentUser(empCode);
		if(userInfo != null && userInfo.getOrgRefRel() != null){
			for(Map<String,Object> loginEmpOrgRel : userInfo.getOrgRefRel()){
				if(orgId != null && orgId.equals((Integer)loginEmpOrgRel.get("orgId"))){
					//1.刷新tokenUserInfoCache
					userInfo.setOrgId((Integer) loginEmpOrgRel.get("orgId"));
					userInfo.setOrgCode((String) loginEmpOrgRel.get("orgCode"));
					userInfo.setOrgType((Integer) loginEmpOrgRel.get("orgType"));
					userInfo.setOrgName((String) loginEmpOrgRel.get("orgName"));
					userInfo.setCmsOrgId(loginEmpOrgRel.get("otherOrgId").toString());
					userInfo.setCmsBaseOrgCode((String) loginEmpOrgRel.get("otherBaseOrgCode"));
					userInfo.setCmsOrgType((Integer) loginEmpOrgRel.get("otherOrgType"));
					userInfo.setCmsOrgName((String) loginEmpOrgRel.get("otherOrgName"));
					tokenUserInfoCache.put(token, userInfo);
					//2.刷新cookie中的org
					String topDomain = getTopDomainByServerName(request.getServerName());
					response.addCookie(getCookie("omgOrgId", loginEmpOrgRel.get("orgId").toString(), topDomain));
					response.addCookie(getCookie("encryptOrgId", new String(Base64.encodeBase64(loginEmpOrgRel.get("orgId").toString().getBytes())), topDomain));
					response.addCookie(getCookie("cmsOrgId", loginEmpOrgRel.get("otherOrgId").toString(), topDomain));
					return responseModel.setStatus(ResponseModel.SUCCESS);
				}
			}
		}
		return responseModel.setStatus(ResponseModel.ERROR).setMessage("未找到该绑定关系..");
		
		
	}
	/**
	 * 获取cookie信息
	 * @param cookieName
	 * @param cookieValue
	 * @param topDomain
	 * @return
	 * @author XJ
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
	 *获取portal页面所需数据
	 * @param empCode
	 * @return
	 */
	@RequestMapping("/portalInfo")
	@ResponseBody
	public ResponseModel portalInfo(HttpServletRequest request) {
		//设置返回信息
		ResponseModel responseModel = new ResponseModel();
		try {
			String empCode = (String) request.getAttribute("empCode");
			//判断转入参数是否为空
			if(empCode != null && !"".equals(empCode.trim())){
				//根据员工编码查询员工信息；
				EmpVo empVo = empBiz.findEmpByEmpCode(empCode);
				List<OtherEmpRelationVo> otherEmpRelationVos = empBiz.findQkEmpRelationByEmpId(empVo.getEmpId());
				List<Integer> orgIds = Lists.newArrayList();
				orgIds.add(empVo.getOrgId());
				if(otherEmpRelationVos != null && otherEmpRelationVos.size() > 0){
					for(OtherEmpRelationVo vo : otherEmpRelationVos){
						orgIds.add(vo.getOrgId());
					}
				}
				//根据机构ID查询机构信息
				List<OrgVo> orgVos = orgBiz.findOrgByIds(orgIds);
				//设置返回数据
				Map<String,Object> datas = Maps.newHashMap();
				datas.put("empCode", empVo.getEmpCode());
				datas.put("empName", empVo.getEmpName());
				
				if(orgVos != null && orgVos.size() > 0){
					List<Map<String,Object>> otherOrgs = Lists.newArrayList();
					for(OrgVo orgVo : orgVos){
						Map<String,Object> otherOrg = Maps.newHashMap();
						if(empVo.getOrgId().toString().equals(orgVo.getOrgId().toString())){
							datas.put("orgCode", orgVo.getOrgCode());
							datas.put("orgType", orgVo.getOrgType());
							datas.put("orgName", orgVo.getOrgName());
						}
						otherOrg.put("orgCode", orgVo.getOrgCode());
						otherOrg.put("orgType", orgVo.getOrgType());
						otherOrg.put("orgName", orgVo.getOrgName());
						otherOrgs.add(otherOrg);
					}
					datas.put("otherOrg", otherOrgs);
				}
				
				//获取所有启动的系统信息
				SystemInfoVo systemInfoVo = new SystemInfoVo();
				systemInfoVo.setEnable(1);
				List<SystemInfoVo> sysTemInfoVos = systemInfoBiz.findByCondition(systemInfoVo);
				//只返回前端所需的系统信息， 其他信息就不必返回
				List<Map<String,Object>> sysInfos = Lists.newArrayList();
				for(SystemInfoVo sysTemInfoVo : sysTemInfoVos){
					Map<String,Object> sysInfo = Maps.newHashMap();
					sysInfo.put("systemName", sysTemInfoVo.getSystemName());
					sysInfo.put("systemCode", sysTemInfoVo.getSystemCode());
					sysInfo.put("refUrl", sysTemInfoVo.getWebIndexUrl());
					sysInfo.put("remark", sysTemInfoVo.getRemark());
					sysInfos.add(sysInfo);
				}
				//设置放回的数据
				datas.put("sysTmeInfo", sysInfos);
				responseModel.setErrorMsg(ErrorMsg.SUCCESS);
				responseModel.setData(datas);
				
			}else{
				//设置错误消息
				responseModel.setErrorMsg(ErrorMsg.EMPCODE_NULL);
			}
		} catch (Exception e) {
			//设置错误消息
			LOG.error("获取portal数据异常：", e);
			responseModel.setErrorMsg(ErrorMsg.EXCEPTION);
		}
		return responseModel;
	}
	
	/**
	 * 跳转portal页面
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/logout")
	@ResponseBody
	public void logOut(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		ResponseModel responseModel = new ResponseModel();
		String empCode = (String) request.getAttribute("empCode");
		String token = (String) request.getAttribute("token");
		String Ip = getIpAddress(request);

		LogoutVo logoutVo = new LogoutVo();
		//设置用户toenID
		logoutVo.setTokenId(token);
		//设置系统码
		logoutVo.setSystemCode("SSO");
		//设置登出时间
		logoutVo.setLogoutTime(new Date());
		//设置用户Code
		logoutVo.setEmpCode(empCode);
		//设置用户IP
		logoutVo.setIpAddr(Ip);
		try {
			authBiz.logout(logoutVo);
			HttpSession session = request.getSession();
			//删除cookie信息，by jiyongchao
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		responseModel.setErrorMsg(ErrorMsg.SUCCESS);

		response.setStatus(302);
		response.setHeader("location",request.getContextPath()+"/toLogin.action");
	}
	
	/**
	 * 
	 * @param updPwdVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @date 2018年1月12日 下午2:03:05
	 */
	@RequestMapping("/updPwd")
	@ResponseBody
	public LoginResultVo updPwd(UpdPwdVo updPwdVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String empCode = (String) request.getAttribute("empCode");
		String token = (String) request.getAttribute("token");
		updPwdVo.setEmpCode(empCode);
		updPwdVo.setTokenId(token);
		LoginResultVo loginResultVo = new LoginResultVo();
		if (StringUtil.isBlank(updPwdVo.getEmpCode())
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
				//add by xj
				List<PortalDictDataVo> accessSwitch = portalBiz.getDictDataByTypeClassCode(SsoMainConstant.PORTAL_ACCESS_CONTROL.PORTAL_ACCESS_SWITCH);
				for(PortalDictDataVo dict : accessSwitch){
					//存在密码修改推送开关，则推送，否则不推送
					if(dict != null && dict.getTypeCode() != null && dict.getTypeCode().equals(SsoMainConstant.PORTAL_ACCESS_CONTROL.PORTAL_ACCESS_SWITCHS.UPDATE_PASSWORD_PUSH)){
						PasswordUpdateVo up = new PasswordUpdateVo();
						up.setEmpCode(empCode);
						passwordUpdateTemplate.send(JSON.toJSONString(up));
					}
				}
				//add by xj
			} else {
				loginResultVo.setErrorCode("fail");
			}
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
	 * 获取公告
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/article")
	@ResponseBody
	public ResponseModel article(HttpServletRequest request) {
		ResponseModel responseModel = new ResponseModel();
		try {
			String path = request.getSession().getServletContext().getRealPath("/");
			responseModel = getFileName(path);
		} catch (Exception e) {
			LOG.error("portal获取公告失败：" ,e);
			responseModel.setErrorMsg(ErrorMsg.EXCEPTION);
		}
        return responseModel;
	}
	
	/**
	 * 获取公告内容
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws Exception 
	 */
	@RequestMapping("/getArticle")
	@ResponseBody
	public void getArticle(HttpServletRequest request,HttpServletResponse response) throws IOException {
		FileInputStream fis = null;
		BufferedInputStream bis =null;
		ServletOutputStream outputStream = null;
		try {
			String fileName = request.getParameter("fileName");
			if(fileName != null && !"".equals(fileName) && 
					!fileName.contains("/") && !fileName.contains("\\")){
				String path = request.getSession().getServletContext().getRealPath("/");
				path += "/article/"+fileName; // 路径
				File file = new File(path);
			    fis = new FileInputStream(file);
		        bis = new BufferedInputStream(fis);
		        outputStream = response.getOutputStream();
		        byte[] b = new byte[bis.available()];  
		        int i = bis.read(b);
		        if (i == 0) {
		        	LOG.error("获取文件为空");
		        }
		        outputStream.write(b);
			}
		} catch (Exception e) {
			LOG.error("portal获取公告失败：" ,e);
		}finally{
			if(fis != null){
				fis.close();
			}
			if(bis != null){
				bis.close();
			}
			if(outputStream != null){
				outputStream.close();
			}
		}
		
	}
	
	
	/**
	 * 获取所有文件的目录名称
	 * @throws UnsupportedEncodingException 
	 */
	private ResponseModel getFileName(String path) throws UnsupportedEncodingException {
		ResponseModel responseModel = new ResponseModel();
		path += "/article"; // 路径
        File file = new File(path);
        if (!file.exists()) {
        	responseModel.setErrorMsg(ErrorMsg.FILE_EXISTS);
            return responseModel;
        }
        String fileCode = System.getProperty("file.encoding");
        List<String> sort = Lists.newArrayList();
        File fa[] = file.listFiles();
        for (int i = 0; i < fa.length; i++) {
        	File fs = fa[i];
            if (!fs.isDirectory()) {
            	 String fileName = fs.getName();
                 fileName = new String (fileName.getBytes(fileCode),fileCode);
            	sort.add(fileName);
            }
            
        }
        List<Map<String,Object>> fileNames = Lists.newArrayList();
        Collections.sort(sort,Collections.reverseOrder());  
        for(int j = 0 ; j < sort.size(); j++){
        	Map<String,Object> map = Maps.newHashMap();
        	String[] split = getFileNameNoEx(sort.get(j)).split("_");
         	map.put("date", split[0]);
         	map.put("fileName", split[1]);
         	map.put("fileNameAll", sort.get(j));
         	fileNames.add(map);
        }
        responseModel.setErrorMsg(ErrorMsg.SUCCESS);
        responseModel.setData(fileNames);
        return responseModel;
    }
	/**
	 * 获取不带后缀的文件名
	 * @param filename
	 * @return
	 */
	private String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	} 
	
	/**
	 * Description: 获取IP地址
	 * @param request
	 * @return
	 * @author zhangRenbing
	 * @date 2017年7月10日
	 */
	public String getIpAddress(HttpServletRequest request) {  
        String ip = request.getHeader("x-forwarded-for");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }  
	
	
	/**
	 * 
	  * <p>Description : 设置机构类</p>
	  * @author : crj
	  * @date : 2018年1月9日下午12:50:30
	 */
	public void setOrgBiz(IOrgBiz orgBiz) {
		this.orgBiz = orgBiz;
	}
	/**
	 * 
	  * <p>Description : 设置员工类</p>
	  * @author : crj
	  * @date : 2018年1月9日下午12:50:30
	 */
	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
	}
	/**
	 * 
	  * <p>Description : 设置系统类</p>
	  * @author : crj
	  * @date : 2018年1月9日下午12:50:30
	 */
	public void setSystemInfoBiz(ISystemInfoBiz systemInfoBiz) {
		this.systemInfoBiz = systemInfoBiz;
	}
	/**
	 * 
	  * <p>Description : 设置类</p>
	  * @author : crj
	  * @date : 2018年1月9日下午12:50:30
	 */
	public void setAuthBiz(IAuthBiz authBiz) {
		this.authBiz = authBiz;
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * portal前台页面弹出提示公告
	 * </pre>
	 * @param request
	 * @return
	 * @return List<PortalCompanyNoticeVo>
	 * @author zb
	 * @date 2018年06月08日下午2:37:48
	 */
	@RequestMapping("/findNoticeAlert")
	@ResponseBody
	public List<PortalCompanyNoticeVo> findNoticeAlert(HttpServletRequest request, String orgId) {
		// 查询通知信息
		List<PortalCompanyNoticeVo> portalCompanyNoticeVos = portalBiz.findNoticeAlert((String) request.getAttribute("empCode"), orgId);
		return portalCompanyNoticeVos;
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * portal前台页面展示前十条公司公告
	 * </pre>
	 * @param request
	 * @return
	 * @return List<PortalCompanyNoticeVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月30日下午2:37:48
	 */
	@RequestMapping("/noticeTopicTen")
	@ResponseBody
	public List<PortalCompanyNoticeVo> noticeTopicTen(HttpServletRequest request, String typeId, String orgId) {
		// 查询通知信息
		List<PortalCompanyNoticeVo> portalCompanyNoticeVos = portalBiz.findNoticeTopicTen((String) request.getAttribute("empCode"), typeId, orgId);
		return portalCompanyNoticeVos;
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据ID查询数据
	 * </pre>
	 * @param request
	 * @param id
	 * @return
	 * @return PortalCompanyNotice
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月30日下午2:39:03
	 */
	@RequestMapping("/findNoticById")
	@ResponseBody
	public PortalCompanyNoticeVo findNoticById(HttpServletRequest request,String empCode, Long id, String orgCode, String empName) {
		PortalCompanyNoticeVo portalCompanyNoticeVo = portalBiz.saveAndFindById(id, empCode, orgCode, empName);
		
		OrgVo orgVo = orgBiz.findOrgByOrgCode(portalCompanyNoticeVo.getCreateOrg());
		if (null != orgVo) {
			portalCompanyNoticeVo.setCreateOrgName(orgVo.getOrgName());
		}
		int viewNum = portalBiz.findCompanyViewNum(portalCompanyNoticeVo.getId());
		portalCompanyNoticeVo.setViewCount(viewNum);
		return portalCompanyNoticeVo;
	}
	/**
	 * @Description: 根据UserMenu的empCode、orgCode查询，
	 * 获取UserMenu的permissionId集合，用于前端常用菜单展示
	 * @return
	 * @throws Exception
	 * @author JYC
	 * @date 2017年08月02日 上午9:43:25
	 */
	@RequestMapping(value="getUserMenu")
	@ResponseBody
	public List<Long> getUserMenu(HttpServletRequest request) throws Exception {
		PortalUserMenu userMenu = new PortalUserMenu();
		userMenu.setEmpCode((String) request.getAttribute("empCode"));
		PortalCurrentUser currentUser = getCurrentUser(request);
		if(currentUser != null){
			userMenu.setOrgCode(currentUser.getCmsBaseOrgCode());
		}
		List<Long> userMenuList = portalBiz.findUserMenuByEmpCodeAndOrgCode(userMenu);
		if (userMenuList == null || userMenuList.isEmpty()) {
			String token = (String) request.getAttribute("token");
			PortalCurrentUser userInfo = tokenUserInfoCache.get(token);
			//增加默认常用菜单
			userMenu.setEmpCode(userInfo.getOrgType().toString());
			userMenu.setOrgCode(userInfo.getOrgType().toString());
			userMenuList = portalBiz.findUserMenuByEmpCodeAndOrgCode(userMenu);
		}
		return userMenuList;
	}
	
	/**
	 * @Description: 常用菜单的移入和移除操作
	 * @param ids
	 * @param empCode
	 * @param cmsBaseOrgCode
	 * @param way：in为移入操作，out为移除操作
	 * @return
	 * @throws Exception
	 * @author JYC
	 * @date 2017年08月02日 上午9:43:25
	 */
	@RequestMapping(value="saveUserMenu")
	@ResponseBody
	public void saveUserMenu(Long[] ids, String empCode, String cmsBaseOrgCode) throws Exception {
		if (!checkValue(ids)) {
			Long[] sorts = new Long[ids.length];
			for (int i = 0; i < ids.length; i++) {
				sorts[i] = (long) (i + 1);
			}
			//通过permissionId集合、empCode、orgCode、sorts集合加工成List<UserMenu>
			List<PortalUserMenu> userMenuList = portalBiz.processByParam(ids, empCode, cmsBaseOrgCode, sorts);
			//常用菜单初始化为空时
			portalBiz.deleteUserMenuByEmpAndOrg(empCode, cmsBaseOrgCode);
			if (null != userMenuList && !userMenuList.isEmpty()) {
				portalBiz.saveUserMenu(userMenuList);
			}
		} else {
			portalBiz.deleteUserMenuByEmpAndOrg(empCode, cmsBaseOrgCode);
		}
	}
	
	/**
	 * 单条操作常用菜单
	 * @param permissionId
	 */
	@RequestMapping(value="operatorUserMenu")
	@ResponseBody
	public String operatorUserMenu(HttpServletRequest request, Long permissionId, int operatorFlag, String empCode, String cmsBaseOrgCode) {
		if (null != permissionId) {
			try {
				//加常用菜单
				if (operatorFlag == 0) {
					PortalUserMenu userMenu = new PortalUserMenu();
					userMenu.setPermissionId(permissionId);
					userMenu.setEmpCode(empCode);
					userMenu.setOrgCode(cmsBaseOrgCode);
					userMenu.setCreateTime(new Date());
					userMenu.setSort(0);
					LOG.warn("收藏常用菜单："+JSON.toJSONString(userMenu));
					portalBiz.saveUserMenu(userMenu);
				} else {
					portalBiz.deleteUserMenuByParam(empCode, cmsBaseOrgCode, permissionId);
				}
			} catch (Exception e) {
				if (e.getCause().toString().contains("MySQLIntegrityConstraintViolationException")) {
					return "success";
				}
				LOG.warn("常用菜单操作报错。", e);
				return "fail";
			}
		}
		return "success";
	}
	
	/**
	 * @Description: 判断Long[]是否为空
	 * @return boolean
	 * @param arr
	 * @author JYC
	 * @date 2017年08月02日 上午9:43:25
	 */
	public static boolean checkValue(Long[] arr) {
		if (null == arr) {
			return true;
		}
		if (arr.length == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 查询各系统首页跳转信息
	 * </pre>
	 * @param request
	 * @return
	 * @return List<PortalHomePageInfoVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年4月2日下午3:55:03
	 */
	@RequestMapping("/findHomePageInfo")
	@ResponseBody
	public List<PortalHomePageInfoVo> findHomePageInfo(HttpServletRequest request) {
		List<PortalHomePageInfoVo> portalHomePageInfoVos = portalBiz.findHomePageInfo();
		return portalHomePageInfoVos;
	}
	
	/**
	 * @Description: (getDictDataByTypeClassCode) 
	 * @param typeClassCode
	 * @return
	 * @author XJ
	 * @date 2018年5月24日 下午2:09:34
	 */
	@RequestMapping("/getDictDataByTypeClassCode")
	@ResponseBody
	public List<PortalDictDataVo> getDictDataByTypeClassCode(String typeClassCode){
		return portalBiz.getDictDataByTypeClassCode(typeClassCode);
	}
	
	@RequestMapping("/refreshToken")
	@ResponseBody
	public boolean refreshToken(){
		return true;
	}
	
	/**
	 * 根据permissionCode和systemCode查询菜单
	 * @param permissionCode
	 * @param systemCode
	 * @return
	 */
	@RequestMapping("/findPermissionAndSystemCode")
	@ResponseBody
	public PortalPermissionVo findPermissionAndSystemCode(String permissionCode, String systemCode){
		return portalBiz.findPermissionAndSystemCode(permissionCode, systemCode);
	}
	
	/**
	 * 菜单权限校验
	 * @param empCode
	 * @param systemCode
	 * @return
	 */
	@RequestMapping("/menuPermissionCheck")
	@ResponseBody
	public boolean menuPermissionCheck(String empCode,String systemCode){
		return portalBiz.menuPermissionCheck(empCode, systemCode);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 菜单操作日志搜集
	 * </pre>
	 * @return void
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月10日下午1:47:39
	 */
	@RequestMapping("/collectOpenPermissionInfo")
	@ResponseBody
	public void collectOpenPermissionInfo(PortalCollectPermissionVo portalPermission) {
		portalPermission.setCreateTime(new Date());
		portalPermission.setCreateEmp(portalPermission.getEmpCode());
		portalBiz.collectOpenPermissionInfo(portalPermission);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 查询公告类型
	 * </pre>
	 * @return
	 * @return Map<String,Object>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月19日下午3:14:22
	 */
	@RequestMapping(value="findNoticeType")
	@ResponseBody
	public List<NoticeParentInfoVo> findNoticeType() {
		
		return portalBiz.findNoticeType();
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 发送消息接变更状态
	 * </pre>
	 * @param portalPermission
	 * @return void
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年5月13日下午4:45:47
	 */
	@RequestMapping("/updateMessageStatus")
	@ResponseBody
	public void updateMessageStatus(PortalMessageStatusVo porMessageStatusVo) {
		if (null == porMessageStatusVo || null == porMessageStatusVo.getReqId()) {
			return;
		}
		porMessageStatusVo.setCreateTime(new Date());
		String[] reqIdArr = porMessageStatusVo.getReqId().split(",");
		List<String> reqIds = new ArrayList<>();
		for (String reqId : reqIdArr) {
			reqIds.add(reqId);
		}
		porMessageStatusVo.setReqIds(reqIds);
		List<String> empCodes = new ArrayList<>();
		empCodes.add(porMessageStatusVo.getEmpCode());
		porMessageStatusVo.setEmpCodes(empCodes);
		portalBiz.updateMessageStatus(porMessageStatusVo);
	}
}
