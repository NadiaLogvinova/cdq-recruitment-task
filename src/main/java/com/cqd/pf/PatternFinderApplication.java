package com.cqd.pf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableMongoRepositories
@EnableWebMvc
@EnableCaching
public class PatternFinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatternFinderApplication.class, args);
    }

}
