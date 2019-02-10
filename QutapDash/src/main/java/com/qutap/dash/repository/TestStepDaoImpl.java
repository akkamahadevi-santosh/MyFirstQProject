package com.qutap.dash.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.commonUtils.StatusCode;
import com.qutap.dash.commonUtils.Utils;
import com.qutap.dash.customException.TestStepException;
import com.qutap.dash.domain.ModuleDomain;
import com.qutap.dash.domain.ProjectInfoDomain;
import com.qutap.dash.domain.RequirementDomain;
import com.qutap.dash.domain.TestCaseDomain;
import com.qutap.dash.domain.TestScenarioDomain;
import com.qutap.dash.domain.TestStepDomain;

@Repository
@Transactional
public class TestStepDaoImpl implements TestStepDao {

	Logger log = LoggerFactory.getLogger(ModuleDaoImpl.class);
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public Response saveTestStep(List<TestStepDomain> testStepList, String testCaseId) {
		Response response = Utils.getResponseObject("updating TestCase Details");
		try {
						Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.requirementList.testScenarioList.testCaseList.testCaseId")
					.is(testCaseId));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			
			if (projectInfo != null) {
				
				List<ModuleDomain> moduleDomainList = projectInfo.getModuleList();
				
				for (ModuleDomain moduleDomain : moduleDomainList) {
					List<RequirementDomain> requirementDomainlist = moduleDomain.getRequirementList();
					for (RequirementDomain requirementDomain : requirementDomainlist) {
						List<TestScenarioDomain> testScenarioDomainList = requirementDomain.getTestScenarioList();
						for (TestScenarioDomain testScenarioDomain : testScenarioDomainList) {
							
							List<TestCaseDomain> testCaseList  = testScenarioDomain.getTestCaseList();
				  System.out.println(testCaseList.size());
				  for (TestCaseDomain testCaseDomain : testCaseList) {
						
						if(testCaseDomain.getTestCaseId().equalsIgnoreCase(testCaseId)) {
							Update update = new Update();

							testCaseDomain.setTestStepList(testStepList);
							update.set("moduleList", moduleDomainList);
						mongoTemplate.upsert(query, update, "projectInfo");
						
						}
						response.setData(testCaseDomain);	
							}
							
							
						}
					}

				
			
			}response.setStatus(StatusCode.SUCCESS.name());
			return response;
			}
			return null;
		} catch (Exception e) {
			log.error("error in getting testStep detail", e);
			throw new TestStepException("error in getting testStep detail");
		}
	}

	@Override
	public TestStepDomain getTestStepById(String testStepsId, String testCaseId, String testScenarioId,
			String requirementId, String moduleId, String projectId) {
		Response response = Utils.getResponseObject("getting testStep Details");
		try {

			Query query = new Query();
			query.addCriteria(
					Criteria.where("moduleList.requirementList.testScenarioList.testCaseList.testStepList.testStepsId")
							.is(testStepsId));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			ModuleDomain moduleDomain = projectInfo.getModuleList().stream()
					.filter(i -> i.getModuleId().equalsIgnoreCase(moduleId)).collect(Collectors.toList()).get(0);
			RequirementDomain requirementDomain = moduleDomain.getRequirementList().stream()
					.filter(i -> i.getRequirementId().equalsIgnoreCase(requirementId)).collect(Collectors.toList())
					.get(0);
			TestScenarioDomain testScenarioDomain = requirementDomain.getTestScenarioList().stream()
					.filter(i -> i.getTestScenarioId().equalsIgnoreCase(testScenarioId)).collect(Collectors.toList())
					.get(0);
			TestCaseDomain testCaseDomain = testScenarioDomain.getTestCaseList().stream()
					.filter(i -> i.getTestCaseId().equalsIgnoreCase(testCaseId)).collect(Collectors.toList()).get(0);
			return testCaseDomain.getTestStepList().stream()
					.filter(i -> i.getTestStepsId().equalsIgnoreCase(testStepsId)).collect(Collectors.toList()).get(0);
		} catch (Exception e) {
			log.error("error in getting testStep detail", e);
			throw new TestStepException("error in getting testStep detail");
		}
	}

	@Override
	public List<TestStepDomain> getTestStepList(String testCaseId, String testScenarioId, String requirementId,
			String moduleId, String projectId) {
		Response response = Utils.getResponseObject("getting testCaseList Details");
		try {

			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.requirementList.testScenarioList.testCaseList.testCaseId")
					.is(testCaseId).andOperator(Criteria
							.where("moduleList.requirementList.testScenarioList.testScenarioId").is(testScenarioId)));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);

			ModuleDomain moduleDomain = projectInfo.getModuleList().stream()
					.filter(i -> i.getModuleId().equalsIgnoreCase(moduleId)).collect(Collectors.toList()).get(0);
			RequirementDomain requirementDomain = moduleDomain.getRequirementList().stream()
					.filter(i -> i.getRequirementId().equalsIgnoreCase(requirementId)).collect(Collectors.toList())
					.get(0);
			TestScenarioDomain testScenarioDomain = requirementDomain.getTestScenarioList().stream()
					.filter(i -> i.getTestScenarioId().equalsIgnoreCase(testScenarioId)).collect(Collectors.toList())
					.get(0);
			TestCaseDomain testCaseDomain = testScenarioDomain.getTestCaseList().stream()
					.filter(i -> i.getTestCaseId().equalsIgnoreCase(testCaseId)).collect(Collectors.toList()).get(0);
			return testCaseDomain.getTestStepList();
		} catch (Exception e) {
			log.error("error in getting testStepList detail", e);
			throw new TestStepException("error in getting testStepList detail");
		}
	}

	@Override
	public Response updateTestStep(TestStepDomain testStepDomain, String testCaseId, String testScenarioId,
			String requirementId, String moduleId, String projectId) {
		Response response = Utils.getResponseObject("updating TestCase Details");
		try {
			String testStepId = testStepDomain.getTestStepsId();
			Query query = new Query();
			query.addCriteria(
					Criteria.where("moduleList.requirementList.testScenarioList.testCaseList.testStepList.testStepsId")
							.is(testStepId)
							.andOperator(Criteria
									.where("moduleList.requirementList.testScenarioList.testCaseList.testCaseId")
									.is(testCaseId)));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);

			ModuleDomain moduleDomain = projectInfo.getModuleList().stream()
					.filter(i -> i.getModuleId().equalsIgnoreCase(moduleId)).collect(Collectors.toList()).get(0);
			RequirementDomain requirementDomain = moduleDomain.getRequirementList().stream()
					.filter(i -> i.getRequirementId().equalsIgnoreCase(requirementId)).collect(Collectors.toList())
					.get(0);
			TestScenarioDomain testScenarioDomain = requirementDomain.getTestScenarioList().stream()
					.filter(i -> i.getTestScenarioId().equalsIgnoreCase(testScenarioId)).collect(Collectors.toList())
					.get(0);
			TestCaseDomain testCaseDomain = testScenarioDomain.getTestCaseList().stream()
					.filter(i -> i.getTestCaseId().equalsIgnoreCase(testCaseId)).collect(Collectors.toList()).get(0);
			TestStepDomain testStDomain = testCaseDomain.getTestStepList().stream()
					.filter(i -> i.getTestStepsId().equalsIgnoreCase(testStepId)).collect(Collectors.toList()).get(0);
			if (!(testStDomain.getAction().equalsIgnoreCase(testStepDomain.getAction()))
					&& testStepDomain.getAction() != null) {
				testStDomain.setAction(testStepDomain.getAction());

			}
			if (!(testStDomain.getStepParam().equalsIgnoreCase(testStepDomain.getStepParam()))
					&& testStepDomain.getStepParam() != null) {

				testStDomain.setStepParam(testStepDomain.getStepParam());

			}
			if (!(testStDomain.getDependency().equalsIgnoreCase(testStepDomain.getDependency()))
					&& testStepDomain.getDependency() != null) {

				testStDomain.setDependency(testStepDomain.getDependency());

			}
			if (!(testStDomain.getParamGroupObject().equalsIgnoreCase(testStepDomain.getParamGroupObject()))
					&& testStepDomain.getParamGroupObject() != null) {

				testStDomain.setParamGroupObject(testStepDomain.getParamGroupObject());

			}

			List<ModuleDomain> moduleDomainList = projectInfo.getModuleList();
			Update update = new Update();
			update.set("moduleList", moduleDomainList);
			mongoTemplate.upsert(query, update, "projectInfo");
			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(testStDomain);
			return response;
		} catch (Exception e) {
			log.error("error in getting testStep detail", e);
			throw new TestStepException("error in getting testStep detail");
		}
	}

	@Override
	public Response deleteTestStep(String testStepsId, String testCaseId, String testScenarioId, String requirementId,
			String moduleId, String projectId) {
		Response response = Utils.getResponseObject("deleting testStep Details");

		try {
			Query query = new Query();
			query.addCriteria(
					Criteria.where("moduleList.requirementList.testScenarioList.testCaseList.testStepList.testStepsId")
							.is(testStepsId)
							.andOperator(Criteria
									.where("moduleList.requirementList.testScenarioList.testCaseList.testCaseId")
									.is(testCaseId)));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);

			ModuleDomain moduleDomain = projectInfo.getModuleList().stream()
					.filter(i -> i.getModuleId().equalsIgnoreCase(moduleId)).collect(Collectors.toList()).get(0);
			RequirementDomain requirementDomain = moduleDomain.getRequirementList().stream()
					.filter(i -> i.getRequirementId().equalsIgnoreCase(requirementId)).collect(Collectors.toList())
					.get(0);
			TestScenarioDomain testScenarioDomain = requirementDomain.getTestScenarioList().stream()
					.filter(i -> i.getTestScenarioId().equalsIgnoreCase(testScenarioId)).collect(Collectors.toList())
					.get(0);
			TestCaseDomain testCaseDomain = testScenarioDomain.getTestCaseList().stream()
					.filter(i -> i.getTestCaseId().equalsIgnoreCase(testCaseId)).collect(Collectors.toList()).get(0);
			testCaseDomain.getTestStepList().removeIf(i -> i.getTestStepsId().equalsIgnoreCase(testStepsId));
			Update update = new Update();
			List<ModuleDomain> moduleDomainList = projectInfo.getModuleList();
			update.set("moduleList", moduleDomainList);
			mongoTemplate.upsert(query, update, "projectInfo");
			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(testScenarioDomain);
			return response;
		} catch (Exception e) {
			log.error("error in deleting testStepList detail", e);
			throw new TestStepException("error in deleting testStepList detail");
		}
	}

}