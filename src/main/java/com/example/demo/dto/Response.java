package com.example.demo.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {
	
	String message;
	int statusCode;
	
	public static ResponseEntity<Response> getResponse(String message, Object data, HttpStatus status) {
		return ResponseEntity.status(status).body(new Response( message, status.value()));
	}
	
	public static ResponseEntity<Response> getResponse(String message, Object data) {
		return ResponseEntity.status(HttpStatus.OK).body(new Response( message, HttpStatus.OK.value()));
	}



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public Response( String message, int statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}

	public Response() {
		super();
	}

}