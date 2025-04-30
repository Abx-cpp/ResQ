package com.example.resq_1.aid;

import com.example.resq_1.victim.victim;
import jakarta.persistence.*;

@Entity
@Table(name = "aid")
public class aid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aidid;

    @ManyToOne
    @JoinColumn(name = "victim_id", referencedColumnName = "victim_id")
    private victim victim; // assuming you have a 'victim' entity already

    @Column(name = "aidtype")
    private String aidtype;

    @Column(name = "aidquantity")
    private Integer aidquantity;

    @Column(name = "verifystatus")
    private String verifystatus;

    // New aid_name column
    @Column(name = "aid_subtype")
    private String aidName;

    // Getters and Setters
    public int getAidid() {
        return aidid;
    }

    public void setAidid(int aidid) {
        this.aidid = aidid;
    }

    public victim getVictim() {
        return victim;
    }

    public void setVictim(victim victim) {
        this.victim = victim;
    }

    public String getAidtype() {
        return aidtype;
    }

    public void setAidtype(String aidtype) {
        this.aidtype = aidtype;
    }

    public int getAidquantity() {
        return aidquantity;
    }

    public void setAidquantity(int aidquantity) {
        this.aidquantity = aidquantity;
    }

    public String getVerifystatus() {
        return verifystatus;
    }

    public void setVerifystatus(String verifystatus) {
        this.verifystatus = verifystatus;
    }

    public String getAidsubtype() {
        return aidName;
    }

    public void setAidName(String aidName) {
        this.aidName = aidName;
    }
}
