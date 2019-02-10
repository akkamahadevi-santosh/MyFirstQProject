package com.exilant.qutap.plugin;

import org.json.JSONObject;

import com.exilant.qutap.agent.Task;
import com.exilant.qutap.customException.SeleniumActionException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class SeleniumPlugin implements Task {

	@Override
	public JSONObject processRequest(JSONObject requestJSON) {
		System.out.println("processRequest(JSONObject requestJSON) 22222called");

		JSONObject respJSON = getJson(requestJSON);
		System.out.println(respJSON);
		return respJSON;

	}

	String error = "";
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	JSONObject respJSON = new JSONObject();
	JSONObject mainJSON = new JSONObject();

	StringWriter errors = new StringWriter();

	List<JSONObject> ParamList = new ArrayList<JSONObject>();

	public JSONObject getJson(JSONObject requestJSON) {
		JSONObject resJSONFromAction = new JSONObject();
		List<Object> myParamList = new ArrayList<Object>();
System.out.println("SELENIUM REQUEST JSONHJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ"+requestJSON);
		JSONArray jsonMainArr = requestJSON.getJSONArray("testStepList");
		System.out.println(jsonMainArr.toString());
		
		System.out.println(jsonMainArr.toString());

		for (int i = 0; i < jsonMainArr.length(); i++) {
			JSONObject childJSONObject = jsonMainArr.getJSONObject(i);
			String action = childJSONObject.getString("action");
			String paramGroupObject = childJSONObject.getString("paramGroupObject");
			String stepParam= childJSONObject.getString("stepParam");
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
				System.out.println(locator);
			} 
			 /*if (!(stepParam.equals(""))) {
					
					
					int valuintValuee=(int)Double.parseDouble(stepParam.trim());
					
					myParamList.add(new Integer(valuintValuee));
			}*/
			if ((action.equalsIgnoreCase("wait"))) {
				
				
				int valuintValuee=(int)Double.parseDouble(stepParam.trim());
				
				myParamList.add(new Integer(valuintValuee));
		} else if(action.equalsIgnoreCase("checkAttributeValue")){
			myParamList.add(stepParam);
		}
			
			if(value.length()==0) {
				if ((action.equalsIgnoreCase("click"))) {
					myParamList.add("");
				}
			}else {
			for (int j = 0; j < value.length(); j++) {
				/*if ((action.equalsIgnoreCase("wait"))) {
				
					int valuintValuee=(int)Double.parseDouble(value.getString(j));
					myParamList.add(new Integer(valuintValuee));
			    } 
				else */
				System.out.println("KKKKKKKKKKKKKKKKKLLLLLLLLLLLLPPPPPPPPPP");
				if (!(value.getString(j).equals(""))) {
					myParamList.add(value.getString(j));
				}

			}
			}

			System.out.println(action);
			myParamList.stream().forEach(System.out::println);
			Object[] paramListObject = new String[myParamList.size()];
			paramListObject = myParamList.toArray();
			System.out.println("llLLLLLLLLLLLLLLIIIIIIIIIIIIISSSSSSSSSSSSSTTTTTTT"+paramListObject.toString());
			Date startDate = new Date();
			Calendar calendar1 = Calendar.getInstance();
			long timeMilli1 = calendar1.getTimeInMillis();
			long timeMilli2=0;
        
			resJSONFromAction = runReflectionMethod("com.exilant.qutap.plugin.SeleniumPluginHelper", action, paramListObject);
			if(!resJSONFromAction.getString("status").equalsIgnoreCase("fail")) {
				Date endDate = new Date();
				Calendar calendar2 = Calendar.getInstance();
			   timeMilli2 = calendar2.getTimeInMillis();

				System.out.println("statusssssssssssssssssssssssssssssssssssssss"+resJSONFromAction);
		//		resJSONFromAction.put("testStepId", childJSONObject.get("testStepId").toString());
				resJSONFromAction.put("StartDateTime", dateFormat.format(startDate));
				resJSONFromAction.put("EndDateTime", dateFormat.format(endDate));
				resJSONFromAction.put("testStepId",childJSONObject.getString("testStepsId"));
				resJSONFromAction.put("runnerType", requestJSON.getString("runnerType"));
				resJSONFromAction.put("paramGroupObject", paramGroupObject);
				resJSONFromAction.put("action", action);
				resJSONFromAction.put("testParamData", value);
				resJSONFromAction.put("duration", DateUtility.getDuration(String.valueOf(timeMilli1),String.valueOf(timeMilli2)));
			//	resJSONFromAction.put("comment", comment);
				ParamList.add(resJSONFromAction);

				myParamList.clear();
				System.out.println("ParamList........if block "+ParamList);
				mainJSON.put("TestResult", ParamList);
				System.out.println(mainJSON);

				
			}else {
				Date endDate = new Date();
				Calendar calendar2 = Calendar.getInstance();
			   timeMilli2 = calendar2.getTimeInMillis();

				System.out.println("statusssssssssssssssssssssssssssssssssssssss"+resJSONFromAction);
		//		resJSONFromAction.put("testStepId", childJSONObject.get("testStepId").toString());
				resJSONFromAction.put("StartDateTime", dateFormat.format(startDate));
				resJSONFromAction.put("EndDateTime", dateFormat.format(endDate));
				resJSONFromAction.put("testStepId",childJSONObject.getString("testStepsId"));
				resJSONFromAction.put("runnerType", requestJSON.getString("runnerType"));
				resJSONFromAction.put("paramGroupObject", paramGroupObject);
				resJSONFromAction.put("action", action);
				resJSONFromAction.put("testParamData", value);
				resJSONFromAction.put("duration", DateUtility.getDuration(String.valueOf(timeMilli1),String.valueOf(timeMilli2)));
			//	resJSONFromAction.put("comment", comment);
				ParamList.add(resJSONFromAction);

				myParamList.clear();
				mainJSON.put("TestResult", ParamList);
				System.out.println("ParamList........else block "+ParamList);
				System.out.println(mainJSON);
		//		runReflectionMethod("com.exilant.qutap.plugin.SeleniumPluginHelper", "closeBrowser");
				break;
				
				
			}
			

		}
		
		return mainJSON;
		

	}

	public JSONObject runReflectionMethod(String strClassName, String strMethodName, Object... inputArgs) {
		System.out.println("runReflectionMethod(String strClassName:: String strMethodName:::"+strMethodName+" Object... inputArgs::"+inputArgs+"  called");
		Class<?> params[] = new Class[inputArgs.length];
		Object ob = null;
		String res = "";

		for (int i = 0; i < inputArgs.length; i++) {
			if (inputArgs[i] instanceof String) {
				params[i] = String.class;
			}
			if (inputArgs[i] instanceof Integer) {
				params[i] = Integer.class;
			}
		}
		try {
			Class<?> cls = Class.forName(strClassName);
			Object _instance = cls.newInstance();
			System.out.println("in selenium reflection"+strMethodName+"    "+inputArgs);
			Method myMethod = cls.getDeclaredMethod(strMethodName, params);
			ob = myMethod.invoke(_instance, inputArgs);

		} 
		catch (SeleniumActionException e) {
			System.err.format(strClassName + ":- Class not found%n");
		//	e.printStackTrace(new PrintWriter(errors));
			// error = errors.toString();
		    	
			System.out.println("jesion obj in catch after handling");
			error = e.getMessage();
			
			
			System.out.println("jesion obj in catch after#####################****************############################## handling");
			ob =e.getjSONObject();
			return (JSONObject) ob;

		}
		catch (ClassNotFoundException e) {
			System.err.format(strClassName + ":- Class not found%n");
			//e.printStackTrace(new PrintWriter(errors));
			// error = errors.toString();
			
			error = e.getMessage();

		} catch (IllegalArgumentException e) {
			System.err.format("Method invoked with wrong number of arguments%n");
			//e.printStackTrace(new PrintWriter(errors));

			error = e.getMessage();

		} catch (NoSuchMethodException e) {

			//e.printStackTrace(new PrintWriter(errors));
			// error = errors.toString();
			error = e.getMessage();

		} catch (InvocationTargetException e) {
			System.err.format("Exception thrown by an invoked method%n");
			//e.printStackTrace();
			// e.printStackTrace(new PrintWriter(errors));
			// error = errors.toString();
			error = e.getMessage();
//			ob =e.getjSONObject();
//			return (JSONObject) ob;
			System.out.println("InvocationTargetException catch block came..................*************************************************");

		} catch (IllegalAccessException e) {
			System.err.format("Can not access a member of class with modifiers private%n");
			
			
			// error = errors.toString();
			error = e.getMessage();

		} catch (InstantiationException e) {
			System.err.format("Object cannot be instantiated for the specified class using the newInstance method%n");
			
			// error = errors.toString();
			error = e.getMessage();

		} catch (Exception e) {
			
			// error = errors.toString();
			error = e.getMessage();
		}

		if (ob == null) {
		//	StoreScreenShot storeScreenShot= new StoreScreenShot();
			String screnShort=StoreScreenShot.getScreenshot();
			System.out.println("getting screenshor........ob===null............."+screnShort);
			
			respJSON.put("screenShot", screnShort);
			
			respJSON.put("status", "FAIL");
			respJSON.put("response", "");
			respJSON.put("error", error);
			return respJSON;
		} else {
			return (JSONObject) ob;
		}

	}

}