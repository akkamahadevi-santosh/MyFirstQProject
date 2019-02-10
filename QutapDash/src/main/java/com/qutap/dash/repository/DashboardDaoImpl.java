package com.qutap.dash.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.qutap.dash.commonUtils.DateUtility;
import com.qutap.dash.customException.ApplicationHealthException;
import com.qutap.dash.customException.ExecutionBreakDownException;
import com.qutap.dash.customException.OnBoardException;
import com.qutap.dash.customException.TestExecutionResponseException;
import com.qutap.dash.domain.ApplicationBuildHealthDomain;
import com.qutap.dash.domain.ApplicationDetailsDomain;
import com.qutap.dash.domain.ApplicationGroup;
import com.qutap.dash.domain.ApplicationList;
import com.qutap.dash.domain.ApplicationStatusReportDomain;
import com.qutap.dash.domain.ModuleDomain;
import com.qutap.dash.domain.ProjectInfoDomain;
import com.qutap.dash.domain.RadarDefectsDomain;
import com.qutap.dash.domain.RequirementDomain;
import com.qutap.dash.domain.TestCaseDomain;
import com.qutap.dash.domain.TestCaseExecutionListDomain;
import com.qutap.dash.domain.TestResultResponseDomain;
import com.qutap.dash.domain.TestScenarioDomain;
import com.qutap.dash.domain.TestStepExecutionListDomain;
import com.qutap.dash.domain.UserGroupsDomain;

@Repository
@Transactional
public class DashboardDaoImpl implements DashboardDao {

	org.slf4j.Logger log = LoggerFactory.getLogger(DashboardDaoImpl.class);

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	ProjectInfoDao projectInfoDao;

	@Override
	public List<ApplicationBuildHealthDomain> getApplicationHealth(String projectName) {
		try {
			// int totalTestSteps=0;

			Query query = new Query();
			query.addCriteria(Criteria.where("projectName").is(projectName));
			/*
			 * query.with(new Sort(Order.desc("txnId"))); query.limit(4);
			 */
			List<TestResultResponseDomain> responseValue = mongoTemplate.find(query, TestResultResponseDomain.class,
					"testExecution");
			List<ApplicationBuildHealthDomain> applicationBuildHealthDomainlist = new ArrayList();
			responseValue.sort((TestResultResponseDomain s1,
					TestResultResponseDomain s2) -> Integer.valueOf(s2.getTxnId()) - Integer.valueOf(s1.getTxnId()));
			int limit = 0;
			for (TestResultResponseDomain testResultResponseDomain : responseValue) {
				if (limit != 4) {
					limit++;
					ApplicationBuildHealthDomain applicationBuildHealthDomain = new ApplicationBuildHealthDomain();

					applicationBuildHealthDomain.setProjectName(testResultResponseDomain.getProjectName());
					long failedTest = 0;
					List<TestCaseExecutionListDomain> testCaseExecutionListDomain = testResultResponseDomain
							.getTestCaseExecutionList();
					for (TestCaseExecutionListDomain testCaseExecutionListDomainValue : testCaseExecutionListDomain) {
						if (testCaseExecutionListDomainValue.getStatus().equalsIgnoreCase("fail")) {
							failedTest++;
						} // if
					} // for
					applicationBuildHealthDomain.setBuildNumber("Build" + " " + testResultResponseDomain.getTxnId());
					applicationBuildHealthDomain.setFailed(failedTest);
					long testCaseCount = testResultResponseDomain.getTestCaseExecutionList().size();
					applicationBuildHealthDomain.setTotatTests(testCaseCount);

					long percentage = (((failedTest) * 100) / testCaseCount);
					applicationBuildHealthDomain.setPercentage(percentage + "%");
					if (percentage >= 76 && percentage <= 100) {
						applicationBuildHealthDomain.setColourCode("#FF0F00");
						applicationBuildHealthDomain.setColourName("Dark Red");
					} else if (percentage >= 51 && percentage <= 75) {
						applicationBuildHealthDomain.setColourCode("#FF6600");
						applicationBuildHealthDomain.setColourName("Orange");
					} else if (percentage >= 26 && percentage <= 50) {
						applicationBuildHealthDomain.setColourCode("#FF9E01");
						applicationBuildHealthDomain.setColourName("Yellow");
					} else if (percentage >= 1 && percentage <= 25) {
						applicationBuildHealthDomain.setColourCode("#90EE90");
						applicationBuildHealthDomain.setColourName("Light Green");
					} else if (percentage <= 0) {
						applicationBuildHealthDomain.setColourCode("#28a745");
						applicationBuildHealthDomain.setColourName("Green");
					} // if
					applicationBuildHealthDomainlist.add(applicationBuildHealthDomain);

				}
			}
			return applicationBuildHealthDomainlist;
		} catch (Exception e) {

			log.error("error getting lastFourBuild applicationHealth of individualApp  data", e);
			throw new ApplicationHealthException(
					"error getting lastFourBuild applicationHealth of individualApp  data");
		}

	}

	@Override
	public List<ApplicationStatusReportDomain> getexecutionBreakDownWeekly(String projectName) {
		try {
			List<ApplicationStatusReportDomain> applicationStatusReportDomainList = new ArrayList<>();
			LocalDateTime localDateTime = LocalDateTime.now();
			Date previous7Days = null;
			Date currentDate = null;
			for (int i = 0; i <= 3; i++) {
				if (i == 0) {
					previous7Days = DateUtility.getStringToDate(
							DateUtility.getPrevious7Days(DateUtility.FORMAT_1, localDateTime), DateUtility.FORMAT_1);

					currentDate = DateUtility.getStringToDate1(new Date(), DateUtility.FORMAT_1);

				} else {
					localDateTime = localDateTime.minusDays(7);
					previous7Days = DateUtility.getStringToDate(
							DateUtility.getPrevious7Days(DateUtility.FORMAT_1, localDateTime), DateUtility.FORMAT_1);

					currentDate = DateUtility.getStringToDate2(localDateTime, DateUtility.FORMAT_1);
				}
				String days7 = DateUtility.getDate(previous7Days, DateUtility.FORMAT_1);
				String current = DateUtility.getDate(currentDate, DateUtility.FORMAT_1);
				List<ApplicationStatusReportDomain> ApplicationStatusReportDomain = getValue(projectName, days7,
						current, i);
				for (ApplicationStatusReportDomain applicationStatusReportDomain : ApplicationStatusReportDomain) {

					applicationStatusReportDomainList.add(applicationStatusReportDomain);
				}
			}
			return applicationStatusReportDomainList;
		} catch (Exception e) {

			log.error("error getting  getexecutionBreakDownWeekly  data", e);
			throw new ExecutionBreakDownException("error getting getexecutionBreakDownWeekly  data");
		}

	}

	public List<ApplicationStatusReportDomain> getValue(String projectName, String previous7days, String currentDate,
			int i) {
		try {

			Query query = new Query();
			query.addCriteria(Criteria.where("projectName").is(projectName));
			List<ApplicationStatusReportDomain> applicationStatusReportDomainList = new ArrayList<ApplicationStatusReportDomain>();

			List<TestResultResponseDomain> testResultResponseDomain = mongoTemplate.find(query,
					TestResultResponseDomain.class, "testExecution");
			Date currentDateValueToCompare = DateUtility.getStringToDate(DateUtility.AddingDaysDate(currentDate),
					DateUtility.FORMAT_1);
			Date previous7daysdateValuesToCompare = DateUtility.getStringToDate(previous7days, DateUtility.FORMAT_1);

			testResultResponseDomain.sort((TestResultResponseDomain s1,
					TestResultResponseDomain s2) -> Integer.valueOf(s2.getTxnId()) - Integer.valueOf(s1.getTxnId()));
			ApplicationStatusReportDomain applicationStatusReportDomain = null;
			boolean inside = false;
			if (!(testResultResponseDomain.isEmpty())) {
				for (TestResultResponseDomain testResultResponseDomainValue : testResultResponseDomain) {
					Date dateValueToCompare = DateUtility
							.getStringToDate(testResultResponseDomainValue.getExecutionDate(), DateUtility.FORMAT_1);
					if (inside != true) {
						if ((dateValueToCompare.after(previous7daysdateValuesToCompare)
								&& (dateValueToCompare.before(currentDateValueToCompare)))) {
							inside = true;
							Set<String> addApplication = new HashSet<>();
							applicationStatusReportDomain = new ApplicationStatusReportDomain();
							applicationStatusReportDomain.setStartDate(previous7days);
							applicationStatusReportDomain.setEndDate(currentDate);
							applicationStatusReportDomain
									.setBuildNumber("Build" + " " + testResultResponseDomainValue.getTxnId());
							applicationStatusReportDomain
									.setApplicationName(testResultResponseDomainValue.getProjectName());
							applicationStatusReportDomain
									.setWeek("Week" + " " + DateUtility.getWeekofYear(currentDate));
							List<TestCaseExecutionListDomain> testCaseExecutionListDomainList = testResultResponseDomainValue
									.getTestCaseExecutionList();
							long testCount = 0;
							long passCount = 0;
							long failCount = 0;
							long skipCount = 0;
							for (TestCaseExecutionListDomain testCaseExecutionListDomain : testCaseExecutionListDomainList) {
								if (testCaseExecutionListDomain.getStatus().equalsIgnoreCase("PASS")) {

									passCount++;
								} else if (testCaseExecutionListDomain.getStatus().equalsIgnoreCase("FAIL")) {

									failCount++;
								} else {

									skipCount++;
								}
							}

							addApplication.add(testResultResponseDomainValue.getProjectName());
							applicationStatusReportDomain.setPassed(passCount);
							applicationStatusReportDomain.setFailed(failCount);
							applicationStatusReportDomain.setSkipped(skipCount);
							applicationStatusReportDomain.setApplicationCount(addApplication.size());
							applicationStatusReportDomain.setTotatTests(testCaseExecutionListDomainList.size());
							applicationStatusReportDomainList.add(applicationStatusReportDomain);
						}

					}
				}
				if (inside == false) {
					applicationStatusReportDomain = new ApplicationStatusReportDomain();
					Calendar cl = Calendar.getInstance();
					applicationStatusReportDomain
							.setWeek("Week" + " " + Integer.toString(cl.get(Calendar.WEEK_OF_YEAR) - i));
					applicationStatusReportDomainList.add(applicationStatusReportDomain);
				}
			} else {
				applicationStatusReportDomain = new ApplicationStatusReportDomain();
				Calendar cl = Calendar.getInstance();
				applicationStatusReportDomain
						.setWeek("Week" + " " + Integer.toString(cl.get(Calendar.WEEK_OF_YEAR) - i));
				applicationStatusReportDomainList.add(applicationStatusReportDomain);
			}
			return applicationStatusReportDomainList;
		} catch (Exception e) {
			log.error("error getting  getexecutionBreakDownWeekly  data", e);
			throw new ExecutionBreakDownException("error getting getexecutionBreakDownWeekly  data");
		}
	}

	@Override
	public List<ApplicationStatusReportDomain> getexecutionBreakDownLastFourBuild(String projectName) {
		try {
			LocalDateTime localDateTime = LocalDateTime.now();

			Date previous30Days = DateUtility.getStringToDate(
					DateUtility.getPrevious30Days(DateUtility.FORMAT_1, localDateTime), DateUtility.FORMAT_1);

			Date currentDate = DateUtility.getStringToDate1(new Date(), DateUtility.FORMAT_1);
			String days30 = DateUtility.getDate(previous30Days, DateUtility.FORMAT_1);
			String current = DateUtility.getDate(currentDate, DateUtility.FORMAT_1);
			String tomarrowDate = DateUtility.getTommarowDate();
			Date currentDateValueToCompare = DateUtility.getStringToDate(tomarrowDate, DateUtility.FORMAT_1);
			Date previous30daysdateValuesToCompare = DateUtility.getStringToDate(days30, DateUtility.FORMAT_1);
			Query query = new Query();

			query.addCriteria(Criteria.where("projectName").is(projectName));
			List<ApplicationStatusReportDomain> applicationStatusReportDomainList = new ArrayList<ApplicationStatusReportDomain>();
			List<TestResultResponseDomain> testResultResponseDomain = mongoTemplate.find(query,
					TestResultResponseDomain.class, "testExecution");
			testResultResponseDomain.sort((TestResultResponseDomain s1,
					TestResultResponseDomain s2) -> Integer.valueOf(s2.getTxnId()) - Integer.valueOf(s1.getTxnId()));
			int limit = 0;
			if (!testResultResponseDomain.isEmpty()) {
				for (TestResultResponseDomain testResultResponseDomainValue : testResultResponseDomain) {
					Date dateValueToCompare = DateUtility
							.getStringToDate(testResultResponseDomainValue.getExecutionDate(), DateUtility.FORMAT_1);
					if (limit != 4) {
						if ((dateValueToCompare.after(previous30daysdateValuesToCompare)
								&& (dateValueToCompare.before(currentDateValueToCompare)))) {
							limit++;
							ApplicationStatusReportDomain applicationStatusReportDomain = new ApplicationStatusReportDomain();
							applicationStatusReportDomain
									.setBuildNumber("Build" + " " + testResultResponseDomainValue.getTxnId());
							applicationStatusReportDomain.setApplicationName(projectName);
							applicationStatusReportDomain.setStartDate(days30);
							applicationStatusReportDomain.setEndDate(current);
							applicationStatusReportDomain.setWeek("Week" + " " + DateUtility.getWeekofYear(days30));
							List<TestCaseExecutionListDomain> testCaseExecutionListDomainList = testResultResponseDomainValue
									.getTestCaseExecutionList();
							long testCount = 0;
							long passCount = 0;
							long failCount = 0;
							long skipCount = 0;

							for (TestCaseExecutionListDomain testCaseExecutionListDomain : testCaseExecutionListDomainList) {
								if (testCaseExecutionListDomain.getStatus().equalsIgnoreCase("PASS")) {

									passCount++;
								} else if (testCaseExecutionListDomain.getStatus().equalsIgnoreCase("FAIL")) {

									failCount++;
								} else {

									skipCount++;
								}
							}

							applicationStatusReportDomain.setPassed(passCount);
							applicationStatusReportDomain.setFailed(failCount);
							applicationStatusReportDomain.setSkipped(skipCount);
							applicationStatusReportDomain.setTotatTests(testCaseExecutionListDomainList.size());
							applicationStatusReportDomainList.add(applicationStatusReportDomain);
						}
					}
				}
			}
			return applicationStatusReportDomainList;
		} catch (Exception e) {

			log.error("error getting lastFourBuild  of individualApp  data", e);
			throw new ExecutionBreakDownException("error getting lastFourBuild  of individualApp  data");
		}
	}

//========
	@Override
	public List<TestResultResponseDomain> getLastBuildTestExecution(String projectName) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("projectName").is(projectName));

			query.with(new Sort(Order.desc("txnId")));
			query.limit(1);
			List<TestResultResponseDomain> testResultResponseDomain = mongoTemplate.find(query,
					TestResultResponseDomain.class, "testExecution");
			return testResultResponseDomain;
		} catch (Exception e) {

			log.error("error getting in LastBuildTestExecution", e);
			throw new TestExecutionResponseException("error getting in LastBuildTestExecution");
		}
	}

	@Override
	public List<TestResultResponseDomain> getAllTestExecution(String projectName) {

		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("projectName").is(projectName));

			query.with(new Sort(Order.desc("txnId")));

			List<TestResultResponseDomain> testResultResponseDomain = mongoTemplate.find(query,
					TestResultResponseDomain.class, "testExecution");
			return testResultResponseDomain;
		} catch (Exception e) {

			log.error("error getting in AllTestExecution", e);
			throw new TestExecutionResponseException("error getting in AllTestExecution");
		}
	}

	@Override
	 public ApplicationStatusReportDomain getexecutionBreakDownLastBuild(String projectName) {
	 try {
	 Query query = new Query();
	 query.addCriteria(Criteria.where("projectName").is(projectName));
	 query.with(new Sort(Order.desc("txnId")));
	 query.limit(1);
	 ApplicationStatusReportDomain  applicationStatusReportDomain=null;
	 TestResultResponseDomain testResultResponseDomain = mongoTemplate.findOne(query,TestResultResponseDomain.class, "testExecution");

	 if (testResultResponseDomain==null) {

	 System.out.println(projectName +" Application is not executed....");
	 applicationStatusReportDomain=new ApplicationStatusReportDomain();
	 applicationStatusReportDomain.setApplicationName(projectName);
	 applicationStatusReportDomain.setPassed(0);
	 applicationStatusReportDomain.setFailed(0);
	 applicationStatusReportDomain.setSkipped(0);
	 applicationStatusReportDomain.setTotatTests(0);
	 return applicationStatusReportDomain;
	 }

	 else{
	 Set<String> addApplication=new HashSet<>();
	 addApplication.add(testResultResponseDomain.getProjectName());
	 applicationStatusReportDomain=new ApplicationStatusReportDomain();
	 applicationStatusReportDomain.setApplicationName(testResultResponseDomain.getProjectName());
	 applicationStatusReportDomain.setBuildNumber("Build"+" "+testResultResponseDomain.getTxnId());

	 applicationStatusReportDomain.setApplicationCount(addApplication.size());

	 List<TestCaseExecutionListDomain> TestCaseExecutionListDomainList=testResultResponseDomain.getTestCaseExecutionList();
	 long testCount = 0;
	 long passCount = 0;
	 long failCount = 0;
	 long skipCount = 0;
	 for(TestCaseExecutionListDomain testCaseExecutionListDomain:TestCaseExecutionListDomainList) {
	 /*testCount++;  

	 for(TestStepExecutionListDomain testStepExecutionListDomain:testCaseExecutionListDomain.getTestResult()) { */

	 if (testCaseExecutionListDomain.getStatus().equalsIgnoreCase("PASS")) {

	 passCount++;
	 } else if (testCaseExecutionListDomain.getStatus().equalsIgnoreCase("FAIL")) {

	 failCount++;
	 } else {

	 skipCount++;
	 }
	 }


	 applicationStatusReportDomain.setPassed(passCount);
	 applicationStatusReportDomain.setFailed(failCount);
	 applicationStatusReportDomain.setSkipped(skipCount);
	 applicationStatusReportDomain.setTotatTests(TestCaseExecutionListDomainList.size());

	 return applicationStatusReportDomain;

	 }


	 }catch (Exception e) {

	 log.error("error getting executionBreakDownLastBuild  of individualApp  data", e);
	 throw new ExecutionBreakDownException("error getting executionBreakDownLastBuild  of individualApp  data");
	 }
	 }
	
	
	@Override
	public List<RadarDefectsDomain> getOpenDefectsFromRadar(String componentName) {
		String responseString = null;

		RadarDefectsDomain radarDefects = new RadarDefectsDomain();
		List<RadarDefectsDomain> radarDefectsList = new ArrayList<>();
		try {

			responseString = "[{ priority:1 },{ priority:2 },{ priority:2 },{ priority:3 },{ priority:3 },{ priority:3 },{ priority:3 },{ priority:4 },{ priority:4 },{ priority:4 }]";

			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(responseString);
			JsonArray jsonArray = jsonElement.getAsJsonArray();
			int p1Counter = 0;
			int p2Counter = 0;
			int p3Counter = 0;
			int p4Counter = 0;

			for (JsonElement jsonElement2 : jsonArray) {
				if (jsonElement2.getAsJsonObject().get("priority").getAsString().equalsIgnoreCase("1")) {

					p1Counter++;
				}
				if (jsonElement2.getAsJsonObject().get("priority").getAsString().equalsIgnoreCase("2")) {
					p2Counter++;
				}
				if (jsonElement2.getAsJsonObject().get("priority").getAsString().equalsIgnoreCase("3")) {

					p3Counter++;
				}
				if (jsonElement2.getAsJsonObject().get("priority").getAsString().equalsIgnoreCase("4")) {

					p4Counter++;
				}

			}

			radarDefects = new RadarDefectsDomain();
			radarDefects.setPriority("P1");
			radarDefects.setValue(p1Counter);
			radarDefectsList.add(radarDefects);
			radarDefects = new RadarDefectsDomain();
			radarDefects.setPriority("P2");
			radarDefects.setValue(p2Counter);
			radarDefectsList.add(radarDefects);
			radarDefects = new RadarDefectsDomain();
			radarDefects.setPriority("P3");
			radarDefects.setValue(p3Counter);
			radarDefectsList.add(radarDefects);
			radarDefects = new RadarDefectsDomain();
			radarDefects.setPriority("P4");
			radarDefects.setValue(p4Counter);
			radarDefectsList.add(radarDefects);

		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}

		if (radarDefectsList.isEmpty()) {
			radarDefects.setErrorMessage("There are no Defects for this component : " + componentName + " ");
			radarDefectsList.add(radarDefects);
		}

		return radarDefectsList;

	}

	@Override
	public String getVideoPath(String txnId, String testCaseId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("testCaseExecutionList.testCaseId").is(testCaseId)
				.andOperator(Criteria.where("txnId").is(txnId)));
		TestResultResponseDomain testResultResponseDomain = mongoTemplate.findOne(query, TestResultResponseDomain.class,
				"testExecution");
		List<TestCaseExecutionListDomain> testCaseList = testResultResponseDomain.getTestCaseExecutionList();
		if (testCaseList != null) {
			TestCaseExecutionListDomain testCase = testCaseList.stream()
					.filter(i -> i.getTestCaseId().equalsIgnoreCase(testCaseId)).collect(Collectors.toList()).get(0);

			String path = testCase.getTestCaseExecutionVideo();

			return path;
		}
		return null;
	}

	@Override
	public String getScreenShotPath(String txnId, String testCaseId, String testStepId) {

		Query query = new Query();
		query.addCriteria(Criteria.where("testCaseExecutionList.testCaseId").is(testCaseId)
				.andOperator(Criteria.where("txnId").is(txnId)));
		TestResultResponseDomain testResultResponseDomain = mongoTemplate.findOne(query, TestResultResponseDomain.class,
				"testExecution");

		List<TestCaseExecutionListDomain> testCaseList = testResultResponseDomain.getTestCaseExecutionList();
		TestStepExecutionListDomain testStep = null;
		if (testCaseList != null) {
			TestCaseExecutionListDomain testCase = testCaseList.stream()
					.filter(i -> i.getTestCaseId().equalsIgnoreCase(testCaseId)).collect(Collectors.toList()).get(0);
			List<TestStepExecutionListDomain> testStepList = testCase.getTestResult();
			if (testStepList != null) {
				testStep = testStepList.stream().filter(i -> i.getTestStepId().equalsIgnoreCase(testStepId))
						.collect(Collectors.toList()).get(0);

				String path = testStep.getScreenShot();

				return path;

			}

		}
		return null;
	}

	@Override
	public String getLastTransactionResult(String projectName) {

		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("projectName").is(projectName));
			query.with(new Sort(Order.desc("txnId")));
			query.limit(1);
			TestResultResponseDomain testResultResponseDomain = mongoTemplate.findOne(query,
					TestResultResponseDomain.class, "testExecution");
			if (testResultResponseDomain == null) {
				System.out.println("returining tx id 0");
				return "0";
			}
			System.out.println("dao tx id................." + testResultResponseDomain.getTxnId());
			return testResultResponseDomain.getTxnId();
		} catch (Exception e) {

			log.error("error getting in lastFourTestExecution", e);
			throw new TestExecutionResponseException("error getting in lastFourTestExecution");
		}
	}

	@Override
	public List<ApplicationBuildHealthDomain> getAllApplicationHealth(List<String> projectNameList) {
		try {

			List<ApplicationBuildHealthDomain> allApplicationBuildHealthDomainlist = new ArrayList();

			for (String projectName : projectNameList) {
				Query query = new Query();
				query.addCriteria(Criteria.where("projectName").is(projectName));
				query.with(new Sort(Order.desc("txnId")));
				query.limit(1);
				TestResultResponseDomain testResultResponseDomain = mongoTemplate.findOne(query,
						TestResultResponseDomain.class, "testExecution");
				ApplicationBuildHealthDomain applicationBuildHealthDomain = new ApplicationBuildHealthDomain();
				applicationBuildHealthDomain.setProjectName(testResultResponseDomain.getProjectName());
				long failedTest = 0;
				List<TestCaseExecutionListDomain> testCaseExecutionListDomain = testResultResponseDomain
						.getTestCaseExecutionList();
				for (TestCaseExecutionListDomain testCaseExecutionListDomainValue : testCaseExecutionListDomain) {
					if (testCaseExecutionListDomainValue.getStatus().equalsIgnoreCase("fail")) {
						failedTest++;
					}
				}
				applicationBuildHealthDomain.setBuildNumber("Build" + " " + testResultResponseDomain.getTxnId());
				applicationBuildHealthDomain.setFailed(failedTest);
				long testCaseCount = testResultResponseDomain.getTestCaseExecutionList().size();
				applicationBuildHealthDomain.setTotatTests(testCaseCount);

				long percentage = (((failedTest) * 100) / testCaseCount);
				applicationBuildHealthDomain.setPercentage(percentage + "%");
				if (percentage >= 76 && percentage <= 100) {
					applicationBuildHealthDomain.setColourCode("#FF0F00");
					applicationBuildHealthDomain.setColourName("Dark Red");
				} else if (percentage >= 51 && percentage <= 75) {
					applicationBuildHealthDomain.setColourCode("#FF6600");
					applicationBuildHealthDomain.setColourName("Orange");
				} else if (percentage >= 26 && percentage <= 50) {
					applicationBuildHealthDomain.setColourCode("#FF9E01");
					applicationBuildHealthDomain.setColourName("Yellow");
				} else if (percentage >= 1 && percentage <= 25) {
					applicationBuildHealthDomain.setColourCode("#90EE90");
					applicationBuildHealthDomain.setColourName("Light Green");
				} else if (percentage <= 0) {
					applicationBuildHealthDomain.setColourCode("#28a745");
					applicationBuildHealthDomain.setColourName("Green");
				}

				allApplicationBuildHealthDomainlist.add(applicationBuildHealthDomain);

			}

			return allApplicationBuildHealthDomainlist;
		} catch (Exception e) {

			log.error("error getting lastBuild of all applicationHealth of individualApp  data", e);
			throw new ApplicationHealthException(
					"error getting lastBuild of all applicationHealth of individualApp  data");
		}
	}

	/**
	 * Service for getting all application details for dashboard bubbleChart
	 */

	@Override
	public List<ApplicationDetailsDomain> getAllApplicationDetails(String dsid) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("dsid").is(dsid));
			UserGroupsDomain userIdmsDataDomain = mongoTemplate.findOne(query, UserGroupsDomain.class, "userGroups");

			List<ApplicationDetailsDomain> allApplicationdetails = new ArrayList<ApplicationDetailsDomain>();

			List<ApplicationGroup> applicationGroup = userIdmsDataDomain.getGrouplist();
			for (ApplicationGroup group : applicationGroup) {
				List<ApplicationList> application = group.getApplicationList();

				long FT = 0;
				long NFT = 0;

				for (ApplicationList list : application) {
					ApplicationDetailsDomain appDetails = new ApplicationDetailsDomain();
					String applicationId = list.getApplicationId();
					String applicationName = list.getApplicationName();
					appDetails.setApplicationId(applicationId);
					appDetails.setApplicationName(applicationName);
					appDetails.setOnBoard(list.isOnBoard());

					ProjectInfoDomain projectInfoDomain = projectInfoDao.getProjectInfoById(applicationId);

					long totalNoOfTestCases = getTotalNoOfTEstCases(projectInfoDomain);
					appDetails.setTotalTestCases(totalNoOfTestCases);

					ApplicationBuildHealthDomain applicationBuildHealthDomain = getApplicationHealthLastBuild(
							applicationName);
					System.out.println(
							"applnbuildhealthdomain in getApplicationDetails::::::;" + applicationBuildHealthDomain);

					String perc = applicationBuildHealthDomain.getPercentage();
					System.out.println("percentage" + perc);
					appDetails.setPercentage(perc);

					// ---
					if (FT > 100 || NFT > 100) {
						FT = 0;
						NFT = 0;
					}

					FT = FT + 10;
					NFT = NFT + 5;
					appDetails.setFT(FT);
					appDetails.setNFT(NFT);

					allApplicationdetails.add(appDetails);
				}

			}

			return allApplicationdetails;
		} catch (Exception e) {
			log.error("error in getting getApplicationNames details", e);
			throw new OnBoardException("error in getting getApplicationNames details");

		}

	}

	public long getTotalNoOfTEstCases(ProjectInfoDomain projectInfo)

	{
		System.out.println("getTotalNoOfTEstCases(ProjectInfoDomain projectInfo  from DashboardServiceImpl)");
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

	// ---

	public ApplicationBuildHealthDomain getApplicationHealthLastBuild(String projectName) {
		 try {
		 // int totalTestSteps=0;

		 Query query = new Query();
		 query.addCriteria(Criteria.where("projectName").is(projectName)); 
		 query.with(new Sort(Order.desc("txnId")));
		 query.limit(1);
		 TestResultResponseDomain testResultResponseDomain=mongoTemplate.findOne(query,TestResultResponseDomain.class, "testExecution");
		 ApplicationBuildHealthDomain applicationBuildHealthDomain = new ApplicationBuildHealthDomain();

		 if(testResultResponseDomain == null)
		 {
		 System.out.println(projectName+ " Application is not tested...");
		 applicationBuildHealthDomain = new ApplicationBuildHealthDomain();
		 long percentage =0;
		 applicationBuildHealthDomain.setPercentage(percentage + "%");
		 return applicationBuildHealthDomain;

		 }

		 else {
		 applicationBuildHealthDomain = new ApplicationBuildHealthDomain();
		 System.out.println("testResultResponseDomain::"+testResultResponseDomain);

		 applicationBuildHealthDomain.setProjectName(testResultResponseDomain.getProjectName());
		 long failedTest = 0;
		 List<TestCaseExecutionListDomain> testCaseExecutionListDomain = testResultResponseDomain
		 .getTestCaseExecutionList();
		 for (TestCaseExecutionListDomain testCaseExecutionListDomainValue : testCaseExecutionListDomain) {
		 if (testCaseExecutionListDomainValue.getStatus().equalsIgnoreCase("fail")) {
		 failedTest++;
		 }//if
		 }//for
		 applicationBuildHealthDomain.setBuildNumber("Build"+" "+testResultResponseDomain.getTxnId());
		 applicationBuildHealthDomain.setFailed(failedTest);
		 long testCaseCount = testResultResponseDomain.getTestCaseExecutionList().size();
		 applicationBuildHealthDomain.setTotatTests(testCaseCount);

		 long percentage = (((failedTest) * 100) / testCaseCount);
		 applicationBuildHealthDomain.setPercentage(percentage + "%");

		 if (percentage >= 76 && percentage <= 100) {
		 applicationBuildHealthDomain.setColourCode("#FF0F00");
		 applicationBuildHealthDomain.setColourName("Dark Red");
		 } else if (percentage >= 51 && percentage <= 75) {
		 applicationBuildHealthDomain.setColourCode("#FF6600");
		 applicationBuildHealthDomain.setColourName("Orange");
		 } else if (percentage >= 26 && percentage <= 50) {
		 applicationBuildHealthDomain.setColourCode("#FF9E01");
		 applicationBuildHealthDomain.setColourName("Yellow");
		 } else if (percentage >= 1 && percentage <= 25) {
		 applicationBuildHealthDomain.setColourCode("#90EE90");
		 applicationBuildHealthDomain.setColourName("Light Green");
		 } else if (percentage <= 0) {
		 applicationBuildHealthDomain.setColourCode("#28a745");
		 applicationBuildHealthDomain.setColourName("Green");
		 }//if
		 //applicationBuildHealthDomainlist.add(applicationBuildHealthDomain);

		 System.out.println("applicationBuildHealthDomain :::"+applicationBuildHealthDomain);

		 }

		 return applicationBuildHealthDomain;

		 } catch (Exception e) {

		 log.error("error getting getApplicationHealthLastBuild  data", e);

		 throw new ApplicationHealthException(
		 "error getting getApplicationHealthLastBuild  data");
		 }

		 }

}