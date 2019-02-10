package com.qutap.dash.repository;

import java.text.ParseException;
import java.util.List;

import com.qutap.dash.domain.ApplicationBuildHealthDomain;
import com.qutap.dash.domain.ApplicationDetailsDomain;
import com.qutap.dash.domain.ApplicationStatusReportDomain;
import com.qutap.dash.domain.RadarDefectsDomain;
import com.qutap.dash.domain.SummaryReportDomain;
import com.qutap.dash.domain.TestResultResponseDomain;

public interface DashboardDao {

	List<ApplicationBuildHealthDomain> getApplicationHealth(String projectName);

	List<ApplicationStatusReportDomain> getexecutionBreakDownWeekly(String projectName) throws ParseException;

	List<ApplicationStatusReportDomain> getexecutionBreakDownLastFourBuild(String projectName);
	
	List<TestResultResponseDomain> getAllTestExecution(String projectName);

	ApplicationStatusReportDomain getexecutionBreakDownLastBuild(String projectName);

	List<RadarDefectsDomain> getOpenDefectsFromRadar(String componentName);

	List<TestResultResponseDomain> getLastBuildTestExecution(String projectName);

	String getScreenShotPath(String txnId, String testCaseId, String testStepId);

	String getVideoPath(String txnId, String testCaseId);
	
	String getLastTransactionResult(String projectName);

	List<ApplicationBuildHealthDomain> getAllApplicationHealth(List<String> projectNameList);

	List<ApplicationDetailsDomain> getAllApplicationDetails(String dsid);


}
