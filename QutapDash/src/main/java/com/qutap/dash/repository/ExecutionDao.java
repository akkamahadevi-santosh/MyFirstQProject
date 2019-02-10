package com.qutap.dash.repository;
 
import java.util.List;

import org.json.JSONObject;

import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.domain.TestResultResponseDomain;

public interface ExecutionDao {

	Response executeData(TestResultResponseDomain testResultResponseDomain);

}
