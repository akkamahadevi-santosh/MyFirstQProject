package com.qutap.dash.service;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

import org.json.JSONObject;

import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.model.TestCaseCountModel;
import com.qutap.dash.model.TestResultResponseModel;

public interface ExecutionService {

	public TestResultResponseModel executeData(String testScenarioName, String requirementName, String moduleName, String projectName);

	public TestResultResponseModel executeAllTestCases(String projectId,List<String> listOfTestSuiteIds,String dsid);
	public TestResultResponseModel executeAllTestCases(String projectId);
	public TestCaseCountModel getTestCaseCount();

	//public TestCaseCountModel gethDataFromHazelCast();
	public JSONObject getTotalNoOFTestCases();
   
	

	

}
