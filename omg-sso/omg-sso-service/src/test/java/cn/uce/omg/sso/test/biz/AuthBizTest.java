/** 
 * @项目名称: FSP
 * @文件名称: AuthBizTest extends BaseJunitTest
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.test.biz;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.uce.omg.sso.biz.IAuthBiz;
import cn.uce.omg.sso.constant.AuthConstants;
import cn.uce.omg.sso.test.BaseJunitTest;
import cn.uce.omg.sso.vo.AuthCheckVo;
import cn.uce.omg.sso.vo.AuthResultVo;
import cn.uce.omg.sso.vo.EmpInfoVo;
import cn.uce.omg.sso.vo.ExpCodeVo;
import cn.uce.omg.sso.vo.LoginVo;
import cn.uce.omg.sso.vo.LogoutVo;
import cn.uce.omg.sso.vo.ResetPwdVo;
import cn.uce.omg.sso.vo.UpdPwdVo;
import cn.uce.omg.vo.SystemInfoVo;

public class AuthBizTest extends BaseJunitTest{

	@Autowired
	private IAuthBiz authBiz;
	
	@Test
	public void loginTest(){
		LoginVo loginVo = new LoginVo();
		loginVo.setSystemCode("SSO");
		loginVo.setIpAddr("192.168.0.118");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -1);
		loginVo.setLoginTime(new Date());
		loginVo.setUserName("00006230");
		loginVo.setPassword("ec1b8d01cefb52ed477e0c41834f475c");
		try {
			AuthResultVo result = authBiz.login(loginVo);
			if(result != null) {
				System.out.println("登录用户:" + result.getEmpCode());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//进行登出
	@Test
	public void logoutTest(){
		LogoutVo logoutVo = new LogoutVo();
		logoutVo.setSystemCode("SSO");
		logoutVo.setTokenId("V3j7xPGjHErWU4fU52qqiwjR7GStaFo+CgnZYjfVxUX6iv5V5KT6Ag==");
		logoutVo.setIpAddr("192.168.0.118");
		logoutVo.setEmpCode("00006230");
		logoutVo.setLogoutTime(new Date());
		try {
			AuthResultVo result = authBiz.logout(logoutVo);
			if(result != null) {
				System.out.println("登出用户:" + result.getUserName());
			}
		} catch (Exception e) {
			System.out.println("登出错误信息" + e.getMessage());
		}
	}
	
	//修改密码
	@Test
	public void updPwdTest(){
		UpdPwdVo updPwdVo = new UpdPwdVo();
		updPwdVo.setSystemCode("SSO");
		updPwdVo.setIpAddr("192.168.0.118");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -1);
		updPwdVo.setUpdateTime(new Date());
		updPwdVo.setTokenId("oMKqpC9l9v7nGlHZ7mSyVIOGP564m8H8tWIMDxP4Nhb6iv5V5KT6Ag==");
		updPwdVo.setEmpCode("00012349");
		updPwdVo.setNewPassword("setNewPassword");
		updPwdVo.setOldPassword("196d3485228c78b11921759f671e6fcd");
		updPwdVo.setPasswordStrength("STRONG");
		try {
			AuthResultVo result = authBiz.updPwd(updPwdVo);
			if(result != null) {
				System.out.println("修改密码用户:" + result.getUserName());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}	

	@Test
	public void resetPwdTest(){
		ResetPwdVo resetPwdVo = new ResetPwdVo();
		resetPwdVo.setSystemCode("SSO");
		resetPwdVo.setRestTime(new Date());
		resetPwdVo.setEmpCode("019327");
		resetPwdVo.setNewPassword("ec1b8d01cefb52ed477e0c41834f475c");
		resetPwdVo.setPasswordStrength("STRONG");
		resetPwdVo.setResetPwdKey("210124");
		try {
			AuthResultVo result = authBiz.resetPwd(resetPwdVo);
			if(result != null) {
				System.out.println("重置密码用户:" + result.getUserName());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void getCodeTest() {
		ExpCodeVo expCodeVo = new ExpCodeVo();
		expCodeVo.setEmpCode("13168046648");
		expCodeVo.setSendTime(new Date());
		expCodeVo.setCodeType(AuthConstants.CODE_TYPE_RTEPWD);
		try {
			authBiz.getCode(expCodeVo);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void checkCodeTest() {
		ExpCodeVo expCodeVo = new ExpCodeVo();
		expCodeVo.setEmpCode("13168046648");
		expCodeVo.setSendTime(new Date());
		expCodeVo.setCodeType(AuthConstants.CODE_TYPE_RTEPWD);
		try {
			String result = authBiz.checkCode(expCodeVo);
			if (result != null) {
				System.out.println("校验验证码:" + result);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void authCheckTest() {
		AuthCheckVo authCheckVo = new AuthCheckVo();
		authCheckVo.setSystemCode("SSO");
 		authCheckVo.setTokenId("oMKqpC9l9v7nGlHZ7mSyVIOGP564m8H8tWIMDxP4Nhb6iv5V5KT6Ag");
		authCheckVo.setCurrentTime(new Date(new Date().getTime()+1000*1888*10) );
		authCheckVo.setEmpCode("0006230");
		AuthResultVo resultCheck = null;
		try {
			resultCheck = authBiz.authCheck(authCheckVo);
			if(resultCheck != null){
				System.out.println("tokenId:" + resultCheck.getTokenId());
				System.out.println("用户名:" + resultCheck.getUserName());
				System.out.println("过期时间:" + resultCheck.getExpireTime());
				System.out.println("权限列表:" + resultCheck.getUserRoleList());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void decryptTokenIdBySystemCodeTest() {
		try {
			String result = authBiz.decryptTokenIdBySystemCode("oMKqpC9l9v7nGlHZ7mSyVIOGP564m8H8tWIMDxP4Nhb6iv5V5KT6Ag", "SSO");
			if (result != null) {
				System.out.println("解密token:" + result);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void findSystemByConditionTest() {
		SystemInfoVo searchSystemInfo = new SystemInfoVo();
		searchSystemInfo.setSystemName("SSO");
		try {
			List<SystemInfoVo> list = authBiz.findSystemByCondition(searchSystemInfo);
			if (list != null && list.size() > 0 ) {
				System.out.println("获取所有系统信息:" + list.size());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	@Test
	public void findSystemBySystemCodeTest() {
		try {
			SystemInfoVo searchSystemInfo = authBiz.findSystemBySystemCode("SSO");
			if (searchSystemInfo != null) {
				System.out.println("根据systemCode获取系统信息:" + searchSystemInfo.getSystemName());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	@Test
	public void findEmpInfoByUserNameTest() {
		try {
			EmpInfoVo empInfoVo = authBiz.findEmpInfoByUserName("00006230");
			if (empInfoVo != null &&  empInfoVo.getEmpVo() != null) {
				System.out.println("根据用户名查询用户信息:" + empInfoVo.getEmpVo().getEmpName());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void authCheckUserTest() {
		try {
			EmpInfoVo empInfoVo = authBiz.authCheckUser("00006230", AuthConstants.LOGIN_TYPE_EMP_CODE);
			if (empInfoVo != null &&  empInfoVo.getEmpVo() != null) {
				System.out.println("查询用户信息:" + empInfoVo.getEmpVo().getEmpName());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
