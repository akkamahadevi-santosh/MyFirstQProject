package com.exilant.qutap.plugin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.zip.DataFormatException;



public class DateUtility {
		
	public static String FORMAT_1 ="dd-M-yyyy hh:mm:ss";
	
	
	public static String getDate(Date date,String format){
		SimpleDateFormat dateFormat=new SimpleDateFormat(format);		
		return dateFormat.format(date);	
		
	}

	public static String getDuration(String startDateValue,String endDateValue) {
		try {
			long diff=Long.parseLong(endDateValue)-Long.parseLong(startDateValue);
			long diffMilliseconds = diff % 1000 ;
		    long diffSeconds = diff / 1000 % 60;
		    long diffMinutes = diff / (60 * 1000) % 60;
		    long diffHours = diff / (60 * 60 * 1000);
		    return String.valueOf(diffHours+":"+diffMinutes+":"+diffSeconds+"."+diffMilliseconds);
		}catch (Exception e) {
		throw new RuntimeException(e);	
		}
		
	}
	
}
