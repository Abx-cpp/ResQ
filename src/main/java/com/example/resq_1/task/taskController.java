package com.example.resq_1.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class taskController {

    private final taskService taskService;

    @Autowired
    public taskController(taskService taskService) {
        this.taskService = taskService;
    }

    // Get all tasks
    @GetMapping
    public List<task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // Get task by ID
    @GetMapping("/{id}")
    public ResponseEntity<task> getTaskById(@PathVariable int id) {
        return taskService.getTaskById(id);
    }

    // Create a new task
    @PostMapping
    public task createTask(@RequestBody task taskObj) {
        return taskService.createTask(taskObj);
    }

    // Update an existing task
    @PutMapping("/{id}")
    public ResponseEntity<task> updateTask(@PathVariable int id, @RequestBody task taskObj) {
        return taskService.updateTask(id, taskObj);
    }

    // Delete a task
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id) {
        return taskService.deleteTask(id);
    }
}
