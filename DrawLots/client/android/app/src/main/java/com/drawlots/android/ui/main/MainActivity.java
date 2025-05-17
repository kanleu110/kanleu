package com.drawlots.android.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.drawlots.android.R;
import com.drawlots.android.ui.draw.DrawLotsActivity;
import com.drawlots.android.ui.login.LoginActivity;
import com.drawlots.android.ui.vip.VipApplicationActivity;
import com.drawlots.android.util.PreferenceManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    
    private CircleImageView ivAvatar;
    private TextView tvUsername;
    private TextView tvUserRole;
    private TextView tvVipStatus;
    private Button btnDrawLots;
    private Button btnApplyVip;
    private Button btnAdminVip;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 设置工具栏
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        // 初始化视图
        ivAvatar = findViewById(R.id.iv_avatar);
        tvUsername = findViewById(R.id.tv_username);
        tvUserRole = findViewById(R.id.tv_user_role);
        tvVipStatus = findViewById(R.id.tv_vip_status);
        btnDrawLots = findViewById(R.id.btn_draw_lots);
        btnApplyVip = findViewById(R.id.btn_apply_vip);
        btnAdminVip = findViewById(R.id.btn_admin_vip);
        
        // 设置用户信息
        setupUserInfo();
        
        // 设置按钮点击事件
        btnDrawLots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到抽签页面
                Intent intent = new Intent(MainActivity.this, DrawLotsActivity.class);
                startActivity(intent);
            }
        });
        
        btnApplyVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到VIP申请页面
                Intent intent = new Intent(MainActivity.this, VipApplicationActivity.class);
                intent.putExtra("isAdmin", false);
                startActivity(intent);
            }
        });
        
        btnAdminVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到VIP管理页面
                Intent intent = new Intent(MainActivity.this, VipApplicationActivity.class);
                intent.putExtra("isAdmin", true);
                startActivity(intent);
            }
        });
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // 刷新用户信息
        setupUserInfo();
    }
    
    private void setupUserInfo() {
        // 设置用户名
        String username = PreferenceManager.getUserName();
        tvUsername.setText(username);
        
        // 设置用户角色
        String userRole = PreferenceManager.getUserRole();
        if ("ADMIN".equals(userRole)) {
            tvUserRole.setText("管理员");
            btnAdminVip.setVisibility(View.VISIBLE);
        } else {
            tvUserRole.setText("普通用户");
            btnAdminVip.setVisibility(View.GONE);
        }
        
        // 设置VIP状态
        boolean isVip = PreferenceManager.isVip();
        tvVipStatus.setVisibility(isVip ? View.VISIBLE : View.GONE);
        
        // 设置用户头像
        // 在实际应用中，应该从服务器获取用户头像URL
        // 这里使用默认头像
        Glide.with(this)
                .load(R.drawable.ic_launcher_foreground)
                .apply(RequestOptions.circleCropTransform())
                .into(ivAvatar);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void logout() {
        // 清除用户数据
        PreferenceManager.clearUserData();
        
        // 跳转到登录页面
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
} 