package com.qutap.dash.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
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

import com.qutap.dash.commonUtils.PasswordEncAndDec;
import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.commonUtils.StatusCode;
import com.qutap.dash.commonUtils.Utils;
import com.qutap.dash.customException.UserException;
import com.qutap.dash.model.RoleModel;
import com.qutap.dash.model.UsersModel;
import com.qutap.dash.service.UserService;

@RestController
@RequestMapping("/Qutap")
public class UserController {

	Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;
	@Autowired
	private PasswordEncAndDec passwordDecoder;

	@PostMapping("/registration")
	public Response saveUserInfo(@RequestBody UsersModel user, HttpServletRequest req) {
		log.info("url of the application" + req.getRequestURL().toString());
		Response response = userService.saveUser(user);
		response.setUrl(req.getRequestURL().toString());
		return response;
	}

	@PostMapping("/login")
	public Response userLogin(@RequestBody UsersModel user, HttpServletRequest req) {
		log.info("url of the application" + req.getRequestURL().toString());
		System.out.println("input pswd::::::::;"+user.getUserPassword());
		String pass=user.getUserPassword();
		Response response = new Response();
		try {
			UsersModel userdb = userService.userLogin(user);
			if (userdb != null) {
				
				String password= passwordDecoder.passwordDecoder(userdb.getUserPassword());
				System.out.println("pswd::::;"+password);
				System.out.println("input pswd::::::::;"+user.getUserPassword());
				if(pass.equals(password)) {		
				System.out.println("passsword"+password);
				response.setStatus(StatusCode.SUCCESS.name());
				response.setMessage("login successfull");
				response.setUrl(req.getRequestURL().toString());
				return response;
				
				}else {
					System.out.println("ttttttttt");
					response.setStatus(StatusCode.FAILURE.name());
					response.setUrl(req.getRequestURL().toString());
					
					response.setErrors("password is not matched");
					return response;
				}
				
			} else {
				System.out.println("aaaaaaaa");
				response.setStatus(StatusCode.FAILURE.name());
				response.setUrl(req.getRequestURL().toString());
				
				response.setErrors("user not found");
				return response;
			}
		} catch (Exception e) {
			log.error("error in user login detail when searching by Id", e);
			throw new UserException("error in user login ");
		}

		
	}

	@GetMapping("/userDataById/{userId}")
	public @ResponseBody String getUserInfo(@PathVariable String userId, HttpServletRequest req) throws IOException {
		Response response = Utils.getResponseObject("getting user details data");
		try {
			UsersModel userModel = userService.getUserById(userId);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(userModel);
		} catch (Exception e) {
			log.error("error in getting user detail when searching by Id", e);
			throw new UserException("userId not found");
		}
		return (String) Utils.getJson(response);
	}

	@GetMapping("/userDataByName/{userName}")
	public @ResponseBody String getuserbyName(@PathVariable String userName, HttpServletRequest req)
			throws IOException {
		Response response = Utils.getResponseObject("getting user details data");
		try {
			UsersModel userModel = userService.getUserByName(userName);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(userModel);
		} catch (Exception e) {
			log.error("error in getting user detail when searching by name", e);
			throw new UserException("userName not found");
		}
		return (String) Utils.getJson(response);
	}

	@GetMapping("/listOfUsers")
	public @ResponseBody String getUserListInfo(HttpServletRequest req) throws IOException {
		Response response = Utils.getResponseObject("getting user details data");
		try {
			List<UsersModel> userModel = userService.getUserList();
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(userModel);

		} catch (Exception e) {
			log.error("error in getting list of user details", e);
			throw new UserException("user details not found");
		}
		return (String) Utils.getJson(response);
	}

	@PutMapping("/updateUser")
	public Response updateuser(@RequestBody UsersModel userModel, HttpServletRequest req) {
		log.info("url of the application" + req.getRequestURL().toString());
		Response response = userService.updateUserInfo(userModel);
		response.setUrl(req.getRequestURL().toString());
		return response;
	}

	@DeleteMapping("/deleteUser/{userId}")
	public Response deleteuser(@PathVariable String projectId, HttpServletRequest req) {
		log.info("url of the application" + req.getRequestURL().toString());
		Response response = userService.deleteUserInfo(projectId);
		response.setUrl(req.getRequestURL().toString());
		return response;
	}

	@GetMapping("/getListOfUserRoles")
		public @ResponseBody Response getListOfUserRoles(HttpServletRequest req) throws IOException {

			try {
				Response response = userService.getRoleNameList();
				response.setStatus(StatusCode.SUCCESS.name());
				response.setMessage("getting role data successfully");
				return response;
			} catch (Exception e) {
				log.error("error in getting list of user details", e);
				throw new UserException("user details not found");			
			}
			
		}
	@PostMapping("/saveRole")
	public @ResponseBody Response saveRoles(@RequestBody List<RoleModel> roleModelList,HttpServletRequest req)throws IOException{
		try {
			//RoleModel model=new 
			Response response = userService.saveRoles(roleModelList);
			//response.setUrl(req.getRequestURL().toString());
		System.out.println("response::::"+response.getData());
			return response;
		}catch(Exception e) {
			log.error("error in saveRoles details", e);
			throw new UserException("error in saveRoles details");
		}

	}
	

	@GetMapping("/validateMailId/{mailId}")
		public @ResponseBody Response validateUserMailId(@PathVariable String mailId,HttpServletRequest req) throws IOException {
			Response response= new Response(); 
			try {
				UsersModel userModel= userService.getUserByMail(mailId);
				System.out.println(userModel+"ffff");
				if(userModel.getUserMailId()==null) {
					response.setData(null);
					response.setStatus(StatusCode.FAILURE.name());
					response.setMessage("mailId data not found");
					
				}else {
					System.out.println("in else:::"+userModel);
				response.setData(userModel);
				response.setStatus(StatusCode.SUCCESS.name());
				response.setMessage("getting mailId data successfully");
				System.out.println("in else:::"+response);
				}
				System.out.println("in else:::"+response.getStatus());
				JSONObject json=new JSONObject(response);
				System.out.println("in else:::"+json);
				return response;
			} catch (Exception e) {
				log.error("error in getting mailId of user details", e);
				throw new UserException("error in getting mailId of user details");			
			}
			
		}

	
	@PutMapping("/resetUserPassword")
	public @ResponseBody Response resetUserPassword(@RequestBody UsersModel userModel ,HttpServletRequest req) throws IOException {
		Response response= new Response(); 
		try {
			
			UsersModel userModelData= userService.resetUserPassword(userModel.getUserMailId(),userModel.getUserPassword());
			if(userModel!=null) {
			response.setData(userModelData);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setMessage("password successfully changed");
			}else{
				response.setErrors("error in changing password");
				response.setStatus(StatusCode.FAILURE.name());
			}
			return response;
		} catch (Exception e) {
			log.error("error in setting password", e);
			throw new UserException("error in setting password");			
		}
		
	}
}
