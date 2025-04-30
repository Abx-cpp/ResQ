package com.example.resq_1.disaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/disasters")
public class disasterController {

    private final disasterService disasterService;

    @Autowired
    public disasterController(disasterService disasterService) {
        this.disasterService = disasterService;
    }
    @Autowired
    private disasterrepository disasterRepo;
    @GetMapping
    public List<disaster> getAllDisasters() {
        return disasterService.getAllDisasters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<disaster> getDisasterById(@PathVariable Long id) {
        return disasterService.getDisasterById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public disaster createDisaster(@RequestBody disaster disaster) {
        return disasterService.saveDisaster(disaster);
    }

    @PutMapping("/{id}")
    public ResponseEntity<disaster> updateDisaster(@PathVariable Long id, @RequestBody disaster disaster) {
        return ResponseEntity.ok(disasterService.updateDisaster(id, disaster));
    }
//for deleting the disaster
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisaster(@PathVariable Long id) {
        disasterService.deleteDisaster(id);
        return ResponseEntity.noContent().build();
    }
    //reporting of disasters
    @PostMapping("/report")
    public ResponseEntity<String> reportDisaster(@RequestBody disaster disasterReport) {
        disasterService.reportDisaster(disasterReport);
        return ResponseEntity.ok("Disaster reported successfully.");
    }
    // Search by name advanced
   @GetMapping("/search")

    public ResponseEntity<List<disaster>> searchDisastersByName(@RequestParam String keyword) {
        List<disaster> results = disasterService.searchByName(keyword);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }


//    // Optional: Search by location extra func
//    @GetMapping("/search/location")
//    public ResponseEntity<List<disaster>> searchDisastersByLocation(@RequestParam String location) {
//        List<disaster> results = disasterService.searchByLocation(location);
//        return new ResponseEntity<>(results, HttpStatus.OK);
//    }

    //getting all disasters for donation
    @GetMapping("/available-disasters")
    public List<disaster> getVerifiedDisasters() {
        return disasterService.getVerifiedDisasters();
    }

    // Search by exact name (exact match) basic search
    @GetMapping("/search/exact-name")
    public ResponseEntity<?> searchDisasterByExactName(@RequestParam String name) {
        disaster result = disasterService.searchByExactName(name);
        if (result == null) {
            return new ResponseEntity<>("Disaster not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    // Get total disaster count
    @GetMapping("/verified-disasters")
    public long getVerified1Disasters() {
        return disasterRepo.countVerifiedDisasters("verified");
    }

}