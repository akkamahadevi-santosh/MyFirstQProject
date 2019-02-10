
package com.qutap.dash.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qutap.dash.commonUtils.DateUtility;
import com.qutap.dash.commonUtils.PasswordEncAndDec;
import com.qutap.dash.commonUtils.Response;

import com.qutap.dash.customException.UserException;
import com.qutap.dash.domain.RoleDomain;
import com.qutap.dash.domain.UsersDomain;
import com.qutap.dash.model.RoleModel;
import com.qutap.dash.model.UsersModel;
import com.qutap.dash.repository.UserDao;

@Service
public class UserServiceImpl implements UserService {

	Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserDao userDao;
	@Autowired
	private PasswordEncAndDec passwordDecoder;

	@Override
	public Response saveUser(UsersModel user) {
		try {
			Response response = new Response();
			UsersDomain userDomain = new UsersDomain();
			System.out.println("getName" + user.getUserName() + "      " + "mailId" + user.getUserMailId());
			UsersDomain userDomainName = userDao.getUserByName(user.getUserName());
			UsersDomain userDomainMailId = userDao.getUserByMail(user.getUserMailId());
			System.out.println("name::::" + userDomainName + "::::Mail::::" + userDomainMailId);
			if (userDomainName == null && userDomainMailId == null) {
				user.setUserId(new ObjectId().toString());
				user.setUserPassword(passwordDecoder.passwordEncoder(user.getUserPassword()));
				user.setCreatedDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));
				BeanUtils.copyProperties(user, userDomain);
				response = userDao.saveUser(userDomain);
			} else {
				response.setMessage("userName or userMailId is already exist in the database");
			}
			return response;
		} catch (Exception e) {
			log.error("error in saving user detail", e);
			throw new UserException("error in saving user detail");
		}
	}

	@Override
	public UsersModel userLogin(UsersModel user) {

		try {
			Response response = new Response();
			UsersDomain userDomain = new UsersDomain();

			BeanUtils.copyProperties(user, userDomain);
			UsersDomain userDomainValue = userDao.userLogin(userDomain);
			if (userDomainValue != null) {
				BeanUtils.copyProperties(userDomainValue, user);
			} else {
				user = null;
			}

			return user;
		} catch (Exception e) {
			log.error("error in user login detail", e);
			throw new UserException("error in user login detail");
		}

	}

	@Override
	public UsersModel getUserByName(String userName) {
		try {
			UsersModel userModel = new UsersModel();
			UsersDomain userDomain = userDao.getUserByName(userName);
			BeanUtils.copyProperties(userDomain, userModel);
			return userModel;
		} catch (Exception e) {
			log.error("error in getting user detail when searching by name", e);
			throw new UserException("error in getting user detail when searching by Name");
		}
	}

	@Override
	public UsersModel getUserById(String userId) {
		try {
			UsersModel userModel = new UsersModel();
			UsersDomain userDomain = userDao.getUserById(userId);
			BeanUtils.copyProperties(userDomain, userModel);
			return userModel;
		} catch (Exception e) {
			log.error("error in getting user detail when searching by Id", e);
			throw new UserException("error in getting user detail when searching by Id");

		}
	}

	@Override
	public List<UsersModel> getUserList() {
		try {
			List<UsersModel> userModelList = new ArrayList<UsersModel>();
			List<UsersDomain> userDomainList = userDao.getUserList();
			for (UsersDomain userDomain : userDomainList) {
				UsersModel userModel = new UsersModel();
				BeanUtils.copyProperties(userDomain, userModel);
				userModelList.add(userModel);
			}
			return userModelList;
		} catch (Exception e) {
			log.error("error in getting list of user details", e);
			throw new UserException("error in getting list of user details");
		}
	}

	@Override
	public Response updateUserInfo(UsersModel userModel) {
		try {
			UsersDomain userDomain = new UsersDomain();
			BeanUtils.copyProperties(userModel, userDomain);
			Response response = userDao.updateUserInfo(userDomain);
			return response;
		} catch (Exception e) {
			log.error("error in updating user details", e);
			throw new UserException("error in updating user details");
		}
	}

	@Override
	public Response deleteUserInfo(String userId) {
		try {

			Response response = userDao.deleteUserInfo(userId);
			return response;
		} catch (Exception e) {
			log.error("error in deleting user details", e);
			throw new UserException("error in deleting user details");
		}
	}

	@Override
	public Response getRoleNameList() {
		try {
			Response response = new Response();
			List<RoleDomain> roleDomainlist = userDao.getRoleNameList();
			
			response.setData(roleDomainlist);
			return response;
		} catch (Exception e) {
			log.error("error in geeting getRoleNameList details", e);
			throw new UserException("error in geeting getRoleNameList details");
		}
	}

	@Override
	public Response saveRoles(List<RoleModel> roleModelList) {
		try {
			Response response = new Response();
			RoleDomain roleDomain=null;
			List<RoleDomain> list=new ArrayList<RoleDomain>();
			for (RoleModel roleModel : roleModelList) {
				roleDomain= new RoleDomain();
				roleModel.setRoleId(new ObjectId().toString());
				BeanUtils.copyProperties(roleModel, roleDomain);
				list.add(roleDomain);
			}
			
			
			response= userDao.saveRole(list);
			System.out.println("service::::"+response.getData());
			return response;
		} catch (Exception e) {
			log.error("error in saving user detail", e);
			throw new UserException("error in saving user detail");
		}
	}
	
	@Override
	public UsersModel getUserByMail(String mailId) {
		try {
			UsersModel userModel = new UsersModel();
			UsersDomain userDomain = userDao.getUserByMail(mailId);
			if(userDomain!=null) {
				userDomain.setUserPassword(passwordDecoder.passwordDecoder(userDomain.getUserPassword()));
				System.out.println("doa::::"+userDomain.getUserPassword());
			BeanUtils.copyProperties(userDomain, userModel);
			}
			System.out.println("service:::;model"+userModel);
			return userModel;
		} catch (Exception e) {
			log.error("error in getting user detail when searching by MailId", e);
			throw new UserException("error in getting user detail when searching by MailId");

		}
	}
	@Override
	public UsersModel resetUserPassword(String mailId, String password) {
		try {
			UsersModel userModel = new UsersModel();
			String pwd=null;
			if(password!=null) {
			pwd=	passwordDecoder.passwordEncoder(password);
			}
			UsersDomain userDomain = userDao.resetUserPassword(mailId,pwd);
			if(userDomain!=null) {
			BeanUtils.copyProperties(userDomain, userModel);
			}
			return userModel;
		} catch (Exception e) {
			log.error("error in getting user detail when searching by MailId", e);
			throw new UserException("error in getting user detail when searching by MailId");

		}
	}
}