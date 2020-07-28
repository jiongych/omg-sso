package cn.uce.omg.sso.util.redis;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;

import cn.uce.omg.sso.util.ReflectionUtils;

/**
 * 通过bound封装指定的key，进行一系列的操作而无须“显式”的再次指定Key
 * @author zhangfh
 *
 * @param <T> redis中存放的对象
 */
@SuppressWarnings("unchecked")
public abstract class BoundZSetRedisSupport<T> {
	
	private Class<T> tClass = ReflectionUtils.getSuperClassGenricType(this.getClass());
	protected RedisTemplate<Serializable, T> redisTemplate;
	protected BoundZSetOperations<Serializable, T> boundZSetOperations;

	/**
	 * key默认值为“存放的对象的简单类名:zset”,可在子类中覆盖默认key
	 */
	protected String hkey = tClass.getSimpleName() + ":zset";;
	
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
		boundZSetOperations = this.redisTemplate.boundZSetOps(hkey);
	}

	/**
	 * 添加缓存
	 * @param map
	 */
	public boolean add(T member, double score){
		Boolean addSuccess = boundZSetOperations.add(member, score);
		if (addSuccess != null && addSuccess) {
			return true;
		}
		return false;
	}
	
	/**
	 * 排序
	 * @param min
	 * @param max
	 * @return
	 */
	public Set<T> rangeByScore(double min, double max) {
		return boundZSetOperations.rangeByScore(min, max);
	}
	
	/**
	 * 移除缓存
	 * @param map
	 */
	public boolean remove(String member) {
		Boolean removeSuccess = boundZSetOperations.remove(member);
		if (removeSuccess != null && removeSuccess) {
			return true;
		}
//		if (removeSuccess != null && removeSuccess > 0) {
//			return true;
//		}
		return false;
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
