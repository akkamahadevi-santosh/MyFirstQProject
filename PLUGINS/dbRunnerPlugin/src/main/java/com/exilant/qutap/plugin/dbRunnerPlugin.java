package com.exilant.qutap.plugin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

import com.exilant.qutap.agent.Task;

public class dbRunnerPlugin implements Task {

	DBAction con = null;

	List<JSONObject> paramList = new ArrayList<JSONObject>();
	JSONObject mainJSON = new JSONObject();
	Map<String, String> loadValue = new HashMap<String, String>();
	Map<String,String> selectedValue=new HashMap<String,String>();	
	
	@Override
	public JSONObject processRequest(JSONObject requestJSON) {

		mainJSON = getJSON(requestJSON);
		return mainJSON;

	}

	public JSONObject getJSON(JSONObject payLoad) {

		JSONArray testSteps = payLoad.getJSONArray("testSteps");

		for (int i = 0; i < testSteps.length(); i++) {
			String error = "";
			String result = "FAIL";
			JSONObject mainObject = testSteps.getJSONObject(i);
			JSONObject respJSON = new JSONObject();

			if (mainObject.getString("action").equalsIgnoreCase("mysqlconnection")) {
				JSONArray values = mainObject.getJSONArray("values");
				String url = values.getString(0);
				String username = values.getString(1);
				String password = values.getString(2);
				con = new DBConnection();
				String resp=null;
				try {
					 resp=con.getConnection(url, username, password, mainObject.getString("action"));
					result = "PASS";
				} catch (Exception e) {
					error = e.getMessage();
					result = "FAIL";

				} finally {
					String testStepid1 = mainObject.get("testStepId").toString();
					respJSON.put("testStepId", testStepid1);
					if (result.equalsIgnoreCase("pass")) {
						respJSON.put("Response", resp);
						respJSON.put("status", result);
						respJSON.put("error", "");

					} else {
						respJSON.put("Response", resp);
						respJSON.put("status", result);
						respJSON.put("error", error);
					}
				}
			} else if (mainObject.getString("action").equalsIgnoreCase("loadtable")) {
				try {

					JSONArray values = mainObject.getJSONArray("values");

					String query = values.getString(0);
					loadValue=con.execute(query,"loadTable");
					result = "PASS";
					error = "";
				} catch (Exception e) {
					result = "FAIL";
					error = e.getMessage();
				} finally {
					String testStepid1 = mainObject.get("testStepId").toString();
					respJSON.put("testStepId", testStepid1);
					if (result.equalsIgnoreCase("pass")) {

						respJSON.put("Response", loadValue);
						respJSON.put("status", result);
						respJSON.put("error", "");

					} else {
						respJSON.put("Response", loadValue);
						respJSON.put("status", result);
						respJSON.put("error", error);
					}
				}
			} else if (mainObject.getString("action").equalsIgnoreCase("execute")) {
				try {

					JSONArray values = mainObject.getJSONArray("values");

					String query = values.getString(0);
					Set<String> st = loadValue.keySet();
					Iterator<String> it = st.iterator();
					while (it.hasNext()) {
						String key = it.next();
						if (query.contains("<@@@" + key + "@@@>")) {
							query = query.replaceAll("<@@@" + key + "@@@>", "'" + loadValue.get(key) + "'".toString());
						}
					}
					selectedValue=con.execute(query,"select");
					result = "PASS";
					error = "";
				} catch (Exception e) {
					result = "FAIL";
					error = e.getMessage();
				} finally {
					String testStepid1 = mainObject.get("testStepId").toString();
					respJSON.put("testStepId", testStepid1);
					if (result.equalsIgnoreCase("pass")) {
						respJSON.put("Response", selectedValue);
						respJSON.put("status", result);
						respJSON.put("error", "");

					} else {
						respJSON.put("Response", selectedValue);
						respJSON.put("status", result);
						respJSON.put("error", error);
					}
				}
				

			}
			 else if (mainObject.getString("action").equalsIgnoreCase("executeUpdate")) {
					
					try {
						JSONArray values = mainObject.getJSONArray("values");

						String query = values.getString(0);
						Set<String> st = loadValue.keySet();
						Iterator<String> it = st.iterator();
						while (it.hasNext()) {
							String key = it.next();
							if (query.contains("<@@@" + key + "@@@>")) {
								query = query.replaceAll("<@@@" + key + "@@@>", "'" + loadValue.get(key) + "'".toString());
							}					
					}
						con.executeUpdate(query);
						result = "PASS";
						error = "";
						} catch (Exception e) {
						result = "FAIL";
						error = e.getMessage();
					} finally {
						String testStepid1 = mainObject.get("testStepId").toString();
						respJSON.put("testStepId", testStepid1);
						if (result.equalsIgnoreCase("pass")) {
							respJSON.put("Response","Changes done");

							respJSON.put("status", result);
							respJSON.put("error", "");

						} else {
							respJSON.put("Response", "Changes failed");

							respJSON.put("status", result);
							respJSON.put("error", error);
						}
					}								 
				}
			selectedValue.clear();
			paramList.add(respJSON);
		}
		try {
			con.con.close();
			con.stmt.close();
		}catch (SQLException e) {			
		}		
		mainJSON.put("testSteps", paramList);
		return mainJSON;
	}
		
}
