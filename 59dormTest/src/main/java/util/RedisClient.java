package util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 连接Redis
 * @author wangk
 *
 */
public class RedisClient {
	
	public Jedis jedis;
	private JedisPool jedisPool;
	
	public RedisClient(){
		jedisPool = new JedisPool("redis.59temai.com", 6380);
		jedis = jedisPool.getResource();
	}
	
}
