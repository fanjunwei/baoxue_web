package com.baoxue.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.baoxue.common.ActionBase;

public class UploadWeather extends ActionBase {
	private String msg;

	private File file;
	private String fileContentType;
	private String fileFileName;
	private boolean showFileinfo = false;
	private String uploadSize;
	private String apkVersion;

	public boolean isShowFileinfo() {
		return showFileinfo;
	}

	public void setShowFileinfo(boolean showFileinfo) {
		this.showFileinfo = showFileinfo;
	}

	public String getUploadSize() {
		return uploadSize;
	}

	public void setUploadSize(String uploadSize) {
		this.uploadSize = uploadSize;
	}

	public String getApkVersion() {
		return apkVersion;
	}

	public void setApkVersion(String apkVersion) {
		this.apkVersion = apkVersion;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String execute() throws Exception {
		if (isPost()) {
			if (file != null) {
				int length = 0;

				FileInputStream input = null;
				File outfile = new File(Weather.getApkPath());
				if (outfile.exists()) {
					if (!outfile.canWrite()) {
						setMsg("无写入权限,请确认tomcat有文件" + outfile.getAbsolutePath()
								+ "的写入权限");
						showFileinfo = false;
						return INPUT;

					}
				} else {

					if (!outfile.getParentFile().canWrite()) {
						setMsg("无写入权限,请确认tomcat有目录"
								+ outfile.getParentFile().getAbsolutePath()
								+ "的写入权限");
						showFileinfo = false;
						return INPUT;

					}
				}

				outfile.deleteOnExit();

				FileOutputStream out = null;
				try {
					out = new FileOutputStream(outfile);
					input = new FileInputStream(file);
					byte[] buffer = new byte[1024];
					int b = 0;

					while ((b = input.read(buffer)) >= 0) {
						length += b;
						out.write(buffer, 0, b);
					}

				} finally {
					if (input != null)
						input.close();
					if (out != null)
						out.close();

				}
				showFileinfo = true;
				if (length > 1024 * 1024) {
					setUploadSize(((float) length) / (1024 * 1024) + "MB");
				} else {
					setUploadSize(((float) length) / 1024 + "KB");
				}

				setApkVersion(Weather.getVersion(Weather.getAaptPath(),
						Weather.getApkPath()));
				setMsg("上传成功");
				return SUCCESS;
			}
			setMsg("上传失败");
		}
		showFileinfo = false;
		return INPUT;
	}
}
