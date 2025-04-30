package com.example.resq_1;

import com.example.resq_1.users.users;
import com.example.resq_1.users.usersrepository;
import com.example.resq_1.victim.victim;
import com.example.resq_1.volunteer.volunteer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;



@Service
public class GeoCodingService {

    private final String API_URL = "https://nominatim.openstreetmap.org/search";
    private final double EARTH_RADIUS_KM = 6371;  // Earth's radius in km

    @Autowired
    private usersrepository userRepository;
    public double[] getLatLonFromAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new RuntimeException("Address is empty or null.");
        }

        RestTemplate restTemplate = new RestTemplate();

        // Build the URL
        String url = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("q", address)
                .queryParam("format", "json")
                .build()
                .toUriString();

        // Add mandatory User-Agent header
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) ResQApp/1.0 (your_email@example.com)");

        org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(headers);

        // Use exchange method to send the headers
        org.springframework.http.ResponseEntity<String> response = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.GET,
                entity,
                String.class
        );

        // Log the raw API response
        System.out.println("Geocoding API Response: " + response.getBody());

        try {
            // Parse the JSON response
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            if (root.isArray() && root.size() > 0) {
                JsonNode firstResult = root.get(0);

                String latText = firstResult.get("lat").asText();
                String lonText = firstResult.get("lon").asText();
                System.out.println("Latitude: " + latText);
                System.out.println("Longitude: " + lonText);

                if (latText != null && lonText != null) {
                    try {
                        double lat = Double.parseDouble(latText);
                        double lon = Double.parseDouble(lonText);
                        return new double[]{lat, lon};
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Invalid number format for latitude or longitude.");
                    }
                } else {
                    throw new RuntimeException("Latitude or longitude is missing in the response.");
                }
            } else {
                throw new RuntimeException("Address not found or invalid response from geocoding API.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse geocoding response: " + e.getMessage(), e);
        }
    }

    // This method calculates the distance between two sets of coordinates
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c; // Result in km
    }

    // This method calculates the distance between a volunteer and a victim
    public double getDistanceBetweenVolunteerAndVictim(volunteer volunteer, victim victim) {
        double volunteerLat = volunteer.getUser().getLatitude();
        double volunteerLon = volunteer.getUser().getLongitude();
        double victimLat = victim.getUser().getLatitude();
        double victimLon = victim.getUser().getLongitude();

        return calculateDistance(volunteerLat, volunteerLon, victimLat, victimLon);
    }

    // This method updates the user coordinates using the address
    public void updateUserCoordinates(int userId, String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new RuntimeException("Address is empty or null.");
        }

        double[] latLon = getLatLonFromAddress(address);
        if (latLon == null || latLon.length == 0) {
            throw new RuntimeException("Failed to retrieve coordinates for address: " + address);
        }

        double latitude = latLon[0];
        double longitude = latLon[1];

        // Retrieve the user from the repository and update their coordinates
        users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setLatitude(latitude);
        user.setLongitude(longitude);

        userRepository.save(user);
    }
}
