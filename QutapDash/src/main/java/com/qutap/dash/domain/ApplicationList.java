package com.qutap.dash.domain;

import java.io.Serializable;

public class ApplicationList  implements Serializable{
	private String applicationId;
	private String applicationName;
	private boolean onBoard;


	private static final long serialVersionUID = 1L;
	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public boolean isOnBoard() {
		return onBoard;
	}

	public void setOnBoard(boolean onBoard) {
		this.onBoard = onBoard;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	@Override
	public String toString() {
		return "ApplicationList [applicationId=" + applicationId + ", applicationName=" + applicationName + ", onBoard="
				+ onBoard + "]";
	}

	

}
