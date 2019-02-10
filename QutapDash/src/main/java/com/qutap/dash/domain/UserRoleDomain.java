package com.qutap.dash.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userRole")
public class UserRoleDomain {	
	private String userName;
	private String dsid;
	private String role;
	


	public String getDsid() {
		return dsid;
	}

	public void setDsid(String dsid) {
		this.dsid = dsid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserRole [userName=" + userName + ", dsid=" + dsid + ", role=" + role + "]";
	}

	


}
