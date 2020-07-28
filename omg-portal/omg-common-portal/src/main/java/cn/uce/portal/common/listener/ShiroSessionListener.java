package cn.uce.portal.common.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ShiroSessionListener implements SessionListener {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private RedisSessionDAO redisSessionDAO;
	
	@Override
	public void onStart(Session session) {
		if(logger.isDebugEnabled()) {
			logger.debug("session on start!");
		}
	}

	@Override
	public void onStop(Session session) {
		try {
//			redisSessionDAO.delete(session); 改为配置里删除
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("session onStop!");
			}
		}
	}

	@Override
	public void onExpiration(Session session) {
		if(logger.isDebugEnabled()) {
			logger.debug("session 已经超时 on exporation!");
		}
	}
    
}
