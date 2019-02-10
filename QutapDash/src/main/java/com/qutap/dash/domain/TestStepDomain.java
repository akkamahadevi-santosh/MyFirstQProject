package com.qutap.dash.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.qutap.dash.commonUtils.DateUtility;
import com.qutap.dash.model.TestStepModel;


public class TestStepDomain implements Serializable {

	private static final long serialVersionUID = -2807279589980631672L;
	//@Id
	private String testStepsId;
	private String action;
	//private String excecuteOrSkip;
	private String dependency;
	private String paramGroupObject;
	private String StepParam; 
	private List<String> TestParamData;
	private String expectedResult;
	private String actualResult;
	private String createdDate;
	private String modifiedDate;
	private String paramGroupId;
	

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

	public String getTestStepsId() {
		return testStepsId;
	}

	public void setTestStepsId(String testStepsId) {
		this.testStepsId = testStepsId;
	}
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

//	public String getExcecuteOrSkip() {
//		return excecuteOrSkip;
//	}
//
//	public void setExcecuteOrSkip(String excecuteOrSkip) {
//		this.excecuteOrSkip = excecuteOrSkip;
//	}

	
	public String getDependency() {
		return dependency;
	}

	public String getStepParam() {
		return StepParam;
	}

	public void setStepParam(String stepParam) {
		StepParam = stepParam;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	@Override
	public String toString() {
		return "TestStepDomain [testStepsId=" + testStepsId + ", action=" + action + ", dependency=" + dependency
				+ ", paramGroupObject=" + paramGroupObject + ", StepParam=" + StepParam + ", TestParamData="
				+ TestParamData + ", expectedResult=" + expectedResult + ", actualResult=" + actualResult
				+ ", paramGroupId=" + paramGroupId + "]";
	}

	
	
}
