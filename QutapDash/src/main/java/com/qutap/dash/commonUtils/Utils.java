package com.qutap.dash.commonUtils;



import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	static ObjectMapper mapper = new ObjectMapper();
	private static String addStr = "globalPORTALAccessKEY";
	private static final Logger logger = LoggerFactory.getLogger(Utils.class);
	private static HttpSession session = null;

	public static Object getObject(String str) throws IOException {
		try {
			return mapper.readValue(str, Object.class);
		} catch (JsonProcessingException e) {
			logger.error("getJsonResponse:Error in json processing: ", e);
		}
		return "";
	}
	
	public static Object getJson(Object obj) throws IOException {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error("getJsonResponse:Error in json processing: ", e);
		}
		return "";
	}

	public static Response getResponseObject(String message) {
		Response response = new Response();
		response.setStatus(StatusCode.SUCCESS.name());
		response.setMessage(message);
		return response;
	}

	public static ErrorObject getErrorResponse(String title, String errorMessage) {
		ErrorObject errorObject = new ErrorObject();
		errorObject.setTitle(title);
		errorObject.setUrl(errorMessage);
		return errorObject;
	}
	
	public static String  getTime(){
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(cal.getTime());
	}

	

}
