package com.baoxue.db;

// Generated 2012-12-6 11:56:55 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * TTask generated by hbm2java
 */
@Entity
@Table(name = "T_TASK", catalog = "baoxue", uniqueConstraints = @UniqueConstraint(columnNames = "C_NAME"))
public class TTask implements java.io.Serializable {

	private String CId;
	private String CName;
	private String CVersionRegex;
	private Date CCreateTime;
	private boolean CPublish;
	private boolean CDelete;
	private boolean CEdit;
	private boolean CWaiteResult;
	private String CTaskDeviceId;

	public TTask() {
	}

	public TTask(String CId, String CName, Date CCreateTime, boolean CPublish,
			boolean CDelete, boolean CEdit, boolean CWaiteResult) {
		this.CId = CId;
		this.CName = CName;
		this.CCreateTime = CCreateTime;
		this.CPublish = CPublish;
		this.CDelete = CDelete;
		this.CEdit = CEdit;
		this.CWaiteResult = CWaiteResult;
	}

	public TTask(String CId, String CName, String CVersionRegex,
			Date CCreateTime, boolean CPublish, boolean CDelete, boolean CEdit,
			boolean CWaiteResult, String CTaskDeviceId) {
		this.CId = CId;
		this.CName = CName;
		this.CVersionRegex = CVersionRegex;
		this.CCreateTime = CCreateTime;
		this.CPublish = CPublish;
		this.CDelete = CDelete;
		this.CEdit = CEdit;
		this.CWaiteResult = CWaiteResult;
		this.CTaskDeviceId = CTaskDeviceId;
	}

	@Id
	@Column(name = "C_ID", unique = true, nullable = false, length = 36)
	public String getCId() {
		return this.CId;
	}

	public void setCId(String CId) {
		this.CId = CId;
	}

	@Column(name = "C_NAME", unique = true, nullable = false)
	public String getCName() {
		return this.CName;
	}

	public void setCName(String CName) {
		this.CName = CName;
	}

	@Column(name = "C_VERSION_REGEX", length = 200)
	public String getCVersionRegex() {
		return this.CVersionRegex;
	}

	public void setCVersionRegex(String CVersionRegex) {
		this.CVersionRegex = CVersionRegex;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "C_CREATE_TIME", nullable = false, length = 19)
	public Date getCCreateTime() {
		return this.CCreateTime;
	}

	public void setCCreateTime(Date CCreateTime) {
		this.CCreateTime = CCreateTime;
	}

	@Column(name = "C_PUBLISH", nullable = false)
	public boolean isCPublish() {
		return this.CPublish;
	}

	public void setCPublish(boolean CPublish) {
		this.CPublish = CPublish;
	}

	@Column(name = "C_DELETE", nullable = false)
	public boolean isCDelete() {
		return this.CDelete;
	}

	public void setCDelete(boolean CDelete) {
		this.CDelete = CDelete;
	}

	@Column(name = "C_EDIT", nullable = false)
	public boolean isCEdit() {
		return this.CEdit;
	}

	public void setCEdit(boolean CEdit) {
		this.CEdit = CEdit;
	}

	@Column(name = "C_WAITE_RESULT", nullable = false)
	public boolean isCWaiteResult() {
		return this.CWaiteResult;
	}

	public void setCWaiteResult(boolean CWaiteResult) {
		this.CWaiteResult = CWaiteResult;
	}

	@Column(name = "C_TASK_DEVICE_ID", length = 36)
	public String getCTaskDeviceId() {
		return this.CTaskDeviceId;
	}

	public void setCTaskDeviceId(String CTaskDeviceId) {
		this.CTaskDeviceId = CTaskDeviceId;
	}

}
