package com.qutap.dash.domain;

import java.util.Comparator;

public class ApplicationBuildHealthDomain {
	
	private String projectName;
	private String buildNumber;
	private long failed;
	private long totatTests;
	private String percentage;
	private String colourName;
	private String colourCode;

	public static class OrderByBuildNumber implements Comparator<ApplicationBuildHealthDomain> {

		@Override
		public int compare(ApplicationBuildHealthDomain o1, ApplicationBuildHealthDomain o2) {
			return o1.buildNumber.compareTo(o2.buildNumber);
		}
	}

	public static class OrderByApplicationName implements Comparator<ApplicationBuildHealthDomain> {

		@Override
		public int compare(ApplicationBuildHealthDomain o1, ApplicationBuildHealthDomain o2) {
			return o1.projectName.compareTo(o2.projectName);
		}
	}



	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public long getFailed() {
		return failed;
	}

	public void setFailed(long failed) {
		this.failed = failed;
	}

	public long getTotatTests() {
		return totatTests;
	}

	public void setTotatTests(long totatTests) {
		this.totatTests = totatTests;
	}

	public String getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String string) {
		this.percentage = string;
	}

	public String getColourName() {
		return colourName;
	}

	public void setColourName(String colourName) {
		this.colourName = colourName;
	}

	public String getColourCode() {
		return colourCode;
	}

	public void setColourCode(String colourCode) {
		this.colourCode = colourCode;
	}

	@Override
	public String toString() {
		return "ApplicationBuildHealthDomain [projectName=" + projectName + ", buildNumber=" + buildNumber + ", failed="
				+ failed + ", totatTests=" + totatTests + ", percentage=" + percentage + ", colourName=" + colourName
				+ ", colourCode=" + colourCode + "]";
	}
	
	

}
