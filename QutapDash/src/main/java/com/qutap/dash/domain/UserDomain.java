package com.qutap.dash.domain;

import java.util.List;

public class UserDomain {
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
	
		
}
