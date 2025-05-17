package com.drawlots.service;

import com.drawlots.entity.User;
import com.drawlots.entity.VipApplication;

import java.util.List;

public interface VipApplicationService {
    
    VipApplication submitApplication(User applicant, String organization, String reason);
    
    List<VipApplication> getPendingApplications();
    
    List<VipApplication> getUserApplications(User user);
    
    VipApplication approveApplication(Long applicationId, User admin, int vipDays);
    
    VipApplication rejectApplication(Long applicationId, User admin);
    
    VipApplication findById(Long id);
} 