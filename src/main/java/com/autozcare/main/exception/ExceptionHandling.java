package com.autozcare.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandling {

	
	@ExceptionHandler({Exception.class}) 
	public ResponseEntity<ErrorResponse> handleException(Exception exception) {
		
		ErrorResponse response = ErrorResponse.builder().errorCode(HttpStatus.BAD_REQUEST.value())
		.errorDesc(HttpStatus.BAD_REQUEST.getReasonPhrase())
		.userDesc(exception.getMessage()).build();
		return ResponseEntity.badRequest().body(response);
	}
}
