package com.baoxue.action.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.baoxue.common.Zip;
import com.baoxue.db.TDoTaskLog;
import com.baoxue.db.TPackageUpdate;
import com.baoxue.db.TTask;
import com.baoxue.db.TTaskItem;

public class Task extends ServiceBase {

	String taskId;
	String resultZipBase64;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getResultZipBase64() {
		return resultZipBase64;
	}

	public void setResultZipBase64(String resultZipBase64) {
		this.resultZipBase64 = resultZipBase64;
	}

	@Override
	public String execute() throws Exception {

		ResTask resTask = new ResTask();
		TTask task = null;
		Session session = getDBSession();
		Transaction tx = session.beginTransaction();
		try {
			String sql = "select * from T_TASK t "
					+ "where t.C_PUBLISH=1 and t.C_DELETE=0 and t.C_EDIT=0 and not exists (select * from T_DO_TASK_LOG l where l.C_TASK_ID=t.C_ID and C_DEVICE_ID=:deviceId)"
					+ "order by t.C_CREATE_TIME";
			// "select * from T_TASK t left outer join T_DO_TASK_LOG l on t.C_ID=l.C_TASK_ID "
			// +
			// "where t.C_PUBLISH=1 and t.C_DELETE=0 and t.C_EDIT=0 and (l.C_DEVICE_ID is null or l.C_DEVICE_ID<>:deviceId) order by t.C_CREATE_TIME";

			SQLQuery realQuery = session.createSQLQuery(sql);
			realQuery.addEntity("t", TTask.class);
			realQuery.setString("deviceId", getDeviceId());
			List<TTask> tasks = realQuery.list();

			for (TTask t : tasks) {
				System.out.println("task queryed");
				if (getDeviceVersion() != null
						&& !"".equals(getDeviceVersion())
						&& t.getCVersionRegex() != null
						&& !"".equals(t.getCVersionRegex())) {

					Matcher matcherVersion = Pattern.compile(
							t.getCVersionRegex()).matcher(getDeviceVersion());
					if (matcherVersion.find()) {
						System.out.println("task matched");

						if (getDeviceId() == null
								|| "".equals(getDeviceId())
								|| t.getCTaskDeviceId() == null
								|| "".equals(t.getCTaskDeviceId())
								|| t.getCTaskDeviceId().indexOf(getDeviceId()) != -1) {
							System.out.println("task find");
							task = t;
							break;
						} else {
							System.out
									.printf("task getDeviceId()=%s,getCTaskDeviceId()=%s,index=%d",
											getDeviceId(),
											t.getCTaskDeviceId(), t
													.getCTaskDeviceId()
													.indexOf(getDeviceId()));
						}
					}

				}

			}
			if (task != null) {

				String hql = "from TTaskItem i where i.CTaskId=:taskId order by i.CIndex";
				Query query = session.createQuery(hql);
				query.setString("taskId", task.getCId());
				resTask.setId(task.getCId());
				resTask.setWaitResult(task.isCWaiteResult());
				List<TTaskItem> taskitems = query.list();
				for (TTaskItem taskitem : taskitems) {
					ResTaskItem ri = new ResTaskItem();
					ri.setCommand(taskitem.getCCommand());
					ri.setId(taskitem.getCId());
					if (com.baoxue.action.Task.CMD_UPDATE_PACKAGE
							.equals(taskitem.getCCommand())) {

						ri.setUpdataPackageTaskItem(new ResUpdataPackageTaskItem());
						TPackageUpdate update = getPackageUpdata(
								taskitem.getCP1(), session);
						if (update != null) {
							ri.getUpdataPackageTaskItem().setForcesUpdate(
									update.isCForcesUpdate());
							ri.getUpdataPackageTaskItem().setPackageName(
									update.getCPackageName());
							ri.getUpdataPackageTaskItem().setUrl(
									getBaseUrl() + "/apk/"
											+ update.getCFileName());
							ri.getUpdataPackageTaskItem().setVersionCode(
									update.getCVersionCode());
						}
					} else if (com.baoxue.action.Task.CMD_DELETE_PACKAGE
							.equals(taskitem.getCCommand())) {
						ri.setDeletePackageTaskItem(new ResDeletePackageTaskItem());
						ri.getDeletePackageTaskItem().setPackageName(
								taskitem.getCP1());
					} else if (com.baoxue.action.Task.CMD_LINK.equals(taskitem
							.getCCommand())) {
						ResLinkTaskItem linkitem = new ResLinkTaskItem();
						ri.setLinkTaskItem(linkitem);
						linkitem.setMessage(taskitem.getCP1());

						linkitem.setUrl(taskitem.getCP2());
						if ("1".equals(taskitem.getCP3())) {
							linkitem.setBackground(true);
						} else {
							linkitem.setBackground(false);
						}
						if ("1".equals(taskitem.getCP4())) {
							linkitem.setAutoOpen(true);
						} else {
							linkitem.setAutoOpen(false);
						}
					} else if (com.baoxue.action.Task.CMD_SHELL.equals(taskitem
							.getCCommand())) {
						ResShellTaskItem item = new ResShellTaskItem();
						ri.setShellTaskItem(item);
						item.setShell(taskitem.getCP1());
					} else if (com.baoxue.action.Task.CMD_DOWNLOAD_FILE
							.equals(taskitem.getCCommand())) {
						ResDownloadFileTaskItem item = new ResDownloadFileTaskItem();
						ri.setDownloadFileItem(item);
						item.setUrl(getBaseUrl() + "/file/" + taskitem.getCP1());
						item.setPath(taskitem.getCP2());
					}
					resTask.getItems().add(ri);

				}

				if (!task.isCWaiteResult() && getDeviceId() != null
						&& !"".equals(getDeviceId())) {
					TDoTaskLog log = new TDoTaskLog();
					log.setCId(UUID.randomUUID().toString());
					log.setCTaskId(task.getCId());
					log.setCDeviceId(getDeviceId());
					log.setCDeviceVersion(getDeviceVersion());
					log.setCIp(getRequest().getRemoteHost());
					log.setCTime(new Date());
					session.save(log);
				}

			}
			tx.commit();
		} catch (Exception ex) {
			tx.rollback();
		} finally {
			session.close();
		}
		result = resTask;
		return INPUT;
	}

	private TPackageUpdate getPackageUpdata(String id, Session session) {

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
		String res = null;
		if (resultZipBase64 != null && !"".equals(resultZipBase64)) {
			try {
				res = new String(Zip.zipBase64decompression(resultZipBase64),
						"utf-8");
			} catch (Exception ex) {
				System.out.println("resultZipBase64 error" + ex.getMessage());
			}
		}
		if (taskId != null && !"".equals(taskId)) {
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
				log.setCResult(res);
				session.save(log);
				tx.commit();

			} catch (Exception ex) {
				tx.rollback();
			}
		}
		result = null;
		return INPUT;
	}
}
