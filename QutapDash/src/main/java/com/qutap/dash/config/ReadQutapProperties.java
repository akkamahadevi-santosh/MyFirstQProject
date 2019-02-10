package com.qutap.dash.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties("qutap")
public class ReadQutapProperties {
	
	private String excelPath;
	private String excecutionPath;
	private  static String memberIP;
	private String profilerPortNum;
	private String remoteIpProfiler;
	private String excecutionPathSuite;
	
	
	public String getExcecutionPathSuite() {
		return excecutionPathSuite;
	}

	public void setExcecutionPathSuite(String excecutionPathSuite) {
		this.excecutionPathSuite = excecutionPathSuite;
	}

	public String getRemoteIpProfiler() {
		return remoteIpProfiler;
	}

	public void setRemoteIpProfiler(String remoteIpProfiler) {
		this.remoteIpProfiler = remoteIpProfiler;
	}

	public String getProfilerPortNum() {
		return profilerPortNum;
	}

	public void setProfilerPortNum(String profilerPortNum) {
		this.profilerPortNum = profilerPortNum;
	}

	public String getExcecutionPath() {
		return excecutionPath;
	}

	public void setExcecutionPath(String excecutionPath) {
		this.excecutionPath = excecutionPath;
	}

	public String getExcelPath() {
		return excelPath;
	}

	public void setExcelPath(String excelPath) {
		this.excelPath = excelPath;
	}

	public static String getMemberIP() {
		return memberIP;
	}

	public static void setMemberIP(String memberIP) {
		ReadQutapProperties.memberIP = memberIP;
	}

	
	
	
	

}
