package com.example.resq_1.volunteer;

import com.example.resq_1.users.users;
import com.example.resq_1.task.task;
import jakarta.persistence.*;

@Entity
@Table(name = "volunteer")
public class volunteer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "volunteer_id")
    private Integer volunteerId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, unique = true)
    private users user;

    @Column(name = "volunteer_type", length = 255)
    private String volunteerType;

    @ManyToOne
    @JoinColumn(name = "assigned_task_id")
    private task assignedTask;

    @Column(name = "task_id")
    private Integer taskId;  // If needed separately

    @Column(name = "vol_type", nullable = false, length = 10)
    private String volType = "on-site"; // Default value is "on-site"






    // Constructors
    public volunteer() {}

    public volunteer(users user, String volunteerType, String volType) {
        this.user = user;
        this.volunteerType = volunteerType;
        this.volType = volType;
    }

    // Getters and Setters
    public Integer getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(Integer volunteerId) {
        this.volunteerId = volunteerId;
    }

    public users getUser() {
        return user;
    }

    public void setUser(users user) {
        this.user = user;
    }

    public String getVolunteerType() {
        return volunteerType;
    }

    public void setVolunteerType(String volunteerType) {
        this.volunteerType = volunteerType;
    }

    public task getAssignedTask() {
        return assignedTask;
    }

    public void setAssignedTask(task assignedTask) {
        this.assignedTask = assignedTask;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getVolType() {
        return volType;
    }

    public void setVolType(String volType) {
        this.volType = volType;
    }
}
