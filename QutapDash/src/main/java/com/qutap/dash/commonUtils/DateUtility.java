package com.qutap.dash.commonUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtility {

	public static String FORMAT_1 = "dd-M-yyyy hh:mm:ss";

	public static String getDate(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);

	}

	public static String getPrevious7Days(String dateformat, LocalDateTime localDateTime) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(dateformat);
		// LocalDateTime now = LocalDateTime.now();
		LocalDateTime then = localDateTime.minusDays(6);
		return then.format(format);
	}

	public static String getPrevious30Days(String dateformat, LocalDateTime localDateTime) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(dateformat);
		// LocalDateTime now = LocalDateTime.now();
		LocalDateTime then = localDateTime.minusDays(32);
		return then.format(format);
	}

	public static String getDuration(String startDateValue, String endDateValue) throws ParseException {
//		SimpleDateFormat dateFormat=new SimpleDateFormat(DateUtility.FORMAT_1);
//		Date startDate=dateFormat.parse(startDateValue);
//		Date endDate=dateFormat.parse(endDateValue);
//		 long diff = endDate.getTime() - startDate.getTime();
//		    long diffSeconds = diff / 1000 % 60;
//		    long diffMinutes = diff / (60 * 1000) % 60;
//		    long diffHours = diff / (60 * 60 * 1000);
//		return String.valueOf(diffHours+":"+diffMinutes+":"+diffSeconds);
		long diff = Long.parseLong(endDateValue) - Long.parseLong(startDateValue);
		long diffMilliseconds = diff % 1000;
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000);
		return String.valueOf(diffHours + ":" + diffMinutes + ":" + diffSeconds + "." + diffMilliseconds);

	}

	public static Date getStringToDate(String DateValue, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.parse(DateValue);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Date getStringToDate1(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			String value = dateFormat.format(date);
			return dateFormat.parse(value);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Date getStringToDate2(LocalDateTime date, String format) {
		Date in = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
		return in;

	}

	public static String getTommarowDate() {
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_1);
		String tommarowDate = dateFormat.format(dt);
		return tommarowDate;
	}

	public static long getWeekofYear(String input) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_1);
		Date date = df.parse(input);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		return week;
	}

	public static String AddingDaysDate(String dateValue) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_1);
		Calendar c = Calendar.getInstance();
		c.setTime(df.parse(dateValue));
		c.add(Calendar.DAY_OF_MONTH, 1);
		Date dt = c.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_1);
		String tommarowDate = dateFormat.format(dt);
		return tommarowDate;
	}

}
