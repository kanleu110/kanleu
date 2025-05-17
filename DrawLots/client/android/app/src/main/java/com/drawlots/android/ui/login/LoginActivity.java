package com.drawlots.android.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.drawlots.android.R;
import com.drawlots.android.api.ApiClient;
import com.drawlots.android.model.User;
import com.drawlots.android.ui.main.MainActivity;
import com.drawlots.android.util.PreferenceManager;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    
    private Button btnDingTalkLogin;
    private Button btnFeishuLogin;
    private ProgressBar progressBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        btnDingTalkLogin = findViewById(R.id.btn_dingtalk_login);
        btnFeishuLogin = findViewById(R.id.btn_feishu_login);
        progressBar = findViewById(R.id.progress_bar);
        
        btnDingTalkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWithDingTalk();
            }
        });
        
        btnFeishuLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWithFeishu();
            }
        });
    }
    
    private void loginWithDingTalk() {
        // 显示进度条
        showLoading(true);
        
        // 这里应该调用钉钉SDK进行登录，获取钉钉用户信息
        // 为了演示，这里模拟一个钉钉用户信息
        String dingTalkId = "dingtalk123456";
        String username = "钉钉用户";
        String avatar = "https://example.com/avatar.jpg";
        
        // 调用API进行登录
        Map<String, String> loginData = new HashMap<>();
        loginData.put("dingTalkId", dingTalkId);
        loginData.put("username", username);
        loginData.put("avatar", avatar);
        
        ApiClient.getApiService().dingTalkLogin(loginData).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                showLoading(false);
                if (response.isSuccessful() && response.body() != null) {
                    handleLoginSuccess(response.body());
                } else {
                    Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                showLoading(false);
                Toast.makeText(LoginActivity.this, R.string.error_message, Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void loginWithFeishu() {
        // 显示进度条
        showLoading(true);
        
        // 这里应该调用飞书SDK进行登录，获取飞书用户信息
        // 为了演示，这里模拟一个飞书用户信息
        String feishuPicId = "feishu123456";
        String username = "飞书用户";
        String avatar = "https://example.com/avatar.jpg";
        
        // 调用API进行登录
        Map<String, String> loginData = new HashMap<>();
        loginData.put("feishuPicId", feishuPicId);
        loginData.put("username", username);
        loginData.put("avatar", avatar);
        
        ApiClient.getApiService().feishuLogin(loginData).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                showLoading(false);
                if (response.isSuccessful() && response.body() != null) {
                    handleLoginSuccess(response.body());
                } else {
                    Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                showLoading(false);
                Toast.makeText(LoginActivity.this, R.string.error_message, Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void handleLoginSuccess(Map<String, Object> responseData) {
        // 保存token
        String token = (String) responseData.get("token");
        PreferenceManager.saveToken(token);
        
        // 解析用户信息
        Gson gson = new Gson();
        String userJson = gson.toJson(responseData.get("user"));
        User user = gson.fromJson(userJson, User.class);
        
        // 保存用户信息
        PreferenceManager.saveUserInfo(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getIsVip() != null && user.getIsVip()
        );
        
        // 跳转到主页面
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    
    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        btnDingTalkLogin.setEnabled(!isLoading);
        btnFeishuLogin.setEnabled(!isLoading);
    }
} 