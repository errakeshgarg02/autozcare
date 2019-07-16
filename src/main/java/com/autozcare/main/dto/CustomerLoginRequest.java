package com.autozcare.main.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class CustomerLoginRequest  implements Serializable {

	private static final long serialVersionUID = 7760917322135509809L;
	
	@NotBlank
	private String name;
    
	@NotNull
    @Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="Mobile number is not correct")
	private String mobileNumber;
}
