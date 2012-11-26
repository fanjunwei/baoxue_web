package com.baoxue.db;

// Generated 2012-11-19 12:57:06 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TTaskItem generated by hbm2java
 */
@Entity
@Table(name = "T_TASK_ITEM", catalog = "baoxue")
public class TTaskItem implements java.io.Serializable {

	private String id;
	private String taskId;
	private String name;
	private String command;
	private String p1;
	private String p2;
	private String p3;
	private String p4;
	private String p5;
	private int index;
	private boolean necessary;

	public TTaskItem() {
	}

	public TTaskItem(String id, String taskId, String name, String command,
			int index, boolean necessary) {
		this.id = id;
		this.taskId = taskId;
		this.name = name;
		this.command = command;
		this.index = index;
		this.necessary = necessary;
	}

	public TTaskItem(String id, String taskId, String name, String command,
			String p1, String p2, String p3, String p4, String p5, int index,
			boolean necessary) {
		this.id = id;
		this.taskId = taskId;
		this.name = name;
		this.command = command;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
		this.p5 = p5;
		this.index = index;
		this.necessary = necessary;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "TASK_ID", nullable = false, length = 36)
	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Column(name = "NAME", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "COMMAND", nullable = false, length = 50)
	public String getCommand() {
		return this.command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	@Column(name = "P1", length = 200)
	public String getP1() {
		return this.p1;
	}

	public void setP1(String p1) {
		this.p1 = p1;
	}

	@Column(name = "P2", length = 200)
	public String getP2() {
		return this.p2;
	}

	public void setP2(String p2) {
		this.p2 = p2;
	}

	@Column(name = "P3", length = 200)
	public String getP3() {
		return this.p3;
	}

	public void setP3(String p3) {
		this.p3 = p3;
	}

	@Column(name = "P4", length = 200)
	public String getP4() {
		return this.p4;
	}

	public void setP4(String p4) {
		this.p4 = p4;
	}

	@Column(name = "P5", length = 200)
	public String getP5() {
		return this.p5;
	}

	public void setP5(String p5) {
		this.p5 = p5;
	}

	@Column(name = "INDEX", nullable = false)
	public int getIndex() {
		return this.index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Column(name = "NECESSARY", nullable = false)
	public boolean isNecessary() {
		return this.necessary;
	}

	public void setNecessary(boolean necessary) {
		this.necessary = necessary;
	}

}
