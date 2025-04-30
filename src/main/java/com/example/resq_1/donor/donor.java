package com.example.resq_1.donor;

import jakarta.persistence.*;

@Entity
public class donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int donor_id;

    @Column(unique = true, nullable = false)
    private int user_id;

    private String donor_type;

    private boolean verification = false;

    // Getters and setters
    public int getDonor_id() {
        return donor_id;
    }

    public void setDonor_id(int donor_id) {
        this.donor_id = donor_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDonor_type() {
        return donor_type;
    }

    public void setDonor_type(String donor_type) {
        this.donor_type = donor_type;
    }

    public boolean isVerification() {
        return verification;
    }

    public void setVerification(boolean verification) {
        this.verification = verification;
    }
}
