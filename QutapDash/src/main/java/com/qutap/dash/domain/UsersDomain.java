package com.qutap.dash.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "userData")
public class UsersDomain {
	
	//@Id
	//@Field("userId")
	private String userId; 
	private String userName;
	private String userPassword;
	private String userMailId;
	private String userRole;
//	private String confirmPassword;
	private String createdDate;
	private String updatedDate;
	//private boolean status;
	

	public String getUserName() {
		return userName;
	}
	
	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserMailId() {
		return userMailId;
	}

	public void setUserMailId(String userMailId) {
		this.userMailId = userMailId;
	}

	@Override
	public String toString() {
		return "UsersDomain [userId=" + userId + ", userName=" + userName + ", userPassword=" + userPassword
				+ ", userMailId=" + userMailId + ", userRole=" + userRole + ", createdDate=" + createdDate
				+ ", updatedDate=" + updatedDate + "]";
	}

//	public boolean getStatus() {
//		return status;
//	}
//
//	public void setStatus(boolean status) {
//		this.status = status;
//	}

//	public String getConfirmPassword() {
//		return confirmPassword;
//	}
//
//	public void setConfirmPassword(String confirmPassword) {
//		this.confirmPassword = confirmPassword;
//	}
	

	
}
