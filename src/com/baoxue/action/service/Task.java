package com.baoxue.action.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.baoxue.db.TDoTaskLog;
import com.baoxue.db.TPackageUpdate;
import com.baoxue.db.TTask;
import com.baoxue.db.TTaskItem;

public class Task extends ServiceBase {

	String taskId;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Override
	public String execute() throws Exception {

		ResTask resTask = new ResTask();
		List<ResTaskItem> items = new ArrayList<ResTaskItem>();
		resTask.setItems(items);
		TTask task = null;
		Session session = getDBSession();
		String sql = "select * from T_TASK t left outer join T_DO_TASK_LOG l on t.C_ID=l.C_TASK_ID " +
				"where t.C_PUBLISH=1 and (l.C_DEVICE_ID is null or l.C_DEVICE_ID<>:deviceId)";
		SQLQuery realQuery = session.createSQLQuery(sql);
		realQuery.addEntity("t", TTask.class);
		realQuery.setString("deviceId", getDeviceId());
		List<TTask> tasks = realQuery.list();

		for (TTask t : tasks) {
			task = t;
			break;
		}
		if (task != null) {
			String hql = "from TTaskItem i where i.CTaskId=:taskId order by i.CIndex";
			Query query = session.createQuery(hql);
			query.setString("taskId", task.getCId());
			resTask.setId(task.getCId());
			List<TTaskItem> taskitems = query.list();
			for (TTaskItem taskitem : taskitems) {
				ResTaskItem ri = new ResTaskItem();
				ri.setCommand(taskitem.getCCommand());
				if (CMD_UPDATE_PACKAGE.equals(taskitem.getCCommand())) {

					TPackageUpdate update = getPackageUpdata(taskitem.getCP1());
					if (update != null) {
						ri.setForcesUpdate(update.isCForcesUpdate());
						ri.setPackageName(update.getCPackageName());
						ri.setUrl(getBaseUrl() + "/apk/"
								+ update.getCFileName());
						ri.setVersionCode(update.getCVersionCode());
						items.add(ri);
					}
				} else if (CMD_DELETE_PACKAGE.equals(taskitem.getCCommand())) {
					ri.setPackageName(taskitem.getCP1());
					items.add(ri);
				}

			}

		}
		result = resTask;
		return INPUT;
	}

	private TPackageUpdate getPackageUpdata(String id) {
		Session session = getDBSession();
		String hql = "from TPackageUpdate p where p.CId=:id";
		Query query = session.createQuery(hql);
		query.setString("id", id);
		// query.setMaxResults(1);
		List<TPackageUpdate> res = query.list();
		if (res != null && res.size() > 0) {
			return res.get(0);
		} else {
			return null;
		}
	}

	public String doTask() {
		Session session = getDBSession();
		Transaction tx = session.beginTransaction();
		try {
			TDoTaskLog log = new TDoTaskLog();
			log.setCId(UUID.randomUUID().toString());
			log.setCTaskId(taskId);
			log.setCDeviceId(getDeviceId());
			log.setCDeviceVersion(getDeviceVersion());
			log.setCIp(getRequest().getRemoteHost());
			log.setCTime(new Date());
			session.save(log);
			tx.commit();

		} catch (Exception ex) {
			tx.rollback();
		}

		result = null;
		return INPUT;
	}
}
