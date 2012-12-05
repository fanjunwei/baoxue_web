package com.baoxue.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.baoxue.common.ActionBase;
import com.baoxue.db.TPackageUpdate;

public class ManagePackage extends ActionBase {

	private List<TPackageUpdate> packages = null;
	private boolean showMsg;
	private String msgTitle;
	private List<String> msgItem = new ArrayList<String>();

	private TPackageUpdate editPackage;
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<TPackageUpdate> getPackages() {
		return packages;
	}

	public void setPackages(List<TPackageUpdate> packages) {
		this.packages = packages;
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

	public TPackageUpdate getEditPackage() {
		return editPackage;
	}

	public void setEditPackage(TPackageUpdate editPackage) {
		this.editPackage = editPackage;
	}

	@Override
	public String execute() throws Exception {

		setShowMsg(false);
		query();
		return SUCCESS;
	}

	public String delete() throws Exception {
		setShowMsg(true);
		Session session = getDBSession();
		Transaction tx = session.beginTransaction();
		String hql = "from TPackageUpdate p where p.CId=:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<TPackageUpdate> res = query.list();
		if (res.size() > 0) {
			String fileName = res.get(0).getCFileName();
			String fullName = getRealPath("/apk/" + fileName);
			File file = new File(fullName);
			if (file.delete()) {
				System.out.println("success delete file");
			}
			try {
				session.delete(res.get(0));
				tx.commit();
			} catch (Exception ex) {
				tx.rollback();
			} finally {
				session.close();
			}
			setMsgTitle("删除成功");
		} else {
			setMsgTitle("删除失败");
		}

		query();
		return SUCCESS;
	}

	public String edit() throws Exception {
		setShowMsg(false);
		Session session = getDBSession();

		try {
			String hql = "from TPackageUpdate p where p.CId=:id";
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			@SuppressWarnings("unchecked")
			List<TPackageUpdate> res = query.list();
			if (res.size() > 0) {
				editPackage = res.get(0);
				return "edit";

			} else {
				return INPUT;
			}
		} finally {
			session.close();
		}

	}

	public String update() throws Exception {
		if (isPost()) {
			setShowMsg(true);
			Session session = getDBSession();
			Transaction tx = session.beginTransaction();
			try {
				String hql = "from TPackageUpdate p where p.CId=:id";
				Query query = session.createQuery(hql);
				query.setParameter("id", editPackage.getCId());
				@SuppressWarnings("unchecked")
				List<TPackageUpdate> res = query.list();
				if (res.size() > 0) {
					TPackageUpdate pk = res.get(0);
					pk.setCOldPackageName(editPackage.getCOldPackageName());
					pk.setCForcesUpdate(editPackage.isCForcesUpdate());
					pk.setCPublish(editPackage.isCPublish());
					session.update(pk);
					setMsgTitle("编辑成功");
				} else {
					setMsgTitle("编辑失败");
				}
				tx.commit();
			} catch (Exception ex) {
				tx.rollback();
			} finally {
				session.close();
			}

		} else {
			setShowMsg(false);
		}
		query();
		return INPUT;
	}

	@SuppressWarnings("unchecked")
	private void query() {
		String hql = "from TPackageUpdate p order by p.CPackageName, p.CUploadTime";
		Session session = getDBSession();
		try {
			Query query = session.createQuery(hql);
			packages = query.list();
		} finally {
			session.close();
		}
	}
}
