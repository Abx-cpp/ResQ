package com.example.resq_1.volunteer;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.resq_1.volunteer")
public class volunteerConfig {
    // Configuration specific to volunteer module
}