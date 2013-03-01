package com.baoxue.common;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class DbSessionInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		String result = invocation.invoke();
		//HibernateSessionFactory.closeAllSession();
		return result;

	}
}