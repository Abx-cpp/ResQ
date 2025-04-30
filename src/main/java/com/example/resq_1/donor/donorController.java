package com.example.resq_1.donor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.resq_1.disaster.disaster;
import com.example.resq_1.disaster.disasterService;
import com.example.resq_1.donation.donation;
import com.example.resq_1.donation.donationService;
import com.example.resq_1.resource.resourceService;

import java.util.Optional;

@RestController
@RequestMapping("/donors")
public class donorController {

    @Autowired
    private donorService donorService;

    @Autowired
    private disasterService disasterService;

    @Autowired
    private donationService donationService;

    @Autowired
    private resourceService resourceService; // Inject resource service

    // Create or Update Donor
    @PostMapping
    public donor addOrUpdateDonor(@RequestBody donor donor) {
        return donorService.saveDonor(donor);
    }

    // Get Donor by ID
    @GetMapping("/{donor_id}")
    public Optional<donor> getDonor(@PathVariable int donor_id) {
        return donorService.getDonorById(donor_id);
    }

    // Donation Endpoint donor is donating now
    @PostMapping("/donate")
    public ResponseEntity<?> donate(@RequestBody donation donationRequest) {
        // Log the incoming donation request
        System.out.println("Donation request: " + donationRequest);

        // Validate donor
        Optional<donor> donorOpt = donorService.getVerifiedDonorById(donationRequest.getDonor().getDonor_id());
        if (donorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Donor not verified or does not exist.");
        }
        donor donor = donorOpt.get();
        System.out.println("Verified Donor: " + donor);

        // Validate disaster
        Optional<disaster> disasterOpt = disasterService.getDisasterById(donationRequest.getDisaster().getDisasterId());
        if (disasterOpt.isEmpty() || !"verified".equalsIgnoreCase(disasterOpt.get().getVerificationStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Disaster not verified or does not exist.");
        }
        disaster disaster = disasterOpt.get();
        System.out.println("Verified Disaster: " + disaster);

        // Ensure the donation request contains valid data
        if (donationRequest.getItemCategory() == null || donationRequest.getItemQuantity() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid donation data: itemCategory or itemQuantity is missing.");
        }

        // Set donor and disaster
        donationRequest.setDonor(donor);
        donationRequest.setDisaster(disaster);

        // Save donation
        try {
            donation savedDonation = donationService.saveDonation(donationRequest);

            // Update resource quantity based on donation subtype
            resourceService.updateResourceQuantity(donationRequest.getItemCategory(), donationRequest.getItemQuantity(), false);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedDonation);
        } catch (Exception e) {
            System.out.println("Error while saving donation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while processing the donation: " + e.getMessage());
        }
    }

}
