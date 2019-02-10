package com.exilant.qutap.agent.service;

import org.json.JSONObject;

import com.exilant.qutap.agent.server.BaseServiceHandler;

public class AgentStatusReport extends BaseServiceHandler {
	private static final long serialVersionUID = 1L;
	private static String PATH = "/itap/engine/agent/register";

	@Override
	public String getPath() {
		return PATH;
	}

	@Override
	public JSONObject processRequest(JSONObject requestJSON) {
		return null;
	}
}
