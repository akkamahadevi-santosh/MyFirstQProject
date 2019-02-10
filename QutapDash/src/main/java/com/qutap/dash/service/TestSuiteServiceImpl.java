    

package com.qutap.dash.service;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bson.UuidRepresentation;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qutap.dash.commonUtils.DateUtility;
import com.qutap.dash.commonUtils.Response;

import com.qutap.dash.customException.TestSuiteException;
import com.qutap.dash.customException.UserException;
import com.qutap.dash.customException.UserException;
import com.qutap.dash.domain.ProjectInfoDomain;
import com.qutap.dash.domain.TestSuiteDomain;
import com.qutap.dash.domain.UserDomain;
import com.qutap.dash.domain.UserDomain;
import com.qutap.dash.model.TestSuiteModel;
import com.qutap.dash.model.UserModel;
import com.qutap.dash.model.UserModel;
import com.qutap.dash.repository.TestSuiteDao;
import com.qutap.dash.repository.UserDao;
import com.qutap.dash.repository.UserDao;

@Service
public class TestSuiteServiceImpl implements TestSuiteService {

	Logger log = LoggerFactory.getLogger(TestSuiteServiceImpl.class);


	
	@Autowired
	TestSuiteDao testSuiteDao;
	

	@Override
	// public Response saveTestSuite(TestSuiteModel testSuiteModel){
	 public TestSuiteModel saveTestSuite(TestSuiteModel testSuiteModel){
		try {
			System.out.println("TestSuiteServiceImpl--saveTestSuite(TestSuiteModel testSuiteModel)");
			TestSuiteDomain testSuiteDomain = new TestSuiteDomain();

			testSuiteModel.setTestSuiteId(new ObjectId().toString());
			testSuiteModel.setCreatedDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));
		
			BeanUtils.copyProperties(testSuiteModel, testSuiteDomain);
			
			
			//Response response = testSuiteDao.saveTestSuite(testSuiteDomain);
			
			TestSuiteDomain testSuiteDomain2  = testSuiteDao.saveTestSuite(testSuiteDomain);
			TestSuiteModel testSuiteModel2= new TestSuiteModel();
			BeanUtils.copyProperties(testSuiteDomain2, testSuiteModel2);
			
			    return testSuiteModel2;
	          
		} catch (Exception e) {
			log.error("error in saving TestSuite detail", e);
			throw new TestSuiteException("error in saving TestSuite detail");
		}
	}

	@Override
	public TestSuiteModel getTestSuiteByName(String projectId, String testSuiteName) {
		try {
			TestSuiteModel testSuiteModel = new TestSuiteModel();
			TestSuiteDomain testSuiteDomain = testSuiteDao.getTestSuiteByName(projectId,testSuiteName);
			BeanUtils.copyProperties(testSuiteDomain, testSuiteModel);
			return testSuiteModel;
		} catch (Exception e) {
			log.error("error in getting user detail when searching by name", e);
			throw new UserException("error in getting user detail when searching by Name");
		}
	}

	@Override
 public TestSuiteModel getTestSuiteInfoById(String testSuiteId) {
		try {
			TestSuiteModel testSuiteModel = new TestSuiteModel();
			TestSuiteDomain testSuiteDomain = testSuiteDao.getTestSuiteInfoById(testSuiteId);
			BeanUtils.copyProperties(testSuiteDomain, testSuiteModel);
			return testSuiteModel;
		} catch (Exception e) {
			log.error("error in getting TestSuite detail when searching by Id", e);
			throw new TestSuiteException("Test suite Does not exist ");

		}
	}

	@Override
	public List<TestSuiteModel> getListOfTestSuitesByProjectId(String projectId) {
		try {
			List<TestSuiteModel> testSuiteModelList = new ArrayList<TestSuiteModel>();
			List<TestSuiteDomain> testSuiteDomainList = testSuiteDao.getListOfTestSuitesByProjectId( projectId);
			for (TestSuiteDomain testSuiteDomain : testSuiteDomainList) {
				TestSuiteModel testSuiteModel = new TestSuiteModel();
				BeanUtils.copyProperties(testSuiteDomain, testSuiteModel);
				testSuiteModelList.add(testSuiteModel);
			}
			return 	testSuiteModelList;
		} catch (Exception e) {
			log.error("error in getting list of user details", e);
			throw new UserException("error in getting list of user details");
		}
	}

	

	

}