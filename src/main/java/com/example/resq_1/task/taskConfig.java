package com.example.resq_1.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class taskConfig {

    @Bean
    public task sampleTask() {
        // Return a sample task object with dummy data
        return new task(
                "unassigned", // task status
                "This is a sample task description.", // task description
                "on-site" // task type as a string (no enum)
        );
    }
}
