package com.qutap.dash.domain;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.qutap.dash.model.TestCaseModel;

public class TestScenarioDomain implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1203441579510190923L;
	//@Id
	private String testScenarioId;
	private String testScenarioName;
	private String testScenarioDescription;
	private String createdDate;
	private String modifiedDate;
	private List<TestCaseDomain> testCaseList;

	@Override
	public String toString() {
		return "TestScenarioDomain [testScenarioId=" + testScenarioId + ", testScenarioName=" + testScenarioName
				+ ", testScenarioDescription=" + testScenarioDescription + ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + ", testCaseList=" + testCaseList + "]";
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

	public String getTestScenarioId() {
		return testScenarioId;
	}

	public void setTestScenarioId(String testScenarioId) {
		this.testScenarioId = testScenarioId;
	}

	public String getTestScenarioName() {
		return testScenarioName;
	}

	public void setTestScenarioName(String testScenarioName) {
		this.testScenarioName = testScenarioName;
	}

	public String getTestScenarioDescription() {
		return testScenarioDescription;
	}

	public void setTestScenarioDescription(String testScenarioDescription) {
		this.testScenarioDescription = testScenarioDescription;
	}

	public List<TestCaseDomain> getTestCaseList() {
		return testCaseList;
	}

	public void setTestCaseList(List<TestCaseDomain> testCaseList) {
		this.testCaseList = testCaseList;
	}

}
