package com.qutap.dash.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.domain.TestScenarioDomain;
import com.qutap.dash.model.RequirementModel;
import com.qutap.dash.model.TestScenarioModel;

@Service
public interface TestScenarioService {
	Response saveTestScenario(List<TestScenarioModel> testScenarioDomain, String requirementId, String moduleId,
			String projectId);

	TestScenarioModel getTestScenario(String TestScenarioName, String requirementName, String moduleName,
			String projectName);

	TestScenarioModel getTestScenarioById(String testScenarioId);

	List<TestScenarioModel> getTestScenarioList(String requirementId, String moduleId, String projectId);

	TestScenarioModel getTestScenarioById(String testScenarioId, String requirementId, String moduleId,
			String projectId);

	Response updateTestScenario(TestScenarioModel testScenarioDomain, String requirementId, String ModuleId,
			String projectId);

	Response deleteTestScenario(String TestScenarioName, String requirementName, String moduleName, String projectName);

	Response deleteAllTestScenario(String requirementId, String moduleId, String projectId);

}