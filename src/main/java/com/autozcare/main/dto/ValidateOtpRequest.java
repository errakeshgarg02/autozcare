package com.autozcare.main.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class ValidateOtpRequest implements Serializable {

	private static final long serialVersionUID = 3099155454399757055L;
	
	@NotNull
    @Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="Mobile number is not correct")
	private String mobileNumber;
	
	@NotNull
	private String otp;
}
