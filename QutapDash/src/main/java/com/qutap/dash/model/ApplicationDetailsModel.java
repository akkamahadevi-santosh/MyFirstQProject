package com.qutap.dash.model;

public class ApplicationDetailsModel {
	
	private String applicationId;
	private String applicationName;
	private boolean onBoard;
	private long totalTestCases;
	private long FT;
	private long NFT;
	private String percentage;
	

	public long getTotalTestCases() {
		return totalTestCases;
	}

	public void setTotalTestCases(long totalTestCases) {
		this.totalTestCases = totalTestCases;
	}

	public String getApplicationId() {
		return applicationId;
	}
	
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	
	public String getApplicationName() {
		return applicationName;
	}
	
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	
	public boolean isOnBoard() {
		return onBoard;
	}
	
	public void setOnBoard(boolean onBoard) {
		this.onBoard = onBoard;
	}
	
	public long getFT() {
		return FT;
	}
	public void setFT(long fT) {
		FT = fT;
	}
	public long getNFT() {
		return NFT;
	}
	
	public void setNFT(long nFT) {
		NFT = nFT;
	}
	
	
	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	@Override
	public String toString() {
		return "ApplicationDetailsModel [applicationId=" + applicationId + ", applicationName=" + applicationName
				+ ", onBoard=" + onBoard + ", totalTestCases=" + totalTestCases + ", FT=" + FT + ", NFT=" + NFT
				+ ", percentage=" + percentage + "]";
	}

	

}
