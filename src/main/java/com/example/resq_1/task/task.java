package com.example.resq_1.task;

import jakarta.persistence.*;

@Entity
@Table(name = "task")
public class task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer taskId;

    @Column(name = "task_status", nullable = false, length = 255)
    private String taskStatus;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "task_type", nullable = false, length = 10)
    private String taskType; // Now directly using String for task_type

    // Default constructor
    public task() {
    }

    // Constructor with parameters
    public task(String taskStatus, String description, String taskType) {
        this.taskStatus = taskStatus;
        this.description = description;
        this.taskType = taskType;
    }

    // Getters and Setters
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}
