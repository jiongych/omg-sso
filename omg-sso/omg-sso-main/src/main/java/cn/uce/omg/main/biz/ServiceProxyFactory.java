/** 
 * @项目名称: FSP
 * @文件名称: ServiceProxyFactory 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.main.biz;

import java.util.Map;

/**
 * 服务代理工厂
 * @author huangting
 * @date 2017年6月9日 下午1:39:45
 */
public class ServiceProxyFactory {
	/** 服务对象映射Map,通过Spring进行注入 */
	private Map<String, ServiceProxy> serviceProxyMap;

	public ServiceProxy getServiceProxy(String serviceName) {
		return serviceProxyMap.get(serviceName);
	}

	/**
	 * @param serviceProxyMap the serviceProxyMap to set
	 */
	public void setServiceProxyMap(Map<String, ServiceProxy> serviceProxyMap) {
		this.serviceProxyMap = serviceProxyMap;
	}

}
