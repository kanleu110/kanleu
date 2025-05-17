package com.drawlots.android.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    
    @SerializedName("id")
    private Long id;
    
    @SerializedName("username")
    private String username;
    
    @SerializedName("avatar")
    private String avatar;
    
    @SerializedName("dingTalkId")
    private String dingTalkId;
    
    @SerializedName("feishuPicId")
    private String feishuPicId;
    
    @SerializedName("role")
    private String role;
    
    @SerializedName("isVip")
    private Boolean isVip;
    
    @SerializedName("vipExpiryDate")
    private String vipExpiryDate;
    
    @SerializedName("createdAt")
    private String createdAt;
    
    @SerializedName("lastLoginAt")
    private String lastLoginAt;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getAvatar() {
        return avatar;
    }
    
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
    public String getDingTalkId() {
        return dingTalkId;
    }
    
    public void setDingTalkId(String dingTalkId) {
        this.dingTalkId = dingTalkId;
    }
    
    public String getFeishuPicId() {
        return feishuPicId;
    }
    
    public void setFeishuPicId(String feishuPicId) {
        this.feishuPicId = feishuPicId;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public Boolean getIsVip() {
        return isVip;
    }
    
    public void setIsVip(Boolean isVip) {
        this.isVip = isVip;
    }
    
    public String getVipExpiryDate() {
        return vipExpiryDate;
    }
    
    public void setVipExpiryDate(String vipExpiryDate) {
        this.vipExpiryDate = vipExpiryDate;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getLastLoginAt() {
        return lastLoginAt;
    }
    
    public void setLastLoginAt(String lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }
    
    public boolean isAdmin() {
        return "ADMIN".equals(role);
    }
} 