package com.drawlots.service;

import com.drawlots.entity.Building;
import com.drawlots.entity.User;

import java.util.List;

public interface BuildingService {
    
    List<Building> getAllBuildings();
    
    List<Building> getAvailableBuildings();
    
    List<Building> getSelectedBuildings();
    
    Building findById(Long id);
    
    boolean isBuildingAvailable(Long buildingId);
    
    boolean isBuildingAvailableByName(String name);
    
    Building selectBuilding(Long buildingId, User user, String ipAddress);
    
    void initializeBuildings();
} 