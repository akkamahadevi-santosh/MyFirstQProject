package com.qutap.dash.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.qutap.dash.commonUtils.ErrorObject;
import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.commonUtils.StatusCode;
import com.qutap.dash.commonUtils.Utils;
import com.qutap.dash.customException.ApplicationHealthException;
import com.qutap.dash.customException.OnBoardException;
import com.qutap.dash.customException.TestExecutionResponseException;
import com.qutap.dash.domain.ApplicationBuildHealthDomain;
import com.qutap.dash.domain.ApplicationStatusReportDomain;
import com.qutap.dash.domain.TestResultResponseDomain;
import com.qutap.dash.model.ApplicationBuildHealthModel;
import com.qutap.dash.model.ApplicationDetailsModel;
import com.qutap.dash.model.ApplicationStatusReportModel;
import com.qutap.dash.model.RadarDefectsModel;
import com.qutap.dash.model.TestResultResponseModel;
import com.qutap.dash.repository.DashboardDao;
import com.qutap.dash.service.DashboardService;

@RestController
@RequestMapping("/Qutap")
public class DashboardController {

	Logger log = LoggerFactory.getLogger(DashboardController.class);
	
	@Autowired
	DashboardService dashboardService;
	
	@Autowired
	DashboardDao dashboardDao;

	@GetMapping("applicationHealth/lastFourBuild/individualApp/{projectName}")
	public @ResponseBody String getApplicationHealth(@PathVariable String projectName, HttpServletRequest req)
			throws IOException {
		Response response = Utils.getResponseObject("getting lastFourBuild applicationHealth of individualApp  data");
		try {
			List<ApplicationBuildHealthModel> applicationBuildHealthModel = dashboardService.getApplicationHealth(projectName);
			if(!applicationBuildHealthModel.isEmpty()) {
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(applicationBuildHealthModel);
			}else {
				ErrorObject err=Utils.getErrorResponse("getting lastFourBuild  applicationHealth", "error in getting lastFourBuild applicationHealth");
				response.setStatus(StatusCode.FAILURE.name());
				response.setErrors(err);
				response.setUrl(req.getRequestURL().toString());
			}
		} catch (Exception e) {
			log.error("error getting lastFourBuild applicationHealth of individualApp  data", e);
			throw new ApplicationHealthException("error getting lastFourBuild applicationHealth of individualApp");
		}
		return (String) Utils.getJson(response);
	}
	
	
	
	
	@GetMapping("executionBreakDown/individualApp/weekly/{projectName}")
	public @ResponseBody String getexecutionBreakDownWeekly(@PathVariable String projectName, HttpServletRequest req)
			throws IOException {
		Response response = Utils.getResponseObject("getting executionBreakDownWeekly data");
		try {
			List<ApplicationStatusReportModel>  applicationStatusReportModel= dashboardService.getexecutionBreakDownWeekly(projectName);
			if(!applicationStatusReportModel.isEmpty()) {
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(applicationStatusReportModel);
			}
			else {
				response.setStatus(StatusCode.FAILURE.name());
				response.setErrors("Error in getting applicationBuildHealth");
				response.setUrl(req.getRequestURL().toString());
			}
		} catch (Exception e) {
			log.error("error getting executionBreakDownWeekly  data", e);
			throw new ApplicationHealthException("error getting executionBreakDownWeekly data");
		}
		return (String) Utils.getJson(response);
	}
	
	@GetMapping("executionBreakDown/individualApp/lastFourBuild/{projectName}")
	public @ResponseBody String getexecutionBreakDownLastFourBuild(@PathVariable String projectName, HttpServletRequest req)
			throws IOException {
		Response response = Utils.getResponseObject("getting executionBreakDownLastFourBuild data");
		try {
			List<ApplicationStatusReportModel>  applicationStatusReportModel= dashboardService.getexecutionBreakDownLastFourBuild(projectName);
			if(!applicationStatusReportModel.isEmpty()) {
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(applicationStatusReportModel);
			}else {
				response.setStatus(StatusCode.FAILURE.name());
				response.setErrors("Error in getting executionBreakDownLastFourBuild");
				response.setUrl(req.getRequestURL().toString());
			}
		} catch (Exception e) {
			log.error("error getting executionBreakDownLastFourBuild  data", e);
			throw new ApplicationHealthException("error getting executionBreakDownLastFourBuild data");
		}
		return (String) Utils.getJson(response);
	}
	
	@GetMapping("testExecutionResponse/lastBuild/{projectName}")
	public @ResponseBody String getLastBuildTestExecution(@PathVariable String projectName, HttpServletRequest req)
			throws IOException {
		Response response = Utils.getResponseObject("getting LastBuildTestExecution data");
		try {
			List<TestResultResponseModel>  testResultResponseModel= dashboardService.getLastBuildTestExecution(projectName);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(testResultResponseModel);
		} catch (Exception e) {
			log.error("error getting LastBuildTestExecution  data", e);
			throw new TestExecutionResponseException("error getting LastBuildTestExecution data");
		}
		return (String) Utils.getJson(response);
	}
	
	@GetMapping("testExecutionResponse/allBuild/{projectName}")
	public @ResponseBody String getAllTestExecution(@PathVariable String projectName, HttpServletRequest req)
			throws IOException {
		Response response = Utils.getResponseObject("getting All BuildTestExecution data");
		try {
			List<TestResultResponseModel>  testResultResponseModel= dashboardService.getAllTestExecution(projectName);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(testResultResponseModel);
		} catch (Exception e) {
			log.error("error getting AllBuildTestExecution  data", e);
			throw new TestExecutionResponseException("error getting All BuildTestExecution data");
		}
		return (String) Utils.getJson(response);
	}
	
	@GetMapping("executionBreakDown/individualApp/lastBuild/{projectName}")
	public @ResponseBody String getexecutionBreakDownLastBuild(@PathVariable String projectName, HttpServletRequest req)
			throws IOException {
		Response response = Utils.getResponseObject("getting executionBreakDownLastBuild data");
		try {
			ApplicationStatusReportModel  applicationStatusReportModel= dashboardService.getexecutionBreakDownLastBuild(projectName);
			if(applicationStatusReportModel!=null) {
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(applicationStatusReportModel);
			}else {
				ErrorObject err=Utils.getErrorResponse("getting executionBreakDownLastBuild", "error in getting executionBreakDownLastBuild Data");
				response.setStatus(StatusCode.FAILURE.name());
				response.setErrors(err);
				response.setUrl(req.getRequestURL().toString());
			}
			
		} catch (Exception e) {
			log.error("error getting executionBreakDownLastBuild  data", e);
			throw new ApplicationHealthException("error getting executionBreakDownLastBuild data");
		}
		return (String) Utils.getJson(response);
	}
	
	@GetMapping("openRadars/{componentName}")
	public @ResponseBody String getOpenDefectsFromRadar(@PathVariable("componentName") String componentName, HttpServletRequest req) throws IOException {
		Response response = Utils.getResponseObject("getting RadrDefects data");
		try {
		List<RadarDefectsModel>  radarDefectsModelList= dashboardService.getOpenDefectsFromRadar(componentName);
		response.setStatus(StatusCode.SUCCESS.name());
		response.setUrl(req.getRequestURL().toString());
		response.setData(radarDefectsModelList);
		} catch (Exception e) {
			log.error("error getting RadrDefects data", e);
			throw new TestExecutionResponseException("error getting RadrDefects data");
		}
		return (String) Utils.getJson(response);
	}
	
	

@GetMapping("video/{txnId}/{testCaseId}")
	public ResponseEntity<Resource>  getVideoPath(@PathVariable("txnId") String txnId,@PathVariable("testCaseId") String testCaseId, HttpServletRequest req) throws IOException {
		Response response = Utils.getResponseObject("getting videoPath data");
		try {
			String path=dashboardService.getVideoPath(txnId,testCaseId);
			File file=new File(path);
					    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
					    String mimeType = URLConnection.guessContentTypeFromName(file.getName());
					    HttpHeaders headers = new HttpHeaders();
					    headers.set(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=" + file.getName());
					    return ResponseEntity.ok()
					            .contentLength(file.length())
					            .headers(headers)
					            .contentType(MediaType.parseMediaType("application/octet-stream"))
					            .body(resource);
	
	    
		} catch (Exception e) {
			log.error("error getting video data", e);
			throw new TestExecutionResponseException("error getting video data");
		}
		
	}
	
	@GetMapping("screen/{txnId}/{testCaseId}/{testStepId}")
	public ResponseEntity<Resource>  getScreenShotPath(@PathVariable("txnId") String txnId,@PathVariable("testCaseId")String testCaseId,@PathVariable("testStepId") String testStepId, HttpServletRequest req) throws IOException {
		Response response = Utils.getResponseObject("getting screenShotPath data");
		try {
			String path=dashboardService.getScreenShotPath(txnId,testCaseId,testStepId);		
			File file=new File(path);
					    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
					    HttpHeaders headers = new HttpHeaders();
					    headers.set(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=" + file.getName());
					    return ResponseEntity.ok()
					            .contentLength(file.length())
					            .contentType(MediaType.parseMediaType("application/octet-stream"))
					            .body(resource);
	
	    
		} catch (Exception e) {
			log.error("error getting screenShot data", e);
			throw new TestExecutionResponseException("error getting screenShot data");
		}
		
	}
	
	
	@PostMapping("applicationHealth/lastBuild/individualApp")
	public @ResponseBody String getAllApplicationHealth(@RequestBody List<String> projectNameList, HttpServletRequest req)
			throws IOException {
		Response response = Utils.getResponseObject("getting lastBuild of all applicationHealth of individualApp  data");
		try {
			List<ApplicationBuildHealthModel> applicationBuildHealthModel = dashboardService.getAllApplicationHealth(projectNameList);
			if(!applicationBuildHealthModel.isEmpty()) {
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(applicationBuildHealthModel);
			}else {
				ErrorObject err=Utils.getErrorResponse("getting lastBuild of all applicationHealth", "error in getting lastBuild of allapplicationHealth");
				response.setStatus(StatusCode.FAILURE.name());
				response.setErrors(err);
				response.setUrl(req.getRequestURL().toString());
			}
			
		} catch (Exception e) {
			log.error("error getting lastBuild of all applicationHealth of individualApp  data", e);
			throw new ApplicationHealthException("error getting lastBuild of all applicationHealth of individualApp");
		}
		return (String) Utils.getJson(response);
	}
	
	@SuppressWarnings("unused")
	@PostMapping("executionBreakDown/individualApp/lastBuild")
	public @ResponseBody String getAllExecutionBreakDownLastBuild(@RequestBody List<String> projectNameList, HttpServletRequest req)
			throws IOException {
		Response response = Utils.getResponseObject("getting all project executionBreakDownLastBuild data");
		try {
			long passed=0;
			long failed=0;
			long totatTests=0;
			long applicationCount=0;
			for(String projectName:projectNameList) {
				ApplicationStatusReportDomain  applicationStatusReportDomain= dashboardDao.getexecutionBreakDownLastBuild(projectName);
				passed=passed+applicationStatusReportDomain.getPassed();
				failed=failed+applicationStatusReportDomain.getFailed();
				totatTests=totatTests+applicationStatusReportDomain.getTotatTests();
				applicationCount=applicationCount+applicationStatusReportDomain.getApplicationCount();
			}
			ApplicationStatusReportDomain allApplicationStatusReportDomain=new ApplicationStatusReportDomain();
			allApplicationStatusReportDomain.setApplicationCount(applicationCount);
			allApplicationStatusReportDomain.setPassed(passed);
			allApplicationStatusReportDomain.setFailed(failed);
			allApplicationStatusReportDomain.setTotatTests(totatTests);
			if(allApplicationStatusReportDomain!=null) {
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(allApplicationStatusReportDomain);
			}else{
				ErrorObject err=Utils.getErrorResponse("getting all project executionBreakDownLastBuild", "error in getting all project executionBreakDownLastBuild Data");
				response.setStatus(StatusCode.FAILURE.name());
				response.setErrors(err);
				response.setUrl(req.getRequestURL().toString());
			}
			
		} catch (Exception e) {
			log.error("error getting all project executionBreakDownLastBuild  data", e);
			throw new ApplicationHealthException("error getting all project executionBreakDownLastBuild data");
		}
		return (String) Utils.getJson(response);
	}
	
	@PostMapping("executionBreakDown/individualApp/weekly")
	public @ResponseBody String getAllExecutionBreakDownWeekly(@RequestBody List<String> projectNameList, HttpServletRequest req)
			throws IOException {
		Response response = Utils.getResponseObject("getting all project executionBreakDownWeekly data");
		try {
			List<ApplicationStatusReportDomain>  allApplicationStatusReportDomain=new ArrayList<>();
			for(String projectName:projectNameList) {
			List<ApplicationStatusReportDomain>  applicationStatusReportDomain= dashboardDao.getexecutionBreakDownWeekly(projectName);
			System.out.println("value"+applicationStatusReportDomain);
			allApplicationStatusReportDomain.addAll(applicationStatusReportDomain);
			}
			if(!allApplicationStatusReportDomain.isEmpty()) {
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(allApplicationStatusReportDomain);
			}
			else {
				response.setStatus(StatusCode.FAILURE.name());
				response.setErrors("Error in getting all application ExecutionBreakDownWeekly");
				response.setUrl(req.getRequestURL().toString());
			}
		} catch (Exception e) {
			log.error("error getting all project executionBreakDownWeekly  data", e);
			throw new ApplicationHealthException("error getting all project executionBreakDownWeekly data");
		}
		return (String) Utils.getJson(response);
	}
	
	@PostMapping("executionBreakDown/individualApp/lastFourBuild")
	public @ResponseBody String getAllExecutionBreakDownLastFourBuild(@RequestBody List<String> projectNameList, HttpServletRequest req)
			throws IOException {
		Response response = Utils.getResponseObject("getting all project executionBreakDownLastFourBuild data");
		try {
			List<ApplicationStatusReportDomain>  allApplicationStatusReportDomain=new ArrayList<>();
			for(String projectName:projectNameList) {	
			List<ApplicationStatusReportDomain>  applicationStatusReportDomain= dashboardDao.getexecutionBreakDownLastFourBuild(projectName);
			System.out.println("value"+applicationStatusReportDomain);
			allApplicationStatusReportDomain.addAll(applicationStatusReportDomain);
			}
			if(!allApplicationStatusReportDomain.isEmpty()) {
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(allApplicationStatusReportDomain);
			}
			else {
				response.setStatus(StatusCode.FAILURE.name());
				response.setErrors("Error in getting all ExecutionBreakDownLastFourBuild");
				response.setUrl(req.getRequestURL().toString());
			}
		} catch (Exception e) {
			log.error("error getting all project executionBreakDownLastFourBuild  data", e);
			throw new ApplicationHealthException("error getting all project executionBreakDownLastFourBuild data");
		}
		return (String) Utils.getJson(response);
	}
	
	@PostMapping("testExecutionResponse/lastBuild")
	public @ResponseBody String getAllLastBuildTestExecution(@RequestBody List<String> projectNameList, HttpServletRequest req)
			throws IOException {
		Response response = Utils.getResponseObject("getting all project LastBuildTestExecution data");
		try {
			List<TestResultResponseDomain>  allTestResultResponseDomain=new ArrayList<>();
			for(String projectName:projectNameList) {
			List<TestResultResponseDomain>  testResultResponseDomain= dashboardDao.getLastBuildTestExecution(projectName);
			allTestResultResponseDomain.addAll(testResultResponseDomain);
			}
			if(!allTestResultResponseDomain.isEmpty()) {
				response.setStatus(StatusCode.SUCCESS.name());
				response.setUrl(req.getRequestURL().toString());
				response.setData(allTestResultResponseDomain);
				}
				else {
					response.setStatus(StatusCode.FAILURE.name());
					response.setErrors("Error in getting all project last build testExecutionResponse");
					response.setUrl(req.getRequestURL().toString());
				}
		} catch (Exception e) {
			log.error("error in all project getting LastBuildTestExecution  data", e);
			throw new TestExecutionResponseException("error in getting all project LastBuildTestExecution data");
		}
		return (String) Utils.getJson(response);
	}
	
	@PostMapping("testExecutionResponse/allBuild")
	public @ResponseBody String getAllTestExecution(@RequestBody List<String> projectNameList, HttpServletRequest req)
			throws IOException {
		Response response = Utils.getResponseObject("getting All project testExecutionResponse data");
		try {
			List<TestResultResponseDomain>  allTestResultResponseDomain=new ArrayList<>();
			for(String projectName:projectNameList) {
			List<TestResultResponseDomain>  testResultResponseDomain= dashboardDao.getAllTestExecution(projectName);
			allTestResultResponseDomain.addAll(testResultResponseDomain);
			}
			if(!allTestResultResponseDomain.isEmpty()) {
				response.setStatus(StatusCode.SUCCESS.name());
				response.setUrl(req.getRequestURL().toString());
				response.setData(allTestResultResponseDomain);
				}
				else {
					response.setStatus(StatusCode.FAILURE.name());
					response.setErrors("Error in getting all project testExecutionResponse");
					response.setUrl(req.getRequestURL().toString());
				}
		} catch (Exception e) {
			log.error("error getting AllBuildTestExecution  data", e);
			throw new TestExecutionResponseException("error getting All project testExecutionResponse data");
		}
		return (String) Utils.getJson(response);
	}
	
	
	/**  Service for getting all application details   
    for dashboard bubbleChart */

@GetMapping("/getAllApplicationDetails/{dsid}")
public @ResponseBody String getAllApplicationDetails(@PathVariable String dsid, HttpServletRequest req,
		HttpServletResponse res) throws IOException {
	Response response = Utils.getResponseObject("getting getAllApplicationDetails  data");
	try {
		List<ApplicationDetailsModel> applicationList = dashboardService.getAllApplicationDetails(dsid);
		response.setStatus(StatusCode.SUCCESS.name());
		response.setUrl(req.getRequestURL().toString());
		response.setData(applicationList);
	} catch (Exception e) {
		log.error("error in getting getAllApplicationDetails data ", e);
		throw new OnBoardException("error in getting getAllApplicationDetails  data");
		
	}
	return (String) Utils.getJson(response);
}

	
}
