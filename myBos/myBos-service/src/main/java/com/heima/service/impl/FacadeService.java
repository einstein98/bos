package com.heima.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heima.service.RegionService;
import com.heima.service.StaffService;
import com.heima.service.StandardService;
import com.heima.service.SubareaService;
import com.heima.service.UserService;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月7日 下午8:00:34
 */
@Service
public class FacadeService {

	private UserService userService;
	private StandardService standardService;
	private StaffService staffService;
	private RegionService regionService;
	private SubareaService subarea;

	public StaffService getStaffService() {
		return staffService;
	}

	public RegionService getRegionService() {
		return regionService;
	}

	public SubareaService getSubareaService() {
		return subarea;
	}

	@Autowired
	public void setStaffService(StaffService staffService) {
		this.staffService = staffService;
	}

	@Autowired
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	@Autowired
	public void setSubareaService(SubareaService subarea) {
		this.subarea = subarea;
	}

	public StandardService getStandardService() {
		return standardService;
	}

	@Autowired
	public void setStandardService(StandardService standardService) {
		this.standardService = standardService;
	}

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
