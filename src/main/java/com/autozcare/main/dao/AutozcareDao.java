package com.autozcare.main.dao;

import java.util.Optional;

import com.autozcare.main.enums.RoleName;
import com.autozcare.main.model.Role;
import com.autozcare.main.model.User;

public interface AutozcareDao {
	
	Optional<User> findUserByMobileNumber(String mobileNumber);
	
	Optional<User> saveUser(User user);
	
	Optional<Role> findRoleByName(RoleName roleName);
	
	public Boolean existsUserByMobileNumber(String mobileNumber);

}
