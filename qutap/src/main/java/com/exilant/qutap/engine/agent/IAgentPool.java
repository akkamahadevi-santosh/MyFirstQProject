package com.exilant.qutap.engine.agent;

import java.util.List;

import com.exilant.qutap.engine.data.IAgentInfo;
import com.exilant.qutap.engine.exception.ITAPEngineException;

public interface IAgentPool {

	IAgentInfo barrowAgent() throws ITAPEngineException;
	IAgentInfo barrowAgent(IAgentInfo agent) throws ITAPEngineException;
	void releaseAgent(IAgentInfo agent) throws ITAPEngineException;
	void passivateAgent(IAgentInfo agent) throws ITAPEngineException;
	void activateAgent(IAgentInfo agent) throws ITAPEngineException;
	void addAgent(IAgentInfo agent) throws ITAPEngineException;
	void removeAgent(IAgentInfo agent) throws ITAPEngineException;
	
	List<IAgentInfo> getAllAgents() throws ITAPEngineException;
	List<IAgentInfo> getActiveAgents() throws ITAPEngineException;
	List<IAgentInfo> getPassiveAgents() throws ITAPEngineException;
	
}
