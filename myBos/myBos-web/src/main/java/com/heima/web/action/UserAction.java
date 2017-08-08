package com.heima.web.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.heima.Exception.UserLoginException;
import com.heima.domain.User;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月7日 下午7:59:38
 */

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("default")
public class UserAction extends BaseAction<User> {

	private static final long serialVersionUID = -1924437703786779062L;

	@Action("user_checkcode")
	public String checkcode() throws Exception {
		String generate_code = (String) getSessionAttr("key");
		String input_code = getParameter("checkcode");
		if (StringUtils.isNotBlank(input_code)) {
			if (input_code.equalsIgnoreCase(generate_code)) {
				push(true);
				return "toJson";
			}
		}
		push(false);
		return "toJson";
	}

	@Action("user_changePassword")
	public String changePassword() throws Exception {
		User user = (User) getSessionAttr("current_user");
		if (user != null) {
			getService().getUserService().changePassword(user, getModel().getPassword());
		}

		return NONE;
	}

	@Action(value = "user_login", results = {
			@Result(name = "login_success", type = "redirect", location = "/index.jsp"),
			@Result(name = "login_error", type = "dispatcher", location = "/login.jsp") })
	public String login() throws Exception {
		User user = null;
		try {
			user = getService().getUserService().login(getModel());
		} catch (UserLoginException e) {
			addActionError(getText(e.getMessage()));
			return "login_error";
		}
		if (user != null) {
			setSessionAttr("current_user", user);
			return "login_success";
		}
		return "login_error";

	}

	@Action(value = "user_logout", results = {
			@Result(name = "user_logout", type = "redirect", location = "/login.jsp"), })
	public String logout() throws Exception {
		removeSessionAttr("current_user");
		return "user_logout";

	}

}
