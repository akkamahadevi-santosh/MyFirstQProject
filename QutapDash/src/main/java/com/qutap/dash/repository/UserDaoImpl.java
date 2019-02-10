
package com.qutap.dash.repository;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.qutap.dash.commonUtils.ErrorObject;
import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.commonUtils.StatusCode;
import com.qutap.dash.commonUtils.Utils;
import com.qutap.dash.customException.ProjectInfoException;
import com.qutap.dash.customException.UserException;
import com.qutap.dash.domain.RoleDomain;
import com.qutap.dash.domain.UserDomain;
import com.qutap.dash.domain.UsersDomain;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

	Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

	@Autowired
	MongoTemplate mongoTemplate;

	// Register Data Save
	@Override
	public Response saveUser(UsersDomain userDomain) {
		Response response = Utils.getResponseObject("Adding user Details");
		try {
			mongoTemplate.insert(userDomain, "userData");
			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(userDomain);
			return response;
		} catch (Exception e) {
			log.error("error in saving user detail", e);
			throw new UserException("error in saving user detail");
		}

	}

	@Override

	public UsersDomain getUserByName(String userName) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("userName").is(userName));
			return mongoTemplate.findOne(query, UsersDomain.class, "userData");
		} catch (Exception e) {
			log.error("error in getting project detail  when searching by Name", e);
			throw new ProjectInfoException("error in getting project detail when searching by Name");
		}
	}

	@Override
	public UsersDomain getUserById(String userId) {

		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("userId").is(userId));
			UsersDomain userDomaindb = mongoTemplate.findOne(query, UsersDomain.class, "userData");
			return userDomaindb;
		} catch (Exception e) {
			log.error("error in get user detail", e);
			throw new UserException("error in get user detail");
		}

	}

	@Override
	public UsersDomain getUserByMail(String mailId) {

		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("userMailId").is(mailId));
			UsersDomain userDomaindb = mongoTemplate.findOne(query, UsersDomain.class, "userData");
			return userDomaindb;
		} catch (Exception e) {
			log.error("error in get user detail", e);
			throw new UserException("error in get user detail");
		}

	}

	// @Override
	// public Response doLogin(UserDomain userDomain){
	// Response response = Utils.getResponseObject("Adding user Details");
	// try {
	//
	// Query query = new Query();
	// query.addCriteria(Criteria.where("userMail").is(userDomain.getUserMail()));
	// UserDomain userDomainDb= mongoTemplate.findOne(query, UserDomain.class,
	// "user");
	// System.out.println("dao username::::::::::"+userDomainDb);
	//
	//
	//
	// response.setStatus(StatusCode.SUCCESS.name());
	// response.setData(userDomain);
	// return response;
	// } catch (Exception e) {
	// log.error("error in saving user detail", e);
	// throw new UserException("error in saving user detail");
	// }
	//
	// }
	//
	@Override
	public UsersDomain userLogin(UsersDomain userDomain) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("userName").is(userDomain.getUserName()));
			System.out.println(userDomain.getUserPassword() + "saasssssssssdao");
			UsersDomain userDomainValue = mongoTemplate.findOne(query, UsersDomain.class, "userData");
			return userDomainValue;
		} catch (Exception e) {
			log.error("error in login", e);
			throw new UserException("error in login");
		}
	}

	@Override
	public List<UsersDomain> getUserList() {

		try {
			return mongoTemplate.findAll(UsersDomain.class, "userData");
		} catch (Exception e) {
			log.error("error in getting list of user details", e);
			throw new UserException("error in getting list of user details");
		}
	}

	@Override
	public Response updateUserInfo(UsersDomain userDomain) {
		Response response = Utils.getResponseObject("updating user Details");
		try {
			Query query = new Query(Criteria.where("userId").is(userDomain.getUserId()));
			Document doc = new Document();
			mongoTemplate.getConverter().write(userDomain, doc);
			Update update = new Update();
			for (String key : doc.keySet()) {
				Object value = doc.get(key);
				if (value != null) {
					update.set(key, value);
				}
			}
			userDomain = mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true),
					UsersDomain.class);// it will return New Updated Data
			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(userDomain);
			return response;
		} catch (Exception e) {
			log.error("error in updating user details", e);
			throw new UserException("error in updating user details");
		}
	}

	@Override
	public Response deleteUserInfo(String userId) {
		Response response = Utils.getResponseObject("deleting user Details");
		ErrorObject err = Utils.getErrorResponse("deleting user", "user id  not found");
		try {
			UsersDomain userDomain = getUserById(userId);
			mongoTemplate.remove(userDomain);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(userDomain);
			return response;
		} catch (Exception e) {
			log.error("error in deleting user details", e);
			throw new ProjectInfoException("error in deleting user details");
		}
	}

	@Override
	public List<RoleDomain> getRoleNameList() {
		try {
			List<RoleDomain> roleDomainList = mongoTemplate.findAll(RoleDomain.class, "dashUserRole");
			return roleDomainList;
		} catch (Exception e) {
			log.error("error in get list of roleName detail", e);
			throw new UserException("error in get list of roleName detail");
		}
	}

	@Override
	public Response saveRole(List<RoleDomain> roleDomainList) {
		Response response = new Response();
		try {
			Query query = null;
			RoleDomain roleDomainDb = null;
			String succName = null;
			String failName = null;
			List<RoleDomain> roleDomainListSucc = new ArrayList<>();
			List<String> listSucc = new ArrayList<String>();
			List<String> listFail = new ArrayList<String>();
			for (RoleDomain roleDomainEach : roleDomainList) {
				query = new Query(Criteria.where("roleName").is(roleDomainEach.getRoleName()));

				roleDomainDb = mongoTemplate.findOne(query, RoleDomain.class, "dashUserRole");

				if (roleDomainDb == null) {
					mongoTemplate.insert(roleDomainEach, "dashUserRole");
					succName = roleDomainEach.getRoleName();
					roleDomainListSucc.add(roleDomainEach);
					listSucc.add(succName);
				} else {
					failName = roleDomainDb.getRoleName();
					// response.setStatus(StatusCode.FAILURE.name());

					// response.setData(null);
					listFail.add(failName);
					response.setErrors(listFail + " are Already Exist ");
					continue;
				}
			}

			// response.setStatus(StatusCode.SUCCESS.name());
			response.setMessage(listSucc + " are inserted successfully");
			response.setData(roleDomainListSucc);
			return response;
		} catch (Exception e) {
			log.error("error in saving user detail", e);
			throw new UserException("error in saving user detail");
		}

	}

	@Override
	public UsersDomain resetUserPassword(String mailId, String pwd) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("userMailId").is(mailId));
			UsersDomain usersDomain = mongoTemplate.findOne(query, UsersDomain.class, "userData");
			if(usersDomain!=null) {
			Update update = new Update();
			Document doc = new Document();
			System.out.println(usersDomain+"ddddd");
			mongoTemplate.getConverter().write(usersDomain, doc);		
			for (String key : doc.keySet()) {
				Object value = doc.get(key);
				if (value != null) {
					update.set("userPassword", pwd);
				}
			}
			usersDomain = mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true),UsersDomain.class);// it will return New Updated Data
			}else {
				
			}
			return usersDomain;
		} catch (Exception e) {
			log.error("error in reseting password", e);
			throw new UserException("error in reseting password");
		}
	}
}
