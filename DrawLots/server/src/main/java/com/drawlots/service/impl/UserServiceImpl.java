package com.drawlots.service.impl;

import com.drawlots.entity.User;
import com.drawlots.repository.UserRepository;
import com.drawlots.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("找不到ID为 " + id + " 的用户"));
    }
    
    @Override
    public User findByDingTalkId(String dingTalkId) {
        return userRepository.findByDingTalkId(dingTalkId)
                .orElseThrow(() -> new NoSuchElementException("找不到钉钉ID为 " + dingTalkId + " 的用户"));
    }
    
    @Override
    public User findByFeishuPicId(String feishuPicId) {
        return userRepository.findByFeishuPicId(feishuPicId)
                .orElseThrow(() -> new NoSuchElementException("找不到飞书PIC ID为 " + feishuPicId + " 的用户"));
    }
    
    @Override
    public User createUserWithDingTalk(String dingTalkId, String username, String avatar) {
        User user = new User();
        user.setDingTalkId(dingTalkId);
        user.setUsername(username);
        user.setAvatar(avatar);
        user.setRole(User.UserRole.USER);
        user.setIsVip(false);
        user.setCreatedAt(LocalDateTime.now());
        user.setLastLoginAt(LocalDateTime.now());
        return userRepository.save(user);
    }
    
    @Override
    public User createUserWithFeishu(String feishuPicId, String username, String avatar) {
        User user = new User();
        user.setFeishuPicId(feishuPicId);
        user.setUsername(username);
        user.setAvatar(avatar);
        user.setRole(User.UserRole.ADMIN);
        user.setIsVip(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setLastLoginAt(LocalDateTime.now());
        return userRepository.save(user);
    }
    
    @Override
    public void updateLastLogin(User user) {
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);
    }
    
    @Override
    public boolean setUserVipStatus(Long userId, boolean isVip, int daysValid) {
        try {
            User user = findById(userId);
            user.setIsVip(isVip);
            if (isVip) {
                user.setVipExpiryDate(LocalDateTime.now().plusDays(daysValid));
            } else {
                user.setVipExpiryDate(null);
            }
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
} 