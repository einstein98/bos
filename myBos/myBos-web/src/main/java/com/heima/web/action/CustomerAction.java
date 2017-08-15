package com.heima.web.action;

import java.util.ResourceBundle;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.heima.domain.Customer;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月15日 上午9:45:14
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("default")
public class CustomerAction extends BaseAction<Customer> {

	private static final long serialVersionUID = -1651319327826564915L;
	private final String CRM_URL = ResourceBundle.getBundle("webserviceURL").getString("crmURL");
	private final String GET_BY_TEL = "getCustomerByTel/";

	@Action(value = "customer_getCustomerByTel", results = @Result(name = "getCustomerByTel", type = "fastJson", params = {
			"includeProperties", "id,name" }))
	public String getCustomerByTel() throws Exception {
		Customer customer = WebClient.create(CRM_URL + GET_BY_TEL + getModel().getTelephone())
				.accept(MediaType.APPLICATION_JSON).get(Customer.class);
		push(customer);
		return "getCustomerByTel";
	}

}
