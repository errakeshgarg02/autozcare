package com.autozcare.main.helper;

import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AutozcareHelper {

	private String numbers = "0123456789";
	private Random rndm_method = new Random();

	public String generateOtp(int len) {
		char[] otp = new char[len];
		for (int i = 0; i < len; i++) {
			otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
		}
		return String.valueOf(otp);
	}

	public <T> ResponseEntity<T> prepareResponse(HttpStatus httpStatus, T payload) {
		return ResponseEntity.status(httpStatus).body(payload);
	}
}
