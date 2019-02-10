package com.qutap.dash.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qutap.dash.commonUtils.Response;

import com.qutap.dash.model.TestCaseModel;

@Service
public interface TestCaseService {
	Response saveTestCase(List<TestCaseModel> testCaseModelList, String testScenarioId, String requirementId,
			String moduleId, String projectId);

	TestCaseModel getTestCaseById(String testCaseId, String testScenarioId, String requirementId, String moduleId,
			String projectId);

	TestCaseModel getTestCaseByIdOnly(String testCaseId,String testScenarioId);

	List<TestCaseModel> getTestCaseList(String testScenarioId, String requirementId, String moduleId, String projectId);

	Response deleteTestCase(String testCaseId, String testScenarioId, String requirementId, String moduleId,
			String projectId);

	Response updateTestCaseOnly(TestCaseModel testCaseModel);

	Response updateTestCase(TestCaseModel testCaseModel, String testScenarioId, String requirementId, String moduleId,
			String projectId);

	Response deleteAllTestCase(String testScenarioId, String requirementId, String moduleId, String projectId);

}
