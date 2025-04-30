package com.example.resq_1.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "users")  // Explicitly maps to "users" table in database
public class users {
    @Id
    private int user_id;
    private String name;
    private String email;
    private String phone_no;
    private String password;
    private String address;
    private String role;
    private Timestamp created_at;
    private Double latitude;  // Latitude field to store the latitude of the user
    private Double longitude; // Longitude field to store the longitude of the user

    // Default constructor
    public users() {
    }

    // Parameterized constructor
    public users(int user_id, String name, String email, String phone_no, String password, String address, String role, Timestamp created_at, double latitude, double longitude) {
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.phone_no = phone_no;
        this.password = password;
        this.address = address;
        this.role = role;
        this.created_at = created_at;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // toString() method for debugging
    @Override
    public String toString() {
        return "users{" +
                "user_id=" + user_id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone_no='" + phone_no + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", role='" + role + '\'' +
                ", created_at=" + created_at +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
