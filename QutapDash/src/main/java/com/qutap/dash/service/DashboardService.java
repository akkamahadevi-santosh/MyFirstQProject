package com.qutap.dash.service;

import java.util.List;
import com.qutap.dash.domain.TestResultResponseDomain;
import com.qutap.dash.model.ApplicationBuildHealthModel;
import com.qutap.dash.model.ApplicationDetailsModel;
import com.qutap.dash.model.ApplicationStatusReportModel;
import com.qutap.dash.model.DetailedReportModel;
import com.qutap.dash.model.RadarDefectsModel;
import com.qutap.dash.model.SummaryReportModel;
import com.qutap.dash.model.TestResultResponseModel;

public interface DashboardService {

	List<ApplicationBuildHealthModel> getApplicationHealth(String projectName);

	List<ApplicationStatusReportModel> getexecutionBreakDownWeekly(String projectName);

	List<ApplicationStatusReportModel> getexecutionBreakDownLastFourBuild(String projectName);
	
	List<TestResultResponseModel> getAllTestExecution(String projectName);

	ApplicationStatusReportModel getexecutionBreakDownLastBuild(String projectName);

	List<RadarDefectsModel> getOpenDefectsFromRadar(String componentName);

	List<TestResultResponseModel> getLastBuildTestExecution(String projectName);

	String getVideoPath(String txnId, String testCaseId);

	String getScreenShotPath(String txnId, String testCaseId, String testStepId);

	List<ApplicationBuildHealthModel> getAllApplicationHealth(List<String> projectNameList);

	List<ApplicationDetailsModel> getAllApplicationDetails(String dsid);

}
