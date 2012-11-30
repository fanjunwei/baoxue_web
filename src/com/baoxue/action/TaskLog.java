package com.baoxue.action;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.baoxue.common.ActionBase;
import com.baoxue.db.TDoTaskLog;

public class TaskLog extends ActionBase {

	List<TDoTaskLog> taskLogs;
	String taskID;

	public List<TDoTaskLog> getTaskLogs() {
		return taskLogs;
	}

	public void setTaskLogs(List<TDoTaskLog> taskLogs) {
		this.taskLogs = taskLogs;
	}

	public String getTaskID() {
		return taskID;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}

	@Override
	public String execute() throws Exception {
		Session session = getDBSession();
		String hql = "from TDoTaskLog l where l.CTaskId=:taskId";
		Query query = session.createQuery(hql);
		query.setString("taskId", taskID);
		taskLogs = query.list();
		return SUCCESS;
	}

}
