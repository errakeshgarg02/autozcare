package com.autozcare.main.intializer;

import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autozcare.main.dao.RoleRepository;
import com.autozcare.main.enums.RoleName;
import com.autozcare.main.model.Role;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DataInitializer {


	@Autowired
	private RoleRepository roleRepository;
	
	@PostConstruct
	public void saveRole() {
		log.debug("inside saveRole method");
		Stream.of(RoleName.values()).forEach(name -> {
			log.debug("Role name ::{}", name);
			Role role = new Role();
			role.setName(name);
			roleRepository.save(role);
		});
		log.debug("saveRole done");
	}
}
