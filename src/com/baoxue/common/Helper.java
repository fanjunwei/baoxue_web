package com.baoxue.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.struts2.ServletActionContext;

public class Helper {

	public static int FourBytesToInt(byte Bytes[]) {
		return FourBytesToInt(Bytes, 0);
	}

	public static int FourBytesToInt(byte Bytes[], int startIndex) {
		int a = Bytes[startIndex] & 0xff;
		int b = Bytes[startIndex + 1] & 0xff;
		int c = Bytes[startIndex + 2] & 0xff;
		int d = Bytes[startIndex + 3] & 0xff;
		return a | b << 8 | c << 16 | d << 24;
	}

	public static byte[] intToByte(int i) {
		byte[] result = new byte[4];
		result[0] = (byte) (i & 0xff);
		result[1] = (byte) ((i & 0xff00) >> 8);
		result[2] = (byte) ((i & 0xff0000) >> 16);
		result[3] = (byte) ((i & 0xff000000) >> 24);
		return result;

	}

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

	public static boolean Copy(File src, File des) {
		if (src == null || des == null) {
			System.out.println("参数不能为空");
			return false;
		}
		if (!src.exists()) {
			System.out.println("原文件不存在");
			return false;
		}

		if (!src.canRead()) {
			System.out.println("原文件无权限读取");
			return false;
		}
		if (!des.getParentFile().exists() || !des.getParentFile().canWrite()) {
			System.out.println("目标目录无法写入");
			return false;
		}

		if (des.exists()) {
			if (!des.canWrite()) {
				System.out.println("目标文件存在但无法写入");
				return false;
			} else {
				des.delete();
			}
		}

		FileOutputStream output = null;
		FileInputStream input = null;
		try {
			output = new FileOutputStream(des);
			input = new FileInputStream(src);
			byte[] buffer = new byte[1024];
			int b = 0;

			while ((b = input.read(buffer)) >= 0) {
				output.write(buffer, 0, b);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (input != null)
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			if (output != null)
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
		}

		System.out.println("success copy to " + des.getAbsolutePath());
		return true;
	}

	public static ApkInfo getApkInfo(String apk) {

		ApkInfo info = null;
		Properties props = System.getProperties();
		String osName = props.getProperty("os.name");
		String aaptName;
		if (osName != null && osName.equals("Mac OS X")) {
			aaptName = "aapt_mac";
		} else {
			aaptName = "aapt_linux";
		}
		System.out.println(osName);
		String aapt = ServletActionContext.getServletContext().getRealPath(
				"/tools/" + aaptName);
		File f1 = new File(aapt);
		File f2 = new File(apk);
		if (f1.exists() && f2.exists()) {
			String[] prog1 = new String[] { "chmod", "a+x", aapt };
			String[] prog = new String[] { aapt, "d", "badging", apk };

			try {
				Process pros1 = Runtime.getRuntime().exec(prog1);
				pros1.waitFor();
				Process pros = Runtime.getRuntime().exec(prog);
				String str = null;
				BufferedReader stdin = null;
				BufferedReader stderr = null;
				try {
					stdin = new BufferedReader(new InputStreamReader(
							pros.getInputStream()));

					stderr = new BufferedReader(new InputStreamReader(
							pros.getErrorStream()));
					while ((str = stderr.readLine()) != null) {
						System.out.println(str);
					}
					if ((str = stdin.readLine()) != null) {
						info = new ApkInfo();
						Matcher matcherVersionName = Pattern.compile(
								"versionName='(.*?)'").matcher(str);
						if (matcherVersionName.find()) {
							String versionName = matcherVersionName.group(1);
							info.setVersionName(versionName);
						}

						Matcher matcherPackageName = Pattern.compile(
								"package: name='(.*?)'").matcher(str);
						if (matcherPackageName.find()) {
							String packageName = matcherPackageName.group(1);
							info.setPackageName(packageName);
						}

						Matcher matcherVersionCode = Pattern.compile(
								"versionCode='(.*?)'").matcher(str);
						if (matcherVersionCode.find()) {
							String versionCode = matcherVersionCode.group(1);
							info.setVersionCode(Integer.parseInt(versionCode));
						}
					}
				} finally {
					if (stderr != null)
						stderr.close();
					if (stdin != null)
						stdin.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} catch (InterruptedException e) {

				e.printStackTrace();
				return null;
			}
		}
		return info;
	}

	public static String formatLength(long size) {

		if (size > 1024 * 1024 * 1024) {
			return ((float) size) / (1024 * 1024 * 1024) + "GB";
		} else if (size > 1024 * 1024) {
			return ((float) size) / (1024 * 1024) + "MB";
		} else {
			return ((float) size) / 1024 + "KB";
		}
	}
}
