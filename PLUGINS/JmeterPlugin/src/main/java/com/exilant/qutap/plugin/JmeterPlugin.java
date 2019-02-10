package com.exilant.qutap.plugin;

import java.io.File;
import java.io.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Properties;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.exilant.qutap.agent.Task;

public class JmeterPlugin implements Task{

	JSONObject mainJSON = new JSONObject();
	StringWriter errors = new StringWriter();
	List<JSONObject> ParamList = new ArrayList<JSONObject>();
	String result = "";
	String res = "";

	@Override
	public JSONObject processRequest(JSONObject requestJSON) {
		System.out.println("processRequest(JSONObject   JMETER plugin   requestJSON) 22222called");

		JSONObject respJSON = getJson(requestJSON);
	
		return respJSON;

	}

	/**
	 *  THis function is going to parse the request Json and will filter all the request parameters and finally returns the response in JSON format
	 *  
	 * @param requestJSON
	 * @return mainJSON
	 */
	public JSONObject getJson(JSONObject requestJSON) {

		List<String> myParamList = new ArrayList<String>();
		JSONArray jsonMainArr = requestJSON.getJSONArray("testStepList");
		System.out.println("JMETER REQUEST JSONHJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ"+jsonMainArr.toString());

		for (int i = 0; i < jsonMainArr.length(); i++) {
			JSONObject childJSONObject = jsonMainArr.getJSONObject(i);

			JSONArray value = childJSONObject.getJSONArray("testParamData");

			for (int j = 0; j < value.length(); j++) {
				myParamList.add(value.getString(j));
			}
			
			if(childJSONObject.getString("action").equalsIgnoreCase("JMeterAPITest")){
				result=JMeterAPITest(myParamList.toArray());
			}


			JSONObject resJSONFromAction = new JSONObject();
			resJSONFromAction.put("testStepId", childJSONObject.get("testStepsId"));
			resJSONFromAction.put("runnerType", "com.plugin.JMETER");
			if (result.equalsIgnoreCase("PASS")) {

				resJSONFromAction.put("status", result);
				resJSONFromAction.put("error", "");
			} else {

				resJSONFromAction.put("status", "FAIL");
				resJSONFromAction.put("error", result);
			}

			ParamList.add(resJSONFromAction);

			myParamList.clear();

		}
		mainJSON.put("TestResult", ParamList);
		//mainJSON.put("testSteps", ParamList);
		System.out.println(mainJSON);

		return mainJSON;

	}

	/**
	 * This method will actually run the JMeter with all the requested parameters and will return result as pass or fail
	 * 
	 * @param objects
	 * @return res
	 */
	public String JMeterAPITest(Object[] objects) {

		
		System.out.println("in JMETER API TESTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT   ************************8");
		Properties prop = null;
		try {
			prop = getProperties();
			String path=prop.getProperty("jmeterPath");
			File jmeterHome = new File(path);
			System.out.println(prop.getProperty("jmeterPath"));
			String slash = System.getProperty("file.separator");

			if (jmeterHome.exists()) {
				File jmeterProperties = new File(jmeterHome.getPath() + slash + "bin" + slash + "jmeter.properties");
				if (jmeterProperties.exists()) {
					

					StandardJMeterEngine jmeter = new StandardJMeterEngine();

					JMeterUtils.setJMeterHome(jmeterHome.getPath());
					JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
					JMeterUtils.initLogging();
					JMeterUtils.initLocale();

					HashTree testPlanTree = new HashTree();
					HTTPSamplerProxy jmeterProxySampler = new HTTPSamplerProxy();
					
	// Setting all parameters in HHTPSamplerProxy here
					jmeterProxySampler.setDomain((String) objects[1]);
					jmeterProxySampler.setPort(80);
					jmeterProxySampler.setPath("/");
					jmeterProxySampler.setMethod("GET");
					jmeterProxySampler.setName("Open"+objects[1]);
					jmeterProxySampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
					jmeterProxySampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());

					LoopController loopController = new LoopController();
   // Setting  all parameters in loopController here
					loopController.setLoops(Integer.parseInt((String) objects[3]));
					loopController.setFirst(true);
					loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
					loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
					loopController.initialize();

					ThreadGroup threadGroup = new ThreadGroup();
   // Setting  all parameters in ThreadGroup here
					threadGroup.setName("Sample Thread Group");
					threadGroup.setNumThreads(Integer.parseInt((String) objects[5]));
					threadGroup.setRampUp(Integer.parseInt((String) objects[7]));
					threadGroup.setSamplerController(loopController);
					threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
					threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());

					TestPlan testPlan = new TestPlan("Create JMeter Script From Java Code");
	// Setting  all parameters in TestPlan here
					testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
					testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
					testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());

					testPlanTree.add(testPlan);
					HashTree threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);
					threadGroupHashTree.add(jmeterProxySampler);

					SaveService.saveTree(testPlanTree, new FileOutputStream("result.jmx"));

					Summariser summer = null;
					String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
					if (summariserName.length() > 0) {
						summer = new Summariser(summariserName);
					}

					String reportFile = "report.jtl";
					String csvFile = "report.csv";
					ResultCollector logger = new ResultCollector(summer);
					logger.setFilename(reportFile);
					ResultCollector csvlogger = new ResultCollector(summer);
					csvlogger.setFilename(csvFile);
					testPlanTree.add(testPlanTree.getArray()[0], logger);
					testPlanTree.add(testPlanTree.getArray()[0], csvlogger);

					jmeter.configure(testPlanTree);
					jmeter.run();
					res = "PASS";
					System.out.println("Test completed. See " + jmeterHome + slash + "report.jtl file for results");
					System.out.println("JMeter .jmx script is available at " + jmeterHome + slash + "result.jmx");

				}
				
			}

		} catch (Exception e) {

			e.printStackTrace(new PrintWriter(errors));
			res = errors.toString();

		}
		System.out.println("====="+res);
		return res;
	}
	
	/**
	 * THis method will be used to load and start the JMeter where path will be specified in the jmeter.properties file
	 * @return prop
	 * @throws JmeterConfigException
	 */
	public Properties getProperties() throws JmeterConfigException {
		Properties prop = new Properties();
		String propFileName = "Jmeter.properties";
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

		if (inputStream != null) {
			try {
				prop.load(inputStream);
			} catch (IOException e) {
				throw new JmeterConfigException("Initialization ERROR: could not load " + propFileName + "", e);
			}
		} else
			throw new JmeterConfigException("Initialization ERROR: no Jmeter configuration was found. Please check if "
					+ propFileName + " file is in class path", null);
		return prop;
	}
}

