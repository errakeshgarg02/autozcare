package com.autozcare.main.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class SignUpRequest implements Serializable {

	private static final long serialVersionUID = 7656582437045125410L;

	@NotBlank
	private String name;

	@Email
	private String email;

	@NotNull
	@Pattern(regexp = "[\\s]*[0-9]*[1-9]+", message = "Mobile number is not correct")
	private String mobileNumber;

	private String alternateMobileNumber;

	@NotBlank
	private String password;

	private String dob;

	private Integer noOfTeamMember;
	
	private String businessName;
	
	private String building;
	
	private String street;
	
	private String locality;
	
	private Long pincode;
	
	private String city;
	
	private String state;
	
	private String country;
}