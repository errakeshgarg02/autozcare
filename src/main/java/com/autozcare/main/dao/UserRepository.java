package com.autozcare.main.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.autozcare.main.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	public Optional<User> findByUsernameOrEmail(String username, String emailId);
	
	public Boolean existsByUsername(String username);
	
	public Boolean existsByEmail(String email);
	
	

}
