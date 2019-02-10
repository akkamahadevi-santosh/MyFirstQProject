package com.qutap.dash.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.bson.codecs.UuidCodecProvider;
import org.springframework.data.annotation.Id;

public class ModuleModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3047911934691716032L;
	private String moduleId;
	private String moduleName;
	private String moduleDescription;
	private String createdDate;
	private String modifiedDate;
	private List<RequirementModel> requirementList;

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

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public List<RequirementModel> getRequirementList() {
		return requirementList;
	}

	public void setRequirementList(List<RequirementModel> requirementList) {
		this.requirementList = requirementList;
	}

	public String getModuleDescription() {
		return moduleDescription;
	}

	public void setModuleDescription(String moduleDescription) {
		this.moduleDescription = moduleDescription;
	}

	@Override
	public String toString() {
		return "ModuleModel [moduleId=" + moduleId + ", moduleName=" + moduleName + ", moduleDescription="
				+ moduleDescription + ", requirementList=" + requirementList + "]";
	}

}
