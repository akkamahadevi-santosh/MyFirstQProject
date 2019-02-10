package com.qutap.dash.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class TestStepModel implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2807279589980631672L;
	
	private String testStepsId;
	private String testCaseId;
	private String action;
	private String excecuteOrSkip;
	private String dependency;
	private String paramGroupObject;
	private List<String> TestParamData;
	private String expectedResult;
	private String actualResult;
	private String paramGroupId;
	private String createdDate;
	private String modifiedDate;
	public String getTestStepsId() {
		return testStepsId;
	}
	
	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setTestStepsId(String testStepsId) {
		this.testStepsId = testStepsId;
	}
	public String getTestCaseId() {
		return testCaseId;
	}
	public void setTestCaseId(String testCaseId) {
		this.testCaseId = testCaseId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getExcecuteOrSkip() {
		return excecuteOrSkip;
	}
	public void setExcecuteOrSkip(String excecuteOrSkip) {
		this.excecuteOrSkip = excecuteOrSkip;
	}
	public String getDependency() {
		return dependency;
	}
	public void setDependency(String dependency) {
		this.dependency = dependency;
	}
	public String getParamGroupObject() {
		return paramGroupObject;
	}
	public void setParamGroupObject(String paramGroupObject) {
		this.paramGroupObject = paramGroupObject;
	}
	public List<String> getTestParamData() {
		return TestParamData;
	}
	public void setTestParamData(List<String> testParamData) {
		TestParamData = testParamData;
	}
	public String getExpectedResult() {
		return expectedResult;
	}
	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
	}
	public String getActualResult() {
		return actualResult;
	}
	public void setActualResult(String actualResult) {
		this.actualResult = actualResult;
	}
	public String getParamGroupId() {
		return paramGroupId;
	}
	public void setParamGroupId(String paramGroupId) {
		this.paramGroupId = paramGroupId;
	}
	
	
	
	
	
	

}
