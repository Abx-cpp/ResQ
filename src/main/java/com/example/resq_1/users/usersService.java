package com.example.resq_1.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class usersService {

    @Autowired
    private usersrepository userRepo;

    // Create/Register a new user
    public users createUser(users newUser) {
        // No password encoding here, store password as plain text
        newUser.setCreated_at(new Timestamp(System.currentTimeMillis()));
        return userRepo.save(newUser);
    }

    // Get all users
    public List<users> getAllUsers() {
        return userRepo.findAll();
    }

    // Get user by ID
    public Optional<users> getUserById(int user_id) {
        return userRepo.findById(user_id);
    }

    // Update user
    public users updateUser(int user_id, users updatedUser) {
        return userRepo.findById(user_id).map(user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPhone_no(updatedUser.getPhone_no());
            user.setAddress(updatedUser.getAddress());
            user.setRole(updatedUser.getRole());
            return userRepo.save(user);
        }).orElse(null);
    }

    // Delete user
    public void deleteUser(int user_id) {
        userRepo.deleteById(user_id);
    }

    // Custom method: Check if email exists
    public boolean emailExists(String email) {
        return userRepo.existsByEmail(email);
    }

    // Create/Register a new user (Sign Up logic)
    public users signUpUser(users newUser) {
        // Check if the email already exists
        if (userRepo.existsByEmail(newUser.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }

        // Set creation timestamp
        newUser.setCreated_at(new Timestamp(System.currentTimeMillis()));

        // Save the user to the database
        return userRepo.save(newUser);
    }

    public String login(String email, String password) {
        // Find the user by email
        users user = userRepo.findByEmail(email);

        // Check if the user exists and if the password matches (no password encoding here)
        if (user != null && password.equals(user.getPassword())) {
            // Return the role if login is successful
            return "Login successful. Role: " + user.getRole();
        } else {
            return "Invalid email or password";
        }
    }
}
