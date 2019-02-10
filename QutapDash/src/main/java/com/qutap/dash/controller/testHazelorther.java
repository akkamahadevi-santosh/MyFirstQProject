package com.qutap.dash.controller;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class testHazelorther {

    public static void main(String[] args) throws Exception {
    	
	  RestTemplate restTemplate = new RestTemplate();
		

			
			
		/*String s1=	restTemplate.getForObject("http://localhost:9000/Qutap/execution/testCaseCount",String.class);
		System.out.println("ssssssssssssssssssss11111111111111"+s1); */
		
		String s2=	restTemplate.getForObject("http://localhost:9000/Qutap/execution/testCaseCountFromHazelCast/5c3ed8f23737701425e365be",String.class);
		System.out.println("ssssssssssstestCaseCountFromHazelCastsssssssss11111111111111"+s2);
		String s32 = restTemplate.getForObject("http://localhost:9000/Qutap/execution/saveDataIntoHazelCast",
				String.class);
		
		String URL = "http://localhost:9000/Qutap/execution/getProfileDataFromHazelCast/Tririga leas";
		URL = URL.replaceAll("%20", " ");

		 String s3=restTemplate.getForObject(URL,String.class);
		 JSONObject jsonObject = new JSONObject(s3);
//    	Date dt = new Date();
//    	Calendar c = Calendar.getInstance(); 
//    	c.setTime(dt); 
//    	c.add(Calendar.DATE, 1);
//    	dt = c.getTime();

	
}
}