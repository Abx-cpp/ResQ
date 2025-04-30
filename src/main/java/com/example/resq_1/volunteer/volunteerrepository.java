package com.example.resq_1.volunteer;
import com.example.resq_1.task.task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface volunteerrepository extends JpaRepository<volunteer, Integer> {
    long count();

}