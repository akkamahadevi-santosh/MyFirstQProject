package com.qutap.dash.repository;



import java.util.List;
import java.util.stream.Collectors;

import org.apache.xmlbeans.impl.xb.ltgfmt.TestCase;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.commonUtils.StatusCode;
import com.qutap.dash.commonUtils.Utils;
import com.qutap.dash.customException.RequirementException;
import com.qutap.dash.customException.TestCaseException;
import com.qutap.dash.customException.TestScenarioException;
import com.qutap.dash.domain.ModuleDomain;
import com.qutap.dash.domain.ProjectInfoDomain;
import com.qutap.dash.domain.RequirementDomain;
import com.qutap.dash.domain.TestCaseDomain;
import com.qutap.dash.domain.TestScenarioDomain;
import com.qutap.dash.domain.TestStepDomain;

 
@Repository
@Transactional
public class ExcelDataDaoImpl implements ExcelDataDao{
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	TestScenarioDao testScenarioDao;
	
	org.slf4j.Logger log= LoggerFactory.getLogger(ExcelDataDaoImpl.class);

	public Response saveExcelData(ProjectInfoDomain projectInfoDomain) {
		Response response = Utils.getResponseObject("Adding project Details");
		try {
			mongoTemplate.insert(projectInfoDomain, "projectInfo");
			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(projectInfoDomain);
			return response;
			} catch (Exception e) {
			log.error("error in saving  testcase and testStep detail", e);
			throw new TestCaseException("error in saving testcase and testStep detail");
		}

	}

	
}

