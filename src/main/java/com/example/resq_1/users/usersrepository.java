package com.example.resq_1.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface usersrepository extends JpaRepository<users, Integer> {

    // Custom query to find user by email (since email is UNIQUE in DB)
  //  Optional<users> findByEmail(String email);
   // users findByUsername(String username);
    // Custom query to check if email exists (for registration)
    boolean existsByEmail(String email);
    users findByEmail(String email);


    // Add other custom queries as needed
    // Example: Find users by role, phone number, etc.
}