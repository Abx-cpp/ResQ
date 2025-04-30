package com.example.resq_1.donation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/donations")
public class donationController {

    @Autowired
    private donationService donationService;
    @Autowired
    private donationrepository donationRepo;
    @GetMapping
    public List<donation> getAllDonations() {
        return donationService.getAllDonations();
    }

    @GetMapping("/{id}")
    public Optional<donation> getDonationById(@PathVariable Long id) {
        return donationService.getDonationById(id);
    }

    @PostMapping
    public donation createDonation(@RequestBody donation donation) {
        return donationService.saveDonation(donation);
    }

    @DeleteMapping("/{id}")
    public void deleteDonation(@PathVariable Long id) {
        donationService.deleteDonation(id);
    }
// Get total donation count
    @GetMapping("/total-donations")
    public long getTotalDonations() {
        return donationRepo.count();
    }

    @GetMapping("/top-donors")
    public ResponseEntity<List<Map<String, Object>>> getTopDonors() {
        return ResponseEntity.ok(donationService.getTop4MoneyDonors());
    }

    @GetMapping("/monthly-comparison")
    public ResponseEntity<List<Map<String, Object>>> getMonthlyDonationComparison() {
        return ResponseEntity.ok(donationService.getLast6MonthsDonationComparison());
    }

}

