package com.baoxue.action;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.baoxue.common.ActionBase;
import com.baoxue.db.TDoTaskLog;
import com.baoxue.db.TTask;

public class TaskLog extends ActionBase {

	private static Object sessionLock = new Object();
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

	private void runSetIpLocate() {
		new Thread() {

			@Override
			public void run() {
				Session session = getDBSession();
				Transaction tx = session.beginTransaction();
				try {
					String hql = "select l.CId,l.CIpLocate,l.CIp from TDoTaskLog l";
					Query query = session.createQuery(hql).setFetchSize(1000);
					ScrollableResults scroll = query
							.scroll(ScrollMode.FORWARD_ONLY);
					// taskLogs = query.list();
					int i = 0;
					while (scroll.next()) {
						i++;
						String CId = scroll.getString(0);
						String CIpLocate = scroll.getString(1);
						String CIp = scroll.getString(2);
						System.out.println(CId);
						System.out.println(CIpLocate);
						System.out.println(CIp);
						if (CIpLocate == null || CIpLocate.equals("")) {
							Query upatequery = session
									.createQuery("update TDoTaskLog l set l.CIpLocate =:CIpLocate where l.CId =:CId");

							String locate = getIpLocate(CIp);
							if (locate != null) {
								upatequery.setString("CIpLocate", locate);
								System.out.println(":" + locate);
								upatequery.setString("CId", CId);
								upatequery.executeUpdate();
							}

						}
						if (i % 10 == 0) {
							session.flush();
							session.clear();
						}
						synchronized (sessionLock) {
							doIpResult res = getIpResult();
							res.setCount(res.getCount() + 1);
						}
					}
					tx.commit();
				} catch (Exception ex) {
					tx.rollback();
				} finally {
					session.close();
					synchronized (sessionLock) {
						doIpResult res = getIpResult();
						res.setSuccess(1);
					}
				}
			}

		}.start();

	}

	private static doIpResult ipResult;

	private doIpResult getIpResult() {
		// doIpResult res = (doIpResult) getSession().getAttribute("IpResult");
		if (ipResult == null) {
			ipResult = new doIpResult();
			// getSession().setAttribute("IpResult", res);
		}
		return ipResult;
	}

	public String DoIpLocateProgress() {

		synchronized (sessionLock) {
			result = getIpResult();
		}
		return "json";
	}

	public String DoIpLocate() {
		Session session = getDBSession();

		try {
			String chql = "select count(*) from TDoTaskLog";
			int total = ((Long) session.createQuery(chql).iterate().next())
					.intValue();
			synchronized (sessionLock) {
				doIpResult res = getIpResult();
				if (res.getSuccess() == 1) {
					res.setSuccess(0);
					res.setTotal(total);
					res.setCount(0);
				} else {
					return "iplocate";
				}
			}
			runSetIpLocate();
		} finally {
			session.close();
		}
		return "iplocate";
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
					if (locate != null) {
						l.setCIpLocate(locate);
						session.update(l);
					}
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

	private String jsonGetString(JSONObject json, String key) {

		try {
			return json.getString(key);
		} catch (Exception ex) {
			return null;
		}
	}

	private String getIpLocate(String ip) {

		try {
			int byteread;
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
			if ("1".equals(jsonGetString(json, "ret"))) {
				String country = jsonGetString(json, "country");
				String province = jsonGetString(json, "province");
				String city = jsonGetString(json, "city");
				String district = jsonGetString(json, "district");
				String isp = jsonGetString(json, "isp");

				return country + province + city + district + isp;
			} else {
				return "无";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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
