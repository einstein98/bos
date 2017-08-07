package com.heima.web.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;

import com.heima.domain.Standard;
import com.heima.domain.User;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月3日 下午8:25:07
 */
@Controller
@Scope("prototype")
@ParentPackage("default")
@Namespace("/")
public class StandardAction extends BaseAction<Standard> {

	private static final long serialVersionUID = 2859881689001355168L;

	// 保存后，弹窗close，iframe向standard.jsp跳转，跳转后页面自动发送ajax请求，获取后台数据库数据交个datagrid显示
	@Action(value = "standard_save")
	public String save() throws Exception {
		Standard standard = getModel();

		User user = (User) getSessionAttr("current_user");
		standard.setOperationtime(new Date());
		standard.setOperator(user.getEmail());
		standard.setOperatorcompany(user.getStation());

		service.getStandardService().save(standard);
		return NONE;
	}

	@Action(value = "standard_findPage")
	public String findPage() throws Exception {
		Order deltag = new Order(Direction.DESC, "deltag");
		Order id = new Order(Direction.ASC, "id");
		Page<Standard> page = service.getStandardService().findPage(getPageRequest(deltag, id));
		setPageData(page);
		returnJSON(getPageMap());
		return NONE;
	}

	@Action(value = "standard_batchDelete")
	public String batchDelete() throws Exception {
		String ids = getParameter("ids");
		if (StringUtils.isNotBlank(ids)) {
			service.getStandardService().batchDelete(ids);
		}
		return NONE;
	}

	@Action(value = "standard_revertBatch")
	public String revertBatch() throws Exception {
		String ids = getParameter("ids");
		if (StringUtils.isNotBlank(ids)) {
			service.getStandardService().batchRevert(ids);
		}
		return NONE;
	}

	@Action(value = "standard_getStandardList")
	public String getStandardList() throws Exception {
		List<Standard> list = service.getStandardService().getStandardList();
		setIncludeProperties("name");
		returnJSON(list);
		return NONE;
	}

}
