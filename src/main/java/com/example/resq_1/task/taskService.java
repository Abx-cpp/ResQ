package com.example.resq_1.task;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class taskService {

    private final taskrepository taskRepository;

    @Autowired
    public taskService(taskrepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Get all tasks
    public List<task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Get task by ID
    public ResponseEntity<task> getTaskById(int id) {
        Optional<task> optionalTask = taskRepository.findById(id);
        return optionalTask.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new task
    public task createTask(task taskObj) {
        return taskRepository.save(taskObj);
    }

    // Update an existing task
    public ResponseEntity<task> updateTask(int id, task taskObj) {
        Optional<task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            task existingTask = optionalTask.get();
            existingTask.setTaskStatus(taskObj.getTaskStatus());
            existingTask.setDescription(taskObj.getDescription());
            task updatedTask = taskRepository.save(existingTask);
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a task
    public ResponseEntity<String> deleteTask(int id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.ok("Task deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
