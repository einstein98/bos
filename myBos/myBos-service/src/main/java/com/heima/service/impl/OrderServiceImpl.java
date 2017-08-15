package com.heima.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.heima.dao.OrderDao;
import com.heima.domain.qp.Order;
import com.heima.service.OrderService;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月14日 下午10:39:54
 */
@Transactional
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDao orderDao;

	@Override
	public void saveOrder(Order order) {
		System.out.println(order);
	}
}
