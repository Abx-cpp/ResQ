package com.example.resq_1.aid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.resq_1.resource.resourceService;

import java.util.List;
import java.util.Optional;

@Service
public class aidService {

    private final aidrepository aidRepository;
    private final resourceService resourceService;

    @Autowired
    public aidService(aidrepository aidRepository, resourceService resourceService) {
        this.aidRepository = aidRepository;
        this.resourceService = resourceService;
    }

    public List<aid> getAllAids() {
        return aidRepository.findAll();
    }

    public aid getAidById(Integer id) {
        return aidRepository.findById(id).orElse(null);
    }

    public aid saveAid(aid aid) {
        return aidRepository.save(aid);
    }

    public void deleteAid(int id) {
        aidRepository.deleteById(id);
    }

    public List<aid> getAllByVerifystatus(String status) {
        return aidRepository.getAllByVerifystatus(status);
    }

    public aid requestAid(aid newAid) {
        // Auto-verification logic
        if ("money".equalsIgnoreCase(newAid.getAidtype()) && newAid.getAidquantity() < 5000) {
            newAid.setVerifystatus("verified");
        } else if (("shelter".equalsIgnoreCase(newAid.getAidtype()) || "goods".equalsIgnoreCase(newAid.getAidtype()))
                && newAid.getAidquantity() < 5) {
            newAid.setVerifystatus("verified");
        } else {
            newAid.setVerifystatus("unverified");
        }

        // Save the aid request
        aid savedAid = aidRepository.save(newAid);

        // If aid is verified, deduct resource quantity
        if ("verified".equalsIgnoreCase(savedAid.getVerifystatus())) {
            resourceService.deductResourceQuantity(savedAid.getAidsubtype(), savedAid.getAidquantity());
        }

        return savedAid;
    }
}
