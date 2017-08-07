package com.heima.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月7日 上午9:52:52
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-redis.xml")
public class RedisTest {
	@Autowired
	private RedisTemplate<String, String> template;

	@Test
	public void demo() {
		template.opsForValue().set("hello", "hah");
	}

}
