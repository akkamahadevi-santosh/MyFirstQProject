package com.qutap.dash.model;

import java.util.ArrayList;

public class TestStepExecutionListModel {
	private String StartDateTime;
	 private String EndDateTime;
	 private String response;
	 private String paramGroupObject;
	 private String action;
	 private String runnerType;
	 private String error;
	 private String testStepId;
	 private String status;
	 private String duration;
	 private String testExecutionVideo;
	 private String screenShot;
	 ArrayList < Object > testParamData = new ArrayList < Object > ();

	 public String getStartDateTime() {
	  return StartDateTime;
	 }

	 public String getEndDateTime() {
	  return EndDateTime;
	 }

	 public String getResponse() {
	  return response;
	 }

	 public String getParamGroupObject() {
	  return paramGroupObject;
	 }

	 public String getAction() {
	  return action;
	 }

	 public String getRunnerType() {
	  return runnerType;
	 }

	 public String getError() {
	  return error;
	 }

	 public String getTestStepId() {
	  return testStepId;
	 }

	 public String getStatus() {
	  return status;
	 }

	 // Setter Methods 

	 public void setStartDateTime(String StartDateTime) {
	  this.StartDateTime = StartDateTime;
	 }

	 public void setEndDateTime(String EndDateTime) {
	  this.EndDateTime = EndDateTime;
	 }

	 public void setResponse(String response) {
	  this.response = response;
	 }

	 public void setParamGroupObject(String paramGroupObject) {
	  this.paramGroupObject = paramGroupObject;
	 }

	 public void setAction(String action) {
	  this.action = action;
	 }

	 public void setRunnerType(String runnerType) {
	  this.runnerType = runnerType;
	 }

	 public void setError(String error) {
	  this.error = error;
	 }

	 public void setTestStepId(String testStepId) {
	  this.testStepId = testStepId;
	 }

	 public void setStatus(String status) {
	  this.status = status;
	 }
	 
	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getTestExecutionVideo() {
		return testExecutionVideo;
	}

	public void setTestExecutionVideo(String testExecutionVideo) {
		this.testExecutionVideo = testExecutionVideo;
	}

	public String getScreenShot() {
		return screenShot;
	}

	public void setScreenShot(String screenShot) {
		this.screenShot = screenShot;
	}

	public ArrayList<Object> getTestParamData() {
		return testParamData;
	}

	public void setTestParamData(ArrayList<Object> testParamData) {
		this.testParamData = testParamData;
	}

	@Override
	public String toString() {
		return "TestResultsDomain [StartDateTime=" + StartDateTime + ", EndDateTime=" + EndDateTime + ", response="
				+ response + ", paramGroupObject=" + paramGroupObject + ", action=" + action + ", runnerType="
				+ runnerType + ", error=" + error + ", testStepId=" + testStepId + ", status=" + status
				+ ", testParamData=" + testParamData + "]";
	}
	 
	 
	 

}
