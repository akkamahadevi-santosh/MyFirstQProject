package com.exilant.qutap.controller;

import java.io.IOException;

import com.exilant.qutap.engine.exception.ITAPEngineException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AbstractController {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	Object getMappedObject(String request,Class objectClass){
		ObjectMapper mapper = new ObjectMapper();
		Object mappedObject = null;
		try {
			mappedObject = mapper.readValue(request, objectClass);
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new ITAPEngineException("JsonParseException "+request+":"+objectClass);
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new ITAPEngineException("JsonMappingException "+request+":"+objectClass);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new ITAPEngineException("IOException "+request+":"+objectClass);
		}
		return mappedObject;
	}

}
