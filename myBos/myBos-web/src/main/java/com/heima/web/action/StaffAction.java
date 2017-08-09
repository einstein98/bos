package com.heima.web.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;

import com.heima.domain.Staff;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月8日 下午1:16:21
 */

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("default")
public class StaffAction extends BaseAction<Staff> {

	private static final long serialVersionUID = 4499864695727519477L;

	@Action("staff_updateStaff")
	public String updateStaff() throws Exception {
		Staff staff = getModel();
		getService().getStaffService().updateStaff(getModel());
		return NONE;
	}

	@Action("staff_checkTel")
	public String checkTel() throws Exception {
		Staff staff = getModel();
		boolean flag = getService().getStaffService().checkTel(getModel());
		push(flag);
		return "toJson";
	}

	@Action("staff_getPage")
	public String getPage() throws Exception {
		Order deltag = new Order(Direction.DESC, "deltag");
		Order id = new Order(Direction.DESC, "id");
		Page pageData = getService().getStaffService().getPage(getPageRequest(deltag, id), params);
		push(getPageMap(pageData));
		return "toJson";
	}

	@Action(value = "staff_batchDelete")
	public String batchDelete() throws Exception {
		String ids = getParameter("ids");
		getService().getStaffService().batchDelete(ids);
		return NONE;
	}

	@Action(value = "staff_batchRevert")
	public String batchRevert() throws Exception {
		String ids = getParameter("ids");
		getService().getStaffService().batchRevert(ids);
		return NONE;
	}

}
