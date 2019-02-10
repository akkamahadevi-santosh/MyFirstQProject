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
import com.qutap.dash.customException.ModuleException;
import com.qutap.dash.customException.TestCaseException;
import com.qutap.dash.customException.TestScenarioException;
import com.qutap.dash.domain.ModuleDomain;
import com.qutap.dash.domain.ProjectInfoDomain;
import com.qutap.dash.domain.RequirementDomain;
import com.qutap.dash.domain.TestCaseDomain;
import com.qutap.dash.domain.TestScenarioDomain;

@Repository
@Transactional
public class TestCaseDaoImpl implements TestCaseDao {

	Logger log = LoggerFactory.getLogger(ModuleDaoImpl.class);
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public Response saveTestCase(List<TestCaseDomain> testCaseDomainList, String requirementId, String moduleId,
			String projectId) {
		Response response = Utils.getResponseObject("Adding testcase and testStep Details");
		String testScenarioName = testCaseDomainList.stream().map(tmp -> tmp.getTestScenarioName())
				.collect(Collectors.toList()).get(0).trim();

		try {
			Query query = new Query();
			query.addCriteria(
					Criteria.where("moduleList.requirementList.testScenarioList.testScenarioName").is(testScenarioName)
							.andOperator(Criteria.where("moduleList.requirementList.requirementId").is(requirementId)));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);

			if (projectInfo != null) {

				ModuleDomain moduleDomain = projectInfo.getModuleList().stream()
						.filter(i -> i.getModuleId().equalsIgnoreCase(moduleId)).collect(Collectors.toList()).get(0);
				List<RequirementDomain> requirementDomainList = moduleDomain.getRequirementList().stream()
						.filter(i -> i.getRequirementId().equalsIgnoreCase(requirementId)).collect(Collectors.toList());
				if (!requirementDomainList.isEmpty()) {
					RequirementDomain requirementDomain = requirementDomainList.get(0);

					TestScenarioDomain testScenarioDomain = requirementDomain.getTestScenarioList().stream()
							.filter(i -> i.getTestScenarioName().equalsIgnoreCase(testScenarioName))
							.collect(Collectors.toList()).get(0);
					List<ModuleDomain> moduleDomainList = projectInfo.getModuleList();
					Update update = new Update();
					if (testScenarioDomain.getTestCaseList() == null) {
						testScenarioDomain.setTestCaseList(testCaseDomainList);
						update.set("moduleList", moduleDomainList);
						mongoTemplate.upsert(query, update, "projectInfo");
					} else {

						for (TestCaseDomain testCase : testCaseDomainList) {

							List<TestCaseDomain> testCaseList = testScenarioDomain.getTestCaseList();

							if (!testCaseList.stream()
									.anyMatch(i -> i.getTestCaseId().equalsIgnoreCase(testCase.getTestCaseId()))) {
								testCaseList.add(testCase);
								update.set("moduleList", moduleDomainList);
								mongoTemplate.upsert(query, update, "projectInfo");
							}

						}
					}
					response.setStatus(StatusCode.SUCCESS.name());
					response.setData(testScenarioDomain);
					return response;
				}
			}
			return null;
		} catch (Exception e) {
			log.error("error in saving testCase detail", e);
			throw new TestCaseException("error in saving testCase detail");
		}
	}

	@Override
	public TestCaseDomain getTestCaseById(String testCaseId, String testScenarioId, String requirementId,
			String moduleId, String projectId) {
		Response response = Utils.getResponseObject("getting testCase Details");
		try {

			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.requirementList.testScenarioList.testCaseList.tsetCaseId")
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
			return testScenarioDomain.getTestCaseList().stream()
					.filter(i -> i.getTestCaseId().equalsIgnoreCase(testCaseId)).collect(Collectors.toList()).get(0);
		} catch (Exception e) {
			log.error("error in getting testCase detail", e);
			throw new TestCaseException("error in getting testCase detail");
		}
	}

	@Override
	public TestCaseDomain getTestCaseByIdOnly(String testCaseName,String testScenarioId) {
		Response response = Utils.getResponseObject("getting testCase Details");
		try {

			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.requirementList.testScenarioList.testScenarioId").is(testScenarioId).andOperator(Criteria.where("moduleList.requirementList.testScenarioList.testCaseList.testCaseName").is(testCaseName)));
			//-----TestCaseDomain testCaseDomain=new TestCaseDomain();
			TestCaseDomain testCaseDomain=null;
			
			
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			//System.out.println("*******"+projectInfo);
			if (projectInfo != null) {

				List<ModuleDomain> moduleDomainList = projectInfo.getModuleList();

				for (ModuleDomain moduleDomain : moduleDomainList) {
					List<RequirementDomain> requirementDomainList = moduleDomain.getRequirementList();
					for (RequirementDomain requirementDomain : requirementDomainList) {
						List<TestScenarioDomain> testScenarioList = requirementDomain.getTestScenarioList().stream().filter(i->i.getTestScenarioId().equalsIgnoreCase(testScenarioId)).collect(Collectors.toList());
						if (!testScenarioList.isEmpty()) {

							TestScenarioDomain testScenarioDomain=testScenarioList.get(0);

							List<TestCaseDomain> testCaseDomainList = testScenarioDomain.getTestCaseList().stream()
									.filter(i -> i.getTestCaseName().equalsIgnoreCase(testCaseName))
									.collect(Collectors.toList());
							//System.out.println("$$$$$$$$$$$"+testCaseDomainList);
							if (!testCaseDomainList.isEmpty()) {
								TestCaseDomain testCase= testCaseDomainList.get(0);
                              //System.out.println("%%%%%%%%%%%%%%%%"+testCase);
								testCaseDomain=testCase;
							}
						}
					}
				}
//System.out.println(testCaseDomain);
				return testCaseDomain;
			}
			return null;
		} catch (Exception e) {
			log.error("error in getting testCase detail", e);
			throw new TestCaseException("error in getting testCase detail");
		}
	}
	
	@Override
	public TestCaseDomain getTestCaseByNameOnly(String testCaseName, String projectName) {
		Response response = Utils.getResponseObject("getting testCase Details");
		try {

			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.requirementList.testScenarioList.testCaseList.testCaseName")
					.is(testCaseName).andOperator(Criteria.where("projectName").is(projectName)));
			TestCaseDomain testCaseDomain = new TestCaseDomain();
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			if (projectInfo != null) {

				List<ModuleDomain> moduleDomainList = projectInfo.getModuleList();

				for (ModuleDomain moduleDomain : moduleDomainList) {
					List<RequirementDomain> requirementDomainList = moduleDomain.getRequirementList();
					for (RequirementDomain requirementDomain : requirementDomainList) {
						List<TestScenarioDomain> testScenarioList = requirementDomain.getTestScenarioList();
						for (TestScenarioDomain testScenarioDomain : testScenarioList) {
							List<TestCaseDomain> testCaseDomainList = testScenarioDomain.getTestCaseList().stream()
									.filter(i -> i.getTestCaseName().equalsIgnoreCase(testCaseName))
									.collect(Collectors.toList());

							if (!testCaseDomainList.isEmpty()) {
								testCaseDomain = testCaseDomainList.get(0);
								return testCaseDomain;

							}
						}
					}
				}

				
			}
			return null;
		} catch (Exception e) {
			log.error("error in getting testCase detail", e);
			throw new TestCaseException("error in getting testCase detail");
		}
	}

	@Override
	public List<TestCaseDomain> getTestCaseList(String testScenarioId, String requirementId, String moduleId,
			String projectId) {

		Response response = Utils.getResponseObject("getting testCaseList Details");
		try {

			Query query = new Query();
			query.addCriteria(
					Criteria.where("moduleList.requirementList.testScenarioList.testScenarioId").is(testScenarioId)
							.andOperator(Criteria.where("moduleList.requirementList.requirementId").is(requirementId)));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);

			ModuleDomain moduleDomain = projectInfo.getModuleList().stream()
					.filter(i -> i.getModuleId().equalsIgnoreCase(moduleId)).collect(Collectors.toList()).get(0);
			RequirementDomain requirementDomain = moduleDomain.getRequirementList().stream()
					.filter(i -> i.getRequirementId().equalsIgnoreCase(requirementId)).collect(Collectors.toList())
					.get(0);
			TestScenarioDomain testScenarioDomain = requirementDomain.getTestScenarioList().stream()
					.filter(i -> i.getTestScenarioId().equalsIgnoreCase(testScenarioId)).collect(Collectors.toList())
					.get(0);
			return testScenarioDomain.getTestCaseList();
		} catch (Exception e) {
			log.error("error in getting testcaseList detail", e);
			throw new TestCaseException("error in getting testCaselist detail");
		}
	}

	@Override
	public Response updateTestCase(TestCaseDomain testCaseDomain, String testScenarioId, String requirementId,
			String moduleId, String projectId) {
		Response response = Utils.getResponseObject("updating TestCase Details");
		try {
			String testCaseId = testCaseDomain.getTestCaseId();

			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.requirementList.testScenarioList.testCaseList.testCaseId")
					.is(testCaseId));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class, "projectInfo");
			ModuleDomain moduleDomain = projectInfo.getModuleList().stream()
					.filter(i -> i.getModuleId().equalsIgnoreCase(moduleId)).collect(Collectors.toList()).get(0);
			RequirementDomain requirementDomain = moduleDomain.getRequirementList().stream()
					.filter(i -> i.getRequirementId().equalsIgnoreCase(requirementId)).collect(Collectors.toList())
					.get(0);

			TestScenarioDomain testScenarioDomain = requirementDomain.getTestScenarioList().stream()
					.filter(i -> i.getTestScenarioId().equalsIgnoreCase(testScenarioId)).collect(Collectors.toList())
					.get(0);
			TestCaseDomain testCase = testScenarioDomain.getTestCaseList().stream()
					.filter(i -> i.getTestCaseId().equalsIgnoreCase(testCaseId)).collect(Collectors.toList()).get(0);

			if (!(testCase.getTestCaseName().equalsIgnoreCase(testCaseDomain.getTestCaseName()))
					&& testCaseDomain.getTestCaseName() != null) {

				testCase.setTestCaseName(testCaseDomain.getTestCaseName());

			}
			if (!(testCase.getTestCasePriority().equalsIgnoreCase(testCaseDomain.getTestCasePriority()))
					&& testCaseDomain.getTestCasePriority() != null) {

				testCase.setTestCasePriority(testCaseDomain.getTestCasePriority());

			}
			if (!(testCase.getTestCaseDesciption().equalsIgnoreCase(testCaseDomain.getTestCaseDesciption()))
					&& testCaseDomain.getTestCaseDesciption() != null) {

				testCase.setTestCaseDesciption(testCaseDomain.getTestCaseDesciption());

			}
			if (!(testCase.getTestCaseCategory().equalsIgnoreCase(testCaseDomain.getTestCaseCategory()))
					&& testCaseDomain.getTestCaseCategory() != null) {

				testCase.setTestCaseCategory(testCaseDomain.getTestCaseCategory());

			}

			List<ModuleDomain> modList = projectInfo.getModuleList();
			Update update = new Update();
			update.set("moduleList", modList);
			mongoTemplate.updateMulti(query, update, "projectInfo");
			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(testCase);
			return response;
		} catch (Exception e) {
			log.error("error in updating testingCase detail", e);
			throw new TestCaseException("error in updating testCase detail");

		}
	}

	@Override
	public Response updateTestCaseOnly(TestCaseDomain testCaseDomain) {
		Response response = Utils.getResponseObject("updating TestCase Details");
		try {
			//System.out.println("@@@@@@@@@@@@@@"+testCaseDomain);
			String testCaseId = testCaseDomain.getTestCaseId();
			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.requirementList.testScenarioList.testCaseList.testCaseId")
					.is(testCaseId));
			TestCaseDomain testCasDomain = new TestCaseDomain();
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			List<ModuleDomain> moduleDomainList = projectInfo.getModuleList();
			for (ModuleDomain moduleDomain : moduleDomainList) {
				List<RequirementDomain> requirementDomainList = moduleDomain.getRequirementList();
				for (RequirementDomain requirementDomain : requirementDomainList) {
					List<TestScenarioDomain> testScenarioList = requirementDomain.getTestScenarioList();
					for (TestScenarioDomain testScenarioDomain : testScenarioList) {
						List<TestCaseDomain> testCaseDomainList = testScenarioDomain.getTestCaseList().stream()
								.filter(i -> i.getTestCaseId().equalsIgnoreCase(testCaseId))
								.collect(Collectors.toList());

						if (!testCaseDomainList.isEmpty()) {
							testCasDomain = testCaseDomainList.get(0);
							break;
						}
					}
				}
			}

//			if (!(testCasDomain.getTestCaseName().equalsIgnoreCase(testCaseDomain.getTestCaseName()))
//					&& testCaseDomain.getTestCaseName() != null) {
//
//				testCasDomain.setTestCaseName(testCaseDomain.getTestCaseName());
//
//			}
			/*
			 * if (!(testCasDomain.getTestCasePriority().equalsIgnoreCase(testCaseDomain.
			 * getTestCasePriority())) && testCaseDomain.getTestCasePriority()!= null) {
			 * 
			 * testCasDomain.setTestCasePriority(testCaseDomain.getTestCasePriority());
			 * 
			 * } if
			 * (!(testCasDomain.getTestCaseDesciption().equalsIgnoreCase(testCaseDomain.
			 * getTestCaseDesciption())) && testCaseDomain.getTestCaseDesciption()!= null) {
			 * 
			 * testCasDomain.setTestCaseDesciption(testCaseDomain.getTestCaseDesciption());
			 * 
			 * } if (!(testCasDomain.getTestCaseCategory().equalsIgnoreCase(testCaseDomain.
			 * getTestCaseCategory())) && testCaseDomain.getTestCaseCategory()!= null) {
			 * 
			 * testCasDomain.setTestCaseCategory(testCaseDomain.getTestCaseCategory());
			 * 
			 * }
			 */
			testCasDomain.setTestCasePriority(testCaseDomain.getTestCasePriority());
			testCasDomain.setTestCaseDesciption(testCaseDomain.getTestCaseDesciption());
			
				testCasDomain.setTestCaseTag(testCaseDomain.getTestCaseTag());

				testCasDomain.setPositiveOrNegative(testCaseDomain.getPositiveOrNegative());
				testCasDomain.setTestCaseCategory(testCaseDomain.getTestCaseCategory());
                  testCasDomain.setExcecuteOrSkip(testCaseDomain.getExcecuteOrSkip());
			//System.out.println("testCase%4%$%"+testCaseDomain.getRunnerType());

				testCasDomain.setRunnerType(testCaseDomain.getRunnerType());

			

			Update update = new Update();
			update.set("moduleList", moduleDomainList);
			mongoTemplate.updateMulti(query, update, "projectInfo");
			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(testCasDomain);
			return response;
		} catch (Exception e) {
			log.error("error in updating testingCase detail", e);
			throw new TestCaseException("error in updating testCase detail");

		}
	}

	@Override
	public Response deleteTestCase(String testCaseId, String testScenarioId, String requirementId, String moduleId,
			String projectId) {
		Response response = Utils.getResponseObject("deleting testCase Details");

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
			testScenarioDomain.getTestCaseList().removeIf(i -> i.getTestCaseId().equalsIgnoreCase(testCaseId));
			Update update = new Update();
			List<ModuleDomain> modList = projectInfo.getModuleList();
			update.set("moduleList", modList);
			mongoTemplate.updateMulti(query, update, "projectInfo");
			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(testScenarioDomain);
			return response;
		} catch (Exception e) {
			log.error("error in deleting testCase detail", e);
			throw new TestCaseException("error in delating testCase detail");
		}
	}

	@Override
	public Response deleteAllTestCase(String testScenarioId, String requirementId, String moduleId, String projectId) {
		Response response = Utils.getResponseObject("deleting testCase Details");

		try {
			Query query = new Query();
			query.addCriteria(
					Criteria.where("moduleList.requirementList.testScenarioList.testScenarioId").is(testScenarioId));

			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);

			ModuleDomain moduleDomain = projectInfo.getModuleList().stream()
					.filter(i -> i.getModuleId().equalsIgnoreCase(moduleId)).collect(Collectors.toList()).get(0);
			RequirementDomain requirementDomain = moduleDomain.getRequirementList().stream()
					.filter(i -> i.getRequirementId().equalsIgnoreCase(requirementId)).collect(Collectors.toList())
					.get(0);
			TestScenarioDomain testScenarioDomain = requirementDomain.getTestScenarioList().stream()
					.filter(i -> i.getTestScenarioId().equalsIgnoreCase(testScenarioId)).collect(Collectors.toList())
					.get(0);
			testScenarioDomain.setTestCaseList(null);
			Update update = new Update();
			List<ModuleDomain> modList = projectInfo.getModuleList();

			update.set("moduleList", modList);
			mongoTemplate.updateMulti(query, update, "projectInfo");

			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(testScenarioDomain);
			return response;
		} catch (Exception e) {
			log.error("error in deleting testCase details", e);
			throw new TestCaseException("error in deleting testCase details");
		}
	}

	

}