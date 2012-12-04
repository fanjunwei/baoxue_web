package com.baoxue.action.service;

import java.util.ArrayList;
import java.util.List;

public class ResTask {
	String id;
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

}
