package com.eaccount.partner.java.bo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "application.bo", ignoreUnknownFields = true, ignoreInvalidFields = true)
@Data
public class BoProperties {

	public String test;
	public String testProp;
}
