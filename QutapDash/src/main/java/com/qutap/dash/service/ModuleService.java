package com.qutap.dash.service;

import java.util.List;

import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.domain.ModuleDomain;
import com.qutap.dash.model.ModuleModel;
import com.qutap.dash.model.ProjectInfoModel;

public interface ModuleService {

	Response saveModuleModel(List<ModuleModel> moduleModel, String projectId);

	ModuleModel getModuleByName(String moduleName, String projectName);

	List<ModuleModel> getModuleList(String projectId);

	ModuleModel getModuleById(String moduleId, String projectId);

	Response updateModule(ModuleModel moduleModel, String projectId);

	Response deleteModuleModel(String moduleId, String projectId);

	Response deleteAllModuleModel(String projectId);

}
