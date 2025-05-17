package com.drawlots.repository;

import com.drawlots.entity.IpRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IpRecordRepository extends JpaRepository<IpRecord, Long> {
    
    boolean existsByIpAddress(String ipAddress);
} 