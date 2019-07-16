package com.autozcare.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionHandling {

	
	@ExceptionHandler({BadRequestException.class, AuthenticationException.class}) 
	public ResponseEntity<ErrorResponse> handleBadRequestException(Exception exception) {
		log.error("Error occured in handleBadRequestException ", exception);
		ErrorResponse response = ErrorResponse.builder().errorCode(HttpStatus.BAD_REQUEST.value())
		.errorDesc(HttpStatus.BAD_REQUEST.getReasonPhrase())
		.userDesc(exception.getMessage()).build();
		return ResponseEntity.badRequest().body(response);
	}
	
	@ExceptionHandler({AccessDeniedException.class}) 
	public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException exception) {
		log.error("Error occured in handleAccessDeniedException ", exception);
		ErrorResponse response = ErrorResponse.builder().errorCode(HttpStatus.UNAUTHORIZED.value())
		.errorDesc(HttpStatus.UNAUTHORIZED.getReasonPhrase())
		.userDesc(exception.getMessage()).build();
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}
	
	@ExceptionHandler({AutozcareException.class}) 
	public ResponseEntity<ErrorResponse> handleAutozcareException(AutozcareException exception) {
		log.error("Error occured in handleAutozcareException ", exception);
		ErrorResponse response = ErrorResponse.builder().errorCode(exception.getHttpStatus().value())
		.errorDesc(exception.getHttpStatus().getReasonPhrase())
		.userDesc(exception.getMessage()).build();
		return ResponseEntity.status(exception.getHttpStatus()).body(response);
	}
	
	@ExceptionHandler({Exception.class}) 
	public ResponseEntity<ErrorResponse> handleInternalServerErrorException(Exception exception) {
		log.error("Error occured in handleInternalServerErrorException ", exception);
		ErrorResponse response = ErrorResponse.builder().errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
		.errorDesc(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
		.userDesc(exception.getMessage()).build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}
