package com.baoxue.common;

import java.security.MessageDigest;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.struts2.ServletActionContext;

public class Helper {

	public final static String getMD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isPost() {
		String method = ServletActionContext.getRequest().getMethod();
		if (method.equals("GET")) {
			return false;
		} else {
			return true;
		}
	}

	public static String getString(String key) {
		Locale locale = Locale.getDefault();
		ResourceBundle rb = ResourceBundle.getBundle("resource", locale);
		return rb.getString(key);

	}
}
