package com.example.resq_1.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface adminrepository extends JpaRepository<admin, Integer> {
    // Custom query methods can be added here if needed
}
