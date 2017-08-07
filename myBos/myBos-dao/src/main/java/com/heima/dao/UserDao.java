package com.heima.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.heima.domain.User;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月2日 上午9:11:36
 */

public interface UserDao extends JpaRepository<User, Integer> {
	User findByEmail(String email);
	// @Query(value = "select * from t_user where email=?1", nativeQuery = true)
	// User login(String email);
}
