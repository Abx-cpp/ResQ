package com.example.resq_1.disaster;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "disaster")
public class disaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "disaster_id")
    private Long disasterId;

    @NotNull
    @Column(name = "disaster_name", nullable = false)
    private String disasterName;

    @Column(name = "disaster_location")
    private String disasterLocation;

    @Min(0)
    @Column(name = "severity")
    private Integer severity;

    @Column(name = "verification_status")
    private String verificationStatus;

    @Column(name = "fund_goal")
    private Integer fundGoal;

    @Column(name = "achieved_donations", columnDefinition = "integer default 0")
    private Integer achievedDonations = 0;

    @Column(name = "reg_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime regDate;

    @Column(name = "disaster_details")
    private String disasterDetails;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    // Getters and Setters
    public Long getDisasterId() {
        return disasterId;
    }

    public void setDisasterId(Long disasterId) {
        this.disasterId = disasterId;
    }

    public String getDisasterName() {
        return disasterName;
    }

    public void setDisasterName(String disasterName) {
        this.disasterName = disasterName;
    }

    public String getDisasterLocation() {
        return disasterLocation;
    }

    public void setDisasterLocation(String disasterLocation) {
        this.disasterLocation = disasterLocation;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public Integer getFundGoal() {
        return fundGoal;
    }

    public void setFundGoal(Integer fundGoal) {
        this.fundGoal = fundGoal;
    }

    public Integer getAchievedDonations() {
        return achievedDonations;
    }

    public void setAchievedDonations(Integer achievedDonations) {
        this.achievedDonations = achievedDonations;
    }

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    public String getDisasterDetails() {
        return disasterDetails;
    }

    public void setDisasterDetails(String disasterDetails) {
        this.disasterDetails = disasterDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
