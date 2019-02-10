package com.qutap.dash.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;

public class RequirementModel implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6812313138564800129L;
	private String requirementId;
	private String requirementName;
	private String requirementCases;
	private String requirementDescription;
	private String createdDate;
	private String modifiedDate;
	private List<TestScenarioModel> testScenarioList;
	
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
	public String getRequirementId() {
		return requirementId;
	}
	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
	}
	public String getRequirementName() {
		return requirementName;
	}
	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}
	public String getRequirementCases() {
		return requirementCases;
	}
	public void setRequirementCases(String requirementCases) {
		this.requirementCases = requirementCases;
	}
	public String getRequirementDescription() {
		return requirementDescription;
	}
	public void setRequirementDescription(String requirementDescription) {
		this.requirementDescription = requirementDescription;
	}
	public List<TestScenarioModel> getTestScenarioList() {
		return testScenarioList;
	}
	public void setTestScenarioList(List<TestScenarioModel> testScenarioList) {
		this.testScenarioList = testScenarioList;
	}
	
	

}
