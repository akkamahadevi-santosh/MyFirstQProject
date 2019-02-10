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
import com.qutap.dash.customException.ModuleException;
import com.qutap.dash.customException.ProjectInfoException;
import com.qutap.dash.model.ModuleModel;
import com.qutap.dash.service.ModuleService;

@RestController
@RequestMapping("/Qutap")
public class ModuleController {
	Logger log = LoggerFactory.getLogger(ModuleController.class);

	@Autowired
	ModuleService moduleService;

	@PostMapping("/saveModule/{projectId}")
	public Response saveModule(@RequestBody List<ModuleModel> moduleModel,@PathVariable String projectId , HttpServletRequest req) {
		log.info("url of the application" + req.getRequestURL().toString());
		Response response = moduleService.saveModuleModel(moduleModel,projectId);
		response.setUrl(req.getRequestURL().toString());
		return response;
	}
	
	@GetMapping("/getModuleByName/{moduleName}/{projectName}")
	public @ResponseBody String getModuleByName(@PathVariable String moduleName ,@PathVariable String projectName, HttpServletRequest req) throws IOException {
		log.info("url of the application" + req.getRequestURL().toString());
		Response response = Utils.getResponseObject("getting module details data");
		try {
			
			ModuleModel moduleModel = moduleService.getModuleByName(moduleName, projectName);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(moduleModel);
			}
			catch(Exception e) {
				log.error("error in getting module by id",e);
				throw new ModuleException("error in getting module by id");
			}
			return (String) Utils.getJson(response);
		}
	
	

	@GetMapping("/getModuleById/{moduleId}/{projectId}")
	public @ResponseBody String getModuleById(@PathVariable String moduleId ,@PathVariable String projectId, HttpServletRequest req) throws IOException {
		
		Response response = Utils.getResponseObject("getting module details data");
		try {
		
		ModuleModel moduleModel = moduleService.getModuleById(moduleId, projectId);
		response.setStatus(StatusCode.SUCCESS.name());
		response.setUrl(req.getRequestURL().toString());
		response.setData(moduleModel);
		}
		catch(Exception e) {
			log.error("error in getting module by id",e);
			throw new ModuleException("error in getting module by id");
		}
		return (String) Utils.getJson(response);
	}
	
	@GetMapping("/listOfModules/{projectId}")
	public @ResponseBody String getModuleList(@PathVariable String projectId, HttpServletRequest req) throws IOException {
		Response response = Utils.getResponseObject("getting modules details data");
		try {
			List<ModuleModel> moduleModel = moduleService.getModuleList(projectId);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(moduleModel);

		} catch (Exception e) {
			log.error("error in getting list of modules details", e);
			throw new ModuleException("error in getting list of modules details");			
		}
		return (String) Utils.getJson(response);
	}
	
	@PutMapping("/updateModule/{projectId}")
	public Response updateModule(@RequestBody ModuleModel moduleModel,@PathVariable String projectId , HttpServletRequest req) {
		log.info("url of the application" + req.getRequestURL().toString());
		Response response = moduleService.updateModule(moduleModel, projectId);
		response.setUrl(req.getRequestURL().toString());
		return response;
	}

	
	@DeleteMapping("/deleteModule/{moduleId}/{projectId}")
	public Response deleteModule(@PathVariable String moduleId,@PathVariable String projectId , HttpServletRequest req) {
		log.info("url of the application" + req.getRequestURL().toString());
		Response response = moduleService.deleteModuleModel(moduleId,projectId);
		response.setUrl(req.getRequestURL().toString());
		return response;
	}
	
	@DeleteMapping("/deleteAllModule/{projectId}")
	public Response deleteAllModule(@PathVariable String projectId , HttpServletRequest req) {
		log.info("url of the application" + req.getRequestURL().toString());
		Response response = moduleService.deleteAllModuleModel(projectId);
		response.setUrl(req.getRequestURL().toString());
		return response;
	}
	
	
	

}