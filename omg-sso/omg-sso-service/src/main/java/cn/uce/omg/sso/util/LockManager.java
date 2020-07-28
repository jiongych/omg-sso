package cn.uce.omg.sso.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.uce.omg.sso.exception.TimeoutException;

/**
 * add by zhagnfh 2017-09-14 
 * @Description: 锁
 * @author user
 * @date 2017年9月14日 下午3:18:05
 */
public class LockManager {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	/**
	 * 同步信号对象
	 */
	private Object semaphore = new Object();
	
	/**
	 * 锁存放Map<锁类型，<锁Key, 锁Value>>
	 */
	private ConcurrentMap<String, ConcurrentMap<Object, Object>> lockMap = new ConcurrentHashMap<String, ConcurrentMap<Object, Object>>();
	
	/**
	 * 为对象Key加锁
	 * @param lockType 待锁对象类型
	 * @param key 待锁对象
	 * @return 是否加锁成功
	 */
	public boolean getLock(String lockType, Object key) {
		synchronized (semaphore) {
			ConcurrentMap<Object, Object> map = lockMap.get(lockType);
			if (map == null) {
				map = new ConcurrentHashMap<Object, Object>();
				lockMap.put(lockType, map);
			}
			return map.putIfAbsent(key, key) == null;
		}
	}
	
	
	/**
	 * 为对象Key加锁，如果没有获取到锁，则不停重试直到超时
	 * @param lockType 待锁对象类型
	 * @param key 待锁对象
	 * @param timeout 超时时间(毫秒)
	 * @throws TimeoutException
	 */
	public void getLock(String lockType, Object key, long timeout) throws TimeoutException {
		if (timeout <= 0) {
			timeout = Long.MAX_VALUE;
		}
		long beginTime = System.currentTimeMillis();
		long endTime = beginTime + timeout;
		while (!getLock(lockType, key)) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				log.warn("LockManager: "+e.getMessage(),e);	
				Thread.currentThread().interrupt();
			}
			if (System.currentTimeMillis() >= endTime) {
				throw new TimeoutException();
			}
		}
	}
	
	
	/**
	 * 释放对象Key的锁
	 * @param lockType 锁类型
	 * @param key 被锁对象
	 */
	public void releaseLock(String lockType, Object key) {
		synchronized (semaphore) {
			ConcurrentMap<Object, Object> map = lockMap.get(lockType);
			if (map != null) {
				map.remove(key);
			}
		}
	}
}
