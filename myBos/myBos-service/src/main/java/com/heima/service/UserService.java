
package com.heima.service;

import com.heima.Exception.UserLoginException;
import com.heima.domain.User;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月7日 下午8:01:42
 */
public interface UserService {

	User login(User user) throws UserLoginException;

	void changePassword(User user, String password);

}
