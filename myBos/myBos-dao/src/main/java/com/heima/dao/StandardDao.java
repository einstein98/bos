package com.heima.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.heima.domain.Standard;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月8日 上午9:44:20
 */
public interface StandardDao extends JpaRepository<Standard, Integer> {
	@Query("update Standard set deltag=0 where id=?1")
	@Modifying
	void logicDelete(int i);

	@Query("update Standard set deltag=1 where id=?1")
	@Modifying
	void logicRevert(int parseInt);

}
