package com.exilant.qutap.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.exilant.qutap.engine.exception.ITAPEngineException;

public class LoadConfiguration  {

	public final static String SERVER_CONFIG = "server.config";

	public  Properties getProperties() {
		Properties prop = new Properties();
		// String propFileName = "server.config";
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(SERVER_CONFIG);

		if (inputStream != null) {
			try {
				prop.load(inputStream);
			} catch (IOException e) {
				throw new ITAPEngineException("Initialization ERROR: could not load " + SERVER_CONFIG);

			}
		} else {
			throw new ITAPEngineException("Initialization ERROR: could not load " + SERVER_CONFIG);
		}
		return prop;
	}

}
