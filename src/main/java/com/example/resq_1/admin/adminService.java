package com.example.resq_1.admin;

import com.example.resq_1.disaster.disaster;
import com.example.resq_1.disaster.disasterrepository;
import com.example.resq_1.donor.donor;
import com.example.resq_1.donor.donorrepository;
import com.example.resq_1.aid.aid;
import com.example.resq_1.aid.aidService;
import com.example.resq_1.resource.resource;
import com.example.resq_1.resource.resourceService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Service
public class adminService {

    @Autowired
    private disasterrepository disasterRepository;
    @Autowired
    private donorrepository donorRepository;
    @Autowired
    private aidService aidService;
    @Autowired
    private resourceService resourceService;

    // Verify a disaster
    public String verifyDisaster(Long id) {
        Optional<disaster> optionalDisaster = disasterRepository.findById(id);
        if (optionalDisaster.isPresent()) {
            disaster d = optionalDisaster.get();
            d.setVerificationStatus("verified");
            disasterRepository.save(d);
            return "Disaster with ID " + id + " verified successfully.";
        } else {
            throw new RuntimeException("Disaster not found with ID: " + id);
        }
    }

    // Add a disaster with default values
    @Transactional
    public disaster addDisaster(disaster newDisaster) {
        if (newDisaster.getAchievedDonations() == null) {
            newDisaster.setAchievedDonations(0);
        }
        if (newDisaster.getRegDate() == null) {
            newDisaster.setRegDate(LocalDateTime.now());
        }
        if (newDisaster.getVerificationStatus() == null) {
            newDisaster.setVerificationStatus("pending");
        }
        return disasterRepository.save(newDisaster);
    }

    // Remove a disaster by ID
    @Transactional
    public boolean removeDisaster(Long id) {
        if (disasterRepository.existsById(id)) {
            disasterRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Verify a donor
    public donor verifyDonor(int donor_id) {
        Optional<donor> donorOpt = donorRepository.findById(donor_id);
        if (donorOpt.isPresent()) {
            donor donor = donorOpt.get();
            donor.setVerification(true);
            return donorRepository.save(donor);
        }
        return null;
    }

    // Get all aid requests (both verified and unverified)
    public List<aid> getAllAidRequests() {
        return aidService.getAllAids(); // This should be a method in aidService to return all aid requests
    }

    // Admin verifies an aid request
    @Transactional
    public aid verifyAidRequest(Integer aidId) {
        aid aidRequest = aidService.getAidById(aidId);

        if (aidRequest == null) {
            throw new IllegalArgumentException("Aid request not found.");
        }

        if ("verified".equalsIgnoreCase(aidRequest.getVerifystatus())) {
            throw new IllegalArgumentException("Aid request is already verified.");
        }

        // Find matching resource based on aid subtype
        resource resourceItem = resourceService.getResourceBySubtype(aidRequest.getAidsubtype());

        if (resourceItem == null) {
            throw new IllegalArgumentException("No matching resource found for the aid subtype: " + aidRequest.getAidsubtype());
        }

        if (resourceItem.getResQuantity() < aidRequest.getAidquantity()) {
            throw new IllegalArgumentException("Not enough resources available. Required: "
                    + aidRequest.getAidquantity() + ", Available: " + resourceItem.getResQuantity());
        }

        // Deduct the quantity
        resourceItem.setResQuantity(resourceItem.getResQuantity() - aidRequest.getAidquantity());
        resourceService.saveResource(resourceItem);

        // Update aid request status
        aidRequest.setVerifystatus("verified");
        aidService.saveAid(aidRequest);

        return aidRequest;
    }
}
