package com.heima.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heima.Exception.UserLoginException;
import com.heima.dao.UserDao;
import com.heima.domain.User;
import com.heima.service.UserService;
import com.heima.utils.MyUtils;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月7日 下午8:04:08
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
	private UserDao userDao;

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User login(User user) throws UserLoginException {
		String email = user.getEmail();
		if (MyUtils.isNotBlank(email)) {
			User existsUser = userDao.findByEmail(email);
			if (existsUser == null) {
				throw new UserLoginException("user_not_exists");
			} else if (!existsUser.getPassword().equals(user.getPassword())) {
				throw new UserLoginException("wrong_password");
			} else {
				return existsUser;
			}
		}
		System.out.println("登录数据没有传过来");
		return null;
	}

	@Override
	public void changePassword(User user, String password) {
		userDao.updateUserPassword(user.getId(), password);
	}

}
