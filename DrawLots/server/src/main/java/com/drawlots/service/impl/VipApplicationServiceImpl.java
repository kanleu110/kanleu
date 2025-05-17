package com.drawlots.service.impl;

import com.drawlots.entity.User;
import com.drawlots.entity.VipApplication;
import com.drawlots.repository.VipApplicationRepository;
import com.drawlots.service.UserService;
import com.drawlots.service.VipApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class VipApplicationServiceImpl implements VipApplicationService {
    
    @Autowired
    private VipApplicationRepository vipApplicationRepository;
    
    @Autowired
    private UserService userService;
    
    @Override
    public VipApplication submitApplication(User applicant, String organization, String reason) {
        VipApplication application = new VipApplication();
        application.setApplicant(applicant);
        application.setOrganization(organization);
        application.setReason(reason);
        application.setStatus(VipApplication.ApplicationStatus.PENDING);
        return vipApplicationRepository.save(application);
    }
    
    @Override
    public List<VipApplication> getPendingApplications() {
        return vipApplicationRepository.findByStatus(VipApplication.ApplicationStatus.PENDING);
    }
    
    @Override
    public List<VipApplication> getUserApplications(User user) {
        return vipApplicationRepository.findByApplicant(user);
    }
    
    @Override
    @Transactional
    public VipApplication approveApplication(Long applicationId, User admin, int vipDays) {
        VipApplication application = findById(applicationId);
        
        if (application.getStatus() != VipApplication.ApplicationStatus.PENDING) {
            throw new IllegalStateException("该申请已处理，无法重复审批");
        }
        
        application.setStatus(VipApplication.ApplicationStatus.APPROVED);
        application.setApprovedBy(admin);
        application.setApprovedAt(LocalDateTime.now());
        
        // 设置用户VIP状态
        userService.setUserVipStatus(application.getApplicant().getId(), true, vipDays);
        
        return vipApplicationRepository.save(application);
    }
    
    @Override
    public VipApplication rejectApplication(Long applicationId, User admin) {
        VipApplication application = findById(applicationId);
        
        if (application.getStatus() != VipApplication.ApplicationStatus.PENDING) {
            throw new IllegalStateException("该申请已处理，无法重复审批");
        }
        
        application.setStatus(VipApplication.ApplicationStatus.REJECTED);
        application.setApprovedBy(admin);
        application.setApprovedAt(LocalDateTime.now());
        
        return vipApplicationRepository.save(application);
    }
    
    @Override
    public VipApplication findById(Long id) {
        return vipApplicationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("找不到ID为 " + id + " 的VIP申请"));
    }
} 