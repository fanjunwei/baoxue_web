package com.baoxue.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.baoxue.common.ActionBase;
import com.baoxue.common.ApkInfo;
import com.baoxue.common.Helper;
import com.baoxue.db.TPackageUpdate;

public class UploadPackage extends ActionBase {

	private String oldPackageName;
	private boolean forces;
	private boolean publish;
	private File file;
	private String fileContentType;
	private String fileFileName;

	private boolean showMsg;
	private String msgTitle;
	private List<String> msgItem = new ArrayList<String>();

	private boolean showDelete;
	private String id;

	public String getOldPackageName() {
		return oldPackageName;
	}

	public void setOldPackageName(String oldPackageName) {
		this.oldPackageName = oldPackageName;
	}

	public boolean isForces() {
		return forces;
	}

	public void setForces(boolean forces) {
		this.forces = forces;
	}

	public boolean isPublish() {
		return publish;
	}

	public void setPublish(boolean publish) {
		this.publish = publish;
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

	public boolean isShowDelete() {
		return showDelete;
	}

	public void setShowDelete(boolean showDelete) {
		this.showDelete = showDelete;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String execute() throws Exception {

		if (isPost()) {
			if (file == null) {
				setShowMsg(true);
				setMsgTitle("文件不能为空");
				getMsgItem().clear();
				return INPUT;
			}

			String fileName = UUID.randomUUID().toString() + ".apk";
			String fullName = getRealPath("/apk/" + fileName);
			File desFile = new File(fullName);

			if (Helper.Copy(file, desFile)) {
				ApkInfo info = Helper.getApkInfo(desFile.getAbsolutePath());
				if (info != null && info.enable()) {
					Session session = getDBSession();
					Transaction tx = session.beginTransaction();
					TPackageUpdate pu = new TPackageUpdate();
					pu.setCId(UUID.randomUUID().toString());
					pu.setCFileName(fileName);
					pu.setCForcesUpdate(forces);
					if (oldPackageName != null && "".equals(oldPackageName)) {
						pu.setCOldPackageName(oldPackageName);
					}
					pu.setCPublish(publish);
					pu.setCPackageName(info.getPackageName());
					pu.setCUploadTime(new Date());
					pu.setCVersionCode(info.getVersionCode());
					pu.setCVersionName(info.getVersionName());

					session.save(pu);
					tx.commit();
					setShowMsg(true);
					setMsgTitle("上传成功");
					setId(pu.getCId());
					setShowDelete(true);
					msgItem.add("文件大小:" + Helper.formatLength(file.length()));
					msgItem.add("packageName:" + info.getPackageName());
					msgItem.add("versionCode:" + info.getVersionCode());
					msgItem.add("versionName:" + info.getVersionName());
				} else {
					desFile.deleteOnExit();
					setShowMsg(true);
					setMsgTitle("文件格式错误");
					getMsgItem().clear();
				}
			} else {
				setShowMsg(true);
				setMsgTitle("上传失败");
				getMsgItem().clear();
			}
		}
		return INPUT;
	}

	public String delete() throws Exception {
		setShowMsg(true);
		setShowDelete(false);
		Session session = getDBSession();
		Transaction tx = session.beginTransaction();
		String hql = "from TPackageUpdate p where p.CId=:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
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
			setMsgTitle("删除成功");
		} else {
			setMsgTitle("删除失败");
		}
		tx.commit();

		return SUCCESS;
	}
}
