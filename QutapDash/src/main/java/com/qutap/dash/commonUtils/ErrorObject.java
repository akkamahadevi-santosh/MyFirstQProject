package com.qutap.dash.commonUtils;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class ErrorObject {
	
	private String title;
	private String status;
	private HttpStatus code;
	private String message;
	private String errorMessage;
	private Date date;
	private String url;
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public HttpStatus getCode() {
		return code;
	}
	public void setCode(HttpStatus internalServerError) {
		this.code = internalServerError;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
