package com.qutap.dash.service;

import java.util.List;

import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.domain.UserDomain;
import com.qutap.dash.model.TestSuiteModel;
import com.qutap.dash.model.UserModel;

   
public interface TestSuiteService {

	//public Response saveTestSuite(TestSuiteModel testSuiteModel);
	public TestSuiteModel saveTestSuite(TestSuiteModel testSuiteModel);

	public TestSuiteModel getTestSuiteInfoById(String testSuiteId);

	public TestSuiteModel getTestSuiteByName(String projectId, String testSuiteName);

	public List<TestSuiteModel> getListOfTestSuitesByProjectId(String projectId);


}
