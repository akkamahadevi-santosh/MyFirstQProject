package com.qutap.dash.model;

public class MemoryUsagesDomain {

	private String heapUsedMemory;
	private String heapMaxMemory;
	private String heapInit;
	private String heapCommitted;
	private String nonHeapUsedMemory;
	// private String nonHeapMaxMemory;
	private String nonHeapInit;
	private String nonHeapCommitted;
	private long numProcessors;
	private String cpuTime;
	private String committedVirtualMemorySize;
	private String freePhysicalMemorySize;
	private double processCpuLoad;
	private String processCputime;
	private double systemCpuLoad;
	private String totalPhysicalMemorySize;
	private String totalSwapSpaceSize;

	public String getHeapUsedMemory() {
		return heapUsedMemory;
	}

	public void setHeapUsedMemory(String heapUsedMemory) {
		this.heapUsedMemory = heapUsedMemory;
	}

	public String getHeapMaxMemory() {
		return heapMaxMemory;
	}

	public void setHeapMaxMemory(String heapMaxMemory) {
		this.heapMaxMemory = heapMaxMemory;
	}

	public String getHeapInit() {
		return heapInit;
	}

	public void setHeapInit(String heapInit) {
		this.heapInit = heapInit;
	}

	public String getHeapCommitted() {
		return heapCommitted;
	}

	public void setHeapCommitted(String heapCommitted) {
		this.heapCommitted = heapCommitted;
	}

	public String getNonHeapUsedMemory() {
		return nonHeapUsedMemory;
	}

	public void setNonHeapUsedMemory(String nonHeapUsedMemory) {
		this.nonHeapUsedMemory = nonHeapUsedMemory;
	}

	public String getNonHeapInit() {
		return nonHeapInit;
	}

	public void setNonHeapInit(String nonHeapInit) {
		this.nonHeapInit = nonHeapInit;
	}

	public String getNonHeapCommitted() {
		return nonHeapCommitted;
	}

	public void setNonHeapCommitted(String nonHeapCommitted) {
		this.nonHeapCommitted = nonHeapCommitted;
	}

	public long getNumProcessors() {
		return numProcessors;
	}

	public void setNumProcessors(long numProcessors) {
		this.numProcessors = numProcessors;
	}

	public String getCpuTime() {
		return cpuTime;
	}

	public void setCpuTime(String cpuTime) {
		this.cpuTime = cpuTime;
	}

	public String getCommittedVirtualMemorySize() {
		return committedVirtualMemorySize;
	}

	public void setCommittedVirtualMemorySize(String committedVirtualMemorySize) {
		this.committedVirtualMemorySize = committedVirtualMemorySize;
	}

	public String getFreePhysicalMemorySize() {
		return freePhysicalMemorySize;
	}

	public void setFreePhysicalMemorySize(String freePhysicalMemorySize) {
		this.freePhysicalMemorySize = freePhysicalMemorySize;
	}

	public double getProcessCpuLoad() {
		return processCpuLoad;
	}

	public void setProcessCpuLoad(double processCpuLoad) {
		this.processCpuLoad = processCpuLoad;
	}

	public String getProcessCputime() {
		return processCputime;
	}

	public void setProcessCputime(String processCputime) {
		this.processCputime = processCputime;
	}

	public double getSystemCpuLoad() {
		return systemCpuLoad;
	}

	public void setSystemCpuLoad(double systemCpuLoad) {
		this.systemCpuLoad = systemCpuLoad;
	}

	public String getTotalPhysicalMemorySize() {
		return totalPhysicalMemorySize;
	}

	public void setTotalPhysicalMemorySize(String totalPhysicalMemorySize) {
		this.totalPhysicalMemorySize = totalPhysicalMemorySize;
	}

	public String getTotalSwapSpaceSize() {
		return totalSwapSpaceSize;
	}

	public void setTotalSwapSpaceSize(String totalSwapSpaceSize) {
		this.totalSwapSpaceSize = totalSwapSpaceSize;
	}

	@Override
	public String toString() {
		return "MemoryUsagesDomain [heapUsedMemory=" + heapUsedMemory + "bytes, heapMaxMemory=" + heapMaxMemory
				+ "bytes, heapInit=" + heapInit + "bytes, heapCommitted=" + heapCommitted + "bytes, nonHeapUsedMemory="
				+ nonHeapUsedMemory + "bytes, nonHeapInit=" + nonHeapInit + "bytes, nonHeapCommitted="
				+ nonHeapCommitted + "bytes, numProcessors=" + numProcessors + ", cpuTime=" + cpuTime
				+ "ms, committedVirtualMemorySize=" + committedVirtualMemorySize + "bytes, freePhysicalMemorySize="
				+ freePhysicalMemorySize + "bytes, processCpuLoad=" + processCpuLoad + "bytes, processCputime="
				+ processCputime + ", systemCpuLoad=" + systemCpuLoad + ", totalPhysicalMemorySize="
				+ totalPhysicalMemorySize + "bytes, totalSwapSpaceSize=" + totalSwapSpaceSize + "bytes]";
	}

}
