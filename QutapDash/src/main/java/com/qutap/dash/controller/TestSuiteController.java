 package com.qutap.dash.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.commonUtils.StatusCode;
import com.qutap.dash.commonUtils.Utils;
import com.qutap.dash.customException.ProjectInfoException;
import com.qutap.dash.customException.TestSuiteException;
import com.qutap.dash.customException.UserException;
import com.qutap.dash.model.ProjectInfoModel;
import com.qutap.dash.model.TestSuiteModel;
import com.qutap.dash.model.UserModel;
import com.qutap.dash.service.ProjectInfoService;
import com.qutap.dash.service.TestSuiteService;
import com.qutap.dash.service.UserService;

@RestController
@RequestMapping("/Qutap")
public class TestSuiteController {

	 
	Logger log = LoggerFactory.getLogger(TestSuiteController.class);

	
	@Autowired
	TestSuiteService testSuiteService ;
	
		 	  	   
	   @PostMapping("/saveTestSuite")
		public  @ResponseBody String saveTestSuite(@RequestBody TestSuiteModel testSuiteModel, HttpServletRequest req) throws Exception {
System.out.println("TestSuiteController....saveTestSuite(@RequestBody TestSuiteModel testSuiteModel, HttpServletRequest req)"+testSuiteModel);
			
      Response response = Utils.getResponseObject("getting TestSuite details data");
     try {
			TestSuiteModel testSuiteModel2 = testSuiteService.saveTestSuite(testSuiteModel);
			System.out.println("after response rteturn**********************"+response);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(testSuiteModel2);
		
		}	
     catch (Exception e) {
			//response.setStatus(StatusCode.FAILURE.name());
			//response.setStatus(HttpStatus.SC_NOT_FOUND+"");
			//response.setErrors(e);
			System.out.println("in cache block.................");
			response.setUrl(req.getRequestURL().toString());
			response.setMessage(e.getMessage());
		
			log.error("error in getting TestSuite detail when searching by Id", e);
	      	throw new TestSuiteException("testSuiteId not found");
		}
		System.out.println("response   "+Utils.getJson(response));
		return (String) Utils.getJson(response);
	}
	  
	 

		@GetMapping("/TestSuiteDataById/{testSuiteId}")
		public @ResponseBody String getTestSuiteInfoById(@PathVariable String testSuiteId, HttpServletRequest req)throws IOException {
			System.out.println("String getTestSuiteInfoById(@PathVariable String testSuiteId, HttpServletRequest req)...called .....TestSuiteController");
			Response response = Utils.getResponseObject("getting TestSuite details data");
			try {
				TestSuiteModel testSuiteModel = testSuiteService.getTestSuiteInfoById(testSuiteId);
				response.setStatus(StatusCode.SUCCESS.name());
				response.setUrl(req.getRequestURL().toString());
				response.setData(testSuiteModel);
			} catch (Exception e) {
				//response.setStatus(StatusCode.FAILURE.name());
				//response.setStatus(HttpStatus.SC_NOT_FOUND+"");
				//response.setErrors(e);
				System.out.println("in cache block.................");
				response.setUrl(req.getRequestURL().toString());
				response.setMessage(e.getMessage());
			
				log.error("error in getting TestSuite detail when searching by Id", e);
		      	throw new TestSuiteException("testSuiteId not found");
			}
			System.out.println("response   "+Utils.getJson(response));
			return (String) Utils.getJson(response);
		}

		@GetMapping("/testSuiteDataByName/{projectId}/{testSuiteName}")
		public @ResponseBody String getTestSuitebyName(@PathVariable String projectId,@PathVariable String testSuiteName, HttpServletRequest req)
				throws IOException {
			Response response = Utils.getResponseObject("getting TestSuite details data");
			try {
				TestSuiteModel testSuiteModel  = testSuiteService.getTestSuiteByName(projectId,testSuiteName);
				response.setStatus(StatusCode.SUCCESS.name());
				response.setUrl(req.getRequestURL().toString());
				response.setData(testSuiteModel);
			} catch (Exception e) {
				response.setStatus(StatusCode.FAILURE.name());
				log.error("error in getting TestSuite detail when searching by name", e);
				throw new TestSuiteException("TestSuite not found");
			}
			return (String) Utils.getJson(response);
		}

		@GetMapping("/getListOfTestSuitesByProjectId/{projectId}")
		public @ResponseBody String getListOfTestSuitesByProjectId(@PathVariable String projectId,HttpServletRequest req) throws IOException {
			Response response = Utils.getResponseObject("getting TestSuite details data");
			System.out.println("String getListOfTestSuitesByProjectId(@PathVariable String projectId,HttpServletRequest req).**************..called");
			try {
				List<TestSuiteModel> testSuiteModelList = testSuiteService.getListOfTestSuitesByProjectId(projectId);
				response.setStatus(StatusCode.SUCCESS.name());
				response.setUrl(req.getRequestURL().toString());
				response.setData(testSuiteModelList);

			} catch (Exception e) {
				response.setStatus(StatusCode.FAILURE.name());
				log.error("error in getting list of user details", e);
				throw new TestSuiteException("TestSuite details not found");			
			}
			System.out.println("test suite list............../////////////////////////////////////"+Utils.getJson(response));
			return (String) Utils.getJson(response);
		}

		
	 
	   
}
