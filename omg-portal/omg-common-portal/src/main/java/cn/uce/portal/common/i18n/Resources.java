package cn.uce.portal.common.i18n;

import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * <p>Description: </p>
 * @author zhangd
 * @date 2017年7月11日
 */
@Component
public class Resources implements ApplicationContextAware{
	
	/** 国际化信息 */
	public static String getMessage(String key, Object... params) {
		//默认取本地
		Locale locale = LocaleContextHolder.getLocale();
		return applicationContext.getMessage(key, params, locale);
	}
	
	private static ApplicationContext applicationContext;  
	  
    public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }  
  
    @Override  
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {  
        applicationContext = arg0;  
    }  
  
    public static Object getBean(String id) {  
        Object object = null;  
        object = applicationContext.getBean(id);  
        return object;  
    }
}
