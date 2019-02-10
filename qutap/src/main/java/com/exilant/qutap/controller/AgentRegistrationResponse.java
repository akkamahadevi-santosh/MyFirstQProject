package com.exilant.qutap.controller;

public class AgentRegistrationResponse implements IAgentRegistrationResponse {

	Boolean registrationStatus;
	
	@Override
	public Boolean getRegistrationStatus() {
		// TODO Auto-generated method stub
		return registrationStatus;
	}

	@Override
	public void setRegistrationStatus(Boolean status) {
		// TODO Auto-generated method stub
		this.registrationStatus = status;
	}

	
	
}
