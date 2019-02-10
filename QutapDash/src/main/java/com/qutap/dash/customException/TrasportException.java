package com.qutap.dash.customException;

public class TrasportException extends Exception {
	private static final long serialVersionUID = 1L;
	public TrasportException(String msg,Exception e){
		super(msg,e);

	}

}
