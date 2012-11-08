package com.baoxue.action;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.baoxue.annotation.CheckLogin;
import com.baoxue.common.ActionBase;

@CheckLogin(check = false)
public class Weather extends ActionBase {

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public String weatherVersion() {
		return SUCCESS;
	}

	public InputStream getVersionXML() {
		String baseurl = getRequest().getScheme() + "://"
				+ getRequest().getServerName() + ":"
				+ getRequest().getServerPort() + getRequest().getContextPath();
		System.out.println(baseurl);

		String aapt = getRealPath("/tools/aapt");
		System.out.println(aapt);

		String apk = getRealPath("/apk/weather.apk");
		System.out.println(apk);
		String apkurl = baseurl + "/apk/weather.apk";
		String version = getVersion(aapt, apk);
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		xml += "<Version>\n";
		xml += "<Url>" + apkurl + "</Url>\n";
		xml += "<VersionName>" + version + "</VersionName>\n";
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

	private String getVersion(String aapt, String apk) {

		System.out.println("getVersion");
		File f1 = new File(aapt);
		File f2 = new File(apk);
		if (f1.exists() && f2.exists()) {
			System.out.println("file exist");
			String[] prog1 = new String[] { "chmod", "a+x", aapt };
			String[] prog = new String[] { aapt, "d", "badging", apk };
			// StringBuffer sb = new StringBuffer();
			try {
				Process pros1 = Runtime.getRuntime().exec(prog1);
				pros1.waitFor();
				Process pros = Runtime.getRuntime().exec(prog);
				String str = null;
				BufferedReader stdin = new BufferedReader(
						new InputStreamReader(pros.getInputStream()));
				// while ((str = stdin.readLine()) != null) {
				// sb.append(str + "\n");
				// }
				// System.out.println(sb.toString());
				// versionName='1.4'
				if ((str = stdin.readLine()) != null) {
					System.out.println("line1=" + str);
					System.out.println(str.toString());
					Pattern pattern = Pattern.compile("versionName='(.*?)'");
					Matcher matcher = pattern.matcher(str);
					if (matcher.find()) {
						String vn = matcher.group(1);
						System.out.println(vn);
						return vn;
					}
				}

			} catch (IOException e) {

				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("default version");
		return "1.0";
	}
}
