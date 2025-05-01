package com.example.resq_1.victim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.resq_1.aid.aid;
import com.example.resq_1.aid.aidService;

import java.util.List;

@RestController
@RequestMapping("/api/victims")
@CrossOrigin(origins = "http://localhost:5173")
public class victimController {

    @Autowired
    private victimService service;

    @GetMapping
    public List<victim> getAllVictims() {
        return service.getAllVictims();
    }

    @GetMapping("/{id}")
    public victim getVictim(@PathVariable Integer id) {
        return service.getVictimById(id);
    }

    @PostMapping
    public victim createVictim(@RequestBody victim v) {
        return service.saveVictim(v);
    }

    @DeleteMapping("/{id}")
    public void deleteVictim(@PathVariable Integer id) {
        service.deleteVictim(id);
    }

    // POST endpoint for victims to request aid
    @PostMapping("/{victimId}/request-aid")
    public aid requestAid(@PathVariable Integer victimId, @RequestBody aid newAidRequest) {
        return service.requestAid(victimId, newAidRequest);
    }
}
