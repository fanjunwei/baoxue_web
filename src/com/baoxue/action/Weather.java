package com.baoxue.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.InputBuffer;
import org.hibernate.Query;
import org.hibernate.Session;
import org.omg.CORBA.Principal;

import com.baoxue.annotation.CheckLogin;
import com.baoxue.common.ActionBase;
import com.baoxue.common.Helper;
import com.baoxue.common.HibernateSessionFactory;
import com.baoxue.db.TUsers;

@CheckLogin(check = false)
public class Weather extends ActionBase {

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public String eatherVersion() {
		return SUCCESS;
	}

	public InputStream getVersionXML() {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		xml += "<Version>\n";
		xml += "<Url>http://setup.3533.com/weather1.4.apk</Url>\n";
		xml += "<VersionName>1.4</VersionName>\n";
		xml += "</Version>\n";

		byte[] bytes = null;
		try {
			bytes = xml.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {

		}
		if (bytes != null) {
			InputStream input = new ByteArrayInputStream(bytes);

			return input;
		} else {
			return null;
		}
	}
}
