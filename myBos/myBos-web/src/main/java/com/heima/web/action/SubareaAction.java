package com.heima.web.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.heima.domain.Region;
import com.heima.domain.Subarea;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月9日 上午8:19:17
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("default")
public class SubareaAction extends BaseAction<Subarea> {

	private static final long serialVersionUID = 2080675872182601985L;

	@Action("subarea_checkId")
	public String checkId() throws Exception {
		String oldId = getParameter("oldId");
		boolean flag = getService().getSubareaService().checkId(oldId, getModel().getId());
		push(flag);
		return "toJson";
	}

	@Action("subarea_updateSubarea")
	public String updateSubarea() throws Exception {
		String regionId = getParameter("region[id]");
		if (StringUtils.isNotBlank(regionId)) {
			Region r = new Region();
			r.setId(regionId);
			getModel().setRegion(r);
		}
		getService().getSubareaService().updateSubarea(getModel());
		return NONE;
	}

	@Action("subarea_getPage")
	public String getPage() throws Exception {
		String pageInJson = getService().getSubareaService().getPage(params, getPageRequest());
		writeJsonOut(pageInJson);
		return NONE;
	}

}
