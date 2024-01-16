package com.cqd.pf.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String TASK_CACHE_KEY_PATTERN = "#id";
    public static final String TASK_REQUEST_CACHE_KEY_PATTERN = "#taskRequest.input.length() + #taskRequest.input + #taskRequest.pattern";
    public static final String SUBSTRING_MATCHER_CACHE_KEY_PATTERN = "#input + #pattern";

}
