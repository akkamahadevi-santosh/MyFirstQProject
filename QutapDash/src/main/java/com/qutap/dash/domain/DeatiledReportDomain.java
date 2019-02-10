package com.qutap.dash.domain;

public class DeatiledReportDomain {
	private String testExeID;
	private String caseName;
	private String runner;
	private String status;
	private String comment;
	private double duration;
	private String paramObjectGroup;
	private String actionName;
	private String paramData;
	private String updatedBy;
	private String exeDate;

	public String getTestExeID() {
		return testExeID;
	}

	public void setTestExeID(String testExeID) {
		this.testExeID = testExeID;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getRunner() {
		return runner;
	}

	public void setRunner(String runner) {
		this.runner = runner;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public String getParamObjectGroup() {
		return paramObjectGroup;
	}

	public void setParamObjectGroup(String paramObjectGroup) {
		this.paramObjectGroup = paramObjectGroup;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getParamData() {
		return paramData;
	}

	public void setParamData(String paramData) {
		this.paramData = paramData;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getExeDate() {
		return exeDate;
	}

	public void setExeDate(String exeDate) {
		this.exeDate = exeDate;
	}

}
