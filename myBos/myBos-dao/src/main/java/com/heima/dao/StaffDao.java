package com.heima.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.heima.domain.Staff;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月4日 下午6:34:59
 */
public interface StaffDao extends JpaRepository<Staff, String>, JpaSpecificationExecutor<Staff> {
	@Modifying
	@Query("update Staff set deltag=1 where id=?1")
	void logicDelete(String id);

	@Modifying
	@Query("update Staff set deltag=0 where id=?1")
	void logicRevert(String id);

	@Query("from Staff where telephone=?1")
	Staff getStaffByTel(String input_tel);

}
