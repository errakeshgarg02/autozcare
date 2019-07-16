package com.autozcare.main.dao.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.autozcare.main.dao.AutozcareDao;
import com.autozcare.main.enums.RoleName;
import com.autozcare.main.model.Role;
import com.autozcare.main.model.User;
import com.autozcare.main.repository.RoleRepository;
import com.autozcare.main.repository.UserRepository;

@Repository
public class AutozcareDaoImpl implements AutozcareDao {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Optional<User> findUserByMobileNumber(String mobileNumber) {
		return userRepository.findByMobileNumber(mobileNumber);
	}

	@Override
	public Optional<User> saveUser(User user) {
		return Optional.ofNullable(user).map(userRepository::save);
	}

	@Override
	public Optional<Role> findRoleByName(RoleName roleName) {
		return roleRepository.findByName(roleName);
	}

	@Override
	public Boolean existsUserByMobileNumber(String mobileNumber) {
		return userRepository.existsByMobileNumber(mobileNumber);
	}

}
