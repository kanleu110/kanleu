package com.drawlots.service;

import com.drawlots.entity.User;

public interface UserService {
    
    User findById(Long id);
    
    User findByDingTalkId(String dingTalkId);
    
    User findByFeishuPicId(String feishuPicId);
    
    User createUserWithDingTalk(String dingTalkId, String username, String avatar);
    
    User createUserWithFeishu(String feishuPicId, String username, String avatar);
    
    void updateLastLogin(User user);
    
    boolean setUserVipStatus(Long userId, boolean isVip, int daysValid);
} 