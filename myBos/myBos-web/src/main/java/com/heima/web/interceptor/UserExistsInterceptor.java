package com.heima.web.interceptor;

import org.springframework.stereotype.Controller;

import com.heima.domain.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月3日 上午9:26:51
 */
@Controller("userExistsInterceptor")
public class UserExistsInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = 5450887239914169983L;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		User user = (User) invocation.getInvocationContext().getSession().get("current_user");
		if (user == null) {
			ActionSupport action = (ActionSupport) invocation.getAction();
			action.addActionError(action.getText("no_user_exist"));
			return "no_user_exist";
		}
		return invocation.invoke();

	}

}
