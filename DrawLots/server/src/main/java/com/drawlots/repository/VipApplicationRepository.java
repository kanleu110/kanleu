package com.drawlots.repository;

import com.drawlots.entity.User;
import com.drawlots.entity.VipApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VipApplicationRepository extends JpaRepository<VipApplication, Long> {
    
    List<VipApplication> findByApplicant(User applicant);
    
    List<VipApplication> findByStatus(VipApplication.ApplicationStatus status);
} 