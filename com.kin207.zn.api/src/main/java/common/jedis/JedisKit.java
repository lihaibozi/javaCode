package common.jedis;

import java.util.Properties;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisKit {

	private static final Logger log = Logger.getLogger(JedisKit.class);
	
	private static JedisPool pool;
	
	public static void initFromProperties(Properties properties)
	{
		startJedis(properties);
		JedisKit.testPoolOk();
	}
	
	private static void startJedis(Properties properties) 
	{
		String host = properties.getProperty("redis_host");
		String password = properties.getProperty("redis_password");
		int port = Integer.valueOf(properties.getProperty("redis_port"));
		
		int timeout = Integer.valueOf(properties.getProperty("redis_timeout"));
		int maxIdle = Integer.valueOf(properties.getProperty("redis_max_idle"));
		int maxTotal = Integer.valueOf(properties.getProperty("redis_max_total"));
		
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(maxIdle);
		config.setMaxTotal(maxTotal);
		config.setTestOnBorrow(false);
		config.setTestOnReturn(false);

		pool = new JedisPool(config, host, port, timeout, password);
		
	}
	
	public static void stopJedisPool()
	{
		if (pool != null) 
		{
			pool.close();
		}
	}
	
	private static JedisPool getPool()
	{
		return pool;
	}
	
	/**
	 * 使用完成之后，一定要关 jedis
	 * @return
	 */
	public static Jedis getResource() 
	{
		return getPool().getResource();
	}
	
	public static void close(Jedis jedis) 
	{
		if (jedis != null) 
		{
			jedis.close();
		}
	}
	
	public static void destroyPool() 
	{
		
	}
	
	
	public static void printStatus() 
	{
	/*	System.out.println("pool active: " + getPool().getNumActive() 
						+ "\t	pool idle: " + getPool().getNumIdle()
						+ "\t	pool waiters: " + getPool().getNumWaiters());*/
		log.info("pool active: " + getPool().getNumActive() 
				+ "\t	pool idle: " + getPool().getNumIdle()
				+ "\t	pool waiters: " + getPool().getNumWaiters());
	}

	public static void testPoolOk() 
	{
		Jedis jedis = getResource();
		close(jedis);
		log.info("------------- jedis pool 启动成功 --------------");
	}
	
	public static void testConnection() 
	{
		Jedis jedis = getResource();
		try {
			jedis.set("test_foo", "bar");
			String foobar = jedis.get("foo");
//			System.out.println("foobar:" + foobar);
			log.info("foobar:" + foobar);
		} finally {
			close(jedis);
		}
	}

}
