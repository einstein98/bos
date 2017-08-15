package com.heima.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.heima.domain.qp.Order;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月14日 下午10:39:26
 */
@Produces("*/*")
public interface OrderService {
	@POST
	@Consumes({ "application/json", "application/xml" })
	@Path("/order/save")
	public void saveOrder(Order order);
}
