package cn.uce.omg.login.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.uce.omg.login.biz.ICasBiz;
import cn.uce.omg.login.service.CasService;
import cn.uce.omg.sso.vo.AuthCheckVo;
import cn.uce.omg.sso.vo.AuthResultVo;
import cn.uce.omg.sso.vo.LogoutVo;
import cn.uce.omg.sso.vo.UpdPwdVo;

/**
 * @Description:(CasBizImpl) 
 * @author XJ
 * @date 2017年7月28日 上午9:32:16
 */
@Service("fspCasBiz")
public class CasBizImpl implements ICasBiz {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private CasService fspCasService;
	@Value("${omg.sso.casUrl}")
	private String casUrl;
	@Value("${omg.sso.systemCode}")
	private String systemCode;
	
	/**
	 * @Description: (获取系统编码) 
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午1:34:02
	 */
	public String getSystemCode(){
		return systemCode;
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: getSsoUrl</p> 
	* <p>Description: 获取ssoUrl</p> 
	* @return 
	* @see cn.uce.omg.login.biz.web.login.biz.ICasBiz#getSsoUrl()
	 */
	@Override
	public String getSsoUrl() {
		return casUrl + "?systemCode=" + systemCode;
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: logout</p> 
	* <p>Description: 退出</p> 
	* @param token
	* @param userName
	* @param empCode
	* @return
	* @throws Exception 
	* @see cn.uce.omg.login.biz.web.login.biz.ICasBiz#logout(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean logout(LogoutVo logoutVo) throws Exception {
		return fspCasService.logout(logoutVo);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: updatePwd</p> 
	* <p>Description: 更新密码</p> 
	* @param updPwdVo
	* @return
	* @throws Exception 
	* @see cn.uce.omg.login.biz.web.login.biz.ICasBiz#updatePwd(cn.uce.omg.sso.vo.UpdPwdVo)
	 */
	@Override
	public boolean updatePwd(UpdPwdVo updPwdVo) throws Exception {
		updPwdVo.setSystemCode(systemCode);
		return fspCasService.updatePwd(updPwdVo);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: authCheck</p> 
	* <p>Description: sso登录校验</p> 
	* @param authCheckVo
	* @return 
	* @see cn.uce.omg.login.biz.web.login.biz.ICasBiz#authCheck(cn.uce.omg.sso.vo.AuthCheckVo)
	 */
	@Override
	public AuthResultVo authCheck(AuthCheckVo authCheckVo) {
		AuthResultVo authResultVo = null;
		try{
			authResultVo = fspCasService.authCheck(authCheckVo);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return authResultVo;
	}
}
