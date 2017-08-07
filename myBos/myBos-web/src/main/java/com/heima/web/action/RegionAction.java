package com.heima.web.action;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import com.heima.domain.Region;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月6日 上午11:43:54
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("default")
public class RegionAction extends BaseAction<Region> {

	private static final long serialVersionUID = 7431090023606170359L;

	private File file;

	public void setFile(File file) {
		this.file = file;
	}

	@Action("region_regionUpload")
	public String regionUpload() throws Exception {
		try {
			service.getRegionService().saveRegion(file);
			returnJSON(true);
		} catch (Exception e) {
			e.printStackTrace();
			returnJSON(false);
		}
		return NONE;
	}

	@Action("region_checkId")
	public String checkId() throws Exception {
		String newId = getParameter("code");
		String usedId = getParameter("id");
		if (StringUtils.isNotBlank(usedId)) {
			if (usedId.equals(newId)) {
				returnJSON(true);
				return NONE;
			}
		}
		returnJSON(service.getRegionService().checkId(newId));
		return NONE;
	}

	@Action("region_checkPostcode")
	public String checkPostcode() throws Exception {
		String postcode = getParameter("code");
		String id = getParameter("id");
		returnJSON(service.getRegionService().checkPostCode(postcode, id));
		return NONE;
	}

	@Action("region_regionPage")
	public String regionPage() throws Exception {
		Page<Region> page = service.getRegionService().getRegionPage(getPageRequest(), params);
		setPageData(page);
		returnJSON(getPageMap());
		return NONE;
	}

}
