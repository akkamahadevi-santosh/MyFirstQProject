package com.qutap.dash.domain;
 
import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "testSuiteData")
public class TestSuiteDomain implements Serializable {
	
	@Id
	@Field("testSuiteId")
	private String testSuiteId; 
	private String testSuiteName;
	private String projectId;

	private String createdDate;
	private String modifiedDate;
    private List<String> testCaseIdList;
	public String getTestSuiteId() {
		return testSuiteId;
	}
	public void setTestSuiteId(String testSuiteId) {
		this.testSuiteId = testSuiteId;
	}
	public String getTestSuiteName() {
		return testSuiteName;
	}
	public void setTestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
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
	public List<String> getTestCaseIdList() {
		return testCaseIdList;
	}
	public void setTestCaseIdList(List<String> testCaseIdList) {
		this.testCaseIdList = testCaseIdList;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	@Override
	public String toString() {
		return "TestSuiteDomain [testSuiteId=" + testSuiteId + ", testSuiteName=" + testSuiteName + ", projectId="
				+ projectId + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", testCaseIdList="
				+ testCaseIdList + "]";
	}

	
}
