package com.qutap.dash.model;

import java.io.Serializable;

import java.util.Map;

import javax.management.ObjectName;

public class MemoryUsagesModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String current_HeapSize;
	private String max_HeapSize;
	private String init_HeapSize;
	private String committed_HeapSize;
	private String current_NonHeapSize;
	// private String nonHeapMaxMemory;
	private String init_NonHeapSize;
	private String committed_NonHeapSize;
	private long numberOf_Processors;
	private String cpuTime;
	private String committedVirtual_MemorySize;
	private String freePhysical_MemorySize;
	private double processCpuLoad;
	private String processCputime;
	private double systemCpuLoad;
	private String totalPhysical_MemorySize;
	private String totalSwap_SpaceSize;
	private int threadCount;
	private String currentThread_CpuTime;
	private int daemonThreadCount;
	private long totalThreadStartedCount;
	private int peakThreadCount;
	private String currentThreadUserTime;
//	private Object objectName;
	private Map<String, Object> isValid;
	private Map<String, Object> collectionTime;
	private Map<String, Object> memoryPoolNames;
	private Map<String, Object> collectionCount;
	private int loadedClassCount;
	private Long totalLoadedClassCount;
	private Long totalUnloadedClassCount;
	
	
	
	
	public String getCurrent_HeapSize() {
		return current_HeapSize;
	}
	public void setCurrent_HeapSize(String current_HeapSize) {
		this.current_HeapSize = current_HeapSize;
	}
	public String getMax_HeapSize() {
		return max_HeapSize;
	}
	public void setMax_HeapSize(String max_HeapSize) {
		this.max_HeapSize = max_HeapSize;
	}
	public String getInit_HeapSize() {
		return init_HeapSize;
	}
	public void setInit_HeapSize(String init_HeapSize) {
		this.init_HeapSize = init_HeapSize;
	}
	public String getCommitted_HeapSize() {
		return committed_HeapSize;
	}
	public void setCommitted_HeapSize(String committed_HeapSize) {
		this.committed_HeapSize = committed_HeapSize;
	}
	public String getCurrent_NonHeapSize() {
		return current_NonHeapSize;
	}
	public void setCurrent_NonHeapSize(String current_NonHeapSize) {
		this.current_NonHeapSize = current_NonHeapSize;
	}
	public String getInit_NonHeapSize() {
		return init_NonHeapSize;
	}
	public void setInit_NonHeapSize(String init_NonHeapSize) {
		this.init_NonHeapSize = init_NonHeapSize;
	}
	public String getCommitted_NonHeapSize() {
		return committed_NonHeapSize;
	}
	public void setCommitted_NonHeapSize(String committed_NonHeapSize) {
		this.committed_NonHeapSize = committed_NonHeapSize;
	}
	public long getNumberOf_Processors() {
		return numberOf_Processors;
	}
	public void setNumberOf_Processors(long numberOf_Processors) {
		this.numberOf_Processors = numberOf_Processors;
	}
	public String getCpuTime() {
		return cpuTime;
	}
	public void setCpuTime(String cpuTime) {
		this.cpuTime = cpuTime;
	}
	public String getCommittedVirtual_MemorySize() {
		return committedVirtual_MemorySize;
	}
	public void setCommittedVirtual_MemorySize(String committedVirtual_MemorySize) {
		this.committedVirtual_MemorySize = committedVirtual_MemorySize;
	}
	public String getFreePhysical_MemorySize() {
		return freePhysical_MemorySize;
	}
	public void setFreePhysical_MemorySize(String freePhysical_MemorySize) {
		this.freePhysical_MemorySize = freePhysical_MemorySize;
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
	public String getTotalPhysical_MemorySize() {
		return totalPhysical_MemorySize;
	}
	public void setTotalPhysical_MemorySize(String totalPhysical_MemorySize) {
		this.totalPhysical_MemorySize = totalPhysical_MemorySize;
	}
	public String getTotalSwap_SpaceSize() {
		return totalSwap_SpaceSize;
	}
	public void setTotalSwap_SpaceSize(String totalSwap_SpaceSize) {
		this.totalSwap_SpaceSize = totalSwap_SpaceSize;
	}
	public int getThreadCount() {
		return threadCount;
	}
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
	public String getCurrentThread_CpuTime() {
		return currentThread_CpuTime;
	}
	public void setCurrentThread_CpuTime(String currentThread_CpuTime) {
		this.currentThread_CpuTime = currentThread_CpuTime;
	}
	public int getDaemonThreadCount() {
		return daemonThreadCount;
	}
	public void setDaemonThreadCount(int daemonThreadCount) {
		this.daemonThreadCount = daemonThreadCount;
	}
	public long getTotalThreadStartedCount() {
		return totalThreadStartedCount;
	}
	public void setTotalThreadStartedCount(long totalThreadStartedCount) {
		this.totalThreadStartedCount = totalThreadStartedCount;
	}
	public int getPeakThreadCount() {
		return peakThreadCount;
	}
	public void setPeakThreadCount(int peakThreadCount) {
		this.peakThreadCount = peakThreadCount;
	}
	public String getCurrentThreadUserTime() {
		return currentThreadUserTime;
	}
	public void setCurrentThreadUserTime(String currentThreadUserTime) {
		this.currentThreadUserTime = currentThreadUserTime;
	}
	public Map<String, Object> getIsValid() {
		return isValid;
	}
	public void setIsValid(Map<String, Object> isValid) {
		this.isValid = isValid;
	}
	public Map<String, Object> getCollectionTime() {
		return collectionTime;
	}
	public void setCollectionTime(Map<String, Object> collectionTime) {
		this.collectionTime = collectionTime;
	}
	public Map<String, Object> getMemoryPoolNames() {
		return memoryPoolNames;
	}
	public void setMemoryPoolNames(Map<String, Object> memoryPoolNames) {
		this.memoryPoolNames = memoryPoolNames;
	}
	public Map<String, Object> getCollectionCount() {
		return collectionCount;
	}
	public void setCollectionCount(Map<String, Object> collectionCount) {
		this.collectionCount = collectionCount;
	}
	public int getLoadedClassCount() {
		return loadedClassCount;
	}
	public void setLoadedClassCount(int loadedClassCount) {
		this.loadedClassCount = loadedClassCount;
	}
	public Long getTotalLoadedClassCount() {
		return totalLoadedClassCount;
	}
	public void setTotalLoadedClassCount(Long totalLoadedClassCount) {
		this.totalLoadedClassCount = totalLoadedClassCount;
	}
	public Long getTotalUnloadedClassCount() {
		return totalUnloadedClassCount;
	}
	public void setTotalUnloadedClassCount(Long totalUnloadedClassCount) {
		this.totalUnloadedClassCount = totalUnloadedClassCount;
	}
	



}
