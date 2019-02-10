package com.exilant.qutap.agent.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;


/**
 * The Class BaseServiceHandler is the foundation for a servlet. all Agent operations
 * that need to be exposed should extent this call and implement its on processRequest & 
 * getPath methods
 */
public abstract class BaseServiceHandler extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * This will be the default doGet handler for a service
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Map<String, String[]> map = request.getParameterMap();
			JSONObject requestJSON = new JSONObject(map);
			JSONObject responseJSON = processRequest(requestJSON);

			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println(responseJSON.toString());

		} catch (Exception e) {
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace(response.getWriter());
		}

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * 	 This will be the default doPost handler for a service
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			StringBuffer jb = new StringBuffer();
			String line = null;

			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				jb.append(line);
			}

			JSONObject requestJSON = new JSONObject(jb.toString());
			// Process this request and send the response back to the client
			JSONObject responseJSON = processRequest(requestJSON);

			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println(responseJSON.toString());
		} catch (Exception e) {
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace(response.getWriter());
		}
	}

	/**
	 * Process request.
	 *
	 * @param requestJSON the request json
	 * @return the JSON object
	 */
	public abstract JSONObject processRequest(JSONObject requestJSON);

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public abstract String getPath();

}
