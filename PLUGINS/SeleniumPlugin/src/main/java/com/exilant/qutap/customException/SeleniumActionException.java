package com.exilant.qutap.customException;

import org.json.JSONObject;

public class SeleniumActionException extends RuntimeException {
	JSONObject jSONObject=null;
	public SeleniumActionException() {
		super();
	}

	public SeleniumActionException(String msg ) {
		super(msg);
	}
	public SeleniumActionException(String msg ,JSONObject jSONObject) {
		super(msg);
		this.jSONObject=jSONObject;
	}

	public JSONObject getjSONObject() {
		return jSONObject;
	}

	public void setjSONObject(JSONObject jSONObject) {
		this.jSONObject = jSONObject;
	}


}
