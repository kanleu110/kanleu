package com.drawlots.android.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Building implements Serializable {
    
    @SerializedName("id")
    private Long id;
    
    @SerializedName("name")
    private String name;
    
    @SerializedName("isSelected")
    private Boolean isSelected;
    
    @SerializedName("selectedBy")
    private User selectedBy;
    
    @SerializedName("selectedIp")
    private String selectedIp;
    
    @SerializedName("selectedAt")
    private String selectedAt;
    
    @SerializedName("createdAt")
    private String createdAt;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Boolean getIsSelected() {
        return isSelected;
    }
    
    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }
    
    public User getSelectedBy() {
        return selectedBy;
    }
    
    public void setSelectedBy(User selectedBy) {
        this.selectedBy = selectedBy;
    }
    
    public String getSelectedIp() {
        return selectedIp;
    }
    
    public void setSelectedIp(String selectedIp) {
        this.selectedIp = selectedIp;
    }
    
    public String getSelectedAt() {
        return selectedAt;
    }
    
    public void setSelectedAt(String selectedAt) {
        this.selectedAt = selectedAt;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
} 