package com.autozcare.main.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.autozcare.main.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	public Optional<User> findByUsernameOrEmailOrMobileNumber(String username, String emailId, String mobileNumber);
	
	public Boolean existsByUsername(String username);
	
	public Boolean existsByEmail(String email);
	
	public Boolean existsByMobileNumber(String mobileNumber);
	
	

}
