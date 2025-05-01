package com.example.resq_1.volunteer;

import com.example.resq_1.GeoCodingService;
import com.example.resq_1.task.task;
import com.example.resq_1.victim.victim;
import com.example.resq_1.task.taskrepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.stream.Collectors;

import java.util.List;

@RestController
@RequestMapping("/api/volunteers")
@CrossOrigin(origins = "http://localhost:5173")
public class volunteerController {

    private final volunteerService volunteerService;

    @Autowired
    public volunteerController(volunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }
    @Autowired
    private volunteerrepository volunteerRepo;

    @Autowired
    private GeoCodingService geoCodingService;

    @GetMapping
    public List<volunteer> getAllvolunteers() {
        return volunteerService.getAllvolunteers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<volunteer> getvolunteerById(@PathVariable Integer id) {
        return volunteerService.getvolunteerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public volunteer createvolunteer(@RequestBody volunteer volunteer) {
        return volunteerService.savevolunteer(volunteer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<volunteer> updatevolunteer(@PathVariable Integer id, @RequestBody volunteer volunteer) {
        if (volunteerService.getvolunteerById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        volunteer.setVolunteerId(id);
        return ResponseEntity.ok(volunteerService.savevolunteer(volunteer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletevolunteer(@PathVariable Integer id) {
        volunteerService.deletevolunteer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{volunteerId}/tasks")
    public List<task> getTasksForVolunteer(@PathVariable Integer volunteerId) {
        // Call the service to get tasks based on the volunteer's type
        return volunteerService.showTasksByType(volunteerId);
    }





    // Select a volunteer role (ONSITE or OFFSITE)
    @PostMapping("/select-role")
    public ResponseEntity<String> selectRole(@RequestParam int volunteerId, @RequestParam String role) {
        try {
            volunteerService.selectRole(volunteerId, role);
            return ResponseEntity.ok("Role assigned successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Select a task based on volunteer role
    @PostMapping("/select-task")
    public ResponseEntity<String> selectTask(@RequestParam int volunteerId, @RequestParam int taskId) {
        try {
            volunteerService.selectTask(volunteerId, taskId);
            return ResponseEntity.ok("Task assigned successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Mark task as done
    @PutMapping("/mark-task-done")
    public ResponseEntity<String> markTaskAsDone(@RequestParam int volunteerId, @RequestParam int taskId) {
        try {
            volunteerService.markTaskAsDone(volunteerId, taskId);
            return ResponseEntity.ok("Task marked as done successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    // Get total volunteer count
    @GetMapping("/total-volunteers")
    public long getTotalVolunteers() {
        return volunteerRepo.count();
    }
    @GetMapping("/{volunteerId}/victims-in-range")
    public ResponseEntity<List<victim>> getVictimsInRange(@PathVariable int volunteerId) {
        volunteer volunteer = volunteerRepo.findById(volunteerId)
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));

        // Check if volunteer's coordinates are available
        if (volunteer.getUser() == null || volunteer.getUser().getLatitude() == null || volunteer.getUser().getLongitude() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // volunteer has no location
        }

        List<victim> victims = volunteerService.getVictimsWithinRange(volunteerId);

        List<victim> victimsInRange = victims.stream()
                .filter(victim -> victim.getUser() != null
                        && victim.getUser().getLatitude() != null
                        && victim.getUser().getLongitude() != null) // Check if victim has location
                .filter(victim -> {
                    double distance = geoCodingService.getDistanceBetweenVolunteerAndVictim(volunteer, victim);
                    return distance <= 5; // Only within 5km
                })
                .collect(Collectors.toList());

        if (victimsInRange.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(victimsInRange, HttpStatus.OK);
    }


}
