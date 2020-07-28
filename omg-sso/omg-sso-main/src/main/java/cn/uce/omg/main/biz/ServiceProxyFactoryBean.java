/** 
 * @项目名称: FSP
 * @文件名称: ServiceProxyFactoryBean implements FactoryBean<ServiceProxy>, BeanClassLoaderAware, BeanFactoryAware, InitializingBean, DisposableBean 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.main.biz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.util.ClassUtils;

/**
 * 服务代理工厂bean
 * @author huangting
 * @date 2017年6月9日 下午1:39:56
 */
public class ServiceProxyFactoryBean implements FactoryBean<ServiceProxy>, BeanClassLoaderAware, BeanFactoryAware, InitializingBean, DisposableBean {

	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());
	/**
	 * 是否是单例
	 */
	private boolean singleton = true;
	/**
	 * 获取类的加载器
	 */
	private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
	/**
	 * bean工厂
	 */
	private BeanFactory beanFactory;
	/**
	 * 是否实例化
	 */
	private boolean initialized = false;
	/**
	 * bean的代理实现
	 */
	private ServiceProxy singletonInstance;

	/**
	 * 真实的服务对象
	 */
	private Object service;

	/**
	 * 调用的方法名,访问对象的参考为两个,一个是Partner,一个是Object
	 */
	private String methodName;

	/**
	 * @return the service
	 */
	public Object getService() {
		return service;
	}

	/**
	 * @param service the service to set
	 */
	public void setService(Object service) {
		this.service = service;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * Set if a singleton should be created, or a new object on each request otherwise. Default is <code>true</code> (a singleton).
	 */
	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}
	/**
	 * @param methodName the isSingleton to get
	 */
	public boolean isSingleton() {
		return this.singleton;
	}
	/**
	 * @param methodName the setBeanClassLoader to set
	 */
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.beanClassLoader = classLoader;
	}
	/**
	 * @param methodName the setBeanFactory to set
	 */
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	/**
	 * Return the BeanFactory that this bean runs in.
	 */
	protected BeanFactory getBeanFactory() {
		return this.beanFactory;
	}

	/**
	 * Obtain a bean type converter from the BeanFactory that this bean runs in. This is typically a fresh instance for each call, since TypeConverters are usually <i>not</i>
	 * thread-safe.
	 * <p>
	 * Falls back to a SimpleTypeConverter when not running in a BeanFactory.
	 * 
	 * @see ConfigurableBeanFactory#getTypeConverter()
	 * @see org.springframework.beans.SimpleTypeConverter
	 */
	protected TypeConverter getBeanTypeConverter() {
		BeanFactory beanFactory = getBeanFactory();
		if (beanFactory instanceof ConfigurableBeanFactory) {
			return ((ConfigurableBeanFactory) beanFactory).getTypeConverter();
		} else {
			return new SimpleTypeConverter();
		}
	}

	/**
	 * Expose the singleton instance or create a new prototype instance.
	 * 
	 * @see #createInstance()
	 * @see #getEarlySingletonInterfaces()
	 */
	public final ServiceProxy getObject() throws Exception {
		if (isSingleton()) {
			if (!this.initialized) {
				this.initialized = true;
				this.singletonInstance = createInstance();
			}
			return this.singletonInstance;
		} else {
			return createInstance();
		}
	}

	/**
	 * Destroy the singleton instance, if any.
	 * 
	 * @see #destroyInstance(Object)
	 */
	public void destroy() throws Exception {
		if (isSingleton()) {
			destroyInstance(this.singletonInstance);
		}
	}

	/**
	 * This abstract method declaration mirrors the method in the FactoryBean interface, for a consistent offering of abstract template methods.
	 * 
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	public Class<ServiceProxy> getObjectType() {
		return ServiceProxy.class;
	}

	/**
	 * Template method that subclasses must override to construct the object returned by this factory.
	 * <p>
	 * Invoked on initialization of this FactoryBean in case of a singleton; else, on each {@link #getObject()} call.
	 * 
	 * @return the object returned by this factory
	 * @throws Exception if an exception occured during object creation
	 * @see #getObject()
	 */
	protected ServiceProxy createInstance() throws Exception {
		ServiceProxy serviceProxy = new ServiceProxy();
		serviceProxy.setService(service);
		serviceProxy.setMethodName(methodName);
		return serviceProxy;
	}

	/**
	 * Callback for destroying a singleton instance. Subclasses may override this to destroy the previously created instance.
	 * <p>
	 * The default implementation is empty.
	 * 
	 * @param instance the singleton instance, as returned by {@link #createInstance()}
	 * @throws Exception in case of shutdown errors
	 * @see #createInstance()
	 */
	protected void destroyInstance(ServiceProxy instance) throws Exception {
		// sonar检查需要方法体有代码或者注释
	}

	/**
	 * @return the beanClassLoader
	 */
	public final ClassLoader getBeanClassLoader() {
		return beanClassLoader;
	}
	/**
	 * 所有的属性被初始化后调用
	 */
	public void afterPropertiesSet() throws Exception {
		// sonar检查需要方法体有代码或者注释
	}

}
