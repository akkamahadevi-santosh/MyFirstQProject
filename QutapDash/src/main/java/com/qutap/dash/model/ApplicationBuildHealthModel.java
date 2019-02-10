package com.qutap.dash.model;

import java.util.Comparator;



public class ApplicationBuildHealthModel {
	
	private String projectName;
	private String buildNumber;
	private long failed;
	private long totatTests;
	private String percentage;
	private String colourName;
	private String colourCode;

	public static class OrderByBuildNumber implements Comparator<ApplicationBuildHealthModel> {

		@Override
		public int compare(ApplicationBuildHealthModel o1, ApplicationBuildHealthModel o2) {
			return o1.buildNumber.compareTo(o2.buildNumber);
		}
	}

	public static class OrderByApplicationName implements Comparator<ApplicationBuildHealthModel> {

		@Override
		public int compare(ApplicationBuildHealthModel o1, ApplicationBuildHealthModel o2) {
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

}
