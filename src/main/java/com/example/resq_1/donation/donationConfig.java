package com.example.resq_1.donation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.resq_1.donation")  // Enables the JPA repositories in the admin package
public class donationConfig {



    // You can add more beans related to admin services here if needed
}
