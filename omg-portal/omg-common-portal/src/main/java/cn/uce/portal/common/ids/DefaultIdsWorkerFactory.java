package cn.uce.portal.common.ids;

import org.apache.shiro.util.Assert;
import org.apache.zookeeper.ZooKeeper;

import redis.clients.jedis.JedisPool;
import cn.uce.portal.common.ids.redis.RedisIdsWorker;
import cn.uce.portal.common.ids.snowflake.SnowFlakeIdsWorker;
import cn.uce.portal.common.ids.uuid.UUIdsWorker;
import cn.uce.portal.common.ids.zookeeper.ZookeeperIdsWorker;

public class DefaultIdsWorkerFactory implements IdsWorkerFactory{
	
	private JedisPool jedisPool;
	
	private ZooKeeper zooKeeper;
	
	/**
	 * SnowFlakeIdsWorker 由 workerId, dataCenterId, timestamp 来生成19位有序序列
	 * workerId和centerId都是0-255之前的整数，共有组合65000多种
	 * 一个JVM只会有一个worker实例,在一个worker实例中，即使是同一个时间点生成序列
	 * 也不会重复，但是在多JVM进程中，如果遇到workerId, centerId, timestamp都相同时还是有可能会有ID重复的
	 * 要保证绝对不重复需要搭建统一的SnowFlakeID服务器
	 * @author zhuhy
	 */
	@Override
	public IdsWorker getSnowflakeIdsWorker() {
		return SnowFlakeIdsWorker.getWorker();
	}
	
	/**
	 * Redis生成稳定有序的ID
	 */
	@Override
	public IdsWorker getRedisIdsWorker() {
		Assert.notNull(jedisPool, "jedisPool must not be null.");
		return new RedisIdsWorker(jedisPool);
	}
	
	/**
	 * UUID生成无序ID
	 */
	@Deprecated
	@Override
	public IdsWorker getUUIdsWorker() {
		return UUIdsWorker.getWorker();
	}
	
	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
	
	@Deprecated
	@Override
	public IdsWorker getZooKeeperIdsWorker() {
		Assert.notNull(zooKeeper, "zooKeeper must not be null.");
		return ZookeeperIdsWorker.getWorker(zooKeeper);
	}

}
