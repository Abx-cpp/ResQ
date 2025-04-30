package com.example.resq_1.disaster;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
@Repository
public interface disasterrepository extends JpaRepository<disaster, Long> {

    @Query("SELECT COUNT(d) FROM disaster d WHERE d.verificationStatus = :status")
    long countVerifiedDisasters(@Param("status") String status);
    //List<disaster> findByDisasterNameContainingIgnoreCase(String keyword);
    List<disaster> findByDisasterNameContainingIgnoreCaseAndVerificationStatus(String disasterName, String Status);
    List<disaster> findByVerificationStatus(String verificationStatus);
    public disaster findByName(String name); // Custom query for exact name search


    // Optional: Add more for other fields
    List<disaster> findByDisasterLocationContainingIgnoreCase(String location);
}