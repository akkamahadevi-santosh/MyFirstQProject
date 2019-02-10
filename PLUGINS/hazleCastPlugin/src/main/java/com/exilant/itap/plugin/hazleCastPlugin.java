package com.exilant.itap.plugin;

import org.json.JSONObject;
import com.hazelcast.core.*;
import com.hazelcast.config.*;

import java.util.Map;
import java.util.Queue;
import com.exilant.itap.agent.Task;

//Interface Task Extended from Agent
public class hazleCastPlugin implements Task {

	@Override
	public JSONObject processRequest(JSONObject requestJSON) {
		Config cfg = new Config();
		HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
		Map<String, String> mapAgents = instance.getMap("agents");
		mapAgents.put("AgentID", "1");
		return null;
	}

}
