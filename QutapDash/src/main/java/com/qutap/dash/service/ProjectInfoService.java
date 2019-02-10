package com.qutap.dash.service;

import java.util.List;

import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.model.ProjectInfoModel;


public interface ProjectInfoService {

	public Response saveProjectInfo(ProjectInfoModel projectInfoModel) ;
	
	public ProjectInfoModel getProjectInfoById(String projectId);
	
	public ProjectInfoModel getProjectInfoByName(String projectId);

	public List<ProjectInfoModel> getProjectListInfo();

	public Response updateProjectInfo(ProjectInfoModel projectInfoModel);

	public Response deleteProjectInfo(String projectId);

	 

}