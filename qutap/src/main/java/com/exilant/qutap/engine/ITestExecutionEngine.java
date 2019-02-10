package com.exilant.qutap.engine;

import java.util.List;

import com.exilant.qutap.engine.data.IAgentInfo;
import com.exilant.qutap.engine.exception.ITAPEngineException;

public interface ITestExecutionEngine {
	
	
	Boolean registerAgent(IAgentInfo agentInformation) throws ITAPEngineException;

	String executeTest(String testCaseRequest) throws ITAPEngineException;
	
	List<IAgentInfo> getAgents() throws ITAPEngineException;	
	
	void agentHeartBeat(IAgentInfo agentInformation);
	
	void agentPing();

	String executeTest(String testCaseRequest, IAgentInfo agent) throws ITAPEngineException;
	
	
	
	//munnaf
	
	String executeTestSuite(String testCaseRequest)throws ITAPEngineException;
	String executeTestSuite(String testCaseRequest,IAgentInfo agent)throws ITAPEngineException ;
	
}
