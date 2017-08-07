package com.heima.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heima.service.RegionService;
import com.heima.service.StaffService;
import com.heima.service.StandardService;
import com.heima.service.UserService;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月2日 下午7:25:07
 */
@Service
public class FacadeService {

	private UserService userService;
	private StandardService standardService;
	private StaffService staffService;
	private RegionService regionService;

	public RegionService getRegionService() {
		return regionService;
	}

	@Autowired
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public StaffService getStaffService() {
		return staffService;
	}

	@Autowired
	public void setStaffService(StaffService staffService) {
		this.staffService = staffService;
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
