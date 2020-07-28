package com.uc56.uop.client;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import uce.sso.sdk.core.UceSsoClient;
import uce.sso.sdk.exception.ClientException;
import uce.sso.sdk.util.codec.DigestUtils;
import uce.sso.sdk.vo.AuthCheckVo;
import uce.sso.sdk.vo.ClientVo;
import uce.sso.sdk.vo.ExpCodeVo;
import uce.sso.sdk.vo.LoginVo;
import uce.sso.sdk.vo.LogoutVo;
import uce.sso.sdk.vo.ResetPwdVo;
import uce.sso.sdk.vo.ResultRoleVo;
import uce.sso.sdk.vo.UpdPwdVo;
import uce.sso.sdk.vo.UserInfoVo;
import uce.sso.sdk.vo.UserSearchVo;
import uce.sso.sdk.vo.base.ResponseVo;

/**
 * TestSdk  
 * @Description: TestSdk  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class TestSdk {

	// 发送地址
	//public static final String SEND_URL = "http://localhost:6080/omg-sso-main/gateway/gateway.action";
	//public static final String SEND_URL = "http://10.201.1.224:9021/omg-sso-main/gateway/gateway.action";
	public static final String SEND_URL = "http://sit-sso.uce.cn/omg-eif/gateway/gateway.action";
	// 字符集
	public static final String CHARSET = "UTF-8";
//	public static final String CHARSET = "UTF-8";

	// 签名方式
	public static final String SIGN_TYPE = "md5";

	// 数据格式
	public static final String DATA_TYPE = "xml";
	// 密钥
	public static final String SECURITY_KEY = "438405883469043062f8086f4bbc7589";
	// 系统编码
	public static final String SYSTEM_CODE = "OMG";

	// 调用的服务(映射的方法名)
	private static final String SERVER_NAME = "login";

	/** 连接时间：默认2分钟 */
	private static final int connectTimeout = 120000;
	/** 读取时间：默认2分钟 */
	private static final int readTimeout = 120000;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static Map<String, String> sendParam() throws ClientException {
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("systemCode", SYSTEM_CODE);
			param.put("charset", CHARSET);
			param.put("dataType", DATA_TYPE);
			param.put("serviceName", SERVER_NAME);
//			param.put("data", getData());
//			param.put("dataSign", SecurityUtil.sign(param, SIGN_TYPE, SECURITY_KEY, CHARSET));
			return param;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ClientException("生成发送的数据失败");
		}
	}

	/**
	 * 登录
	 * @throws ClientException
	 * @throws Exception
	 */
	public static void login() throws ClientException, Exception {
		
		/**
		 * <login>
			    <systemCode>OMG_CAS</systemCode>
			    <loginTime>2017-12-15 12:19:22</loginTime>
			    <userName>019327</userName>
			    <password>ec1b8d01cefb52ed477e0c41834f475c</password>
			</login>
		 */
		
		UceSsoClient client = new UceSsoClient(getClientVo("login"));
		LoginVo loginVo = new LoginVo();
		loginVo.setSystemCode("OMG");
		loginVo.setIpAddr("192.168.0.118");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -1);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(sdf.format(cal.getTime()));
		loginVo.setLoginTime(new Date());
		loginVo.setUserName("019706");
		loginVo.setPassword("16f43fe8f87cf7d014d11c1888ae4848");//wx180596@  196d3485228c78b11921759f671e6fcd
		ResponseVo result = client.login(loginVo);
		System.out.println("用户名:");
		/*System.out.println("tokenId:" + result.getTokenId());
		System.out.println("用户名:" + result.getUserName());
		System.out.println("过期时间:" + sdf.format(result.getExpireTime()));
		System.out.println("权限列表:" + result.getUserRoleList());*/
	}
	
	/**
	 * 修改密码
	 * @throws ClientException
	 * @throws Exception
	 */
	public static void updPwd() throws ClientException, Exception {
		UceSsoClient client = new UceSsoClient(getClientVo("upd_pwd"));
		UpdPwdVo updPwdVo = new UpdPwdVo();
		updPwdVo.setSystemCode("SSO");
		updPwdVo.setTokenId("2zHy6zpneyBOL7Hli4kwfpKq+ervG0qtt7iDoRegPiX6iv5V5KT6Ag==");
		updPwdVo.setUpdateTime(new Date());
		updPwdVo.setEmpCode("019518");
		updPwdVo.setNewPassword("0659c7992e268962384eb17fafe88367");
		updPwdVo.setOldPassword("0659c7992e268962384eb17fafe88361");
		updPwdVo.setPasswordStrength("STRONG");
		client.updPwd(updPwdVo);
	}

	/**
	 * 用户锁定登陆测试
	 * @throws ClientException
	 * @throws Exception
	 */
	public static void logins() throws ClientException, Exception {
		UceSsoClient client = new UceSsoClient(getClientVo("login"));
		LoginVo loginVo = new LoginVo();
		loginVo.setSystemCode("SSO");
		loginVo.setIpAddr("192.168.0.118");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -1);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(sdf.format(cal.getTime()));
		for (int i = 0; i < 10; i++) {
			loginVo.setLoginTime(new Date());
			loginVo.setUserName("00012349");
			loginVo.setPassword("1密码从无");//wx180596@
			ResponseVo result = client.login(loginVo);
			System.out.println("登陆测试" + i+1);
			if(result != null){
				/*System.out.println("tokenId:" + result.getTokenId());
				System.out.println("用户名:" + result.getUserName());
				System.out.println("权限列表:" + result.getUserRoleList());*/
			}
		}
	}	
	
	/**
	 * 多ip登录
	 * @throws ClientException
	 * @throws Exception
	 */
	public static void loginIps() throws ClientException, Exception {
		System.out.println("-------192.168.0.118登陆信息------");
		UceSsoClient client = new UceSsoClient(getClientVo("login"));
		LoginVo loginVo = new LoginVo();
		loginVo.setSystemCode("SSO");
		loginVo.setIpAddr("192.168.0.118");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -1);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(sdf.format(cal.getTime()));
		loginVo.setLoginTime(new Date());
		loginVo.setUserName("00012349");
		loginVo.setPassword("196d3485228c78b11921759f671e6fcd");//wx180596@
		ResponseVo result = client.login(loginVo);
		/*System.out.println("tokenId:" + result.getTokenId());
		System.out.println("用户名:" + result.getUserName());
		System.out.println("过期时间:" + sdf.format(result.getExpireTime()));
		System.out.println("权限列表:" + result.getUserRoleList());*/
		
		
		System.out.println("-------192.168.0.119登陆信息------");
		
		LoginVo loginVo2 = new LoginVo();
		loginVo2.setSystemCode("SSO");
		loginVo2.setIpAddr("192.168.0.119");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(sdf.format(cal.getTime()));
		loginVo2.setLoginTime(new Date());
		loginVo2.setUserName("00012349");
		loginVo2.setPassword("196d3485228c78b11921759f671e6fcd");//wx180596@
		ResponseVo result2 = client.login(loginVo2);
		/*System.out.println("tokenId:" + result2.getTokenId());
		System.out.println("用户名:" + result2.getUserName());
		System.out.println("过期时间:" + sdf.format(result2.getExpireTime()));
		System.out.println("权限列表:" + result2.getUserRoleList());*/
		
		System.out.println("-------不传入登陆ip登陆-----");
		LoginVo loginVo3 = new LoginVo();
		loginVo3.setSystemCode("SSO");
	//	loginVo3.setIpAddr("192.168.0.119");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(sdf.format(cal.getTime()));
		loginVo3.setLoginTime(new Date());
		loginVo3.setUserName("00012349");
		loginVo3.setPassword("196d3485228c78b11921759f671e6fcd");//wx180596@
		ResponseVo result3 = client.login(loginVo3);
		/*System.out.println("tokenId:" + result3.getTokenId());
		System.out.println("用户名:" + result3.getUserName());
		System.out.println("过期时间:" + sdf.format(result3.getExpireTime()));
		System.out.println("权限列表:" + result3.getUserRoleList());*/
	}	
	
	//标准登出
	public static void logout() {
		UceSsoClient client = new UceSsoClient(getClientVo("login"));
		/*System.out.println("-------登出先登陆信息------");
		LoginVo loginVo = new LoginVo();
		loginVo.setSystemCode("SSO");
		loginVo.setIpAddr("192.168.0.118");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -1);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(sdf.format(cal.getTime()));
		loginVo.setLoginTime(new Date());
		//无效用户名
	//	loginVo.setUserName("00012349s");
		//正确员工
		loginVo.setUserName("019518");
		loginVo.setPassword("196d3485228c78b11921759f671e6fcd");//wx180596@
		AuthResultVo result;
		String tokenId = null;
		try {
			result = client.login(loginVo);
			if(result != null){
				tokenId = result.getTokenId();
				System.out.println("tokenId:" + result.getTokenId());
				System.out.println("用户名:" + result.getUserName());
				System.out.println("过期时间:" + result != null ? sdf.format(result.getExpireTime()):"");
			}
		} catch (ClientException | IOException e) {
			System.out.println("登陆错误信息" + e.getMessage());
		}*/
		
		UceSsoClient clientOut = new UceSsoClient(getClientVo("logout"));
		System.out.println("-------标准登出信息-----");
		LogoutVo logoutVo = new LogoutVo();
		logoutVo.setSystemCode("SSO");
		logoutVo.setLogoutTime(new Date());
		logoutVo.setIpAddr("192.168.0.118");
		logoutVo.setTokenId("oAiZAazdiSjbryrhC+1oxg3Io1tVv3q1TjqE7RGXwFL6iv5V5KT6Ag==");
		logoutVo.setEmpCode("019518");
		//无效用户名
		ResponseVo resultOut;
		try {
			resultOut = clientOut.logout(logoutVo);
			if(resultOut != null){
				/*System.out.println("tokenId:" + resultOut.getTokenId());
				System.out.println("用户名:" + resultOut.getUserName());
				System.out.println("过期时间:" + resultOut.getExpireTime());
				System.out.println("权限列表:" + resultOut.getUserRoleList());*/
			}
		} catch (Exception e) {
			System.out.println("登出错误信息" + e.getMessage());
		}
	}

	//登出后检查token
	public static void logoutCheckAuth() {
		UceSsoClient client = new UceSsoClient(getClientVo("login"));
		System.out.println("-------登出先登陆信息------");
		LoginVo loginVo = new LoginVo();
		loginVo.setSystemCode("SSO");
		loginVo.setIpAddr("192.168.0.118");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -1);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(sdf.format(cal.getTime()));
		loginVo.setLoginTime(new Date());
		loginVo.setUserName("00012349");
		loginVo.setPassword("196d3485228c78b11921759f671e6fcd");//wx180596@
		ResponseVo result;
		String tokenId = null;
		try {
			result = client.login(loginVo);
			if(result != null){
				/*tokenId = result.getTokenId();
				System.out.println("tokenId:" + result.getTokenId());
				System.out.println("用户名:" + result.getUserName());
				System.out.println("过期时间:" + result != null ? sdf.format(result.getExpireTime()):"");*/
			}
		} catch (Exception e) {
			System.out.println("登陆错误信息" + e.getMessage());
		}
		
		UceSsoClient clientOut = new UceSsoClient(getClientVo("logout"));
		System.out.println("-------标准登出-----");
		LogoutVo logoutVo = new LogoutVo();
		logoutVo.setSystemCode("SSO");
		logoutVo.setLogoutTime(new Date());
		logoutVo.setIpAddr("192.168.0.118");
		logoutVo.setTokenId(tokenId);
		logoutVo.setEmpCode("00012349");
		ResponseVo resultOut;
		try {
			resultOut = clientOut.logout(logoutVo);
			if(resultOut != null){
				/*System.out.println("tokenId:" + resultOut.getTokenId());
				System.out.println("用户名:" + resultOut.getUserName());
				System.out.println("过期时间:" + resultOut.getExpireTime());
				System.out.println("权限列表:" + resultOut.getUserRoleList());*/
			}
		} catch (Exception e) {
			System.out.println("登出错误信息" + e.getMessage());
		}
		
		System.out.println("-------登出后检查token有效性-----");
		UceSsoClient clientCheck = new UceSsoClient(getClientVo("auth_check"));
		AuthCheckVo authCheckVo = new AuthCheckVo();
		authCheckVo.setSystemCode("SSO");
 		authCheckVo.setTokenId(tokenId);
		authCheckVo.setCurrentTime(new Date());
		authCheckVo.setEmpCode("00012349");
		ResponseVo resultCheck = null;
		try {
			resultCheck = clientCheck.authCheck(authCheckVo);
			if(resultCheck != null){
				/*System.out.println("tokenId:" + resultCheck.getTokenId());
				System.out.println("用户名:" + resultCheck.getUserName());
				System.out.println("过期时间:" + resultCheck.getExpireTime());
				System.out.println("权限列表:" + resultCheck.getUserRoleList());*/
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}	
	
	/**
	 * 权限检查
	 * @throws ClientException
	 * @throws Exception
	 */
	public static void authCheck(){
		UceSsoClient client = new UceSsoClient(getClientVo("login"));
		System.out.println("-------登陆信息------");
		LoginVo loginVo = new LoginVo();
		loginVo.setSystemCode("SSO");
		loginVo.setIpAddr("192.168.0.118");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -1);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(sdf.format(cal.getTime()));
		loginVo.setLoginTime(new Date());
		loginVo.setUserName("00012349");
		loginVo.setPassword("196d3485228c78b11921759f671e6fcd");//wx180596@
		ResponseVo result;
		String tokenId = null;
		try {
			result = client.login(loginVo);
			if(result != null){
				/*tokenId = result.getTokenId();
				System.out.println("tokenId:" + result.getTokenId());
				System.out.println("用户名:" + result.getUserName());
				System.out.println("过期时间:" + result != null ? sdf.format(result.getExpireTime()):"");*/
			}
		} catch (Exception e) {
			System.out.println("登陆错误信息" + e.getMessage());
		}
		
		System.out.println("-----------------校验token");
		UceSsoClient clientCheck = new UceSsoClient(getClientVo("auth_check"));
		AuthCheckVo authCheckVo = new AuthCheckVo();
		authCheckVo.setSystemCode("SSO");
 		authCheckVo.setTokenId(tokenId);
		authCheckVo.setCurrentTime(new Date(new Date().getTime()+1000*1888*10) );
		//其它员工
		authCheckVo.setEmpCode("00012349");
		//正确的员工
//		authCheckVo.setEmpCode("00012349");
		ResponseVo resultCheck = null;
		try {
			resultCheck = clientCheck.authCheck(authCheckVo);
			if(resultCheck != null){
				/*System.out.println("tokenId:" + resultCheck.getTokenId());
				System.out.println("用户名:" + resultCheck.getUserName());
				System.out.println("过期时间:" + resultCheck.getExpireTime());
				System.out.println("权限列表:" + resultCheck.getUserRoleList());*/
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	

	/**
	 * 找回密码
	 * @throws ClientException
	 * @throws Exception
	 */
	public static void resetPwd() throws ClientException, Exception {
	/*	UceSsoClient clientCheck = new UceSsoClient(getClientVo("check_code"));
		ExpCodeVo expCodeVoCheck = new ExpCodeVo();
		expCodeVoCheck.setSystemCode("SSO");
		expCodeVoCheck.setSendTime(new Date());
		expCodeVoCheck.setEmpCode("019329");
		expCodeVoCheck.setCode("993161");
		expCodeVoCheck.setCodeType("RTEPWD");*/
		//String key = clientCheck.checkCode(expCodeVoCheck);
		
		//System.out.println("----------check key----" + key);
		
		UceSsoClient resultReset = new UceSsoClient(getClientVo("reset_pwd"));
		ResetPwdVo resetPwdVo = new ResetPwdVo();
		resetPwdVo.setSystemCode("SSO");
		resetPwdVo.setRestTime(new Date());
		resetPwdVo.setEmpCode("019518");
		resetPwdVo.setNewPassword("65e3a0e5f86c6510ed28c772e0676ab4");
		resetPwdVo.setPasswordStrength("STRONG");
		resetPwdVo.setResetPwdKey("11");
		ResponseVo resultCheck = resultReset.resetPwd(resetPwdVo);
		//System.out.println(resultCheck.getEmpCode());
		/*try {
			resultCheck = clientCheck.resetPwd(resetPwdVo);
			if(resultCheck != null){
				System.out.println("tokenId:" + resultCheck.getTokenId());
				System.out.println("用户名:" + resultCheck.getUserName());
				System.out.println("过期时间:" + resultCheck.getExpireTime());
				System.out.println("权限列表:" + resultCheck.getUserRoleList());
			}
		} catch (ClientException | IOException e) {
			System.out.println(e.getMessage());
		}*/
	}

	/**
	 * 获取验证码
	 * @throws ClientException
	 * @throws Exception
	 * 
	 */
	public static void getCode() throws ClientException, Exception {
		UceSsoClient client = new UceSsoClient(getClientVo("get_code"));
		ExpCodeVo expCodeVo = new ExpCodeVo();
		expCodeVo.setSystemCode("SSO");
		expCodeVo.setSendTime(new Date());
		expCodeVo.setCodeType("RTEPWD");
		expCodeVo.setMobile("13168046648");
		client.getCode(expCodeVo);
	}

	/**
	 * 获取验证码
	 * @throws ClientException
	 * @throws Exception
	 */
	public static void checkCode() throws ClientException, Exception {
		UceSsoClient client = new UceSsoClient(getClientVo("check_code"));
		ExpCodeVo expCodeVo = new ExpCodeVo();
		expCodeVo.setSystemCode("SSO");
		expCodeVo.setSendTime(new Date());
		expCodeVo.setEmpCode("019327");
		expCodeVo.setCode("225751");
		expCodeVo.setCodeType("RTEPWD");
		client.checkCode(expCodeVo);
	}

	/**
	 * 根据用户查询用户角色
	 * @param empId
	 * @return
	 * @throws Exception
	 */
	public static void findUserRole(){
		UceSsoClient client = new UceSsoClient(getClientVo("find_user_role"));
		UserSearchVo userSearch = new UserSearchVo();
		userSearch.setUserName("000123xxx");
		//userSearch.setUserName("00012349");
		ResponseVo authResultVo;
		System.out.println("-----------------查询角色开始");
		try {
			authResultVo = client.findUserRole(userSearch);
			/*System.out.println(authResultVo.getTokenId());
			System.out.println(authResultVo.getUserRoleList());*/
		} catch (Exception e) {
			System.out.println("查询角色错误" + e.getMessage());
		}
	}
	
	/**
	 * 查询用户相关信息
	 * @param empId
	 * @return
	 * @throws Exception
	 */
	public static UserInfoVo findUser() throws Exception {
		UceSsoClient client = new UceSsoClient(getClientVo("find_user"));
		UserSearchVo userSearch = new UserSearchVo();
		userSearch.setUserName("019327");
		return client.findUser(userSearch);
	}
	
	/**
	 * 查询角色
	 * @return
	 * @throws Exception
	 */
	public static ResultRoleVo findRole() throws Exception {
		UceSsoClient client = new UceSsoClient(getClientVo("find_role"));
		UserSearchVo userSearch = new UserSearchVo();
		userSearch.setRoleName("网点管理员");
		return client.findRole(userSearch);
	}
	
	/**
	 * 查询角色关系
	 * @return
	 * @throws Exception
	 */
	public static ResultRoleVo findRoleRel() throws Exception {
		UceSsoClient client = new UceSsoClient(getClientVo("find_role_rel"));
		UserSearchVo userSearch = new UserSearchVo();
		userSearch.setEmpId(102361);
//		userSearch.setRoleCode("102361");
		return client.findRoleRel(userSearch);
	}

	private static ClientVo getClientVo(String serverName) {
		ClientVo clientVo = new ClientVo();
		clientVo.setSendUrl(SEND_URL);
		clientVo.setSystemCode(SYSTEM_CODE);
		clientVo.setCharset(CHARSET);
		clientVo.setDataType(DATA_TYPE);
		clientVo.setServiceName(serverName);
		clientVo.setSignType(SIGN_TYPE);
		clientVo.setSecurityKey(SECURITY_KEY);
		clientVo.setConnectTimeout(connectTimeout);
		clientVo.setReadTimeout(readTimeout);
		return clientVo;
	}
	
	public static void main(String[] args) throws ClientException {
		try {
			
			//SignUtils.md5(, CHARSET);
			//System.out.println(DigestUtils.md5Hex("uc1212.."));
			// 登录
     		TestSdk.login();
//     		TestSdk.logins();
 //    		loginIps();
//			TestSdk.logout();
//			TestSdk.logoutCheckAuth();
//			// 验证用户
//			TestSdk.authCheck();
			// 修改密码
//			TestSdk.updPwd();
			// 找回密码
//			TestSdk.resetPwd();
//			// 获取验证码
//			TestSdk.getCode();
//			// 验证验证码
//			TestSdk.checkCode();
//			// 查询用户角色
//			TestSdk.findUserRole();
//			// 查询用户
//			TestSdk.findUser();
//			// 查询角色
//			TestSdk.findRole();
			// 查询角色关系
//			TestSdk.findRoleRel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
