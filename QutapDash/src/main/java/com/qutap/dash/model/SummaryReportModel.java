package com.qutap.dash.model;

public class SummaryReportModel {
	
	private String testSuiteId;
	private String caseName;
	private String description;
	private String classificationTag;
	private String runner;
	private String status;
	private String exceptionErrors;
	private double duration;
	private String createdBy;
	private String createdDateTime;

	public String getTestSuiteId() {
		return testSuiteId;
	}

	public String setTestSuiteId(String testSuiteId) {
		return this.testSuiteId = testSuiteId;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getClassificationTag() {
		return classificationTag;
	}

	public void setClassificationTag(String classificationTag) {
		this.classificationTag = classificationTag;
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

	public String getExceptionErrors() {
		return exceptionErrors;
	}

	public void setExceptionErrors(String exceptionErrors) {
		this.exceptionErrors = exceptionErrors;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(String createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

}
