package com.heima.service;

import java.util.List;

import com.heima.domain.User;
import com.heima.exception.UserLoginException;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月2日 上午11:31:07
 */
public interface UserService {

	void save(User user);

	void delete(User user);

	void update(User user);

	List<User> findAll();

	User findUserById(Integer id);

	User login(User user) throws UserLoginException;

	void changePassword(User user);

}
