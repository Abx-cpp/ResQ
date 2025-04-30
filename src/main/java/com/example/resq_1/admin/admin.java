package com.example.resq_1.admin;

import com.example.resq_1.users.users;  // <-- Correct import statement
import com.example.resq_1.disaster.disaster;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin")
public class admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Integer adminId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private users user;  // Reference to the 'users' entity

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public admin() {}

    public admin(users user) {
        this.user = user;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public users getUser() {
        return user;
    }

    public void setUser(users user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
