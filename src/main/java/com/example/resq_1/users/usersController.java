package com.example.resq_1.users;

import com.example.resq_1.donor.donor;
import com.example.resq_1.donor.donorService;
import com.example.resq_1.volunteer.volunteer;
import com.example.resq_1.volunteer.volunteerService;
import com.example.resq_1.victim.victim;
import com.example.resq_1.victim.victimService;
import com.example.resq_1.GeoCodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class usersController {

    @Autowired
    private usersService userService;

    @Autowired
    private usersrepository userRepository;

    @Autowired
    private GeoCodingService geoCodingService;

    @Autowired
    private victimService victimService;

    @Autowired
    private donorService donorService;

    @Autowired
    private volunteerService volunteerService;

    // Create User
    @PostMapping
    public ResponseEntity<users> createUser(@RequestBody users newUser) {
        if (userService.emailExists(newUser.getEmail())) {
            return ResponseEntity.badRequest().body(null); // Or throw custom exception with error details
        }
        users createdUser = userService.createUser(newUser);
        return ResponseEntity.ok(createdUser);
    }

    // Get All Users
    @GetMapping
    public ResponseEntity<List<users>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Get User by ID
    @GetMapping("/{user_id}")
    public ResponseEntity<Optional<users>> getUserById(@PathVariable int user_id) {
        return ResponseEntity.ok(userService.getUserById(user_id));
    }

    // Update User
    @PutMapping("/{user_id}")
    public ResponseEntity<users> updateUser(@PathVariable int user_id, @RequestBody users updatedUser) {
        users result = userService.updateUser(user_id, updatedUser);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    // Delete User
    @DeleteMapping("/{user_id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int user_id) {
        userService.deleteUser(user_id);
        return ResponseEntity.noContent().build();
    }

//    // Custom Endpoint: Get User by Email
//    @GetMapping("/email/{email}")
//    public ResponseEntity<Optional<users>> getUserByEmail(@PathVariable String email) {
//        return ResponseEntity.ok(userService.findByEmail(email));
//    }

    // Update Coordinates based on User's Address
    @PutMapping("/updateCoordinates/{user_id}")
    public ResponseEntity<String> updateCoordinates(@PathVariable int user_id) {
        try {
            // Fetch user from the repository
            users user = userRepository.findById(user_id)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Check if the address is null or empty
            if (user.getAddress() == null || user.getAddress().isEmpty()) {
                return ResponseEntity.badRequest().body("Error: Address not available for user ID: " + user_id);
            }

            // Update coordinates
            geoCodingService.updateUserCoordinates(user_id, user.getAddress());
            return ResponseEntity.ok("Coordinates updated successfully for user ID: " + user_id);
        } catch (RuntimeException e) {
            // Catch user-related errors
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        } catch (Exception e) {
            // Catch other errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    // Sign Up - Create a new user with role-based attributes
    @PostMapping("/signup")
    public ResponseEntity<String> signUpUser(@RequestBody users newUser) {
        try {
            // Check if the user already exists by email
            if (userService.emailExists(newUser.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Email already exists!");
            }

            // Set the creation timestamp
            newUser.setCreated_at(new Timestamp(System.currentTimeMillis()));

            // Save the new user to the database
            users createdUser = userService.signUpUser(newUser);

            // Based on the role, create the corresponding entry in the specific table
            switch (newUser.getRole()) {
                case "victim":
                    // Add victim-specific details
                    victim newVictim = new victim();
                    newVictim.setUser(createdUser);
                    victimService.saveVictim(newVictim);
                    break;

                case "donor":
                    // Add donor-specific details
                    donor newDonor = new donor();
                    newDonor.setUser_id(createdUser.getUser_id());
                    newDonor.setDonor_type("Regular");  // Set default donor type, can be changed based on frontend
                    donorService.saveDonor(newDonor);
                    break;

                case "volunteer":
                    // Add volunteer-specific details
                    volunteer newVolunteer = new volunteer();
                    newVolunteer.setUser(createdUser);
                    newVolunteer.setVolunteerType("General");  // Default volunteer type, can be set from frontend
                    volunteerService.saveVolunteer(newVolunteer);
                    break;

                default:
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Invalid role provided.");
            }

            // Optionally update coordinates (e.g., using the address provided by the user)
            try {
                geoCodingService.updateUserCoordinates(createdUser.getUser_id(), createdUser.getAddress());
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error during coordinates update: " + e.getMessage());
            }

            return ResponseEntity.status(HttpStatus.CREATED).body("User successfully signed up!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during signup: " + e.getMessage());
        }
    }


        // Existing methods...

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        // Call the service to check the login details
        String response = userService.login(email, password);

        // Return appropriate response
        if (response.contains("successful")) {
            return ResponseEntity.ok(response); // Login successful, return role
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response); // Invalid email or password
        }
    }

}





