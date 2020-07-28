/** 
 * @项目名称: FSP
 * @文件名称: PropertyConfigurer extends PropertyPlaceholderConfigurer
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 属性配置
 * @author huangting
 * @date 2017年6月9日 下午2:52:02
 */
public class PropertyConfigurer extends PropertyPlaceholderConfigurer{
	private static Map<String, Object> ctxPropertiesMap = new HashMap<String, Object>();;  
	
  /**
	* 
	* @param beanFactory
	* @param props
	* @throws BeansException 
	* @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer#processProperties(org.springframework.beans.factory.config.ConfigurableListableBeanFactory, java.util.Properties)
	*/
    @Override  
    protected void processProperties(ConfigurableListableBeanFactory beanFactory,  Properties props)throws BeansException {  
        super.processProperties(beanFactory, props);  
        for (Object key : props.keySet()) {  
            String keyStr = key.toString();  
            String value = props.getProperty(keyStr);  
            ctxPropertiesMap.put(keyStr, value);  
        }  
    }  
  
    /**
     * 
     * @param name
     * @return
     */
    public static Object getContextProperty(String name) {  
        return ctxPropertiesMap.get(name);  
    }  
}
