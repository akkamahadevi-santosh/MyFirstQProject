package com.exilant.qutap.agent.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import com.exilant.qutap.agent.responseBaen.SuiteResponse;
import com.exilant.qutap.agent.server.BaseServiceHandler;

public class TestExecSuite extends BaseServiceHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected final Log LOG = LogFactory.getLog(getClass());

	/** The path. */
	private static String PATH = "/agent/testSuite/execute";
//munnaf	private static TaskExec taskExec= new TaskExec();
	private static TaskExec taskExec=null;
	
	@Override
	public JSONObject processRequest(JSONObject requestJSON) {
		
		LOG.debug("TestExecSuite----processRequest(JSONObject requestJSON)...."+requestJSON);
		
		List<JSONObject>  listOfResponseJason= new ArrayList<JSONObject>();
		
		JSONArray jsonMainArr = requestJSON.getJSONArray("testCaseDomain");
		
		JSONObject prePareJson= new JSONObject();
		prePareJson.put("agentMeta", requestJSON.get("agentMeta"));
		prePareJson.put("plugin", requestJSON.get("plugin"));
		prePareJson.put("projectName", requestJSON.get("projectName"));
		prePareJson.put("projectId", requestJSON.get("projectId"));
	
		for (int i = 0; i < jsonMainArr.length(); i++) {
			
			JSONObject childJSONObject = jsonMainArr.getJSONObject(i);
			prePareJson.put("testCaseDomain", childJSONObject);
			LOG.debug("TestExecSuite----processRequest(JSONObject requestJSON)...."+prePareJson);
			if(taskExec==null)
			{
				taskExec= new TaskExec();
			}
			
			JSONObject jsonResponse=taskExec.processRequest(prePareJson);
			
			LOG.debug("TestExecSuite---Response Comming from ......"+jsonResponse);
			listOfResponseJason.add(jsonResponse);
			
			
		}
		SuiteResponse  suiteResponse=new SuiteResponse();
		suiteResponse.setListOfResponseJason(listOfResponseJason);
	
		JSONObject ruturnjsonResponse= new JSONObject(suiteResponse);
		LOG.debug("TestExecSuite---after executing one testsuite response"+ruturnjsonResponse);
		
		return ruturnjsonResponse;
	}

	@Override
	public String getPath() {
		System.out.println("Agent---TestExecSuite-->getPath() called");
		return PATH;
	}
	

}
