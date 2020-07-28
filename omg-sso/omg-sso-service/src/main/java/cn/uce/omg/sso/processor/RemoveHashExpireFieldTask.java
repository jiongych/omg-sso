/** 
 * @项目名称: FSP
 * @文件名称: RemoveHashExpireFieldTask<T> extends Thread implements DisposableBean 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.processor;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import cn.uce.omg.sso.cache.HashFieldExpireTimeCache;

/**
 * 删除redis过期数据
 * @author zhangfenghuang
 * @param <T>
 */
public class RemoveHashExpireFieldTask<T> extends Thread implements DisposableBean {
	private Log LOG = LogFactory.getLog(this.getClass());
	/** 
	 * 缓存
	 */
	private HashFieldExpireTimeCache HashFieldExpireTimeCache;
	
	/** 
	 * 消息模板
	 */
	protected RedisTemplate<Serializable, T> redisTemplate;
	
	/** 
	 * 默认5分钟 
	 */
	private int waitTime = 300;
	
	/** 
	 * 退出标识
	 */
	private boolean exitFlag;
	
	/** 
	 * 分割符
	 */
	private String splitChar = "#";

	/**
	 * 线程执行入口
	 * @author zhangfh
	 * @date 2017年6月14日 下午4:06:47
	 */
	public void run() {
		//等待时间
		waitTime = waitTime * 1000;
		while (!exitFlag) {
			try {
				//获取当前时间
				long now = System.currentTimeMillis();
				Set<String> keySet = HashFieldExpireTimeCache.rangeByScore(0, now);
				// 删除过期key
				Iterator<String> keyIterator = keySet.iterator();
				while (keyIterator.hasNext()) {
					String key = keyIterator.next();
					//按符号分割
					String[] keyArr = key.split(splitChar);
					if (keyArr != null && keyArr.length == 2) {
						String hashKey = keyArr[0];
						String hashField = keyArr[1];
						try {
							BoundHashOperations<Serializable, String, T> boundHashOperations = this.redisTemplate.boundHashOps(hashKey);
							boundHashOperations.delete(hashField);
							//根据key删除过期缓存
							HashFieldExpireTimeCache.remove(key);
						} catch (Exception e) {
							LOG.error("删除HASH过期field失败，key非法，key=" + key, e);
						}
					} else {
						LOG.error("删除HASH过期field失败，key非法，key=" + key);
					}
				}
			} catch (Exception e) {
				LOG.error("删除HASH过期field失败，", e);
			}
			try {
				//线程等待
				Thread.sleep((waitTime));
			} catch (Exception e) {
				LOG.error(e);
			}
		}
	}

	/**
	 * @param hashFieldExpireTimeCache the hashFieldExpireTimeCache to set
	 */
	public void setHashFieldExpireTimeCache(
			HashFieldExpireTimeCache hashFieldExpireTimeCache) {
		HashFieldExpireTimeCache = hashFieldExpireTimeCache;
	}

	/**
	 * @param redisTemplate the redisTemplate to set
	 */
	public void setRedisTemplate(RedisTemplate<Serializable, T> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * @param waitTime the waitTime to set
	 */
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	/**
	* 线程销毁
	* @see java.lang.Thread#destroy()
	*/
	public void destroy() {
		this.exitFlag = true;
	}
}
