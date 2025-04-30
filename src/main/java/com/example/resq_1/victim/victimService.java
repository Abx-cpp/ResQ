package com.example.resq_1.victim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.resq_1.aid.aid;
import com.example.resq_1.aid.aidService;
import com.example.resq_1.resource.resourceService;
import java.util.List;

@Service
public class victimService {

    @Autowired
    private victimrepository repository;

    @Autowired
    private aidService aidService;

    @Autowired
    private resourceService resourceService;

    // Get all victims
    public List<victim> getAllVictims() {
        return repository.findAll();
    }

    // Get victim by ID
    public victim getVictimById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    // Save a victim
    public victim saveVictim(victim v) {
        return repository.save(v);
    }

    // Delete a victim by ID
    public void deleteVictim(Integer id) {
        repository.deleteById(id);
    }

    // Method to request aid for a victim
    public aid requestAid(Integer victimId, aid newAidRequest) {
        // Find the victim by ID to ensure that the request is valid
        victim v = repository.findById(victimId).orElse(null);
        if (v == null) {
            throw new IllegalArgumentException("Victim not found with ID: " + victimId);
        }

        // Now, proceed with the aid request logic
        if (newAidRequest == null || newAidRequest.getAidtype() == null || newAidRequest.getAidquantity() == 0) {
            throw new IllegalArgumentException("Aid type and quantity cannot be null or zero.");
        }

        // Business logic for automatic verification of aid
        if ("money".equalsIgnoreCase(newAidRequest.getAidtype()) && newAidRequest.getAidquantity() < 5000) {
            newAidRequest.setVerifystatus("verified");
        } else if (("shelter".equalsIgnoreCase(newAidRequest.getAidtype()) || "goods".equalsIgnoreCase(newAidRequest.getAidtype()))
                && newAidRequest.getAidquantity() < 5) {
            newAidRequest.setVerifystatus("verified");
        } else {
            newAidRequest.setVerifystatus("unverified");
        }

        // If aid is verified, update resource quantity
        if ("verified".equalsIgnoreCase(newAidRequest.getVerifystatus())) {
            // Save the verified aid request
            aid savedAidRequest = aidService.saveAid(newAidRequest);

            // Update the resource quantity based on aid type and quantity
            try {
                resourceService.updateResourceQuantity(newAidRequest.getAidtype(), newAidRequest.getAidquantity(), true);
            } catch (Exception e) {
                throw new RuntimeException("Error while updating resource quantity: " + e.getMessage());
            }

            return savedAidRequest; // Return the aid request with verification status
        }

        // If not verified, just save and return with unverified status
        aid savedAidRequest = aidService.saveAid(newAidRequest);
        return savedAidRequest;
    }
}
