package com.heima.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.heima.domain.Region;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月6日 上午11:45:10
 */
public interface RegionDao extends JpaRepository<Region, String>, JpaSpecificationExecutor<Region> {

	Region findByPostcode(String postcode);

}
