package com.qutap.dash.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.customException.TestStepException;

import com.qutap.dash.domain.TestStepDomain;

import com.qutap.dash.model.TestStepModel;
import com.qutap.dash.repository.TestStepDao;

@Service
public class TestStepServiceImpl implements TestStepService {
	Logger log = LoggerFactory.getLogger(ModuleServiceImpl.class);
	@Autowired
	TestStepDao testStepDao;

	@Override
	public TestStepModel getTestStepById(String testStepsId, String testCaseId, String testScenarioId,
			String requirementId, String moduleId, String projectId) {
		try {
			TestStepDomain testStepDomain = testStepDao.getTestStepById(testStepsId, testCaseId, testScenarioId,
					requirementId, moduleId, projectId);
			TestStepModel testStepModel = new TestStepModel();
			BeanUtils.copyProperties(testStepDomain, testStepModel);
			return testStepModel;
		} catch (Exception e) {
			log.error("error in getting testStep detail", e);
			throw new TestStepException("error in getting testStep detail");
		}
	}

	@Override
	public List<TestStepModel> getTestStepList(String testCaseId, String testScenarioId, String requirementId,
			String moduleId, String projectId) {
		try {
			List<TestStepModel> testStepModelList = new ArrayList<>();
			List<TestStepDomain> testStepDomainList = testStepDao.getTestStepList(testCaseId, testScenarioId,
					requirementId, moduleId, projectId);
			for (TestStepDomain testStepDomain : testStepDomainList) {
				TestStepModel testStepModel = new TestStepModel();
				BeanUtils.copyProperties(testStepDomain, testStepModel);
				testStepModelList.add(testStepModel);
			}
			return testStepModelList;
		} catch (Exception e) {
			log.error("error in getting testStepList detail", e);
			throw new TestStepException("error in getting testStepList detail");
		}
	}

	@Override
	public Response updateTestStep(TestStepModel testStepModel, String testCaseId, String testScenarioId,
			String requirementId, String moduleId, String projectId) {
		try {
			TestStepDomain testStepDomain = new TestStepDomain();
			BeanUtils.copyProperties(testStepModel, testStepDomain);
			Response response = testStepDao.updateTestStep(testStepDomain, testCaseId, testScenarioId, requirementId,
					moduleId, projectId);

			return response;
		} catch (Exception e) {
			log.error("error in updating testStepList detail", e);
			throw new TestStepException("error in updating testStepList detail");
		}

	}

	@Override
	public Response deleteTestStep(String testStepsId, String testCaseId, String testScenarioId, String requirementId,
			String moduleId, String projectId) {
		try {

			Response response = testStepDao.deleteTestStep(testStepsId, testCaseId, testScenarioId, requirementId,
					moduleId, projectId);
			return response;
		} catch (Exception e) {
			log.error("error in deleting testStepList detail", e);
			throw new TestStepException("error in deleting testStepList detail");
		}
	}

}