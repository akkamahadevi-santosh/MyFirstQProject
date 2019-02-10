package com.exilant.qutap.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.web.client.RestTemplate;

import com.exilant.qutap.engine.data.IAgentInfo;
import com.exilant.qutap.engine.exception.ITAPEngineException;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class AgentUtil {

	Map<String, Object> map = new HashMap<String, Object>();

	public static String executeTestCase(IAgentInfo agentInfo, String request) {

		
		System.out.println("AgentUtil-->executeTestCase(IAgentInfo agentInfo, String request) called");
		
		RestTemplate restTemplate = new RestTemplate();
		String agentLocationUrl = getAgentLocationUrl(agentInfo.getIpAddress(), agentInfo.getPortNumber());
		String response = null;
		try {
			response = restTemplate.postForObject(agentLocationUrl + "/agent/testcase/execute", request, String.class);
		} catch (Exception ex) {
			throw new ITAPEngineException(ex.getMessage());
		}
		return response;

	}

	private static String getAgentLocationUrl(String ipAddress, Integer portNumber) {
		// TODO Auto-generated method stub
		StringBuilder sbf = new StringBuilder();
		sbf.append("http://").append(ipAddress).append(":").append(portNumber);
		return sbf.toString();

	}

	public Map<String, Object> hazelCastMethod() {
  
		
	
		 Config config = new Config();

	        config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
	        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
	        config.getNetworkConfig().getJoin().getTcpIpConfig().addMember("10.141.202.79:5701");

	        HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance(config);
	        System.out.println("hezelcast calling in client:::::::::"+hzInstance.hashCode());
		// Create all the maps that you was all client to access
	        Map<String, Object> map1 = hzInstance.getMap("map");

		// puting a dummy value to read from the client

		map1.put("dummyData", "this is a dummy entry. You will see this message when you invoke the client");

		for (String entry : map1.keySet()) {
			System.out.println(entry + " " + map1.get(entry));
		}
		return map1;

	}
	
	
	
	
	//munnaf
	

	public static String executeTestSuite(IAgentInfo agentInfo, String request) {

		
		System.out.println("AgentUtil-->executeTestSuite(IAgentInfo agentInfo, String request) called");
		
		RestTemplate restTemplate = new RestTemplate();
		String agentLocationUrl = getAgentLocationUrl(agentInfo.getIpAddress(), agentInfo.getPortNumber());
		String response = null;
		try {
			System.out.println("url: "+agentLocationUrl + "/agent/testSuite/execute");
			response = restTemplate.postForObject(agentLocationUrl + "/agent/testSuite/execute", request, String.class);
			System.out.println("AgentUtil-->executeTestSuite(IAgentInfo agentInfo, String request) response  from agent..."+response);
			
		} catch (Exception ex) {
			throw new ITAPEngineException(ex.getMessage());
		}
		return response;

	}
	
	
	

}
