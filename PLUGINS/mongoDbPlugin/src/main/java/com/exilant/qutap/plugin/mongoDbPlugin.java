package com.exilant.qutap.plugin;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.exilant.qutap.agent.Task;
import com.exilant.qutap.plugin.MongoAction;
import com.mongodb.MongoException;

public class mongoDbPlugin implements Task {


	MongoAction action=new MongoAction();
	JSONObject mainJSON = new JSONObject();
	JSONArray values;
	String key,val;
	
	List<JSONObject> paramList = new ArrayList<JSONObject>();
	@Override
	public JSONObject processRequest(JSONObject requestJSON) {

		JSONObject respJSON = null;
		try {
			respJSON = getJSON(requestJSON);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println(respJSON);
		return respJSON;

	}

	 public  JSONObject getJSON(JSONObject payload) throws JSONException {
			
			
		 
			JSONArray testSteps=payload.getJSONArray("testSteps");

			for (int i = 0; i < testSteps.length(); i++) {
			String error = "";
			String result = "";
			JSONObject mainObject = testSteps.getJSONObject(i);
			JSONObject respJSON = new JSONObject();			
			
			if(mainObject.getString("action").equalsIgnoreCase("mongoconnection")){
				String mesg = null;
				try {
					values=mainObject.getJSONArray("values");
					
					
					String host=values.get(0).toString();
			
					int port=Integer.parseInt(values.get(1).toString());
			
					mesg=action.mongoconnection(host,port);
					
					result="PASS";
				} catch (UnknownHostException e) {
					result="FAIL";
					error=e.getMessage();
				}finally {
					String testStepid1 = mainObject.get("testStepId").toString();
					respJSON.put("testStepId", testStepid1);
					if (result.equalsIgnoreCase("pass")) {
						respJSON.put("response", mesg);
						respJSON.put("status", result);
						respJSON.put("error", "");

					} else {
						respJSON.put("response", mesg);
						respJSON.put("status", result);
						respJSON.put("error", error);
					}
				}
			}
			if(mainObject.getString("action").equalsIgnoreCase("getDB")){
				String mesg=null;
				try {
					values=mainObject.getJSONArray("values");
					
					String dbName=values.getString(0);
					
					mesg=action.getDB(dbName);
					result="PASS";
				} catch (Exception e) {
					result="FAIL";
					error=e.getMessage();
				}finally {
					String testStepid1 = mainObject.get("testStepId").toString();
					respJSON.put("testStepId", testStepid1);
					if (result.equalsIgnoreCase("pass")) {
						respJSON.put("response", mesg);
						respJSON.put("status", result);
						respJSON.put("error", "");

					} else {
						respJSON.put("response", mesg);
						respJSON.put("status", result);
						respJSON.put("error", error);
					}
				}
			}
			if(mainObject.getString("action").equalsIgnoreCase("getCollection")){
				String mesg=null;
				try {
					
					values=mainObject.getJSONArray("values");
					String collection=values.getString(0);
					
					mesg=action.getCollection(collection);
					
					result="PASS";
				} catch (Exception e) {
					result="FAIL";
					error=e.getMessage();
				}finally {
					String testStepid1 = mainObject.get("testStepId").toString();
					respJSON.put("testStepId", testStepid1);
					if (result.equalsIgnoreCase("pass")) {
						respJSON.put("response", mesg);
						respJSON.put("status", result);
						respJSON.put("error", "");

					} else {
						respJSON.put("response", mesg);
						respJSON.put("status", result);
						respJSON.put("error", error);
					}
				}
			}
			if(mainObject.getString("action").equalsIgnoreCase("insert")){
				
				long count=0;
				try {
			         
			          
			       
			        
					values=mainObject.getJSONArray("values");
					key=values.getString(0);
					val=values.getString(1);
					
				   
					count=action.insert(key,val);
					
					
					result="PASS";
					
				} catch (Exception e) {
					result="FAIL";
					error=e.getMessage();
					
				}finally {
					String testStepid1 = mainObject.get("testStepId").toString();
					respJSON.put("testStepId", testStepid1);
					if (result.equalsIgnoreCase("pass")) {
						respJSON.put("response", count+" document inserted");

						respJSON.put("status", result);
						respJSON.put("error", "");

					} else {
						respJSON.put("response", count);

						respJSON.put("status", result);
						respJSON.put("error", error);
					}
				}
			}
			
			if(mainObject.getString("action").equalsIgnoreCase("search")){
				
				List<String> searchedval=new ArrayList<String>();

				try {
					
					
					
					values=mainObject.getJSONArray("values");
					key=values.getString(0);
					val = values.getString(1);
			
					searchedval=action.search(key,val);
				
					result="PASS";
					
					if(searchedval.isEmpty()){
						throw new MongoException("SEARCHED FAILED!!!");
					}
					
				} catch (Exception e) {
					result="FAIL";
					error=e.getMessage();
					
				}finally {
					String testStepid1 = mainObject.get("testStepId").toString();
					respJSON.put("testStepId", testStepid1);
					if (result.equalsIgnoreCase("pass")) {
						respJSON.put("response", searchedval);

						respJSON.put("status", result);
						respJSON.put("error", "");

					} else {	
						respJSON.put("response", searchedval);
						respJSON.put("status", result);
						respJSON.put("error", error);
					}
				}
			}
			if(mainObject.getString("action").equalsIgnoreCase("update")){
				List<String> updatedval=new ArrayList<String>();

				
				try {
					
					
					
					values=mainObject.getJSONArray("values");
					String oldval = values.getString(0);
					String newval = values.getString(1);
			
					updatedval=action.update(oldval,newval);
				
					result="PASS";
					
				} catch (Exception e) {
					result="FAIL";
					error=e.getMessage();
					
				}finally {
					String testStepid1 = mainObject.get("testStepId").toString();
					respJSON.put("testStepId", testStepid1);
					if (result.equalsIgnoreCase("pass")) {
						respJSON.put("response", updatedval);

						respJSON.put("status", result);
						respJSON.put("error", "");

					} else {
						respJSON.put("response", updatedval);

						respJSON.put("status", result);
						respJSON.put("error", error);
					}
				}
			}
			
			if(mainObject.getString("action").equalsIgnoreCase("close")){
				String mesg=null;
				try {					
					mesg=action.close();				
					result="PASS";
				} catch (Exception e) {
					result="FAIL";
					error=e.getMessage();
				}finally {
					String testStepid1 = mainObject.get("testStepId").toString();
					respJSON.put("testStepId", testStepid1);
					if (result.equalsIgnoreCase("pass")) {
						respJSON.put("response", mesg);
						respJSON.put("status", result);
						respJSON.put("error", "");

					} else {
						respJSON.put("response", mesg);
						respJSON.put("status", result);
						respJSON.put("error", error);
					}
				}
			}
			paramList.add(respJSON);
			result = "";
		}
		mainJSON.put("testSteps", paramList);
		return mainJSON;
			

	}
}
