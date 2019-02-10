package com.qutap.dash.service;

import java.util.List;

import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.domain.ApplicationGroup;
import com.qutap.dash.domain.ApplicationList;
import com.qutap.dash.model.UserGroupsModel;
import com.qutap.dash.model.UserModel;

public interface ApplicationGroupService {

	List<ApplicationGroup> getApplicationGroupInfo();
	
	Response saveUserIdmsDataModel(UserModel userModel);

	UserGroupsModel getUserGroupsInfo(String dsid);

	ApplicationGroup getGroupNameInfo(String applicationName);

	List<ApplicationList>  getApplicationNames(String dsid);

	List<String> getAllApplicationNames();
	
	List<UserGroupsModel> getAllUsersUnderGroup(String groupId);

	Response updateOnBoard(String groupId,String applicationName,boolean value);

	List<ApplicationList> getAllOnBoardedApplications(String dsid);

	List<ApplicationList> getApplicationDetails(String applicationName,String dsid);

/*	List<String> getAllApplicationNames1(String step, String group);*/

}
