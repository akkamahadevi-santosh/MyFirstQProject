package com.qutap.dash.service;

import java.util.List;

import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.domain.RequirementDomain;
import com.qutap.dash.model.ModuleModel;
import com.qutap.dash.model.RequirementModel;

public interface RequirementService {

	Response saveRequirement(List<RequirementModel> requirementModelList, String moduleId, String projectId);

	RequirementModel getRequirement(String requirementName, String moduleName, String projectName);

	RequirementModel getRequirementById(String requirementId, String moduleId, String projectId);

	public List<RequirementModel> getRequirementList(String moduleId, String projectId);

	RequirementModel getRequirementById(String requirementId);

	Response updateRequirement(RequirementModel requirementModel);

	public Response updateRequirement(RequirementModel requirementModel, String moduleId, String projectId);

	Response deleteRequirement(String requirementId, String moduleId, String projectId);

	Response deleteAllRequirement(String moduleId, String projectId);

}