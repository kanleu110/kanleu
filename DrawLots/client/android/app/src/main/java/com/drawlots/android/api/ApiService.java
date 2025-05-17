package com.drawlots.android.api;

import com.drawlots.android.model.Building;
import com.drawlots.android.model.VipApplication;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    
    // 认证相关
    @POST("auth/dingtalk/login")
    Call<Map<String, Object>> dingTalkLogin(@Body Map<String, String> loginData);
    
    @POST("auth/feishu/login")
    Call<Map<String, Object>> feishuLogin(@Body Map<String, String> loginData);
    
    // 楼层相关
    @GET("buildings")
    Call<List<Building>> getAllBuildings();
    
    @GET("buildings/available")
    Call<List<Building>> getAvailableBuildings();
    
    @GET("buildings/selected")
    Call<List<Building>> getSelectedBuildings();
    
    @POST("buildings/{id}/select")
    Call<Map<String, Object>> selectBuilding(@Path("id") Long id);
    
    // VIP申请相关
    @POST("vip/apply")
    Call<Map<String, Object>> applyForVip(@Body Map<String, String> applicationData);
    
    @GET("vip/applications")
    Call<List<VipApplication>> getUserApplications();
    
    @GET("vip/admin/applications")
    Call<List<VipApplication>> getPendingApplications();
    
    @POST("vip/admin/applications/{id}/approve")
    Call<Map<String, Object>> approveApplication(@Path("id") Long id, @Body Map<String, Integer> approvalData);
    
    @POST("vip/admin/applications/{id}/reject")
    Call<Map<String, Object>> rejectApplication(@Path("id") Long id);
} 
 