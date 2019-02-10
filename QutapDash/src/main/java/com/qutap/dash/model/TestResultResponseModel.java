package com.qutap.dash.model;

import java.util.ArrayList;
import java.util.List;

public class TestResultResponseModel {
		 private String projectName;
		 private String projectId;
		 private String executedBy;
		 private String executionDate;	 
		 private String apiVersion;
		 private String service;	 
		 private String txnId;
		 List<TestCaseExecutionListModel> testCaseExecutionList;
		public String getProjectName() {
			return projectName;
		}
		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}
		public String getProjectId() {
			return projectId;
		}
		public void setProjectId(String projectId) {
			this.projectId = projectId;
		}
		public String getExecutedBy() {
			return executedBy;
		}
		public void setExecutedBy(String executedBy) {
			this.executedBy = executedBy;
		}
		public String getExecutionDate() {
			return executionDate;
		}
		public void setExecutionDate(String executionDate) {
			this.executionDate = executionDate;
		}
		public String getApiVersion() {
			return apiVersion;
		}
		public void setApiVersion(String apiVersion) {
			this.apiVersion = apiVersion;
		}
		public String getService() {
			return service;
		}
		public void setService(String service) {
			this.service = service;
		}
		
		public String getTxnId() {
			return txnId;
		}
		public void setTxnId(String txnId) {
			this.txnId = txnId;
		}
		public List<TestCaseExecutionListModel> getTestCaseExecutionList() {
			return testCaseExecutionList;
		}
		public void setTestCaseExecutionList(List<TestCaseExecutionListModel> testCaseExecutionList) {
			this.testCaseExecutionList = testCaseExecutionList;
		}
		@Override
		public String toString() {
			return "TestResultResponseDomain [projectName=" + projectName + ", projectId=" + projectId + ", executedBy="
					+ executedBy + ", executionDate=" + executionDate + ", apiVersion=" + apiVersion + ", service="
					+ service + ", txnId=" + txnId + ", testCaseExecutionList="
					+ testCaseExecutionList + "]";
		}
		 
		 
		
		


		
		

		

		

		
		

}
