package com.example.resq_1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {
       "com.example.resq_1.admin",
        "com.example.resq_1.victim",
        "com.example.resq_1.users",
        "com.example.resq_1.task",
        "com.example.resq_1.volunteer",
       "com.example.resq_1.donor"

       // "com.example.demo.victim"
})
public class ResQ1Application {
    public static void main(String[] args) {
        SpringApplication.run(ResQ1Application.class, args);
    }
}