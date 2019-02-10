package com.exilant.qutap.engine;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exilant.qutap.engine.agent.IAgentPool;
import com.exilant.qutap.engine.data.IAgentInfo;
import com.exilant.qutap.engine.exception.ITAPEngineException;

/**
 * 
 * @author girish.v
 *
 */
public class TestExecutionEngine implements ITestExecutionEngine {

	//static Map<String,String> agents = new HashMap<String,String>();
	private static final long agentPingInterval = 15000;
	
	@Autowired
	IAgentPool agentPool;
	
	@Override
	public Boolean registerAgent(IAgentInfo agentInformation)
			throws ITAPEngineException {
		// TODO Auto-generated method stub
		System.out.println("TestExecutionEngine--->registerAgent(IAgentInfo agentInformation)");
		Integer agentId = agentInformation.getAgentId();
		if(agentId == null){
			Random rand = new Random();
			agentId = rand.nextInt();
			if(agentId < 0){
				agentId=agentId*-1;
			}
		}
		String agentName = agentInformation.getAgentName();
		if(agentName == null){
			agentName ="null";
		}
		String ipAddress = agentInformation.getIpAddress();
		Integer port = agentInformation.getPortNumber();
		if(ipAddress == null || port == null){
			throw new ITAPEngineException("Incomplete agent registration information");
		}
		agentInformation.activate();
		agentInformation.setLatestPingTime();
		agentInformation.activate();
		if(agentPool.getActiveAgents().contains(agentInformation)){
			//no need to do anything as agent already exists just use this as ping request
			agentPool.activateAgent(agentInformation);
		} else if(agentPool.getPassiveAgents().contains(agentInformation)){
			agentPool.activateAgent(agentInformation);
			
		} else{
			agentPool.addAgent(agentInformation);
		}
		return true;
	}

	

	@Override
	public List<IAgentInfo> getAgents() throws ITAPEngineException {
		// TODO Auto-generated method stub
		return agentPool.getAllAgents();
	}

	/*@Override
	public ResponseEntity<String> uploadPlugin(String pluginName,File pluginArchive) throws ITAPEngineException {
		// TODO Auto-generated method stub
		ResponseEntity<String> fileresponse = AgentUtil.uploadPlugin(pluginName,pluginArchive);
		return fileresponse;
	}*/

	@Override
	public String executeTest(String testCaseRequest)
			throws ITAPEngineException {
		// TODO Auto-generated method stub
		//get agent from pool
		System.out.println("TestExecutionEngine-->executeTest(String testCaseRequest) called");
		IAgentInfo agent = agentPool.barrowAgent();
		return executeTest(testCaseRequest,agent);
	}

	@Override
	public String executeTest(String testCaseRequest,IAgentInfo agent)
			throws ITAPEngineException {
		System.out.println("TestExecutionEngine-->executeTest(String testCaseRequest,IAgentInfo agent) called");
		// TODO Auto-generated method stub
		//get agent from pool
		 
		//use agent to execute testcase
		String testExecutionResponse = AgentUtil.executeTestCase(agent, testCaseRequest);
		//after execution release agent back to pool. This will only decrement agent loading factor
		agentPool.releaseAgent(agent);
		return testExecutionResponse;
	}

	@Override
	public void agentHeartBeat(IAgentInfo agentInformation) {
		// TODO Auto-generated method stub
		agentPool.barrowAgent(agentInformation).setLatestPingTime();
		agentPool.releaseAgent(agentInformation);
		
	}

	@Override
	public void agentPing() {
		// TODO Auto-generated method stub
		try{
			IAgentInfo agent = agentPool.barrowAgent();
			
			Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis()-agentPingInterval);
			if(currentTimeStamp.after(agent.getLatestPingTime())){
				agentPool.passivateAgent(agent);
			}
		} catch(ITAPEngineException exception){
			//ignore
			//exception.printStackTrace();
		}
		
	}
	
	
	
	//munnaf
	
	
	
	@Override
	public String executeTestSuite(String testCaseRequest)throws ITAPEngineException {
		// TODO Auto-generated method stub
		//get agent from pool
		System.out.println("TestExecutionEngine-->executeTestSuite(String testCaseRequest) called");
		IAgentInfo agent = agentPool.barrowAgent();
		return executeTestSuite(testCaseRequest,agent);
	}

	@Override
	public String executeTestSuite(String testSuiteRequest,IAgentInfo agent)throws ITAPEngineException {
		System.out.println("TestExecutionEngine-->executeTestSuite(String testSuiteRequest,IAgentInfo agent) called");
		// TODO Auto-generated method stub
		//get agent from pool
		 
		//use agent to execute testcase
	//--munnaf	String testExecutionResponse = AgentUtil.executeTestCase(agent, testCaseRequest);
		String testExecutionResponse = AgentUtil.executeTestSuite(agent, testSuiteRequest);
		//after execution release agent back to pool. This will only decrement agent loading factor
		System.out.println("TestExecutionEngine-->executeTestSuite(String testSuiteRequest,IAgentInfo agent) return response..."+testExecutionResponse);
		agentPool.releaseAgent(agent);
		return testExecutionResponse;
	}
	
	
	

}
