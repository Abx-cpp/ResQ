package com.example.resq_1.donation;
import jakarta.persistence.*;
import com.example.resq_1.disaster.disaster;
import com.example.resq_1.donor.donor;
import java.time.LocalDate;
@Entity
@Table(name = "donation")
public class donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//
//    @Column(name = "item_name", nullable = false, length = 100)
//    private String itemName;

    @Column(name = "donation_subtype", nullable = false, length = 50)
    private String itemCategory;

    @Column(name = "item_quantity", nullable = false)
    private Integer itemQuantity;

    @Column(name = "donation_type", nullable = false, length = 50)
    private String donationType;

    @ManyToOne
    @JoinColumn(name = "donor_id", nullable = false)
    private donor donor;

    @ManyToOne
    @JoinColumn(name = "disaster_id", nullable = false)
    private disaster disaster;

    @Column(name = "donation_date", nullable = false)
    private LocalDate donationDate;

    // Getters and setters
    public LocalDate getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(LocalDate donationDate) {
        this.donationDate = donationDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getItemName() {
//        return itemName;
//    }
//
//    public void setItemName(String itemName) {
//        this.itemName = itemName;
//    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getDonationType() {
        return donationType;
    }

    public void setDonationType(String donationType) {
        this.donationType = donationType;
    }

    public donor getDonor() {
        return donor;
    }

    public void setDonor(donor donor) {
        this.donor = donor;
    }

    public disaster getDisaster() {
        return disaster;
    }

    public void setDisaster(disaster disaster) {
        this.disaster = disaster;
    }
}
