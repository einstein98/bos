package com.heima.web.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;

import com.heima.domain.Staff;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月4日 下午6:25:24
 */

@Controller
@Scope("prototype")
@ParentPackage("default")
@Namespace("/")
public class StaffAction extends BaseAction<Staff> {

	private static final long serialVersionUID = 4499864695727519477L;

	@Action(value = "staff_findPage")
	public String findPage() throws Exception {
		Order deltag = new Order(Direction.ASC, "deltag");
		Order id = new Order(Direction.ASC, "id");

		Page<Staff> pageData = service.getStaffService().findPage(getPageRequest(deltag, id), params);
		setPageData(pageData);
		returnJSON(getPageMap());
		return NONE;
	}

	@Action(value = "staff_checkPhone", results = @Result(name = "checkPhone", type = "json"))
	public String checkPhone() throws Exception {

		boolean flag = service.getStaffService().checkPhone(getModel());
		push(flag);
		return "checkPhone";
	}

	@Action("staff_updateStaff")
	public String updateStaff() throws Exception {
		service.getStaffService().updateStaff(getModel());
		return NONE;
	}

	@Action("staff_batchDelete")
	public String batchDelete() throws Exception {
		String ids = getParameter("ids");
		service.getStaffService().batchDelete(ids);
		return NONE;
	}

	@Action("staff_batchRevert")
	public String batchRevert() throws Exception {
		String ids = getParameter("ids");
		service.getStaffService().batchRevert(ids);
		return NONE;
	}
}
