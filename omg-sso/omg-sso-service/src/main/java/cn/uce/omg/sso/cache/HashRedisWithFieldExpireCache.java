/** 
 * @项目名称: FSP
 * @文件名称: HashRedisWithFieldExpireCache<T> extends BoundHashRedisSupport<T> implements InitializingBean 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.cache;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import cn.uce.omg.sso.util.redis.BoundHashRedisSupport;
import cn.uce.omg.sso.vo.RedisHashFieldExpireVo;

/**
 * 在redis的操作上再加一层，可以控制hash field的过期时间
 * @author zhangfh
 *
 * @param <T> redis中存放的对象
 */
public class HashRedisWithFieldExpireCache<T> extends BoundHashRedisSupport<T> implements InitializingBean {
	private Log LOG = LogFactory.getLog(this.getClass());
	protected HashFieldExpireTimeCache hashFieldExpireTimeCache;
	private String splitChar = "#";

	@Override
	public void afterPropertiesSet() throws Exception {
		if (hashFieldExpireTimeCache == null) {
			throw new IllegalArgumentException("HashFieldExpireTimeCache cann't be null");
		}
	}

  /**
	* 存入缓存 
	* @param key
	* @param value 
	* @see cn.uce.omg.sso.util.redis.BoundHashRedisSupport#put(java.lang.String, java.lang.Object)
	 */
	public void put(final String key, T value) {
		if (value instanceof RedisHashFieldExpireVo) {
			RedisHashFieldExpireVo tokenVo = (RedisHashFieldExpireVo) value;
			long now = System.currentTimeMillis();
			Long expireTime = tokenVo.getExpireTime();
			if (expireTime != null) {
				long timeDelay = expireTime - now;
				if (timeDelay <= 0) {
					LOG.warn(hkey + ".put()已到达失效时间：" + expireTime + ", 不做存入操作");
					return;
				}
				hashFieldExpireTimeCache.add(generateZsetMember(key), expireTime);
			} else {
				hashFieldExpireTimeCache.add(generateZsetMember(key), -1);
			}
		}
		super.put(key, value);
	}

	/**
	* 存入缓存 ，取当前配置的expireTime作为缓存失效时间
	* @param key
	* @param value 
	* @author 014266
	* @see cn.uce.omg.sso.util.redis.BoundHashRedisSupport#put(java.lang.String, java.lang.Object)
	 */
	public void puts(final String key, T value) {
		if (value instanceof RedisHashFieldExpireVo) {
			RedisHashFieldExpireVo tokenVo = (RedisHashFieldExpireVo) value;
			long now = System.currentTimeMillis();
			Long expireTime = tokenVo.getExpireTime();
			if (expireTime != null) {
				long timeDelay = expireTime - now;
				if (timeDelay <= 0) {
					LOG.warn(hkey + ".put()已到达失效时间：" + expireTime + ", 不做存入操作");
					return;
				}
				//设置当前配置的失效时间
				hashFieldExpireTimeCache.add(generateZsetMember(key), timeDelay);
			} else {
				hashFieldExpireTimeCache.add(generateZsetMember(key), -1);
			}
		}
		super.put(key, value);
	}
  /**
    * 获取缓存
	* @param key
	* @return 
	* @see cn.uce.omg.sso.util.redis.BoundHashRedisSupport#get(java.lang.String)
	*/
	public T get(String key) {
		T value = super.get(key);
		// 判断是否失效
		if (value instanceof RedisHashFieldExpireVo) {
			RedisHashFieldExpireVo tokenVo = (RedisHashFieldExpireVo) value;
			Long expireTime = tokenVo.getExpireTime();
			if (expireTime != null) {
				long now = System.currentTimeMillis();
				if (expireTime <= now) {
					delete(key);
					return null;
				}
			}
		}
		return value;
	}

  /**
	* 遍历
	* @return 
	*/
	@Override
	public Map<String, T> entries() {
		return super.entries();
	}

  /**
	* 删除缓存
	* @param key 
	* @see cn.uce.omg.sso.util.redis.BoundHashRedisSupport#delete(java.lang.String)
	*/
	public void delete(String key) {
		super.delete(key);
		hashFieldExpireTimeCache.remove(generateZsetMember(key));
	}

	/**
	 * 
	 * @param key
	 * @return
	 * @date 2017年6月9日 下午6:12:24
	 */
	public String generateZsetMember(String key) {
		String member = this.getHkey() + splitChar + key;
		return member;
	}

	public void setHashFieldExpireTimeCache(HashFieldExpireTimeCache hashFieldExpireTimeCache) {
		this.hashFieldExpireTimeCache = hashFieldExpireTimeCache;
	}

}
