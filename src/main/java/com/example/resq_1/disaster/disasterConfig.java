package com.example.resq_1.disaster;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.resq_1.disaster")
@EnableTransactionManagement
public class disasterConfig {

    // Additional configuration can be added here if needed
    // For example:
    /*
    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }
    */
}