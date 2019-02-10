package com.qutap.dash.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.customException.TestCaseException;
import com.qutap.dash.customException.TestSuiteException;
import com.qutap.dash.service.ExcelDataService;


@RestController
@RequestMapping("/Qutap")
public class ExcelDataController {
	
	Logger log= LoggerFactory.getLogger(ExcelDataController.class);
	
	@Autowired
	ExcelDataService excelDataService;
	
	
	@PostMapping("/qutapExcel/{projectName}/{testSuiteName}")
	public Response readQutapExcelData(@RequestParam("file") MultipartFile multipartFile,@PathVariable String projectName,@PathVariable String testSuiteName,HttpServletRequest req) throws IOException { 
		log.info("url of the application"+req.getRequestURL().toString());
		Response response=excelDataService.readExcelData(multipartFile,projectName,testSuiteName);
		response.setUrl(req.getRequestURL().toString());
		return response;		
	}	
	
	@PostMapping("/pokeITExcel/{projectName}/{testSuiteName}")
	public Response readPokeITExcelData(@RequestParam("file") MultipartFile multipartFile,@PathVariable String projectName,@PathVariable String testSuiteName, HttpServletRequest req) throws IOException { 
		log.info("url of the application"+req.getRequestURL().toString());
		Response response=excelDataService.readPockietExcelData(multipartFile,projectName,testSuiteName);
		response.setUrl(req.getRequestURL().toString());
		return response;		
	}	
	
	@PostMapping("/excel/{applicationName}/{projectName}/{testSuiteName}")
	public Response readExcelData(@RequestParam("file") MultipartFile multipartFile,@PathVariable String applicationName,@PathVariable String projectName,@PathVariable String testSuiteName,HttpServletRequest req) throws IOException { 
		log.info("url of the application"+req.getRequestURL().toString());
		Response response=null;
		try {		
		if (applicationName.equalsIgnoreCase("core")) {
			System.out.println("from Qutap");
		//--response=excelDataService.readExcelData(multipartFile);	
			response=excelDataService.readExcelData(multipartFile,projectName,testSuiteName);	 
		} else if(applicationName.equalsIgnoreCase("pokeit")) {
			System.out.println("from pokeIT");
		 response=excelDataService.readPockietExcelData(multipartFile,projectName,testSuiteName);
		}
		}
		catch(Exception e) {
			 response = new Response();
				if(e instanceof TestSuiteException)
				{   log.error("TestSuite Name Already Exist", e);
					//throw new TestSuiteException("TestSuite Name Already Exist");	
				  response.setMessage(e.getMessage());
				  response.setStatus("Fail");
				}
				else {
				log.error("\"not a valid application name"+applicationName+":",e);
				//throw new TestCaseException("not a valid application name"+applicationName+":"+e);
				response.setMessage(e.getMessage());
				  response.setStatus("Fail");
				}
		}
		response.setUrl(req.getRequestURL().toString());
		return response;		
	}	
	

}
