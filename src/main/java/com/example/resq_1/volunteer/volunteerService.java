package com.example.resq_1.volunteer;

import com.example.resq_1.users.users;
import com.example.resq_1.task.task;
import com.example.resq_1.task.taskrepository;
import com.example.resq_1.victim.victim;
import com.example.resq_1.victim.victimrepository;
import com.example.resq_1.GeoCodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class volunteerService {

    private final volunteerrepository volunteerrepository;
    private final taskrepository taskRepository;
    private final victimrepository victimRepository;
    private final GeoCodingService geoCodingService;

    @Autowired
    public volunteerService(volunteerrepository volunteerrepository, taskrepository taskRepository,
                            victimrepository victimRepository, GeoCodingService geoCodingService) {
        this.volunteerrepository = volunteerrepository;
        this.taskRepository = taskRepository;
        this.victimRepository = victimRepository;
        this.geoCodingService = geoCodingService;
    }

    public List<volunteer> getAllvolunteers() {
        return volunteerrepository.findAll();
    }
    public List<volunteer> getAllVolunteers() {
        return volunteerrepository.findAll();
    }


    public Optional<volunteer> getvolunteerById(Integer id) {
        return volunteerrepository.findById(id);
    }

    public volunteer savevolunteer(volunteer volunteer) {
        return volunteerrepository.save(volunteer);
    }

    public void deletevolunteer(Integer id) {
        volunteerrepository.deleteById(id);
    }

    // This method retrieves all tasks regardless of volunteer type
    public List<task> showTask() {
        return taskRepository.findAll();
    }

    public List<task> showTasksByType(int volunteerId) {
        // Find the volunteer by ID
        volunteer volunteerObj = volunteerrepository.findById(volunteerId)
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));

        // Get the volunteer's type (ONSITE or OFFSITE)
        String volunteerType = volunteerObj.getVolType();

        // Log the value of volunteerType for debugging
        System.out.println("Volunteer Type: " + volunteerType);

        // Check the volunteer's type and match with the corresponding task type in the database
        if ("on-site".equalsIgnoreCase(volunteerType)) {
            return taskRepository.findByTaskType("on-site");
        } else if ("off-site".equalsIgnoreCase(volunteerType)) {
            return taskRepository.findByTaskType("off-site");
        } else {
            throw new IllegalArgumentException("Invalid volunteer type: " + volunteerType);
        }
    }

    // Select a task for a volunteer
    public void selectTask(int volunteerId, int taskId) {
        task taskObj = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if ("assigned".equalsIgnoreCase(taskObj.getTaskStatus())) {
            throw new RuntimeException("Task is already assigned");
        }

        volunteer volunteerObj = volunteerrepository.findById(volunteerId)
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));

        volunteerObj.setAssignedTask(taskObj);
        taskObj.setTaskStatus("assigned");

        volunteerrepository.save(volunteerObj);
        taskRepository.save(taskObj);
    }

    // Updating the task status
    public void markTaskAsDone(int volunteerId, int taskId) {
        volunteer volunteer = volunteerrepository.findById(volunteerId)
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));

        task assignedTask = volunteer.getAssignedTask();

        if (assignedTask == null || !assignedTask.getTaskId().equals(taskId)) {
            throw new RuntimeException("This task is not assigned to this volunteer");
        }

        assignedTask.setTaskStatus("done");
        taskRepository.save(assignedTask);
    }

    public void selectRole(int volunteerId, String role) {
        volunteer volunteerObj = volunteerrepository.findById(volunteerId)
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));

        if (!"on-site".equalsIgnoreCase(role) && !"off-site".equalsIgnoreCase(role)) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }

        volunteerObj.setVolType(role.toLowerCase());
        volunteerrepository.save(volunteerObj);
    }

    // Method to calculate distance between two lat/long points
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    // Method to get victims within 5 km of a volunteer
    public List<victim> getVictimsWithinRange(int volunteerId) {
        volunteer volunteerObj = volunteerrepository.findById(volunteerId)
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));

        users volunteerUser = volunteerObj.getUser();
        double volunteerLat = volunteerUser.getLatitude();  // Latitude from the User entity
        double volunteerLon = volunteerUser.getLongitude();  // Longitude from the User entity

        List<victim> allVictims = victimRepository.findAll();
        List<victim> victimsWithinRange = new ArrayList<>();

        for (victim v : allVictims) {
            users victimUser = v.getUser();
            double victimLat = victimUser.getLatitude();
            double victimLon = victimUser.getLongitude();

            // Calculate the distance using the GeoCodingService
            double distance = geoCodingService.calculateDistance(volunteerLat, volunteerLon, victimLat, victimLon);

            // If within 5 km, add victim to the list
            if (distance <= 5) {
                victimsWithinRange.add(v);
            }
        }

        return victimsWithinRange;
    }
    public void saveVolunteer(volunteer newVolunteer) {
        volunteerrepository.save(newVolunteer); // Save volunteer details
    }
}
