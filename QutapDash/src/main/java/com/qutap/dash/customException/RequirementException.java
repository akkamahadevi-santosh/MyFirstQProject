package com.qutap.dash.customException;

public class RequirementException extends RuntimeException{
	
	public RequirementException() {
		super();
	}


	public RequirementException(String message) {
		super(message);
	}

}