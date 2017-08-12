package com.heima.utils;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月11日 上午9:57:01
 */
public class RedisUtils {
	@SuppressWarnings("unchecked")
	public static void clearRedisCache(RedisTemplate<String, String> redis) {
		redis.execute(new RedisCallback() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return "ok";
			}
		});
	}
}
