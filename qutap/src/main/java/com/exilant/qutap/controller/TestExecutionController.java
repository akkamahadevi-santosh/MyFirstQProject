package com.exilant.qutap.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exilant.qutap.engine.ITestExecutionEngine;
import com.exilant.qutap.engine.agent.AbstractAgentPool;
import com.exilant.qutap.engine.data.AgentInfo;
import com.exilant.qutap.engine.data.IAgentInfo;

@RestController
public class TestExecutionController extends AbstractController {

	@Autowired
	AbstractAgentPool abstractAgentPool;
	
	@Autowired(required=true)
	ITestExecutionEngine testExecutionEngine;

	@RequestMapping(path = "/engine/testcase/execute", method = RequestMethod.POST, consumes = "application/json")
	public String executeTestCase(@RequestBody String request) {
		System.out.println("TestExecutionController-->executeTestCase(@RequestBody String request) called..........."+request);
		String response = testExecutionEngine.executeTest(request);
		return response;
	}

	
	
	@RequestMapping(path = "/engine/testcase/executeAgent", method = RequestMethod.POST, consumes = "application/json")
	public String executeTestCaseWithAgent(@RequestBody String request) throws JSONException {
		// get the agent ip and port from the request
		String response = null;
		AgentInfo info = new AgentInfo();
		JSONObject obj = new JSONObject(request);
		info.setIpAddress((String) obj.get("ipAddress"));
		info.setPortNumber((Integer) obj.get("portNumber"));
		info.setAgentId((Integer) obj.get("agentId"));
		IAgentInfo agent = abstractAgentPool.barrowAgent(info);
		if (agent != null) {
			response = testExecutionEngine.executeTest(request, agent);
		} else {
			// send error
			response = "Agent is not Registered with the Server";
		}

		return response;
	}

	
	
	
	
	
	//munnaf
	
	
	
	@RequestMapping(path = "/engine/testSuite/execute", method = RequestMethod.POST, consumes = "application/json")
	public String executeTestSuite(@RequestBody String request) {
		System.out.println("TestExecutionController-->executeTestSuite(@RequestBody String request)......."+request);
		String response = testExecutionEngine.executeTestSuite(request);
		
		System.out.println("TestExecutionController-->executeTestSuite(@RequestBody String request)...returning response...."+response);
		return response;
	}
	

}
