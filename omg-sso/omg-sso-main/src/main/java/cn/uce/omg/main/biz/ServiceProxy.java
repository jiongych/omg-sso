/** 
 * @项目名称: FSP
 * @文件名称: ServiceProxy 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.main.biz;

import java.lang.reflect.Method;

import cn.uce.omg.sso.exception.GatewayException;

/**
 * 服务代理类
 * @author huangting
 * @date 2017年6月9日 下午1:39:16
 */
public class ServiceProxy {
	/**
	 * 真实的服务对象
	 */
	private Object service;

	/**
	 * 调用的方法名,访问对象的参考为两个
	 */
	private String methodName;

    /**
     * 
     * @param systemCode
     * @param data
     * @return
     * @throws Exception
     * @author huangting
     * @date 2017年6月9日 下午1:39:32
     */
	public Object execute(String systemCode, Object data) throws Exception {
		try {
			Class<?> serviceClass = service.getClass();
			Method[] methods = serviceClass.getMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					if (method.getParameterTypes().length == 2) {
						return method.invoke(service, systemCode, data);
					} else if (method.getParameterTypes().length == 1) {
						return method.invoke(service, data);
					} else if (method.getParameterTypes().length == 0) {
						return method.invoke(service, new Object[] {});
					}
				}
			}
		} catch (Exception e) {
			if (e.getCause() instanceof GatewayException) {
				GatewayException gatewayException = (GatewayException) e.getCause();
				throw new GatewayException(e, gatewayException.getErrorCode(), gatewayException.getErrorMessage());
			}
			throw e;
		}
		throw new RuntimeException("method not found!");
	}

	/**
	 * @param service the service to set
	 */
	public void setService(Object service) {
		this.service = service;
	}

	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
}
