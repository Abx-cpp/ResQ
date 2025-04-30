package com.example.resq_1.resource;

import com.example.resq_1.resource.resource;
import com.example.resq_1.resource.resourcerepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface resourcerepository extends JpaRepository<resource, Integer> {
    Optional<resource> findByResSubtype(String resSubtype);
}
