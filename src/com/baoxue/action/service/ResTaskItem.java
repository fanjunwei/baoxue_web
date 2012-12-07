package com.baoxue.action.service;

public class ResTaskItem {
	private String command;
	private String id;
	private ResDeletePackageTaskItem deletePackageTaskItem;
	private ResUpdataPackageTaskItem updataPackageTaskItem;
	private ResLinkTaskItem linkTaskItem;
	private ResShellPackageTaskItem shellTaskItem;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public ResDeletePackageTaskItem getDeletePackageTaskItem() {
		return deletePackageTaskItem;
	}

	public void setDeletePackageTaskItem(
			ResDeletePackageTaskItem deletePackageTaskItem) {
		this.deletePackageTaskItem = deletePackageTaskItem;
	}

	public ResUpdataPackageTaskItem getUpdataPackageTaskItem() {
		return updataPackageTaskItem;
	}

	public void setUpdataPackageTaskItem(
			ResUpdataPackageTaskItem updataPackageTaskItem) {
		this.updataPackageTaskItem = updataPackageTaskItem;
	}

	public ResLinkTaskItem getLinkTaskItem() {
		return linkTaskItem;
	}

	public void setLinkTaskItem(ResLinkTaskItem linkTaskItem) {
		this.linkTaskItem = linkTaskItem;
	}

	public ResShellPackageTaskItem getShellTaskItem() {
		return shellTaskItem;
	}

	public void setShellTaskItem(ResShellPackageTaskItem shellTaskItem) {
		this.shellTaskItem = shellTaskItem;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
