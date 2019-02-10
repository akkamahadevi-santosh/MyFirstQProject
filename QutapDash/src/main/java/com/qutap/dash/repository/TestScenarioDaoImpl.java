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

import com.qutap.dash.customException.TestScenarioException;
import com.qutap.dash.domain.ModuleDomain;
import com.qutap.dash.domain.ProjectInfoDomain;
import com.qutap.dash.domain.RequirementDomain;
import com.qutap.dash.domain.TestScenarioDomain;

@Repository
@Transactional
public class TestScenarioDaoImpl implements TestScenarioDao {
	Logger log = LoggerFactory.getLogger(ModuleDaoImpl.class);
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public Response saveTestScenario(List<TestScenarioDomain> testScenarioDomainList, String requirementId,
			String moduleId, String projectId) {
		Response response = Utils.getResponseObject("Adding TestScenario Details");
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.requirementList.requirementId").is(requirementId)
					.andOperator(Criteria.where("moduleList.moduleId").is(moduleId)));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			if (projectInfo != null) {
				List<ModuleDomain> moduleDomainList = projectInfo.getModuleList();
				ModuleDomain moduleDomain = projectInfo.getModuleList().stream()
						.filter(i -> i.getModuleId().equalsIgnoreCase(moduleId)).collect(Collectors.toList()).get(0);
				RequirementDomain requirementDomain = moduleDomain.getRequirementList().stream()
						.filter(i -> i.getRequirementId().equalsIgnoreCase(requirementId)).collect(Collectors.toList())
						.get(0);
				Update update = new Update();
				if (requirementDomain.getTestScenarioList() == null) {
					requirementDomain.setTestScenarioList(testScenarioDomainList);
					update.set("moduleList", moduleDomainList);
					mongoTemplate.upsert(query, update, "projectInfo");
				} else {
					for (TestScenarioDomain testScenarioDomain : testScenarioDomainList) {
						List<TestScenarioDomain> testScenarioList = requirementDomain.getTestScenarioList();
						if (!(testScenarioList.stream().anyMatch(i -> i.getTestScenarioName()
								.equalsIgnoreCase(testScenarioDomain.getTestScenarioName())))) {
							testScenarioList.add(testScenarioDomain);
							update.set("moduleList", moduleDomainList);
							mongoTemplate.upsert(query, update, "projectInfo");
						}
					}
				}

				response.setStatus(StatusCode.SUCCESS.name());
				response.setData(requirementDomain);
				return response;
			}
			return null;
		} catch (Exception e) {
			log.error("error in saving TestScenario detail", e);
			throw new TestScenarioException("error in saving TestScenario detail");
		}
	}

	@Override
	public List<TestScenarioDomain> getTestScenarioList(String requirementId, String moduleId, String projectId) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.requirementList.requirementId").is(requirementId)
					.andOperator(Criteria.where("moduleList.moduleId").is(moduleId)));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			if (projectInfo.getProjectId().equalsIgnoreCase(projectId)) {
				ModuleDomain moduleDomain = projectInfo.getModuleList().stream()
						.filter(i -> i.getModuleId().equalsIgnoreCase(moduleId)).collect(Collectors.toList()).get(0);
				RequirementDomain requirementDomain = moduleDomain.getRequirementList().stream()
						.filter(i -> i.getRequirementId().equalsIgnoreCase(requirementId)).collect(Collectors.toList())
						.get(0);
				List<TestScenarioDomain> testScenarioDomainList = requirementDomain.getTestScenarioList();
				if (testScenarioDomainList == null) {
					log.error("no testScenario data available");
					throw new TestScenarioException("no testScenario data available");
				} else {

					return requirementDomain.getTestScenarioList();
				}
			}
			throw new TestScenarioException("project id does not match with db data");
		} catch (Exception e) {
			log.error("error in getting list of testScenario details", e);
			throw new TestScenarioException("error in getting list of testScenario details");
		}
	}

	@Override
	public TestScenarioDomain getTestScenarioById(String testScenarioId, String requirementId, String moduleId,
			String projectId) {
		Response response = Utils.getResponseObject("getting testScenario Details");
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
			return requirementDomain.getTestScenarioList().stream()
					.filter(i -> i.getTestScenarioId().equalsIgnoreCase(testScenarioId)).collect(Collectors.toList())
					.get(0);
		} catch (Exception e) {
			log.error("error in getting testScenario detail", e);
			throw new ModuleException("error in testScenario Module detail");
		}
	}

	@Override
	public TestScenarioDomain getTestScenarioByName(String testScenarioName, String requirementName, String moduleName,
			String projectName) {
		Response response = Utils.getResponseObject("getting  TestScenario Details");
		try {

			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.requirementList.testScenarioList.testScenarioName")
					.is(testScenarioName));

			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			ModuleDomain moduleDomain = projectInfo.getModuleList().stream()
					.filter(i -> i.getModuleName().equalsIgnoreCase(moduleName)).collect(Collectors.toList()).get(0);
			RequirementDomain requirementDomain = moduleDomain.getRequirementList().stream()
					.filter(i -> i.getRequirementName().equalsIgnoreCase(requirementName)).collect(Collectors.toList())
					.get(0);
			TestScenarioDomain testScenarioDomain = requirementDomain.getTestScenarioList().stream()
					.filter(i -> i.getTestScenarioName().equalsIgnoreCase(testScenarioName))
					.collect(Collectors.toList()).get(0);
			return testScenarioDomain;
		} catch (Exception e) {
			log.error("error in getting testScenario detail", e);
			throw new TestScenarioException("error in getting testScenario detail");
		}
	}

	@Override
	public TestScenarioDomain getTestScenarioByIdOnly(String testScenarioId) {
		Response response = Utils.getResponseObject("getting testScenario Details");
		try {

			Query query = new Query();
			query.addCriteria(
					Criteria.where("moduleList.requirementList.testScenarioList.testScenarioId").is(testScenarioId));
			TestScenarioDomain testScenarioDomain1 = new TestScenarioDomain();
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			List<ModuleDomain> moduleDomainList = projectInfo.getModuleList();
			for (ModuleDomain moduleDomain : moduleDomainList) {
				List<RequirementDomain> requirementDomainList = moduleDomain.getRequirementList();
				for (RequirementDomain requirementDomain : requirementDomainList) {
					TestScenarioDomain testScenarioDomain = requirementDomain.getTestScenarioList().stream()
							.filter(i -> i.getTestScenarioId().equalsIgnoreCase(testScenarioId))
							.collect(Collectors.toList()).get(0);
					if (testScenarioDomain != null) {
						testScenarioDomain1 = testScenarioDomain;
						break;
					}
				}
			}
			return testScenarioDomain1;
		} catch (Exception e) {
			log.error("error in getting testScenario detail", e);
			throw new ModuleException("error in testScenario Module detail");
		}
	}

	@Override
	public TestScenarioDomain getTestScenarioByNameOnly(String testScenarioName, String requirementId) {
		Response response = Utils.getResponseObject("getting testScenario Details");
		try {

			Query query = new Query();
			query.addCriteria(
					Criteria.where("moduleList.requirementList.testScenarioList.testScenarioName").is(testScenarioName)
							.andOperator(Criteria.where("moduleList.requirementList.requirementId").is(requirementId)));
			TestScenarioDomain testScenarioDomain1 = new TestScenarioDomain();
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			if (projectInfo != null) {

				List<ModuleDomain> moduleDomainList = projectInfo.getModuleList();
				for (ModuleDomain moduleDomain : moduleDomainList) {
					List<RequirementDomain> requirementDomainList = moduleDomain.getRequirementList().stream()
							.filter(i -> i.getRequirementId().equalsIgnoreCase(requirementId))
							.collect(Collectors.toList());
					if (!requirementDomainList.isEmpty()) {
						RequirementDomain requirementDomain = requirementDomainList.get(0);
						List<TestScenarioDomain> testScenarioDomainList = requirementDomain.getTestScenarioList()
								.stream().filter(i -> i.getTestScenarioName().equalsIgnoreCase(testScenarioName))
								.collect(Collectors.toList());
						if (!testScenarioDomainList.isEmpty()) {
							testScenarioDomain1 = testScenarioDomainList.get(0);
						}
					}
				}
				return testScenarioDomain1;

			}
			return null;
		} catch (Exception e) {
			log.error("error in getting testScenario detail", e);
			throw new ModuleException("error in testScenario Module detail");
		}
	}

	@Override
	public Response updateTestScenario(TestScenarioDomain testScenarioDomain, String requirementId, String moduleId,
			String projectId) {
		Response response = Utils.getResponseObject("updating Module Details");
		try {
			String testScenarioId = testScenarioDomain.getTestScenarioId();
			Query query = new Query();
			query.addCriteria(
					Criteria.where("moduleList.requirementList.testScenarioList.testScenarioId").is(testScenarioId));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class, "projectInfo");
			ModuleDomain moduleDomain = projectInfo.getModuleList().stream()
					.filter(i -> i.getModuleId().equalsIgnoreCase(moduleId)).collect(Collectors.toList()).get(0);
			RequirementDomain requirementDomain = moduleDomain.getRequirementList().stream()
					.filter(i -> i.getRequirementId().equalsIgnoreCase(requirementId)).collect(Collectors.toList())
					.get(0);

			TestScenarioDomain testSceDomain = requirementDomain.getTestScenarioList().stream()
					.filter(i -> i.getTestScenarioId().equalsIgnoreCase(testScenarioId)).collect(Collectors.toList())
					.get(0);

			if (!(testSceDomain.getTestScenarioName().equalsIgnoreCase(testScenarioDomain.getTestScenarioName()))
					&& testScenarioDomain.getTestScenarioName() != null) {
				testSceDomain.setTestScenarioName(testScenarioDomain.getTestScenarioName());

			}

			List<ModuleDomain> moduleDomainList = projectInfo.getModuleList();
			Update update = new Update();
			update.set("moduleList", moduleDomainList);
			mongoTemplate.updateMulti(query, update, "projectInfo");
			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(testSceDomain);
			return response;
		} catch (Exception e) {
			log.error("error in updating testingScenario detail", e);
			throw new TestScenarioException("error in updating testingScenario detail");

		}
	}

	@Override
	public Response updateTestScenario(TestScenarioDomain testScenarioDomain) {
		Response response = Utils.getResponseObject("updating test  sceanario Details");
		try {
			String testScenarioId = testScenarioDomain.getTestScenarioId();
			Query query = new Query();
			query.addCriteria(
					Criteria.where("moduleList.requirementList.testScenarioList.testScenarioId").is(testScenarioId));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class, "projectInfo");
			if (projectInfo != null) {
				List<ModuleDomain> moduleDomainList = projectInfo.getModuleList();
				for (ModuleDomain moduleDomain : moduleDomainList) {
					List<RequirementDomain> requirementDomainList = moduleDomain.getRequirementList();
					for (RequirementDomain requirementDomain : requirementDomainList) {
						List<TestScenarioDomain> testScenarioDomainList = requirementDomain.getTestScenarioList()
								.stream().filter(i -> i.getTestScenarioId().equalsIgnoreCase(testScenarioId))
								.collect(Collectors.toList());

						testScenarioDomainList.add(testScenarioDomain);
						Update update = new Update();
						update.set("moduleList", moduleDomainList);
						mongoTemplate.updateMulti(query, update, "projectInfo");

					}
				}
			}

			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(testScenarioDomain);
			return response;
		} catch (Exception e) {
			log.error("error in updating testingScenario detail", e);
			throw new TestScenarioException("error in updating testingScenario detail");

		}
	}

	@Override
	public Response deleteTestScenario(String testScenarioId, String requirementId, String moduleId, String projectId) {
		Response response = Utils.getResponseObject("deleting testScenario Details");
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
			requirementDomain.getTestScenarioList()
					.removeIf(i -> i.getTestScenarioId().equalsIgnoreCase(testScenarioId));
			Update update = new Update();
			List<ModuleDomain> moduleDomainList = projectInfo.getModuleList();
			update.set("moduleList", moduleDomainList);
			mongoTemplate.updateMulti(query, update, "projectInfo");
			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(requirementDomain);
			return response;
		} catch (Exception e) {
			log.error("error in deleting testScenario detail", e);
			throw new TestScenarioException("error in deleting testScenario detail");
		}
	}

	@Override
	public Response deleteAllTestScenario(String requirementId, String moduleId, String projectId) {
		Response response = Utils.getResponseObject("deleting testScenario Details");
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.requirementList.requirementId").is(requirementId));

			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			ModuleDomain moduleDomain = projectInfo.getModuleList().stream()
					.filter(i -> i.getModuleId().equalsIgnoreCase(moduleId)).collect(Collectors.toList()).get(0);
			RequirementDomain requirementDomain = moduleDomain.getRequirementList().stream()
					.filter(i -> i.getRequirementId().equalsIgnoreCase(requirementId)).collect(Collectors.toList())
					.get(0);
			requirementDomain.setTestScenarioList(null);
			Update update = new Update();
			List<ModuleDomain> moduleDomainList = projectInfo.getModuleList();
			update.set("moduleList", moduleDomainList);
			mongoTemplate.updateMulti(query, update, "projectInfo");
			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(requirementDomain);
			return response;
		} catch (Exception e) {
			log.error("error in deletingAll testScenario detail", e);
			throw new TestScenarioException("error in deletingAll testScenario detail");
		}
	}

}