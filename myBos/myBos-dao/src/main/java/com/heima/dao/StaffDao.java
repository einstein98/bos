package com.heima.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.heima.domain.Staff;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月8日 下午1:21:20
 */
public interface StaffDao extends JpaRepository<Staff, String>, JpaSpecificationExecutor<Staff> {

	Staff findByTelephone(String telephone);

	@Modifying
	@Query("update Staff set deltag=1 where id = ?1")
	void logicRevert(String id);

	@Modifying
	@Query("update Staff set deltag=0 where id = ?1")
	void logicDelete(String id);

	@Query("from Staff where deltag=1")
	List<Staff> getStaffList();

}
