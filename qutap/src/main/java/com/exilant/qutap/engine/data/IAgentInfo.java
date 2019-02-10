package com.exilant.qutap.engine.data;

import java.io.Serializable;
import java.sql.Timestamp;


public interface IAgentInfo extends Serializable,Comparable<IAgentInfo> {
	
	String getIpAddress();
	void setIpAddress(String ipAddress);
	
	Integer getPortNumber();
	void setPortNumber(Integer portNumber);
	
	String getAgentName();
	void setAgentName(String agentName);
	
	
	Integer getAgentId();
	void setAgentId(Integer agentId);
	
	Boolean isActive();
	void incativate();
	void activate();
	
	Integer getCurrentLoad();
	void incrementCurrentLoad();
	void decrementCurrentLoad();
	
	Timestamp getLatestPingTime();
	 void setLatestPingTime();
	
	
	
	

}
