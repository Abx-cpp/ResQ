package com.example.resq_1.victim;

import org.springframework.data.jpa.repository.JpaRepository;

public interface victimrepository extends JpaRepository<victim, Integer> {
    // Custom methods if needed
}
