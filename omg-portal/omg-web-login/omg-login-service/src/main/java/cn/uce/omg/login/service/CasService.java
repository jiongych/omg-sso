package cn.uce.omg.login.service;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.uce.omg.sso.api.IAuthApi;
import cn.uce.omg.sso.api.IAuthRoleApi;
import cn.uce.omg.sso.vo.AuthCheckVo;
import cn.uce.omg.sso.vo.AuthResultVo;
import cn.uce.omg.sso.vo.LogoutVo;

/**
 * @Description: (CasService) 
 * @author XJ
 * @date 2017年7月28日 上午9:31:51
 */
@Service("fspCasService")
public class CasService {
	public Log log = LogFactory.getLog(CasService.class);
	
	@Value("${omg.sso.systemCode}")
	private String systemCode;
	
	@Resource(name = "authServiceProxy")
	private IAuthApi authApi;
	
	@Resource(name = "authRoleServiceProxy")
	private IAuthRoleApi authRoleApi;
	
	/**
	 * @Description:(调用sso退出) 
	 * @param token
	 * @param userName
	 * @param empCode
	 * @return
	 * @throws Exception
	 * @author XJ
	 * @date 2017年4月25日 下午4:06:13
	 */
	public boolean logout(LogoutVo logoutVo) throws Exception{
		AuthResultVo authResultVo =  authApi.logout(logoutVo);
		if(authResultVo != null && authResultVo.getUserName()!=null){
			log.info(authResultVo.getUserName()+"sso退出成功！");
			return true;
		}
		return false;
	}
	
	/**
	 * @Description:(调用sso修改密码) 
	 * @param updPwdVo
	 * @return
	 * @throws Exception
	 * @author XJ
	 * @date 2017年4月26日 上午11:42:44
	 */
	public boolean updatePwd(cn.uce.omg.sso.vo.UpdPwdVo updPwdVo) throws Exception{
		AuthResultVo authResultVo = authApi.updPwd(updPwdVo);
		if(authResultVo != null && authResultVo.getEmpCode()!=null) {
			log.info(authResultVo.getEmpCode()+"sso修改密码成功！");
			return true;
		}
		return false;
	}
	
	/**
	 * @Description: (cookie中获取的token上sso校验) 
	 * @param authCheckVo
	 * @return
	 * @throws Exception
	 * @author XJ
	 * @date 2017年7月5日 下午2:15:06
	 */
	public AuthResultVo authCheck(AuthCheckVo authCheckVo) throws Exception{
		return  authApi.authCheck(authCheckVo);
	}
	
}