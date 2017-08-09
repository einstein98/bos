package com.heima.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.heima.domain.Subarea;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月8日 下午1:27:00
 */
public interface SubareaDao extends JpaRepository<Subarea, String>, JpaSpecificationExecutor<Subarea> {

}
