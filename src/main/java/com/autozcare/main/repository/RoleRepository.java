package com.autozcare.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.autozcare.main.enums.RoleName;
import com.autozcare.main.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	public Optional<Role> findByName(RoleName roleName);

}
