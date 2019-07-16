package com.autozcare.main.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autozcare.main.model.User;
import com.autozcare.main.repository.UserRepository;
import com.autozcare.main.security.UserPrincipal;
import com.autozcare.main.service.CustomUserDetailsService;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findByMobileNumber(username)
                .orElseThrow(() -> 
                        new UsernameNotFoundException("User not found with mobileNumber : " + username)
        );

        return new UserPrincipal(user);
    }

    // This method is used by JWTAuthenticationFilter
    @Override
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new UsernameNotFoundException("User not found with id : " + id)
        );
        return new UserPrincipal(user);
    }

	@Override
	@Transactional
	public UserDetails findUserByMobileNumber(String mobileNumber) {
		User user = userRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with mobile number : " + mobileNumber));
		return new UserPrincipal(user);
	}
}