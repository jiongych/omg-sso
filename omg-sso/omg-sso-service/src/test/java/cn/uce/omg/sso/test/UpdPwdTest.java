/** 
 * @项目名称: FSP
 * @文件名称: UpdPwdTest extends BaseJunitTest
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
import cn.uce.omg.sso.vo.UpdPwdVo;

/**
 * UpdPwdTest extends BaseJunitTest 
 * @Description: UpdPwdTest extends BaseJunitTest 
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class UpdPwdTest extends BaseJunitTest{

	@Autowired
	private IAuthBiz authBiz;
	/**
	 * 
	 * @Description: TODO 
	 * @author zhangyadong
	 * @date 2017年4月22日 下午4:52:56
	 */
	
	
	//先登入再修改密码
		@Test
		public void loginTest(){
			LoginVo loginVo = new LoginVo();
			loginVo.setSystemCode("SSO");
			loginVo.setIpAddr("192.168.0.118");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR_OF_DAY, -1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			System.out.println(sdf.format(cal.getTime()));
			loginVo.setLoginTime(new Date());
			//loginVo.setUserName("00012349");
			//loginVo.setPassword("196d3485228c78b11921759f671e6fcd");//wx180596@
			loginVo.setUserName("019327");
			loginVo.setPassword("3edc6feaca76158151316372be36ff1b");
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
	
		/**
		 * 
		 * @Description: TODO 
		 * @author zhangyadong
		 * @date 2017年4月22日 下午4:52:56
		 */
	
	
	//修改密码
	@Test
	public void updPwdTest(){
		UpdPwdVo updPwdVo = new UpdPwdVo();
		updPwdVo.setSystemCode("SSO");
		updPwdVo.setIpAddr("192.168.0.118");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -1);
		updPwdVo.setUpdateTime(new Date());
		//updPwdVo.setTokenId("B/y0MJPrA05umCNd0eU6EInjPx4UMYCItTjp35N2Jiz6iv5V5KT6Ag==");
		updPwdVo.setTokenId("oMKqpC9l9v7nGlHZ7mSyVIOGP564m8H8tWIMDxP4Nhb6iv5V5KT6Ag==");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//updPwdVo.setEmpCode("00006121");
		updPwdVo.setEmpCode("00012349");
		updPwdVo.setNewPassword("setNewPassword");
		updPwdVo.setOldPassword("196d3485228c78b11921759f671e6fcd");
		//updPwdVo.setOldPassword("6827ee2ea4fc8d5a503bccc63cf5f1e1");
		updPwdVo.setPasswordStrength("STRONG");
		try {
			AuthResultVo result = authBiz.updPwd(updPwdVo);
			System.out.println("tokenId:" + result.getTokenId());
			System.out.println("empCode:" + result.getEmpCode());
			System.out.println("用户名:" + result.getUserName());
			System.out.println("过期时间:" + sdf.format(result.getExpireTime()));
			System.out.println("权限列表:" + result.getUserRoleList());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}	
	/**
	 * 
	 * @Description: TODO 
	 * @author zhangyadong
	 * @date 2017年4月22日 下午4:52:56
	 */
		//新旧密码不能相同
		//@Test
		public void updPwd1Test(){
			UpdPwdVo updPwdVo = new UpdPwdVo();
			updPwdVo.setSystemCode("SSO");
			updPwdVo.setIpAddr("192.168.0.118");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR_OF_DAY, -1);
			updPwdVo.setUpdateTime(new Date());
			//updPwdVo.setTokenId("B/y0MJPrA05umCNd0eU6EInjPx4UMYCItTjp35N2Jiz6iv5V5KT6Ag==");
			updPwdVo.setTokenId("oMKqpC9l9v7nGlHZ7mSyVIOGP564m8H8tWIMDxP4Nhb6iv5V5KT6Ag==");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//updPwdVo.setEmpCode("00006121");
			updPwdVo.setEmpCode("00012349");
			updPwdVo.setNewPassword("196d3485228c78b11921759f671e6fcd");
			updPwdVo.setOldPassword("196d3485228c78b11921759f671e6fcd");
			//updPwdVo.setOldPassword("6827ee2ea4fc8d5a503bccc63cf5f1e1");
			updPwdVo.setPasswordStrength("STRONG");
			try {
				AuthResultVo result = authBiz.updPwd(updPwdVo);
				System.out.println("tokenId:" + result.getTokenId());
				System.out.println("empCode:" + result.getEmpCode());
				System.out.println("用户名:" + result.getUserName());
				System.out.println("过期时间:" + sdf.format(result.getExpireTime()));
				System.out.println("权限列表:" + result.getUserRoleList());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}	
		
		/**
		 * 
		 * @Description: TODO 
		 * @author zhangyadong
		 * @date 2017年4月22日 下午4:52:56
		 */
		
				//校验密码强度
				//@Test
				public void updPwd2Test(){
					UpdPwdVo updPwdVo = new UpdPwdVo();
					updPwdVo.setSystemCode("SSO");
					updPwdVo.setIpAddr("192.168.0.118");
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.HOUR_OF_DAY, -1);
					updPwdVo.setUpdateTime(new Date());
					//updPwdVo.setTokenId("B/y0MJPrA05umCNd0eU6EInjPx4UMYCItTjp35N2Jiz6iv5V5KT6Ag==");
					updPwdVo.setTokenId("oMKqpC9l9v7nGlHZ7mSyVIOGP564m8H8tWIMDxP4Nhb6iv5V5KT6Ag==");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					//updPwdVo.setEmpCode("00006121");
					updPwdVo.setEmpCode("00012349");
					updPwdVo.setNewPassword("111111111111111");
					//updPwdVo.setNewPassword("aaaaaaaaaaaaaaa");
					updPwdVo.setOldPassword("196d3485228c78b11921759f671e6fcd");
					
					//updPwdVo.setPasswordStrength("WEAK");
					//updPwdVo.setPasswordStrength("AVERAGE");
					updPwdVo.setPasswordStrength(null);
					try {
						AuthResultVo result = authBiz.updPwd(updPwdVo);
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
