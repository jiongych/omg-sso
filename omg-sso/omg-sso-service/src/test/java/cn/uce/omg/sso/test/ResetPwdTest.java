/** 
 * @项目名称: FSP
 * @文件名称: ResetPwdTest 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.test;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.uce.omg.sso.biz.IAuthBiz;
import cn.uce.omg.sso.vo.ResetPwdVo;

public class ResetPwdTest {

	@Autowired
	private IAuthBiz authBiz;
	
	@Test
	public void ResetTest(){
		ResetPwdVo resetPwdVo = new ResetPwdVo();
		resetPwdVo.setSystemCode("SSO");
		resetPwdVo.setRestTime(new Date());
		resetPwdVo.setEmpCode("019327");
		resetPwdVo.setNewPassword("ec1b8d01cefb52ed477e0c41834f475c");
		resetPwdVo.setPasswordStrength("STRONG");
		resetPwdVo.setResetPwdKey("210124");
		try {
			authBiz.resetPwd(resetPwdVo);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
