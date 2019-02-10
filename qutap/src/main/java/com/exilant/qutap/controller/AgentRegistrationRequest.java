package com.exilant.qutap.controller;

import java.io.Serializable;

import com.exilant.qutap.engine.data.AgentInfo;
import com.exilant.qutap.engine.data.IAgentInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


public class AgentRegistrationRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2477461623921666893L;

	@JsonDeserialize(as=AgentInfo.class)
	IAgentInfo  agentInfo;

	public IAgentInfo getAgentInfo() {
		return agentInfo;
	}

	public void setAgentInfo(IAgentInfo agentInfo) {
		this.agentInfo = agentInfo;
	}
	
}
