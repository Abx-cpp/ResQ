package com.example.resq_1.donation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.resq_1.aid.aid;
import com.example.resq_1.aid.aidrepository;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Optional;
import java.time.LocalDate;
import java.time.YearMonth;


@Service
public class donationService {

    @Autowired
    private donationrepository donationRepository;
    @Autowired
    private aidrepository aidRepository;
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

    public List<Map<String, Object>> getLast6MonthsDonationComparison() {
        List<Map<String, Object>> response = new ArrayList<>();

        // Step 1: Generate last 6 months
        YearMonth current = YearMonth.now();
        List<YearMonth> last6Months = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {
            last6Months.add(current.minusMonths(i));
        }

        // Step 2: Query donation counts grouped by year and month
        String query = "SELECT EXTRACT(YEAR FROM donation_date) AS year, " +
                "EXTRACT(MONTH FROM donation_date) AS month, " +
                "COUNT(*) AS total_donations " +
                "FROM donation " +
                "WHERE donation_date >= :startDate " +
                "GROUP BY year, month";

        LocalDate startDate = current.minusMonths(5).atDay(1);
        List<Object[]> results = entityManager.createNativeQuery(query)
                .setParameter("startDate", java.sql.Date.valueOf(startDate))
                .getResultList();

        // Step 3: Store results in a lookup map
        Map<String, Integer> donationsMap = new HashMap<>();
        for (Object[] row : results) {
            int year = ((Number) row[0]).intValue();
            int month = ((Number) row[1]).intValue();
            int count = ((Number) row[2]).intValue();
            donationsMap.put(year + "-" + month, count);
        }

        // Step 4: Build response with zero where needed
        for (YearMonth ym : last6Months) {
            String key = ym.getYear() + "-" + ym.getMonthValue();
            Map<String, Object> map = new HashMap<>();
            map.put("year", ym.getYear());
            map.put("month", ym.getMonthValue());
            map.put("total_donations", donationsMap.getOrDefault(key, 0));
            response.add(map);
        }

        return response;
    }

    public Map<String, Long> getDonationAndAidCount() {
        long donationCount = donationRepository.count();
        long aidCount = aidRepository.count();

        Map<String, Long> result = new HashMap<>();
        result.put("totalDonations", donationCount);
        result.put("totalAids", aidCount);

        return result;
    }
}