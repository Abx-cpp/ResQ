package com.example.resq_1.donor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.resq_1.donation.donation;
import com.example.resq_1.donation.donationrepository;
import java.util.List;
import java.util.Optional;

@Service
public class donorService {

    @Autowired
    private donorrepository donorRepository;

    @Autowired
    private donationrepository donationRepository; // ✅ Inject this

    public donor saveDonor(donor donor) {
        return donorRepository.save(donor);
    }

    public Optional<donor> getDonorById(int donor_id) {
        return donorRepository.findById(donor_id);
    }
////verification of donor done by admin
//    public donor verifyDonor(int donor_id) {
//        Optional<donor> donorOpt = donorRepository.findById(donor_id);
//        if (donorOpt.isPresent()) {
//            donor donor = donorOpt.get();
//            donor.setVerification(true);
//            return donorRepository.save(donor);
//        }
//        return null;
//    }
// making donations
    public donation saveDonation(donation donation) {
        return donationRepository.save(donation);
    }

    public Optional<donor> getVerifiedDonorById(int donorId) {
        Optional<donor> donorOpt = donorRepository.findById(donorId);
        if (donorOpt.isPresent() && donorOpt.get().isVerification()) {
            return donorOpt;
        }
        return Optional.empty();
    }

    public List<donor> getAllDonors() {
        return donorRepository.findAll();  // assuming you're using Spring Data JPA
    }

}