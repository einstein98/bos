package com.heima.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heima.domain.User;
import com.heima.exception.UserLoginException;
import com.heima.service.UserService;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月2日 下午2:28:48
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-domain.xml", "classpath:applicationContext-service.xml",
		"classpath:applicationContext-dao.xml" })
public class UserServiceTest {
	@Autowired
	private UserService service;

	@Test
	public void demo() {
		User user = new User();
		user.setEmail("43404154@qq.com");
		user.setPassword("sw116001");
		try {
			User user2 = service.login(user);
			System.out.println("login success");

		} catch (UserLoginException e) {
			System.out.println(e.getMessage());
		}
	}
}
