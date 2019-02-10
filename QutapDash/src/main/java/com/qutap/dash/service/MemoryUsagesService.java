package com.qutap.dash.service;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;

import com.qutap.dash.model.MemoryUsagesModel;

public interface MemoryUsagesService {
	public MemoryUsagesModel getMemoryUsages() throws InstanceNotFoundException, AttributeNotFoundException, ReflectionException, MBeanException, MalformedURLException, MalformedObjectNameException, IOException;

}
