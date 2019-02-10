package com.qutap.dash.domain;

import java.io.File;
import java.util.List;

public class TestCaseExecutionListDomain {
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
	 private String testCaseExecutionVideo; 
	 List<TestStepExecutionListDomain> TestResult;
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
	public List<TestStepExecutionListDomain> getTestResult() {
		return TestResult;
	}
	public void setTestResult(List<TestStepExecutionListDomain> testResult) {
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
	
	
	public String getTestCaseExecutionVideo() {
		return testCaseExecutionVideo;
	}
	public void setTestCaseExecutionVideo(String testCaseExecutionVideo) {
		this.testCaseExecutionVideo = testCaseExecutionVideo;
	}
	@Override
	public String toString() {
		return "TestCaseExecutionListDomain [testCaseDescription=" + testCaseDescription + ", testCaseName="
				+ testCaseName + ", testCaseId=" + testCaseId + ", testExecutionId=" + testExecutionId
				+ ", testCaseTag=" + testCaseTag + ", moduleName=" + moduleName + ", requirementName=" + requirementName
				+ ", duration=" + duration + ", testCaseCategory=" + testCaseCategory + ", testCasePriority="
				+ testCasePriority + ", status=" + status + ", testCaseExecutionVideo=" + testCaseExecutionVideo
				+ ", TestResult=" + TestResult + "]";
	}
	
		
}
