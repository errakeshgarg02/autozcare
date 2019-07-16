package com.autozcare.main.service;

import java.util.Optional;

import com.autozcare.main.dto.CustomerLoginRequest;
import com.autozcare.main.dto.SignUpRequest;
import com.autozcare.main.dto.ValidateOtpRequest;
import com.autozcare.main.enums.RoleName;
import com.autozcare.main.model.User;

public interface AuthenticationService {
	
	public Optional<User> registerOrLoginCustomer(CustomerLoginRequest customerLoginRequest);
	
	public Optional<User> validateOtp(ValidateOtpRequest validateOtpRequest);
	
	public Optional<String> validateOtpAndCreateSession(ValidateOtpRequest validateOtpRequest);
	
	public Optional<User> registerUser(SignUpRequest signUpRequest, RoleName roleName);
	
	public Optional<Boolean> invalidateOtp(String mobileNumber);
	
}
