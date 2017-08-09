package com.heima.web.action;

import java.io.File;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.heima.domain.Region;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月8日 下午3:59:36
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("default")
public class RegionAction extends BaseAction<Region> {

	private static final long serialVersionUID = 7431090023606170359L;
	private File upload;

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	@Action("region_getPage")
	public String getPage() throws Exception {
		String pageInJson = getService().getRegionService().getPage(getPageRequest(), params);
		writeJsonOut(pageInJson);
		return NONE;
	}

	@Action("region_getRegionList")
	public String getRegionList() throws Exception {
		String param = getParameter("q");
		String listJSON = getService().getRegionService().getRegionList(param);
		writeJsonOut(listJSON);
		return NONE;
	}

	@Action("region_checkId")
	public String checkId() throws Exception {
		String newId = getParameter("code");
		boolean flag = getService().getRegionService().checkId(newId, getModel().getId());
		push(flag);
		return "toJson";
	}

	@Action("region_checkPostcode")
	public String checkPostcode() throws Exception {
		String postcode = getParameter("code");
		boolean flag = getService().getRegionService().checkPostcode(postcode, getModel().getId());
		push(flag);
		return "toJson";
	}

	@Action("region_updateRegion")
	public String updateRegion() throws Exception {
		getService().getRegionService().updateRegion(getModel());
		return NONE;
	}

	@Action("region_upload")
	public String upload() throws Exception {
		try {
			getService().getRegionService().upload(upload);
			push(true);
		} catch (Exception e) {
			e.printStackTrace();
			push(false);
		}
		return "toJson";
	}

}
