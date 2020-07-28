/** 
 * @项目名称: FSP
 * @文件名称: IAuthBiz 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.biz;

import java.util.List;

import cn.uce.omg.sso.vo.AuthCheckVo;
import cn.uce.omg.sso.vo.AuthResultVo;
import cn.uce.omg.sso.vo.EmpInfoVo;
import cn.uce.omg.sso.vo.ExpCodeVo;
import cn.uce.omg.sso.vo.LoginVo;
import cn.uce.omg.sso.vo.LogoutVo;
import cn.uce.omg.sso.vo.ResetPwdVo;
import cn.uce.omg.sso.vo.UpdPwdVo;
import cn.uce.omg.sso.vo.UserPasswordVo;
import cn.uce.omg.vo.SystemInfoVo;

/**
 * 认证接口
 * @author huangting
 * @date 2017年6月9日 下午12:14:27
 */
public interface IAuthBiz {

	/**
	 * 登录模块，提供RMI，HTTP方式进行校验
	 * @param loginVo
	 * @return
	 * @throws Exception
	 */
	AuthResultVo login(LoginVo loginVo) throws Exception;
	
	/**
	 * @Description: 壹米滴答免密登录
	 * @param loginVo
	 * @return
	 * @throws Exception
	 * @author huangting
	 * @date 2019年9月9日 下午5:42:01
	 */
	AuthResultVo secretFreeLogin(LoginVo loginVo) throws Exception;
	
	/**
	 * 退出登录
	 * @param logoutVo
	 * @throws Exception
	 */
	AuthResultVo logout(LogoutVo logoutVo) throws Exception;

	/**
	 * 修改密码，提供RMI，HTTP方式进行校验
	 * @param updPwdVo
	 * @return
	 * @throws Exception
	 */
	public AuthResultVo updPwd(UpdPwdVo updPwdVo) throws Exception;
	
	/**
	 * 找回密码，提供RMI，HTTP方式进行校验
	 * @param rtePwdVo
	 * @return
	 * @throws Exception
	 */
	public AuthResultVo resetPwd(ResetPwdVo resetPwdVo) throws Exception;
	
	/**
	 * 获取验证码，提供RMI，HTTP方式进行校验
	 * @param expCodeVo
	 * @return
	 * @throws Exception
	 */
	public void getCode(ExpCodeVo expCodeVo) throws Exception;
	
	/**
	 * 校验验证码，提供RMI，HTTP方式进行校验
	 * @param expCodeVo
	 * @return
	 * @throws Exception
	 */
	public String checkCode(ExpCodeVo expCodeVo) throws Exception;
	
	/**
	 * 权限检查（系统切换接口）
	 * @param authCheckVo
	 * @return
	 * @throws Exception
	 */
	AuthResultVo authCheck(AuthCheckVo authCheckVo) throws Exception;
	

	/**
	 * 方法的描述：
	 * <pre>
	 * 扫码登录补全数据
	 * </pre>
	 * @param loginVo
	 * @param authResultVo
	 * @throws Exception
	 * @return void
	 * @author LH
	 * @email luhenguce@uce.cn
	 * @date 2019年7月9日下午2:47:26
	 */
	public void subParamForLoginCode(LoginVo loginVo,AuthResultVo authResultVo) throws Exception;
	
	/**
	 * 解密token
	 * @param encryptTokenId 加密token
	 * @param systemCode 系统编码
	 * @return
	 * @throws Exception 
	 */
	public String decryptTokenIdBySystemCode(String encryptTokenId, String systemCode) throws Exception;
	
	/**
	 * 获取所有系统信息
	 * @return
	 * @throws Exception 
	 */
	public List<SystemInfoVo> findSystemByCondition(SystemInfoVo searchSystemInfo) throws Exception;
	
	/**
	 * 根据systemCode获取系统信息
	 * @return
	 * @throws Exception 
	 */
	public SystemInfoVo findSystemBySystemCode(String systemCode) throws Exception;
	
	/**
	 * 根据用户名查询用户信息
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	EmpInfoVo findEmpInfoByUserName(String userName) throws Exception;
	
	/**
	 * 校验用户是否存在,支持根据手机号,邮箱,empCode查找
	 * @param key
	 * @param type 1=empCode;2=mobile;3=email
	 * @return
	 * @throws Exception
	 */
	EmpInfoVo authCheckUser(String key, int type) throws Exception;

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
	
	/**
	 * @Description: 接收快运mq消息修改密码
	 * @param userPasswordVo
	 * @throws Exception
	 * @author huangting
	 * @date 2019年9月19日 下午5:19:44
	 */
	public boolean updPwdByMq(UserPasswordVo userPasswordVo) throws Exception;

}
