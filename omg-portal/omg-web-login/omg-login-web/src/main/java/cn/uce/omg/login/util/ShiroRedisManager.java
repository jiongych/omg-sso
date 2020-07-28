package cn.uce.omg.login.util;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.crazycake.shiro.RedisManager;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * Redis客户端 初始化 author：zhangd date：2017-03-01 09:30
 */
public class ShiroRedisManager extends RedisManager{

	private String host = "127.0.0.1";
	
	private int port = 6379;
	
	// 0 - never expire
	private int expire = 0;
	
	//timeout for jedis try to connect to redis server, not expire time! In milliseconds
	private int timeout = 3000;
	
	private String password = "";
	
	/** 默认连接Redis节点下标 */
	private int database = 0;
	
	private static JedisPool jedisPool = null;
	
	/** jedis连接池-配置参数 */
	private JedisPoolConfig poolConfig = null;
	
	/** 日志 */
	Log log = LogFactory.getLog(getClass());
	
	public ShiroRedisManager(){
		
	}
	
	/**
	 * 初始化方法
	 */
	public synchronized void init() {
		try {
			if (jedisPool == null) {
				if (password != null && !"".equals(password)) {
					jedisPool = new JedisPool(getPoolConfig(), host, port,
							timeout, password, database);
				} else if (timeout != 0) {
					jedisPool = new JedisPool(getPoolConfig(), host, port,
							timeout);
				} else {
					jedisPool = new JedisPool(getPoolConfig(), host, port);
				}
				log.info("RedisClient init() jedisPool success !");
			}
		} catch (JedisConnectionException e) {
			log.error("RedisClient init() jedisPool error", e);
		}
	}
	
	
	public JedisPoolConfig getPoolConfig() {
		if (poolConfig == null) {
			log.info("RedisClient init() jedisPool success !");
			poolConfig = new JedisPoolConfig();
		}
		return poolConfig;
	}

	public void setPoolConfig(JedisPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}
	
	/**
	 * get value from redis
	 * @param key
	 * @return
	 */
	public byte[] get(byte[] key){
		byte[] value = null;
		Jedis jedis = jedisPool.getResource();
		try{
			value = jedis.get(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		return value;
	}
	
	/**
	 * set 
	 * @param key
	 * @param value
	 * @return
	 */
	public byte[] set(byte[] key,byte[] value){
		Jedis jedis = jedisPool.getResource();
		try{
			jedis.set(key,value);
			if(this.expire != 0){
				jedis.expire(key, this.expire);
		 	}
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		return value;
	}
	
	/**
	 * set 
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public byte[] set(byte[] key,byte[] value,int expire){
		Jedis jedis = jedisPool.getResource();
		try{
			jedis.set(key,value);
			if(expire != 0){
				jedis.expire(key, expire);
		 	}
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		return value;
	}
	
	/**
	 * del
	 * @param key
	 */
	public void del(byte[] key){
		Jedis jedis = jedisPool.getResource();
		try{
			jedis.del(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	/**
	 * flush
	 */
	public void flushDB(){
		Jedis jedis = jedisPool.getResource();
		try{
			jedis.flushDB();
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	/**
	 * size
	 */
	public Long dbSize(){
		Long dbSize = 0L;
		Jedis jedis = jedisPool.getResource();
		try{
			dbSize = jedis.dbSize();
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		return dbSize;
	}

	/**
	 * keys
	 * @param regex
	 * @return
	 */
	public Set<byte[]> keys(String pattern){
		Set<byte[]> keys = null;
		Jedis jedis = jedisPool.getResource();
		try{
			keys = jedis.keys(pattern.getBytes());
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		return keys;
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getDatabase() {
		return database;
	}
	
	public void setDatabase(int database) {
		this.database = database;
	}
}
