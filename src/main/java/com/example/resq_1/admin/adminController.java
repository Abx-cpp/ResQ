package com.example.resq_1.admin;

import com.example.resq_1.disaster.disaster;
import com.example.resq_1.disaster.disasterService;
import com.example.resq_1.volunteer.volunteer;
import com.example.resq_1.volunteer.volunteerService;
import com.example.resq_1.donation.donation;
import com.example.resq_1.donation.donationService;
import com.example.resq_1.donor.donor;
import com.example.resq_1.donor.donorService;
import com.example.resq_1.aid.aid;
import com.example.resq_1.aid.aidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class adminController {

    @Autowired
    private adminService adminService;

    @Autowired
    private disasterService disasterService;

    @Autowired
    private volunteerService volunteerService;

    @Autowired
    private donationService donationService;

    @Autowired
    private aidService aidService;

//verification of disasters
    @PutMapping("/verify-disaster/{id}")
    public ResponseEntity<String> verifyDisaster(@PathVariable Long id) {
        try {
            String response = adminService.verifyDisaster(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

// adding the disasters
    @PostMapping("/disasters/add")
    public ResponseEntity<disaster> addDisaster(@RequestBody disaster newDisaster) {
        disaster added = disasterService.addDisaster(newDisaster);
        return new ResponseEntity<>(added, HttpStatus.CREATED);
    }
// delete disaster
    @DeleteMapping("/disasters/{id}")
    public ResponseEntity<String> removeDisaster(@PathVariable Long id) {  // Changed from int to Long
        try {
            boolean deleted = disasterService.removeDisaster(id);
            if (deleted) {
                return ResponseEntity.ok("Disaster removed successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Disaster not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error removing disaster: " + e.getMessage());
        }
    }

// show the list of all disasters
    @GetMapping("/disasters")
    public ResponseEntity<List<disaster>> getAllDisasters() {
        return ResponseEntity.ok(disasterService.getAllDisasters());
    }
// show all volunteers and their data
    @GetMapping("/volunteers")
    public ResponseEntity<List<volunteer>> viewAllVolunteers() {
        List<volunteer> volunteers = volunteerService.getAllVolunteers();
        return new ResponseEntity<>(volunteers, HttpStatus.OK);
    }
    // show all doantions
    @GetMapping("/donations")
    public ResponseEntity<List<donation>> viewDonations() {
        List<donation> donations = donationService.getAllDonations();
        return new ResponseEntity<>(donations, HttpStatus.OK);
    }
    //verify the donor
    @PatchMapping("/verify-donor/{donor_id}")
    public ResponseEntity<?> verifyDonor(@PathVariable int donor_id) {
        donor verifiedDonor = adminService.verifyDonor(donor_id);
        if (verifiedDonor != null) {
            return ResponseEntity.ok(verifiedDonor);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Donor not found.");
        }
    }
    // show all requests
    @GetMapping("/admin/aid-requests")
    public List<aid> getAllAidRequests() {
        return adminService.getAllAidRequests();  // Ensure this method retrieves all aid requests
    }

    @PutMapping("/verify-aid/{aidId}")
    public ResponseEntity<String> verifyAidRequest(@PathVariable Long aidId) {
        try {
            aid verifiedAid = adminService.verifyAidRequest(aidId.intValue());
            return ResponseEntity.ok("Aid request with ID " + aidId + " verified successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }




}