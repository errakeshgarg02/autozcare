package com.autozcare.main.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerLoginResponse implements Serializable {

	private static final long serialVersionUID = 9137754227057242766L;
	
	private String mobileNumber;
	private String message;

}
