package com.qutap.dash.commonUtils;

import org.apache.commons.codec.binary.Base64;

import org.springframework.stereotype.Component;

@Component
public class PasswordEncAndDec {
	
	    public static String passwordEncoder(String str) {
	     
	    	  byte[] encoded = Base64.encodeBase64(str.getBytes());  
	        
	        
	        return new String(encoded);
	    }
	   
	    public static String passwordDecoder(String str) {
	      
	    	 byte[] decoded =  Base64.decodeBase64(str.getBytes()); 
	        
	        
	        return new String(decoded);
	    }
	
	
}
