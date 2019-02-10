package com.qutap.dash.service;



import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.qutap.dash.commonUtils.DateUtility;
import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.commonUtils.StatusCode;
import com.qutap.dash.config.LoadConfiguration;
import com.qutap.dash.config.ReadQutapProperties;
import com.qutap.dash.customException.TestCaseException;
import com.qutap.dash.customException.TestSuiteException;
import com.qutap.dash.domain.ModuleDomain;
import com.qutap.dash.domain.ProjectInfoDomain;
import com.qutap.dash.domain.RequirementDomain;
import com.qutap.dash.domain.TestCaseDomain;
import com.qutap.dash.domain.TestScenarioDomain;
import com.qutap.dash.domain.TestStepDomain;
import com.qutap.dash.domain.TestSuiteDomain;
import com.qutap.dash.model.AgentMeta;

import com.qutap.dash.model.TestCaseModel;
import com.qutap.dash.repository.ExcelDataDao;
import com.qutap.dash.repository.ModuleDao;
import com.qutap.dash.repository.ProjectInfoDao;
import com.qutap.dash.repository.RequirementDao;
import com.qutap.dash.repository.TestCaseDao;
import com.qutap.dash.repository.TestScenarioDao;
import com.qutap.dash.repository.TestStepDao;
import com.qutap.dash.repository.TestSuiteDao;

@Service
public class ExcelDataServiceImpl implements ExcelDataService {

	Logger log = LoggerFactory.getLogger(ExcelDataServiceImpl.class);

	@Autowired
	ExcelDataDao excelDataDao;

	@Autowired
	ProjectInfoDao projectInfoDao;

	@Autowired
	ModuleDao moduleDao;
	@Autowired
	RequirementDao requirementDao;

	@Autowired
	TestScenarioDao testScenarioDao;

	@Autowired
	TestCaseDao testCaseDao;

	@Autowired
	TestStepDao testStepDao;
	@Autowired
	ReadQutapProperties readProperties;

	
	@Autowired
	TestSuiteDao testSuiteDao;
	
	
	Response response = new Response();

	private static long txnId;
	private static final String TEST_CASE_END = "TC_END";
	ModuleDomain moduleDomain;

	TestScenarioDomain testScenarioDomain;
	TestCaseDomain testCaseDomain;

	@Override
	public Response readExcelData(MultipartFile multipartFile,String projectName,@PathVariable String testSuiteName) {

		try {
			try {
				if (readProperties.getExcelPath() != null && !"".equals(readProperties.getExcelPath().trim())) {
					File dirFileObj = FileUtils.getFile(readProperties.getExcelPath());
					if (!dirFileObj.exists()) {
						FileUtils.forceMkdir(dirFileObj);
					}
					byte[] bytes = multipartFile.getBytes();
					Path path = Paths.get(readProperties.getExcelPath() + "/" + multipartFile.getOriginalFilename());
					Files.write(path, bytes);
				}
			} catch (IOException e) {
				log.error("error in creating directory", e);
				throw new TestCaseException("error in creating dirctory");
			}

			Workbook workbook = WorkbookFactory.create(multipartFile.getInputStream());
			int numberOfSheets = workbook.getNumberOfSheets();
			String testCaseValue;

			ProjectInfoDomain projectInfoDomain = new ProjectInfoDomain();

			ModuleDomain moduleDomain = null;
			RequirementDomain requirementDomain = null;

			Row row;

			TestStepDomain testStepDomain;
			DataFormatter dataFormatter = new DataFormatter();

		//--	for (int i = 0; i < numberOfSheets; i++) {
				
				Sheet sheet = workbook.getSheetAt(0);
				Iterator<Row> rowIterator = sheet.rowIterator();
				 projectInfoDomain=projectInfoDao.getProjectInfoByName(projectName) ;
				 System.out.println("projectInfoDomain........"+               projectInfoDomain);
	                TestSuiteDomain testSuiteDomain= testSuiteDao.getTestSuiteByName(projectInfoDomain.getProjectId(), testSuiteName);
	                System.out.println("testSuiteDomain.............."+testSuiteDomain);
	                if(testSuiteDomain == null)
	                {
	                	
	                	   testSuiteDomain= new TestSuiteDomain(); 
	                	   testSuiteDomain.setTestSuiteId(new ObjectId().toString());
	                	   testSuiteDomain.setTestSuiteName(testSuiteName);
	                	   testSuiteDomain.setProjectId(projectInfoDomain.getProjectId()); 
	                	   testSuiteDomain.setCreatedDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));
	                	     List<String> testCaseIdList= new ArrayList<String>();
				while (rowIterator.hasNext()) {
					row = rowIterator.next();

					int rownum = row.getRowNum();

					if (rownum >= 6) {

						//--if (!((dataFormatter.formatCellValue(row.getCell(0)).isEmpty()))) {
                //--   projectInfoDomain=projectInfoDao.getProjectInfoByName(dataFormatter.formatCellValue(row.getCell(0))) ;
			
							if (projectInfoDomain== null) {
							
								projectInfoDomain = new ProjectInfoDomain();
								projectInfoDomain.setProjectId(new ObjectId().toString());
								projectInfoDomain.setProjectName(dataFormatter.formatCellValue(row.getCell(0)));
								projectInfoDomain.setModuleList(new ArrayList<ModuleDomain>());
								projectInfoDomain.setCreatedDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));
								projectInfoDao.saveProjectInfo(projectInfoDomain);

							}
				

						if (!((dataFormatter.formatCellValue(row.getCell(1)).isEmpty()))) {
							moduleDomain=moduleDao.getModuleByNameOnly(dataFormatter.formatCellValue(row.getCell(1)),projectInfoDomain.getProjectId());
							if (moduleDomain == null) {
									

								List<ModuleDomain> moduleDomainList = new ArrayList<>();

								moduleDomain = new ModuleDomain();
								moduleDomain.setModuleId(new ObjectId().toString());
								moduleDomain.setModuleName(dataFormatter.formatCellValue(row.getCell(1)));
								moduleDomain.setRequirementList(new ArrayList<RequirementDomain>());
								moduleDomain.setCreatedDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));
								moduleDomainList.add(moduleDomain);
								moduleDao.saveModule(moduleDomainList, projectInfoDomain.getProjectId());
							}
							
						}
						if (!((dataFormatter.formatCellValue(row.getCell(2)).isEmpty()))) {
							requirementDomain = requirementDao.getRequirementByNameOnly(dataFormatter.formatCellValue(row.getCell(2)), moduleDomain.getModuleId());

						
							if ( requirementDomain== null) {

								List<RequirementDomain> requirementDomainList = new ArrayList<>();
								requirementDomain = new RequirementDomain();
								requirementDomain.setRequirementId(new ObjectId().toString());
								requirementDomain.setRequirementName(dataFormatter.formatCellValue(row.getCell(2)));
								requirementDomain.setTestScenarioList(new ArrayList<TestScenarioDomain>());
								requirementDomain.setCreatedDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));
								requirementDomainList.add(requirementDomain);
								requirementDao.saveRequirement(requirementDomainList, moduleDomain.getModuleId(),
										projectInfoDomain.getProjectId());
							}
						}
						if (!((dataFormatter.formatCellValue(row.getCell(3)).isEmpty()))) {

							testScenarioDomain = testScenarioDao.getTestScenarioByNameOnly(dataFormatter.formatCellValue(row.getCell(3)),
									requirementDomain.getRequirementId());
							
							System.out.println("testScenarioDomain while uploading..............."+testScenarioDomain);
							if (testScenarioDomain == null) {
								System.out.println("save scenarion when not exist .........testExecutionServiceImpl............. ");

								List<TestScenarioDomain> testScenarioDomainList = new ArrayList<>();
								testScenarioDomain = new TestScenarioDomain();
								testScenarioDomain.setTestScenarioId(new ObjectId().toString());
								testScenarioDomain.setTestScenarioName(dataFormatter.formatCellValue(row.getCell(3)));
								testScenarioDomain.setTestCaseList(new ArrayList<TestCaseDomain>());
								testScenarioDomain
										.setCreatedDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));
								testScenarioDomainList.add(testScenarioDomain);
								testScenarioDao.saveTestScenario(testScenarioDomainList,
										requirementDomain.getRequirementId(), moduleDomain.getModuleId(),
										projectInfoDomain.getProjectId());
							}
						}
						if (!((dataFormatter.formatCellValue(row.getCell(5)).isEmpty()))) {
							testCaseDomain = testCaseDao
									.getTestCaseByIdOnly(dataFormatter.formatCellValue(row.getCell(5)),testScenarioDomain.getTestScenarioId());
							System.out.println("testCaseDomain not present in particular Scenario............."+testCaseDomain);
							if (testCaseDomain != null) {
								
								System.out.println("updating  testCase when exist .........testExecutionServiceImpl............. ");
								System.out.println("**********"+dataFormatter.formatCellValue(row.getCell(5)));
								testCaseDomain.setTestCaseCategory(dataFormatter.formatCellValue(row.getCell(6)));
								testCaseDomain.setTestCasePriority(dataFormatter.formatCellValue(row.getCell(7)));
								testCaseDomain.setTestCaseTag(dataFormatter.formatCellValue(row.getCell(8)));
								testCaseDomain.setTestCaseDesciption(dataFormatter.formatCellValue(row.getCell(9)));
								testCaseDomain.setPositiveOrNegative(dataFormatter.formatCellValue(row.getCell(10)));
								testCaseDomain.setExcecuteOrSkip(dataFormatter.formatCellValue(row.getCell(14)));
								testCaseDomain.setRunnerType(dataFormatter.formatCellValue(row.getCell(11)));
								testCaseDao.updateTestCaseOnly(testCaseDomain);
								testCaseIdList.add(testCaseDomain.getTestCaseId());
							} else {
								System.out.println("saving  testCase when not exist .........testExecutionServiceImpl............. ");
								List<TestCaseDomain> testCaseDomainList = new ArrayList<>();
								testCaseDomain = new TestCaseDomain();

								testCaseDomain.setTestScenarioName(dataFormatter.formatCellValue(row.getCell(3)));
								testCaseDomain.setTestCaseId((new ObjectId().toString()));
								testCaseDomain.setTestCaseName(dataFormatter.formatCellValue(row.getCell(5)));
								testCaseDomain.setTestCaseCategory(dataFormatter.formatCellValue(row.getCell(6)));
								testCaseDomain.setTestCasePriority(dataFormatter.formatCellValue(row.getCell(7)));
								testCaseDomain.setTestCaseTag(dataFormatter.formatCellValue(row.getCell(8)));
								testCaseDomain.setTestCaseDesciption(dataFormatter.formatCellValue(row.getCell(9)));
								testCaseDomain.setPositiveOrNegative(dataFormatter.formatCellValue(row.getCell(10)));
								testCaseDomain.setRunnerType(dataFormatter.formatCellValue(row.getCell(11)));
								testCaseDomain.setExcecuteOrSkip(dataFormatter.formatCellValue(row.getCell(14)));
								testCaseDomain.setCreatedDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));
								testCaseDomain.setTestStepList(new ArrayList<>());
								testCaseDomainList.add(testCaseDomain);
								testCaseDao.saveTestCase(testCaseDomainList, requirementDomain.getRequirementId(),
										moduleDomain.getModuleId(), projectInfoDomain.getProjectId());
								testCaseIdList.add(testCaseDomain.getTestCaseId());
							}
						}

						if (!((dataFormatter.formatCellValue(row.getCell(12)).isEmpty()))) {
								List<TestStepDomain> testStepList = new ArrayList<>();
								while (rowIterator.hasNext()) {
									row = rowIterator.next();

									if (dataFormatter.formatCellValue(row.getCell(12)).equals(TEST_CASE_END)) {
										break;
									}
									if(!dataFormatter.formatCellValue(row.getCell(13)).equalsIgnoreCase("snooze")) {
                                   	
									testStepDomain = new TestStepDomain();
									testStepDomain.setTestStepsId(new ObjectId().toString());

									testStepDomain.setAction(dataFormatter.formatCellValue(row.getCell(13)));
									testStepDomain.setDependency(dataFormatter.formatCellValue(row.getCell(15)));
									testStepDomain.setParamGroupObject(dataFormatter.formatCellValue(row.getCell(16)));
									
									
									if(dataFormatter.formatCellValue(row.getCell(13)).equalsIgnoreCase("wait") || dataFormatter.formatCellValue(row.getCell(13)).equalsIgnoreCase("checkAttributeValue")) {
										 
										 testStepDomain.setStepParam(dataFormatter.formatCellValue(row.getCell(17)));
										 
									 }else {
									 testStepDomain.setStepParam("");
									 }
									
									
									
									
									
									testStepDomain.setExpectedResult(dataFormatter.formatCellValue(row.getCell(19)));
									// testStepDomain.setActualResult(dataFormatter.formatCellValue(row.getCell(25)));
									testStepDomain.setParamGroupId(dataFormatter.formatCellValue(row.getCell(20)));
									testStepDomain.setCreatedDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));
									testCaseValue = dataFormatter.formatCellValue(row.getCell(18));
									List<String> testParamData = new ArrayList<String>();
									if (testCaseValue.equals("")) {
										testStepDomain.setTestParamData(testParamData);
									} else {
										for (String eachValue : testCaseValue.split(",")) {
											testParamData.add(eachValue);
										}
										testStepDomain.setTestParamData(testParamData);
									}

									testStepList.add(testStepDomain);
								}
								}
								//System.out.println(" testCaseId "+testCaseDomain.getTestCaseId()+"testStep"+testStepList);
								testStepDao.saveTestStep(testStepList, testCaseDomain.getTestCaseId());
							}//testStep
						
						
					//--	testSuiteDomain.setTestCaseIdList(testCaseIdList);
					//--	testSuiteDao.saveTestSuite(testSuiteDomain);

					}//if testSuite null
			       
			                

				}//if row >6
				
				testSuiteDomain.setTestCaseIdList(testCaseIdList);
			   testSuiteDao.saveTestSuite(testSuiteDomain);
			}
	                else  
			         {
			        	 throw new TestSuiteException("TestSuite Name Already Exist");	
			         }       
	                response.setData(projectInfoDao.getProjectInfoById(projectInfoDomain.getProjectId()));
			response.setStatus(StatusCode.SUCCESS.name());
			//}
		} 
			catch (Exception e) {
				if(e instanceof TestSuiteException)
				{   log.error("TestSuite Name Already Exist", e);
					throw new TestSuiteException("TestSuite Name Already Exist");	
				}
				else
				{
				log.error("error in saving the excelData", e);
				throw new TestCaseException("error in saving the excelData");
				}
		}
		return response;
	}


	@Override
	public Response readPockietExcelData(MultipartFile multipartFile,String projectName,@PathVariable String testSuiteName) {

		try {
			try {
				if (readProperties.getExcelPath() != null && !"".equals(readProperties.getExcelPath().trim())) {
					File dirFileObj = FileUtils.getFile(readProperties.getExcelPath());
					if (!dirFileObj.exists()) {
						FileUtils.forceMkdir(dirFileObj);
					}
					byte[] bytes = multipartFile.getBytes();
					Path path = Paths.get(readProperties.getExcelPath() + "/" + multipartFile.getOriginalFilename());
					Files.write(path, bytes);
				}
			} catch (IOException e) {
				log.error("error in creating directory", e);
				throw new TestCaseException("error in creating dirctory");
			}

			Workbook workbook = WorkbookFactory.create(multipartFile.getInputStream());
			int numberOfSheets = workbook.getNumberOfSheets();
			String testCaseValue;
			String testParamGroup;
			Row row;
			ProjectInfoDomain projectInfoDomain = new ProjectInfoDomain();
			ModuleDomain moduleDomain = null;
			RequirementDomain requirementDomain = null;
			TestStepDomain testStepDomain;
			DataFormatter dataFormatter = new DataFormatter();

				Sheet sheet = workbook.getSheetAt(1);
				Iterator<Row> rowIterator = sheet.rowIterator();				
               projectInfoDomain=projectInfoDao.getProjectInfoByName(projectName) ;
               System.out.println("projectInfoDomain........"+               projectInfoDomain);
                TestSuiteDomain testSuiteDomain= testSuiteDao.getTestSuiteByName(projectInfoDomain.getProjectId(), testSuiteName);
                if(testSuiteDomain == null)
                {
                	   testSuiteDomain= new TestSuiteDomain(); 
                	   testSuiteDomain.setTestSuiteId(new ObjectId().toString());
                	   testSuiteDomain.setTestSuiteName(testSuiteName);
                	   testSuiteDomain.setProjectId(projectInfoDomain.getProjectId()); 
                	   testSuiteDomain.setCreatedDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));
                	     List<String> testCaseIdList= new ArrayList<String>();
                	
				if (projectInfoDomain == null) {
				
					projectInfoDomain = new ProjectInfoDomain();
					projectInfoDomain.setProjectId(new ObjectId().toString());
					projectInfoDomain.setProjectName(projectName);
					projectInfoDomain.setModuleList(new ArrayList<ModuleDomain>());
					projectInfoDomain.setCreatedDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));
					projectInfoDao.saveProjectInfo(projectInfoDomain);

				}			
				moduleDomain=moduleDao.getModuleByNameOnly("Module1",projectInfoDomain.getProjectId());
				if (moduleDomain == null) {
						

					List<ModuleDomain> moduleDomainList = new ArrayList<>();

					moduleDomain = new ModuleDomain();
					moduleDomain.setModuleId(new ObjectId().toString());
					moduleDomain.setModuleName("Module1");
					moduleDomain.setRequirementList(new ArrayList<RequirementDomain>());
					moduleDomain.setCreatedDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));
					moduleDomainList.add(moduleDomain);
					moduleDao.saveModule(moduleDomainList, projectInfoDomain.getProjectId());
				}
				
				requirementDomain = requirementDao.getRequirementByNameOnly("Requirement1", moduleDomain.getModuleId());
			
				if ( requirementDomain== null) {

					List<RequirementDomain> requirementDomainList = new ArrayList<>();
					requirementDomain = new RequirementDomain();
					requirementDomain.setRequirementId(new ObjectId().toString());
					requirementDomain.setRequirementName("Requirement1");
					requirementDomain.setTestScenarioList(new ArrayList<TestScenarioDomain>());
					requirementDomain.setCreatedDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));
					requirementDomainList.add(requirementDomain);
					requirementDao.saveRequirement(requirementDomainList, moduleDomain.getModuleId(),
							projectInfoDomain.getProjectId());
				}
			
				testScenarioDomain = testScenarioDao.getTestScenarioByNameOnly("TestScenario1",
						requirementDomain.getRequirementId());
				/*if (testScenarioDomain == null) {
				
					List<TestScenarioDomain> testScenarioDomainList = new ArrayList<>();
					testScenarioDomain = new TestScenarioDomain();
					testScenarioDomain.setTestScenarioId(new ObjectId().toString());
					testScenarioDomain.setTestScenarioName("TestScenario1");
					testScenarioDomain.setTestCaseList(new ArrayList<TestCaseDomain>());
					testScenarioDomain
							.setCreatedDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));
					testScenarioDomainList.add(testScenarioDomain);
					testScenarioDao.saveTestScenario(testScenarioDomainList,
							requirementDomain.getRequirementId(), moduleDomain.getModuleId(),
							projectInfoDomain.getProjectId());
				}*/
				
				while (rowIterator.hasNext()) {
					row = rowIterator.next();

					int rownum = row.getRowNum();

					if (rownum >= 6) {
						if (!((dataFormatter.formatCellValue(row.getCell(3)).isEmpty()))) {
							if (testScenarioDao.getTestScenarioByNameOnly(dataFormatter.formatCellValue(row.getCell(3)),
									requirementDomain.getRequirementId()) == null) {

							
								List<TestScenarioDomain> testScenarioDomainList = new ArrayList<>();
								testScenarioDomain = new TestScenarioDomain();
								testScenarioDomain.setTestScenarioId(new ObjectId().toString());
								testScenarioDomain.setTestScenarioName(dataFormatter.formatCellValue(row.getCell(3)));
								testScenarioDomain.setTestCaseList(new ArrayList<TestCaseDomain>());
								testScenarioDomain
										.setCreatedDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));
								testScenarioDomainList.add(testScenarioDomain);
								testScenarioDao.saveTestScenario(testScenarioDomainList,
										requirementDomain.getRequirementId(), moduleDomain.getModuleId(),
										projectInfoDomain.getProjectId());
							}

						} else {
							if (testScenarioDao.getTestScenarioByNameOnly("TestScenario1",
									requirementDomain.getRequirementId()) == null) {

								
								List<TestScenarioDomain> testScenarioDomainList = new ArrayList<>();
								testScenarioDomain = new TestScenarioDomain();
								testScenarioDomain.setTestScenarioId(new ObjectId().toString());
								testScenarioDomain.setTestScenarioName("TestScenario1");
								testScenarioDomain.setTestCaseList(new ArrayList<TestCaseDomain>());
								testScenarioDomain
										.setCreatedDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));
								testScenarioDomainList.add(testScenarioDomain);
								testScenarioDao.saveTestScenario(testScenarioDomainList,
										requirementDomain.getRequirementId(), moduleDomain.getModuleId(),
										projectInfoDomain.getProjectId());
							}
						}


						if (!((dataFormatter.formatCellValue(row.getCell(1)).isEmpty()))) {
							testCaseDomain=testCaseDao
									.getTestCaseByIdOnly(dataFormatter.formatCellValue(row.getCell(1)),testScenarioDomain.getTestScenarioId());

							if (testCaseDomain != null) {
								System.out.println("in test caseeeeeeee");
								testCaseDomain.setTestCaseTag(dataFormatter.formatCellValue(row.getCell(2)));
								testCaseDomain.setPositiveOrNegative(dataFormatter.formatCellValue(row.getCell(5)));
								testCaseDomain.setRunnerType(dataFormatter.formatCellValue(row.getCell(6)));
								testCaseDao.updateTestCaseOnly(testCaseDomain);
								testCaseIdList.add(testCaseDomain.getTestCaseId());
							} else {
								System.out.println("in test caseeeeeeee111111111111211");
								List<TestCaseDomain> testCaseDomainList = new ArrayList<>();
								testCaseDomain = new TestCaseDomain();						
									testCaseDomain.setTestScenarioName(testScenarioDomain.getTestScenarioName());	
								String testCaseId=	new ObjectId().toString();
								testCaseDomain.setTestCaseId(testCaseId);
								testCaseIdList.add(testCaseId);
								testCaseDomain.setTestCaseName(dataFormatter.formatCellValue(row.getCell(1)));
								//testCaseDomain.setTestCaseCategory("");
								 //testCaseDomain.setTestCasePriority(dataFormatter.formatCellValue(row.getCell(7)));
								testCaseDomain.setTestCaseTag(dataFormatter.formatCellValue(row.getCell(2)));
								testCaseDomain.setTestCaseDesciption(dataFormatter.formatCellValue(row.getCell(4)));
								testCaseDomain.setPositiveOrNegative(dataFormatter.formatCellValue(row.getCell(5)));
								testCaseDomain.setRunnerType("Selenium");
								testCaseDomain.setExcecuteOrSkip(dataFormatter.formatCellValue(row.getCell(9)));
								testCaseDomain.setCreatedDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));
								testCaseDomain.setTestStepList(new ArrayList<>());
								testCaseDomainList.add(testCaseDomain);
								testCaseDao.saveTestCase(testCaseDomainList, requirementDomain.getRequirementId(),
										moduleDomain.getModuleId(), projectInfoDomain.getProjectId());
							}

							if (!((dataFormatter.formatCellValue(row.getCell(7)).isEmpty()))) {
								List<TestStepDomain> testStepList = new ArrayList<>();
								while (rowIterator.hasNext()) {
									row = rowIterator.next();

									if (dataFormatter.formatCellValue(row.getCell(7)).equals(TEST_CASE_END)) {
										break;
									}
									
                                         if(!dataFormatter.formatCellValue(row.getCell(8)).equalsIgnoreCase("snooze")) {
                                        	 testStepDomain = new TestStepDomain();
         									testStepDomain.setTestStepsId(new ObjectId().toString());
                                        	 testStepDomain.setAction(dataFormatter.formatCellValue(row.getCell(8)));                                         																
									testStepDomain.setDependency(dataFormatter.formatCellValue(row.getCell(10)));
									 if(dataFormatter.formatCellValue(row.getCell(8)).equalsIgnoreCase("wait") || dataFormatter.formatCellValue(row.getCell(8)).equalsIgnoreCase("checkAttributeValue")) {
										 
										 testStepDomain.setStepParam(dataFormatter.formatCellValue(row.getCell(13)));
										 
									 }else {
									 testStepDomain.setStepParam("");
									 }
									testStepDomain.setExpectedResult(dataFormatter.formatCellValue(row.getCell(15)));
									testStepDomain
											.setCreatedDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));
									testCaseValue = dataFormatter.formatCellValue(row.getCell(14));
									List<String> testParamData = new ArrayList<String>();
									if (testCaseValue.equals("")) {
										testStepDomain.setTestParamData(testParamData);
									} else {
										for (String eachValue : testCaseValue.split(",")) {
											testParamData.add(eachValue);
										}
										testStepDomain.setTestParamData(testParamData);
									}
									testParamGroup = dataFormatter.formatCellValue(row.getCell(12));
									testStepDomain.setParamGroupObject(testParamGroup);
									if ( testParamGroup != null) {
										Sheet sheet1 = workbook.getSheetAt(0);
										Iterator<Row> rowIterator1 = sheet1.rowIterator();
										while (rowIterator1.hasNext()) {
											Row row1 = rowIterator1.next();
											if (!dataFormatter.formatCellValue(row1.getCell(0)).isEmpty()) {
												if (testParamGroup.equalsIgnoreCase(
														dataFormatter.formatCellValue(row1.getCell(0)))) {
													testStepDomain.setParamGroupId(
															dataFormatter.formatCellValue(row1.getCell(1)));
												}else if(testParamGroup.equalsIgnoreCase(
														"NoObject") ){
													testStepDomain.setParamGroupId("");
												}
											}

										}

									}
                                         

									testStepList.add(testStepDomain);
								}
								}

								testStepDao.saveTestStep(testStepList, testCaseDomain.getTestCaseId());
							}

						}

					}
					response.setData(projectInfoDao.getProjectInfoByName(projectName));
					response.setStatus(StatusCode.SUCCESS.name());

				}
				System.out.println("testCaseIdList...............after analysis"+testCaseIdList);
				testSuiteDomain.setTestCaseIdList(testCaseIdList);
				testSuiteDao.saveTestSuite(testSuiteDomain);
				
				
            }
           else
           {
        	   throw new TestSuiteException("TestSuite Name Already Exist");
                	
           }
                
			
		} 
		    
		catch (Exception e) {
			if(e instanceof TestSuiteException)
			{   log.error("TestSuite Name Already Exist", e);
				throw new TestSuiteException("TestSuite Name Already Exist");	
			}
			else
			{
			log.error("error in saving the excelData", e);
			throw new TestCaseException("error in saving the excelData");
			}
		}
		return response;
	}

}