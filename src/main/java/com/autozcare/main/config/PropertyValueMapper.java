package com.autozcare.main.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Configuration
@Component
@Getter
public class PropertyValueMapper {
	
	@Value("${autozcare.otp.length:5}")
	private Integer otpLength;
	
	@Value("${autozcare.sms.api.url}")
	private String smsApiUrl;
	
	@Value("${autozcare.sms.api.username}")
	private String smsApiUsername;
	
	@Value("${autozcare.sms.api.password}")
	private String smsApiPassword;
	
	@Value("${autozcare.sms.api.sender.name}")
	private String smsApiSenderName;

}
