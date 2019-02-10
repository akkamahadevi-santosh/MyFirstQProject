package com.qutap.dash.model;

import java.util.List;

import com.qutap.dash.domain.TestStepExecutionListDomain;

public class TestCaseExecutionListModel {
	private String testCaseDescription;
	 private String testCaseName;
	 private String testCaseId;
	 private String testExecutionId;
	 private String testCaseTag;
	 private String moduleName;
	 private String requirementName;
	 private String duration;
	 private String testCaseCategory;
	 private String testCasePriority;
	 private String status;
	 List<TestStepExecutionListModel> TestResult;
	public String getTestCaseDescription() {
		return testCaseDescription;
	}
	public void setTestCaseDescription(String testCaseDescription) {
		this.testCaseDescription = testCaseDescription;
	}
	public String getTestCaseName() {
		return testCaseName;
	}
	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}
	public String getTestCaseId() {
		return testCaseId;
	}
	public void setTestCaseId(String testCaseId) {
		this.testCaseId = testCaseId;
	}
	public String getTestExecutionId() {
		return testExecutionId;
	}
	public void setTestExecutionId(String testExecutionId) {
		this.testExecutionId = testExecutionId;
	}
	public String getTestCaseTag() {
		return testCaseTag;
	}
	public void setTestCaseTag(String testCaseTag) {
		this.testCaseTag = testCaseTag;
	}
	public List<TestStepExecutionListModel> getTestResult() {
		return TestResult;
	}
	public void setTestResult(List<TestStepExecutionListModel> testResult) {
		TestResult = testResult;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getRequirementName() {
		return requirementName;
	}
	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getTestCaseCategory() {
		return testCaseCategory;
	}
	public void setTestCaseCategory(String testCaseCategory) {
		this.testCaseCategory = testCaseCategory;
	}
	public String getTestCasePriority() {
		return testCasePriority;
	}
	public void setTestCasePriority(String testCasePriority) {
		this.testCasePriority = testCasePriority;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "TestCaseExecutionList [testCaseDescription=" + testCaseDescription + ", testCaseName=" + testCaseName
				+ ", testCaseId=" + testCaseId + ", testExecutionId=" + testExecutionId + ", testCaseTag=" + testCaseTag
				+ ", TestResult=" + TestResult + "]";
	}

	

	
	
}
