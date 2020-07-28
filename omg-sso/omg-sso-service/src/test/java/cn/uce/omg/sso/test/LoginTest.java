/** 
 * @项目名称: FSP
 * @文件名称: LoginTest extends BaseJunitTest
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
import cn.uce.omg.sso.vo.AuthResultVo;
import cn.uce.omg.sso.vo.LoginVo;
	
public class LoginTest extends BaseJunitTest{

	@Autowired
	private IAuthBiz authBiz;
	
	//正常登陆
	@Test
	public void loginTest(){
		LoginVo loginVo = new LoginVo();
		loginVo.setSystemCode("SSO");
		loginVo.setIpAddr("192.168.0.118");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(sdf.format(cal.getTime()));
		loginVo.setLoginTime(new Date());
		loginVo.setUserName("019327");
		loginVo.setPassword("3edc6feaca76158151316372be36ff1b");//wx180596@
		try {
			AuthResultVo result = authBiz.login(loginVo);
			System.out.println("tokenId:" + result.getTokenId());
			System.out.println("empCode:" + result.getEmpCode());
			System.out.println("鐢ㄦ埛鍚�" + result.getUserName());
			System.out.println("杩囨湡鏃堕棿:" + sdf.format(result.getExpireTime()));
			System.out.println("鏉冮檺鍒楄〃:" + result.getUserRoleList());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//不同ip登陆
	@Test
	public void loginIpsTest(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		LoginVo loginVo = new LoginVo();
		loginVo.setSystemCode("SSO");
		loginVo.setIpAddr("192.168.0.118");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -1);
//		System.out.println(sdf.format(cal.getTime()));
		loginVo.setLoginTime(new Date());
		loginVo.setUserName("019327");
		loginVo.setPassword("3edc6feaca76158151316372be36ff1b");//wx180596@
		try {
			System.out.println("192.168.0.118");
			AuthResultVo result = authBiz.login(loginVo);
			System.out.println("tokenId:" + result.getTokenId());
			System.out.println("empCode:" + result.getEmpCode());
			System.out.println("用户名:" + result.getUserName());
			System.out.println("过期时间:" + sdf.format(result.getExpireTime()));
			System.out.println("权限列表:" + result.getUserRoleList());
		} catch (Exception e) {
			System.out.println("192.168.0.118" + e.getMessage());
		}
		
		LoginVo loginVo2 = new LoginVo();
		loginVo2.setSystemCode("SSO");
		loginVo2.setIpAddr("192.168.0.119");
//		System.out.println(sdf.format(cal.getTime()));
		loginVo2.setLoginTime(new Date());
		loginVo2.setUserName("019327");
		loginVo2.setPassword("3edc6feaca76158151316372be36ff1b");//wx180596@
		try {
			System.out.println("192.168.0.119");
			AuthResultVo result = authBiz.login(loginVo2);
			System.out.println("tokenId:" + result.getTokenId());
			System.out.println("empCode:" + result.getEmpCode());
			System.out.println("用户名:" + result.getUserName());
			System.out.println("过期时间:" + sdf.format(result.getExpireTime()));
			System.out.println("权限列表:" + result.getUserRoleList());
		} catch (Exception e) {
			System.out.println("192.168.0.119" + e.getMessage());
		}
		
		LoginVo loginVo3 = new LoginVo();
		loginVo3.setSystemCode("SSO");
		loginVo3.setIpAddr("192.168.0.110");
//		System.out.println(sdf.format(cal.getTime()));
		loginVo3.setLoginTime(new Date());
		loginVo3.setUserName("019327");
		loginVo3.setPassword("3edc6feaca76158151316372be36ff1b");//wx180596@
		try {
			System.out.println("192.168.0.110");
			AuthResultVo result = authBiz.login(loginVo3);
			System.out.println("tokenId:" + result.getTokenId());
			System.out.println("empCode:" + result.getEmpCode());
			System.out.println("用户名:" + result.getUserName());
			System.out.println("过期时间:" + sdf.format(result.getExpireTime()));
			System.out.println("权限列表:" + result.getUserRoleList());
		} catch (Exception e) {
			System.out.println("192.168.0.110" + e.getMessage());
		}		
	}
	
	//多次错误登陆
	//@Test
	public void loginsTest(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		LoginVo loginVo = new LoginVo();
		loginVo.setSystemCode("SSO");
		loginVo.setIpAddr("192.168.0.118");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -1);
//		System.out.println(sdf.format(cal.getTime()));
		loginVo.setLoginTime(new Date());
		loginVo.setUserName("019327");
		loginVo.setPassword("------------错误密码");//wx180596@
		for (int i = 0; i < 100; i++) {
			try {
				System.out.println("登陆次数" + i+1);
				AuthResultVo result = authBiz.login(loginVo);
				System.out.println("tokenId:" + result.getTokenId());
				System.out.println("empCode:" + result.getEmpCode());
				System.out.println("用户名:" + result.getUserName());
				System.out.println("过期时间:" + sdf.format(result.getExpireTime()));
				System.out.println("权限列表:" + result.getUserRoleList());
			} catch (Exception e) {
				System.out.println(i+1 + "异常信息:" + e.getMessage());
			}
		}
	}
	
	//员工验证登陆
	//@Test
	public void loginOtherEmpTest(){
		LoginVo loginVo = new LoginVo();
		loginVo.setSystemCode("SSO");
		loginVo.setIpAddr("192.168.0.118");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(sdf.format(cal.getTime()));
		loginVo.setLoginTime(new Date());
		//无此用户
	//	loginVo.setUserName("03327");
		//测试，员工停用，离职，删除等状态，返回相应的值。
	    loginVo.setUserName("00012349");
		loginVo.setPassword("196d3485228c78b11921759f671e6fcd");//wx180596@
		try {
			AuthResultVo result = authBiz.login(loginVo);
			System.out.println("tokenId:" + result.getTokenId());
			System.out.println("empCode:" + result.getEmpCode());
			System.out.println("用户名:" + result.getUserName());
			System.out.println("过期时间:" + sdf.format(result.getExpireTime()));
			System.out.println("权限列表:" + result.getUserRoleList());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}	
	
	//系统性问题验证登陆
	@Test
	public void loginSystemEmpTest(){
		LoginVo loginVo = new LoginVo();
		//不正确的系统编码，系统停用，启动需要携带机器码，则必须要携带机器码
		//loginVo.setSystemCode("SSO");
		loginVo.setSystemCode("SSO");
		loginVo.setIpAddr("192.168.0.118");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(sdf.format(cal.getTime()));
		loginVo.setLoginTime(new Date());
		//无此用户
	//	loginVo.setUserName("03327");
		//测试，员工停用，离职，删除等状态，返回相应的值。
	    loginVo.setUserName("00012349");
		loginVo.setPassword("196d3485228c78b11921759f671e6fcd");//wx180596@
		try {
			AuthResultVo result = authBiz.login(loginVo);
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
