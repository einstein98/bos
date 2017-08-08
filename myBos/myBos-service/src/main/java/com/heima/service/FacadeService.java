package com.heima.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月7日 下午8:00:34
 */
@Service
public class FacadeService {

	private UserService userService;
	private StandardService standardService;

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
