/** 
 * @项目名称: FSP
 * @文件名称: AuthCheckTest extends BaseJunitTest
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
	
/**
 * AuthCheckTest extends BaseJunitTest 
 * @Description: AuthCheckTest extends BaseJunitTest 
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
/**
 * AuthCheckTest extends BaseJunitTest 
 * @Description: AuthCheckTest extends BaseJunitTest 
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class AuthCheckTest extends BaseJunitTest{

	@Autowired
	private IAuthBiz authBiz;
	
	//先登录获取tokenid然后,通过tokenid获取过期时间.
//	@Test
	public void authTest(){
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
		
		
		AuthCheckVo authCheckVo = new AuthCheckVo();
		authCheckVo.setSystemCode("SSO");
 		authCheckVo.setTokenId(tokenId);
 //		authCheckVo.setTokenId("rCOMUJpTE3FXx8nmYpH7GMuXvFMJ4T8+Vh3bj51tfCH6iv5V5KT6Ag==");
		authCheckVo.setCurrentTime(new Date(expireTime.getTime() - 1000*60));
		authCheckVo.setEmpCode("00012349");
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
	
	//标准刷新token,传入不同条件的员工,tokenid,返回错误编码
	@Test
	public void authBasicTest(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		AuthCheckVo authCheckVo = new AuthCheckVo();
		authCheckVo.setSystemCode("SSO");
// 		authCheckVo.setTokenId("z/fDLssJ6br1vswysHr6I5y2Y11y6P15CLW+33QlJbr6iv5V5KT6Ag==");
 		authCheckVo.setTokenId("8j585o3IGJct0WLTDpHnq/oQxjKA+fQ1tcaPUkj5QUD6iv5V5KT6Ag==");
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
	
	//通过当前时间能够延长过期时间.
	//@Test
	public void authExpireTest(){
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
		
		
		AuthCheckVo authCheckVo = new AuthCheckVo();
		authCheckVo.setSystemCode("SSO");
 		authCheckVo.setTokenId(tokenId);
 //		authCheckVo.setTokenId("rCOMUJpTE3FXx8nmYpH7GMuXvFMJ4T8+Vh3bj51tfCH6iv5V5KT6Ag==");
		authCheckVo.setCurrentTime(new Date(expireTime.getTime() + 450*60*1000));
		authCheckVo.setEmpCode("00012349");
		System.out.println("---刷新token测试开始---");
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
	
	//token已存在token，传入错误token,传入错误员工，错误的系统信息
	//@Test
	public void authWrogTest(){
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
		
		
		AuthCheckVo authCheckVo = new AuthCheckVo();
		authCheckVo.setSystemCode("SSOssss");
//		authCheckVo.setSystemCode("SSO");
// 		authCheckVo.setTokenId(tokenId);
		authCheckVo.setTokenId("rCOMUJpTE3FXx8nmYpH7GMuXvFMJ4T8+Vh3bj51tfCH6iv5V5KT6Ag==");
		authCheckVo.setCurrentTime(new Date(expireTime.getTime() - 1000*60*10));
		authCheckVo.setEmpCode("00012349");
		//authCheckVo.setEmpCode("019327xx");
//		authCheckVo.setEmpCode("019327cvdf");
		System.out.println("---刷新token测试开始---");
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
