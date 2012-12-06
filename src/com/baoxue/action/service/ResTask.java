package com.baoxue.action.service;

import java.util.ArrayList;
import java.util.List;

public class ResTask {
	String id;
	boolean waitResult;
	List<ResTaskItem> items = new ArrayList<ResTaskItem>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<ResTaskItem> getItems() {
		return items;
	}

	public void setItems(List<ResTaskItem> items) {
		this.items = items;
	}

	public boolean isWaitResult() {
		return waitResult;
	}

	public void setWaitResult(boolean waitResult) {
		this.waitResult = waitResult;
	}
	

}
