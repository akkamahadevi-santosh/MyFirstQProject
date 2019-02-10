package com.exilant.qutap.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

public class RestActions {

	String res = "";
	JSONObject respJSON = new JSONObject();
	StringWriter errors = new StringWriter();

	public JSONObject load_URL(String path, String code) {
		int code1 = Integer.parseInt(code);
		HttpUriRequest request = new HttpGet(path);
		HttpResponse httpResponse = null;
		try {
			httpResponse = HttpClientBuilder.create().build().execute(request);

			if (httpResponse.getStatusLine().getStatusCode() == code1) {
				respJSON.put("status", "PASS");
				respJSON.put("response", httpResponse.getStatusLine().getStatusCode());
				respJSON.put("error", "");
			} else {
				respJSON.put("status", "FAIL");
				respJSON.put("response", "");
				respJSON.put("error", "");
			}

		} catch (IOException e) {

			e.printStackTrace(new PrintWriter(errors));

			respJSON.put("status", "FAIL");
			respJSON.put("response", "");
			respJSON.put("error", errors.toString());
		}

		return respJSON;
	}

	public JSONObject get_Response(String path, String object1, String object2, String value) {

		String result = null;
		HttpUriRequest request = new HttpGet(path);
		HttpResponse httpResponse = null;
		try {
			httpResponse = HttpClientBuilder.create().build().execute(request);

			result = EntityUtils.toString(httpResponse.getEntity());

			
			JSONObject jo = new JSONObject(result);

			JSONArray results = jo.getJSONArray(object1);

			JSONObject first = results.getJSONObject(0);

			String shipper = (String) first.get(object2);

			if (first.get(object2).toString().equals(value)) {
				respJSON.put("status", "PASS");
				respJSON.put("response", first.get(object2).toString());
				respJSON.put("error", "");
			} else {
				respJSON.put("status", "FAIL");
				respJSON.put("response", "");
				respJSON.put("error", "");
			}

		} catch (IOException e) {

			e.printStackTrace(new PrintWriter(errors));

			respJSON.put("status", "FAIL");
			respJSON.put("response", "");
			respJSON.put("error", errors.toString());

		}

		return respJSON;

	}

}
