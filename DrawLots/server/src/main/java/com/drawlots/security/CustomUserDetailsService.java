package com.drawlots.security;

import com.drawlots.entity.User;
import com.drawlots.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            // 这里的username实际上是用户ID
            Long userId = Long.parseLong(username);
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UsernameNotFoundException("找不到ID为 " + userId + " 的用户"));
            
            return new org.springframework.security.core.userdetails.User(
                    String.valueOf(user.getId()),
                    "", // 密码为空，因为我们使用的是JWT认证
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
            );
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("无效的用户ID: " + username);
        }
    }
} 