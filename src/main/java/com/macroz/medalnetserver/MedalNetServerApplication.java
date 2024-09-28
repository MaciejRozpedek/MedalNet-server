package com.macroz.medalnetserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class MedalNetServerApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(MedalNetServerApplication.class);

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(MedalNetServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Retrieve MySQL properties from the environment
		String dbUrl = env.getProperty("spring.datasource.url");
		String dbUsername = env.getProperty("spring.datasource.username");
		String dbPassword = env.getProperty("spring.datasource.password");

		// Log MySQL configuration
		logger.info("MySQL Configuration (before application starts):");
		logger.info("URL: {}", dbUrl);
		logger.info("Username: {}", dbUsername);

		// Avoid logging passwords in production; for debugging only
		logger.info("Password: {}", dbPassword);
	}
}
