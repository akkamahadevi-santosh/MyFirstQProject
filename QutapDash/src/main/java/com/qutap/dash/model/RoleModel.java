package com.qutap.dash.model;

public class RoleModel {
	private String roleId;
	private String roleName;
	private String roleDescription;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	@Override
	public String toString() {
		return "RoleDomain [roleId=" + roleId + ", roleName=" + roleName + ", roleDescription=" + roleDescription + "]";
	}

}
