package com.heima.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.heima.domain.Region;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月8日 下午1:23:34
 */
public interface RegionDao extends JpaRepository<Region, String>, JpaSpecificationExecutor<Region> {

	Region findByPostcode(String postcode);

	@Query("from Region where city like ?1 or province like ?1 or district like ?1")
	List<Region> findByParam(String param);

}
