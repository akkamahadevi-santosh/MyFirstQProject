package com.qutap.dash.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qutap.dash.customException.ApplicationHealthException;
import com.qutap.dash.customException.OnBoardException;
import com.qutap.dash.customException.ProjectInfoException;
import com.qutap.dash.customException.TestExecutionResponseException;
import com.qutap.dash.domain.ApplicationBuildHealthDomain;
import com.qutap.dash.domain.ApplicationDetailsDomain;
import com.qutap.dash.domain.ApplicationStatusReportDomain;
import com.qutap.dash.domain.ProjectInfoDomain;
import com.qutap.dash.domain.RadarDefectsDomain;
import com.qutap.dash.domain.SummaryReportDomain;
import com.qutap.dash.domain.TestResultResponseDomain;
import com.qutap.dash.model.ApplicationBuildHealthModel;
import com.qutap.dash.model.ApplicationDetailsModel;
import com.qutap.dash.model.ApplicationStatusReportModel;
import com.qutap.dash.model.DetailedReportModel;
import com.qutap.dash.model.ProjectInfoModel;
import com.qutap.dash.model.RadarDefectsModel;
import com.qutap.dash.model.SummaryReportModel;
import com.qutap.dash.model.TestResultResponseModel;
import com.qutap.dash.repository.DashboardDao;

@Service
public class DashboardServiceImpl implements DashboardService{

	@Autowired
	DashboardDao dashboardDao;


	Logger log= LoggerFactory.getLogger(DashboardServiceImpl.class);


	@Override
	public List<ApplicationBuildHealthModel> getApplicationHealth(String projectName) {
		try {
			List<ApplicationBuildHealthModel> applicationBuildHealthModelList = new ArrayList<>();
			List<ApplicationBuildHealthDomain> applicationBuildHealthDomainList = dashboardDao.getApplicationHealth(projectName);

			for(ApplicationBuildHealthDomain applicationBuildHealthDomain:applicationBuildHealthDomainList) {
				ApplicationBuildHealthModel applicationBuildHealthModel=new ApplicationBuildHealthModel();
				BeanUtils.copyProperties(applicationBuildHealthDomain, applicationBuildHealthModel);
				applicationBuildHealthModelList.add(applicationBuildHealthModel);
			}

			return applicationBuildHealthModelList;
		} catch (Exception e) {
			log.error("error getting lastFourBuild applicationHealth of individualApp  data", e);
			throw new ApplicationHealthException("error getting lastFourBuild applicationHealth of individualApp  data");

		}
	}


	@Override
	public List<ApplicationStatusReportModel> getexecutionBreakDownWeekly(String projectName) {
		try {
			List<ApplicationStatusReportModel> ApplicationStatusReportModelList = new ArrayList<>();
			List<ApplicationStatusReportDomain> ApplicationStatusReportDomainList = dashboardDao.getexecutionBreakDownWeekly(projectName);

			for(ApplicationStatusReportDomain applicationStatusReportDomain:ApplicationStatusReportDomainList) {
				ApplicationStatusReportModel applicationStatusReportModel=new ApplicationStatusReportModel();
				BeanUtils.copyProperties(applicationStatusReportDomain, applicationStatusReportModel);
				ApplicationStatusReportModelList.add(applicationStatusReportModel);
			}
			return ApplicationStatusReportModelList;
		} catch (Exception e) {
			log.error("error getting executionBreakDownWeekly  data", e);
			throw new ApplicationHealthException("error getting executionBreakDownWeekly  data");

		}
	}


	@Override
	public List<ApplicationStatusReportModel> getexecutionBreakDownLastFourBuild(String projectName) {
		try {
			List<ApplicationStatusReportModel> ApplicationStatusReportModelList = new ArrayList<>();
			List<ApplicationStatusReportDomain> ApplicationStatusReportDomainList = dashboardDao.getexecutionBreakDownLastFourBuild(projectName);

			for(ApplicationStatusReportDomain applicationStatusReportDomain:ApplicationStatusReportDomainList) {
				ApplicationStatusReportModel applicationStatusReportModel=new ApplicationStatusReportModel();
				BeanUtils.copyProperties(applicationStatusReportDomain, applicationStatusReportModel);
				ApplicationStatusReportModelList.add(applicationStatusReportModel);
			}
			return ApplicationStatusReportModelList;
		} catch (Exception e) {
			log.error("error getting executionBreakDownLastFourBuild  data", e);
			throw new ApplicationHealthException("error getting executionBreakDownLastFourBuild  data");

		}
	}


	@Override
	public List<TestResultResponseModel> getLastBuildTestExecution(String projectName) {
		try {
			List<TestResultResponseModel> testResultResponseModelList = new ArrayList<>();
			List<TestResultResponseDomain> testResultResponseDomainList = dashboardDao.getLastBuildTestExecution(projectName);

			for(TestResultResponseDomain testResultResponseDomain:testResultResponseDomainList) {
				TestResultResponseModel testResultResponseModel=new TestResultResponseModel();
				BeanUtils.copyProperties(testResultResponseDomain, testResultResponseModel);
				testResultResponseModelList.add(testResultResponseModel);
			}

			return testResultResponseModelList;
		} catch (Exception e) {
			log.error("error getting LastBuildTestExecution", e);
			throw new TestExecutionResponseException("error getting in getLastBuildTestExecution");

		}
	}


	@Override
	public List<TestResultResponseModel> getAllTestExecution(String projectName) {
		try {
			List<TestResultResponseModel> testResultResponseModelList = new ArrayList<>();
			List<TestResultResponseDomain> testResultResponseDomainList = dashboardDao.getAllTestExecution(projectName);

			for(TestResultResponseDomain testResultResponseDomain:testResultResponseDomainList) {
				TestResultResponseModel testResultResponseModel=new TestResultResponseModel();
				BeanUtils.copyProperties(testResultResponseDomain, testResultResponseModel);
				testResultResponseModelList.add(testResultResponseModel);
			}

			return testResultResponseModelList;
		} catch (Exception e) {
			log.error("error getting lastFourBuild  in AllTestExecution", e);
			throw new TestExecutionResponseException("error getting in AllTestExecution");

		}
	}


	@Override
	public ApplicationStatusReportModel getexecutionBreakDownLastBuild(String projectName) {
		try {
			ApplicationStatusReportModel applicationStatusReportModel = new ApplicationStatusReportModel();
			ApplicationStatusReportDomain applicationStatusReportDomain = dashboardDao.getexecutionBreakDownLastBuild(projectName);
			BeanUtils.copyProperties(applicationStatusReportDomain, applicationStatusReportModel);					
			return applicationStatusReportModel;
		} catch (Exception e) {
			log.error("error getting executionBreakDownLastBuild  data", e);
			throw new ApplicationHealthException("error getting executionBreakDownLastBuild  data");

		}
	}
	
	@Override
	public List<RadarDefectsModel> getOpenDefectsFromRadar(String componentName) {
		try {
			List<RadarDefectsModel> radarDefectsModelList=new ArrayList<>();
			List<RadarDefectsDomain> radarDefectsDomainList=dashboardDao.getOpenDefectsFromRadar(componentName);
			for(RadarDefectsDomain radarDefectsDomain:radarDefectsDomainList) {
				RadarDefectsModel radarDefectsModel=new RadarDefectsModel();
				BeanUtils.copyProperties(radarDefectsDomain, radarDefectsModel);
				radarDefectsModelList.add(radarDefectsModel);				
			}
			return radarDefectsModelList;
		} catch (Exception e) {
			log.error("error getting RadarDefects Data", e);
			throw new TestExecutionResponseException("error getting RadarDefects Data");

		}

	}

	
	@Override
	public String getVideoPath(String txnId,String testCaseId) {
		try {
			
			String path= dashboardDao.getVideoPath(txnId,testCaseId);
								
			return path;
		} catch (Exception e) {
			log.error("error getting videoPath  data", e);
			throw new TestExecutionResponseException("error getting videoPath Data");

		}
	}
	@Override
	public String getScreenShotPath(String txnId,String testCaseId,String testStepId) {
		try {
			
			String path= dashboardDao.getScreenShotPath(txnId,testCaseId,testStepId);
								
			return path;
		} catch (Exception e) {
			log.error("error getting videoPath  data", e);
			throw new TestExecutionResponseException("error getting videoPath Data");

		}
	}


	@Override
	public List<ApplicationBuildHealthModel> getAllApplicationHealth(List<String> projectNameList) {
		try {
			List<ApplicationBuildHealthModel> applicationBuildHealthModelList = new ArrayList<>();
			List<ApplicationBuildHealthDomain> applicationBuildHealthDomainList = dashboardDao.getAllApplicationHealth(projectNameList);

			for(ApplicationBuildHealthDomain applicationBuildHealthDomain:applicationBuildHealthDomainList) {
				ApplicationBuildHealthModel applicationBuildHealthModel=new ApplicationBuildHealthModel();
				BeanUtils.copyProperties(applicationBuildHealthDomain, applicationBuildHealthModel);
				applicationBuildHealthModelList.add(applicationBuildHealthModel);
			}

			return applicationBuildHealthModelList;
		} catch (Exception e) {
			log.error("error getting lastBuild all applicationHealth of individualApp  data", e);
			throw new ApplicationHealthException("error getting lastBuild all applicationHealth of individualApp  data");

		}
	}


	/**  Service for getting all application details   
    for dashboard bubbleChart */
	
	@Override
	public List<ApplicationDetailsModel> getAllApplicationDetails(String dsid) {
		
		try {
			List<ApplicationDetailsModel> applicationDetailsModelList = new ArrayList<>();
			List<ApplicationDetailsDomain> applicationDetailsDomainList = dashboardDao.getAllApplicationDetails(dsid);

			for(ApplicationDetailsDomain applicationDetailsDomain:applicationDetailsDomainList) {
				ApplicationDetailsModel applicationDetailsModel=new ApplicationDetailsModel();
				BeanUtils.copyProperties(applicationDetailsDomain, applicationDetailsModel);
				applicationDetailsModelList.add(applicationDetailsModel);
			}

			return applicationDetailsModelList;
			} catch (Exception e) {
				log.error("error in getting getAllApplicationDetails data", e);
				throw new OnBoardException("error in getting getAllApplicationDetails data");
				
			}
		}
	
	

}

