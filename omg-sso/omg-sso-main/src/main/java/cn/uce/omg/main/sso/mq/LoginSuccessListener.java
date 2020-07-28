package cn.uce.omg.main.sso.mq;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.uce.mq.base.BaseMqMessageListener;
import cn.uce.omg.sso.biz.IUserBiz;
import cn.uce.omg.vo.LoginItemVo;
import cn.uce.omg.vo.UserVo;
import cn.uce.utils.FastJsonUtil;
import cn.uce.utils.StringUtil;

public class LoginSuccessListener extends BaseMqMessageListener<String> {
	private final static Log log = LogFactory.getLog(LoginSuccessListener.class);
	private IUserBiz userBiz;
	
	@Override
	public void processMessageBody(String body) {
		if (StringUtil.isBlank(body)) {
			return;
		}
		LoginItemVo loginItemVo = (LoginItemVo) FastJsonUtil.jsonParseObject(body);
		/**
		 * 记录登录日志
		 */
		try {
			userBiz.insertLoginItem(loginItemVo);
		} catch (Exception e) {
			log.error("记录登陆日志失败", e);
		}
		
		//更新用户登录时间(同时更新登陆时的用户信息缓存）
		UserVo userVo = new UserVo();
		userVo.setEmpId(loginItemVo.getEmpId());
		userVo.setEmpCode(loginItemVo.getEmpCode());
		userVo.setLastLoginTime(loginItemVo.getCreateTime());
		userVo.setUpdateTime(new Date());
		userBiz.handleUpdateUserLastLoginTime(userVo);
	}

	/**
	 * @param userBiz the userBiz to set
	 */
	public void setUserBiz(IUserBiz userBiz) {
		this.userBiz = userBiz;
	}

}
