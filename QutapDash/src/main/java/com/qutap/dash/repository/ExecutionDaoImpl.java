package com.qutap.dash.repository;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.commonUtils.StatusCode;
import com.qutap.dash.commonUtils.Utils;
import com.qutap.dash.customException.ExecutionException;
import com.qutap.dash.customException.ProjectInfoException;
import com.qutap.dash.domain.TestResultResponseDomain;

 
@Repository
@Transactional
public class ExecutionDaoImpl implements ExecutionDao{
	
	Logger log = LoggerFactory.getLogger(ExecutionDaoImpl.class);
	
	@Autowired
	MongoTemplate mongoTemplate;

	public Response executeData(TestResultResponseDomain testResultResponseDomain) {
		Response response = Utils.getResponseObject("Adding test executed Result Details");
		try {
			mongoTemplate.insert(testResultResponseDomain, "testExecution");
			response.setStatus(StatusCode.SUCCESS.name());
			response.setData(testResultResponseDomain);
			return response;
		} catch (Exception e) {
			log.error("error in saving test executed Result detail", e);
			throw new ExecutionException("error in saving test executed Result detail");
		}

	}

	
	

}
