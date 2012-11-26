package com.baoxue.common;

public class ApkInfo {

	private String packageName;
	private int versionCode = -1;
	private String versionName;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public boolean enable() {
		if (packageName != null && versionCode != -1 && versionName != null) {
			return true;
		} else {
			return false;
		}
	}

}
