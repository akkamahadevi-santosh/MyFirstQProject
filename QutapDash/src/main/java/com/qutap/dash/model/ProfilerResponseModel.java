package com.qutap.dash.model;

import java.io.Serializable;
import java.util.List;

public class ProfilerResponseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String projectName;
	List<Object> listOfBuilds;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public List<Object> getListOfBuilds() {
		return listOfBuilds;
	}

	public void setListOfBuilds(List<Object> listOfBuilds) {
		this.listOfBuilds = listOfBuilds;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
