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
import com.qutap.dash.domain.ModuleDomain;
import com.qutap.dash.domain.ProjectInfoDomain;

@Repository
@Transactional
public class ModuleDaoImpl implements ModuleDao {

	Logger log = LoggerFactory.getLogger(ModuleDaoImpl.class);

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public Response saveModule(List<ModuleDomain> moduleDomainList, String projectId) {
		Response response = Utils.getResponseObject("Saving module Details");
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("projectId").is(projectId));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			Update update = new Update();
			if (projectInfo.getModuleList() == null) {
				update.set("moduleList", moduleDomainList);
				mongoTemplate.upsert(query, update, "projectInfo");
			} else {
				for (ModuleDomain moduleDomain : moduleDomainList) {
					if (!(projectInfo.getModuleList().stream()
							.anyMatch(i -> i.getModuleName().equalsIgnoreCase(moduleDomain.getModuleName())))) {
						update.addToSet("moduleList", moduleDomain);
						mongoTemplate.upsert(query, update, "projectInfo");
					} else {
						throw new ModuleException(moduleDomain.getModuleName() + "is already present in database");
					}
				}
			}
			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(moduleDomainList);
			return response;
		} catch (Exception e) {
			log.error("error in saving Module detail", e);
			throw new ModuleException("error in saving Module detail");
		}
	}

	@Override
	public Response saveModuleByName(List<ModuleDomain> moduleDomainList, String projectName) {
		Response response = Utils.getResponseObject("Saving module Details");
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("projectName").is(projectName));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			Update update = new Update();
			if (projectInfo.getModuleList() == null) {
				update.set("moduleList", moduleDomainList);
				mongoTemplate.upsert(query, update, "projectInfo");
			} else {
				for (ModuleDomain moduleDomain : moduleDomainList) {
					if (!(projectInfo.getModuleList().stream()
							.anyMatch(i -> i.getModuleName().equalsIgnoreCase(moduleDomain.getModuleName())))) {
						update.addToSet("moduleList", moduleDomain);
						mongoTemplate.upsert(query, update, "projectInfo");
					} else {
						throw new ModuleException(moduleDomain.getModuleName() + "is already present in database");
					}
				}
			}
			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(moduleDomainList);
			return response;
		} catch (Exception e) {
			log.error("error in saving Module detail", e);
			throw new ModuleException("error in saving Module detail");
		}
	}

	@Override
	public List<ModuleDomain> getModuleList(String projectId) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("projectId").is(projectId));

			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			return projectInfo.getModuleList();

		} catch (Exception e) {
			log.error("error in getting list of modules details", e);
			throw new ModuleException("error in getting list of modules details");
		}
	}

	@Override
	public ModuleDomain getModuleByName(String moduleName, String projectName) {
		Response response = Utils.getResponseObject("Getting module Details");
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.moduleName").is(moduleName)
					.andOperator(Criteria.where("projectName").is(projectName)));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			if (projectInfo != null) {
				ModuleDomain moduleDomain = projectInfo.getModuleList().stream()
						.filter(i -> i.getModuleName().equalsIgnoreCase(moduleName)).collect(Collectors.toList())
						.get(0);

				response.setStatus(StatusCode.SUCCESS.name());
				response.setData(moduleDomain);
				return moduleDomain;
			}
			return null;
		} catch (Exception e) {

			log.error("error in getting Module detail", e);
			throw new ModuleException("error in getting Module detail");

		}
	}

	@Override
	public ModuleDomain getModuleById(String moduleId, String projectId) {
		Response response = Utils.getResponseObject("getting module Details");
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.moduleId").is(moduleId)
					.andOperator(Criteria.where("projectId").is(projectId)));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			ModuleDomain moduleDomain = projectInfo.getModuleList().stream()
					.filter(i -> i.getModuleId().equalsIgnoreCase(moduleId)).collect(Collectors.toList()).get(0);
			return moduleDomain;
		} catch (Exception e) {
			log.error("error in getting Module detail", e);
			throw new ModuleException("error in getting Module detail");
		}
	}

	@Override
	public ModuleDomain getModuleByIdOnly(String moduleId) {
		Response response = Utils.getResponseObject("getting module Details");
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.moduleId").is(moduleId));

			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			ModuleDomain moduleDomain = projectInfo.getModuleList().stream()
					.filter(i -> i.getModuleId().equalsIgnoreCase(moduleId)).collect(Collectors.toList()).get(0);
			return moduleDomain;
		} catch (Exception e) {
			log.error("error in getting Module detail", e);
			throw new ModuleException("error in getting Module detail");
		}
	}

	@Override
	public ModuleDomain getModuleByNameOnly(String moduleName, String projectId) {
		Response response = Utils.getResponseObject("Getting module Details");
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.moduleName").is(moduleName)
					.andOperator(Criteria.where("projectId").is(projectId)));

			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			if (projectInfo != null) {
				ModuleDomain moduleDomain = projectInfo.getModuleList().stream()
						.filter(i -> i.getModuleName().equalsIgnoreCase(moduleName)).collect(Collectors.toList())
						.get(0);

				response.setStatus(StatusCode.SUCCESS.name());
				response.setData(moduleDomain);
				return moduleDomain;
			}
			return null;
		} catch (Exception e) {

			log.error("error in getting Module detail", e);
			throw new ModuleException("error in getting Module detail");

		}
	}

	@Override
	public Response updateModule(ModuleDomain moduleDomain, String projectId) {
		Response response = Utils.getResponseObject("updating Module Details");
		try {
			String moduleId = moduleDomain.getModuleId();
			Query query = new Query();
			query.addCriteria(Criteria.where("projectId").is(projectId));
			ProjectInfoDomain projectInfoDomain = mongoTemplate.findOne(query, ProjectInfoDomain.class, "projectInfo");
			List<ModuleDomain> moduleDomainList = projectInfoDomain.getModuleList();

			ModuleDomain module = moduleDomainList.stream().filter(i -> i.getModuleId().equalsIgnoreCase(moduleId))
					.collect(Collectors.toList()).get(0);

			if (!(module.getModuleName().equalsIgnoreCase(moduleDomain.getModuleName()))
					&& moduleDomain.getModuleName() != null) {
				module.setModuleName(moduleDomain.getModuleName());
			}
			Update update = new Update();
			update.set("moduleList", moduleDomainList);
			mongoTemplate.updateMulti(query, update, "projectInfo");
			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(module);
			return response;
		} catch (Exception e) {
			log.error("error in updating Module detail", e);
			throw new ModuleException("error in updating Module detail");
		}
	}

	@Override
	public Response deleteModule(String moduleId, String projectId) {
		Response response = Utils.getResponseObject("deleting module Details");
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("moduleList.moduleId").is(moduleId)
					.andOperator(Criteria.where("projectId").is(projectId)));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			projectInfo.getModuleList().removeIf(i -> i.getModuleId().equalsIgnoreCase(moduleId));
			Update update = new Update();
			List<ModuleDomain> moduleDomainList = projectInfo.getModuleList();
			update.set("moduleList", moduleDomainList);
			mongoTemplate.updateMulti(query, update, "projectInfo");
			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(moduleDomainList);
			return response;
		} catch (Exception e) {
			log.error("error in deleting Module detail", e);
			throw new ModuleException("error in deleting Module detail");
		}
	}

	@Override
	public Response deleteAllModule(String projectId) {
		Response response = Utils.getResponseObject("deleting module Details");
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("projectId").is(projectId));
			ProjectInfoDomain projectInfo = mongoTemplate.findOne(query, ProjectInfoDomain.class);
			projectInfo.setModuleList(null);
			Update update = new Update();
			List<ModuleDomain> moduleDomainList = projectInfo.getModuleList();
			update.set("moduleList", moduleDomainList);
			mongoTemplate.updateMulti(query, update, "projectInfo");
			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(moduleDomainList);
			return response;
		} catch (Exception e) {
			log.error("error in deleting all Module detail", e);
			throw new ModuleException("error in deleting all Module detail");
		}
	}

}