package com.parttime.Modules;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.parttime.Application.GraduationApp;

/**
 * Created by ziyang on 2015/7/1.
 * @Description 用户信息本地化保存
 */
public class Config {

    private static Config instance = new Config();

    public static Config getInstance() {
        return instance;
    }
    SharedPreferences sharedPreferences;

    public Config() {
        sharedPreferences = GraduationApp.getInstance().getSharedPreferences("config",0);
    }

    /**
     * 获取登录状态
     * @return true for login or false for un-login
     */
    public static Boolean getLoginStatus(){
        return Config.getInstance().getBoolean("LoginStatus",false);
    }

    /**
     * 设置登录状态
     * @param loginStatus 登录状态
     */
    public static void setLoginStatus(Boolean loginStatus){
        Config.getInstance().setBoolean("LoginStatus",loginStatus);
    }
    /**
     * @Description 保存用户信息
     * @auther Jumy 2015/7/1 16:29
     */
    public void set(String name, String value) {
        Editor editor = sharedPreferences.edit();
        editor.putString(name, value);

        editor.commit();
    }

    public void delete(String name) {
        Editor editor = sharedPreferences.edit();
        editor.remove(name);
        editor.commit();
    }

    public String get(String name) {
        try {
            return sharedPreferences.getString(name, "");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @Description 取出用户信息
     * @auther Jumy 2015/7/1 16:29
     */
    public String get(String name, String defValue) {
        try {
            return sharedPreferences.getString(name, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }

    public void setBoolean(String name, Boolean value) {
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    public boolean getBoolean(String name) {
        try {
            return sharedPreferences.getBoolean(name, false);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean getBoolean(String name, Boolean defValue) {
        try {
            return sharedPreferences.getBoolean(name, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }

    public void setInt(String name, int value) {
        Editor editor = sharedPreferences.edit();
        editor.putInt(name, value);
        editor.commit();
    }

    public int getInt(String name, int def) {
        try {
            return sharedPreferences.getInt(name, def);
        } catch (Exception e) {
            return def;
        }
    }
    public void setFloat(String name, float dou){
        Editor editor = sharedPreferences.edit();
        editor.putFloat(name, dou);
        editor.commit();
    }

    public float getFloat(String name, float flo) {
        try {
            return sharedPreferences.getFloat(name,flo);
        } catch (Exception e) {
            return flo;
        }
    }
    public boolean contains(String name) {
        return sharedPreferences.contains(name);
    }
}
