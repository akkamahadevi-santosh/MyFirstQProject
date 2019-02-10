package com.qutap.dash.domain;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.qutap.dash.domain.TestCaseDomain;
import com.qutap.dash.model.AgentMeta;

public class TestCaseData implements Serializable {

	
private static final long serialVersionUID = 1L;
	
private String moduleName ;
private String requirementName;
private TestCaseDomain testCaseDomain;
public String getModuleName() {
	return moduleName;
}
public void setModuleName(String moduleName) {
	this.moduleName = moduleName;
}
public String getRequirementName() {
	return requirementName;
}
public void setRequirementName(String requirementName) {
	this.requirementName = requirementName;
}
public TestCaseDomain getTestCaseDomain() {
	return testCaseDomain;
}
public void setTestCaseDomain(TestCaseDomain testCaseDomain) {
	this.testCaseDomain = testCaseDomain;
}


	
	

}
