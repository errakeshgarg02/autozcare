package com.autozcare.main.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class AutozcareException extends RuntimeException {

	private static final long serialVersionUID = -6096109582802973520L;

	private HttpStatus httpStatus;

	public AutozcareException(String message) {
		super(message);
	}

	public AutozcareException(String message, Throwable cause) {
		super(message, cause);
	}

	public AutozcareException(HttpStatus httpStatus, String message) {
		super(message);
		this.httpStatus = httpStatus;
	}

}
