package com.example.resq_1.donation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class donationService {

    @Autowired
    private donationrepository donationRepository;

    @PersistenceContext
    private EntityManager entityManager;

    // Get all donations
    public List<donation> getAllDonations() {
        return donationRepository.findAll();
    }

    // Get a donation by ID
    public Optional<donation> getDonationById(Long id) {
        return donationRepository.findById(id);
    }

    // Save a new donation
    public donation saveDonation(donation donation) {
        return donationRepository.save(donation);
    }

    // Delete a donation by ID
    public void deleteDonation(Long id) {
        donationRepository.deleteById(id);
    }

    // Get top 4 money donors
    public List<Map<String, Object>> getTop4MoneyDonors() {
        String query = "SELECT u.name, u.email, SUM(dn.item_quantity) AS total_donated " +
                "FROM donation dn " +
                "JOIN donor d ON dn.donor_id = d.donor_id " +
                "JOIN users u ON d.user_id = u.user_id " +
                "WHERE dn.donation_type = 'money' " +
                "GROUP BY u.name, u.email " +
                "ORDER BY total_donated DESC " +
                "LIMIT 4";

        List<Object[]> results = entityManager.createNativeQuery(query).getResultList();

        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", row[0]);
            map.put("email", row[1]);
            map.put("total_donated", row[2]);
            response.add(map);
        }
        return response;
    }

    // Get last 6 months donation comparison
    public List<Map<String, Object>> getLast6MonthsDonationComparison() {
        String query = "SELECT EXTRACT(YEAR FROM donation_date) AS year, " +
                "EXTRACT(MONTH FROM donation_date) AS month, " +
                "COUNT(*) AS total_donations " +
                "FROM donation " +
                "GROUP BY year, month " +
                "ORDER BY year DESC, month DESC " +
                "LIMIT 6";

        List<Object[]> results = entityManager.createNativeQuery(query).getResultList();

        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("year", ((Number) row[0]).intValue());
            map.put("month", ((Number) row[1]).intValue());
            map.put("total_donations", ((Number) row[2]).intValue());
            response.add(map);
        }

        // Sort results chronologically (reverse order)
        Collections.reverse(response);

        return response;
    }
}
