/** 
 * @项目名称: FSP
 * @文件名称: LoginOutTest extends BaseJunitTest
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.uce.omg.sso.biz.IAuthBiz;
import cn.uce.omg.sso.vo.AuthCheckVo;
import cn.uce.omg.sso.vo.AuthResultVo;
import cn.uce.omg.sso.vo.LoginVo;
import cn.uce.omg.sso.vo.LogoutVo;
	
public class LoginOutTest extends BaseJunitTest{

	@Autowired
	private IAuthBiz authBiz;
	
	//先登录,然后再登出.
	@Test
	public void loginOutTest(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//登陆获取tokenId
		LoginVo loginVo = new LoginVo();
		loginVo.setSystemCode("SSO");
		loginVo.setIpAddr("192.168.0.118");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -1);
//		System.out.println(sdf.format(cal.getTime()));
		loginVo.setLoginTime(new Date());
		loginVo.setUserName("00012349");
		loginVo.setPassword("196d3485228c78b11921759f671e6fcd");//wx180596@
		String tokenId = null;
		Date expireTime = null;
		try {
			System.out.println("----------登陆信息--------------");
			AuthResultVo result = authBiz.login(loginVo);
			System.out.println("tokenId:" + result.getTokenId());
			tokenId = result.getTokenId();
			System.out.println("empCode:" + result.getEmpCode());
			System.out.println("用户名:" + result.getUserName());
			System.out.println("过期时间:" + sdf.format(result.getExpireTime()));
			expireTime = result.getExpireTime();
			System.out.println("权限列表:" + result.getUserRoleList());
		} catch (Exception e) {
			System.out.println("登陆异常：" + e.getMessage());
		}
		
		//进行登出
		LogoutVo logoutVo = new LogoutVo();
		logoutVo.setSystemCode("SSO");
		logoutVo.setTokenId(tokenId);
		//	logoutVo.setTokenId("V3j7xPGjHErWU4fU52qqiwjR7GStaFo+CgnZYjfVxUX6iv5V5KT6Ag==");
		logoutVo.setIpAddr("192.168.0.118");
		logoutVo.setEmpCode("00012359");
		logoutVo.setLogoutTime(new Date());
		try {
			AuthResultVo result = authBiz.logout(logoutVo);
			System.out.println("----------登出信息--------------");
			System.out.println("tokenId:" + result.getTokenId());
			System.out.println("empCode:" + result.getEmpCode());
			System.out.println("用户名:" + result.getUserName());
			System.out.println("过期时间:" + result.getExpireTime());
			System.out.println("权限列表:" + result.getUserRoleList());
		} catch (Exception e) {
			System.out.println("登出错误信息" + e.getMessage());
		}
		
		System.out.println("---登出后看是否能够刷新token---");
		AuthCheckVo authCheckVo = new AuthCheckVo();
		authCheckVo.setSystemCode("SSO");
// 		authCheckVo.setTokenId("z/fDLssJ6br1vswysHr6I5y2Y11y6P15CLW+33QlJbr6iv5V5KT6Ag==");
 		authCheckVo.setTokenId(tokenId);
		authCheckVo.setCurrentTime(new Date());
		//和token一致的用户
		authCheckVo.setEmpCode("00012349");
		//和token不一致的用户
		//authCheckVo.setEmpCode("00012349");
		System.out.println("---刷新token---");
		try {
			AuthResultVo result = authBiz.authCheck(authCheckVo);
			System.out.println("tokenId:" + result.getTokenId());
			System.out.println("empCode:" + result.getEmpCode());
			System.out.println("用户名:" + result.getUserName());
			System.out.println("过期时间:" + sdf.format(result.getExpireTime()));
			System.out.println("权限列表:" + result.getUserRoleList());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//多ip登陆,然后登出
	//@Test
	public void loginOutIpsTest(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//------------------不同的IP登陆
		LoginVo loginVo2 = new LoginVo();
		loginVo2.setSystemCode("SSO");
		loginVo2.setIpAddr("192.168.0.119");
//		System.out.println(sdf.format(cal.getTime()));
		loginVo2.setLoginTime(new Date());
		loginVo2.setUserName("00012349");
		loginVo2.setPassword("196d3485228c78b11921759f671e6fcd");//wx180596@
		String tokenId2 = null;
		try {
			System.out.println("---192.168.0.119登陆信息--");
			AuthResultVo result = authBiz.login(loginVo2);
			tokenId2 = result.getTokenId();
			System.out.println("tokenId:" + result.getTokenId());
			System.out.println("empCode:" + result.getEmpCode());
			System.out.println("用户名:" + result.getUserName());
			System.out.println("过期时间:" + sdf.format(result.getExpireTime()));
			System.out.println("权限列表:" + result.getUserRoleList());
		} catch (Exception e) {
			System.out.println("登陆异常" + e.getMessage());
		}
		
		//登陆获取tokenId
		LoginVo loginVo = new LoginVo();
		loginVo.setSystemCode("SSO");
		loginVo.setIpAddr("192.168.0.118");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -1);
//		System.out.println(sdf.format(cal.getTime()));
		loginVo.setLoginTime(new Date());
		loginVo.setUserName("00012349");
		loginVo.setPassword("196d3485228c78b11921759f671e6fcd");//wx180596@
		String tokenId = null;
		Date expireTime = null;
		try {
			System.out.println("---192.168.0.118登陆信息---");
			AuthResultVo result = authBiz.login(loginVo);
			System.out.println("tokenId:" + result.getTokenId());
			tokenId = result.getTokenId();
			System.out.println("empCode:" + result.getEmpCode());
			System.out.println("用户名:" + result.getUserName());
			System.out.println("过期时间:" + sdf.format(result.getExpireTime()));
			expireTime = result.getExpireTime();
			System.out.println("权限列表:" + result.getUserRoleList());
		} catch (Exception e) {
			System.out.println("登陆异常：" + e.getMessage());
		}

		//进行登出
		LogoutVo logoutVo = new LogoutVo();
		logoutVo.setSystemCode("SSO");
		logoutVo.setTokenId(tokenId);
		//	logoutVo.setTokenId("V3j7xPGjHErWU4fU52qqiwjR7GStaFo+CgnZYjfVxUX6iv5V5KT6Ag==");
		logoutVo.setIpAddr("192.168.0.118");
		logoutVo.setEmpCode("00012349");
		logoutVo.setLogoutTime(new Date());
		try {
			System.out.println("--登出信息--");
			AuthResultVo result = authBiz.logout(logoutVo);
			tokenId2 = result.getTokenId();
			System.out.println("tokenId:" + result.getTokenId());
			System.out.println("empCode:" + result.getEmpCode());
			System.out.println("用户名:" + result.getUserName());
			System.out.println("权限列表:" + result.getUserRoleList());			
		} catch (Exception e) {
			System.out.println("登出错误信息" + e.getMessage());
		}
		
		System.out.println("---登出后看是否能够刷新token---");
		AuthCheckVo authCheckVo = new AuthCheckVo();
		authCheckVo.setSystemCode("SSO");
// 		authCheckVo.setTokenId("z/fDLssJ6br1vswysHr6I5y2Y11y6P15CLW+33QlJbr6iv5V5KT6Ag==");
 		authCheckVo.setTokenId(tokenId);
		authCheckVo.setCurrentTime(new Date());
		//和token一致的用户
		authCheckVo.setEmpCode("00012349");
		//和token不一致的用户
		//authCheckVo.setEmpCode("00012349");
		System.out.println("---刷新token---");
		try {
			AuthResultVo result = authBiz.authCheck(authCheckVo);
			System.out.println("tokenId:" + result.getTokenId());
			System.out.println("empCode:" + result.getEmpCode());
			System.out.println("用户名:" + result.getUserName());
			System.out.println("过期时间:" + sdf.format(result.getExpireTime()));
			System.out.println("权限列表:" + result.getUserRoleList());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}	
}
