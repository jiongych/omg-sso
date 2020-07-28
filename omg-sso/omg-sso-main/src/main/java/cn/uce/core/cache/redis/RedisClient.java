package cn.uce.core.cache.redis;

import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;
/**
 * 由于uce-base-cache和uce-core-cache的代码有重复，
 * 导致加载时，如果先加载core-cache时，可能会报错，目前解决的方式将uce-base-cache的代码拷贝出来。
 * @author majun
 *
 */
public class RedisClient
  implements InitializingBean, DisposableBean
{
  Log log = LogFactory.getLog(getClass());
  private JedisPool pool;
  private JedisPoolConfig poolConfig = null;

  private AtomicBoolean poolInited = new AtomicBoolean(false);

  private String host = "localhost";

  private int port = 6379;
  private String password;
  private int timeout = 3000;

  public void afterPropertiesSet() throws Exception
  {
    init();
  }

  private void init()
  {
    Jedis j = null;
    try {
      j = new Jedis(this.host, this.port, this.timeout);
      if ((this.password != null) && (this.password != ""))
        j.auth(this.password);
      else {
        j.ping();
      }

      initPool(this.host, this.port, this.timeout, this.password);
      this.poolInited.set(true);
    } catch (JedisConnectionException e) {
      this.poolInited.set(false);
      this.log.error(e.getMessage(), e);
    } finally {
      if (j != null) disconnect(j); 
    }
  }

  private synchronized void initPool(String host, int port, int timeout, String password)
  {
    if (this.pool != null) this.pool.destroy();
    this.pool = new JedisPool(getPoolConfig(), host, port, timeout, password);
    this.log.info("uce-base-cache-RedisClient：jedis连接池初始化完毕omg-sso");
  }

  public boolean getPoolInited()
  {
    return this.poolInited.get();
  }

  private void disconnect(Jedis jedis)
  {
    jedis.disconnect();
  }

  public synchronized Jedis getResource()
  {
    if (this.pool == null) {
      init();
    }
    Jedis jedis = null;
    if (this.pool != null) {
      jedis = (Jedis)this.pool.getResource();
    }
    if (jedis == null) {
      throw new JedisException("uce-base-cache:获取不到jedis连接");
    }
    return jedis;
  }

  public void returnResource(Jedis jedis)
  {
    this.pool.returnResource(jedis);
  }

  public void returnBrokenResource(Jedis jedis) {
    this.pool.returnBrokenResource(jedis);
  }

  public void destroy()
  {
    if (this.pool != null)
      this.pool.destroy();
  }

  public JedisPoolConfig getPoolConfig()
  {
    if (this.poolConfig == null) {
      this.poolConfig = new JedisPoolConfig();

      this.poolConfig.setMaxIdle(200);

      this.poolConfig.setTestOnBorrow(false);
      this.poolConfig.setTestOnReturn(false);
    }
    return this.poolConfig;
  }

  public void setPoolConfig(JedisPoolConfig poolConfig) {
    this.poolConfig = poolConfig;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setTimeout(int timeout) {
    this.timeout = timeout;
  }
}