package com.qutap.dash.model;

import java.util.List;

import com.qutap.dash.domain.ApplicationGroup;

public class UserGroupsModel {
	
	private String userName;
	private String dsid;
	private String role;
	private List<ApplicationGroup> grouplist;
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
	
	
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<ApplicationGroup> getGrouplist() {
		return grouplist;
	}
	public void setGrouplist(List<ApplicationGroup> grouplist) {
		this.grouplist = grouplist;
	}
	@Override
	public String toString() {
		return "UserGroupsModel [userName=" + userName + ", dsid=" + dsid + ", role=" + role + ", grouplist="
				+ grouplist + "]";
	}
	
	
	
	
	

	
}
