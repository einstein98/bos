package com.heima.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.heima.domain.DecidedZone;
import com.heima.domain.Subarea;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月8日 下午1:27:00
 */
public interface SubareaDao extends JpaRepository<Subarea, String>, JpaSpecificationExecutor<Subarea> {
	@Query("from Subarea where decidedZone = null")
	List<Subarea> getSubareaList();

	@Modifying
	@Query("update Subarea set decidedZone=?2 where id=?1")
	void addRegion(String subareaId, DecidedZone model);

}
