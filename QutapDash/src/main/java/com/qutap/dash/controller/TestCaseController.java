package com.qutap.dash.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.qutap.dash.customException.TestCaseException;
import com.qutap.dash.customException.TestScenarioException;
import com.qutap.dash.model.RequirementModel;
import com.qutap.dash.model.TestCaseModel;
import com.qutap.dash.model.TestScenarioModel;
import com.qutap.dash.service.TestCaseService;

@RestController
@RequestMapping("/Qutap")
public class TestCaseController {

	Logger log = LoggerFactory.getLogger(TestCaseController.class);

	@Autowired
	TestCaseService testCaseService;

	@PostMapping("/saveTestCase/{testScenarioId}/{requirementId}/{moduleId}/{projectId}")
	public Response saveTestCase(@RequestBody List<TestCaseModel> testCaseModelList,
			@PathVariable String testScenarioId, @PathVariable String requirementId, @PathVariable String moduleId,
			@PathVariable String projectId, HttpServletRequest req) {
		log.info("url of the application" + req.getRequestURL().toString());
		Response response = testCaseService.saveTestCase(testCaseModelList, testScenarioId, requirementId, moduleId,
				projectId);
		response.setUrl(req.getRequestURL().toString());
		return response;
	}

	@GetMapping("/getTestCaseById/{testCaseId}/{testScenarioId}/{requirementId}/{moduleId}/{projectId}")
	public @ResponseBody String getTestCaseById(@PathVariable String testCaseId, @PathVariable String testScenarioId,
			@PathVariable String requirementId, @PathVariable String moduleId, @PathVariable String projectId,
			HttpServletRequest req) throws IOException {
		Response response = Utils.getResponseObject("getting testCase details data");
		try {
			log.info("url of the application" + req.getRequestURL().toString());
			TestCaseModel testCaseModel = testCaseService.getTestCaseById(testCaseId, testScenarioId, requirementId,
					moduleId, projectId);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(testCaseModel);

		} catch (Exception e) {
			log.error("error in getting tsetCase details", e);
			throw new TestCaseException("error in getting tsetCase details");

		}
		return (String) Utils.getJson(response);
	}

	@GetMapping("/listOfTestCases/{testScenarioId}/{requirementId}/{moduleId}/{projectId}")
	public @ResponseBody String getTestCaseList(@PathVariable String testScenarioId, @PathVariable String requirementId,
			@PathVariable String moduleId, @PathVariable String projectId, HttpServletRequest req) throws IOException {
		Response response = Utils.getResponseObject("getting list of test scenario details data");
		try {
			List<TestCaseModel> testCaseModelList = testCaseService.getTestCaseList(testScenarioId, requirementId,
					moduleId, projectId);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(testCaseModelList);

		} catch (Exception e) {
			log.error("error in getting tsetCaseList details", e);
			throw new TestCaseException("error in getting tsetCaseList details");

		}
		return (String) Utils.getJson(response);
	}

	@GetMapping("/getTestCaseByIdOnly/{testCaseId}")
	public @ResponseBody String getTestCaseById(@PathVariable String testCaseName, @PathVariable String testScenarioId,HttpServletRequest req)
			throws IOException {
		Response response = Utils.getResponseObject("getting testCase details data");
		try {
			log.info("url of the application" + req.getRequestURL().toString());
			TestCaseModel testCaseModel = testCaseService.getTestCaseByIdOnly(testCaseName,testScenarioId);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(testCaseModel);

		} catch (Exception e) {
			log.error("error in getting tsetCase details", e);
			throw new TestCaseException("error in getting tsetCase details");

		}
		return (String) Utils.getJson(response);
	}

	@PutMapping("/updateTestCaseById")
	public Response updateTestCase(@RequestBody TestCaseModel testCaseModel, HttpServletRequest req) {
		try {
			Response response = Utils.getResponseObject("updating testSceanrio details data");
			log.info("url of the application" + req.getRequestURL().toString());
			response = testCaseService.updateTestCaseOnly(testCaseModel);
			response.setUrl(req.getRequestURL().toString());

			return response;
		} catch (Exception e) {
			log.error("error in updating tsetCase details", e);
			throw new TestCaseException("error in updating tsetCase details");

		}

	}

	@PutMapping("/updateTestCase/{testScenarioId}/{requirementId}/{moduleId}/{projectId}")
	public Response updateTestCase(@RequestBody TestCaseModel testCaseModel, @PathVariable String testScenarioId,
			@PathVariable String requirementId, @PathVariable String moduleId, @PathVariable String projectId,
			HttpServletRequest req) {
		try {
			Response response = Utils.getResponseObject("updating testSceanrio details data");
			log.info("url of the application" + req.getRequestURL().toString());
			response = testCaseService.updateTestCase(testCaseModel, testScenarioId, requirementId, moduleId,
					projectId);
			response.setUrl(req.getRequestURL().toString());

			return response;
		} catch (Exception e) {
			log.error("error in updating tsetCase details", e);
			throw new TestCaseException("error in updating tsetCase details");

		}

	}

	@DeleteMapping("/deleteTestCase/{testCaseId}/{testScenarioId}/{requirementId}/{moduleId}/{projectId}")
	public Response deleteTestCase(@PathVariable String testCaseId, @PathVariable String testScenarioId,
			@PathVariable String requirementId, @PathVariable String moduleId, @PathVariable String projectId,
			HttpServletRequest req) {
		try {

			log.info("url of the application" + req.getRequestURL().toString());
			Response response = testCaseService.deleteTestCase(testCaseId, testScenarioId, requirementId, moduleId,
					projectId);
			response.setUrl(req.getRequestURL().toString());
			return response;
		} catch (Exception e) {
			log.error("error in deleting tsetCase details", e);
			throw new TestCaseException("error in getting tsetCase details");

		}

	}

	@DeleteMapping("/deleteAllTestCase/{testScenarioId}/{requirementId}/{moduleId}/{projectId}")
	public Response deleteAllTestCase(@PathVariable String testScenarioId, @PathVariable String requirementId,
			@PathVariable String moduleId, @PathVariable String projectId, HttpServletRequest req) {
		try {

			log.info("url of the application" + req.getRequestURL().toString());
			Response response = testCaseService.deleteAllTestCase(testScenarioId, requirementId, moduleId, projectId);
			response.setUrl(req.getRequestURL().toString());
			return response;
		} catch (Exception e) {
			log.error("error in deleting tsetCase details", e);
			throw new TestCaseException("error in getting tsetCase details");

		}

	}

}