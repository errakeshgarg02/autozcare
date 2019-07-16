package com.autozcare.main.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {

	public UserDetails loadUserById(Long id);
	
	public UserDetails findUserByMobileNumber(String mobileNumber);
}
