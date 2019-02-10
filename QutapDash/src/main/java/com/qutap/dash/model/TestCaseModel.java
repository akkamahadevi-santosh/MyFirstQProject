package com.qutap.dash.model;

import java.io.Serializable;
import java.util.List;

public class TestCaseModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6503807222820347515L;
	private String testCaseId;
	private String testCaseName;
	private String runnerType;
	private String testCaseDesciption;
	private String testCaseCategory;
	private String testCasePriority;
	private String testCaseTag;
	private String PositiveOrNegative;
	private String testScenarioName;
	private String createdDate;
	private String modifiedDate;
	private List<TestStepModel> testStepList;

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

	public String getTestCaseId() {
		return testCaseId;
	}

	public void setTestCaseId(String testCaseId) {
		this.testCaseId = testCaseId;
	}

	public String getTestCaseName() {
		return testCaseName;
	}

	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}

	public String getTestCaseDesciption() {
		return testCaseDesciption;
	}

	public void setTestCaseDesciption(String testCaseDesciption) {
		this.testCaseDesciption = testCaseDesciption;
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

	public String getTestCaseTag() {
		return testCaseTag;
	}

	public void setTestCaseTag(String testCaseTag) {
		this.testCaseTag = testCaseTag;
	}

	public String getPositiveOrNegative() {
		return PositiveOrNegative;
	}

	public void setPositiveOrNegative(String positiveOrNegative) {
		PositiveOrNegative = positiveOrNegative;
	}
	public String getRunnerType() {
		return runnerType;
	}

	public void setRunnerType(String runnerType) {
		this.runnerType = runnerType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<TestStepModel> getTestStepList() {
		return testStepList;
	}

	public void setTestStepList(List<TestStepModel> testStepList) {
		this.testStepList = testStepList;
	}

	public String getTestScenarioName() {
		return testScenarioName;
	}

	public void setTestScenarioName(String testScenarioName) {
		this.testScenarioName = testScenarioName;
	}
	



}
