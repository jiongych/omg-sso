/** 
 * @项目名称: FSP
 * @文件名称: AuthService 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.main.sso.service;

import cn.uce.omg.sso.biz.IAuthBiz;
import cn.uce.omg.sso.vo.AuthCheckVo;
import cn.uce.omg.sso.vo.AuthResultVo;
import cn.uce.omg.sso.vo.ExpCodeVo;
import cn.uce.omg.sso.vo.LogoutVo;
import cn.uce.omg.sso.vo.LoginVo;
import cn.uce.omg.sso.vo.ResetPwdVo;
import cn.uce.omg.sso.vo.UpdPwdVo;

/**
 * 登录相关服务
 * @author huangting
 * @date 2017年6月9日 下午12:12:53
 */
public class AuthService {

	private IAuthBiz authBiz;

	/**
	 * 登录模块，提供RMI，HTTP方式进行校验
	 * @param loginVo
	 * @return
	 * @throws Exception
	 */
	public AuthResultVo login(LoginVo loginVo) throws Exception {
		return authBiz.login(loginVo);
	}
	
	/**
	 * 退出
	 * @param logoutVo
	 * @throws Exception
	 */
	public AuthResultVo logout(LogoutVo logoutVo) throws Exception {
		return authBiz.logout(logoutVo);
	}
	
	/**
	 * 修改密码，提供RMI，HTTP方式进行校验
	 * @param loginVo
	 * @return
	 * @throws Exception
	 */
	public AuthResultVo updPwd(UpdPwdVo updPwdVo) throws Exception {
		return authBiz.updPwd(updPwdVo);
	}
	
	/**
	 * 找回密码，提供RMI，HTTP方式进行校验
	 * @param loginVo
	 * @return
	 * @throws Exception
	 */
	public AuthResultVo resetPwd(ResetPwdVo resetPwdVo) throws Exception {
		return authBiz.resetPwd(resetPwdVo);
	}
	
	/**
	 * 获取验证码，提供RMI，HTTP方式进行校验
	 * @param expCodeVo
	 * @return
	 * @throws Exception
	 */
	public void getCode(ExpCodeVo expCodeVo) throws Exception{
		authBiz.getCode(expCodeVo);
	}
	
	/**
	 * 校验验证码，提供RMI，HTTP方式进行校验
	 * @param expCodeVo
	 * @return
	 * @throws Exception
	 */
	public void checkCode(ExpCodeVo expCodeVo) throws Exception{
		authBiz.checkCode(expCodeVo);
	}
	

	/**
	 * 权限检查（系统切换接口）
	 * @param authCheckVo
	 * @return
	 * @throws Exception
	 */
	public AuthResultVo authCheck(AuthCheckVo authCheckVo) throws Exception {
		return authBiz.authCheck(authCheckVo);
	}
	
	/**
	 * 方法的描述：
	 * <pre>
	 * 确认登录码
	 * </pre>
	 * @param authCheck
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author LH
	 * @email luhenguce@uce.cn
	 * @date 2019年7月3日下午6:02:47
	 */
/*	public boolean confirmLoginCode(AuthCheckVo authCheck) throws Exception{
		return authBiz.confirmLoginCode(authCheck);
	}*/

	public void setAuthBiz(IAuthBiz authBiz) {
		this.authBiz = authBiz;
	}
	


}
