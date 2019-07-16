package com.autozcare.main.integration;

import java.text.MessageFormat;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.autozcare.main.config.PropertyValueMapper;
import com.autozcare.main.constants.AutozcareConstants;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SmsApiIntegration {

	@Autowired
	private PropertyValueMapper propertyValueMapper;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public Boolean sendOtp(String mobileNumber, String otp) {
		Instant start = Instant.now();
		String url = AutozcareConstants.SMS_API_URL_FORMAT.format(new Object[] {
				propertyValueMapper.getSmsApiUrl(), 
				propertyValueMapper.getSmsApiUsername(), 
				propertyValueMapper.getSmsApiPassword(),
				propertyValueMapper.getSmsApiSenderName(),
				mobileNumber, otp});
		
		log.debug("sms api url is {}", url);
	   String response = restTemplate.getForObject(url, String.class);
	   log.debug("sms api taking total time {} ms", Instant.now().toEpochMilli() - start.toEpochMilli());
		log.debug("sms api response for mobile number {} is {}", mobileNumber, response);
		return Boolean.TRUE;
	}
}
