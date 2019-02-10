package com.qutap.dash.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.qutap.dash.commonUtils.DateUtility;
import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.commonUtils.Utils;
import com.qutap.dash.config.LoadConfiguration;
import com.qutap.dash.config.ReadQutapProperties;
import com.qutap.dash.config.TestTransport;
import com.qutap.dash.customException.ExecutionException;
import com.qutap.dash.domain.InputData;
import com.qutap.dash.domain.InputData1;
import com.qutap.dash.domain.ModuleDomain;
import com.qutap.dash.domain.ProjectInfoDomain;
import com.qutap.dash.domain.RequirementDomain;
import com.qutap.dash.domain.TestCaseData;
import com.qutap.dash.domain.TestCaseDomain;
import com.qutap.dash.domain.TestCaseExecutionListDomain;

import com.qutap.dash.domain.TestResultResponseDomain;
import com.qutap.dash.domain.TestScenarioDomain;
import com.qutap.dash.domain.TestStepExecutionListDomain;
import com.qutap.dash.domain.TestSuiteDomain;
import com.qutap.dash.domain.UserGroupsDomain;
import com.qutap.dash.model.AgentMeta;
import com.qutap.dash.model.TestCaseCountModel;
import com.qutap.dash.model.TestResultResponseModel;
import com.qutap.dash.model.UserGroupsModel;
import com.qutap.dash.repository.ApplicationGroupDao;
import com.qutap.dash.repository.DashboardDao;
import com.qutap.dash.repository.ExcelDataDao;
import com.qutap.dash.repository.ExecutionDao;
import com.qutap.dash.repository.ProjectInfoDao;
import com.qutap.dash.repository.TestScenarioDao;
import com.qutap.dash.repository.TestSuiteDao;
import com.qutap.dash.repository.UserDao;

import ch.qos.logback.core.net.SyslogOutputStream;

@Service
public class ExecutionServiceImpl implements ExecutionService {

	Logger log = LoggerFactory.getLogger(ExecutionServiceImpl.class);

	@Autowired
	ExecutionDao executionDao;

	@Autowired
	ProjectInfoDao projectInfoDao;

	@Autowired
	ExcelDataDao excelDataDao;

	@Autowired
	ReadQutapProperties readQutapProperties;

	@Autowired
	TestScenarioDao testScenarioDao;

	@Autowired
	DashboardDao dashboardDao;

	@Autowired
	UserDao userDao;

	@Autowired
	TestSuiteDao testSuiteDao;

	@Autowired
	ApplicationGroupDao applicationGroupDao;

	private static long txnId;

	/*--static  HazelcastInstance client=null;
	Map<String, Object> map=null;
	static
	{
		 ClientConfig clientConfig = new ClientConfig();
	        clientConfig.getNetworkConfig().addAddress("10.1.2.109");
	       client = HazelcastClient.newHazelcastClient(clientConfig);
	        
	        System.out.println("hezelcast calling in Dashboard:::::::::"+client.hashCode());
	        System.out.println(clientConfig.toString());
		
	}
	*/

	TestCaseCountModel testCaseCountModel = null;
	TestCaseCountModel testCaseCountModel1 = new TestCaseCountModel();
	long totalNoOFTestCases = 0;
	JSONObject hazelJson = null;

	@Override
	public TestResultResponseModel executeData(String testScenarioName, String requirementName, String moduleName,
			String projectName) {

		String Txid = dashboardDao.getLastTransactionResult(projectName);
		txnId = Long.parseLong(Txid.trim());

		Response response = new Response();
		JSONObject resJSON = null;
		JSONObject json = null;
		LoadConfiguration loadConfig = new LoadConfiguration();
		JSONObject pluginObject = null;
		long testCaseCount = 0;
		long totalTestCaseCount = 0;

		try {
			pluginObject = new JSONObject(loadConfig.getProperties());
		} catch (Exception e) {
			log.error("error in loading plugin", e);
			throw new ExecutionException("error in loading plugin");
		}
		ProjectInfoDomain projectInfoDomain = projectInfoDao.getProjectInfoByName(projectName);

		TestResultResponseDomain testResultResponseDomain = null;
		TestResultResponseModel testResultResponseModel = null;

		InputData1 input = new InputData1();
		input.setService("QuTapService");
		input.setApiVersion("10.2");

		AgentMeta agentMeta = new AgentMeta();
		agentMeta.setASync("false");
		agentMeta.setLogLevel("debug");
		agentMeta.setResultType("LOG");
		input.setAgentMeta(agentMeta);
//		txnId++;
		input.setTxnId(txnId + "");
		 
		try {
			TestScenarioDomain testScenarioDomain = testScenarioDao.getTestScenarioByName(testScenarioName,
					requirementName, moduleName, projectName);
			System.out.println(testScenarioDomain);
			List<TestCaseDomain> testCaseDomainList = testScenarioDomain.getTestCaseList();
			TestCaseExecutionListDomain testCaseExecutionList = null;
			List<TestCaseExecutionListDomain> listCase = new ArrayList<>();

			for (TestCaseDomain testCaseDomain : testCaseDomainList) {
				testResultResponseDomain = new TestResultResponseDomain();
				testResultResponseModel = new TestResultResponseModel();
				if (testCaseDomain.getExcecuteOrSkip().equalsIgnoreCase("Y")) {

					testCaseExecutionList = new TestCaseExecutionListDomain();

					List<TestStepExecutionListDomain> listdata = new ArrayList<TestStepExecutionListDomain>();
					input.setTestCaseDomain(testCaseDomain);

					String runner = testCaseDomain.getRunnerType();
					String plugin = null;
					for (String string : pluginObject.keySet()) {
						if (string.equals(runner)) {
							plugin = pluginObject.getString(runner);
						}
					}
					input.setPlugin(plugin);
					json = new JSONObject(input);

					testCaseCount++;
					testCaseCountModel.setTestCaseCount(testCaseCount);

					resJSON = TestTransport.postRequestExec(readQutapProperties.getExcecutionPath(), json);

					JSONArray jArray = resJSON.getJSONObject("TestCaseValues").getJSONArray("TestResult");

					if (jArray != null) {
						for (int i = 0; i < jArray.length(); i++) {
							TestStepExecutionListDomain testStepExecutionList = new TestStepExecutionListDomain();
							Gson gson = new Gson();
							TestStepExecutionListDomain testStepExecutionValues = gson
									.fromJson(jArray.getJSONObject(i).toString(), TestStepExecutionListDomain.class);

							listdata.add(testStepExecutionValues);

						}
					}
					txnId++;
					testCaseExecutionList.setTestResult(listdata);
					testCaseExecutionList.setTestCaseId(testCaseDomain.getTestCaseId());
					testCaseExecutionList.setTestCaseName(testCaseDomain.getTestCaseName());
					testCaseExecutionList.setTestCaseDescription(testCaseDomain.getTestCaseDesciption());
					testCaseExecutionList.setTestCaseTag(testCaseDomain.getTestCaseTag());
					testCaseExecutionList.setTestExecutionId(new ObjectId().toString());
					testCaseExecutionList.setStatus(resJSON.getString("status").toString());

					testCaseExecutionList.setModuleName(moduleName);
					testCaseExecutionList.setRequirementName(requirementName);
					testCaseExecutionList.setDuration(DateUtility.getDuration(resJSON.getString("StartDateTime"),
							resJSON.getString("EndDateTime")));
					testCaseExecutionList.setTestCaseCategory(testCaseDomain.getTestCaseCategory());
					testCaseExecutionList.setTestCasePriority(testCaseDomain.getTestCasePriority());

					listCase.add(testCaseExecutionList);

				}
				if (testCaseDomain.getExcecuteOrSkip().equalsIgnoreCase("N")) {
					System.out.println("TestCase data Skipped" + testCaseDomain);
					response = new Response();
				}

			}
			testResultResponseDomain.setProjectId(projectInfoDomain.getProjectId());
			testResultResponseDomain.setProjectName(projectName);
			testResultResponseDomain.setApiVersion(input.getApiVersion());
			testResultResponseDomain.setService(input.getService());

			testResultResponseDomain.setTestCaseExecutionList(listCase);
			testResultResponseDomain.setExecutionDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));
			// testResultResponseDomain.setTxnId(input.getTxnId());
			testResultResponseDomain.setTxnId(txnId + "");
			/*--			for (String entry : map.keySet()) {
							System.out.println(entry + "////////////////////////////////////////////////////////////// " + map.get(entry));
						}
						*/
			testCaseCount = 0;

			// -- map.put("testCaseCountttt",testCaseCount);

			// testResultResponseDomain.setStatus(resJSON.getString("status").toString());
			testResultResponseDomain.setExecutedBy(userDao.getUserById(projectInfoDomain.getUserId()).getUserName());

			BeanUtils.copyProperties(testResultResponseDomain, testResultResponseModel);
			Response responseValue = executionDao.executeData(testResultResponseDomain);
			return testResultResponseModel;
		} catch (Exception e) {
			log.error("error in executing the test case", e);
			throw new ExecutionException("error in executing the test case");
		}

	}

	@Override
	public TestResultResponseModel executeAllTestCases(String projectId, List<String> listOfTestSuiteIds, String dsid) {

		Response response = null;
		JSONObject resJSON = null;
		JSONObject json = null;
		LoadConfiguration loadConfig = new LoadConfiguration();
		JSONObject pluginObject = null;
		long countPlugin = 0;
		String plugin = null;
		List<TestCaseData> listOfTestcaseForALlSUite = new ArrayList<TestCaseData>();
		try {
			pluginObject = new JSONObject(loadConfig.getProperties());
		} catch (Exception e) {
			log.error("error in loading plugin", e);
			throw new ExecutionException("error in loading plugin");
		}
		ProjectInfoDomain projectInfoDomain = projectInfoDao.getProjectInfoById(projectId);
		List<TestSuiteDomain> FilterListOfTestSuite = new ArrayList<TestSuiteDomain>();

		List<TestSuiteDomain> ListOfTestSuite = testSuiteDao.getListOfTestSuitesByProjectId(projectId);

		for (String tsId : listOfTestSuiteIds) {
			TestSuiteDomain testSuiteDomain = ListOfTestSuite.stream().filter(tsu -> {
				if (tsu.getTestSuiteId().equals(tsId))
					return true;
				else
					return false;

			}).collect(Collectors.toList()).get(0);

			FilterListOfTestSuite.add(testSuiteDomain);
		}

		String Txid = dashboardDao.getLastTransactionResult(projectInfoDomain.getProjectName());

		txnId = Long.parseLong(Txid.trim());

		InputData input = new InputData();
		input.setService("QuTapService");
		input.setApiVersion("10.2");

		AgentMeta agentMeta = new AgentMeta();
		agentMeta.setASync("false");
		agentMeta.setLogLevel("debug");
		agentMeta.setResultType("LOG");
		input.setAgentMeta(agentMeta);

		// txnId++;
		input.setTxnId(txnId + "");
		TestResultResponseModel testResultResponseModel = new TestResultResponseModel();
		
		try {
			UserGroupsDomain userGroupsDomain=applicationGroupDao.getUserGroupsInfo(dsid);
			if(userGroupsDomain!=null) {
			ProjectInfoDomain projectInfo = projectInfoDao.getProjectInfoById(projectId);
			input.setProjectId(projectId);
			input.setProjectName(projectInfo.getProjectName());
			testCaseCountModel = new TestCaseCountModel();

			hazelJson = new JSONObject(input);

			/** ---------------munnaf put jmx data into HazalCast ------------------- */
			long profilerTxid = txnId;
			hazelJson.put("ptxid", ++profilerTxid);

			totalNoOFTestCases = getTotalNoOfTEstCasesBasedOnNoOFSuites(FilterListOfTestSuite);

			hazelJson.put("testcaselistForEachScen", totalNoOFTestCases);
			hazelJson.put("projetName", projectInfo.getProjectName());

			testCaseCountModel.setTotalTestCaseCount(totalNoOFTestCases);

			List<ModuleDomain> modList = projectInfo.getModuleList();

			List<TestCaseExecutionListDomain> listCase = new ArrayList<>();
			TestResultResponseDomain testResultResponseDomain = new TestResultResponseDomain();

			for (TestSuiteDomain testSuiteDomain : FilterListOfTestSuite) {

				List<TestCaseDomain> listOfTestCasesOfTestSuite = new ArrayList<TestCaseDomain>();

				if (modList != null) {

					for (ModuleDomain moduleDomain : modList) {

						List<RequirementDomain> reqList = moduleDomain.getRequirementList();
						if (reqList != null) {
							for (RequirementDomain requirementDomain : reqList) {

								List<TestScenarioDomain> testSceList = requirementDomain.getTestScenarioList();

								if (testSceList != null) {
									for (TestScenarioDomain testScenarioDomain : testSceList) {

										String projectName = projectInfo.getProjectName();

										String testScenarioName = testScenarioDomain.getTestScenarioName();

										List<TestCaseDomain> testCaseDomainList = testScenarioDomain.getTestCaseList();

										for (TestCaseDomain testCaseDomain : testCaseDomainList) {

											List<String> testCaseId = null;

											testCaseId = testSuiteDomain.getTestCaseIdList().stream()
													.filter(tcaseId -> {
														if (tcaseId.equals(testCaseDomain.getTestCaseId()))
															return true;
														else
															return false;

													}).collect(Collectors.toList());

											if (!testCaseId.isEmpty()) {

												if (testCaseDomain.getExcecuteOrSkip().equalsIgnoreCase("Y")) {
													String moduleName = moduleDomain.getModuleName();
													String requirementName = requirementDomain.getRequirementName();
													TestCaseData testCaseData = new TestCaseData();
													testCaseData.setModuleName(moduleName);
													testCaseData.setRequirementName(requirementName);
													testCaseData.setTestCaseDomain(testCaseDomain);
													listOfTestcaseForALlSUite.add(testCaseData);
													listOfTestCasesOfTestSuite.add(testCaseDomain);

												}

												if (testCaseDomain.getExcecuteOrSkip().equalsIgnoreCase("N")) {

													response = new Response();
												}
											}

										}

									}

								}
							}

						}
					}
				}

				input.setTestCaseDomain(listOfTestCasesOfTestSuite);

				/*
				 * for(TestCaseDomain testCaseDomain :listOfTestCasesOfTestSuite) { String
				 * runner = testCaseDomain.getRunnerType();
				 * 
				 * for (String string : pluginObject.keySet()) { if (string.equals(runner)) {
				 * plugin = pluginObject.getString(runner); } } }
				 */
				if (!(listOfTestCasesOfTestSuite.isEmpty()) && countPlugin == 0) {
					TestCaseDomain testCaseDomain = listOfTestCasesOfTestSuite.get(0);
					String runner = testCaseDomain.getRunnerType();

					for (String string : pluginObject.keySet()) {
						if (string.equals(runner)) {
							plugin = pluginObject.getString(runner);
						}
					}
					countPlugin++;
				}
				input.setPlugin(plugin);

				json = new JSONObject(input);
				resJSON = TestTransport.postRequestExec(readQutapProperties.getExcecutionPathSuite(), json);

				JSONArray jsonMainArr = resJSON.getJSONArray("listOfResponseJason");

				for (int i = 0; i < jsonMainArr.length(); i++) {
					TestCaseExecutionListDomain testCaseExecutionList = new TestCaseExecutionListDomain();

					List<TestStepExecutionListDomain> listdata = new ArrayList<TestStepExecutionListDomain>();

					JSONObject childJSONObject = jsonMainArr.getJSONObject(i);

					JSONArray jArray = childJSONObject.getJSONObject("TestCaseValues").getJSONArray("TestResult");

					if (jArray != null) {
						for (int j = 0; j < jArray.length(); j++) {
							Gson gson = new Gson();

							TestStepExecutionListDomain testStepExecutionValues = gson
									.fromJson(jArray.getJSONObject(j).toString(), TestStepExecutionListDomain.class);

							listdata.add(testStepExecutionValues);

						}
					}

					testCaseExecutionList.setTestResult(listdata);
					testCaseExecutionList.setTestCaseId(childJSONObject.getString("testCaseId"));
					testCaseExecutionList.setTestCaseName(childJSONObject.getString("testCaseName"));
					testCaseExecutionList.setTestCaseDescription(childJSONObject.getString("testCaseDescription"));
					testCaseExecutionList.setTestCaseTag(childJSONObject.getString("testCaseTag"));
					testCaseExecutionList.setTestExecutionId(new ObjectId().toString());
					// testCaseExecutionList.setStatus(resJSON.getString("status").toString());
					testCaseExecutionList.setStatus(childJSONObject.getString("status").toString());
					for (int j = 0; j < jArray.length(); j++) {
						if (jArray.getJSONObject(j).getString("status").equalsIgnoreCase("fail")) {
							testCaseExecutionList
									.setTestCaseExecutionVideo(childJSONObject.getString("video").toString());
						}
					}

					TestCaseData testCaseData = listOfTestcaseForALlSUite.stream().filter(tcData -> {

						if (tcData.getTestCaseDomain().getTestCaseId().equals(childJSONObject.getString("testCaseId")))
							return true;
						else
							return false;

					}).collect(Collectors.toList()).get(0);

					testCaseExecutionList.setModuleName(testCaseData.getModuleName());
					testCaseExecutionList.setRequirementName(testCaseData.getRequirementName());
					testCaseExecutionList.setDuration(DateUtility.getDuration(
							childJSONObject.getString("StartDateTime"), childJSONObject.getString("EndDateTime")));
					testCaseExecutionList.setTestCaseCategory(childJSONObject.getString("testCasePriority"));
					testCaseExecutionList.setTestCasePriority(childJSONObject.getString("testCaseCategory"));

					listCase.add(testCaseExecutionList);

				}

			}
			txnId++;
			testResultResponseDomain.setProjectId(projectInfoDomain.getProjectId());
			testResultResponseDomain.setProjectName(projectInfoDomain.getProjectName());
			testResultResponseDomain.setApiVersion(input.getApiVersion());
			testResultResponseDomain.setService(input.getService());
			testResultResponseDomain.setTestCaseExecutionList(listCase);
			testResultResponseDomain.setExecutionDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));

			testResultResponseDomain.setTxnId(txnId + "");
			if(dsid!=null) {
			UserGroupsDomain domain = applicationGroupDao.getUserGroupsInfo(dsid);
			String userName = domain.getUserName();
			testResultResponseDomain.setExecutedBy(userName);
			}
			BeanUtils.copyProperties(testResultResponseDomain, testResultResponseModel);
			executionDao.executeData(testResultResponseDomain);
			}else {
				log.error("error in getting dsid data");
				throw new ExecutionException("error in getting dsid data");
			}
		} catch (Exception e) {
			log.error("error in executing the test case", e);
			throw new ExecutionException("error in executing the test case");
		}
		
		return testResultResponseModel;
	}

	@Override
	public TestCaseCountModel getTestCaseCount() {

		return testCaseCountModel;
	}

//	public TestCaseCountModel gethDataFromHazelCast()
//	{
//		System.out.println("gethDataFromHazelCast called from ExecutionServiceImpl");
//	map = client.getMap("map");
//	testCaseCountModel1.setStatus(map.get("statuss").toString());
////	testCaseCountModel1.setTestScenarioName(map.get("ScenarioNamee").toString());
////	testCaseCountModel1.setTestCaseId(map.get("TestCaseIdd").toString());
////	testCaseCountModel1.setTestCaseName(map.get("TestCaseNamee").toString());
//	testCaseCountModel1.setTestCaseCount(Long.parseLong(map.get("testCaseCountttt").toString().trim()));
//	testCaseCountModel1.setTotalTestCaseCount(Long.parseLong(map.get("totalNoOFTestCases").toString().trim()));
//	System.out.println(testCaseCountModel1);
//    return testCaseCountModel1;
//
//	}
	/*
	 * *****************************************************************************
	 * *********************************************************************
	 */
	public long getTotalNoOfTEstCases(ProjectInfoDomain projectInfo)

	{

		List<ModuleDomain> modList = projectInfo.getModuleList();
		long testcaselistForEachScen = 0;
		JSONObject json = new JSONObject();
		if (modList != null) {

			for (ModuleDomain moduleDomain : modList) {

				List<RequirementDomain> reqList = moduleDomain.getRequirementList();
				if (reqList != null) {
					for (RequirementDomain requirementDomain : reqList) {

						List<TestScenarioDomain> testSceList = requirementDomain.getTestScenarioList();

						if (testSceList != null) {
							for (TestScenarioDomain testScenarioDomain : testSceList) {

								List<TestCaseDomain> testCaseDomainList = testScenarioDomain.getTestCaseList();
								testcaselistForEachScen = testcaselistForEachScen + testCaseDomainList.size();

							} // testscenariio
						} // senalist

					} // req
				} // regList
			} // module
		} // modlist

		return testcaselistForEachScen;
	}

	public JSONObject getTotalNoOFTestCases() {
		return hazelJson;
	}

	public long getTotalNoOfTEstCasesBasedOnNoOFSuites(List<TestSuiteDomain> filterListOfTestSuite)

	{

		long testcaselistForEachScen = 0;

		for (TestSuiteDomain testSuiteDomain : filterListOfTestSuite) {

			long testCasecount = testSuiteDomain.getTestCaseIdList().stream().count();
			testcaselistForEachScen += testCasecount;

		}
		return testcaselistForEachScen;

	}

	@Override
	public TestResultResponseModel executeAllTestCases(String projectId) {

		Response response = null;
		JSONObject resJSON = null;
		JSONObject json = null;
		LoadConfiguration loadConfig = new LoadConfiguration();
		JSONObject pluginObject = null;
		// --long testCaseCount=0;
		// --long totalTestCaseCount=0;
		// -- map = client.getMap("map");
		try {
			pluginObject = new JSONObject(loadConfig.getProperties());
		} catch (Exception e) {
			log.error("error in loading plugin", e);
			throw new ExecutionException("error in loading plugin");
		}
		ProjectInfoDomain projectInfoDomain = projectInfoDao.getProjectInfoById(projectId);

		String Txid = dashboardDao.getLastTransactionResult(projectInfoDomain.getProjectName());

		txnId = Long.parseLong(Txid.trim());

		TestResultResponseDomain testResultResponseDomain = null;
		TestResultResponseModel testResultResponseModel = null;

		InputData1 input = new InputData1();
		input.setService("QuTapService");
		input.setApiVersion("10.2");

		AgentMeta agentMeta = new AgentMeta();
		agentMeta.setASync("false");
		agentMeta.setLogLevel("debug");
		agentMeta.setResultType("LOG");
		input.setAgentMeta(agentMeta);
		// txnId++;
		input.setTxnId(txnId + "");
		try {
			List<TestCaseExecutionListDomain> listCase = null;
			ProjectInfoDomain projectInfo = projectInfoDao.getProjectInfoById(projectId);
			input.setProjectId(projectId);
			input.setProjectName(projectInfo.getProjectName());
			testCaseCountModel = new TestCaseCountModel();

			// System.out.println("22222222222"+projectInfo);

			hazelJson = new JSONObject(input);

			totalNoOFTestCases = getTotalNoOfTEstCases(projectInfo);

			hazelJson.put("testcaselistForEachScen", totalNoOFTestCases);
			hazelJson.put("projetName", projectInfo.getProjectName());

			testCaseCountModel.setTotalTestCaseCount(totalNoOFTestCases);

			List<ModuleDomain> modList = projectInfo.getModuleList();

			if (modList != null) {
				listCase = new ArrayList<>();
				for (ModuleDomain moduleDomain : modList) {

					List<RequirementDomain> reqList = moduleDomain.getRequirementList();
					if (reqList != null) {
						for (RequirementDomain requirementDomain : reqList) {

							List<TestScenarioDomain> testSceList = requirementDomain.getTestScenarioList();

							if (testSceList != null) {
								for (TestScenarioDomain testScenarioDomain : testSceList) {

									String projectName = projectInfo.getProjectName();
									String moduleName = moduleDomain.getModuleName();
									String requirementName = requirementDomain.getRequirementName();
									String testScenarioName = testScenarioDomain.getTestScenarioName();

									List<TestCaseDomain> testCaseDomainList = testScenarioDomain.getTestCaseList();
									TestCaseExecutionListDomain testCaseExecutionList = null;

									for (TestCaseDomain testCaseDomain : testCaseDomainList) {

										testResultResponseDomain = new TestResultResponseDomain();
										testResultResponseModel = new TestResultResponseModel();
										if (testCaseDomain.getExcecuteOrSkip().equalsIgnoreCase("Y")) {

											testCaseExecutionList = new TestCaseExecutionListDomain();

											List<TestStepExecutionListDomain> listdata = new ArrayList<TestStepExecutionListDomain>();
											input.setTestCaseDomain(testCaseDomain);

											String runner = testCaseDomain.getRunnerType();
											String plugin = null;
											for (String string : pluginObject.keySet()) {
												if (string.equals(runner)) {
													plugin = pluginObject.getString(runner);
												}
											}
											input.setPlugin(plugin);

											json = new JSONObject(input);

											resJSON = TestTransport
													.postRequestExec(readQutapProperties.getExcecutionPath(), json);

											System.out.println(
													"response after testCase Exection................................."
															+ resJSON);
											JSONArray jArray = resJSON.getJSONObject("TestCaseValues")
													.getJSONArray("TestResult");

											if (jArray != null) {
												for (int i = 0; i < jArray.length(); i++) {
													Gson gson = new Gson();
													/*
													 * if (jArray.getJSONObject(i).getString("status")
													 * .equalsIgnoreCase("fail")) {
													 * jArray.getJSONObject(i).put("screenShot",
													 * resJSON.getString("screenShot").toString()); }
													 */
													TestStepExecutionListDomain testStepExecutionValues = gson.fromJson(
															jArray.getJSONObject(i).toString(),
															TestStepExecutionListDomain.class);

													listdata.add(testStepExecutionValues);

												}
											}
											testCaseExecutionList.setTestResult(listdata);
											testCaseExecutionList.setTestCaseId(testCaseDomain.getTestCaseId());
											testCaseExecutionList.setTestCaseName(testCaseDomain.getTestCaseName());
											testCaseExecutionList
													.setTestCaseDescription(testCaseDomain.getTestCaseDesciption());
											testCaseExecutionList.setTestCaseTag(testCaseDomain.getTestCaseTag());
											testCaseExecutionList.setTestExecutionId(new ObjectId().toString());
											testCaseExecutionList.setStatus(resJSON.getString("status").toString());

											// --testCaseCountModel.setTestCaseCount(testCaseCount);
											// -- map.put("testCaseCountttt",testCaseCount);
											// -- map.put("statuss",resJSON.getString("status").toString());
											// testCaseCountModel.setStatus(resJSON.getString("status").toString());

											for (int i = 0; i < jArray.length(); i++) {
												if (jArray.getJSONObject(i).getString("status")
														.equalsIgnoreCase("fail")) {
													testCaseExecutionList.setTestCaseExecutionVideo(
															resJSON.getString("video").toString());
												}
											}

											testCaseExecutionList.setModuleName(moduleName);
											testCaseExecutionList.setRequirementName(requirementName);
											testCaseExecutionList.setDuration(
													DateUtility.getDuration(resJSON.getString("StartDateTime"),
															resJSON.getString("EndDateTime")));
											testCaseExecutionList
													.setTestCaseCategory(testCaseDomain.getTestCaseCategory());
											testCaseExecutionList
													.setTestCasePriority(testCaseDomain.getTestCasePriority());
											// System.out.println("LLLLLLLLLJKGUYFTYDFYYYYYYYYYY" +
											// testCaseExecutionList);
											listCase.add(testCaseExecutionList);

										}
										if (testCaseDomain.getExcecuteOrSkip().equalsIgnoreCase("N")) {
											System.out.println("TestCase data Skipped" + testCaseDomain);
											response = new Response();
										}

									}

								}

							}
						}

					}
				}
			}
			txnId++;
			testResultResponseDomain.setProjectId(projectInfoDomain.getProjectId());
			testResultResponseDomain.setProjectName(projectInfoDomain.getProjectName());
			testResultResponseDomain.setApiVersion(input.getApiVersion());
			testResultResponseDomain.setService(input.getService());
			testResultResponseDomain.setTestCaseExecutionList(listCase);
			testResultResponseDomain.setExecutionDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));
			// testResultResponseDomain.setTxnId(input.getTxnId());

			testResultResponseDomain.setTxnId(txnId + "");

			/*--	for (String entry : map.keySet()) {
					//--System.out.println(entry + "////////////////////////////////////////////////////////////// " + map.get(entry));
				}*/
//			testCaseCount=0;
//			System.out.println("count last.............."+testCaseCount);
//			map.put("testCaseCountttt",testCaseCount);

			testResultResponseDomain.setExecutedBy(userDao.getUserById(projectInfoDomain.getUserId()).getUserName());

			BeanUtils.copyProperties(testResultResponseDomain, testResultResponseModel);
			executionDao.executeData(testResultResponseDomain);

			return testResultResponseModel;
		} catch (Exception e) {

//			testCaseCount=0;
//			System.out.println("count last.............."+testCaseCount);
//			map.put("testCaseCountttt",testCaseCount);

			log.error("error in executing the test case", e);
			throw new ExecutionException("error in executing the test case");
		}

	}

}
