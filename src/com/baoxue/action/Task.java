package com.baoxue.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.baoxue.common.ActionBase;
import com.baoxue.common.ApkInfo;
import com.baoxue.common.Helper;
import com.baoxue.db.TPackageUpdate;
import com.baoxue.db.TTask;
import com.baoxue.db.TTaskItem;

public class Task extends ActionBase {

	private final static String CMD_UPDATE_PACKAGE = "updatePackage";
	private final static String CMD_DELETE_PACKAGE = "deletePackage";
	private String[] cmdS = new String[] { CMD_UPDATE_PACKAGE,
			CMD_DELETE_PACKAGE };
	private String command;
	private boolean showMsg;
	private String msgTitle;
	private List<String> msgItem = new ArrayList<String>();
	private String taskID;
	private String itemID;
	private String taskName;
	private String versionRegex;

	private boolean addUpdateView;
	private boolean editUpdateView;
	private boolean addDeleteView;
	private boolean editDeleteView;

	private boolean editTaskView;

	private boolean forcesUpdate;

	private File file;
	private String fileContentType;
	private String fileFileName;
	private boolean publish;
	private String packageName;
	private List<TTaskItem> taskItems;
	private List<TTask> tasks;

	public boolean isEditTaskView() {
		return editTaskView;
	}

	public void setEditTaskView(boolean editTaskView) {
		this.editTaskView = editTaskView;
	}

	public List<TTask> getTasks() {
		return tasks;
	}

	public void setTasks(List<TTask> tasks) {
		this.tasks = tasks;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String[] getCmdS() {
		return cmdS;
	}

	public void setCmdS(String[] cmdS) {
		this.cmdS = cmdS;
	}

	public List<TTaskItem> getTaskItems() {
		return taskItems;
	}

	public void setTaskItems(List<TTaskItem> taskItems) {
		this.taskItems = taskItems;
	}

	public String getVersionRegex() {
		return versionRegex;
	}

	public void setVersionRegex(String versionRegex) {
		this.versionRegex = versionRegex;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public boolean isShowMsg() {
		return showMsg;
	}

	public void setShowMsg(boolean showMsg) {
		this.showMsg = showMsg;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public List<String> getMsgItem() {
		return msgItem;
	}

	public void setMsgItem(List<String> msgItem) {
		this.msgItem = msgItem;
	}

	public String getTaskID() {
		return taskID;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public boolean isAddUpdateView() {
		return addUpdateView;
	}

	public void setAddUpdateView(boolean addUpdateView) {
		this.addUpdateView = addUpdateView;
	}

	public boolean isEditUpdateView() {
		return editUpdateView;
	}

	public void setEditUpdateView(boolean editUpdateView) {
		this.editUpdateView = editUpdateView;
	}

	public boolean isAddDeleteView() {
		return addDeleteView;
	}

	public void setAddDeleteView(boolean addDeleteView) {
		this.addDeleteView = addDeleteView;
	}

	public boolean isEditDeleteView() {
		return editDeleteView;
	}

	public void setEditDeleteView(boolean editDeleteView) {
		this.editDeleteView = editDeleteView;
	}

	public boolean isForcesUpdate() {
		return forcesUpdate;
	}

	public void setForcesUpdate(boolean forcesUpdate) {
		this.forcesUpdate = forcesUpdate;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public boolean isPublish() {
		return publish;
	}

	public void setPublish(boolean publish) {
		this.publish = publish;
	}

	@Override
	public String execute() throws Exception {

		queryTask();
		taskID = null;
		publish = false;
		return "task";
	}

	public String taskAdd() throws Exception {

		TTask task = new TTask();
		taskID = UUID.randomUUID().toString();
		task.setCId(taskID);
		task.setCName(taskName);
		task.setCPublish(publish);
		task.setCCreateTime(new Date());
		task.setCDelete(false);
		task.setCVersionRegex(versionRegex);
		Session session = getDBSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(task);
			tx.commit();

		} catch (ConstraintViolationException ex) {
			tx.rollback();
			showMsg = true;
			msgTitle = "添加失败";
			msgItem.add("名称重复");
			return "task";
		} catch (Exception ex) {
			tx.rollback();
			showMsg = true;
			msgTitle = "添加失败";
			msgItem.add(ex.getMessage());
			return "task";
		}

		queryItem();
		return "item";

	}

	private void deleteItem(Session session, Transaction tx, String id)
			throws Exception {
		TTaskItem item = queryItemById(id);
		if (item != null) {
			if (CMD_UPDATE_PACKAGE.equals(item.getCCommand())) {
				String hql = "from TPackageUpdate p where p.CId=:id";
				Query query = session.createQuery(hql);
				query.setParameter("id", item.getCP1());
				@SuppressWarnings("unchecked")
				List<TPackageUpdate> res = query.list();
				if (res != null && res.size() > 0) {
					String fileName = res.get(0).getCFileName();
					String fullName = getRealPath("/apk/" + fileName);
					File file = new File(fullName);
					if (file.delete()) {
						System.out.println("success delete file");
					}
					session.delete(res.get(0));
				}

			}
			session.delete(item);
		}
	}

	public String deleteItem() throws Exception {

		Session session = getDBSession();
		Transaction tx = session.beginTransaction();
		showMsg = true;
		try {
			deleteItem(session, tx, itemID);
			tx.commit();
			setMsgTitle("删除成功");
		} catch (Exception ex) {
			tx.rollback();
			setShowMsg(true);
			setMsgTitle("删除失败");
			msgItem.clear();
			msgItem.add(ex.getMessage());
		}

		queryItem();
		return "item";
	}

	public String editItem() throws Exception {
		TTaskItem item = queryItemById(itemID);
		if (item != null) {
			if (CMD_UPDATE_PACKAGE.equals(item.getCCommand())) {
				editUpdateView = true;
				Session session = getDBSession();
				String hql = "from TPackageUpdate p where p.CId=:id";
				Query query = session.createQuery(hql);
				query.setParameter("id", item.getCP1());
				@SuppressWarnings("unchecked")
				List<TPackageUpdate> res = query.list();
				if (res != null && res.size() > 0) {
					forcesUpdate = res.get(0).isCForcesUpdate();
				}
			} else if (CMD_DELETE_PACKAGE.equals(item.getCCommand())) {
				editDeleteView = true;
				packageName = item.getCP1();
			}
		}
		queryItem();
		return "item";
	}

	public String addItem() throws Exception {

		if (CMD_UPDATE_PACKAGE.equals(command)) {
			addUpdateView = true;
		} else if (CMD_DELETE_PACKAGE.equals(command)) {
			addDeleteView = true;
		}
		itemID = null;
		queryItem();
		return "item";
	}

	public String UpdateAdd() throws Exception {
		if (isPost()) {
			if (file == null) {
				setShowMsg(true);
				setMsgTitle("文件不能为空");
				getMsgItem().clear();
				addUpdateView = true;
			} else {
				String fileName = UUID.randomUUID().toString() + ".apk";
				String fullName = getRealPath("/apk/" + fileName);
				File desFile = new File(fullName);

				if (Helper.Copy(file, desFile)) {
					ApkInfo info = Helper.getApkInfo(desFile.getAbsolutePath());
					if (info != null && info.enable()) {
						Session session = getDBSession();
						Transaction tx = session.beginTransaction();
						try {

							TPackageUpdate pu = new TPackageUpdate();
							pu.setCId(UUID.randomUUID().toString());
							pu.setCFileName(fileName);
							pu.setCForcesUpdate(forcesUpdate);

							pu.setCPackageName(info.getPackageName());
							pu.setCUploadTime(new Date());
							pu.setCVersionCode(info.getVersionCode());
							pu.setCVersionName(info.getVersionName());

							session.save(pu);

							TTaskItem taskitem = new TTaskItem();
							itemID = UUID.randomUUID().toString();
							taskitem.setCId(itemID);
							taskitem.setCCommand(CMD_UPDATE_PACKAGE);
							taskitem.setCEnable(true);
							taskitem.setCIndex(getMaxIndex() + 1);
							String des = "更新包：" + pu.getCPackageName() + ",";
							if (forcesUpdate) {
								des += "强制更新.";
							} else {
								des += "非强制更新.";
							}
							taskitem.setCDescription(des);
							taskitem.setCTaskId(taskID);
							taskitem.setCP1(pu.getCId());
							session.save(taskitem);
							tx.commit();
							setShowMsg(true);
							setMsgTitle("添加成功");
							msgItem.add("文件大小:"
									+ Helper.formatLength(file.length()));
							msgItem.add("packageName:" + info.getPackageName());
							msgItem.add("versionCode:" + info.getVersionCode());
							msgItem.add("versionName:" + info.getVersionName());

						} catch (Exception ex) {
							tx.rollback();
							setShowMsg(true);
							setMsgTitle("添加失败");
							msgItem.clear();
							msgItem.add(ex.getMessage());
						}
					} else {
						desFile.deleteOnExit();
						setShowMsg(true);
						setMsgTitle("文件格式错误");
						getMsgItem().clear();
						addUpdateView = true;
					}
				} else {
					setShowMsg(true);
					setMsgTitle("上传失败");
					getMsgItem().clear();
					addUpdateView = true;
				}
			}
		}
		queryItem();
		return "item";
	}

	public String UpdateEdit() throws Exception {
		Session session = getDBSession();
		Transaction tx = session.beginTransaction();
		TTaskItem item = queryItemById(itemID);
		showMsg = true;
		if (item != null) {
			try {
				if (CMD_UPDATE_PACKAGE.equals(item.getCCommand())) {
					String hql = "from TPackageUpdate p where p.CId=:id";
					Query query = session.createQuery(hql);
					query.setParameter("id", item.getCP1());
					@SuppressWarnings("unchecked")
					List<TPackageUpdate> res = query.list();
					if (res != null && res.size() > 0) {
						TPackageUpdate pu = res.get(0);
						pu.setCForcesUpdate(forcesUpdate);
						session.update(pu);

						String des = "更新包：" + pu.getCPackageName() + ",";
						if (forcesUpdate) {
							des += "强制更新.";
						} else {
							des += "非强制更新.";
						}
						item.setCDescription(des);
						session.update(pu);
						tx.commit();
						setMsgTitle("修改成功");
					}

				}

			} catch (Exception ex) {
				tx.rollback();
				setShowMsg(true);
				setMsgTitle("修改失败");
				msgItem.clear();
				msgItem.add(ex.getMessage());
			}
		}
		queryItem();
		return "item";
	}

	public String itemCancel() throws Exception {
		itemID = null;
		queryItem();
		return "item";
	}

	public String DeleteAdd() throws Exception {
		TTaskItem item = new TTaskItem();
		itemID = UUID.randomUUID().toString();
		item.setCId(itemID);
		item.setCTaskId(taskID);
		item.setCCommand(CMD_DELETE_PACKAGE);
		item.setCEnable(true);
		item.setCIndex(getMaxIndex() + 1);
		item.setCDescription("删除包:" + packageName);
		item.setCP1(packageName);
		Session session = getDBSession();
		Transaction tx = session.beginTransaction();
		showMsg = true;
		try {
			session.save(item);
			tx.commit();
			setMsgTitle("添加成功");
		} catch (Exception ex) {
			tx.rollback();
			setShowMsg(true);
			setMsgTitle("添加失败");
			msgItem.clear();
			msgItem.add(ex.getMessage());
		}
		queryItem();
		return "item";
	}

	public String DeleteEdit() throws Exception {
		Session session = getDBSession();
		Transaction tx = session.beginTransaction();
		TTaskItem item = queryItemById(itemID);
		showMsg = true;
		if (item != null) {
			try {
				if (CMD_DELETE_PACKAGE.equals(item.getCCommand())) {
					item.setCDescription("删除包:" + packageName);
					item.setCP1(packageName);
					session.update(item);
					tx.commit();
					setMsgTitle("修改成功");
				}

			} catch (Exception ex) {
				tx.rollback();
				setShowMsg(true);
				setMsgTitle("修改失败");
				msgItem.clear();
				msgItem.add(ex.getMessage());
			}
		}
		queryItem();
		return "item";
	}

	private void queryItem() {
		Session session = getDBSession();
		String hql = "from TTaskItem t where t.CTaskId=:taskId order by CIndex";
		Query query = session.createQuery(hql);
		query.setString("taskId", taskID);
		taskItems = query.list();
		if (taskName == null || "".equals(taskName)) {
			hql = "from TTask t where t.CId=:id";
			query = session.createQuery(hql);
			query.setString("id", taskID);
			List<TTask> tasks = query.list();
			if (tasks != null && tasks.size() > 0) {
				taskName = tasks.get(0).getCName();
			}
		}
	}

	private TTaskItem queryItemById(String id) {
		Session session = getDBSession();
		String hql = "from TTaskItem t where t.CId=:id";
		Query query = session.createQuery(hql);
		query.setString("id", id);
		List<TTaskItem> items = query.list();
		if (items != null && items.size() > 0) {
			return items.get(0);
		}
		return null;

	}

	private int getMaxIndex() {
		int index = 0;
		if (taskItems == null) {
			queryItem();
		}
		if (taskItems != null) {
			for (TTaskItem item : taskItems) {
				if (item.getCIndex() > index) {
					index = item.getCIndex();
				}
			}
		}
		return index;
	}

	private void queryTask() {
		Session session = getDBSession();
		String hql = "from TTask t order by t.CCreateTime";
		tasks = session.createQuery(hql).list();
	}

	public String deleteTask() throws Exception {
		Session session = getDBSession();
		Transaction tx = session.beginTransaction();
		showMsg = true;
		try {
			String hql = "from TTask t where t.CId=:id";
			Query query = session.createQuery(hql);
			query.setString("id", taskID);
			List<TTask> tasks = query.list();
			if (tasks != null && tasks.size() > 0) {

				TTask task = tasks.get(0);
				hql = "from TTaskItem t where t.CTaskId=:taskId";
				query = session.createQuery(hql);
				query.setString("taskId", task.getCId());
				List<TTaskItem> items = query.list();
				for (TTaskItem item : items) {
					deleteItem(session, tx, item.getCId());
				}
				session.delete(task);
				tx.commit();
				setMsgTitle("删除成功");
			}
		} catch (Exception ex) {
			tx.rollback();
			setMsgTitle("删除失败");
			msgItem.clear();
			msgItem.add(ex.getMessage());
		}
		queryTask();
		return "task";
	}

	public String editTask() throws Exception {

		editTaskView = true;
		Session session = getDBSession();
		String hql = "from TTask t where t.CId=:id";
		Query query = session.createQuery(hql);
		query.setString("id", taskID);
		List<TTask> tasks = query.list();
		if (tasks != null && tasks.size() > 0) {
			TTask task = tasks.get(0);
			taskName = task.getCName();
			versionRegex = task.getCVersionRegex();
			publish = task.isCPublish();
		}
		queryTask();
		return "task";
	}

	public String taskEditOk() throws Exception {
		Session session = getDBSession();
		Transaction tx = session.beginTransaction();
		showMsg = true;
		try {
			String hql = "from TTask t where t.CId=:id";
			Query query = session.createQuery(hql);
			query.setString("id", taskID);
			List<TTask> tasks = query.list();
			if (tasks != null && tasks.size() > 0) {

				TTask task = tasks.get(0);
				task.setCPublish(publish);
				task.setCName(taskName);
				task.setCVersionRegex(versionRegex);
				session.update(task);
				tx.commit();
				setMsgTitle("修改成功");
			}
		} catch (Exception ex) {
			tx.rollback();
			setMsgTitle("修改失败");
			msgItem.clear();
			msgItem.add(ex.getMessage());
		}
		queryTask();
		return "task";
	}

}
