package com.baoxue.action;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import net.sf.json.JSONObject;

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
		Transaction tx = session.beginTransaction();
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

			for (TDoTaskLog l : taskLogs) {
				if (l.getCIpLocate() == null || l.getCIpLocate().equals("")) {
					String locate = getIpLocate(l.getCIp());
					l.setCIpLocate(locate);
					session.update(l);
				}
			}
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
			tx.commit();
			session.close();
		}
	}

	private String getIpLocate(String ip) {

		int byteread;

		try {
			URL url = new URL(
					"http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip="
							+ ip);
			URLConnection conn = url.openConnection();
			InputStream inStream = conn.getInputStream();
			ByteArrayOutputStream output = new ByteArrayOutputStream();

			byte[] buffer = new byte[1204];
			while ((byteread = inStream.read(buffer)) > 0) {
				output.write(buffer, 0, byteread);
			}
			String res = new String(output.toByteArray(), "utf-8");
			JSONObject json = JSONObject.fromObject(res);
			if (json.getString("ret").equals("1")) {
				String country = json.getString("country");
				String province = json.getString("province");
				String city = json.getString("city");
				String district = json.getString("district");
				String isp = json.getString("isp");

				return country + province + city + district + isp;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "无";
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
