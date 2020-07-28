package cn.uce.portal.common.ids;

/**
 * 自增ID生成器工厂类 
 * @author zhuhy
 */
public interface IdsWorkerFactory {
	/**
	 * 来自Twitter 的 Snowflak算法,当前是生成18位递增序列
	 * @return
	 */
	IdsWorker getSnowflakeIdsWorker();
	/**
	 * RedisID生成器
	 * @return
	 */
	IdsWorker getRedisIdsWorker();
	/**
	 * ZooKeeperID 生成器
	 * @return
	 */
	@Deprecated
	IdsWorker getZooKeeperIdsWorker();
	/**
	 * UUID 生成器
	 * @return
	 */
	@Deprecated
	IdsWorker getUUIdsWorker();
	
}
