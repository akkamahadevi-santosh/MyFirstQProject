package com.qutap.dash.repository;
  
import java.util.List;

import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.domain.ProjectInfoDomain;
import com.qutap.dash.domain.TestSuiteDomain;
import com.qutap.dash.domain.UserDomain;
import com.qutap.dash.model.TestSuiteModel;

public interface TestSuiteDao {
	//public Response saveTestSuite(TestSuiteDomain testSuiteDomain);
	public TestSuiteDomain saveTestSuite(TestSuiteDomain testSuiteDomain);
	public TestSuiteDomain getTestSuiteInfoById(String testSuiteId);
	public TestSuiteDomain getTestSuiteByName(String projectId, String testSuiteName);
	public List<TestSuiteDomain> getListOfTestSuitesByProjectId(String projectId);

}
