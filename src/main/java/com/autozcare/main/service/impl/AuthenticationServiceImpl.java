package com.autozcare.main.service.impl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autozcare.main.config.PropertyValueMapper;
import com.autozcare.main.dao.AutozcareDao;
import com.autozcare.main.dto.CustomerLoginRequest;
import com.autozcare.main.dto.SignUpRequest;
import com.autozcare.main.dto.ValidateOtpRequest;
import com.autozcare.main.enums.RoleName;
import com.autozcare.main.exception.AppException;
import com.autozcare.main.exception.AutozcareException;
import com.autozcare.main.helper.AutozcareHelper;
import com.autozcare.main.helper.ValidationHelper;
import com.autozcare.main.integration.SmsApiIntegration;
import com.autozcare.main.model.Address;
import com.autozcare.main.model.Role;
import com.autozcare.main.model.User;
import com.autozcare.main.security.JwtTokenProvider;
import com.autozcare.main.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	@Autowired
	private AutozcareDao autozcareDao;
	
	@Autowired
	private AutozcareHelper autozcareHelper;
	
	@Autowired
	private PropertyValueMapper propertyValueMapper;
	
	@Autowired	
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private ValidationHelper validationHelper;
	
	@Autowired
	private SmsApiIntegration smsApiIntegration;


	@Transactional
	@Override
	public Optional<User> registerOrLoginCustomer(CustomerLoginRequest customerLoginRequest) {

		return Optional
				.ofNullable(
						autozcareDao.findUserByMobileNumber(customerLoginRequest.getMobileNumber()).orElse(new User()))
				.map(user -> {
					BeanUtils.copyProperties(customerLoginRequest, user);
					String otp = autozcareHelper.generateOtp(propertyValueMapper.getOtpLength());
					smsApiIntegration.sendOtp(customerLoginRequest.getMobileNumber(), otp);
					user.setOtp(otp);
					user.setPassword(passwordEncoder.encode(String.valueOf(otp)));
					Role userRole = autozcareDao.findRoleByName(RoleName.ROLE_USER)
							.orElseThrow(() -> new AppException("User Role not set."));
					user.setRoles(new HashSet<>(Arrays.asList(userRole)));
					return user;
				}).flatMap(autozcareDao::saveUser);

	}

	@Transactional
	@Override
	public Optional<User> validateOtp(ValidateOtpRequest validateOtpRequest) {
		return autozcareDao.findUserByMobileNumber(validateOtpRequest.getMobileNumber())
				.filter(user -> user.getOtp().equals(validateOtpRequest.getOtp()))
				.map(user -> {
					user.setValidOtp(true);
					return user;
				})
				.flatMap(user -> autozcareDao.saveUser(user));
	}

	@Override
	public Optional<User> registerUser(SignUpRequest signUpRequest, RoleName roleName) {
		validationHelper.validateSignupRequest(signUpRequest);
		User user = new User();
		BeanUtils.copyProperties(signUpRequest, user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setDob(LocalDate.parse(signUpRequest.getDob()));
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setActive(true);
		if(!StringUtils.isEmpty(signUpRequest.getBusinessName())) {
			Address address = Address.builder()
					.businessName(signUpRequest.getBusinessName())
					.building(signUpRequest.getBuilding())
					.city(signUpRequest.getCity())
					.country(signUpRequest.getCountry())
					.locality(signUpRequest.getLocality())
					.noOfTeamMember(signUpRequest.getNoOfTeamMember())
					.pincode(signUpRequest.getPincode())
					.state(signUpRequest.getState())
					.street(signUpRequest.getStreet())
					.build();
			user.setAddress(address);
		}
		
		
		user.setOtp(autozcareHelper.generateOtp(propertyValueMapper.getOtpLength()));
		smsApiIntegration.sendOtp(signUpRequest.getMobileNumber(), user.getOtp());
		Role userRole = autozcareDao.findRoleByName(roleName)
				.orElseThrow(() -> new AutozcareException(HttpStatus.INTERNAL_SERVER_ERROR, "User Role not set."));

		user.setRoles(Collections.singleton(userRole));

		return autozcareDao.saveUser(user);

	}

	@Override
	public Optional<String> validateOtpAndCreateSession(ValidateOtpRequest validateOtpRequest) {
		return autozcareDao.findUserByMobileNumber(validateOtpRequest.getMobileNumber())
		.filter(user -> user.getOtp().equals(validateOtpRequest.getOtp()))
		.map(user -> {
			user.setActive(true);
			user.setValidOtp(true);
			user.setAccountNonExpired(true);
			user.setAccountNonLocked(true);
			user.setCredentialsNonExpired(true);
			return user;
		})
		.flatMap(user -> autozcareDao.saveUser(user))	
		.filter(user -> user.getRoles().stream().anyMatch(role -> RoleName.ROLE_USER.equals(role.getName())))
		.flatMap(user -> tokenProvider.authenticateUserAndGenerateToken(user.getMobileNumber(), user.getOtp()));
	}

	@Override
	public Optional<Boolean> invalidateOtp(String mobileNumber) {
		return autozcareDao.findUserByMobileNumber(mobileNumber)
		.map(user ->{
			user.setOtp(null);
			user.setActive(false);
			user.setValidOtp(false);
			return user;
		})
		.flatMap(autozcareDao::saveUser)
		.map(user -> Boolean.TRUE);
	}

}
