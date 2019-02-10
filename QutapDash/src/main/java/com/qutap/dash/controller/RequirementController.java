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

import com.qutap.dash.customException.RequirementException;

import com.qutap.dash.model.RequirementModel;
import com.qutap.dash.service.RequirementService;

@RestController
@RequestMapping("/Qutap")
public class RequirementController {

	Logger log = LoggerFactory.getLogger(RequirementController.class);

	@Autowired
	RequirementService requirementService;

	@PostMapping("/saveRequirement/{moduleId}/{projectId}")
	public Response saveRequirement(@RequestBody List<RequirementModel> requirementModelList,
			@PathVariable String moduleId, @PathVariable String projectId, HttpServletRequest req) {
		log.info("url of the application" + req.getRequestURL().toString());
		Response response = requirementService.saveRequirement(requirementModelList, moduleId, projectId);
		response.setUrl(req.getRequestURL().toString());
		return response;
	}

	@GetMapping("/getRequirementByName/{requirementName}/{moduleName}/{projectName}")
	public @ResponseBody String getRequirement(@PathVariable String requirementName, @PathVariable String moduleName,
			@PathVariable String projectName, HttpServletRequest req) throws IOException {
		Response response = Utils.getResponseObject("getting requirement details data");
		try {
			log.info("url of the application" + req.getRequestURL().toString());
			RequirementModel requirementModel = requirementService.getRequirement(requirementName, moduleName,
					projectName);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(requirementModel);

		} catch (Exception e) {
			log.error("error in getting requirement details", e);
			throw new RequirementException("error in getting requirement details");

		}
		return (String) Utils.getJson(response);
	}

	@GetMapping("/listOfRequirement/{moduleId}/{projectId}")
	public @ResponseBody String getRequirementList(@PathVariable String moduleId, @PathVariable String projectId,
			HttpServletRequest req) throws IOException {
		Response response = Utils.getResponseObject("getting requirement details data");
		try {
			List<RequirementModel> requirementModel = requirementService.getRequirementList(moduleId, projectId);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(requirementModel);

		} catch (Exception e) {
			log.error("error in getting list of requirement details", e);
			throw new RequirementException("error in getting list of requirement details");
		}
		return (String) Utils.getJson(response);
	}

	@GetMapping("/getRequirementById/{requirementId}/{moduleId}/{projectId}")
	public @ResponseBody String getRequirementById(@PathVariable String requirementId, @PathVariable String moduleId,
			@PathVariable String projectId, HttpServletRequest req) throws IOException {
		Response response = Utils.getResponseObject("getting requirement details data");
		try {
			log.info("url of the application" + req.getRequestURL().toString());
			RequirementModel requirementModel = requirementService.getRequirementById(requirementId, moduleId,
					projectId);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(requirementModel);

		} catch (Exception e) {
			log.error("error in getting requirement details", e);
			throw new RequirementException("error in getting requirement details");

		}
		return (String) Utils.getJson(response);
	}

	@GetMapping("/getRequirementByIdOnly/{requirementId}")
	public @ResponseBody String getRequirementById(@PathVariable String requirementId, HttpServletRequest req)
			throws IOException {
		Response response = Utils.getResponseObject("getting requirement details data");
		try {
			log.info("url of the application" + req.getRequestURL().toString());
			RequirementModel requirementModel = requirementService.getRequirementById(requirementId);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(requirementModel);

		} catch (Exception e) {
			log.error("error in getting requirement details", e);
			throw new RequirementException("error in getting requirement details");

		}
		return (String) Utils.getJson(response);
	}

	@PutMapping("/updateRequirementById")
	public Response updateRequirement(@RequestBody RequirementModel requirementModel, HttpServletRequest req) {
		log.info("url of the application" + req.getRequestURL().toString());
		Response response = requirementService.updateRequirement(requirementModel);
		response.setUrl(req.getRequestURL().toString());
		return response;
	}

	@PutMapping("/updateRequirement/{moduleId}/{projectId}")
	public Response updateRequirement(@RequestBody RequirementModel requirementModel, @PathVariable String moduleId,
			@PathVariable String projectId, HttpServletRequest req) {
		log.info("url of the application" + req.getRequestURL().toString());
		Response response = requirementService.updateRequirement(requirementModel, moduleId, projectId);
		response.setUrl(req.getRequestURL().toString());
		return response;
	}

	@DeleteMapping("/deleteRequirement/{requirementId}/{moduleId}/{projectId}")
	public Response deleteRequirement(@PathVariable String requirementId, @PathVariable String moduleId,
			String projectId, HttpServletRequest req) {
		log.info("url of the application" + req.getRequestURL().toString());
		Response response = requirementService.deleteRequirement(requirementId, moduleId, projectId);
		response.setUrl(req.getRequestURL().toString());
		return response;
	}

	@DeleteMapping("/deleteAllRequirement/{moduleId}/{projectId}")
	public Response deleteAllRequirement(@PathVariable String moduleId, String projectId, HttpServletRequest req) {
		log.info("url of the application" + req.getRequestURL().toString());
		Response response = requirementService.deleteAllRequirement(moduleId, projectId);
		response.setUrl(req.getRequestURL().toString());
		return response;
	}

}