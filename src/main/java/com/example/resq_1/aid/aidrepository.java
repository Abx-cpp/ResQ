package com.example.resq_1.aid;



//import com.example.resq_1.entity.aid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface aidrepository extends JpaRepository<aid, Integer> {
    List<aid> getAllByVerifystatus(String verifystatus);
    @Query("SELECT COUNT(a) FROM aid a WHERE a.verifystatus = :status")
    long countVerifiedAidRequests(@Param("status") String status);
}


