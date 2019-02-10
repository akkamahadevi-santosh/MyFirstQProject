package com.exilant.qutap.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exilant.qutap.engine.AgentUtil;
import com.exilant.qutap.engine.ITestExecutionEngine;
import com.exilant.qutap.engine.agent.AbstractAgentPool;
import com.exilant.qutap.engine.data.IAgentInfo;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@RestController
public class AgentController extends AbstractController {

	@Autowired
	ITestExecutionEngine testExecutionEngine;

	@Autowired
	IAgentRegistrationResponse response;

	@Autowired
	IAgentInfo agentInfo;

     @Autowired
	AbstractAgentPool abstractAgentPool;
     static Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
     static 
     {
    	 System.out.println("starting  hazelcast  from server AggentControlleer...............................................");
    	 map = hazelCastMethod();
    	 
     }
	
     public static  Map<String, Map<String, String>> hazelCastMethod() {
    	  
    	 LoadConfiguration loadConfig = new LoadConfiguration();
 		Properties PROPS = loadConfig.getProperties();  
 		String memberIP=PROPS.getProperty("memberIP");
 		
		 Config config = new Config();

	        config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
	        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
	      //  config.getNetworkConfig().getJoin().getTcpIpConfig().addMember("10.1.2.109:5701");
	        config.getNetworkConfig().getJoin().getTcpIpConfig().addMember(memberIP);

	        HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance(config);
	        System.out.println("hezelcast calling in client:::::::::"+hzInstance.hashCode());
		// Create all the maps that you was all client to access
	        
	        
	        Map<String, Map<String, String>> map1 = hzInstance.getMap("map");

		// puting a dummy value to read from the client

		//map1.put("dummyData", "this is a dummy entry. You will see this message when you invoke the client");
		//map1.put("agentList", );

		for (String entry : map1.keySet()) {
			System.out.println(entry + " " + map1.get(entry));
		}
		return map1;

	}

	@RequestMapping(path = "/engine/agent/register", method = RequestMethod.POST, consumes = "application/json")
	public IAgentRegistrationResponse registerAgent(@RequestBody String request) {
		System.out.println("QutabServer---AgentController-->registerAgent(@RequestBody String request) called");
		AgentRegistrationRequest regRequest = null;
		regRequest = (AgentRegistrationRequest) getMappedObject(request, AgentRegistrationRequest.class);
		Boolean agentRegistrationStatus = false;
		agentRegistrationStatus = testExecutionEngine.registerAgent(regRequest.getAgentInfo());
		response.setRegistrationStatus(agentRegistrationStatus);

//		// Getting hazal cast map ....need to move code
//		AgentUtil a = new AgentUtil();
//		map = a.hazelCastMethod();

		return response;
	}

	@RequestMapping(path = "/engine/agent/ping", method = RequestMethod.POST, consumes = "application/json")
	public void pingAgent(@RequestBody String request) {
		System.out.println("QutabServer---AgentController-->pingAgent(@RequestBody String request) called");
		AgentRegistrationRequest regRequest = (AgentRegistrationRequest) getMappedObject(request,
				AgentRegistrationRequest.class);
		testExecutionEngine.agentHeartBeat(regRequest.getAgentInfo());
	}

	@RequestMapping(path = "/engine/agent/download/plugin", method = RequestMethod.GET, consumes = "application/json", produces = "application/x-octet-stream")
	public @ResponseBody OutputStream getPlugin(@RequestBody String pluginName) {
		System.out.println("QutabServer---AgentController-->getPlugin(@RequestBody String pluginName) called");
		return null;
	}

	@RequestMapping(path = "/engine/agents", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<IAgentInfo> getAgents() {
		System.out.println("QutabServer---AgentController-->getAgents()) called");
		List<IAgentInfo> agents = new ArrayList<IAgentInfo>();
		agents = testExecutionEngine.getAgents();
		return agents;
	}

	// In order to select the Agent based on the Scenario
	@RequestMapping(path = "/engine/borrowAgent", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<IAgentInfo> barrowAgent() {
		System.out.println("QutabServer---AgentController-->barrowAgent() called");
		List<IAgentInfo> agents = new ArrayList<IAgentInfo>();
		agents = testExecutionEngine.getAgents();
		return agents;
	}

//In order to select the Agent based on the Scenario
	@RequestMapping(path="/engine/activeAgent",method=RequestMethod.GET, produces="application/json")
	public @ResponseBody IAgentInfo activeAgent(){	
		System.out.println("QutabServer---AgentController-->activeAgent() called");
		IAgentInfo agent = abstractAgentPool.barrowAgent();
		return agent;
	}
	
	
	@RequestMapping(path = "/engine/testcase/hazData", method = RequestMethod.GET, produces = "application/json")
	public List<Object> getHazelcastData() {
		System.out.println("QutabServer---AgentController-->getHazelcastData() called");
		List<Object> list = new ArrayList<Object>();

		for (String data : map.keySet()) {
			list.add(data);
			list.add(map.get(data));
		}
		return list;
	}

}
