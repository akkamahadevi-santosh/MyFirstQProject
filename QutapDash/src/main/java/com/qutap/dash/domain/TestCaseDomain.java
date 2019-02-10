package com.qutap.dash.domain;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.qutap.dash.model.TestStepModel;


public class TestCaseDomain  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 492713766521382860L;
	//@Id
	private String testCaseId;
	private String testCaseName;
	private String runnerType;
	private String testCaseDesciption;
	private String testCaseCategory;
	private String testCasePriority;
	private String testCaseTag;
	private String PositiveOrNegative;
	private String excecuteOrSkip;
	private String testScenarioName;
	private String createdDate;
	private String modifiedDate;
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
	private List<TestStepDomain> testStepList;

	

	public String getExcecuteOrSkip() {
		return excecuteOrSkip;
	}
	public void setExcecuteOrSkip(String excecuteOrSkip) {
		this.excecuteOrSkip = excecuteOrSkip;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRunnerType() {
		return runnerType;
	}
	public void setRunnerType(String runnerType) {
		this.runnerType = runnerType;
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

	public String getTestCaseTag() {
		return testCaseTag;
	}
	public void setTestCaseTag(String testCaseTag) {
		this.testCaseTag = testCaseTag;
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
	public String getPositiveOrNegative() {
		return PositiveOrNegative;
	}
	public void setPositiveOrNegative(String positiveOrNegative) {
		PositiveOrNegative = positiveOrNegative;
	}
	public List<TestStepDomain> getTestStepList() {
		return testStepList;
	}
	public void setTestStepList(List<TestStepDomain> testStepList) {
		this.testStepList = testStepList;
	}
	public String getTestScenarioName() {
		return testScenarioName;
	}
	public void setTestScenarioName(String testScenarioName) {
		this.testScenarioName = testScenarioName;
	}
	@Override
	public String toString() {
		return "TestCaseDomain [testCaseId=" + testCaseId + ", testCaseName=" + testCaseName + ", runnerType="
				+ runnerType + ", testCaseDesciption=" + testCaseDesciption + ", testCaseCategory=" + testCaseCategory
				+ ", testCasePriority=" + testCasePriority + ", testCaseTag=" + testCaseTag + ", PositiveOrNegative="
				+ PositiveOrNegative + ", excecuteOrSkip=" + excecuteOrSkip + ", testScenarioName=" + testScenarioName
				+ ", testStepList=" + testStepList + "]";
	}
	
	
	

	
}
