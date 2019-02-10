package com.qutap.dash.model;

public class AgentMeta {

	private String logLevel;
	private String resultType;
	private String aSync;
	public String getASync() {
		return aSync;
	}

	public void setASync(String aSync) {
		this.aSync = aSync;
	}

	public String getLogLevel() {

		return logLevel;
	}

	public void setLogLevel(String logLevel) {

		this.logLevel = logLevel;
	}

	public String getResultType() {

		return resultType;
	}

	public void setResultType(String resultType) {

		this.resultType = resultType;
	}

	@Override
	public String toString() {
		return "AgentMeta [logLevel=" + logLevel + ", resultType=" + resultType + ", aSync=" + aSync + "]";
	}

}
