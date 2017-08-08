package com.heima.web.interceptor;

import com.heima.domain.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月7日 下午10:20:12
 */
public class UserExistsInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = 5450887239914169983L;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		ActionContext context = invocation.getInvocationContext();
		User user = (User) context.getSession().get("current_user");
		if (user == null) {
			ActionSupport action = (ActionSupport) invocation.getAction();
			action.addActionError(action.getText("no_user_exists"));
			return "no_user";
		}
		return invocation.invoke();
	}

}
