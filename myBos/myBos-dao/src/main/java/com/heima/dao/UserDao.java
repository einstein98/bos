package com.heima.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.heima.domain.User;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月7日 下午8:05:34
 */
public interface UserDao extends JpaRepository<User, Integer> {

	User findByEmail(String email);

	@Modifying
	@Query("update User set password=?2 where id=?1")
	void updateUserPassword(Integer id, String password);

}
