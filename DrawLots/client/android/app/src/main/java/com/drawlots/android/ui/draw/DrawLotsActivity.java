package com.drawlots.android.ui.draw;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.drawlots.android.R;
import com.drawlots.android.model.Building;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class DrawLotsActivity extends AppCompatActivity {
    
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ProgressBar progressBar;
    private DrawLotsPagerAdapter pagerAdapter;
    
    private List<Building> availableBuildings = new ArrayList<>();
    private List<Building> selectedBuildings = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_lots);
        
        // 设置工具栏
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        // 初始化视图
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        progressBar = findViewById(R.id.progress_bar);
        
        // 加载数据
        loadData();
    }
    
    private void loadData() {
        // 显示加载进度
        showLoading(true);
        
        // 创建适配器
        pagerAdapter = new DrawLotsPagerAdapter(this, availableBuildings, selectedBuildings);
        viewPager.setAdapter(pagerAdapter);
        
        // 将TabLayout与ViewPager2关联
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText(R.string.available_buildings);
            } else {
                tab.setText(R.string.selected_buildings);
            }
        }).attach();
        
        // 加载可用楼层数据
        loadAvailableBuildings();
        
        // 加载已选楼层数据
        loadSelectedBuildings();
    }
    
    private void loadAvailableBuildings() {
        // 这里应该调用API获取可用楼层数据
        // 为了演示，这里模拟一些数据
        availableBuildings.clear();
        
        Building building1 = new Building();
        building1.setId(1L);
        building1.setName("27#1");
        building1.setIsSelected(false);
        availableBuildings.add(building1);
        
        Building building2 = new Building();
        building2.setId(2L);
        building2.setName("27#2");
        building2.setIsSelected(false);
        availableBuildings.add(building2);
        
        // 通知适配器数据已更新
        pagerAdapter.notifyDataSetChanged();
        
        // 隐藏加载进度
        showLoading(false);
    }
    
    private void loadSelectedBuildings() {
        // 这里应该调用API获取已选楼层数据
        // 为了演示，这里模拟一些数据
        selectedBuildings.clear();
        
        Building building3 = new Building();
        building3.setId(3L);
        building3.setName("27#3");
        building3.setIsSelected(true);
        selectedBuildings.add(building3);
        
        Building building4 = new Building();
        building4.setId(4L);
        building4.setName("27#4");
        building4.setIsSelected(true);
        selectedBuildings.add(building4);
        
        // 通知适配器数据已更新
        pagerAdapter.notifyDataSetChanged();
        
        // 隐藏加载进度
        showLoading(false);
    }
    
    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 