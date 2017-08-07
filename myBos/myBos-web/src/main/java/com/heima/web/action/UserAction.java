package com.heima.web.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.heima.domain.User;
import com.heima.exception.UserLoginException;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月2日 下午9:16:20
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("default")
public class UserAction extends BaseAction<User> {

	private static final long serialVersionUID = -1924437703786779062L;

	@Action(value = "user_checkcode")
	public String checkcode() throws Exception {
		String sessionCode = (String) getSessionAttr("key");
		String inputCode = getParameter("checkcode");
		if (inputCode.equalsIgnoreCase(sessionCode)) {
			returnJSON(true);
		} else {
			returnJSON(false);
		}
		return NONE;
	}

	@Action(value = "user_login", results = {
			@Result(name = "login_success", type = "redirect", location = "/index.jsp"),
			@Result(name = "login_failed", type = "dispatcher", location = "/login.jsp") })
	public String login() throws Exception {
		if (!"POST".equals(getRequest().getMethod())) {
			addActionError(getText("login_illegal"));
			return "login_failed";
		} else {
			try {
				User user = service.getUserService().login(getModel());
				setSessionAttr("current_user", user);
			} catch (UserLoginException e) {
				String text = getText(e.getMessage());
				addActionError(text);
				return "login_failed";
			}
			return "login_success";
		}
	}

	@Action(value = "user_logout", results = @Result(name = "logout", type = "redirect", location = "/login.jsp"))
	public String logout() throws Exception {
		removeSessionAttr("current_user");
		return "logout";
	}

	@Action(value = "user_updatePassword")
	public String updatePassword() throws Exception {
		service.getUserService().changePassword(getModel());
		return NONE;
	}
}
