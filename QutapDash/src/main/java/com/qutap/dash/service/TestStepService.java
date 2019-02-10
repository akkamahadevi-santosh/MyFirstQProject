package com.qutap.dash.service;

import java.util.List;

import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.model.TestStepModel;

public interface TestStepService {
	TestStepModel getTestStepById(String testStepsId,String testCaseId,String testScenarioId, String requirementId, String moduleId,
			String projectId);
	 List<TestStepModel> getTestStepList(String testCaseId,String testScenarioId, String requirementId, String moduleId,
			String projectId);
		Response updateTestStep(TestStepModel testStepModel,String testCaseId, String testScenarioId, String requirementId, String moduleId,
			String projectId);

	Response deleteTestStep(String testStepsId,String testCaseId, String testScenarioId, String requirementId, String moduleId,
			String projectId);

}