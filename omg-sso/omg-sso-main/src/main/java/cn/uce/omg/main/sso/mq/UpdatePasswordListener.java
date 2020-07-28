package cn.uce.omg.main.sso.mq;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.uce.mq.base.BaseMqMessageListener;
import cn.uce.omg.sso.biz.IAuthBiz;
import cn.uce.omg.sso.vo.UserPasswordVo;

import com.alibaba.fastjson.JSONObject;

public class UpdatePasswordListener extends BaseMqMessageListener<Object>{
	private final static Log log = LogFactory.getLog(UpdatePasswordListener.class);
	private IAuthBiz authBiz;
	
	public void setAuthBiz(IAuthBiz authBiz) {
		this.authBiz = authBiz;
	}
	
	@Override
	public void processMessageBody(Object body) {
		if (null == body) {
			return;
		}
		try {
			String message = body.toString().replaceAll("@type", "null");
			if (log.isInfoEnabled()) {
				log.info("接收OMG转发的快运【修改密码】消息 :" + body);
			}
			UserPasswordVo pwdVo = JSONObject.parseObject(message, UserPasswordVo.class);
			boolean falg = authBiz.updPwdByMq(pwdVo);
			if (!falg) {
				log.error("接收OMG转发的快运【修改密码】消息,更新密码失败,参数:" + body);
			}
		} catch (Exception e) {
			log.error("接收壹米滴答分发平台【修改密码】消息,更新密码失败,参数：" + body,e);
		}
	}

}
