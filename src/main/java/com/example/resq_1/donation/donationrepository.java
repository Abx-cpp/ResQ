package com.example.resq_1.donation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import com.example.resq_1.donor.donor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
@Repository
public interface donationrepository extends JpaRepository<donation, Long> {
    // Custom query methods can be added if necessary
   // donation findFirstByItemNameAndDonationType(String itemName, String donationType);
    long count();


}