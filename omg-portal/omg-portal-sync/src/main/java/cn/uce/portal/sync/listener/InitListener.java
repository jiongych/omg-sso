package cn.uce.portal.sync.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.uce.web.common.constants.Constants;

import com.alibaba.fastjson.parser.ParserConfig;


/**
 * @Description: 容器初始化监听器 
 * @author zhangd
 * @date 2017年5月3日 上午10:21:46
 */
public class InitListener implements ServletContextListener {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String fastjsonParserConfig="cn.uce.";
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ParserConfig.getGlobalInstance().addAccept(fastjsonParserConfig);
		try{
			sce.getServletContext().setAttribute(Constants.SYSTEM_NAME, sce.getServletContext().getInitParameter("sysName"));
			sce.getServletContext().setAttribute(Constants.STATIC_SERVERADDRESS, sce.getServletContext().getInitParameter("staticServerAddress"));
			if(logger.isInfoEnabled()) {
				logger.info("InitListener success !");
			}
		}catch(Exception e) {
			logger.error("InitListener fial ",e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
