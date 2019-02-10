package com.qutap.dash.service;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qutap.dash.config.ReadQutapProperties;
import com.qutap.dash.customException.MemoryUsagesException;
import com.qutap.dash.model.MemoryUsagesModel;
@Service
public class MemoryUsagesServiceImpl implements MemoryUsagesService {
	Logger log = LoggerFactory.getLogger(MemoryUsagesServiceImpl.class);
	@Autowired
	ReadQutapProperties readQutapProperties;

	@SuppressWarnings("restriction")
	@Override
	public MemoryUsagesModel getMemoryUsages() {
		
		MemoryUsagesModel memoryUsagesModel;
		try {
			String portNum = readQutapProperties.getProfilerPortNum();
			String remoteIpProfiler = readQutapProperties.getRemoteIpProfiler();
			memoryUsagesModel = new MemoryUsagesModel();
			MBeanServer connection = ManagementFactory.getPlatformMBeanServer();
			HashMap<String, Object> env = new HashMap<String, Object>();
			JMXServiceURL url = new JMXServiceURL(
					"service:jmx:rmi:///jndi/rmi://" + remoteIpProfiler + ":" + portNum + "/jmxrmi");
			JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(url, env, connection);
			Set<ObjectInstance> set = connection.queryMBeans(new ObjectName("java.lang:type=Memory"), null);
			ObjectInstance oi = set.iterator().next();
			// replace "HeapMemoryUsage" with "NonHeapMemoryUsage" to get non-heap mem
			Object attrValue = connection.getAttribute(oi.getObjectName(), "HeapMemoryUsage");
			Object attrNonHeapValue = connection.getAttribute(oi.getObjectName(), "NonHeapMemoryUsage");
			if (!(attrValue instanceof CompositeData)) {
				System.out.println("attribute value is instanceof [" + attrValue.getClass().getName()
						+ ", exitting -- must be CompositeData.");
				return null;
			}
			
			/*
			 * 
			 * Class Loading Information....  
			 * 
			 * 
			 */
			
			ClassLoadingMXBean classLoading = ManagementFactory.getClassLoadingMXBean();
			int loadedClassCount=classLoading.getLoadedClassCount();
			long totalLoadedClassCount=classLoading.getTotalLoadedClassCount();
			long unloadedClassCount=classLoading.getUnloadedClassCount();
			//ObjectName objectNameClassLoaded=classLoading.getObjectName();
			
			
			
			
			
			// heap Area Memory Information
			String heapUsedMemory = ((CompositeData) attrValue).get("used").toString();
			int heapUsed = (Integer.parseInt(heapUsedMemory) / 2048);
			String heapMaxMemory = ((CompositeData) attrValue).get("max").toString();
			int heapMax = (Integer.parseInt(heapMaxMemory) / 2048);
			String heapInit = ((CompositeData) attrValue).get("init").toString();
			int heapInit1 = (Integer.parseInt(heapInit) / 2048);
			String heapCommitted = ((CompositeData) attrValue).get("committed").toString();
			int heapCommited = (Integer.parseInt(heapCommitted) / 2048);

			// Nonheap Area Memory Information
			String nonHeapUsedMemory = ((CompositeData) attrNonHeapValue).get("used").toString();
			int nonHeapUsed = (Integer.parseInt(nonHeapUsedMemory) / 2048); 
			String nonHeapInit = ((CompositeData) attrNonHeapValue).get("init").toString();
			int nonHeapInit1 = (Integer.parseInt(nonHeapInit) / 2048);
			String nonHeapCommitted = ((CompositeData) attrNonHeapValue).get("committed").toString();
			int nonHeapCommited = (Integer.parseInt(nonHeapCommitted) / 2048);

			// Operating system memory information
			com.sun.management.OperatingSystemMXBean mxbean = (com.sun.management.OperatingSystemMXBean) ManagementFactory
					.getOperatingSystemMXBean();

			// Get the number of processors
			int numProcessors = mxbean.getAvailableProcessors();

			// Get the Oracle JDK-specific attribute Process CPU time
			long cpuTime = mxbean.getProcessCpuTime()/1000;
			long committedVirtualMemorySize = mxbean.getCommittedVirtualMemorySize()/2048;
			
			long freePhysicalMemorySize = mxbean.getFreePhysicalMemorySize()/2048;
		
			double processCpuLoad = mxbean.getProcessCpuLoad();
			long processCputime = mxbean.getProcessCpuTime()/1000;
			double systemCpuLoad = mxbean.getSystemCpuLoad();
			long totalPhysicalMemorySize = mxbean.getTotalPhysicalMemorySize()/2048;
			
			long totalSwapSpaceSize = mxbean.getTotalSwapSpaceSize()/2048;
			

			ObjectName operatingSystemName = new ObjectName(ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME);

			String vendor = (String) connection.getAttribute(operatingSystemName, "Name");

			// Thread Memory information
			ThreadMXBean conn = ManagementFactory.getThreadMXBean();
			int threadCount = conn.getThreadCount();
			long currentThreadCpuTime = conn.getCurrentThreadCpuTime()/1000;
			int daemonThreadCount = conn.getDaemonThreadCount();
			long totalThreadStartedCount = conn.getTotalStartedThreadCount();
			int peakThreadCount = conn.getPeakThreadCount();
			long currentThreadUserTime = conn.getCurrentThreadUserTime()/1000;

			/*
			 * Garbage Collector memory information...
			 * 
			 * 
			 */
			
			
			Map<String, Object> collectionTimeMap = new HashMap<>();
			Map<String, Object> isValidMap = new HashMap<>();
			Map<String, Object> collectionCountMap = new HashMap<>();
			Map<String, Object> memoryPoolNamesMap = new HashMap<>();
			List<java.lang.management.GarbageCollectorMXBean> garbageCollectorMXBean = ManagementFactory
					.getGarbageCollectorMXBeans();

			List<Object> collectionTimeList = garbageCollectorMXBean.stream().map(i -> i.getCollectionTime())
					.collect(Collectors.toList());
			for (Object collectionTime : collectionTimeList) {
				collectionTimeMap.put("GarbageCollectorCollectionTime", collectionTime);
			}
			List<Object> isValidList = garbageCollectorMXBean.stream().map(i -> i.isValid())
					.collect(Collectors.toList());
			for (Object isVAlid : isValidList) {
				isValidMap.put("GarbageCollectorIsValid", isVAlid);
			}

			List<Object> collectionCountList = garbageCollectorMXBean.stream().map(i -> i.getCollectionCount())
					.collect(Collectors.toList());
			for (Object collectionCount : collectionCountList) {
				collectionCountMap.put("GarbageCollectorCollectionCount", collectionCount);
			}
			List<Object> memoryPoolNameList = garbageCollectorMXBean.stream().map(i -> i.getMemoryPoolNames())
					.collect(Collectors.toList());
			for (Object memoryPoolNames : memoryPoolNameList) {
				memoryPoolNamesMap.put("GarbagrCollectorMemoryPoolName", memoryPoolNames);
			}
			memoryUsagesModel.setTotalLoadedClassCount(totalLoadedClassCount);
			memoryUsagesModel.setLoadedClassCount(loadedClassCount);
			memoryUsagesModel.setTotalUnloadedClassCount(unloadedClassCount);
			memoryUsagesModel.setCommittedVirtual_MemorySize(committedVirtualMemorySize + "mb");
			memoryUsagesModel.setCpuTime(cpuTime + "second");
			memoryUsagesModel.setFreePhysical_MemorySize(freePhysicalMemorySize + "mb");
			memoryUsagesModel.setCommitted_HeapSize((heapCommited + "mb"));
			memoryUsagesModel.setInit_HeapSize(heapInit1 + "mb");
			memoryUsagesModel.setMax_HeapSize(heapMax + "mb");
			memoryUsagesModel.setCurrent_HeapSize(heapUsed + "mb");
			memoryUsagesModel.setCommitted_NonHeapSize(nonHeapCommited + "mb");
			memoryUsagesModel.setInit_NonHeapSize(nonHeapInit1 + "mb");
			memoryUsagesModel.setCurrent_NonHeapSize(nonHeapUsed + "mb");
			memoryUsagesModel.setNumberOf_Processors(numProcessors);
			memoryUsagesModel.setProcessCpuLoad(processCpuLoad);
			memoryUsagesModel.setProcessCputime(processCputime + "second");
			memoryUsagesModel.setSystemCpuLoad(systemCpuLoad);
			
			memoryUsagesModel.setTotalPhysical_MemorySize(totalPhysicalMemorySize + "mb");
			memoryUsagesModel.setTotalSwap_SpaceSize(totalSwapSpaceSize + "mb");
			memoryUsagesModel.setThreadCount(threadCount);
			memoryUsagesModel.setCurrentThread_CpuTime(currentThreadCpuTime + "second");
			memoryUsagesModel.setDaemonThreadCount(daemonThreadCount);
			memoryUsagesModel.setTotalThreadStartedCount(totalThreadStartedCount);
			memoryUsagesModel.setPeakThreadCount(peakThreadCount);
			memoryUsagesModel.setCurrentThreadUserTime(currentThreadUserTime + "second");

			memoryUsagesModel.setCollectionCount(collectionCountMap);
			memoryUsagesModel.setIsValid(isValidMap);
			memoryUsagesModel.setCollectionTime(collectionTimeMap);
			memoryUsagesModel.setMemoryPoolNames(memoryPoolNamesMap);
			// memoryUsagesModel.setCollectionCount(collectionCountMap);
		} catch (Exception e) {
			log.error("error in getting memory Usages ingormation", e);
			throw new MemoryUsagesException("error in getting memory Usages ingormation");
		}
		return memoryUsagesModel;

	}


}
