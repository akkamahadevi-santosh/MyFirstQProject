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
import com.qutap.dash.customException.ModuleException;
import com.qutap.dash.customException.RequirementException;
import com.qutap.dash.domain.ModuleDomain;
import com.qutap.dash.domain.RequirementDomain;
import com.qutap.dash.model.ModuleModel;
import com.qutap.dash.model.RequirementModel;

import com.qutap.dash.repository.RequirementDao;

@Service
public class RequirementServiceImp implements RequirementService {
	Logger log = LoggerFactory.getLogger(ModuleServiceImpl.class);
	@Autowired
	RequirementDao requirementDao;

	@Override
	public Response saveRequirement(List<RequirementModel> requirementModelList, String moduleId, String projectId) {
		try {
			List<RequirementDomain> reqDomainList = new ArrayList<>();
			requirementModelList.stream().forEach(tmp -> {
				tmp.setRequirementId(new ObjectId().toString());
				tmp.setCreatedDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));
				RequirementDomain requirementDomain = new RequirementDomain();
				BeanUtils.copyProperties(tmp, requirementDomain);
				reqDomainList.add(requirementDomain);
			});
			Response response = requirementDao.saveRequirement(reqDomainList, moduleId, projectId);
			return response;
		} catch (Exception e) {
			log.error("error in saving Requirement details", e);
			throw new RequirementException("error in saving Requirement details");
		}
	}

	@Override
	public RequirementModel getRequirement(String requirementName, String moduleName, String projectName) {
		try {

			RequirementDomain requirementDomain = requirementDao.getRequirementByName(requirementName, moduleName,
					projectName);
			RequirementModel requirementModel = new RequirementModel();
			BeanUtils.copyProperties(requirementDomain, requirementModel);
			return requirementModel;
		} catch (Exception e) {
			log.error("error in getting requirement details", e);
			throw new RequirementException("error in getting Requirement details");
		}
	}

	@Override
	public RequirementModel getRequirementById(String requirementId, String moduleId, String projectId) {
		try {
			RequirementDomain requirementDomain = requirementDao.getRequirementById(requirementId, moduleId, projectId);
			RequirementModel requirementModel = new RequirementModel();
			BeanUtils.copyProperties(requirementDomain, requirementModel);
			return requirementModel;
		} catch (Exception e) {
			log.error("error in getting requirement details", e);
			throw new RequirementException("error in getting requirement details");
		}
	}

	@Override
	public RequirementModel getRequirementById(String requirementId) {
		try {
			RequirementDomain requirementDomain = requirementDao.getRequirementByIdOnly(requirementId);
			RequirementModel requirementModel = new RequirementModel();
			BeanUtils.copyProperties(requirementDomain, requirementModel);
			return requirementModel;
		} catch (Exception e) {
			log.error("error in getting requirement details", e);
			throw new RequirementException("error in getting requirement details");
		}
	}

	@Override
	public List<RequirementModel> getRequirementList(String moduleId, String projectId) {
		try {
			List<RequirementModel> requirementModelList = new ArrayList<RequirementModel>();
			List<RequirementDomain> requirementDomainList = requirementDao.getRequirementList(moduleId, projectId);
			for (RequirementDomain requirementDomain : requirementDomainList) {
				RequirementModel requirementModel = new RequirementModel();
				BeanUtils.copyProperties(requirementDomain, requirementModel);
				requirementModelList.add(requirementModel);
			}
			return requirementModelList;
		} catch (Exception e) {
			log.error("error in getting list of Requirement details", e);
			throw new RequirementException("error in getting list of Requirement details");
		}
	}

	@Override
	public Response updateRequirement(RequirementModel requirementModel) {
		try {
			RequirementDomain requirementDomain = new RequirementDomain();
			BeanUtils.copyProperties(requirementModel, requirementDomain);
			Response response = requirementDao.updateRequirement(requirementDomain);
			return response;
		} catch (Exception e) {
			log.error("error in updating requirement details", e);
			throw new ModuleException("error in updating requirement details");
		}
	}

	@Override
	public Response updateRequirement(RequirementModel requirementModel, String moduleId, String projectId) {
		try {
			RequirementDomain requirementDomain = new RequirementDomain();
			BeanUtils.copyProperties(requirementModel, requirementDomain);
			Response response = requirementDao.updateRequirement(requirementDomain, moduleId, projectId);
			return response;
		} catch (Exception e) {
			log.error("error in updating requirement details", e);
			throw new ModuleException("error in updating requirement details");
		}
	}

	@Override
	public Response deleteRequirement(String requirementId, String moduleId, String projectId) {
		try {

			Response response = requirementDao.deleteRequirement(requirementId, moduleId, projectId);
			return response;
		} catch (Exception e) {
			log.error("error in deleting requirement details", e);
			throw new RequirementException("error in deleting Requirement details");
		}
	}

	@Override
	public Response deleteAllRequirement(String moduleId, String projectId) {
		try {

			Response response = requirementDao.deleteAllRequirement(moduleId, projectId);
			return response;
		} catch (Exception e) {
			log.error("error in deleting All requirements details", e);
			throw new RequirementException("error in deleting All Requirement details");
		}
	}

}