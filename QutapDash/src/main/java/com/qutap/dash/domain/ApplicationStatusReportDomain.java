package com.qutap.dash.domain;

import java.util.Comparator;

public class ApplicationStatusReportDomain {
	private String applicationName;
	private String week;
	private String summary;
	private long passed;
	private long failed;
	private long skipped;
	private long totatTests;
	private String buildNumber;
	private String priority;
	private long value;
	private long applicationCount;
	private String StartDate;
	private String EndDate;

	public static class OrderByBuildNumber implements Comparator<ApplicationStatusReportDomain> {

		@Override
		public int compare(ApplicationStatusReportDomain o1, ApplicationStatusReportDomain o2) {
			return o1.buildNumber.compareTo(o2.buildNumber);
		}
	}
	
	public static class OrderByWeek implements Comparator<ApplicationStatusReportDomain> {

		@Override
		public int compare(ApplicationStatusReportDomain o1, ApplicationStatusReportDomain o2) {
			return o1.week.compareTo(o2.week);
		}
	}
	
	public static class OrderByApplicationName implements Comparator<ApplicationStatusReportDomain> {

		@Override
		public int compare(ApplicationStatusReportDomain o1, ApplicationStatusReportDomain o2) {
			return o1.applicationName.compareTo(o2.applicationName);
		}
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String string) {
		this.week = string;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public long getPassed() {
		return passed;
	}

	public void setPassed(long passed) {
		this.passed = passed;
	}

	public long getFailed() {
		return failed;
	}

	public void setFailed(long failed) {
		this.failed = failed;
	}

	public long getSkipped() {
		return skipped;
	}

	public void setSkipped(long skipped) {
		this.skipped = skipped;
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

	public String getPriority() {
		return priority;
	}

	public void setPriority(String string) {
		this.priority = string;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public long getApplicationCount() {
		return applicationCount;
	}

	public void setApplicationCount(long applicationCount) {
		this.applicationCount = applicationCount;
	}
	
	

	

	public String getStartDate() {
		return StartDate;
	}

	public void setStartDate(String startDate) {
		StartDate = startDate;
	}

	public String getEndDate() {
		return EndDate;
	}

	public void setEndDate(String endDate) {
		EndDate = endDate;
	}

	@Override
	public String toString() {
		return "ApplicationStatusReportDomain [applicationName=" + applicationName + ", week=" + week + ", summary="
				+ summary + ", passed=" + passed + ", failed=" + failed + ", skipped=" + skipped + ", totatTests="
				+ totatTests + ", buildNumber=" + buildNumber + ", priority=" + priority + ", value=" + value
				+ ", applicationCount=" + applicationCount + "]";
	}


}
