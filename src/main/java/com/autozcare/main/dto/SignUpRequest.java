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

    @NotBlank
    private String username;

    @Email
    private String email;
    
    @NotNull
    @Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="msg")
    private String mobileNumber;

    @NotBlank
    private String password;
    
    private String dob;
}