package com.qutap.dash.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="roleGroups")
public class RoleGroups {
	private String roleId;
	private String role;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "RoleGroups [roleId=" + roleId + ", role=" + role + "]";
	}
	
	

}
