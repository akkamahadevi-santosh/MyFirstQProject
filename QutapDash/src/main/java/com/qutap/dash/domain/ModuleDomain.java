package com.qutap.dash.domain;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.annotation.Generated;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class ModuleDomain implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1437529900797636670L;
	// @Id
	private String moduleId;
	private String moduleName;
	private String moduleDescription;
	private String createdDate;
	private String modifiedDate;
	private List<RequirementDomain> requirementList;

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

	public void setModuleId(String string) {
		this.moduleId = string;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleDescription() {
		return moduleDescription;
	}

	public void setModuleDescription(String moduleDescription) {
		this.moduleDescription = moduleDescription;
	}

	public List<RequirementDomain> getRequirementList() {
		return requirementList;
	}

	public void setRequirementList(List<RequirementDomain> requirementList) {
		this.requirementList = requirementList;
	}

	@Override
	public String toString() {
		return "ModuleDomain [moduleId=" + moduleId + ", moduleName=" + moduleName + ", moduleDescription="
				+ moduleDescription + ", requirementList=" + requirementList + "]";
	}

}
