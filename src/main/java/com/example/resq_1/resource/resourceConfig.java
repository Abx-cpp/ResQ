package com.example.resq_1.resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.example.resq_1.resource.resourcerepository;
@Configuration
@EnableJpaRepositories(basePackages = "com.example.resq_1.resource")
public class resourceConfig {
    // Configuration specific to volunteer module
}