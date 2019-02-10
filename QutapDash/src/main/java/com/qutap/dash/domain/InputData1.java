package com.qutap.dash.domain;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.qutap.dash.domain.TestCaseDomain;
import com.qutap.dash.model.AgentMeta;

public class InputData1 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String testExecutionId;
	private AgentMeta agentMeta;
	private String apiVersion;
	private String plugin;
    private TestCaseDomain testCaseDomain;

	private String service;
	private String txnId;
	private String projectName;
	private String projectId;	
	private String executedBy;
	private String executionDate;

	
	

	public TestCaseDomain getTestCaseDomain() {
		return testCaseDomain;
	}

	public void setTestCaseDomain(TestCaseDomain testCaseDomain) {
		this.testCaseDomain = testCaseDomain;
	}

	
	
	
	
	public AgentMeta getAgentMeta() {
		return agentMeta;
	}

	
	public void setAgentMeta(AgentMeta agentMeta) {
		this.agentMeta = agentMeta;
	}

	

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getPlugin() {
		return plugin;
	}

	public void setPlugin(String plugin) {
		this.plugin = plugin;
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

	public String getTestExecutionId() {
		return testExecutionId;
	}

	public void setTestExecutionId(String testExecutionId) {
		this.testExecutionId = testExecutionId;
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
	
	
	
	

}
