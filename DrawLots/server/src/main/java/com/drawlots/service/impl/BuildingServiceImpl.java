package com.drawlots.service.impl;

import com.drawlots.entity.Building;
import com.drawlots.entity.IpRecord;
import com.drawlots.entity.User;
import com.drawlots.repository.BuildingRepository;
import com.drawlots.repository.IpRecordRepository;
import com.drawlots.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BuildingServiceImpl implements BuildingService {
    
    @Autowired
    private BuildingRepository buildingRepository;
    
    @Autowired
    private IpRecordRepository ipRecordRepository;
    
    @Override
    public List<Building> getAllBuildings() {
        return buildingRepository.findAll();
    }
    
    @Override
    public List<Building> getAvailableBuildings() {
        return buildingRepository.findByIsSelectedFalse();
    }
    
    @Override
    public List<Building> getSelectedBuildings() {
        return buildingRepository.findByIsSelectedTrue();
    }
    
    @Override
    public Building findById(Long id) {
        return buildingRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("找不到ID为 " + id + " 的楼层"));
    }
    
    @Override
    public boolean isBuildingAvailable(Long buildingId) {
        Building building = findById(buildingId);
        return !building.getIsSelected();
    }
    
    @Override
    public boolean isBuildingAvailableByName(String name) {
        return !buildingRepository.existsByNameAndIsSelectedTrue(name);
    }
    
    @Override
    @Transactional
    public Building selectBuilding(Long buildingId, User user, String ipAddress) {
        Building building = findById(buildingId);
        
        if (building.getIsSelected()) {
            throw new IllegalStateException("该楼层已被抽走");
        }
        
        if (ipRecordRepository.existsByIpAddress(ipAddress)) {
            throw new IllegalStateException("您的IP地址已参与过抽签，无法重复抽签");
        }
        
        // 更新楼层状态
        building.setIsSelected(true);
        building.setSelectedBy(user);
        building.setSelectedIp(ipAddress);
        building.setSelectedAt(LocalDateTime.now());
        Building savedBuilding = buildingRepository.save(building);
        
        // 记录IP
        IpRecord ipRecord = new IpRecord();
        ipRecord.setIpAddress(ipAddress);
        ipRecord.setUser(user);
        ipRecord.setBuilding(building);
        ipRecordRepository.save(ipRecord);
        
        return savedBuilding;
    }
    
    @PostConstruct
    @Override
    public void initializeBuildings() {
        if (buildingRepository.count() == 0) {
            List<String> buildingNames = Arrays.asList("27#1", "27#2", "27#3", "27#4", "23#4");
            
            for (String name : buildingNames) {
                Building building = new Building();
                building.setName(name);
                building.setIsSelected(false);
                buildingRepository.save(building);
            }
        }
    }
} 