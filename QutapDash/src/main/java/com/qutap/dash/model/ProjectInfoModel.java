package com.qutap.dash.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodecProvider;
import org.springframework.data.mongodb.core.mapping.Field;

import com.qutap.dash.domain.ModuleDomain;

public class ProjectInfoModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -704552328314642587L;
	
	private String projectId;
	private String projectName;
	private String groupId;
	private String groupName;
	private String projectDescription;
	private String createdDate;
	private String modifiedDate;
	private String userId;
	private List<ModuleDomain> moduleList;
	
	
	

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

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public List<ModuleDomain> getModuleList() {
		return moduleList;
	}

	public void setModuleList(List<ModuleDomain> moduleList) {
		this.moduleList = moduleList;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "ProjectInfoDomain [projectId=" + projectId + ", projectName=" + projectName + ", groupId=" + groupId
				+ ", groupName=" + groupName + ", projectDescription=" + projectDescription + ", createdDate="
				+ createdDate + ", modifiedDate=" + modifiedDate + ", userId=" + userId + ", moduleList=" + moduleList
				+ "]";
	}
	

}
