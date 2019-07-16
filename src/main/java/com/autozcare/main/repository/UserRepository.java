package com.autozcare.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.autozcare.main.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	public Optional<User> findByMobileNumber(String mobileNumber);
	
	public Boolean existsByMobileNumber(String mobileNumber);
	
	

}
