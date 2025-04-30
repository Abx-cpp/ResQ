package com.example.resq_1.disaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class disasterService {

    private final disasterrepository disasterRepository;

    @Autowired
    public disasterService(disasterrepository disasterRepository) {
        this.disasterRepository = disasterRepository;
    }


    public List<disaster> getAllDisasters() {
        return disasterRepository.findAll();
    }

    public Optional<disaster> getDisasterById(Long id) {
        return disasterRepository.findById(id);
    }

    public disaster saveDisaster(disaster disaster) {
        return disasterRepository.save(disaster);
    }


    public void deleteDisaster(Long id) {
        disasterRepository.deleteById(id);
    }

    public disaster updateDisaster(Long id, disaster updatedDisaster) {
        return disasterRepository.findById(id)
                .map(disaster -> {
                    disaster.setDisasterName(updatedDisaster.getDisasterName());
                    disaster.setDisasterLocation(updatedDisaster.getDisasterLocation());
                    disaster.setSeverity(updatedDisaster.getSeverity());
                    disaster.setVerificationStatus(updatedDisaster.getVerificationStatus());
                    disaster.setFundGoal(updatedDisaster.getFundGoal());
                    disaster.setAchievedDonations(updatedDisaster.getAchievedDonations());
                    disaster.setDisasterDetails(updatedDisaster.getDisasterDetails());
                    return disasterRepository.save(disaster);
                })
                .orElseGet(() -> {
                    updatedDisaster.setDisasterId(id);
                    return disasterRepository.save(updatedDisaster);
                });
    }
    public void reportDisaster(disaster disasterReport) {
        disasterReport.setRegDate(LocalDateTime.now());
        disasterReport.setVerificationStatus("unverified"); // or "new"
        disasterRepository.save(disasterReport);
    }
    @Transactional
    public disaster addDisaster(disaster newDisaster) {
        if (newDisaster.getAchievedDonations() == null) {
            newDisaster.setAchievedDonations(0);
        }
        if (newDisaster.getRegDate() == null) {
            newDisaster.setRegDate(LocalDateTime.now());
        }
        return disasterRepository.save(newDisaster);
    }

    @Transactional
    public boolean removeDisaster(Long id) {  // Changed from int to Long
        if (disasterRepository.existsById(id)) {
            disasterRepository.deleteById(id);
            return true;
        }
        return false;
    }


//        public List<disaster> searchByName(String keyword) {
//            return disasterRepository.findByDisasterNameContainingIgnoreCase(keyword);
//        }
public List<disaster> searchByName(String keyword) {
    return disasterRepository.findByDisasterNameContainingIgnoreCaseAndVerificationStatus(keyword, "verified");
}

        public List<disaster> searchByLocation(String location) {
            return disasterRepository.findByDisasterLocationContainingIgnoreCase(location);
        }



// get verified disaster for donation
public List<disaster> getVerifiedDisasters() {
    return disasterRepository.findByVerificationStatus("verified");
}
//basic search
    public disaster searchByExactName(String name) {
        return disasterRepository.findByName(name); // Assumes 'name' is the exact column name in the DB.
    }


}