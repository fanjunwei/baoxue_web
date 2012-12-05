package com.baoxue.db;

// Generated 2012-12-5 10:14:47 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TPackageUpdate generated by hbm2java
 */
@Entity
@Table(name = "T_PACKAGE_UPDATE", catalog = "baoxue")
public class TPackageUpdate implements java.io.Serializable {

	private String CId;
	private String CPackageName;
	private String COldPackageName;
	private String CFileName;
	private int CVersionCode;
	private String CVersionName;
	private Date CUploadTime;
	private boolean CPublish;
	private boolean CForcesUpdate;

	public TPackageUpdate() {
	}

	public TPackageUpdate(String CId, String CPackageName, String CFileName,
			int CVersionCode, String CVersionName, Date CUploadTime,
			boolean CPublish, boolean CForcesUpdate) {
		this.CId = CId;
		this.CPackageName = CPackageName;
		this.CFileName = CFileName;
		this.CVersionCode = CVersionCode;
		this.CVersionName = CVersionName;
		this.CUploadTime = CUploadTime;
		this.CPublish = CPublish;
		this.CForcesUpdate = CForcesUpdate;
	}

	public TPackageUpdate(String CId, String CPackageName,
			String COldPackageName, String CFileName, int CVersionCode,
			String CVersionName, Date CUploadTime, boolean CPublish,
			boolean CForcesUpdate) {
		this.CId = CId;
		this.CPackageName = CPackageName;
		this.COldPackageName = COldPackageName;
		this.CFileName = CFileName;
		this.CVersionCode = CVersionCode;
		this.CVersionName = CVersionName;
		this.CUploadTime = CUploadTime;
		this.CPublish = CPublish;
		this.CForcesUpdate = CForcesUpdate;
	}

	@Id
	@Column(name = "C_ID", unique = true, nullable = false, length = 36)
	public String getCId() {
		return this.CId;
	}

	public void setCId(String CId) {
		this.CId = CId;
	}

	@Column(name = "C_PACKAGE_NAME", nullable = false, length = 100)
	public String getCPackageName() {
		return this.CPackageName;
	}

	public void setCPackageName(String CPackageName) {
		this.CPackageName = CPackageName;
	}

	@Column(name = "C_OLD_PACKAGE_NAME", length = 100)
	public String getCOldPackageName() {
		return this.COldPackageName;
	}

	public void setCOldPackageName(String COldPackageName) {
		this.COldPackageName = COldPackageName;
	}

	@Column(name = "C_FILE_NAME", nullable = false, length = 50)
	public String getCFileName() {
		return this.CFileName;
	}

	public void setCFileName(String CFileName) {
		this.CFileName = CFileName;
	}

	@Column(name = "C_VERSION_CODE", nullable = false)
	public int getCVersionCode() {
		return this.CVersionCode;
	}

	public void setCVersionCode(int CVersionCode) {
		this.CVersionCode = CVersionCode;
	}

	@Column(name = "C_VERSION_NAME", nullable = false, length = 45)
	public String getCVersionName() {
		return this.CVersionName;
	}

	public void setCVersionName(String CVersionName) {
		this.CVersionName = CVersionName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "C_UPLOAD_TIME", nullable = false, length = 19)
	public Date getCUploadTime() {
		return this.CUploadTime;
	}

	public void setCUploadTime(Date CUploadTime) {
		this.CUploadTime = CUploadTime;
	}

	@Column(name = "C_PUBLISH", nullable = false)
	public boolean isCPublish() {
		return this.CPublish;
	}

	public void setCPublish(boolean CPublish) {
		this.CPublish = CPublish;
	}

	@Column(name = "C_FORCES_UPDATE", nullable = false)
	public boolean isCForcesUpdate() {
		return this.CForcesUpdate;
	}

	public void setCForcesUpdate(boolean CForcesUpdate) {
		this.CForcesUpdate = CForcesUpdate;
	}

}
