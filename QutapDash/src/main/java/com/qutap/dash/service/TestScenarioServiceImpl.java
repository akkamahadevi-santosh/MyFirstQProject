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
import com.qutap.dash.domain.TestScenarioDomain;
import com.qutap.dash.model.TestScenarioModel;
import com.qutap.dash.repository.TestScenarioDao;

@Service
public class TestScenarioServiceImpl implements TestScenarioService {
	Logger log = LoggerFactory.getLogger(ModuleServiceImpl.class);
	@Autowired
	TestScenarioDao testScenarioDao;

	@Override
	public Response saveTestScenario(List<TestScenarioModel> testScenarioModelList, String requirementId,
			String moduleId, String projectId) {
		try {
			List<TestScenarioDomain> testScenarioList = new ArrayList<>();
			testScenarioModelList.stream().forEach(tmp -> {
				tmp.setTestScenarioId(new ObjectId().toString());
				tmp.setCreatedDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));
				TestScenarioDomain testScenarioDomain = new TestScenarioDomain();
				BeanUtils.copyProperties(tmp, testScenarioDomain);
				testScenarioList.add(testScenarioDomain);
			});
			Response response = testScenarioDao.saveTestScenario(testScenarioList, requirementId, moduleId, projectId);
			return response;
		} catch (Exception e) {
			log.error("error in saving TestScenario detail", e);
			throw new TestScenarioException("error in saving TestScenario detail");
		}
	}

	@Override
	public TestScenarioModel getTestScenario(String testScenarioName, String requirementName, String moduleName,
			String projectName) {
		try {
			TestScenarioModel testScenarioModel = new TestScenarioModel();
			TestScenarioDomain testScenarioDomain = testScenarioDao.getTestScenarioByName(testScenarioName,
					requirementName, moduleName, projectName);
			BeanUtils.copyProperties(testScenarioModel, testScenarioDomain);
			return testScenarioModel;
		} catch (Exception e) {
			log.error("error in getting testScenario detail", e);
			throw new TestScenarioException("error in getting testScenario detail");
		}
	}

	@Override
	public TestScenarioModel getTestScenarioById(String testScenarioId, String requirementId, String moduleId,
			String projectId) {
		try {
			TestScenarioDomain testSceanrioDomain = testScenarioDao.getTestScenarioById(testScenarioId, requirementId,
					moduleId, projectId);
			TestScenarioModel testScanarioModel = new TestScenarioModel();
			BeanUtils.copyProperties(testSceanrioDomain, testScanarioModel);
			return testScanarioModel;
		} catch (Exception e) {
			log.error("error in getting testSceanrio details", e);
			throw new TestScenarioException("error in getting testSceanrio details");
		}
	}

	@Override
	public TestScenarioModel getTestScenarioById(String testScenarioId) {
		try {
			TestScenarioDomain testSceanrioDomain = testScenarioDao.getTestScenarioByIdOnly(testScenarioId);
			TestScenarioModel testScanarioModel = new TestScenarioModel();
			BeanUtils.copyProperties(testSceanrioDomain, testScanarioModel);
			return testScanarioModel;
		} catch (Exception e) {
			log.error("error in getting testSceanrio details", e);
			throw new TestScenarioException("error in getting testSceanrio details");
		}
	}

	@Override
	public List<TestScenarioModel> getTestScenarioList(String requirementId, String moduleId, String projectId) {
		try {
			List<TestScenarioModel> testScenarioModelList = new ArrayList<TestScenarioModel>();
			List<TestScenarioDomain> testScenarioDomainList = testScenarioDao.getTestScenarioList(requirementId,
					moduleId, projectId);
			for (TestScenarioDomain testScenarioDomain : testScenarioDomainList) {
				TestScenarioModel testScenarioModel = new TestScenarioModel();
				BeanUtils.copyProperties(testScenarioDomain, testScenarioModel);
				testScenarioModelList.add(testScenarioModel);
			}
			return testScenarioModelList;
		} catch (Exception e) {
			log.error("error in getting list of testScenario  details", e);
			throw new TestScenarioException("no testScenario Available");
		}
	}

	@Override
	public Response updateTestScenario(TestScenarioModel testScenarioModel, String requirementId, String ModuleId,
			String projectId) {
		try {
			TestScenarioDomain testScenarioDomain = new TestScenarioDomain();
			BeanUtils.copyProperties(testScenarioModel, testScenarioDomain);
			Response response = testScenarioDao.updateTestScenario(testScenarioDomain, requirementId, ModuleId,
					projectId);

			return response;
		} catch (Exception e) {
			log.error("error in updating testScenario details", e);
			throw new TestScenarioException("error in updating testScenario details");
		}
	}

	@Override
	public Response deleteTestScenario(String testScenarioId, String requirementId, String moduleId, String projectId) {
		try {

			Response response = testScenarioDao.deleteTestScenario(testScenarioId, requirementId, moduleId, projectId);
			return response;
		} catch (Exception e) {
			log.error("error in deleting testScenario detail", e);
			throw new TestScenarioException("error in deleting testScenario detail");
		}
	}

	@Override
	public Response deleteAllTestScenario(String requirementId, String moduleId, String projectId) {
		try {

			Response response = testScenarioDao.deleteAllTestScenario(requirementId, moduleId, projectId);
			return response;
		} catch (Exception e) {
			log.error("error in deleting testScenario detail", e);
			throw new TestScenarioException("error in deleting testScenario detail");
		}
	}

}