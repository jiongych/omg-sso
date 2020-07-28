/** 
 * @项目名称: FSP
 * @文件名称: IAuthApi 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.api;

import cn.uce.omg.sso.vo.AuthCheckVo;
import cn.uce.omg.sso.vo.AuthResultVo;
import cn.uce.omg.sso.vo.ExpCodeVo;
import cn.uce.omg.sso.vo.LoginVo;
import cn.uce.omg.sso.vo.LogoutVo;
import cn.uce.omg.sso.vo.UpdPwdVo;

/**
 * IAuthApi  
 * @Description: IAuthApi  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public interface IAuthApi {

	/**
	 * 登录模块，提供RMI，HTTP方式进行校验
	 * @param loginVo
	 * @return
	 * @throws Exception
	 */
	public AuthResultVo login(LoginVo loginVo) throws Exception;
	
	/**
	 * 退出
	 * @param logoutVo
	 * @throws Exception
	 */
	public AuthResultVo logout(LogoutVo logoutVo) throws Exception;
	
	/**
	 * 修改密码，提供RMI，HTTP方式进行校验
	 * @param updPwdVo
	 * @return
	 * @throws Exception
	 */
	public AuthResultVo updPwd(UpdPwdVo updPwdVo) throws Exception;
	
	/**
	 * 获取验证码，提供RMI，HTTP方式进行校验
	 * @param expCodeVo
	 * @return
	 * @throws Exception
	 */
	public void getCode(ExpCodeVo expCodeVo) throws Exception;
	
	/**
	 * 权限检查（系统切换接口）
	 * @param authCheckVo
	 * @return
	 * @throws Exception
	 */
	public AuthResultVo authCheck(AuthCheckVo authCheckVo) throws Exception;
	
	/**
	 * 解锁用户登陆锁定状态
	 * @param empCode
	 * @return
	 * @throws Exception
	 */
	public Boolean unLock(String empCode) throws Exception;
	
	/**
	 * 确认登录码
	 * 
	 * @param authCheck
	 * @return
	 * @throws Exception
	 * @author majun
	 * @date 2019年6月24日 上午11:28:25
	 */
	public boolean confirmLoginCode(AuthCheckVo authCheck) throws Exception;
}
