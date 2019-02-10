package com.qutap.dash.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class ProfilerModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String builId;
	List<MemoryUsagesModel> indivisualBuilds;
	
	public String getBuilId() {
		return builId;
	}
	public void setBuilId(String builId) {
		this.builId = builId;
	}
	public List<MemoryUsagesModel> getIndivisualBuilds() {
		return indivisualBuilds;
	}
	public void setIndivisualBuilds(List<MemoryUsagesModel> indivisualBuilds) {
		this.indivisualBuilds = indivisualBuilds;
	}
	

	

}
