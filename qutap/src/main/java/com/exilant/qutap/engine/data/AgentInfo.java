package com.exilant.qutap.engine.data;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicInteger;


public class AgentInfo implements IAgentInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 482739086856227137L;
	private String ipAddress;
	private Integer portNumber;
	private String agentName;
	private Integer agentId;
	private Boolean active;
	private AtomicInteger currentLoad = new AtomicInteger(0);
	
	private Timestamp latestPingTime;
	
	@Override
	public String getIpAddress() {
		// TODO Auto-generated method stub
		return this.ipAddress;
	}

	@Override
	public void setIpAddress(String ipAddress) {
		// TODO Auto-generated method stub
		this.ipAddress = ipAddress;
	}

	@Override
	public Integer getPortNumber() {
		// TODO Auto-generated method stub
		return this.portNumber;
	}

	@Override
	public void setPortNumber(Integer portNumber) {
		// TODO Auto-generated method stub
		this.portNumber = portNumber;
	}

	@Override
	public String getAgentName() {
		// TODO Auto-generated method stub
		return this.agentName;
	}

	@Override
	public void setAgentName(String agentName) {
		// TODO Auto-generated method stub
		this.agentName = agentName;
	}

	@Override
	public Integer getAgentId() {
		// TODO Auto-generated method stub
		return this.agentId;
	}

	@Override
	public void setAgentId(Integer agentId) {
		// TODO Auto-generated method stub
		this.agentId = agentId;
	}

	@Override
	public Boolean isActive() {
		// TODO Auto-generated method stub
		return this.active;
	}

	@Override
	public void incativate() {
		// TODO Auto-generated method stub
		this.active = false;
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub
		this.active = true;
	}

	@Override
	public Integer getCurrentLoad() {
		// TODO Auto-generated method stub
		return currentLoad.get();
	}

	@Override
	public void incrementCurrentLoad() {
		// TODO Auto-generated method stub
		this.currentLoad.incrementAndGet();
	}

	@Override
	public void decrementCurrentLoad() {
		// TODO Auto-generated method stub
		this.currentLoad.decrementAndGet();
	}

	@Override
	public Timestamp getLatestPingTime() {
		// TODO Auto-generated method stub
		return this.latestPingTime;
	}

	@Override
	public void setLatestPingTime() {
		// TODO Auto-generated method stub
		this.latestPingTime = new Timestamp(System.currentTimeMillis());
	}

	@Override
	public int compareTo(IAgentInfo other) {
		// TODO Auto-generated method stub
		int retValue = 1;
		if (other.getAgentId().equals(this.getAgentId()) &&
				other.getAgentName().equals(this.getAgentName()) &&
				other.getIpAddress().equals(this.getIpAddress()) &&
				other.getPortNumber().equals(this.getPortNumber())){
			retValue = 0;
		}
		return retValue;
	}

}
