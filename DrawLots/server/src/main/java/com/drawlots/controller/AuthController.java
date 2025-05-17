package com.drawlots.controller;

import com.drawlots.entity.User;
import com.drawlots.service.UserService;
import com.drawlots.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @PostMapping("/dingtalk/login")
    public ResponseEntity<?> dingTalkLogin(@RequestBody Map<String, String> loginData) {
        String dingTalkId = loginData.get("dingTalkId");
        String username = loginData.get("username");
        String avatar = loginData.get("avatar");
        
        User user;
        try {
            user = userService.findByDingTalkId(dingTalkId);
            userService.updateLastLogin(user);
        } catch (Exception e) {
            // 用户不存在，创建新用户
            user = userService.createUserWithDingTalk(dingTalkId, username, avatar);
        }
        
        // 生成JWT令牌
        String token = jwtUtils.generateToken(user);
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", user);
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/feishu/login")
    public ResponseEntity<?> feishuLogin(@RequestBody Map<String, String> loginData) {
        String feishuPicId = loginData.get("feishuPicId");
        String username = loginData.get("username");
        String avatar = loginData.get("avatar");
        
        User user;
        try {
            user = userService.findByFeishuPicId(feishuPicId);
            userService.updateLastLogin(user);
        } catch (Exception e) {
            // 用户不存在，创建新用户
            user = userService.createUserWithFeishu(feishuPicId, username, avatar);
        }
        
        // 生成JWT令牌
        String token = jwtUtils.generateToken(user);
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", user);
        
        return ResponseEntity.ok(response);
    }
} 