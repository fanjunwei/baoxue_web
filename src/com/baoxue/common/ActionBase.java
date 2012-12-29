package com.baoxue.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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

	protected Object result;

	public InputStream getResult() {
		try {
			final PipedInputStream input = new PipedInputStream();

			final PipedOutputStream out = new PipedOutputStream(input);

			new Thread() {
				@Override
				public void run() {
					try {
						if (result != null) {
							String json = null;
							if (isArray(result) || isCollection(result)) {
								JSONArray jsonarray = JSONArray
										.fromObject(result);
								json = jsonarray.toString();
							} else {
								JSONObject jsonobject = JSONObject
										.fromObject(result);
								json = jsonobject.toString();
							}
							if (json != null) {
								out.write(json.getBytes("utf-8"));
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (out != null)
							try {
								out.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
					}
				}
			}.start();
			return input;
		} catch (IOException e) {
			return null;
		}
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

	protected String getBaseUrl() {
		String baseurl = getRequest().getScheme() + "://"
				+ getRequest().getServerName() + ":"
				+ getRequest().getServerPort() + getRequest().getContextPath();
		return baseurl;
	}

	public static boolean isArray(Object obj) {
		return obj != null && obj.getClass().isArray();
	}

	public static boolean isCollection(Object obj) {
		return obj != null && Collection.class.isAssignableFrom(obj.getClass());
	}

}
