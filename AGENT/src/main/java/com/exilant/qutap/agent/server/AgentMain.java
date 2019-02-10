package com.exilant.qutap.agent.server;

import org.json.JSONObject;

import com.exilant.qutap.agent.Agent;
import com.exilant.qutap.agent.error.AgentInitException;
import com.exilant.qutap.agent.error.TrasportException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class AgentMain extends AbstractAgent {

	public void main() {
		AgentMain runner = new AgentMain();
		try {
			runner.registerAgent();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			runner.agentBootStrap();
		} catch (AgentInitException e) {
			e.printStackTrace();
		}

	}

	public static void registerAgent() throws AgentInitException, UnknownHostException {

		InetAddress addr = InetAddress.getLocalHost();

		Properties PROPS = Agent.loadConfigurations(Agent.AGENT_CONFIG);
		String port = PROPS.getProperty("agentHttpPort");
		String Ip=PROPS.getProperty("registrationURL");
		// register with the server
		String json = "{ \"agentInfo\":{ \"ipAddress\":\"" + addr.getHostAddress() + "\",\"portNumber\":\"" + port+ "\",\"agentName\":\"agent1\",\"agentId\":1}}";
		
		// String json="{ \"agentInfo\":{\"ipAddress\":\"10.2.1.96\",\"portNumber\":\""+port+"\",\"agentName\":\"agent1\",\"agentId\":1}}";

		System.out.println(json);

		JSONObject agentInfo = new JSONObject(json);
		try {
			JSONObject Status = AgentTransport.postRequestExec(Ip,
					agentInfo);
			System.out.println(Status);
			// here we will build the hearbeat logic if the status is true

		} catch (TrasportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.getMessage();
		}

	}
}