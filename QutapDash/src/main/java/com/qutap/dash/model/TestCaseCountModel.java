package com.qutap.dash.model;

import java.io.Serializable;
import java.util.List;

public class TestCaseCountModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6503807222820347515L;
//	private String testCaseId;
//	private String testCaseName;
//	private String testScenarioName;
	private String status;
	private long testCaseCount;
	private long totalTestCaseCount;
	
	
	
	
	
	public long getTotalTestCaseCount() {
		return totalTestCaseCount;
	}
	
	public void setTotalTestCaseCount(long totalTestCaseCount) {
		this.totalTestCaseCount = totalTestCaseCount;
	}
//	public String getTestCaseId() {
//		return testCaseId;
//	}
//	public void setTestCaseId(String testCaseId) {
//		this.testCaseId = testCaseId;
//	}
//	public String getTestCaseName() {
//		return testCaseName;
//	}
//	public void setTestCaseName(String testCaseName) {
//		this.testCaseName = testCaseName;
//	}
//	public String getTestScenarioName() {
//		return testScenarioName;
//	}
//	public void setTestScenarioName(String testScenarioName) {
//		this.testScenarioName = testScenarioName;
//	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getTestCaseCount() {
		return testCaseCount;
	}
	public void setTestCaseCount(long testCaseCount) {
		this.testCaseCount = testCaseCount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "TestCaseCountModel [testCaseCount=" + testCaseCount + ", totalTestCaseCount=" + totalTestCaseCount
				+ "]";
	}
	
	
	


}
