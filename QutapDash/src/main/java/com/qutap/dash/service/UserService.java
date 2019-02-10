package com.qutap.dash.service;

import java.util.List;

import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.domain.UsersDomain;
import com.qutap.dash.model.RoleModel;
import com.qutap.dash.model.UsersModel;


public interface UserService {

	
	public Response saveUser(UsersModel userDomain);

	public UsersModel getUserByName(String userName);
	
//	public Response doLogin(UserDomain userDomain);
	
	public UsersModel getUserById(String userEmail);

	public UsersModel userLogin(UsersModel userDomain);
		
	public List<UsersModel> getUserList();

	public Response updateUserInfo(UsersModel userDomain);

	public Response deleteUserInfo(String userId);

	public Response getRoleNameList();

	public UsersModel getUserByMail(String mailId);

	Response saveRoles(List<RoleModel> roleModel);

	public UsersModel resetUserPassword(String mailId, String password);
	
}
