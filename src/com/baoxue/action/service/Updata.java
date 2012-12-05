package com.baoxue.action.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.Query;
import org.hibernate.Session;

import com.baoxue.db.TPackageUpdate;

public class Updata extends ServiceBase {

	private List<String> packages = new ArrayList<String>();
	private List<Integer> versionCodes = new ArrayList<Integer>();

	private List<String> updatePackageNames = new ArrayList<String>();
	private List<String> updatePackageUrls = new ArrayList<String>();
	private List<Boolean> forcesUpdates = new ArrayList<Boolean>();

	@JSON(serialize = false)
	public List<String> getPackages() {
		return packages;
	}

	public void setPackages(List<String> packages) {
		this.packages = packages;
	}

	@JSON(serialize = false)
	public List<Integer> getVersionCodes() {
		return versionCodes;
	}

	public void setVersionCodes(List<Integer> versionCodes) {
		this.versionCodes = versionCodes;
	}

	public List<String> getUpdatePackageNames() {
		return updatePackageNames;
	}

	public void setUpdatePackageNames(List<String> updatePackageNames) {
		this.updatePackageNames = updatePackageNames;
	}

	public List<String> getUpdatePackageUrls() {
		return updatePackageUrls;
	}

	public void setUpdatePackageUrls(List<String> updatePackageUrls) {
		this.updatePackageUrls = updatePackageUrls;
	}

	public List<Boolean> getForcesUpdates() {
		return forcesUpdates;
	}

	public void setForcesUpdates(List<Boolean> forcesUpdates) {
		this.forcesUpdates = forcesUpdates;
	}

	@Override
	public String execute() {
		Map<String, TPackageUpdate> appMaps = new HashMap<String, TPackageUpdate>();
		Session session = getDBSession();
		try {
			String hql = "from TPackageUpdate";
			Query query = session.createQuery(hql);
			List<TPackageUpdate> res = query.list();
			for (TPackageUpdate pu : res) {
				appMaps.put(pu.getCPackageName(), pu);
			}
			updatePackageNames.clear();
			updatePackageUrls.clear();
			forcesUpdates.clear();
			for (int i = 0; i < packages.size(); i++) {
				String pn = packages.get(i);
				TPackageUpdate find = null;
				if ((find = appMaps.get(pn)) != null) {
					int code = versionCodes.get(i);
					if (find.getCVersionCode() > code) {
						updatePackageNames.add(find.getCPackageName());
						updatePackageUrls.add(getBaseUrl() + "/apk/"
								+ find.getCFileName());
						forcesUpdates.add(find.isCForcesUpdate());
					}
				}
			}

			for (TPackageUpdate find : res) {
				updatePackageNames.add(find.getCPackageName());
				updatePackageUrls.add(getBaseUrl() + "/apk/"
						+ find.getCFileName());
				forcesUpdates.add(find.isCForcesUpdate());
			}
		} finally {
			session.close();
		}
		return SUCCESS;
	}
}
