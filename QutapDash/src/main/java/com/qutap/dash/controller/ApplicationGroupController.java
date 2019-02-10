package com.qutap.dash.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.qutap.dash.customException.OnBoardException;
import com.qutap.dash.domain.ApplicationGroup;
import com.qutap.dash.domain.ApplicationList;
import com.qutap.dash.model.UserGroupsModel;
import com.qutap.dash.model.UserModel;
import com.qutap.dash.service.ApplicationGroupService;

@RestController
@RequestMapping("/Qutap")
public class ApplicationGroupController {

	Logger log = LoggerFactory.getLogger(ApplicationGroupController.class);

	@Autowired
	ApplicationGroupService applicationGroupService;

	@GetMapping("/groupData")
	public @ResponseBody String getApplicationGroupInfo(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		Response response = Utils.getResponseObject("getting applicationGroup details data");
		try {
			List<ApplicationGroup> applicationGroupModel = applicationGroupService.getApplicationGroupInfo();
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(applicationGroupModel);
		} catch (Exception e) {
			log.error("error in getting applicationGroup detail ", e);
			throw new OnBoardException("error in getting applicationGroup detail");
		}
		return (String) Utils.getJson(response);
	}

	@PostMapping("/saveUserData")
	public @ResponseBody String saveUserIdmsDataModel(@RequestBody UserModel userModel, HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		Response response = Utils.getResponseObject("saveUserIdmsDataModel details data");
		try {
			response = applicationGroupService.saveUserIdmsDataModel(userModel);
			

		} catch (Exception e) {
			log.error("error in  saveUserIdmsDataModel detail ", e);
			throw new OnBoardException("error in saveUserIdmsDataModel detail");
		}
		return (String) Utils.getJson(response);
	}

	@GetMapping("/getGroupData/{dsid}")
	public @ResponseBody String getuserGroupsInfo(@PathVariable String dsid, HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		Response response = Utils.getResponseObject("getting getuserGroupsInfo details data");
		try {
			UserGroupsModel userIdmsDataModel = applicationGroupService.getUserGroupsInfo(dsid);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(userIdmsDataModel);
		} catch (Exception e) {
			log.error("error in getting getuserGroupsInfo detail ", e);
			throw new OnBoardException("error in getting getuserGroupsInfo detail");
		}
		return (String) Utils.getJson(response);
	}

/*	@GetMapping("/getRoleGroups/{roleId}")
	public @ResponseBody Response getRolesInfo(@PathVariable String roleId, HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		Response response = Utils.getResponseObject("getting getRolesInfo details data");
		try {
			RoleGroups roleGroups = applicationGroupService.getRole(roleId);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(roleGroups);
			return response;
		} catch (Exception e) {
			log.error("error in getting getuserGroupsInfo detail ", e);
			throw new OnBoardException("error in getting getuserGroupsInfo detail");
		}
		
	}*/
	
	/*@PostMapping("/saveUserRole/{userName}/{dsid}/{roleId}")
	public @ResponseBody Response saveRoleUser(@PathVariable String userName,@PathVariable String dsid,@PathVariable String roleId, HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		try {
			Response response= applicationGroupService.saveRoleUser(userName,dsid,roleId);
					return response;
		} catch (Exception e) {
			log.error("error in getting list of ApplicationGroup details", e);
			throw new OnBoardException("error in getting list of ApplicationGroup details");

		}

	}*/
	
	@GetMapping("/getGroupName/{applicationName}")
	public @ResponseBody String getGroupNameInfo(@PathVariable String applicationName, HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		Response response = Utils.getResponseObject("getting getGroupNameInfo details data");
		try {
			ApplicationGroup applicationGroup = applicationGroupService.getGroupNameInfo(applicationName);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(applicationGroup);
		} catch (Exception e) {
			log.error("error in getting getGroupNameInfo detail ", e);
			throw new OnBoardException("error in getting getGroupNameInfo detail");
		}
		return (String) Utils.getJson(response);
	}
	
//===
	@GetMapping("/getApplicationNames/{dsid}")
	public @ResponseBody String getApplicationNames(@PathVariable String dsid, HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		Response response = Utils.getResponseObject("getting getApplicationNames details data");
		try {
			List<ApplicationList> applicationList = applicationGroupService.getApplicationNames(dsid);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(applicationList);
		} catch (Exception e) {
			log.error("error in getting getApplicationNames detail ", e);
			throw new OnBoardException("error in getting getApplicationNames detail");
		}
		return (String) Utils.getJson(response);
	}
	
	@GetMapping("/getAllApplicationNames")
	public @ResponseBody String getAllApplicationNames(HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		Response response = Utils.getResponseObject("getting getAllApplicationNames details data");
		try {
			List<String> applicationGroupList = applicationGroupService.getAllApplicationNames();
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(applicationGroupList);
		} catch (Exception e) {
			log.error("error in getting getAllApplicationNames detail ", e);
			throw new OnBoardException("error in getting getAllApplicationNames detail");
		}
		return (String) Utils.getJson(response);
	}
	
	@GetMapping("/getAllUsersUnderGroup/{groupId}")
	public @ResponseBody String getAllUsersUnderGroup(@PathVariable String groupId, HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		Response response = Utils.getResponseObject("getting getGroupsInfo details data");
		try {
			List<UserGroupsModel> userGroupsModelList = applicationGroupService.getAllUsersUnderGroup(groupId);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(userGroupsModelList);
		} catch (Exception e) {
			log.error("error in getting getGroupsInfo detail ", e);
			throw new OnBoardException("error in getting getGroupsInfo detail");
		}
		return (String) Utils.getJson(response);
	}
	
	@PutMapping("/updateOnBoard/{groupId}/{applicationName}/{value}")
	public @ResponseBody String updateOnBoard(@PathVariable String groupId,@PathVariable String applicationName,@PathVariable boolean value, HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		Response response = Utils.getResponseObject("updateOnBoard details data");
		try {
			response = applicationGroupService.updateOnBoard(groupId,applicationName,value);
		} catch (Exception e) {
			log.error("error in  updateOnBoard detail ", e);
			throw new OnBoardException("error in updateOnBoard detail");
		}
		return (String) Utils.getJson(response);
	}
	
	@GetMapping("/getAllOnBoardedApplications/{dsid}")
	public @ResponseBody String getAllOnBoardedApplications(@PathVariable String dsid,HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		Response response = Utils.getResponseObject("getting getAllOnBoardedApplications details data");
		try {
			List<ApplicationList> applicationGroupList = applicationGroupService.getAllOnBoardedApplications(dsid);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(applicationGroupList);
		} catch (Exception e) {
			log.error("error in getting getAllOnBoardedApplications detail ", e);
			throw new OnBoardException("error in getting getAllOnBoardedApplications detail");
		}
		return (String) Utils.getJson(response);
	}
	
/*	@GetMapping("/getAllApplicationNames1/{step}/{group}")
	public @ResponseBody String getAllApplicationNames1(@PathVariable String step,@PathVariable String group,HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		Response response = Utils.getResponseObject("getting getAllApplicationNames details data");
		try {
			List<String> applicationGroupList = applicationGroupService.getAllApplicationNames1(step,group);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(applicationGroupList);
		} catch (Exception e) {
			log.error("error in getting getAllApplicationNames detail ", e);
			throw new OnBoardException("error in getting getAllApplicationNames detail");
		}
		return (String) Utils.getJson(response);
	}*/
	@GetMapping("/getApplicationDetails/{applicationName}/{dsid}")
	public @ResponseBody String getApplicationDetails(@PathVariable String applicationName,@PathVariable String dsid, HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		Response response = Utils.getResponseObject("getting getApplicationDetails details data");
		try {
			List<ApplicationList> applicationList = applicationGroupService.getApplicationDetails(applicationName,dsid);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(applicationList);
		} catch (Exception e) {
			log.error("error in getting getApplicationDetails detail ", e);
			throw new OnBoardException("error in getting getApplicationDetails detail");
		}
		return (String) Utils.getJson(response);
	}

}
