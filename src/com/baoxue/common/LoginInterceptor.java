package com.baoxue.common;

import com.baoxue.annotation.CheckLogin;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		ActionBase baseAction = (ActionBase) invocation.getAction();

		System.out.println(baseAction.getClass().getName());
		System.out.println(invocation.getInvocationContext().getName());
		try {
			Class<? extends ActionBase> c = baseAction.getClass();
			if (c.isAnnotationPresent(CheckLogin.class)) {
				CheckLogin cl = c.getAnnotation(CheckLogin.class);
				if (!cl.check()) {
					return invocation.invoke();
				}
			}
		} catch (Exception e) {
		}

		int LOGIN_TYPE = invocation.getInvocationContext().getSession()
				.get("LOGIN_TYPE") == null ? 0 : (Integer) invocation
				.getInvocationContext().getSession().get("LOGIN_TYPE");
		if (LOGIN_TYPE != 1) {
			return Action.LOGIN;
		} else {
			return invocation.invoke();
		}

	}
}