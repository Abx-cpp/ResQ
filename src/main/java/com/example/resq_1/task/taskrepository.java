package com.example.resq_1.task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface taskrepository extends JpaRepository<task, Integer> {


    // Optional custom method to filter by status
   // List<task> findByTaskStatus(String status);

    List<task> findByTaskType(String taskType);


}
