package com.cqd.pf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableMongoRepositories
@EnableWebMvc
public class PatternFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatternFinderApplication.class, args);
	}

}
