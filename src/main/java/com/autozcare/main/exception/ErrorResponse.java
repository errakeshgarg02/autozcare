package com.autozcare.main.exception;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse implements Serializable {
	
	private static final long serialVersionUID = 4574868965065648297L;
	private int errorCode;
	private String errorDesc;
	private String userDesc;
	

}
