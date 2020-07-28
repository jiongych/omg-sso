/** 
 * @项目名称: FSP
 * @文件名称: BoundHashRedisSupport<T> 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.util.redis;

import java.io.Serializable;
import java.util.Map;

import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import cn.uce.omg.sso.util.ReflectionUtils;

/**
 * 通过bound封装指定的key，进行一系列的操作而无须“显式”的再次指定Key
 * 
 * @author jxiao
 *
 * @param <T> redis中存放的对象
 */
@SuppressWarnings("unchecked")
public class BoundHashRedisSupport<T> {
	
	private Class<T> tClass = ReflectionUtils.getSuperClassGenricType(this.getClass());
	protected RedisTemplate<Serializable, T> redisTemplate;
	protected BoundHashOperations<Serializable, String, T> boundHashOperations;
	
	/**
	 * key默认值为“存放的对象的简单类名:hash”,可在子类中覆盖默认key
	 */
	protected String hkey = tClass.getSimpleName() + ":hash";
		        
	/**
	 * 获取redis模板
	 * @return
	 */
	public RedisTemplate<Serializable, T> getRedisTemplate() {
		return redisTemplate;
	}

	/**
	 * 设置redis模板
	 * @return
	 */
	public void setRedisTemplate(RedisTemplate<Serializable, T> redisTemplate) {
		this.redisTemplate = redisTemplate;
		boundHashOperations = this.redisTemplate.boundHashOps(hkey); 
	}
	
	/**
	 * 存入缓存map
	 * @param map
	 */
	public void putAll(Map<String, T> map){
		boundHashOperations.putAll(map);
	}
	
	/**
	 * 存入缓存
	 * @param key
	 * @param value
	 */
	public void put(final String key, T value){
		boundHashOperations.put(key, value);
	}
	
	/**
     * 根据key获取缓存
     */
	public T get(String key){
		return boundHashOperations.get(key);
	}
	
	/**
     * 遍历缓存
     */
	public Map<String, T> entries(){
		return boundHashOperations.entries();
	}
	

    /**
     * 根据key删除缓存
     */
	public void delete(String key){
		boundHashOperations.delete(key);
	}

	/**
	 * @return the hkey
	 */
	public String getHkey() {
		return hkey;
	}

	/**
	 * @param hkey the hkey to set
	 */
	public void setHkey(String hkey) {
		this.hkey = hkey;
	}

}
