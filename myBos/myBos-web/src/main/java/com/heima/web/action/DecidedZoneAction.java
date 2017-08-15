package com.heima.web.action;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import com.heima.domain.Customer;
import com.heima.domain.DecidedZone;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月9日 下午8:46:43
 */

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("default")
@SuppressWarnings("unchecked")
public class DecidedZoneAction extends BaseAction<DecidedZone> {

	private static final long serialVersionUID = 8210132296812973431L;

	private final String crmURL = ResourceBundle.getBundle("webserviceURL").getString("crmURL");

	@Action("decidedzone_getPage")
	public String getPage() throws Exception {
		String hasDecidedzone = getParameter("hasDecidedzone");
		Page page = getService().getDecidedZoneService().getPage(getModel(), getPageRequest(), hasDecidedzone);
		push(getPageMap(page));
		return "toJson";
	}

	@Action("decidedZone_checkId")
	public String checkId() throws Exception {
		String oldId = getParameter("oldId");
		String newId = getParameter("newId");
		boolean flag = getService().getDecidedZoneService().checkId(oldId, newId);
		push(flag);
		return "toJson";
	}

	@Action("decidedZone_updateDecidedZone")
	public String updateDecidedZone() throws Exception {
		String[] subareaIds = getParameterValue("subareaId");
		getService().getDecidedZoneService().updateDecidedZone(subareaIds, getModel());
		return NONE;
	}

	@Action(value = "decidedzone_getAssociatedCust", results = @Result(name = "getAssociatedCust", type = "fastJson", params = {
			"includeProperties", "id,name,station" }))
	public String getAssociatedCust() throws Exception {
		List<Customer> list = (List<Customer>) WebClient.create(crmURL + "associated/" + getModel().getId())
				.accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
		push(list);
		return "getAssociatedCust";
	}

	@Action(value = "decidedzone_getNoAssociatedCust", results = @Result(name = "getNoAssociatedCust", type = "fastJson", params = {
			"includeProperties", "id,name" }))
	public String getNoAssociatedCust() throws Exception {
		List<Customer> list = (List<Customer>) WebClient.create(crmURL + "noAssociated")
				.accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
		push(list);
		return "getNoAssociatedCust";
	}

	@Action(value = "decidedzone_setCustomersToDecidedzone")
	public String setCustomersToDecidedzone() throws Exception {
		String[] customerIds = getParameterValue("customerIds");
		StringBuilder sb = new StringBuilder(crmURL);
		sb.append("associate/");
		if (customerIds == null || customerIds.length == 0) {
			sb.append("none");
		} else {
			String temp = Arrays.toString(customerIds);
			sb.append(temp.substring(1, temp.length() - 1));
		}
		sb.append("/" + getModel().getId());
		System.out.println(sb);
		WebClient.create(sb.toString()).put(null);
		return NONE;
	}

}
