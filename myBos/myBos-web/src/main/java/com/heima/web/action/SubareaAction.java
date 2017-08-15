package com.heima.web.action;

import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.heima.domain.Region;
import com.heima.domain.Subarea;
import com.heima.utils.DownloadUtils;

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
		String pageInJson = getService().getSubareaService().getPage(getModel(), getPageRequest());
		writeJsonOut(pageInJson);
		return NONE;
	}

	@Action("subarea_getSubareaByDecidedzoneId")
	public String getSubareaByDecidedzoneId() throws Exception {
		String pageInJson = getService().getSubareaService().getSubareaByDecidedzoneId(getModel());
		writeJsonOut(pageInJson);
		return NONE;
	}

	@Action("subarea_export")
	public String export() throws Exception {
		HSSFWorkbook book = getService().getSubareaService().export(getModel());
		HttpServletResponse response = getResponse();
		response.setContentType(ServletActionContext.getServletContext().getMimeType(".xls"));
		response.setHeader("Content-Disposition", "attachment;filename="
				+ DownloadUtils.encodeDownloadFilename("分区表.xls", getRequest().getHeader("user-agent")));
		ServletOutputStream outputStream = response.getOutputStream();
		book.write(outputStream);
		outputStream.close();
		return NONE;
	}

	@Action(value = "subarea_getSubareaList", results = @Result(name = "toJson", type = "fastJson", params = {
			"includeProperties", "subareaId,addresskey,position" }))
	public String getSubareaList() throws Exception {
		List<Subarea> list = getService().getSubareaService().getSubareaList();
		push(list);
		return "toJson";
	}

}
