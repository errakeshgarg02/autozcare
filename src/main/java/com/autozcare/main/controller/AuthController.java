package com.autozcare.main.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.autozcare.main.dto.ApiResponse;
import com.autozcare.main.dto.CustomerLoginRequest;
import com.autozcare.main.dto.CustomerLoginResponse;
import com.autozcare.main.dto.JwtAuthenticationResponse;
import com.autozcare.main.dto.LoginRequest;
import com.autozcare.main.dto.SignUpRequest;
import com.autozcare.main.dto.ValidateOtpRequest;
import com.autozcare.main.enums.RoleName;
import com.autozcare.main.exception.AutozcareException;
import com.autozcare.main.exception.BadRequestException;
import com.autozcare.main.helper.AutozcareHelper;
import com.autozcare.main.security.JwtTokenProvider;
import com.autozcare.main.service.AuthenticationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AutozcareHelper autozcareHelper;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@PostMapping(value = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		log.debug("inside authenticateUser method");
		return tokenProvider.authenticateUserAndGenerateToken(loginRequest.getUserId(), loginRequest.getPassword()).map(
				jwtToken -> autozcareHelper.prepareResponse(HttpStatus.OK, new JwtAuthenticationResponse(jwtToken)))
				.orElseThrow(() -> new AutozcareException(HttpStatus.UNAUTHORIZED, "Error logging in user!"));
	}
	
	
	@PostMapping(value = "/validateOtp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> validateOtp(@Valid @RequestBody ValidateOtpRequest validateOtpRequest) {
		log.debug("inside validateOtp method");

		return authenticationService.validateOtp(validateOtpRequest)
				.map(user -> autozcareHelper.prepareResponse(HttpStatus.OK, new ApiResponse(true, "OTP validated")))
				.orElseThrow(() -> new BadRequestException("OTP in invalid"));
	}
	 

	@PostMapping(value = "/customer/signin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerLoginResponse> registerOrLoginCustomer(
			@Valid @RequestBody CustomerLoginRequest customerLoginRequest) {
		log.debug("inside registerOrLoginCustomer method");
		return authenticationService.registerOrLoginCustomer(customerLoginRequest)
				.map(user -> autozcareHelper.prepareResponse(HttpStatus.ACCEPTED,
						CustomerLoginResponse.builder().mobileNumber(user.getMobileNumber())
								.message("login request accepted").build()))
				.orElseThrow(() -> new AutozcareException(HttpStatus.INTERNAL_SERVER_ERROR, "Error in login"));
	}

	@PostMapping(value = "/customer/validateOtp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JwtAuthenticationResponse> validateCustomerOtp(
			@Valid @RequestBody ValidateOtpRequest validateOtpRequest) {
		log.debug("inside validateCustomerOtp method");
		return authenticationService.validateOtpAndCreateSession(validateOtpRequest).map(
				jstToken -> autozcareHelper.prepareResponse(HttpStatus.OK, new JwtAuthenticationResponse(jstToken)))
				.orElseThrow(() -> new BadRequestException("OTP in invalid"));
	}

	@PostMapping(value = "/admin/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerLoginResponse> registerAdmin(@Valid @RequestBody SignUpRequest signUpRequest) {

		return authenticationService.registerUser(signUpRequest, RoleName.ROLE_ADMIN)
				.map(user -> autozcareHelper.prepareResponse(HttpStatus.ACCEPTED,
						CustomerLoginResponse.builder().mobileNumber(user.getMobileNumber())
								.message("login request accepted").build()))
				.orElseThrow(() -> new AutozcareException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Error in registering admin user"));
	}

	@PostMapping(value = "/vendor/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerLoginResponse> registerVendor(@Valid @RequestBody SignUpRequest signUpRequest) {
		return authenticationService.registerUser(signUpRequest, RoleName.ROLE_VENDOR)
				.map(user -> autozcareHelper.prepareResponse(HttpStatus.ACCEPTED,
						CustomerLoginResponse.builder().mobileNumber(user.getMobileNumber())
								.message("login request accepted").build()))
				.orElseThrow(() -> new AutozcareException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Error in registering vendor user"));
	}
	
	@PutMapping(value = "/invalidteOtp", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> invalidateOtp(@RequestParam String mobileNumber) {
		return authenticationService.invalidateOtp(mobileNumber)
		.map(flag -> autozcareHelper.prepareResponse(HttpStatus.OK, new ApiResponse(true, "OTP invalidated")))
		.orElseThrow(() -> new AutozcareException(HttpStatus.INTERNAL_SERVER_ERROR,
				"Error in invalidating user OTP for mobileNumber " + mobileNumber));
	}

}