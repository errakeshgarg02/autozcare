package com.autozcare.main.constants;

import java.text.MessageFormat;

public final class AutozcareConstants {
	
	public static final String BEARER_WITH_SPACE = "Bearer ";
	
	  public static final MessageFormat SMS_API_URL_FORMAT = new MessageFormat(
		      "{0}?username={1}&password={2}&sendername={3}&mobile={4}&message=User OTP {5} for Autozcare mobile verification");
	
	private AutozcareConstants() {
		super();
	}

}
