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
import com.qutap.dash.customException.ProjectInfoException;
import com.qutap.dash.customException.RequirementException;
import com.qutap.dash.customException.TestScenarioException;
import com.qutap.dash.domain.TestScenarioDomain;
import com.qutap.dash.model.ProjectInfoModel;
import com.qutap.dash.model.RequirementModel;
import com.qutap.dash.model.TestScenarioModel;
import com.qutap.dash.service.TestScenarioService;

@RestController
@RequestMapping("/Qutap")
public class TestScenarioController {
	Logger log = LoggerFactory.getLogger(RequirementController.class);

	@Autowired
	TestScenarioService testScenarioService;

	@PostMapping("/saveTestScenario/{requirementId}/{moduleId}/{projectId}")
	public Response saveTestScenario(@RequestBody List<TestScenarioModel> testScenarioModelList,
			@PathVariable String requirementId, @PathVariable String moduleId, @PathVariable String projectId,
			HttpServletRequest req) {
		log.info("url of the application" + req.getRequestURL().toString());
		Response response = testScenarioService.saveTestScenario(testScenarioModelList, requirementId, moduleId,
				projectId);
		response.setUrl(req.getRequestURL().toString());
		return response;
	}

	@GetMapping("/getTestScenario/{testScenarioName}/{requirementName}/{moduleName}/{projectName}")
	public @ResponseBody String getTestScenario(@PathVariable String testScenarioName,
			@PathVariable String requirementName, @PathVariable String moduleName, @PathVariable String projectName,
			HttpServletRequest req) throws IOException {
		log.info("url of the application" + req.getRequestURL().toString());
		Response response = Utils.getResponseObject("getting testScenario details data");
		try {
			TestScenarioModel testScenarioModel = testScenarioService.getTestScenario(testScenarioName, requirementName,
					moduleName, projectName);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(testScenarioModel);
		} catch (Exception e) {
			log.error("error in getting testScenario detail when searching by name", e);
			throw new TestScenarioException("testScenarioName not found");
		}
		return (String) Utils.getJson(response);
	}

	@GetMapping("/getTestScenarioById/{testScenarioId}/{requirementId}/{moduleId}/{projectId}")
	public @ResponseBody String getTestScenarioById(@PathVariable String testScenarioId,
			@PathVariable String requirementId, @PathVariable String moduleId, @PathVariable String projectId,
			HttpServletRequest req) throws IOException {
		Response response = Utils.getResponseObject("getting testSceanrio details data");
		try {
			log.info("url of the application" + req.getRequestURL().toString());
			TestScenarioModel testScenarioModel = testScenarioService.getTestScenarioById(testScenarioId, requirementId,
					moduleId, projectId);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(testScenarioModel);

		} catch (Exception e) {
			log.error("error in getting tsetScenario details", e);
			throw new TestScenarioException("error in getting tsetScenario details");

		}
		return (String) Utils.getJson(response);
	}

	@GetMapping("/getTestScenarioByIdOnly/{testScenarioId}")
	public @ResponseBody String getTestScenarioById(@PathVariable String testScenarioId, HttpServletRequest req)
			throws IOException {
		Response response = Utils.getResponseObject("getting testSceanrio details data");
		try {
			log.info("url of the application" + req.getRequestURL().toString());
			TestScenarioModel testScenarioModel = testScenarioService.getTestScenarioById(testScenarioId);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(testScenarioModel);

		} catch (Exception e) {
			log.error("error in getting tsetScenario details", e);
			throw new TestScenarioException("error in getting tsetScenario details");

		}
		return (String) Utils.getJson(response);
	}

	@GetMapping("/listOfTestScenario/{requirementId}/{moduleId}/{projectId}")
	public @ResponseBody String getTestScenarioList(@PathVariable String requirementId, @PathVariable String moduleId,
			@PathVariable String projectId, HttpServletRequest req) throws IOException {
		Response response = Utils.getResponseObject("getting list of test scenario details data");
		try {
			List<TestScenarioModel> testScenarioModelList = testScenarioService.getTestScenarioList(requirementId,
					moduleId, projectId);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(testScenarioModelList);

		} catch (Exception e) {
			log.error("error in getting list of testScenario details", e);
			throw new TestScenarioException("error in getting list of testScenario details");
		}
		return (String) Utils.getJson(response);
	}

	@PutMapping("/updateTestScenario/{requirementId}/{moduleId}/{projectId}")
	public Response updateTestScenario(@RequestBody TestScenarioModel testScenarioModel,
			@PathVariable String requirementId, @PathVariable String moduleId, @PathVariable String projectId,
			HttpServletRequest req) {
		Response response = Utils.getResponseObject("updating testSceanrio details data");
		log.info("url of the application" + req.getRequestURL().toString());
		response = testScenarioService.updateTestScenario(testScenarioModel, requirementId, moduleId, projectId);
		response.setUrl(req.getRequestURL().toString());

		return response;
	}

	@DeleteMapping("/deleteTestScenario/{testScenarioId}/{requirementId}/{moduleId}/{projectId}")
	public Response deleteTestScenario(@PathVariable String testScenarioId, @PathVariable String requirementId,
			@PathVariable String moduleId, @PathVariable String projectId, HttpServletRequest req) {
		log.info("url of the application" + req.getRequestURL().toString());
		Response response = testScenarioService.deleteTestScenario(testScenarioId, requirementId, moduleId, projectId);
		response.setUrl(req.getRequestURL().toString());
		return response;
	}

	@DeleteMapping("/deleteAllTestScenario/{requirementId}/{moduleId}/{projectId}")
	public Response deleteAllTestScenario(@PathVariable String requirementId, @PathVariable String moduleId,
			@PathVariable String projectId, HttpServletRequest req) {
		log.info("url of the application" + req.getRequestURL().toString());
		Response response = testScenarioService.deleteAllTestScenario(requirementId, moduleId, projectId);
		response.setUrl(req.getRequestURL().toString());
		return response;

	}

}