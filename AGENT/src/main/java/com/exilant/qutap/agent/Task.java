package com.exilant.qutap.agent;

import org.json.JSONException;
import org.json.JSONObject;


public interface Task {
	
	public JSONObject processRequest(JSONObject requestJSON) throws Exception; 

}
