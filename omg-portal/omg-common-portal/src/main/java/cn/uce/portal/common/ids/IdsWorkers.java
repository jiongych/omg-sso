package cn.uce.portal.common.ids;

import cn.uce.portal.common.ids.snowflake.SnowFlakeIdsWorker;
import cn.uce.portal.common.ids.uuid.UUIdsWorker;


/**
 * 获取主键ID的快速使用方式
 * @author zhuhy
 */
public enum IdsWorkers {
	
	/**
	 * SnowFlakeIdsWorker 由 workerId, dataCenterId, timestamp 来生成19位有序序列
	 * workerId和centerId都是0-255之前的整数，共有组合65000多种
	 * 一个JVM只会有一个worker实例,在一个worker实例中，即使是同一个时间点生成序列
	 * 也不会重复，但是在多JVM进程中，如果遇到workerId, centerId, timestamp都相同时还是有可能会有ID重复的
	 * 要保证绝对不重复需要搭建统一的SnowFlakeID服务器
	 * @author zhuhy
	 */
	SNOWFLAKE(SnowFlakeIdsWorker.getWorker()), 
	/**
	 * UUID生成无序ID
	 */
	@Deprecated
	UUID(UUIdsWorker.getWorker()),
	/**
	 * 此处不可用，只能使用IdsWorkerFactory来访问RedisIdsWorker
	 */
	REDIS(null),
	/**
	 * 此处不可用，只能使用IdsWorkerFactory来访问ZookeeperIdsWorker
	 */
	@Deprecated
	ZOOKEEPER(null);
	
	private final IdsWorker idsWorker;
	
	IdsWorkers(IdsWorker idsWorker){
		this.idsWorker = idsWorker;
	}
	
	public String nextId(){
		if(null == idsWorker)
			throw new UnsupportedOperationException("Redis idsWorker only be used by IdsWorkerFactory.");
		return idsWorker.nextId();
	}
	
	public String nextId(String sequence){
		if(null == idsWorker)
			throw new UnsupportedOperationException("Redis or Zookeeper idsWorker only be used by IdsWorkerFactory.");
		return idsWorker.nextId(sequence);
	}
	
}
