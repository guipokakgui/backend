package com.eaccount.partner.java.bo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gsitm.ustra.java.management.properties.UstraManagementBoProperties;
import com.gsitm.ustra.java.management.services.UstraManagementRefreshTokenService;

@Configuration
public class AuthenticationConfiguration {

	@Bean
	BoJwtAuthenticationProcessor authenticationProcessor(UstraManagementBoProperties properties,
			UstraManagementRefreshTokenService ustraRefreshTokenService) {
		return new BoJwtAuthenticationProcessor(properties, ustraRefreshTokenService);
	}
}
