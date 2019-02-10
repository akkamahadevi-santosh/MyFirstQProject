package com.qutap.dash.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.customException.OnBoardException;
import com.qutap.dash.domain.ApplicationGroup;
import com.qutap.dash.domain.ApplicationList;
import com.qutap.dash.domain.UserDomain;
import com.qutap.dash.domain.UserGroupsDomain;
import com.qutap.dash.model.UserGroupsModel;
import com.qutap.dash.model.UserModel;
import com.qutap.dash.repository.ApplicationGroupDao;

@Service
public class ApplicationGroupServiceImpl implements ApplicationGroupService {

	Logger log = LoggerFactory.getLogger(ApplicationGroupServiceImpl.class);

	@Autowired
	ApplicationGroupDao applicationGroupDao;

	@Override
	public List<ApplicationGroup> getApplicationGroupInfo() {
		try {
			List<ApplicationGroup> applicationGroupModelList = new ArrayList<ApplicationGroup>();
			List<ApplicationGroup> applicationGroupDomainList = applicationGroupDao.getApplicationGroupInfo();
			for (ApplicationGroup applicationGroupDomain : applicationGroupDomainList) {
				ApplicationGroup applicationList = new ApplicationGroup();
				BeanUtils.copyProperties(applicationGroupDomain, applicationList);
				applicationGroupModelList.add(applicationList);
			}
			return applicationGroupModelList;
		} catch (Exception e) {
			log.error("error in getting list of ApplicationGroup details", e);
			throw new OnBoardException("error in getting list of ApplicationGroup details");
		}
	}

	@Override
	public Response saveUserIdmsDataModel(UserModel userModel) {
		try {
			UserDomain userDomain = new UserDomain();
			BeanUtils.copyProperties(userModel, userDomain);
			Response response = applicationGroupDao.saveUserIdmsDataDomain(userDomain);
			return response;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public UserGroupsModel getUserGroupsInfo(String dsid) {
		try {
			UserGroupsModel userIdmsDataModel = new UserGroupsModel();
			UserGroupsDomain userIdmsDataDomain = applicationGroupDao.getUserGroupsInfo(dsid);
			BeanUtils.copyProperties(userIdmsDataDomain, userIdmsDataModel);
			return userIdmsDataModel;
		} catch (Exception e) {
			log.error("error in getting getuserGroupsInfo details", e);
			throw new OnBoardException("error in getting getuserGroupsInfo details");

		}

	}

	@Override
	public ApplicationGroup getGroupNameInfo(String applicationName) {
		try {
			ApplicationGroup userIdmsDataDomain = applicationGroupDao.getGroupNameInfo(applicationName);
			return userIdmsDataDomain;
		} catch (Exception e) {
			log.error("error in getting getGroupNameInfo details", e);
			throw new OnBoardException("error in getting getGroupNameInfo details");

		}
	}

	@Override
	public List<ApplicationList>  getApplicationNames(String dsid) {
		try {
			UserGroupsDomain userIdmsDataDomain = applicationGroupDao.getApplicationNames(dsid);
			List<ApplicationList> allApplicationNames = new ArrayList<ApplicationList>();
			
			List<ApplicationGroup> applicationGroup = userIdmsDataDomain.getGrouplist();
			for (ApplicationGroup group : applicationGroup) {
				List<ApplicationList> application = group.getApplicationList();
				for(ApplicationList list:application) {
					ApplicationList listName= new ApplicationList();
					String applicationId=list.getApplicationId();
					String applicationName=list.getApplicationName();
					listName.setApplicationId(applicationId);
					listName.setApplicationName(applicationName);
					listName.setOnBoard(list.isOnBoard());
					allApplicationNames.add(listName);
				}

			}
			return allApplicationNames;
		} catch (Exception e) {
			log.error("error in getting getApplicationNames details", e);
			throw new OnBoardException("error in getting getApplicationNames details");

		}
	}

	@Override
	public List<String> getAllApplicationNames() {
		try {
			List<ApplicationGroup> allApplicationGroupList = applicationGroupDao.getAllApplicationNames();
			List<String> allApplicationNames = new ArrayList<>();
			for (ApplicationGroup applicationGroup : allApplicationGroupList) {
				List<ApplicationList> applicationList = applicationGroup.getApplicationList();
				for (ApplicationList list : applicationList) {
					if(list.isOnBoard()!=true) {
					String applicationName = list.getApplicationName();
					allApplicationNames.add(applicationName);
					}
				}
			}
			return allApplicationNames;
		} catch (Exception e) {
			log.error("error in getting getAllApplicationNames details", e);
			throw new OnBoardException("error in getting getAllApplicationNames details");

		}
	}

	@Override
	public List<UserGroupsModel> getAllUsersUnderGroup(String groupId) {
		try {

			List<UserGroupsModel> userGroupModelList = new ArrayList<>();
			List<UserGroupsDomain> userGroupsDomainList = applicationGroupDao.getAllUsersUnderGroup(groupId);

			for (UserGroupsDomain domain : userGroupsDomainList) {
				UserGroupsModel userGroupsModel = new UserGroupsModel();
				BeanUtils.copyProperties(domain, userGroupsModel);
				userGroupModelList.add(userGroupsModel);
			}
			return userGroupModelList;

		} catch (Exception e) {
			log.error("error in getting getuserGroupsInfo details", e);
			throw new OnBoardException("error in getting getuserGroupsInfo details");

		}
	}

	@Override
	public Response updateOnBoard(String groupId, String applicationName, boolean value) {
		try {
			Response response = applicationGroupDao.updateOnBoard(groupId, applicationName, value);

			return response;
		} catch (Exception e) {
			log.error("error in getting updateOnBoard details", e);
			throw new OnBoardException("error in getting updateOnBoard details");

		}
	}

	@Override
	public List<ApplicationList> getAllOnBoardedApplications(String dsid) {
		try {
			UserGroupsDomain userGroupsDomain = applicationGroupDao.getAllOnBoardedApplications(dsid);
		List<ApplicationList> applicationGroupList=new ArrayList<>();
		for(ApplicationGroup applicationGroup:userGroupsDomain.getGrouplist()) {
			for(ApplicationList applicationList:applicationGroup.getApplicationList()) {
				ApplicationList appList= new ApplicationList();
				appList.setApplicationId(applicationList.getApplicationId());
				appList.setApplicationName(applicationList.getApplicationName());
				appList.setOnBoard(applicationList.isOnBoard());
				applicationGroupList.add(appList);
			}
		}	
			return applicationGroupList;
		} catch (Exception e) {
			log.error("error in getting getAllOnBoardedApplications details", e);
			throw new OnBoardException("error in getting getAllOnBoardedApplications details");

		}

	}

	@Override
	public List<ApplicationList> getApplicationDetails(String applicationName,String dsid) {
		try {
			UserGroupsDomain userIdmsDataDomain = applicationGroupDao.getApplicationDetails(applicationName,dsid);
			List<ApplicationList> allApplicationNames = new ArrayList<ApplicationList>();
			
			List<ApplicationGroup> applicationGroup = userIdmsDataDomain.getGrouplist();
			for (ApplicationGroup group : applicationGroup) {
				List<ApplicationList> application = group.getApplicationList();
				
				for(ApplicationList list:application) {
					if(list.getApplicationName().equalsIgnoreCase(applicationName)) {
					ApplicationList listName= new ApplicationList();
					String applicationId=list.getApplicationId();
					String applicationNam=list.getApplicationName();
					listName.setApplicationId(applicationId);
					listName.setApplicationName(applicationNam);
					listName.setOnBoard(list.isOnBoard());
					allApplicationNames.add(listName);
					}
				}

			}
			return allApplicationNames;
		} catch (Exception e) {
			log.error("error in getting getApplicationNames details", e);
			throw new OnBoardException("error in getting getApplicationNames details");

		}
	}

/*	@Override
	public List<String> getAllApplicationNames1(String step, String group) {
		try {
			List<ApplicationGroup> allApplicationGroupList = applicationGroupDao.getAllApplicationNames();
			List<String> allApplicationNames = new ArrayList<>();
			for (ApplicationGroup applicationGroup : allApplicationGroupList) {
				List<ApplicationList> applicationList = applicationGroup.getApplicationList();
				for (ApplicationList list : applicationList) {
					if(list.isOnBoard()!=true) {
					String applicationName = list.getApplicationName();
					allApplicationNames.add(applicationName);
					}
				}
			}
			return allApplicationNames;
		} catch (Exception e) {
			log.error("error in getting getAllApplicationNames details", e);
			throw new OnBoardException("error in getting getAllApplicationNames details");

		}
	}*/

	
	
}
