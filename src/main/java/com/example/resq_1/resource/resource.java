package com.example.resq_1.resource;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "resource")
public class resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "res_id")
    private Integer resId;

    @NotNull
    @Column(name = "res_type", nullable = false, length = 50)
    private String resType;

    @Column(name = "res_subtype", length = 50)
    private String resSubtype;

    @Min(0)
    @Column(name = "res_quantity")
    private Integer resQuantity;

    // Constructors
    public resource() {
    }

    public resource(Integer resId, String resType, String resSubtype, Integer resQuantity) {
        this.resId = resId;
        this.resType = resType;
        this.resSubtype = resSubtype;
        this.resQuantity = resQuantity;
    }

    // Getters and Setters
    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public String getResSubtype() {
        return resSubtype;
    }

    public void setResSubtype(String resSubtype) {
        this.resSubtype = resSubtype;
    }

    public Integer getResQuantity() {
        return resQuantity;
    }

    public void setResQuantity(Integer resQuantity) {
        this.resQuantity = resQuantity;
    }
}
