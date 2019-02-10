package com.exilant.qutap.agent.server;

import java.lang.reflect.Method;
import java.util.List;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import com.exilant.qutap.agent.Agent;
import com.exilant.qutap.agent.error.AgentInitException;

public class AbstractAgent {

	private Server server;
	private ServletHandler servletHandler;

	public AbstractAgent() {
		super();
	}

	public void agentBootStrap() throws AgentInitException {
		// load properties for bootstrapping the agent
		Agent.loadAgentConfigurations(Agent.AGENT_CONFIG);

		// setup embedded jetty server @{HTTP_PORT} port
		server = new Server(Agent.getInteger(Agent.HTTP_PORT));

		// The ServletHandler is a dead simple way to create a context handler
		// that is backed by an instance of a Servlet.
		// This handler then needs to be registered with the Server object.
		servletHandler = new ServletHandler();
		server.setHandler(servletHandler);
		initializeServlets();
		try {
			server.start();
		} catch (Exception e) {
			throw new AgentInitException("Initializer Error: could not start the embedded server",e);
		}
		// The use of server.join() the will make the current thread join and
		// wait until the server is done executing.
		// See
		// http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#join()
		try {
			server.join();
		} catch (InterruptedException e) {
		}
	}

	private void initializeServlets() throws AgentInitException {
		List<String> servletList = Agent.getList(Agent.SERVLET_LIST);
		Class noparams[] = {};
		for (String className : servletList) {
			Class<?> c = null;
			try {
				c = Class.forName(className);
				Object obj = c.newInstance();
				// call the printIt method
				Method method = c.getDeclaredMethod("getPath", noparams);
				String path = (String) method.invoke(obj, null);
				// This is a raw Servlet, not a Servlet that has been configured
				// through a web.xml @WebServlet annotation, or anything
				// similar.
				servletHandler.addServletWithMapping(className, path);
			} catch (Exception e) {
				throw new AgentInitException("Initializer Error: " + className + " could not be initialized", e);
			}
		}
	}

}
