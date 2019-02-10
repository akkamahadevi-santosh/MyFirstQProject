package com.qutap.dash.repository;

import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.qutap.dash.commonUtils.ErrorObject;
import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.commonUtils.StatusCode;
import com.qutap.dash.commonUtils.Utils;
import com.qutap.dash.customException.ProjectInfoException;
import com.qutap.dash.domain.ProjectInfoDomain;

@Repository
@Transactional
public class ProjectInfoDaoImpl implements ProjectInfoDao {

	Logger log = LoggerFactory.getLogger(ProjectInfoDaoImpl.class);

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public Response saveProjectInfo(ProjectInfoDomain projectInfoDomain) {
		Response response = Utils.getResponseObject("Adding project Details");
		try {
			mongoTemplate.insert(projectInfoDomain, "projectInfo");
			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(projectInfoDomain);
			return response;
		} catch (Exception e) {
			log.error("error in saving project detail", e);
			throw new ProjectInfoException("error in saving project detail");
		}

	}

	@Override
	public ProjectInfoDomain getProjectInfoById(String projectId) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("projectId").is(projectId));
			return mongoTemplate.findOne(query, ProjectInfoDomain.class, "projectInfo");
		} catch (Exception e) {
			log.error("error in getting project detail when searching by Id", e);
			throw new ProjectInfoException("error in getting project detail when searching by Id");
		}
	}

	@Override
	public ProjectInfoDomain getProjectInfoByName(String projectName) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("projectName").is(projectName));
			return mongoTemplate.findOne(query, ProjectInfoDomain.class, "projectInfo");
		} catch (Exception e) {
			log.error("error in getting project detail  when searching by Name", e);
			throw new ProjectInfoException("error in getting project detail when searching by Name");
		}
	}

	@Override
	public List<ProjectInfoDomain> getProjectListInfo() {
		try {
			return mongoTemplate.findAll(ProjectInfoDomain.class, "projectInfo");
		} catch (Exception e) {
			log.error("error in getting list of project details", e);
			throw new ProjectInfoException("error in getting list of project details");
		}
	}

	@Override
	public Response updateProjectInfo(ProjectInfoDomain projectInfoDomain) {
		Response response = Utils.getResponseObject("updating project Details");
		try {
			Query query = new Query(Criteria.where("projectId").is(projectInfoDomain.getProjectId()));
			Document doc = new Document();
			mongoTemplate.getConverter().write(projectInfoDomain, doc);
			Update update = new Update();
			for (String key : doc.keySet()) {
				Object value = doc.get(key);
				if (value != null) {
					update.set(key, value);
				}
			}
			projectInfoDomain = mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true),
					ProjectInfoDomain.class);// it will return New Updated Data
			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(projectInfoDomain);
			return response;
		} catch (Exception e) {
			log.error("error in updating project details", e);
			throw new ProjectInfoException("error in updating project details");
		}
	}

	@Override
	public Response deleteProjectInfo(String projectId) {
		Response response = Utils.getResponseObject("deleting project Details");
		ErrorObject err = Utils.getErrorResponse("deleting project", "project id  not found");
		try {
			ProjectInfoDomain projectInfoDomain = getProjectInfoById(projectId);
			mongoTemplate.remove(projectInfoDomain);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(projectInfoDomain);
			return response;
		} catch (Exception e) {
			log.error("error in deleting project details", e);
			throw new ProjectInfoException("error in deleting project details");
		}
	}
}