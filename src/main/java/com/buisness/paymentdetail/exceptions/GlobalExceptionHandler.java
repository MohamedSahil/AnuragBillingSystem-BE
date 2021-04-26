package com.buisness.paymentdetail.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleExceptions(Exception e){
		
		ErrorMessage em = new ErrorMessage(401, new Date(), e.getMessage());
		return new ResponseEntity<Object>(em,new HttpHeaders(),HttpStatus.BAD_REQUEST);
//		return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
//				.body(e.getMessage());
		
	
	}
	
}

class ErrorMessage{
	int status;
	Date time;
	String message;
	
	
	public ErrorMessage(int status, Date time, String message) {
		super();
		this.status = status;
		this.time = time;
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
	
}
