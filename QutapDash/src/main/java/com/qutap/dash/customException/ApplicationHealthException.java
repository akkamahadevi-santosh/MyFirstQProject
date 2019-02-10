package com.qutap.dash.customException;

public class ApplicationHealthException extends RuntimeException{
	
	public ApplicationHealthException() {
		super();
	}


	public ApplicationHealthException(String message) {
		super(message);
	}


}
