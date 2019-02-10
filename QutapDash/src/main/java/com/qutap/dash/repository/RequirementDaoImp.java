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
import com.qutap.dash.customException.RequirementException;
import com.qutap.dash.domain.ModuleDomain;
import com.qutap.dash.domain.ProjectInfoDomain;
import com.qutap.dash.domain.RequirementDomain;

@Repository
@Transactional
public class RequirementDaoImp implements RequirementDao {
	Logger log = LoggerFactory.getLogger(ModuleDaoImpl.class);
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public Response saveRequirement(List<RequirementDomain> requirementDomainList, String moduleId, String projectId) {
		Response response = Utils.getResponseObject("Adding requirement Details");
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.moduleId").is(moduleId)
					.andOperator(Criteria.where("projectId").is(projectId)));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			List<ModuleDomain> moduleDomainList = projectInfo.getModuleList();
			ModuleDomain moduleDomain = projectInfo.getModuleList().stream()
					.filter(i -> i.getModuleId().equalsIgnoreCase(moduleId)).collect(Collectors.toList()).get(0);
			Update update = new Update();
			if (moduleDomain.getRequirementList() == null) {
				moduleDomain.setRequirementList(requirementDomainList);
				update.set("moduleList", moduleDomainList);
				mongoTemplate.upsert(query, update, "projectInfo");

			} else {
				for (RequirementDomain requirementDomain : requirementDomainList) {
					List<RequirementDomain> requirementList = moduleDomain.getRequirementList();
					if (!(requirementList.stream().anyMatch(
							i -> i.getRequirementName().equalsIgnoreCase(requirementDomain.getRequirementName())))) {
						requirementList.add(requirementDomain);
						update.set("moduleList", moduleDomainList);
						mongoTemplate.upsert(query, update, "projectInfo");
					}

				}
			}

			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(moduleDomain);
			return response;
		} catch (Exception e) {
			log.error("error in saving requirement detail", e);
			throw new RequirementException("error in saving requirement detail");
		}
	}

	@Override
	public Response saveRequirementByName(List<RequirementDomain> requirementDomainList, String moduleName,
			String projectName) {
		Response response = Utils.getResponseObject("Adding requirement Details");
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.moduleName").is(moduleName)
					.andOperator(Criteria.where("projectName").is(projectName)));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			List<ModuleDomain> moduleDomainList = projectInfo.getModuleList();
			ModuleDomain moduleDomain = projectInfo.getModuleList().stream()
					.filter(i -> i.getModuleId().equalsIgnoreCase(moduleName)).collect(Collectors.toList()).get(0);
			Update update = new Update();
			if (moduleDomain.getRequirementList() == null) {

				moduleDomain.setRequirementList(requirementDomainList);
				update.set("moduleList", moduleDomainList);
				mongoTemplate.upsert(query, update, "projectInfo");

			} else {
				for (RequirementDomain requirementDomain : requirementDomainList) {
					List<RequirementDomain> requirementList = moduleDomain.getRequirementList();
					if (!(requirementList.stream().anyMatch(
							i -> i.getRequirementName().equalsIgnoreCase(requirementDomain.getRequirementName())))) {
						requirementList.add(requirementDomain);
						update.set("moduleList", moduleDomainList);
						mongoTemplate.upsert(query, update, "projectInfo");
					}

				}
			}

			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(moduleDomain);
			return response;
		} catch (Exception e) {
			log.error("error in saving requirement detail", e);
			throw new RequirementException("error in saving requirement detail");
		}
	}

	@Override
	public RequirementDomain getRequirementByName(String requirementName, String moduleName, String projectName) {
		Response response = Utils.getResponseObject("getting  requirement Details");
		try {
			ModuleDomain moduleDomain = null;
			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.requirementList.requirementName").is(requirementName)
					.andOperator(Criteria.where("moduleList.moduleName").is(moduleName)));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			if (projectInfo != null) {
				moduleDomain = projectInfo.getModuleList().stream()
						.filter(i -> i.getModuleName().equalsIgnoreCase(moduleName)).collect(Collectors.toList())
						.get(0);

				RequirementDomain requirementDomain = moduleDomain.getRequirementList().stream()
						.filter(i -> i.getRequirementName().equalsIgnoreCase(requirementName))
						.collect(Collectors.toList()).get(0);

				response.setStatus(StatusCode.SUCCESS.name());
				response.setData(requirementDomain);
				return requirementDomain;
			}

			return null;
		} catch (Exception e) {
			log.error("error in getting Requirement detail", e);
			throw new RequirementException("error in getting requirement detail");
		}
	}

	@Override
	public RequirementDomain getRequirementById(String requirementId, String moduleId, String projectId) {
		Response response = Utils.getResponseObject("getting requirement Details");
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.requirementList.requirementId").is(requirementId)
					.andOperator(Criteria.where("moduleList.moduleId").is(moduleId)));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			ModuleDomain moduleDomain = projectInfo.getModuleList().stream()
					.filter(i -> i.getModuleId().equalsIgnoreCase(moduleId)).collect(Collectors.toList()).get(0);
			RequirementDomain requirementDomain = moduleDomain.getRequirementList().stream()
					.filter(i -> i.getRequirementId().equalsIgnoreCase(requirementId)).collect(Collectors.toList())
					.get(0);
			return requirementDomain;
		} catch (Exception e) {
			log.error("error in getting requirement detail", e);
			throw new RequirementException("error in getting requirement detail");
		}
	}

	@Override
	public RequirementDomain getRequirementByIdOnly(String requirementId) {
		Response response = Utils.getResponseObject("getting requirement Details");
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.requirementList.requirementId").is(requirementId));
			RequirementDomain requirementDomain1 = new RequirementDomain();
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			List<ModuleDomain> moduleDomainList = projectInfo.getModuleList();
			for (ModuleDomain moduleDomain : moduleDomainList) {
				RequirementDomain requirementDomain = moduleDomain.getRequirementList().stream()
						.filter(i -> i.getRequirementId().equalsIgnoreCase(requirementId)).collect(Collectors.toList())
						.get(0);
				if (requirementDomain != null) {
					requirementDomain1 = requirementDomain;
				}
			}
			return requirementDomain1;

		} catch (Exception e) {
			log.error("error in getting requirement detail", e);
			throw new RequirementException("error in getting requirement detail");
		}
	}

	@Override
	public RequirementDomain getRequirementByNameOnly(String requirementName, String moduleId) {
		Response response = Utils.getResponseObject("getting requirement Details");
		Query query = new Query();
		query.addCriteria(Criteria.where("moduleList.requirementList.requirementName").is(requirementName)
				.andOperator(Criteria.where("moduleList.moduleId").is(moduleId)));

		ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
		if (projectInfo != null) {
			RequirementDomain reqDomain1 = new RequirementDomain();

			List<ModuleDomain> moduleDomainList = projectInfo.getModuleList().stream()
					.filter(i -> i.getModuleId().equalsIgnoreCase(moduleId)).collect(Collectors.toList());
			if (!moduleDomainList.isEmpty()) {

				ModuleDomain moduleDomain = moduleDomainList.get(0);
				List<RequirementDomain> requirementDomainList = moduleDomain.getRequirementList().stream()
						.filter(i -> i.getRequirementName().equalsIgnoreCase(requirementName))
						.collect(Collectors.toList());
				if (!requirementDomainList.isEmpty()) {
					reqDomain1 = requirementDomainList.get(0);
				}

			}
			return reqDomain1;
		}
		return null;
	}

	@Override
	public List<RequirementDomain> getRequirementList(String moduleId, String projectId) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.moduleId").is(moduleId)
					.andOperator(Criteria.where("projectId").is(projectId)));

			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);

			ModuleDomain moduleDomain = projectInfo.getModuleList().stream()
					.filter(i -> i.getModuleId().equalsIgnoreCase(moduleId)).collect(Collectors.toList()).get(0);

			return moduleDomain.getRequirementList();

		} catch (Exception e) {
			log.error("error in getting list of requirement details", e);
			throw new RequirementException("error in getting list of requirement details");
		}
	}

	@Override
	public Response updateRequirement(RequirementDomain requirementDomain, String moduleId, String projectId) {
		Response response = Utils.getResponseObject("updating Module Details");
		try {
			String requirementId = requirementDomain.getRequirementId();
			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.requirementList.requirementId").is(requirementId)
					.andOperator(Criteria.where("moduleList.moduleId").is(moduleId)));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class, "projectInfo");
			ModuleDomain moduleDomain = projectInfo.getModuleList().stream()
					.filter(i -> i.getModuleId().equalsIgnoreCase(moduleId)).collect(Collectors.toList()).get(0);
			RequirementDomain requirement = moduleDomain.getRequirementList().stream()
					.filter(i -> i.getRequirementId().equalsIgnoreCase(requirementId)).collect(Collectors.toList())
					.get(0);

			if (!(requirement.getRequirementName().equalsIgnoreCase(requirementDomain.getRequirementName()))
					&& requirementDomain.getRequirementName() != null) {
				requirement.setRequirementName(requirementDomain.getRequirementName());

			}
			List<ModuleDomain> moduleDomainList = projectInfo.getModuleList();

			Update update = new Update();
			update.set("moduleList", moduleDomainList);
			mongoTemplate.updateMulti(query, update, "projectInfo");
			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(requirement);
			return response;
		} catch (Exception e) {
			log.error("error in updating requirement detail", e);
			throw new ModuleException("error in updating requirement detail");
		}
	}

	@Override
	public Response updateRequirement(RequirementDomain requirementDomain) {
		Response response = Utils.getResponseObject("updating Module Details");
		try {
			String requirementId = requirementDomain.getRequirementId();
			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.requirementList.requirementId").is(requirementId));

			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class, "projectInfo");
			if (projectInfo != null) {

				List<ModuleDomain> moduleDomainList = projectInfo.getModuleList();
				for (ModuleDomain moduleDomain : moduleDomainList) {
					List<RequirementDomain> requirementDomainList = moduleDomain.getRequirementList().stream()
							.filter(i -> i.getRequirementId().equalsIgnoreCase(requirementId))
							.collect(Collectors.toList());
					if (!requirementDomainList.isEmpty()) {
						RequirementDomain requirementDomain1 = requirementDomainList.get(0);
						requirementDomain1.setRequirementName(requirementDomain.getRequirementName());

						Update update = new Update();
						update.set("moduleList", moduleDomainList);
						mongoTemplate.updateMulti(query, update, "projectInfo");
						response.setStatus(StatusCode.SUCCESS.name());
						response.setData(requirementDomain1);
						return response;
					}

				}
			}
			return null;
		} catch (Exception e) {
			log.error("error in updating requirement detail", e);
			throw new ModuleException("error in updating requirement detail");
		}
	}

	@Override
	public Response deleteRequirement(String requirementId, String moduleId, String projectId) {
		Response response = Utils.getResponseObject("deleting  requirement Details");
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.requirementList.requirementId").is(requirementId)
					.andOperator(Criteria.where("moduleList.moduleId").is(moduleId)));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			ModuleDomain moduleDomain = projectInfo.getModuleList().stream()
					.filter(i -> i.getModuleId().equalsIgnoreCase(moduleId)).collect(Collectors.toList()).get(0);
			moduleDomain.getRequirementList().removeIf(i -> i.getRequirementId().equalsIgnoreCase(requirementId));
			Update update = new Update();

			List<ModuleDomain> moduleDomainList = projectInfo.getModuleList();
			update.set("moduleList", moduleDomainList);
			mongoTemplate.updateMulti(query, update, "projectInfo");

			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(moduleDomain);
			return response;
		} catch (Exception e) {
			log.error("error in deleting Requirement detail", e);
			throw new RequirementException("error in deleting requirement detail");
		}
	}

	@Override
	public Response deleteAllRequirement(String moduleId, String projectId) {
		Response response = Utils.getResponseObject("deleting All requirement Details");
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.moduleId").is(moduleId));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			ModuleDomain moduleDomain = projectInfo.getModuleList().stream()
					.filter(i -> i.getModuleId().equalsIgnoreCase(moduleId)).collect(Collectors.toList()).get(0);
			moduleDomain.setRequirementList(null);
			Update update = new Update();

			List<ModuleDomain> modList = projectInfo.getModuleList();
			System.out.println(modList);
			update.set("moduleList", modList);
			mongoTemplate.updateMulti(query, update, "projectInfo");

			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(moduleDomain);
			return response;
		} catch (Exception e) {
			log.error("error in deleting all Requirement detail", e);
			throw new RequirementException("error in deleting all requirement detail");
		}
	}

}