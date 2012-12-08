package com.baoxue.action.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.json.annotations.JSON;

import com.baoxue.common.ActionBase;

public abstract class ServiceBase extends ActionBase {

	private String deviceId;

	private int version;
	private String deviceVersion;

	public String getDeviceVersion() {
		return deviceVersion;
	}

	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}

	@JSON(serialize = false)
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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

	public static boolean isArray(Object obj) {
		return obj != null && obj.getClass().isArray();
	}

	public static boolean isCollection(Object obj) {
		return obj != null && Collection.class.isAssignableFrom(obj.getClass());
	}

}
