package cn.uce.omg.login.biz;

import cn.uce.omg.sso.vo.AuthCheckVo;
import cn.uce.omg.sso.vo.AuthResultVo;
import cn.uce.omg.sso.vo.LogoutVo;

/**
 * @Description: (ICasBiz) 
 * @author XJ
 * @date 2017年7月28日 上午9:30:40
 */
public interface ICasBiz {
	/**
	 * 获取ssoUrl
	 * @return
	 */
	String getSsoUrl();
	
	/**
	 * @Description: (获取系统编码) 
	 * @return
	 * @author XJ
	 * @date 2017年7月5日 下午2:24:55
	 */
	String getSystemCode();
	
	
	/**
	 * @Description: (logout) 
	 * @param token
	 * @param userName
	 * @param empCode
	 * @return
	 * @throws Exception
	 * @author XJ
	 * @date 2017年4月25日 下午4:00:42
	 */
	boolean logout(LogoutVo logoutVo) throws Exception;
	
	/**
	 * @Description: (修改密码) 
	 * @param updPwdVo
	 * @return
	 * @author XJ
	 * @throws Exception 
	 * @date 2017年4月26日 上午11:38:37
	 */
	boolean updatePwd(cn.uce.omg.sso.vo.UpdPwdVo updPwdVo) throws Exception;
	
	/**
	 * @Description: (cookie获取的token上sso校验) 
	 * @param authCheckVo
	 * @return
	 * @author XJ
	 * @throws Exception 
	 * @date 2017年7月5日 下午2:19:41
	 */
	AuthResultVo authCheck(AuthCheckVo authCheckVo);
	
}
