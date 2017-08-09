package com.heima.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.heima.domain.DecidedZone;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月9日 下午8:49:06
 */
public interface DecidedZoneDao extends JpaRepository<DecidedZone, String>, JpaSpecificationExecutor<DecidedZone> {

}
