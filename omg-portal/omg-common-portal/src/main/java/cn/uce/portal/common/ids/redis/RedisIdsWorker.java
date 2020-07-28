package cn.uce.portal.common.ids.redis;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.Assert;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import cn.uce.portal.common.ids.IdsWorker;

public class RedisIdsWorker implements IdsWorker{
	
	private final JedisPool jedisPool;
	
	private static final String DEFAULT_SEQUENCE = "global_ids";
	
	public RedisIdsWorker(JedisPool jedisPool){
		if(null == jedisPool){
			throw new NullPointerException("jedis must not be null");
		}
		this.jedisPool = jedisPool;
	}
	
	@Override
	public String nextId() {
		return nextId(DEFAULT_SEQUENCE);
	}

	@Override
	public String nextId(String sequence) {
		Assert.isTrue(StringUtils.isNotBlank(sequence));
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String nextId = jedis.get(sequence);
			if(null == nextId){
				jedis.setnx(sequence, "0");
				return "0";
			} else {
				return String.valueOf(jedis.incrBy(sequence, 1l));
			}
		} finally {
			returnResource(jedis);
		}
	}
	
	@SuppressWarnings("deprecation")
	private void returnResource(Jedis jedis){
		if (jedis != null) {
			jedisPool.returnResource(jedis);
    	}
	}

}
