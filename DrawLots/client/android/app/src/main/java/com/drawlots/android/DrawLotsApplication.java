package com.drawlots.android;

import android.app.Application;

import com.drawlots.android.util.PreferenceManager;

public class DrawLotsApplication extends Application {
    
    private static DrawLotsApplication instance;
    
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        
        // 初始化偏好设置管理器
        PreferenceManager.init(this);
        
        // 初始化钉钉SDK
        initDingTalkSDK();
        
        // 初始化飞书SDK
        initFeishuSDK();
    }
    
    private void initDingTalkSDK() {
        // 初始化钉钉SDK的代码，根据钉钉开放平台文档实现
    }
    
    private void initFeishuSDK() {
        // 初始化飞书SDK的代码，根据飞书开放平台文档实现
    }
    
    public static DrawLotsApplication getInstance() {
        return instance;
    }
} 