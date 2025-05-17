package com.drawlots.android.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    
    private static final String PREFS_NAME = "DrawLotsPrefs";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_ROLE = "user_role";
    private static final String KEY_IS_VIP = "is_vip";
    
    private static SharedPreferences sharedPreferences;
    
    public static void init(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }
    }
    
    public static void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }
    
    public static String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }
    
    public static void saveUserInfo(Long userId, String userName, String userRole, boolean isVip) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(KEY_USER_ID, userId);
        editor.putString(KEY_USER_NAME, userName);
        editor.putString(KEY_USER_ROLE, userRole);
        editor.putBoolean(KEY_IS_VIP, isVip);
        editor.apply();
    }
    
    public static Long getUserId() {
        return sharedPreferences.getLong(KEY_USER_ID, -1);
    }
    
    public static String getUserName() {
        return sharedPreferences.getString(KEY_USER_NAME, null);
    }
    
    public static String getUserRole() {
        return sharedPreferences.getString(KEY_USER_ROLE, null);
    }
    
    public static boolean isAdmin() {
        return "ADMIN".equals(getUserRole());
    }
    
    public static boolean isVip() {
        return sharedPreferences.getBoolean(KEY_IS_VIP, false);
    }
    
    public static boolean isLoggedIn() {
        return getToken() != null;
    }
    
    public static void clearUserData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
} 