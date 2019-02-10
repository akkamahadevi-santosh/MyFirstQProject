package com.qutap.dash.controller;

import java.io.IOException;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.qutap.dash.model.MemoryUsagesModel
;
import com.qutap.dash.service.MemoryUsagesService;

@RestController
@RequestMapping("/Qutap")
public class MemoryUsagesController {
	
	@Autowired
	MemoryUsagesService memoryUsagesService;
	
	@GetMapping("/getMemoryUsagesInformation")
	public @ResponseBody MemoryUsagesModel
 getMemoryUsages(HttpServletRequest req, HttpServletResponse res)
			throws IOException, InstanceNotFoundException, AttributeNotFoundException, MalformedObjectNameException, ReflectionException, MBeanException {
		MemoryUsagesModel
 MemoryUsagesModel
=memoryUsagesService.getMemoryUsages();
		return MemoryUsagesModel
;
		
	}

}
