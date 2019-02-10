package com.qutap.dash.customException;

public class MemoryUsagesException extends RuntimeException {

	public MemoryUsagesException() {
		super();
	}

	public MemoryUsagesException(String msg) {
		super(msg);
	}
}
