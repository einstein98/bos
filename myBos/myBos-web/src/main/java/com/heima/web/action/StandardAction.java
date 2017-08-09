package com.heima.web.action;

import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;

import com.heima.domain.Standard;
import com.heima.domain.User;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月8日 上午9:32:47
 */

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("default")
public class StandardAction extends BaseAction<Standard> {

	private static final long serialVersionUID = 2859881689001355168L;

	@Action("standard_updateStandard")
	public String updateStandard() throws Exception {
		Standard standard = getModel();
		User user = (User) getSessionAttr("current_user");
		standard.setOperator(user.getEmail());
		standard.setOperatorcompany(user.getStation());
		standard.setOperationtime(new Date());
		getService().getStandardService().updateStandard(getModel());
		return NONE;
	}

	@Action(value = "standard_getPage")
	public String getPage() throws Exception {
		Order deltag = new Order(Direction.DESC, "deltag");
		Order id = new Order(Direction.ASC, "id");
		Page pageData = getService().getStandardService().getPage(getPageRequest(deltag, id));
		System.out.println(pageData.getContent());
		push(getPageMap(pageData));
		return "toJson";
	}

	@Action(value = "standard_batchDelete")
	public String batchDelete() throws Exception {
		String ids = getParameter("ids");
		getService().getStandardService().batchDelete(ids);
		return NONE;
	}

	@Action(value = "standard_batchRevert")
	public String batchRevert() throws Exception {
		String ids = getParameter("ids");
		getService().getStandardService().batchRevert(ids);
		return NONE;
	}

	@Action(value = "standard_standardList", results = @Result(name = "standardList", type = "fastJson", params = {
			"includeProperties", "name" }))
	public String standardList() throws Exception {
		List<Standard> list = getService().getStandardService().standardList();
		push(list);
		return "standardList";
	}

}
