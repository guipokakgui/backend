package com.eaccount.partner.java.bo;

import java.io.IOException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.gsitm.ustra.java.mvc.app.ServletApplicationRunner;

@SpringBootApplication
public class BoApplication extends ServletApplicationRunner {

	public BoApplication() {
		super(BoApplication.class);
	}

	public static void main(String[] args) throws IOException {
		final SpringApplicationBuilder builder = new SpringApplicationBuilder(BoApplication.class);
		addProperties(builder);
		ServletApplicationRunner.run(BoApplication.class, builder, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		addProperties(builder);
		super.configure(builder);
		builder.main(BoApplication.class);

		return builder;
	}

	private static void addProperties(SpringApplicationBuilder builder) {
		builder.properties("spring.config.location=classpath:/config/partner-config.yml");
	}
}
