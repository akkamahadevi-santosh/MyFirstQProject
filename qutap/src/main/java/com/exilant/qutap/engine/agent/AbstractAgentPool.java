package com.exilant.qutap.engine.agent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.exilant.qutap.engine.data.IAgentInfo;
import com.exilant.qutap.engine.exception.ITAPEngineException;

public abstract class AbstractAgentPool implements IAgentPool {



	static List<IAgentInfo> activeAgents;
	static List<IAgentInfo> passiveAgents;
	
	

	@Override
	public void releaseAgent(IAgentInfo agent) throws ITAPEngineException {
		// TODO Auto-generated method stub
		agent.decrementCurrentLoad();
	}

	@Override
	public void passivateAgent(IAgentInfo agent) throws ITAPEngineException {
		// TODO Auto-generated method stub
		if(activeAgents.isEmpty()){
			throw new ITAPEngineException("Active agents not available");
		}
				List<IAgentInfo> passivatedAgents = activeAgents.stream().filter(
						t->t.getAgentId().equals(agent.getAgentId()) 
							&& t.getAgentName().equals(agent.getAgentName()
									)).collect(Collectors.toList());
				activeAgents.removeAll(passivatedAgents);
				passiveAgents.addAll(passivatedAgents);
	}

	@Override
	public void activateAgent(IAgentInfo agent) throws ITAPEngineException {
		List<IAgentInfo> activatedAgents = passiveAgents.stream().filter(
				t->t.getAgentId().equals(agent.getAgentId()) 
					&& t.getAgentName().equals(agent.getAgentName()
							)).collect(Collectors.toList());
		activeAgents.addAll(activatedAgents);
		passiveAgents.removeAll(activatedAgents);

	}

	@Override
	public void addAgent(IAgentInfo agent) throws ITAPEngineException {
		// TODO Auto-generated method stub
		agent.setLatestPingTime();
		agent.activate();
		activeAgents.add(agent);
	}

	@Override
	public void removeAgent(IAgentInfo agent) throws ITAPEngineException {
		// TODO Auto-generated method stub
		activeAgents.remove(agent);
		passiveAgents.remove(agent);

	}
	
	@Override
	public IAgentInfo barrowAgent(IAgentInfo agent)
			throws ITAPEngineException {
		// TODO Auto-generated method stub
		if(activeAgents.isEmpty()){
			throw new ITAPEngineException("Active agents not available");
		}
		List<IAgentInfo> selectedActiveAgents = activeAgents.stream().filter(
				t->t.getIpAddress().equals(agent.getIpAddress()) 
					&& t.getPortNumber().equals(agent.getPortNumber()
							)).collect(Collectors.toList());
		return selectedActiveAgents.get(0);
		
		
	}
	
	@Override
	public List<IAgentInfo> getAllAgents() throws ITAPEngineException {
		// TODO Auto-generated method stub
		List<IAgentInfo> allAgents = new ArrayList<IAgentInfo>();
		allAgents.addAll(activeAgents);
		allAgents.addAll(passiveAgents);
		return allAgents;
	}

	@Override
	public List<IAgentInfo> getActiveAgents() throws ITAPEngineException {
		// TODO Auto-generated method stub
		return activeAgents;
	}

	@Override
	public List<IAgentInfo> getPassiveAgents() throws ITAPEngineException {
		// TODO Auto-generated method stub
		return passiveAgents;
	}

	

}
