package com.autozcare.main.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.autozcare.main.dao.RoleRepository;
import com.autozcare.main.dao.UserRepository;
import com.autozcare.main.dto.ApiResponse;
import com.autozcare.main.dto.JwtAuthenticationResponse;
import com.autozcare.main.dto.LoginRequest;
import com.autozcare.main.dto.SignUpRequest;
import com.autozcare.main.enums.RoleName;
import com.autozcare.main.exception.AppException;
import com.autozcare.main.model.Role;
import com.autozcare.main.model.User;
import com.autozcare.main.security.JwtTokenProvider;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping(value = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserId(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/customer/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        
        if(userRepository.existsByMobileNumber(signUpRequest.getMobileNumber())) {
            return new ResponseEntity(new ApiResponse(false, "Mobile number already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        
        User user = new User();
        BeanUtils.copyProperties(signUpRequest, user);
        // Creating user's account
		/*
		 * User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
		 * signUpRequest.getEmail(), signUpRequest.getPassword());
		 */
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        user.setDob(LocalDate.parse(signUpRequest.getDob()));
        
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
    
    @SuppressWarnings("unchecked")
    @PostMapping(value = "/admin/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        
        if(userRepository.existsByMobileNumber(signUpRequest.getMobileNumber())) {
            return new ResponseEntity(new ApiResponse(false, "Mobile number already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        
        User user = new User();
        BeanUtils.copyProperties(signUpRequest, user);
        // Creating user's account
		/*
		 * User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
		 * signUpRequest.getEmail(), signUpRequest.getPassword());
		 */
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        user.setDob(LocalDate.parse(signUpRequest.getDob()));
        
        Role userRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
    
    @SuppressWarnings("unchecked")
    @PostMapping(value = "/vendor/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerVendor(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        
        if(userRepository.existsByMobileNumber(signUpRequest.getMobileNumber())) {
            return new ResponseEntity(new ApiResponse(false, "Mobile number already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        
        User user = new User();
        BeanUtils.copyProperties(signUpRequest, user);
        // Creating user's account
		/*
		 * User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
		 * signUpRequest.getEmail(), signUpRequest.getPassword());
		 */
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        user.setDob(LocalDate.parse(signUpRequest.getDob()));
        
        Role userRole = roleRepository.findByName(RoleName.ROLE_VENDOR)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
    
    @SuppressWarnings("unchecked")
    @PostMapping(value = "/employee/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerEmployee(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        
        if(userRepository.existsByMobileNumber(signUpRequest.getMobileNumber())) {
            return new ResponseEntity(new ApiResponse(false, "Mobile number already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        
        User user = new User();
        BeanUtils.copyProperties(signUpRequest, user);
        // Creating user's account
		/*
		 * User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
		 * signUpRequest.getEmail(), signUpRequest.getPassword());
		 */
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        user.setDob(LocalDate.parse(signUpRequest.getDob()));
        
        Role userRole = roleRepository.findByName(RoleName.ROLE_EMPLOYEE)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}