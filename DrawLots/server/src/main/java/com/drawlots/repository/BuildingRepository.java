package com.drawlots.repository;

import com.drawlots.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
    
    List<Building> findByIsSelectedFalse();
    
    List<Building> findByIsSelectedTrue();
    
    boolean existsByNameAndIsSelectedTrue(String name);
} 