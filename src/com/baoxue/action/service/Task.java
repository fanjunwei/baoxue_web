package com.baoxue.action.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.baoxue.db.TPackageUpdate;
import com.baoxue.db.TTask;
import com.baoxue.db.TTaskItem;
import com.baoxue.db.VTaskLog;

public class Task extends ServiceBase {

	private String deviceId = "";
	private String versionRegex = "";
	private final static String CMD_UPDATE_PACKAGE = "updatePackage";
	private final static String CMD_DELETE_PACKAGE = "deletePackage";

	@Override
	public String execute() throws Exception {

		List<TaskItem> items = new ArrayList<TaskItem>();
		TTask task = null;
		Session session = getDBSession();
		//String hql = "from VTaskLog t where t.id.CDeviceId is null or t.id.CDeviceId!=:deviceId order by t.id.CCreateTime";
		String hql = "select t from TTask t left join TTaskItem i with t.CId=i.CTaskId";
		Query query = session.createQuery(hql);
		//query.setString("deviceId", deviceId);
		List<TTask> tasks = query.list();
		for (TTask t : tasks) {
			task = t;
			break;
		}
		if (task != null) {
			hql = "from TTaskItem i where i.CTaskId=:taskId order by i.CIndex";
			query = session.createQuery(hql);
			query.setString("taskId", task.getCId());
			List<TTaskItem> taskitems = query.list();
			for (TTaskItem taskitem : taskitems) {
				TaskItem ri = new TaskItem();
				ri.setCommand(taskitem.getCCommand());
				if (CMD_UPDATE_PACKAGE.equals(taskitem.getCCommand())) {

					TPackageUpdate update = getPackageUpdata(taskitem.getCP1());
					if (update != null) {
						ri.setForcesUpdate(update.isCForcesUpdate());
						ri.setPackageName(update.getCPackageName());
						ri.setUrl(getBaseUrl() + "/apk/"
								+ update.getCFileName());
						ri.setVersionCode(update.getCVersionCode());
					}
				} else if (CMD_DELETE_PACKAGE.equals(taskitem.getCCommand())) {
					ri.setPackageName(taskitem.getCP1());
				}

			}

		}
		result = items;
		return SUCCESS;
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
}
