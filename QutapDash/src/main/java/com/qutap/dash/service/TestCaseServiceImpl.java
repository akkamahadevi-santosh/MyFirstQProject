package com.qutap.dash.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qutap.dash.commonUtils.DateUtility;
import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.customException.TestScenarioException;
import com.qutap.dash.domain.TestCaseDomain;
import com.qutap.dash.domain.TestScenarioDomain;
import com.qutap.dash.model.TestCaseModel;
import com.qutap.dash.model.TestScenarioModel;
import com.qutap.dash.repository.TestCaseDao;
import com.qutap.dash.repository.TestScenarioDao;

@Service
public class TestCaseServiceImpl implements TestCaseService {

	Logger log = LoggerFactory.getLogger(ModuleServiceImpl.class);

	@Autowired
	TestCaseDao testCaseDao;

	@Override
	public Response saveTestCase(List<TestCaseModel> testCaseModelList, String testScenarioId, String requirementId,
			String moduleId, String projectId) {
		try {
			List<TestCaseDomain> testCaseList = new ArrayList<>();
			testCaseModelList.stream().forEach(tmp -> {
				tmp.setTestCaseId(new ObjectId().toString());
				tmp.setCreatedDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));
				TestCaseDomain testCaseDomain = new TestCaseDomain();
				BeanUtils.copyProperties(tmp, testCaseDomain);
				testCaseList.add(testCaseDomain);
			});
			Response response = testCaseDao.saveTestCase(testCaseList, requirementId, moduleId, projectId);
			return response;
		} catch (Exception e) {
			log.error("error in deleting testScenario detail", e);
			throw new TestScenarioException("error in deleting testScenario detail");
		}
	}

	@Override
	public TestCaseModel getTestCaseByIdOnly(String testCaseId,String testScenarioId) {
		try {
			TestCaseDomain testCaseDomain = testCaseDao.getTestCaseByIdOnly(testCaseId,testScenarioId);
			TestCaseModel testCaseModel = new TestCaseModel();
			BeanUtils.copyProperties(testCaseDomain, testCaseModel);
			return testCaseModel;
		} catch (Exception e) {
			log.error("error in getting testSceanrio details", e);
			throw new TestScenarioException("error in getting testSceanrio details");
		}
	}

	@Override
	public TestCaseModel getTestCaseById(String testCaseId, String testScenarioId, String requirementId,
			String moduleId, String projectId) {
		try {
			TestCaseDomain testCaseDomain = testCaseDao.getTestCaseById(testCaseId, testScenarioId, requirementId,
					moduleId, projectId);
			TestCaseModel testCaseModel = new TestCaseModel();
			BeanUtils.copyProperties(testCaseDomain, testCaseModel);
			return testCaseModel;
		} catch (Exception e) {
			log.error("error in getting testSceanrio details", e);
			throw new TestScenarioException("error in getting testSceanrio details");
		}
	}

	@Override
	public List<TestCaseModel> getTestCaseList(String testScenarioId, String requirementId, String moduleId,
			String projectId) {
		try {
			List<TestCaseModel> testCaseModelList = new ArrayList<>();
			List<TestCaseDomain> testCaseDomainList = testCaseDao.getTestCaseList(testScenarioId, requirementId,
					moduleId, projectId);
			for (TestCaseDomain testCaseDomain : testCaseDomainList) {
				TestCaseModel testCaseModel = new TestCaseModel();
				BeanUtils.copyProperties(testCaseDomain, testCaseModel);
				testCaseModelList.add(testCaseModel);
			}
			return testCaseModelList;
		} catch (Exception e) {
			log.error("error in getting list of testScenario  details", e);
			throw new TestScenarioException("no testScenario Available");
		}
	}

	@Override
	public Response updateTestCaseOnly(TestCaseModel testCaseModel) {
		try {
			TestCaseDomain testCaseDomain = new TestCaseDomain();
			BeanUtils.copyProperties(testCaseModel, testCaseDomain);
			Response response = testCaseDao.updateTestCaseOnly(testCaseDomain);

			return response;
		} catch (Exception e) {
			log.error("error in updating testScenario details", e);
			throw new TestScenarioException("error in updating testScenario details");
		}
	}

	@Override
	public Response updateTestCase(TestCaseModel testCaseModel, String testScenarioId, String requirementId,
			String moduleId, String projectId) {
		try {
			TestCaseDomain testCaseDomain = new TestCaseDomain();
			BeanUtils.copyProperties(testCaseModel, testCaseDomain);
			Response response = testCaseDao.updateTestCase(testCaseDomain, testScenarioId, requirementId, moduleId,
					projectId);

			return response;
		} catch (Exception e) {
			log.error("error in updating testScenario details", e);
			throw new TestScenarioException("error in updating testScenario details");
		}
	}

	@Override
	public Response deleteTestCase(String testCaseId, String testScenarioId, String requirementId, String moduleId,
			String projectId) {
		try {

			Response response = testCaseDao.deleteTestCase(testCaseId, testScenarioId, requirementId, moduleId,
					projectId);
			return response;
		} catch (Exception e) {
			log.error("error in deleting testScenario detail", e);
			throw new TestScenarioException("error in deleting testScenario detail");
		}
	}

	@Override
	public Response deleteAllTestCase(String testScenarioId, String requirementId, String moduleId, String projectId) {
		try {

			Response response = testCaseDao.deleteAllTestCase(testScenarioId, requirementId, moduleId, projectId);
			return response;
		} catch (Exception e) {
			log.error("error in deleting testScenario detail", e);
			throw new TestScenarioException("error in deleting testScenario detail");
		}
	}

}