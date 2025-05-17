package com.drawlots.controller;

import com.drawlots.entity.Building;
import com.drawlots.entity.User;
import com.drawlots.service.BuildingService;
import com.drawlots.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buildings")
public class BuildingController {
    
    @Autowired
    private BuildingService buildingService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<List<Building>> getAllBuildings() {
        return ResponseEntity.ok(buildingService.getAllBuildings());
    }
    
    @GetMapping("/available")
    public ResponseEntity<List<Building>> getAvailableBuildings() {
        return ResponseEntity.ok(buildingService.getAvailableBuildings());
    }
    
    @GetMapping("/selected")
    public ResponseEntity<List<Building>> getSelectedBuildings() {
        return ResponseEntity.ok(buildingService.getSelectedBuildings());
    }
    
    @PostMapping("/{id}/select")
    public ResponseEntity<?> selectBuilding(
            @PathVariable Long id,
            HttpServletRequest request) {
        
        try {
            // 获取当前登录用户
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.findById(Long.parseLong(username));
            
            // 获取客户端IP地址
            String ipAddress = getClientIp(request);
            
            // 抽签
            Building selectedBuilding = buildingService.selectBuilding(id, user, ipAddress);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("building", selectedBuilding);
            response.put("message", "抽签成功！您选择了楼层：" + selectedBuilding.getName());
            
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0];
        }
        return request.getRemoteAddr();
    }
} 