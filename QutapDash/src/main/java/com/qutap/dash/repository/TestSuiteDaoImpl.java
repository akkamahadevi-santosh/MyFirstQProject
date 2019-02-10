   
package com.qutap.dash.repository;

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
import com.qutap.dash.customException.TestSuiteException;
import com.qutap.dash.customException.UserException;
import com.qutap.dash.customException.UserException;
import com.qutap.dash.domain.ProjectInfoDomain;
import com.qutap.dash.domain.TestSuiteDomain;
import com.qutap.dash.domain.UserDomain;
import com.qutap.dash.model.TestSuiteModel;
import com.qutap.dash.domain.UserDomain;

@Repository
@Transactional
public class TestSuiteDaoImpl implements TestSuiteDao {

	Logger log = LoggerFactory.getLogger(TestSuiteDaoImpl.class);
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	

	//public Response saveTestSuite(TestSuiteDomain testSuiteDomain){
	@Override
	//public Response saveTestSuite(TestSuiteDomain testSuiteDomain){
	public TestSuiteDomain saveTestSuite(TestSuiteDomain testSuiteDomain){
		Response response = Utils.getResponseObject("Adding TestSuit Details");
		try {
			mongoTemplate.insert(testSuiteDomain, "testSuiteData");
//			response.setStatus(StatusCode.SUCCESS.name());
//			response.setData(testSuiteDomain);
//			//return response;
			
			
			
			return testSuiteDomain;
		} catch (Exception e) {
			log.error("error in saving TestSuite detail", e);
			throw new TestSuiteException("error in saving TestSuite detail");
		}

	}
	@Override

	public TestSuiteDomain getTestSuiteByName(String projectId, String testSuiteName) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("projectId").is(projectId));
			query.addCriteria(Criteria.where("testSuiteName").is(testSuiteName));
			return mongoTemplate.findOne(query,TestSuiteDomain.class, "testSuiteData");
		} catch (Exception e) {
			log.error("error in getting TestSuite detail  when searching by Name", e);
			throw new TestSuiteException("error in getting TestSuite detail when searching by Name");
		}
	}

	
	
	@Override
	public TestSuiteDomain getTestSuiteInfoById(String testSuiteId){
		
		try {		
			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(testSuiteId));		
			TestSuiteDomain testSuiteDomain = mongoTemplate.findOne(query, TestSuiteDomain.class, "testSuiteData");
			return testSuiteDomain;			
		} catch (Exception e) {
			log.error("error in get TestSuite detail", e);
			throw new TestSuiteException("error in get TestSuite detail");
		}

	}

	@Override
	public  List<TestSuiteDomain> getListOfTestSuitesByProjectId(String projectId) {
		
		try {
			System.out.println("project Id..."+projectId);
			Query query = new Query();
			query.addCriteria(Criteria.where("projectId").is(projectId));
			List<TestSuiteDomain> testSuiteDomainList= mongoTemplate.find(query,TestSuiteDomain.class, "testSuiteData");
		
			
			System.out.println("testSuiteDomainList ......"+testSuiteDomainList);
			return testSuiteDomainList;
		} catch (Exception e) {
			log.error("error in getting list of TestSuite  details", e);
			throw new TestSuiteException("error in getting list of TestSuite  details");
		}
	}
	
	
}
