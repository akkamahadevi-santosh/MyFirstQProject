package com.qutap.dash.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.commonUtils.StatusCode;
import com.qutap.dash.commonUtils.Utils;
import com.qutap.dash.customException.OnBoardException;
import com.qutap.dash.domain.ApplicationGroup;
import com.qutap.dash.domain.ApplicationList;
import com.qutap.dash.domain.GroupNameList;
import com.qutap.dash.domain.UserDomain;
import com.qutap.dash.domain.UserGroupsDomain;
import com.qutap.dash.domain.UserRoleDomain;
import com.qutap.dash.model.ProjectInfoModel;
import com.qutap.dash.service.ProjectInfoService;

@Repository
@Transactional
public class ApplicationGroupDaoImpl implements ApplicationGroupDao {

	Logger log = LoggerFactory.getLogger(ApplicationGroupDaoImpl.class);

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	ProjectInfoService projectInfoService;
	
	@Autowired
	ProjectInfoDao projectInfoDao;

	@Override
	public List<ApplicationGroup> getApplicationGroupInfo() {
		try {
			return mongoTemplate.findAll(ApplicationGroup.class, "groupApplication");
		} catch (Exception e) {
			log.error("error in getting list of getApplicationGroupInfo details", e);
			throw new OnBoardException("error in getting list of getApplicationGroupInfo details");
		}
	}

	@Override
	public Response saveUserIdmsDataDomain(UserDomain userDomain) {
		try {
		Response response = Utils.getResponseObject("Adding UserIdmsDataModel Details");
		List<ApplicationGroup> applicationGroupList = getApplicationGroupInfo();
		List<GroupNameList> groupNameList = userDomain.getGroupList();
		List<UserGroupsDomain> userGroupsDomainList = new ArrayList<>();
		UserGroupsDomain userGroupsDomain = new UserGroupsDomain();
		List<String> groupNameValue = new ArrayList<>();
		UserGroupsDomain UserGroupsDomain = getUserGroupsInfo(userDomain.getDsid());
		if (UserGroupsDomain == null) {
			userGroupsDomain.setUserName(userDomain.getUserName());
			userGroupsDomain.setDsid(userDomain.getDsid());
	/*		userGroupsDomain.setGrouplist(new ArrayList<ApplicationGroup>());//--*/
			UserRoleDomain userRole = getUserRoleDetails(userDomain.getDsid());
			if (userRole == null) {
				userGroupsDomain.setRole(" ");
			} else {
				userGroupsDomain.setRole(userRole.getRole());
			}
			List<ApplicationGroup> applicationGroupListVal= new ArrayList<>();
			List<ApplicationList> applicationlist= new ArrayList<>();
			List<String> appo=new ArrayList<>();
			for (GroupNameList groupName : groupNameList) {
				for (ApplicationGroup applicationGroup : applicationGroupList) {
					if (groupName.getGroupName().equalsIgnoreCase(applicationGroup.getGroupName())) {					
						ApplicationGroup applicationGroupVal= new ApplicationGroup();
						String groupNam=applicationGroup.getGroupName();
						String groupId=applicationGroup.getGroupId();
						applicationGroupVal.setGroupId(groupId);
						applicationGroupVal.setGroupName(groupNam);		
						List<ApplicationList> applicationList= applicationGroup.getApplicationList();
						System.out.println("ppppppppppppppp"+applicationGroup.getApplicationList());
						for(ApplicationList appList:applicationList) {
							if(appList.isOnBoard()==true) {
								System.out.println("QQQQQQQQQQQQQQQq"+appList);
							applicationlist.add(appList);
							}
						}
						applicationGroupVal.setApplicationList(applicationlist);
						applicationGroupListVal.add(applicationGroupVal);				
						userGroupsDomain.setGrouplist(applicationGroupListVal);
						userGroupsDomainList.add(userGroupsDomain);
					}				
				}
			}
			mongoTemplate.insert(userGroupsDomainList, "userGroups");
			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(userGroupsDomainList);
		} else {
			Update update = new Update();
			for (ApplicationGroup userGroupsDomainValues : UserGroupsDomain.getGrouplist()) {

				for (GroupNameList groupName : groupNameList) {

					if (groupName.getGroupName().equalsIgnoreCase(userGroupsDomainValues.getGroupName())) {
						response.setMessage("group is already there in the database");
						return response;
					} else {

						for (GroupNameList groupNameValues : groupNameList) {

							for (ApplicationGroup applicationGroup : applicationGroupList) {
								if (groupNameValues.getGroupName().equalsIgnoreCase(applicationGroup.getGroupName())) {
									
										List<ApplicationGroup> applicationGroupListVal= new ArrayList<>();
										List<ApplicationList> applicationlist= new ArrayList<>();
										ApplicationGroup applicationGroupVal= new ApplicationGroup();
										String groupNameData=applicationGroup.getGroupName();
										String groupId=applicationGroup.getGroupId();
										applicationGroupVal.setGroupId(groupId);
										applicationGroupVal.setGroupName(groupNameData);
										List<ApplicationList> applicationList= applicationGroup.getApplicationList();
										
										for(ApplicationList appList:applicationList) {
											if(appList.isOnBoard()==true) {
											applicationlist.add(appList);
											}
										}
										applicationGroupVal.setApplicationList(applicationlist);
										applicationGroupListVal.add(applicationGroupVal);										
										userGroupsDomain.setGrouplist(applicationGroupListVal);		
										
										}
								}
							}
					
						userGroupsDomainList.add(userGroupsDomain);
						for (ApplicationGroup value : userGroupsDomain.getGrouplist()) {
							update.addToSet("grouplist", value);
						}
						Query query = new Query();
						query.addCriteria(Criteria.where("dsid").is(userDomain.getDsid()));
						mongoTemplate.upsert(query, update, "userGroups");
						response.setStatus(StatusCode.SUCCESS.name());
						response.setData(userDomain);

					}
				}

			}

		}
		return response;
		}catch(Exception e) {
			log.error("error in saving User with GroupsInfo details", e);
			throw new OnBoardException("error in saving User with GroupsInfo details");
		}
		
	}

	@Override
	public UserGroupsDomain getUserGroupsInfo(String dsid) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("dsid").is(dsid));
			UserGroupsDomain domain = mongoTemplate.findOne(query, UserGroupsDomain.class, "userGroups");

			return domain;
		} catch (Exception e) {
			log.error("error in getting getUserGroupsInfo details", e);
			throw new OnBoardException("error in getting getUserGroupsInfo details");
		}
	}

	/*
	 * @Override public RoleGroups getRole(String roleId) { Query query = new
	 * Query(); query.addCriteria(Criteria.where("roleId").is(roleId)); return
	 * mongoTemplate.findOne(query, RoleGroups.class,"roleGroups"); }
	 */

	/*
	 * @Override public Response saveRoleUser(String userName, String dsid,String
	 * roleId) { Response response =
	 * Utils.getResponseObject("Adding userRole Details"); try { UserGroupsDomain
	 * domain = getUserGroupsInfo(dsid); System.out.println("ddddd"+domain);
	 * RoleGroups group=getRole(roleId); if(domain!=null ) { UserRole userRole = new
	 * UserRole(); userRole.setDsid(domain.getDsid());
	 * userRole.setUserName(domain.getUserName());
	 * userRole.setRole(group.getRole()); System.out.println(group.getRole());
	 * mongoTemplate.save(userRole, "userRole"); response.setData(userRole);
	 * response.setMessage("userRole Data Added successfully");
	 * response.setStatus("success"); }else { UserRole userRole = new UserRole();
	 * userRole.setDsid(dsid); userRole.setUserName(userName);
	 * userRole.setRole(group.getRole()); System.out.println(group.getRole());
	 * response.setData(userRole);
	 * response.setMessage("userRole Data Added successfully");
	 * response.setStatus("success"); } return response; }catch (Exception e) {
	 * log.error("error in saving userRole Data  details", e); throw new
	 * ProjectInfoException("error in saving userRole Data  details"); } }
	 */

	@Override
	public UserRoleDomain getUserRoleDetails(String dsid) {
		Response response = Utils.getResponseObject("Adding userRole Details");
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("dsid").is(dsid));
			UserRoleDomain role = mongoTemplate.findOne(query, UserRoleDomain.class, "userRole");
			return role;
		} catch (Exception e) {
			log.error("error in getUserRoleDetails  details", e);
			throw new OnBoardException("error in getUserRoleDetails  details");
		}
	}

	@Override
	public ApplicationGroup getGroupNameInfo(String applicationName) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("applicationList.applicationName").is(applicationName));

			ApplicationGroup domain = mongoTemplate.findOne(query, ApplicationGroup.class, "groupApplication");

			return domain;
		} catch (Exception e) {
			log.error("error in getting getGroupNameInfo details", e);
			throw new OnBoardException("error in getting getGroupNameInfo details");
		}
	}

	@Override
	public UserGroupsDomain getApplicationNames(String dsid) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("dsid").is(dsid));
			UserGroupsDomain domain = mongoTemplate.findOne(query, UserGroupsDomain.class, "userGroups");

			return domain;
		} catch (Exception e) {
			log.error("error in getting getApplicationNames details", e);
			throw new OnBoardException("error in getting getApplicationNames details");
		}
	}

	@Override
	public List<ApplicationGroup> getAllApplicationNames() {
		try {
			List<ApplicationGroup> applicationGroup = mongoTemplate.findAll(ApplicationGroup.class, "groupApplication");
			System.out.println(applicationGroup);
			return applicationGroup;
		} catch (Exception e) {
			log.error("error in getting getAllApplicationNames details", e);
			throw new OnBoardException("error in getting getAllApplicationNames details");
		}
	}

	@Override
	public List<UserGroupsDomain> getAllUsersUnderGroup(String groupId) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("grouplist.groupId").is(groupId));	
			List<UserGroupsDomain> domain = mongoTemplate.find(query, UserGroupsDomain.class, "userGroups");

			return domain;
		} catch (Exception e) {
			log.error("error in getting getGroupsInfo details", e);
			throw new OnBoardException("error in getting getGroupsInfo details");
		}
	}
	
	@Override
	public Response updateOnBoard(String groupId,String applicationName, boolean value) {
		Response response = Utils.getResponseObject("All The Application got OnBoarded");
		try {
	//	List<UserGroupsDomain> userGroupsDomianList = getAllUsersUnderGroup(groupId);
		Query query = new Query();
		String appName=null;
		String applicationId=null;
		query.addCriteria(Criteria.where("groupId").is(groupId)).addCriteria(Criteria.where("applicationList.applicationName").is(applicationName));
		ApplicationGroup applicationGroup=	mongoTemplate.findOne(query, ApplicationGroup.class,"groupApplication");	
		for(ApplicationList applicationList:applicationGroup.getApplicationList()) {
			if(applicationList.isOnBoard()!=true) {
				Update update = new Update();				
				update.set("applicationList.$.onBoard",value);
				mongoTemplate.upsert(query, update, "groupApplication");
				Query query1= new Query();
				query1.addCriteria(Criteria.where("groupId").is(groupId));
				List<ApplicationGroup> ApplicationGroupData=mongoTemplate.find(query1, ApplicationGroup.class,"groupApplication");
				for(ApplicationGroup ApplicationGroup:ApplicationGroupData) {
				List<ApplicationList> applicationGroups=ApplicationGroup.getApplicationList().stream().filter(tmp->{			
						if(tmp.isOnBoard()==true)
							return true;
						else
							return false;
					}).collect(Collectors.toList());
				Update update1= new Update();
				Map<String,String> map= new HashMap<>();
				for(ApplicationList values:applicationGroups) {	
					System.out.println("wwwwwwwwwwwwwwwwpppppp"+values.getApplicationId());
					appName=values.getApplicationName();
					applicationId=values.getApplicationId();
					Document document= new Document();
					document.append("applicationId",applicationId);
					document.append("applicationName", appName);
					document.append("onBoard", value);
					Query query2= new Query();
					query2.addCriteria(Criteria.where("grouplist.groupId").is(groupId)).fields().elemMatch("grouplist", Criteria.where("groupId").is(groupId));
					List<UserGroupsDomain> userGroupDomain=mongoTemplate.find(query2, UserGroupsDomain.class,"userGroups");	
					update1.addToSet("grouplist.$.applicationList",document);
					mongoTemplate.updateMulti(query2, update1, UserGroupsDomain.class,"userGroups");
								
					
					//mongoTemplate.updateMulti(q,update1,new UpdateOptions().arrayFilters(Arrays.asList(Filters.in("element.groupId", Arrays.asList(groupId)))),UserGroupsDomain.class);
				}
				
				response.setStatus(StatusCode.SUCCESS.name());
				response.setMessage("sucessfully onboarded");
				}
				
			}/*else {
				response.setStatus("all the application got onBoarded");
				return response;
			}*/
		}
		if(projectInfoDao.getProjectInfoByName(applicationName)==null){
		ProjectInfoModel projectInfoModel=new ProjectInfoModel();
		projectInfoModel.setProjectId(applicationId);
		projectInfoModel.setProjectName(applicationName);
		
		projectInfoService.saveProjectInfo(projectInfoModel);
		
		}
		}catch(Exception e) {
			log.error("error in updating updateOnBoard details", e);
			throw new OnBoardException("error in updating updateOnBoard details");
		}
		return response;
	}

	@Override
	public UserGroupsDomain getAllOnBoardedApplications(String dsid) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("dsid").is(dsid));
			UserGroupsDomain userGroupsDomains = mongoTemplate.findOne(query,UserGroupsDomain.class,"userGroups");
			System.out.println(userGroupsDomains);
			return userGroupsDomains;
		} catch (Exception e) {
			log.error("error in getting getAllApplicationNames details", e);
			throw new OnBoardException("error in getting getAllApplicationNames details");
		}
	}

	@Override
	public UserGroupsDomain getApplicationDetails(String applicationName,String dsid) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("grouplist.applicationList.applicationName").is(applicationName).andOperator(Criteria.where("dsid").is(dsid)));
			UserGroupsDomain domain = mongoTemplate.findOne(query, UserGroupsDomain.class, "userGroups");

			return domain;
		} catch (Exception e) {
			log.error("error in getting getApplicationNames details", e);
			throw new OnBoardException("error in getting getApplicationNames details");
		}
	}
}
