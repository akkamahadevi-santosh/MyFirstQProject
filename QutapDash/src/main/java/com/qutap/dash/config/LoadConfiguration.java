package com.qutap.dash.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qutap.dash.customException.ExecutionException;
import com.qutap.dash.repository.ExecutionDaoImpl;




public class LoadConfiguration {
	Logger log = LoggerFactory.getLogger(LoadConfiguration.class);
	public Properties getProperties() throws ExecutionException
	{
		Properties prop = new Properties();
		String propFileName = "TestFeed.properties";
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		
		if(inputStream != null)
		{
			try {
				prop.load(inputStream);
			} catch (IOException e) {
				log.error("Initialization ERROR: could not load "+propFileName+"",e);
				throw new ExecutionException("Initialization ERROR: could not load "+propFileName+"");
			}
		}
		else {
			log.error("Initialization ERROR: no TestFeed configuration was found. Please check if "+propFileName+" file is in class path");
			throw new ExecutionException("Initialization ERROR: no TestFeed configuration was found. Please check if "+propFileName+" file is in class path");
		}
			return prop;
	}
}
