package com.qutap.dash.repository;

import java.util.List;

import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.domain.ProjectInfoDomain;
import com.qutap.dash.domain.RoleDomain;
import com.qutap.dash.domain.UsersDomain;
import com.qutap.dash.model.RoleModel;

public interface UserDao {

	

	public Response saveUser(UsersDomain userDomain);

	public UsersDomain getUserByName(String userName);
	
//	public Response doLogin(UserDomain userDomain);
	
	public UsersDomain getUserById(String userId);

	public UsersDomain userLogin(UsersDomain userDomain);
		
	public List<UsersDomain> getUserList();

	public Response updateUserInfo(UsersDomain userDomain);

	public Response deleteUserInfo(String userId);

	UsersDomain getUserByMail(String mailId);

	public List<RoleDomain> getRoleNameList();

	public Response saveRole(List<RoleDomain> list);

	public UsersDomain resetUserPassword(String mailId, String password);
}
