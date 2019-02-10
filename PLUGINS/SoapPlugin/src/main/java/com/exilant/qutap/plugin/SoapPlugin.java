package com.exilant.qutap.plugin;

import org.json.JSONObject;

import com.exilant.qutap.agent.Task;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import java.io.PrintWriter;
import java.io.StringWriter;

//Interface Task Extended from Agent
public class SoapPlugin implements Task {

	@Override
	public JSONObject processRequest(JSONObject requestJSON) {

		JSONObject respJSON = getJson(requestJSON);
		System.out.println(respJSON);
		return respJSON;

	}

	String result;
	String res;
	String error = "";
	String exception;
	JSONArray respArray = new JSONArray();
	JSONObject respJSON = new JSONObject();
	JSONObject mainrespJSON = new JSONObject();
	StringWriter errors = new StringWriter();
	JSONObject mainJSON = new JSONObject();
	List<JSONObject> ParamList = new ArrayList<JSONObject>();

	public JSONObject runReflectionMethod(String strClassName, String strMethodName, Object... inputArgs) {

		JSONObject respJSON = new JSONObject();
		Class<?> params[] = new Class[inputArgs.length];

		Object ob = null;

		for (int i = 0; i < inputArgs.length; i++) {
			if (inputArgs[i] instanceof String) {
				params[i] = String.class;
			}
		}
		try {
			Class<?> cls = Class.forName(strClassName);
			Object _instance = cls.newInstance();
			Method myMethod = cls.getDeclaredMethod(strMethodName, params);
			// ob = (String) myMethod.invoke(_instance, inputArgs);
			ob = myMethod.invoke(_instance, inputArgs);
			

		} catch (ClassNotFoundException e) {
			System.err.format(strClassName + ":- Class not found%n");
			e.printStackTrace(new PrintWriter(errors));
			error = errors.toString();

		} catch (IllegalArgumentException e) {
			System.err.format("Method invoked with wrong number of arguments%n");
			e.printStackTrace(new PrintWriter(errors));
			error = errors.toString();

		} catch (NoSuchMethodException e) {
			System.err.format("In Class " + strClassName + "::" + strMethodName + ":- method does not exists%n");
			e.printStackTrace(new PrintWriter(errors));
			error = errors.toString();

		} catch (InvocationTargetException e) {
			System.err.format("Exception thrown by an invoked method%n");
			e.printStackTrace();
			e.printStackTrace(new PrintWriter(errors));
			error = errors.toString();

		} catch (IllegalAccessException e) {
			System.err.format("Can not access a member of class with modifiers private%n");
			e.printStackTrace();
			e.printStackTrace(new PrintWriter(errors));
			error = errors.toString();

		} catch (InstantiationException e) {
			System.err.format("Object cannot be instantiated for the specified class using the newInstance method%n");
			e.printStackTrace(new PrintWriter(errors));
			error = errors.toString();
		}

		if (ob == null) {
			respJSON.put("status", "FAIL");
			respJSON.put("response", "");
			respJSON.put("error", error);
			return respJSON;
		} else {
			return (JSONObject) ob;
		}

	}

	public JSONObject getJson(JSONObject requestJSON) {

		JSONObject resJSONFromAction = new JSONObject();
		List<Object> myParamList = new ArrayList<Object>();

		JSONArray jsonMainArr = requestJSON.getJSONArray("testSteps");
		

		for (int i = 0; i < jsonMainArr.length(); i++) {
			JSONObject childJSONObject = jsonMainArr.getJSONObject(i);
			String action = childJSONObject.getString("action");
			JSONArray value = childJSONObject.getJSONArray("values");

			for (int j = 0; j < value.length(); j++) {

				myParamList.add(value.getString(j));

			}

			

			Object[] paramListObject = new String[myParamList.size()];
			paramListObject = myParamList.toArray();

			resJSONFromAction = runReflectionMethod("com.exilant.itap.plugin.SoapActions", action, paramListObject);

			resJSONFromAction.put("testStepId", childJSONObject.get("testStepId").toString());
			ParamList.add(resJSONFromAction);

			myParamList.clear();

		}

		mainJSON.put("testSteps", ParamList);
		
		// videoReord.stopRecording();
		return mainJSON;

	}

}
