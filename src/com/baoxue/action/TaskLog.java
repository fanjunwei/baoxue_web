package com.baoxue.action;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.baoxue.common.ActionBase;
import com.baoxue.db.TDoTaskLog;
import com.baoxue.db.TTask;

public class TaskLog extends ActionBase {

	private final static int PAGE_SIZE = 50;
	private List<TDoTaskLog> taskLogs;
	private String taskID;
	private String taskName;
	private int pageIndex = 0;
	private int pageCount;

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

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
		query();
		return SUCCESS;
	}

	private void query() {
		Session session = getDBSession();
		try {
			int beginIndex = pageIndex * PAGE_SIZE;
			String chql = "select count(*) from TDoTaskLog l where l.CTaskId=:taskId";
			int count = ((Long) session.createQuery(chql)
					.setString("taskId", taskID).iterate().next()).intValue();
			System.out.println("cout=" + count);
			pageCount = count / PAGE_SIZE;

			if (count % PAGE_SIZE != 0) {
				pageCount++;
			}
			System.out.println("pageCount=" + pageCount);
			String hql = "from TDoTaskLog l where l.CTaskId=:taskId order by l.CTime";
			Query query = session.createQuery(hql);
			query.setFirstResult(beginIndex);
			query.setMaxResults(PAGE_SIZE);
			query.setString("taskId", taskID);
			taskLogs = query.list();

			if (taskName == null || "".equals(taskName)) {
				hql = "from TTask t where t.CId=:id";
				query = session.createQuery(hql);
				query.setString("id", taskID);
				
				List<TTask> tasks = query.list();
				if (tasks != null && tasks.size() > 0) {
					taskName = tasks.get(0).getCName();
				}
			}
		} finally {
			session.close();
		}
	}

	public String clear() {
		Session session = getDBSession();
		Transaction tx = session.beginTransaction();
		try {
			String sql = "delete from T_DO_TASK_LOG where C_TASK_ID=:taskId";
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("taskId", taskID);
			query.executeUpdate();
			tx.commit();
		} catch (Exception ex) {
			tx.rollback();
		} finally {
			session.close();
		}

		query();
		return SUCCESS;
	}

}
