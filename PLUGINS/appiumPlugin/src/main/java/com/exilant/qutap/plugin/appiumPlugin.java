package com.exilant.qutap.plugin;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.exilant.qutap.agent.Task;

//Interface Task Extended from Agent
public class appiumPlugin implements Task {

	AppiumAndroidTesting testObject = new AppiumAndroidTesting();
	List<JSONObject> ParamList = new ArrayList<JSONObject>();
	JSONObject mainJSON = new JSONObject();

	@Override
	public JSONObject processRequest(JSONObject requestJSON) {
		JSONObject resJSON = null;
		try {
			System.out.println("processRequest(JSONObject   APPIUM   requestJSON) 22222called");
			 resJSON = getResponse(requestJSON);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resJSON;

	}

	private JSONObject getResponse(JSONObject requestJSON) throws InterruptedException {
		
		JSONObject resJSONFromAction = new JSONObject();
		List<Object> myParamList = new ArrayList<Object>();
System.out.println("SELENIUM REQUEST JSONHJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ"+requestJSON);
		JSONArray jsonMainArr = requestJSON.getJSONArray("testStepList");
		System.out.println(jsonMainArr.toString());
		
		System.out.println(jsonMainArr.toString());

		for (int i = 0; i < jsonMainArr.length(); i++) {
			
			System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIII  VALUEEEEEEEEEEEEEEEEE"+i);
			JSONObject childJSONObject = jsonMainArr.getJSONObject(i);
			String action = childJSONObject.getString("action");
			String paramGroupObject = childJSONObject.getString("paramGroupObject");
			//String stepParam= childJSONObject.getString("stepParam");
//			System.out.println(stepParam+"StepParam");
			String locator = childJSONObject.getString("paramGroupId");
		//	String comment = childJSONObject.getString("comment");
			JSONArray value = childJSONObject.getJSONArray("testParamData");
			System.out.println("action"+action+"\t"+value);
          if (!(paramGroupObject.equals("NoObject"))) {
				
				myParamList.add(paramGroupObject);
				System.out.println(paramGroupObject);
				
			}
			if (!(locator.equals(""))) {
				myParamList.add(locator);
			}
			if(value.length() != 0)
			{
				for(int k = 0;k<value.length();k++)
				{
					myParamList.add(value.getString(k));
				}
			}
			
			Object[] paramListObject = new String[myParamList.size()];
			paramListObject = myParamList.toArray();
			String res;
			System.out.println("********"+action+"***"+myParamList);
			res = runReflectionMethod("com.exilant.qutap.plugin.AppiumAndroidTesting", action, paramListObject);
			JSONObject resJSON1 = new JSONObject();
			String result = "";
			resJSON1.put("testStepId", childJSONObject.get("testStepsId"));

			if (res.equalsIgnoreCase("pass")) {

				resJSON1.put("status", res);
				resJSON1.put("error", "");
			} else {
				resJSON1.put("screenShot", "");
				resJSON1.put("status", "FAIL");
				resJSON1.put("error", res);
			}

			ParamList.add(resJSON1);

			myParamList.clear();

		}
		mainJSON.put("TestResult", ParamList);
		//mainJSON.put("", ParamList);
		return mainJSON;
	}	
			
//			private String runReflectionMethod(String string, String action, Object[] paramListObject) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	StringWriter errors = new StringWriter();
	private String runReflectionMethod(String strClassName, String strMethodName, Object... inputArgs) {
				String error = "";
				JSONObject respJSON = new JSONObject();
				Class<?> params[] = new Class[inputArgs.length];
				String ob = "";
				String res = "";

				for (int i = 0; i < inputArgs.length; i++) {
					if (inputArgs[i] instanceof String) {
						params[i] = String.class;
					}
				}
				try {
					Class<?> cls = Class.forName(strClassName);
					Object _instance = cls.newInstance();
					Method myMethod = cls.getDeclaredMethod(strMethodName, params);
					ob = (String) myMethod.invoke(_instance, inputArgs);
					//System.out.println("runReflectionMethod method=" + ob);
					res = ob;
					//exception = ob;
					if (res == null || res.isEmpty()) {
						res = "FAIL";
					}

				} catch (ClassNotFoundException e) {
					System.err.format(strClassName + ":- Class not found%n");
					e.printStackTrace(new PrintWriter(errors));
					error = errors.toString();
				} catch (IllegalArgumentException e) {
					System.err.format("Method invoked with wrong number of arguments%n");
					e.printStackTrace(new PrintWriter(errors));
					error = errors.toString();
				} catch (NoSuchMethodException e) {

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
				} catch (Exception e) {
					e.printStackTrace(new PrintWriter(errors));
					error = errors.toString();
				}

//				if (ob.length() > 10)
//
//					return exception;
				if (res.equalsIgnoreCase("pass"))
					return res;
				//if (res.equalsIgnoreCase("fail"))
					//return res;

				else
					return error;
				//return res;

			}

}


