package com.qutap.dash.repository;

import java.util.List;

import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.domain.ApplicationGroup;
import com.qutap.dash.domain.ApplicationList;
import com.qutap.dash.domain.UserDomain;
import com.qutap.dash.domain.UserGroupsDomain;
import com.qutap.dash.domain.UserRoleDomain;

public interface ApplicationGroupDao {

	List<ApplicationGroup> getApplicationGroupInfo();


	Response saveUserIdmsDataDomain(UserDomain userDomain);


	UserGroupsDomain getUserGroupsInfo(String dsid);
	
//	RoleGroups getRole(String roleId);
	
	//Response saveRoleUser(String userName,String dsid,String roleId);
	
	UserRoleDomain getUserRoleDetails(String dsid);


	ApplicationGroup getGroupNameInfo(String applicationName);


	UserGroupsDomain getApplicationNames(String dsid);


	List<ApplicationGroup> getAllApplicationNames();


	List<UserGroupsDomain> getAllUsersUnderGroup(String groupId);


	Response updateOnBoard(String groupId,String applicationName,boolean value);


	UserGroupsDomain getAllOnBoardedApplications(String dsid);


	UserGroupsDomain getApplicationDetails(String applicationName,String dsid);


	
	
	

}
