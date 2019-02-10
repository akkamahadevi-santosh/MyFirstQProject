package com.exilant.qutap.agent.responseBaen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class SuiteResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<JSONObject>  listOfResponseJason;

	public List<JSONObject> getListOfResponseJason() {
		return listOfResponseJason;
	}

	public void setListOfResponseJason(List<JSONObject> listOfResponseJason) {
		this.listOfResponseJason = listOfResponseJason;
	}
	
	
	

}
