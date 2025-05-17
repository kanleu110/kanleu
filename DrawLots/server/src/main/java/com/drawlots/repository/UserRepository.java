package com.drawlots.repository;

import com.drawlots.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByDingTalkId(String dingTalkId);
    
    Optional<User> findByFeishuPicId(String feishuPicId);
    
    boolean existsByDingTalkId(String dingTalkId);
    
    boolean existsByFeishuPicId(String feishuPicId);
} 