package com.autozcare.main.helper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autozcare.main.dao.AutozcareDao;
import com.autozcare.main.dto.SignUpRequest;
import com.autozcare.main.exception.BadRequestException;

@Component 
public class ValidationHelper {
	
	@Autowired
	private AutozcareDao autozcareDao;
	
	public void validateSignupRequest(SignUpRequest signUpRequest) {
		String errorMessage = null;
		if(autozcareDao.existsUserByMobileNumber(signUpRequest.getMobileNumber())) {
			errorMessage = "Mobile number already in use!";
		}
		
		if(!StringUtils.isEmpty(errorMessage)) {
			throw new BadRequestException(errorMessage);
		}
	}

}
