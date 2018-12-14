package common.jedis;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.kin207.zn.SpringKit;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Jedis启动/关闭
 */
public class JedisPlugin implements ApplicationContextAware{

	private String configLocation;
	
	private static JedisPool pool;
	
	public static JedisPool getPool()
	{
		return pool;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException 
	{
		Properties properties = SpringKit.loadClasspathProperties(configLocation);
		startJedis(properties);
		JedisKit.testPoolOk();
	}
	
	private void startJedis(Properties properties) 
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
	
	public void stopJedis()
	{
		if (pool != null) 
		{
			pool.close();
		}
	}
	

	public String getConfigLocation() {
		return configLocation;
	}

	public void setConfigLocation(String configLocation) {
		this.configLocation = configLocation;
	}
	
}
