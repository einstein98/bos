package com.heima.web.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import com.heima.domain.DecidedZone;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月9日 下午8:46:43
 */

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("default")
public class DecidedZoneAction extends BaseAction<DecidedZone> {

	private static final long serialVersionUID = 8210132296812973431L;

	@Action("decidedzone_getPage")
	public String getPage() throws Exception {
		Page page = getService().getDecidedZoneService().getPage(getModel(), getPageRequest());
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

}
