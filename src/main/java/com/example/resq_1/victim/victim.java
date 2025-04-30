package com.example.resq_1.victim;

import com.example.resq_1.users.users;
import jakarta.persistence.*;

@Entity
@Table(name = "victim")
public class victim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "victim_id")
    private Integer victimId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private users user;

    @Column(name = "family_details")
    private String familyDetails;





    // Constructors
    public victim() {}

    public victim(users user, String familyDetails) {
        this.user = user;
        this.familyDetails = familyDetails;
    }

    // Getters and Setters
    public Integer getVictimId() {
        return victimId;
    }

    public void setVictimId(Integer victimId) {
        this.victimId = victimId;
    }

    public users getUser() {
        return user;
    }

    public void setUser(users user) {
        this.user = user;
    }

    public String getFamilyDetails() {
        return familyDetails;
    }

    public void setFamilyDetails(String familyDetails) {
        this.familyDetails = familyDetails;
    }
}
