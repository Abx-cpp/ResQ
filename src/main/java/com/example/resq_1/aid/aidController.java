package com.example.resq_1.aid;


//import com.example.resq_1.entity.aid;
//import com.example.resq_1.service.aidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/aid")
@CrossOrigin(origins = "http://localhost:5173")
public class aidController {

    @Autowired
    private aidService aidService;
    @Autowired
    private aidrepository aidRepo;
    @GetMapping
    public List<aid> getAllAids() {
        return aidService.getAllAids();
    }

    @GetMapping("/{id}")
    public aid getAidById(@PathVariable int id) {
        aid aidRequest = aidService.getAidById(id);
        if (aidRequest == null) {
            throw new RuntimeException("Aid request not found with ID: " + id);
        }
        return aidRequest;
    }


    @PostMapping
    public aid createAid(@RequestBody aid aid) {
        return aidService.saveAid(aid);
    }

    @DeleteMapping("/{id}")
    public void deleteAid(@PathVariable int id) {
        aidService.deleteAid(id);
    }
//    @PostMapping("/request")
//    public aid requestAid(@RequestBody aid newAid) {
//        return aidService.requestAid(newAid);
//    }

// Get total aid count
    @GetMapping("/verified-aid-requests")
    public long getVerifiedAidRequests() {
        return aidRepo.countVerifiedAidRequests("verified");
    }
}

