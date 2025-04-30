package com.example.resq_1.donor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.resq_1.donor")
public class donorConfig {

    @Bean
    public String config() {
        // Any specific configuration if needed
        return "Donor configuration initialized";
    }
}

