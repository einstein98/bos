package com.heima.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heima.dao.UserDao;
import com.heima.domain.User;
import com.heima.exception.UserLoginException;
import com.heima.service.UserService;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月2日 上午11:42:36
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void save(User user) {
		userDao.save(user);
	}

	@Override
	public void delete(User user) {
		userDao.delete(user);
	}

	@Override
	public void update(User user) {
		userDao.save(user);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public User findUserById(Integer id) {
		return userDao.getOne(id);
	}

	@Override
	public User login(User user) throws UserLoginException {
		User theUser = userDao.findByEmail(user.getEmail());
		if (theUser == null) {
			throw new UserLoginException("no_such_user");
		} else if (!user.getPassword().equals(theUser.getPassword())) {
			throw new UserLoginException("wrong_password");
		} else {
			return theUser;
		}
	}

	@Override
	public void changePassword(User user) {
		User existsUser = userDao.getOne(user.getId());
		existsUser.setPassword(user.getPassword());
		userDao.save(existsUser);
	}

}
