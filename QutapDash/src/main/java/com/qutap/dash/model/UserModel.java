package com.qutap.dash.model;

import java.util.List;

import com.qutap.dash.domain.GroupNameList;

public class UserModel {
	private String userName;
	private String dsid;
	private List<GroupNameList> groupList;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDsid() {
		return dsid;
	}
	public void setDsid(String dsid) {
		this.dsid = dsid;
	}
	public List<GroupNameList> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<GroupNameList> groupList) {
		this.groupList = groupList;
	}
	@Override
	public String toString() {
		return "UserModel [userName=" + userName + ", dsid=" + dsid + ", groupList=" + groupList + "]";
	}
	
	
}
