package com.exilant.qutap.agent;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import com.exilant.qutap.agent.error.AgentInitException;
import com.exilant.qutap.agent.server.AbstractAgent;


/**
 * The Class Agent executes the start up setps needed to setup an agent in the remote host.
 */
public class Agent {

	/** The props. */
	public static Properties PROPS = new Properties();
	
	/** The Constant AGENT_CONFIG. */
	public final static String AGENT_CONFIG = "agent.config";
	
	/** The Constant HTTP_PORT. */
	public final static String HTTP_PORT = "agentHttpPort";
	
	/** The Constant SERVLET_LIST. */
	public final static String SERVLET_LIST = "servletList";
	
	/** The Constant REGISTRATION_PATH. */
	public final static String REGISTRATION_PATH = "/itapServer/engine/agent/register";

	/**
	 * Load agent configurations.
	 *
	 * @param configName the config name
	 * @throws AgentInitException the agent init exception
	 */
	public static void loadAgentConfigurations(String configName) throws AgentInitException {
		if(PROPS==null){
			PROPS = new Properties();
		}
		 
		loadConfigurations(configName);
		 
	}

	/**
	 * Load configurations.
	 *
	 * @param configName the config name
	 * @return the properties
	 * @throws AgentInitException the agent init exception
	 */
	public static Properties loadConfigurations(String configName) throws AgentInitException {
		
		InputStream inStream = null;
		try {
			inStream = AbstractAgent.class.getClassLoader().getResourceAsStream(configName);
			if (inStream == null) {
				throw new AgentInitException(
						"Initialization ERROR: no agent configuration was found. Please check if agent.config file is in class path",
						null);
			}
			try {
				PROPS.load(inStream);
			} catch (IOException e) {
				throw new AgentInitException("Initialization ERROR: could not load agent.config", e);
			}
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
				}
			}
		}
		return PROPS;
	}

	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @return the string
	 * @throws AgentInitException the agent init exception
	 */
	public static String get(String key) throws AgentInitException {
		if (!PROPS.containsKey(key)) {
			throw new AgentInitException(key + " ::  is not configured", null);
		}
		return PROPS.getProperty(key);
	}

	/**
	 * Gets the list.
	 *
	 * @param key the key
	 * @return the list
	 * @throws AgentInitException the agent init exception
	 */
	public static List<String> getList(String key) throws AgentInitException {
		String value = get(key);
		List<String> list = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(value, ",");
		while (tokenizer.hasMoreTokens()) {
			String tokenValue = tokenizer.nextToken();
			list.add(tokenValue);
		}
		return list;
	}

	/**
	 * Gets the integer.
	 *
	 * @param key the key
	 * @return the integer
	 * @throws AgentInitException the agent init exception
	 */
	public static Integer getInteger(String key) throws AgentInitException {
		Integer intValue = 0;
		String value = get(key);
		try {
			intValue = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new AgentInitException(key + " ::  is not a valid integer", e);
		}
		return intValue;
	}
}
