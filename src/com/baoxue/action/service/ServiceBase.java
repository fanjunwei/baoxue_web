package com.baoxue.action.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.json.annotations.JSONParameter;
import org.hibernate.Query;
import org.hibernate.Session;

import com.baoxue.common.ActionBase;
import com.baoxue.common.Helper;
import com.baoxue.db.TPackageUpdate;

public abstract class ServiceBase extends ActionBase {

	
	private int version;

	@JSON(serialize = false)
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	protected List<String> values;

	public InputStream getResult() {
		try {
			final PipedInputStream input = new PipedInputStream();

			final PipedOutputStream out = new PipedOutputStream(input);

			new Thread() {
				@Override
				public void run() {
					try {
						if (values != null) {
							byte[] countbyte = Helper.intToByte(values.size());
							out.write(countbyte);
							for (String item : values) {
								if (item != null) {
									byte[] itembyte = item.getBytes("utf-8");
									byte[] lenbyte = Helper
											.intToByte((int) itembyte.length);
									out.write(lenbyte);
									out.write(itembyte);
								} else {
									byte[] lenbyte = Helper.intToByte((int) 0);
									out.write(lenbyte);
								}
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

	@Override
	public String execute() throws Exception {
		values = new ArrayList<String>();
		values.add("sfsf");

		JSONArray jsonarray = JSONArray.fromObject(new String[] {
				"[sdf\"\nsdf]", "sdf,sdf" });

		System.out.println(jsonarray.toString());

		return SUCCESS;
	}

}
