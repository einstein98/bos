package com.heima.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.heima.domain.qp.Order;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月14日 下午10:42:21
 */
public interface OrderDao extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {

}
