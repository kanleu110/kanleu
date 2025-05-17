package com.drawlots.android.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VipApplication implements Serializable {
    
    @SerializedName("id")
    private Long id;
    
    @SerializedName("applicant")
    private User applicant;
    
    @SerializedName("organization")
    private String organization;
    
    @SerializedName("reason")
    private String reason;
    
    @SerializedName("status")
    private String status;
    
    @SerializedName("approvedBy")
    private User approvedBy;
    
    @SerializedName("approvedAt")
    private String approvedAt;
    
    @SerializedName("createdAt")
    private String createdAt;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getApplicant() {
        return applicant;
    }
    
    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }
    
    public String getOrganization() {
        return organization;
    }
    
    public void setOrganization(String organization) {
        this.organization = organization;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public User getApprovedBy() {
        return approvedBy;
    }
    
    public void setApprovedBy(User approvedBy) {
        this.approvedBy = approvedBy;
    }
    
    public String getApprovedAt() {
        return approvedAt;
    }
    
    public void setApprovedAt(String approvedAt) {
        this.approvedAt = approvedAt;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
    public boolean isPending() {
        return "PENDING".equals(status);
    }
    
    public boolean isApproved() {
        return "APPROVED".equals(status);
    }
    
    public boolean isRejected() {
        return "REJECTED".equals(status);
    }
} 