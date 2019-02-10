package com.qutap.dash.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.commonUtils.StatusCode;
import com.qutap.dash.commonUtils.Utils;
import com.qutap.dash.config.ReadQutapProperties;
import com.qutap.dash.customException.ProjectInfoException;
import com.qutap.dash.model.MemoryUsagesModel;
import com.qutap.dash.model.ProfilerModel;
import com.qutap.dash.model.ProfilerResponseModel;
import com.qutap.dash.model.TestCaseCountModel;
import com.qutap.dash.model.TestResultResponseModel;
import com.qutap.dash.service.ExecutionService;


@RestController
@RequestMapping("/Qutap")
public class ExecutionController {
	
	Logger log = LoggerFactory.getLogger(ExecutionController.class);

	@Autowired
	ExecutionService executionService;
	@Autowired
	ReadQutapProperties readQutapProperties;
	@Autowired
	ProfilerModel profilerModel;  

	static  HazelcastInstance client=null;
	
	TestCaseCountModel testCaseCountModel=new TestCaseCountModel();
	
	Map<String, Map<String,String>> map=null;
	
	/**---------------munnaf put jmx data into HazalCast -------------------*/
	Map<String, Map<String,String>> hpmap=null;
	Map<String, String> pmap=null;
	
	static
	{
		String memberIp = ReadQutapProperties.getMemberIP();
		 ClientConfig clientConfig = new ClientConfig();
	       // clientConfig.getNetworkConfig().addAddress("10.1.2.109");

		 clientConfig.getNetworkConfig().addAddress(memberIp);


		       
	       client = HazelcastClient.newHazelcastClient(clientConfig);
	        
	        System.out.println("hezelcast calling in Dashboard:::::::::"+client.hashCode());
	        System.out.println(clientConfig.toString());
		
	}
	
	@PostMapping("/execution/{testScenarioName}/{requirementName}/{moduleName}/{projectName}")
	public @ResponseBody String executeData(@PathVariable String testScenarioName, @PathVariable String requirementName,@PathVariable String moduleName,@PathVariable String projectName, HttpServletRequest req) throws IOException {	
		Response response = Utils.getResponseObject("getting executionService details data");
		try {
			TestResultResponseModel testResultResponseModel = executionService.executeData(testScenarioName, requirementName, moduleName, projectName);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(testResultResponseModel);
		} catch (Exception e) {
			log.error("error in executionService details data", e);
			throw new ProjectInfoException("testResultResponseModel not found");
		}
		return (String) Utils.getJson(response);
	}
	 
	
	@PostMapping("execution/{projectId}/{dsid}")
	//---public @ResponseBody String executeAllTestCases(@PathVariable String projectId,List<String> listOfTestSuiteIds,HttpServletRequest req) throws IOException{
	public @ResponseBody String executeAllTestCases(@PathVariable String projectId,@RequestBody List<String> listOfTestSuiteIds,@PathVariable String dsid,HttpServletRequest req) throws IOException{
		System.out.println("QutapDash--ExecutionController----executeAllTestCases(@PathVariable String projectId,HttpServletRequest req)"+listOfTestSuiteIds);
		Response response = Utils.getResponseObject("getting executionService details data");
		Map<String, String> cmap=null;
		try {
			
			map = client.getMap("map");
		
			cmap =map.get(projectId);
		
			if(cmap==null)
			{
				cmap= new HashMap<String,String>();
				
			}
			cmap.put("tastCaseCount", "0");
			cmap.put("testCaseStatusss", "In Progress");
			map.put(projectId, cmap);
			System.out.println("projectName .....Agent--- TaskExec :::::::"+projectId+":::::::"+cmap.get("testCaseStatusss"));
			//---TestResultResponseModel testResultResponseModel = executionService.executeAllTestCases(projectId);
			TestResultResponseModel testResultResponseModel = executionService.executeAllTestCases(projectId,listOfTestSuiteIds,dsid);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(testResultResponseModel);
		
			
		} catch (Exception e) {
			log.error("error in executionService details data", e);
			throw new ProjectInfoException("testResultResponseModel not found");
		}
		//cmap.put("tastCaseCount", "0");
		return (String) Utils.getJson(response);
	}
	
	
	@PostMapping("execution/{projectId}")
	public @ResponseBody String executeAllTestCases(@PathVariable String projectId,HttpServletRequest req) throws IOException{
		Response response = Utils.getResponseObject("getting executionService details data");
		try {
			TestResultResponseModel testResultResponseModel = executionService.executeAllTestCases(projectId);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(testResultResponseModel);
		} catch (Exception e) {
			log.error("error in executionService details data", e);
			throw new ProjectInfoException("testResultResponseModel not found");
		}
		return (String) Utils.getJson(response);
	}
	
	/*@PostMapping("execution/{projectId}")
	public Callable<TestResultResponseModel>  executeAllTestCases(@PathVariable String projectId,HttpServletRequest req) throws IOException{
		Response response = Utils.getResponseObject("getting executionService details data");
		try {
			Callable<TestResultResponseModel>  testResultResponseModel = new Callable<TestResultResponseModel>() {

				
				public TestResultResponseModel call() throws Exception {
					
					return executionService.executeAllTestCases(projectId);
				}
			};
			
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(testResultResponseModel);
			return testResultResponseModel;
		} catch (Exception e) {
			log.error("error in executionService details data", e);
			throw new ProjectInfoException("testResultResponseModel not found");
		}
		
	}*/
	
	@RequestMapping("/callablerequest")
	public Callable<String> callable(){
		log.info("#CallableRequest Received!");
		
		Callable<String> result = new Callable<String>() {
 
			@Override
			public String call() throws Exception {
				return doProcess();
			}
		};
		
		log.info("#CallableRequest Finish!");
		return result;
	}
	
	
	public String doProcess(){
		// Simulation the slowly process by taking time
		try{
			Thread.sleep(20000);	
		}catch(Exception e){}
		
		log.info("Service finishes the process!");
		return "Done";
	}
	
	@GetMapping("execution/testCaseCount")
	public @ResponseBody String getTestCaseCount(HttpServletRequest req) throws IOException{
		//Response response = Utils.getResponseObject("getting executionService details data");
		TestCaseCountModel testCaseCountModel=null;
		try {
			 testCaseCountModel = executionService.getTestCaseCount();
			//response.setStatus(StatusCode.SUCCESS.name());
			//response.setUrl(req.getRequestURL().toString());
			//response.setData(testCaseCountModel);
		} catch (Exception e) {
			log.error("error in executionService details data", e);
			throw new ProjectInfoException("testResultResponseModel not found");
		}
		return (String) Utils.getJson(testCaseCountModel);
	}
	
	@GetMapping("execution/testCaseCountFromHazelCast/{projectId}")
	public @ResponseBody String gethDataFromHazelCast(@PathVariable String projectId,HttpServletRequest req) throws IOException{
		
		try {
			
			System.out.println("gethDataFromHazelCast called");
			map = client.getMap("map"); 
			
			JSONObject hazelJson= executionService.getTotalNoOFTestCases();
			System.out.println("hazelJson testcases exec controller;;;;;;;;"+hazelJson);
		//Map<String,String> Clientmap=map.get(hazelJson.getString("projetName"));
			Map<String,String> Clientmap=map.get(projectId);
		//Clientmap.put("testcaselistForEachScen", hazelJson.getString("testcaselistForEachScen"));
		testCaseCountModel.setTestCaseCount(Long.parseLong(Clientmap.get("tastCaseCount")));
		testCaseCountModel.setStatus(Clientmap.get("testCaseStatusss"));
		System.out.println("statusss of hazel:::::::"+Clientmap.get("testCaseStatusss"));
		testCaseCountModel.setTotalTestCaseCount(hazelJson.getLong("testcaselistForEachScen"));
		System.out.println("testCaseCountModel::::::"+testCaseCountModel);
		for (String entry : Clientmap.keySet()) {
			System.out.println(entry + "////////////////////////comming from cotroller////////////////////////////////////// " + Clientmap.get(entry));
		}
			//response.setStatus(StatusCode.SUCCESS.name());
			//response.setUrl(req.getRequestURL().toString());
			//response.setData(testCaseCountModel);
		} catch (Exception e) {
			log.error("error in gethDataFromHazelCast details data", e);
			throw new ProjectInfoException("gethDataFromHazelCast not found");
		}
		return (String) Utils.getJson(testCaseCountModel);
	}
//	----------------------------------
	
	
/**---------------munnaf put jmx data into HazalCast -------------------*/
	

	
	@GetMapping("execution/saveDataIntoHazelCast")
	public @ResponseBody String saveDataIntoHazelCast(HttpServletRequest req) throws IOException{
	
	
		List<MemoryUsagesModel> memoryUsagesDomainList = profilerModel.getIndivisualBuilds();
		System.out.println("memoryUsagesDomainList......"+memoryUsagesDomainList);
		if(memoryUsagesDomainList==null)
		{
			memoryUsagesDomainList= new ArrayList<MemoryUsagesModel>();
			
		}
		
		try {
			
			System.out.println("gethDataFromHazelCast called");
			hpmap = client.getMap("map"); 
			System.out.println("hpmap....."+hpmap);
			JSONObject hazelJson= executionService.getTotalNoOFTestCases();
			System.out.println("hazelJson testcases exec controller;;;;;;;;"+hazelJson);
		//Map<String,String> Clientmap=map.get(hazelJson.getString("projetName"));
			String projectName= hazelJson.getString("projetName");
			String projectTxId= hazelJson.getLong("ptxid")+"";
			String profilerKey=projectName+projectTxId;	
			System.out.println("profilerKey............."+profilerKey);
			pmap =hpmap.get(profilerKey);
			
		
			if(pmap==null)
			{
				pmap= new HashMap<String,String>();
				
			}
			
			RestTemplate restTemplate = new RestTemplate();
			MemoryUsagesModel	memoryUsagesDomain = restTemplate.getForObject("http://localhost:9000/Qutap/getMemoryUsagesInformation",MemoryUsagesModel.class);
			memoryUsagesDomainList.add(memoryUsagesDomain);
			profilerModel.setIndivisualBuilds(memoryUsagesDomainList);
			profilerModel.setBuilId(projectTxId);
			JSONObject  jSONObject= new JSONObject(profilerModel);
			pmap.put(profilerKey, jSONObject.toString());
			hpmap.put(profilerKey, pmap);
			Set<String>sss=	hpmap.keySet();
			System.out.println("sss................."+sss.size());
			Iterator<String>it=	sss.iterator();
			while(it.hasNext())
			{
			String key=	it.next();
			if(key.startsWith(projectName)) {
			Map<String, String> mp=hpmap.get(key);
			System.out.println("size  "+mp.size()+"getting after saving into hc........."+mp.get(key));
			}
			
			}
		
			
			
			
		} catch (Exception e) {
			log.error("error in gethDataFromHazelCast details data", e);
			throw new ProjectInfoException("gethDataFromHazelCast not found");
		}
		return "succesfully profiler data stored into hazelcast";
	}
	
	
	@GetMapping("execution/getProfileDataFromHazelCast/{projectName}")
	public @ResponseBody Response getProfileDataFromHazelCast(@PathVariable String projectName,HttpServletRequest req) throws IOException{
		Map<String,String>	pmap=new HashMap<String,String>();
		List<Object> responseList= new ArrayList<Object>();
		try {		
			System.out.println("gethDataFromHazelCast called");
			hpmap = client.getMap("map"); 
			
			//hpmap.values().stream().collect(Collectors.toList());
				//Set<String> prifilerKeys=hpmap.keySet();
			List<String>list =hpmap.keySet().stream().collect(Collectors.toList());
			Collections.sort(list,Collections.reverseOrder());
			Iterator<String> profileIterator=list.iterator();
			while(profileIterator.hasNext())
			{
				String key=profileIterator.next();
				System.out.println("key///////////"+key);
				String buildId[] = null;
				
				if(key.startsWith(projectName)) {
					buildId=key.split(projectName);
					System.out.println("build iddddddddddddddd"+buildId[0]);
					pmap=hpmap.get(key);
				
				String value=pmap.get(key);
				if(responseList.size()>3)
					break;
				else
					responseList.add(Utils.getObject(value));			
				}
				
			}
		} catch (Exception e) {
			log.error("error in gethDataFromHazelCast details data", e);
			throw new ProjectInfoException("gethDataFromHazelCast not found");
		}
		
		System.out.println("response fro profile from hazelcast.............."+responseList);
	    ProfilerResponseModel  profilerResponseModelData= new ProfilerResponseModel();
	    profilerResponseModelData.setProjectName(projectName);
	    profilerResponseModelData.setListOfBuilds(responseList);
	    Response response = new Response();
		response.setMessage("Susseccfully getting the data");
		response.setStatus(StatusCode.SUCCESS.name());
		response.setData(profilerResponseModelData);
		response.setUrl(req.getRequestURL().toString());
		return response;
	}
	
	
	
	
	
	/*@GetMapping("/executionResult/{projectName}")
	public @ResponseBody String getExecutionResult(@PathVariable String projectId, HttpServletRequest req)
			throws IOException {
		Response response = Utils.getResponseObject("getting executionService details data");
		try {
			TestResultResponseDomain projectInfoModel = executionService.getExecutionResult(projectName);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setUrl(req.getRequestURL().toString());
			response.setData(projectInfoModel);
		} catch (Exception e) {
			log.error("error in getting executionService detail when searching by Id", e);
			throw new ProjectInfoException("projectName not found");
		}
		return (String) Utils.getJson(response);
	}*/

}

	

