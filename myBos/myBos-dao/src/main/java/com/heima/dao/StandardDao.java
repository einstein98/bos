package com.heima.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.heima.domain.Standard;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月3日 下午8:49:54
 */
public interface StandardDao extends JpaRepository<Standard, Integer> {
	@Query("update Standard set deltag=0 where id=?1")
	@Modifying
	void logicDelete(int id);

	@Query("update Standard set deltag=1 where id=?1")
	@Modifying
	void batchRevert(int parseInt);

	@Query("from Standard where deltag=1")
	List<Standard> getStandardList();

}
