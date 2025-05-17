package com.drawlots.android.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.drawlots.android.R;
import com.drawlots.android.ui.login.LoginActivity;
import com.drawlots.android.ui.main.MainActivity;
import com.drawlots.android.util.PreferenceManager;

public class SplashActivity extends AppCompatActivity {
    
    private static final int SPLASH_DELAY = 2000; // 2秒延迟
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToNextScreen();
            }
        }, SPLASH_DELAY);
    }
    
    private void navigateToNextScreen() {
        // 检查用户是否已登录
        if (PreferenceManager.isLoggedIn()) {
            // 用户已登录，跳转到主页面
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            // 用户未登录，跳转到登录页面
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        finish(); // 结束启动页面
    }
} 