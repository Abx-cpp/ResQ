package com.example.resq_1.donor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface donorrepository extends JpaRepository<donor, Integer> {
}
