package com.qutap.dash.domain;

import java.io.Serializable;
import java.util.List;

public class ApplicationGroup  implements Serializable {
	private String groupId;
	private String groupName;
	private List<ApplicationList>  applicationList;
	private static final long serialVersionUID = 1L;
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<ApplicationList> getApplicationList() {
		return applicationList;
	}
	public void setApplicationList(List<ApplicationList> applicationList) {
		this.applicationList = applicationList;
	}
	@Override
	public String toString() {
		return "ApplicationGroupModel [groupId=" + groupId + ", groupName=" + groupName + ", applicationList="
				+ applicationList + "]";
	}
}
