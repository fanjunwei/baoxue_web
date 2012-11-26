package com.baoxue.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;

import com.opensymphony.xwork2.ActionSupport;

public abstract class ActionBase extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5104992752310358012L;

	protected HttpSession getSession() {

		return ServletActionContext.getRequest().getSession();
	}

	protected HttpServletResponse getResponse() {

		return ServletActionContext.getResponse();
	}

	protected HttpServletRequest getRequest() {

		return ServletActionContext.getRequest();
	}

	protected boolean isPost() {
		String method = ServletActionContext.getRequest().getMethod();
		if (method.equals("GET")) {
			return false;
		} else {
			return true;
		}
	}

	protected String getRealPath(String url) {

		return ServletActionContext.getServletContext().getRealPath(url);
	}

	protected Session getDBSession() {
		return HibernateSessionFactory.getSession();
	}
	
	protected String getBaseUrl()
	{
		String baseurl = getRequest().getScheme() + "://"
				+ getRequest().getServerName() + ":"
				+ getRequest().getServerPort() + getRequest().getContextPath();
		return baseurl;
	}
}
