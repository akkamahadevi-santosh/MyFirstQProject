package com.qutap.dash.repository;

import java.util.List;

import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.domain.TestCaseDomain;
import com.qutap.dash.domain.TestStepDomain;

public interface TestStepDao {

	Response saveTestStep(List<TestStepDomain> testStepList, String testCaseId);

	TestStepDomain getTestStepById(String testStepsId, String testCaseId, String testScenarioId, String requirementId,
			String moduleId, String projectId);

	List<TestStepDomain> getTestStepList(String testCaseId, String testScenarioId, String requirementId,
			String moduleId, String projectId);

	Response updateTestStep(TestStepDomain testStepDomain, String testCaseId, String testScenarioId,
			String requirementId, String moduleId, String projectId);

	Response deleteTestStep(String testStepsId, String testCaseId, String testScenarioId, String requirementId,
			String moduleId, String projectId);

}