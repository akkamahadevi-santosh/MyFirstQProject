package com.exilant.qutap.agent.error;

public class AgentInitException extends Exception {
	private static final long serialVersionUID = 1L;
	public AgentInitException(String msg,Exception e){
		super(msg,e);

	}

}
