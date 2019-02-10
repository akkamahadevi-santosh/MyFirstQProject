
package com.qutap.dash.repository;

import java.util.List;

import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.domain.ProjectInfoDomain;


public interface ProjectInfoDao {

	public Response saveProjectInfo(ProjectInfoDomain projectInfoDomain);

	public ProjectInfoDomain getProjectInfoById(String projectId);
	
	public ProjectInfoDomain getProjectInfoByName(String pName);
	
	public List<ProjectInfoDomain> getProjectListInfo();

	public Response updateProjectInfo(ProjectInfoDomain projectInfoDomain);

	public Response deleteProjectInfo(String projectId);

}