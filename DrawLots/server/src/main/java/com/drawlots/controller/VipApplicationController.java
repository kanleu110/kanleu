package com.drawlots.controller;

import com.drawlots.entity.User;
import com.drawlots.entity.VipApplication;
import com.drawlots.service.UserService;
import com.drawlots.service.VipApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vip")
public class VipApplicationController {
    
    @Autowired
    private VipApplicationService vipApplicationService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/apply")
    public ResponseEntity<?> applyForVip(@RequestBody Map<String, String> applicationData) {
        try {
            // 获取当前登录用户
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.findById(Long.parseLong(username));
            
            String organization = applicationData.get("organization");
            String reason = applicationData.get("reason");
            
            VipApplication application = vipApplicationService.submitApplication(user, organization, reason);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("application", application);
            response.put("message", "VIP申请提交成功，请等待管理员审批");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/applications")
    public ResponseEntity<?> getUserApplications() {
        try {
            // 获取当前登录用户
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.findById(Long.parseLong(username));
            
            List<VipApplication> applications = vipApplicationService.getUserApplications(user);
            
            return ResponseEntity.ok(applications);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/admin/applications")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getPendingApplications() {
        List<VipApplication> applications = vipApplicationService.getPendingApplications();
        return ResponseEntity.ok(applications);
    }
    
    @PostMapping("/admin/applications/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> approveApplication(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> approvalData) {
        
        try {
            // 获取当前登录管理员
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User admin = userService.findById(Long.parseLong(username));
            
            Integer vipDays = approvalData.get("vipDays");
            if (vipDays == null || vipDays <= 0) {
                vipDays = 30; // 默认30天
            }
            
            VipApplication application = vipApplicationService.approveApplication(id, admin, vipDays);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("application", application);
            response.put("message", "VIP申请已审批通过");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/admin/applications/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> rejectApplication(@PathVariable Long id) {
        try {
            // 获取当前登录管理员
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User admin = userService.findById(Long.parseLong(username));
            
            VipApplication application = vipApplicationService.rejectApplication(id, admin);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("application", application);
            response.put("message", "VIP申请已拒绝");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
} 