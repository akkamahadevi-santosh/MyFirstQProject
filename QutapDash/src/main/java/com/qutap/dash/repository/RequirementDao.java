package com.qutap.dash.repository;

import java.util.List;

import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.domain.RequirementDomain;

public interface RequirementDao {
	Response saveRequirement(List<RequirementDomain> requirementDomain, String moduleId, String projectId);

	Response saveRequirementByName(List<RequirementDomain> requirementDomainList, String moduleName,
			String projectName);

	RequirementDomain getRequirementByName(String requirementName, String moduleName, String projectName);

	RequirementDomain getRequirementById(String requirementId, String moduleId, String projectId);

	RequirementDomain getRequirementByIdOnly(String requirementId);

	RequirementDomain getRequirementByNameOnly(String requirementName, String moduleId);

	public List<RequirementDomain> getRequirementList(String moduleId, String projectId);

	Response deleteRequirement(String requirementId, String moduleId, String projectId);

	Response updateRequirement(RequirementDomain requirementDomain, String ModuleId, String projectId);

	Response updateRequirement(RequirementDomain requirementDomain);

	Response deleteAllRequirement(String moduleId, String projectId);

}