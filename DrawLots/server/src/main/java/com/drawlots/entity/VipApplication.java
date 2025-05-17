package com.drawlots.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "vip_applications")
public class VipApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User applicant;

    private String organization;
    
    private String reason;
    
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
    
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User approvedBy;
    
    private LocalDateTime approvedAt;
    
    private LocalDateTime createdAt;
    
    public enum ApplicationStatus {
        PENDING,
        APPROVED,
        REJECTED
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        status = ApplicationStatus.PENDING;
    }
} 