package com.exilant.qutap.agent.service;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.plugin.registry.Extension;
import org.java.plugin.registry.ExtensionPoint;
import org.json.JSONArray;
import org.json.JSONObject;

import com.exilant.qutap.agent.Agent;
import com.exilant.qutap.agent.CorePlugin;
import com.exilant.qutap.agent.Recording;
import com.exilant.qutap.agent.Task;
import com.exilant.qutap.agent.Uploading;
import com.exilant.qutap.agent.error.AgentInitException;
import com.exilant.qutap.agent.server.BaseServiceHandler;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;

/**
 * TaskExec class is the called for running specific test cases. This class
 * loads the test framework plugins need to run the test case
 * 
 * INPUT JSON FORMAT ----------------- { "service" : "string", # SERVICE NAME
 * "apiVersion" : "string", # API VERSION (for future you only) "txnId" : "101",
 * # TRANSACTION ID (Tracking this request with a unique ID) "plugin" :
 * "com.exilant.plugin#samplePlugin", # the test plugin to be used to run the
 * test case "agentMeta" : { # AGENT META-DATA for runtime agent behavior
 * "logLevel" : "string", # DEBUG/INFO/ERROR/INTERACTIVE "resultType" :
 * "string", # QUEUE/LOG/CALLBACK "async" : "false" # TRUE/FALSE }, "payload" :
 * {.....} # PAYLOAD is the actual test case/suit/steps with test data
 * 
 * }
 * 
 * 
 * OUTPUT JSON FORMAT ------------------ { "service" : "string", # SERVICE NAME
 * "apiVersion" : "string" # API VERSION (for future you only) "status" :
 * "String", #BAD/OK/FAIL (NOTE: this is not the test result status. This is the
 * agent response status) "error" : "string" "payload" : {...} }
 * 
 */
public class TaskExec extends BaseServiceHandler {

	/** The log. */
	protected final Log LOG = LogFactory.getLog(getClass());

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The path. */
	private static String PATH = "/agent/testcase/execute";

	private static String RES = "{\"service\" : \"\",\"apiVersion\" : \"\",\"status\" : \"\",\"error\" : \"\"}";

	//Map<String, Object> map = null;
	static Map<String, Map<String,String>> map = null;
	long testcaseCount=0;
	static  HazelcastInstance client=null;
	static
	{
		LoadConfiguration loadConfig1 = new LoadConfiguration();
		Properties PROPS1 = loadConfig1.getProperties();
		String memberIP=PROPS1.getProperty("memberIP");
		 ClientConfig clientConfig = new ClientConfig();
	       //   clientConfig.getNetworkConfig().addAddress("10.1.2.109");
	        clientConfig.getNetworkConfig().addAddress(memberIP);
	       client = HazelcastClient.newHazelcastClient(clientConfig);
	        
	        System.out.println("hezelcast calling in Agent---:::::::::"+client.hashCode());
	        System.out.println(clientConfig.toString());
	      
		
	}
	Map<String, String> cmap=null;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.itap.agent.server.BaseServiceHandler#getPath()
	 */
	@Override
	public String getPath() {
		System.out.println("Agent---TaskExec-->getPath() called");
		return PATH;
	}
	
	LoadConfiguration loadConfig = new LoadConfiguration();
	Properties PROPS = loadConfig.getProperties();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.itap.agent.server.BaseServiceHandler#processRequest(org.json.
	 * JSONObject)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.itap.agent.server.BaseServiceHandler#processRequest(org.json.
	 * JSONObject)
	 */
	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public JSONObject processRequest(JSONObject requestJSON) {
		System.out.println("Agent--- TaskExec ---processRequest(JSONObject requestJSON) called");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Getting hazal cast map
		
		
		map = client.getMap("map");
		String projectId = requestJSON.getString("projectId");
		String projectName = requestJSON.getString("projectName");
		System.out.println("projectId .....Agent--- TaskExec::::::::::: "+projectId);
		cmap =map.get(projectId);
	
		if(cmap==null)
		{
			cmap= new HashMap<String,String>();
			
		}
	//	cmap.put("tastCaseCount", testcaseCount+"");
		long testcaseCount=Long.parseLong(cmap.get("tastCaseCount"));
		cmap.put("testCaseStatusss", "In Progress");
		map.put(projectId, cmap);
		System.out.println("projectName .....Agent--- TaskExec :::::::"+projectName+":::::::"+cmap.get("testCaseStatusss"));
		
		//--System.out.println(" TaskExec ---processRequest(JSONObject requestJSON)123 called");

		File file=null;
		JSONObject agentMeta = requestJSON.getJSONObject("agentMeta");
		System.out.println(agentMeta);
		JSONObject testCase = requestJSON.getJSONObject("testCaseDomain");
		String plugin = requestJSON.getString("plugin");
		String runnerName = testCase.getString("runnerType");
		String testCaseId = testCase.getString("testCaseId");
		
	//	String scenarioName = testCase.getString("scenarioName");
	//	LOG.debug("TASK Exec for PLUGIN: " + plugin + " service: " + requestJSON.getString("service") + " apiVersion:"
	//			+ requestJSON.getString("apiVersion") + " txnId: " + requestJSON.getString("txnId"));
		JSONObject respTestCase = null;
		JSONObject respJSON = new JSONObject(RES);
	//	respJSON.put("service", requestJSON.getString("service"));
	//	respJSON.put("apiVersion", requestJSON.getString("apiVersion"));
	//	respJSON.put("txnId", requestJSON.getString("txnId"));
		respJSON.put("testCaseName", testCase.getString("testCaseName"));
		respJSON.put("testCaseDescription", testCase.getString("testCaseDesciption"));
		respJSON.put("testCaseTag", testCase.getString("testCaseTag"));
		respJSON.put("testCasePriority", testCase.getString("testCasePriority"));
		respJSON.put("testCaseCategory", testCase.getString("testCaseCategory"));
		
	//	respJSON.put("projectName", requestJSON.getString("projectName"));
	//	respJSON.put("projectId", requestJSON.getString("projectId"));
	//	respJSON.put("testExecutionId", requestJSON.getString("testExecutionId"));
	//	respJSON.put("executedBy", requestJSON.getString("executedBy"));
	//	respJSON.put("executionDate", requestJSON.getString("executionDate"));
	
		// Adding information into hazal cast map
		//testCase.get(key)
		
		
		//--map.put("scenarioName", testCase.getString("testScenarioName"));
		//map.put("scenarioName", scenarioName);
		//--map.put("testCaseId", testCaseId);
		//-map.put("testCaseName", testCase.getString("testCaseName"));

//		for (String s : map.keySet()) {
//			System.out.println(map.get(s) + " " + "executing");
//		}

		//Uploading uploader=null;
		// end
		try {
			Task task = null;;
			Recording recorder = null;
			Uploading uploader=null;
			
//			 payload.getInt("isRecord");
//			 try {
//			 // check if recording is needed
//			  if (payload.getInt("isRecord") == 1) {
//			 recorder = (Recording)
//			 loadPlugin("com.exilant.plu.record@RecordPlugin");
//			 recorder.startRecording(testCaseId);
//			 }

			// check if plugin is present or not....if not download
			// plugin from server			
			/*try {
				String FILE_LOCATION = PROPS.getProperty("pluginPath");
				File pluginPath=new File(FILE_LOCATION);
				File[] pluginsPath=pluginPath.listFiles();
				String actualPluginName=null;
				String actualRecordPluginName=null;
				for(File value:pluginsPath) {
					if(value.getName().toLowerCase().startsWith(runnerName.toLowerCase())) {
						
						System.out.println("Plugin name"+ value.getName());
						
						File[] pluginsFolder=value.listFiles();
						File[] pluginsFolde1=new File(value.getAbsolutePath()+"/"+"target").listFiles(f->f.getName().toLowerCase().startsWith(runnerName.toLowerCase()) && f.getName().toLowerCase().endsWith(".zip"));
						System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv"+pluginsFolde1[0]);
						System.out.println(pluginsFolde1[0].toString());
						actualPluginName=pluginsFolde1[0].toString();
						
					}
					if(value.getName().toLowerCase().startsWith("recordPlugin".toLowerCase())) {
						
						System.out.println("Plugin name"+ value.getName());
						
						File[] pluginsFolder=value.listFiles();
						File[] pluginsFolde1=new File(value.getAbsolutePath()+"/"+"target").listFiles(f->f.getName().toLowerCase().startsWith("recordPlugin".toLowerCase()) && f.getName().toLowerCase().endsWith(".zip"));
						System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv"+pluginsFolde1[0]);
						System.out.println(pluginsFolde1[0].toString());
						actualRecordPluginName=pluginsFolde1[0].toString();
						
					}
				}
				System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBB"+actualPluginName);
				System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBB1"+actualRecordPluginName);
				int pluginFound = 0;
				Installer installer = new Installer();
				File dir = new File(Installer.PLUGINPATH);
				
				String[] directories = SearchPlugin.getAllDirectories(Installer.PLUGINPATH);
				for (int i = 0; i < directories.length; i++) {
					System.out.println("PPPPPPPPPPPPP"+directories[i]);
					System.out.println("QQQQQQQQQQQQQ"+actualPluginName);
					System.out.println("DDDDDDDDDDDplllllllllllllllllllllllll"+directories[i].toLowerCase().startsWith(runnerName.toLowerCase()));
					System.out.println(directories[i].toLowerCase());

					if (directories[i].toLowerCase().startsWith(runnerName.toLowerCase())) {
						pluginFound = 1;
						System.out.println(pluginFound);
						System.out.println("CPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
						break;
					}
				}
				if (pluginFound == 0) {
					System.out.println("SEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
					String s1[]=actualPluginName.split("/");
					String s[]=actualRecordPluginName.split("/");					
					
					
					System.out.println("VALLLLLLLLLLLLLLLLL"+s1[s1.length-3]+"/"+s1[s1.length-2]+"/"+s1[s1.length-1]);
					String string = installer.installer(s1[s1.length-1]);
					installer.installer(s[s.length-1]);
					System.out.println("OOOOOOOOOOOOOOOOOOOO"+Installer.PLUGINPATH+s[s.length-1]);
					Installer.unzip(Installer.PLUGINPATH+"/"+s1[s1.length-1], Installer.PLUGINPATH);
					Installer.unzip(Installer.PLUGINPATH+"/"+s[s.length-1] , Installer.PLUGINPATH);
				
					
			//		System.out.println(directories);
				}

			} catch (IOException e) {

				e.printStackTrace();
			}*/
			
			// CorePlugin cr=new CorePlugin();
			// cr.reInitiazePlugin(plugin);
			try {
				// check if recording is needed
				
			//	if (payload.getInt("isRecord") == 1) {
		
			String recordPlugin="com.exilant.plu.record@RecordPlugin";
			recorder = (Recording) loadRecordPlugin(recordPlugin);
			recorder.startRecording(testCaseId);
			
			//--	System.out.println("Recoding started");				
				task = (Task) loadPlugin(plugin);
			} catch (Exception e) {
				LOG.error("could not load plugin : " + plugin, e);
				throw e;
			}   
			
			try {
				Date startDate = new Date();
				Calendar calendar1 = Calendar.getInstance();
				long timeMilli1 = calendar1.getTimeInMillis();
				System.out.println("TestCasecount b4 Exection..............................................."+testcaseCount);
				testcaseCount++;
				
				
				
				respTestCase = task.processRequest(testCase);
				
				System.out.println("response After TestCase executed...........***************............"+respTestCase);
				
				
				//cmap.put("tastCaseCount", testcaseCount+"");
				
				
				System.out.println(respTestCase);
				Date endDate = new Date();
				
				Calendar calendar2 = Calendar.getInstance();
				long timeMilli2 = calendar2.getTimeInMillis();
				
				//respJSON.put("StartDateTime", dateFormat.format(startDate));
				//respJSON.put("EndDateTime", dateFormat.format(endDate));
				respJSON.put("StartDateTime",String.valueOf(timeMilli1) );
				respJSON.put("EndDateTime", String.valueOf(timeMilli2));
				respJSON.put("testCaseId", testCaseId);
				respJSON.put("TestCaseValues", respTestCase);
				
				
				JSONArray jsonMainArr = respTestCase.getJSONArray("TestResult");
				ArrayList<String> statusList=new ArrayList<String>();
				System.out.println("Result Json inside screensho"+jsonMainArr);
				
				for (int i = 0; i < jsonMainArr.length(); i++) {
					
					JSONObject childJSONObject = jsonMainArr.getJSONObject(i);
					System.out.println("inside SCreeeeeeeeeeeeen"+childJSONObject);
					if(childJSONObject.getString("status").equalsIgnoreCase("pass")) {						
						respJSON.put("status", "Pass");	
						statusList.add("Pass");
					}else {
						respJSON.put("status", "fail");	
						statusList.add("Fail");
				String screenShot=	childJSONObject.getString("screenShot");
				
				String[] fullpath=screenShot.split("Snapshot");
				String filename=fullpath[1];
				System.out.println("IN ELSEEEEEEEE"+filename);
				String screenShotDirectoryPath = PROPS.getProperty("screenShotFileFolder");
				//String response1 = UploadFile.fileUploadScreenShot(screenShotDirectoryPath,"Snapshot"+filename);
				
				
				String pluginToUpload="com.exilant.plug.upload@UploadPlugin";
				uploader = (Uploading)loadUploadPlugin(pluginToUpload);
				String response1 = uploader.screenShotUpload(screenShotDirectoryPath,"Snapshot"+filename);
				
				childJSONObject.put("screenShot",response1 );
				
				
					}
				}
				Boolean tcStatus=statusList.contains("Fail");
				if(tcStatus==true)
				{
					cmap.put("testCaseStatusss", "Fail");
				}
				else
				{
					cmap.put("testCaseStatusss", "Pass");
				}
				System.out.println("projectName .123....Agent--- TaskExec :::::::"+projectName+":::::::"+cmap.get("testCaseStatusss"));
			//--	map.put(projectName, cmap);
				System.out.println("TestCasecount after Exection..............................................."+testcaseCount);
				
				
			} catch (Exception e) {
				System.out.println("inside catch of TaskExe'''''''''''''''''''''''''''''''''''''''''''''''''''"+e);
				e.printStackTrace();
				LOG.error("could not load plugin : " + plugin, e);
				
				throw e;
			} finally {
				if (recorder != null) {
					recorder.stopRecording();
					System.out.println("Recoding stoped");
					
					
					
				}
			}
			
			// Adding response into hazalacst map

			if (respTestCase != null) {
				
				//map.put("Response", respTestCase.toString());
				for (String s : map.keySet()) {
					//--System.out.println("TestCase with ID " + map.get(s) + " " + "executing");
				}

			}
			
			if (respTestCase != null) {
				JSONArray resultJSON = respTestCase.getJSONArray("TestResult");

				for (int i = 0; i < resultJSON.length(); i++) {
					String status = resultJSON.getJSONObject(i).getString("status");
					System.out.println("Result json"+resultJSON);
					System.out.println("Status:::::;"+status);
					System.out.println("result Json length:::::"+resultJSON.length());
					String startTime = resultJSON.getJSONObject(0).getString("StartDateTime");
					if (status.equalsIgnoreCase("FAIL")) {
						System.out.println("Inside fail");
						String testStepId=resultJSON.getJSONObject(i).getString("testStepId");
						String time = resultJSON.getJSONObject(i).getString("StartDateTime");
						Properties PROPS = Agent.loadConfigurations(Agent.AGENT_CONFIG);
						String directoryPath = PROPS.getProperty("videoFileFolderPath");
						
						System.out.println("directoryPath:::::::;"+directoryPath);
						
						
						//String response = UploadFile.fileUpload(directoryPath,"TestCaseId" + testCaseId);//video upload
						
						String response = uploader.videoUpload(directoryPath, "TestCaseId" + testCaseId);//video upload
						
						
						
						respJSON.put("video", response);
						//file = UploadFile.getFileName(directoryPath, "TestCaseId" + testCaseId+"-"+startTime);	
						/*ScreenShots.StartScreenShots(directoryPath +"/"+file.getName(), startTime,
							resultJSON.getJSONObject(i).getString("EndDateTime"),"testStepId"+testStepId);						
//						String screenShotDirectoryPath = PROPS.getProperty("screenShotFileFolder");
//						String response1 = UploadFile.fileUpload(screenShotDirectoryPath, "testStepId" + testStepId);	*/
						
						
					}

				}
				
			}
			/*if (respTestCase != null) {
				String response = UploadFile.fileUpload("TestCaseId" + testCaseId);
				System.out.println(response);
			}*/
					
		} catch (Exception e) {
			LOG.error("Task exec failed: " + plugin, e);
			respJSON.put("status", "FAIL");
			respJSON.put("error", e.getMessage());
					/*String directoryPath = PROPS.getProperty("videoFileFolderPath");
					System.out.println("directoryPath:::::::;"+directoryPath);
					String response = uploader.videoUpload(directoryPath, "TestCaseId" + testCaseId);//video upload
					respJSON.put("video", response);*/
					
			
		}
		
		cmap.put("tastCaseCount", testcaseCount+"");
		System.out.println("Agent---taskExec--SEEEEEEEEEEEEEEEEEEEEINHHHHHHHHH"+respJSON);
		map.put(projectId, cmap);
		 
		return respJSON;
	}

	private static Object loadPlugin(String plugin) throws Exception {
		System.out.println("Agent---taskExec--loadPlugin(String plugin) called");
		
		ExtensionPoint toolExtPoint = null;
		Object task = null;
		toolExtPoint = CorePlugin.pManager.getRegistry().getExtensionPoint(CorePlugin.cp.getDescriptor().getId(),
				"Task");

		Extension ext = toolExtPoint.getConnectedExtension(plugin);

		CorePlugin.pManager.activatePlugin(ext.getDeclaringPluginDescriptor().getId());
		ClassLoader classLoader = CorePlugin.pManager.getPluginClassLoader(ext.getDeclaringPluginDescriptor());
		// Load Tool class.
		Class<?> toolCls = null;
		toolCls = classLoader.loadClass(ext.getParameter("class").valueAsString());
	
		// Create Tool instance.
	
		task = toolCls.newInstance();
		
		return task;
	}
	
private Object loadRecordPlugin(String plugin) throws Exception {
	System.out.println("Agent---taskExec--loadRecordPlugin(String plugin)");
	
		
		ExtensionPoint toolExtPoint = null;
		Object recorder = null;
		toolExtPoint = CorePlugin.pManager.getRegistry().getExtensionPoint(CorePlugin.cp.getDescriptor().getId(),
				"Recording");
		Extension ext = toolExtPoint.getConnectedExtension(plugin);
		CorePlugin.pManager.activatePlugin(ext.getDeclaringPluginDescriptor().getId());
		ClassLoader classLoader = CorePlugin.pManager.getPluginClassLoader(ext.getDeclaringPluginDescriptor());
		// Load Tool class.
		Class<?> toolCls = null;
		toolCls = classLoader.loadClass(ext.getParameter("class").valueAsString());
		// Create Tool instance.
		recorder = toolCls.newInstance();
		return recorder;
	}


private Object loadUploadPlugin(String plugin) throws Exception {
	System.out.println("Agent---VideoAndScreenshot--loadVideoAndScreenshotPlugin(String plugin)");
	
		
		ExtensionPoint toolExtPoint = null;
		Object uploader = null;
		toolExtPoint = CorePlugin.pManager.getRegistry().getExtensionPoint(CorePlugin.cp.getDescriptor().getId(),
				"Uploading");
		
		System.out.println("toolExtPoint:::"+toolExtPoint);
		
		Extension ext = toolExtPoint.getConnectedExtension(plugin);
		System.out.println("ext:::"+ext);
		CorePlugin.pManager.activatePlugin(ext.getDeclaringPluginDescriptor().getId());
		ClassLoader classLoader = CorePlugin.pManager.getPluginClassLoader(ext.getDeclaringPluginDescriptor());
		System.out.println("classLoader:::"+classLoader);
		// Load Tool class.
		Class<?> toolCls = null;
		toolCls = classLoader.loadClass(ext.getParameter("class").valueAsString());
		System.out.println("toolCls:::"+toolCls);
		// Create Tool instance.
		uploader = toolCls.newInstance();
		System.out.println("uploader:::"+uploader);
		return uploader;
	}



	/**
	 * Validate plugin, if plugin not installed, try to download the plugin and
	 * install the plugin
	 */
	private void validatePluginInstallation() {

		// TODO : implement plugin validation and installation process
	}

	public Map<String, Object> clientHazelCast() throws MalformedURLException, AgentInitException, InterruptedException {

		System.out.println("calling hazelCast::::::::::::::::::");
		
		Properties PROPS = Agent.loadConfigurations(Agent.AGENT_CONFIG);//loading agent properties
		String path = PROPS.getProperty("registrationURL");
		URL url = new URL(path);

//		ClientConfig clientConfig = new ClientConfig();
//		// add the server IP here i.e where you have initialized the server
//		clientConfig.getNetworkConfig().addAddress(url.getHost());
//
//		HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);//agent registration service url giving to new Hazel
//
//		// get the map that is defined in the server
//		Map<String, Object> map = client.getMap("map");
//
//		return map;

		// similarly you can add an entry into the map here and just do map.get
		// on the server side.
		
		
		

    	/********************************************************************************************************************/
            Thread.sleep(50000);
		 ClientConfig clientConfig = new ClientConfig();
	        clientConfig.getNetworkConfig().addAddress("10.1.2.36");
	       
         HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
         System.out.println("hezelcast calling in client:::::::::"+client.hashCode());
         //All cluster operations that you can do with ordinary HazelcastInstance
         
         Map<String, Object>  map = client.getMap("map");
         
         
         System.out.println("data stored from server::config:::..............,,,,,,,,,,,,,,,,,,,....................."+map.get("dummyData"));

/********************************************************************************************************************/




         return map;

	}
	
}
