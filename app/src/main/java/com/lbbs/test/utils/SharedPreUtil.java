package com.lbbs.test.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;


public class SharedPreUtil {
    public final static String CACHE = "CACHE";
    private SharedPreferences Obj = null;
    private static volatile SharedPreUtil instance;

    // 登录相关信息
    static final public String bLoginState = "bLoginState";
    // 记住密码状态
    static final public String bRememberPwd = "bRememberPwd";
    // 登录密码
    public final static String sLoginPwd = "sLoginPwd";
    // AES Key
    public final static String sSessionToken = "sSessionToken";
    // 对应服务器aesKey
    public final static String sDeviceId = "sDeviceId";
    // 登录手机号
    public final static String sPhoneNumber = "sPhoneNumber";
    // 登录用户名
    public final static String sUserName = "sUserName";
    // 申请编号
    public final static String sSqbh = "sSqbh";
    // 是否第一次使用APP
    public final static String bFirstUse = "bFirstUse";
    public final static String sBannerUrl = "sBanner";

    public static SharedPreUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (SharedPreUtil.class) {
                if (instance == null) {
                    instance = new SharedPreUtil(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    private SharedPreUtil(Context context) {
        Obj = context.getSharedPreferences(CACHE, Context.MODE_PRIVATE);
    }

    public boolean getBoolean(String key, boolean value) {
        if (Obj != null) {
            return Obj.getBoolean(key, value);
        }
        return false;
    }

    public boolean setBoolean(String key, boolean value) {
        if (Obj != null) {
            SharedPreferences.Editor editor = Obj.edit();
            editor.putBoolean(key, value);
            return editor.commit();
        }
        return false;
    }

    public int getInt(String key, int defaultValue) {
        if (Obj != null) {
            String sValue = Obj.getString(key, null);
            return Integer.parseInt(sValue);
        }
        return defaultValue;
    }

    public boolean setInt(String key, int value) {
        if (Obj != null) {
            SharedPreferences.Editor editor = Obj.edit();
            String sValue = String.valueOf(value);
            editor.putString(key, sValue);
            return editor.commit();
        }
        return false;
    }

    public String getString(String key, String value) {
        if (Obj != null) {
            return Obj.getString(key, value);
        }
        return null;
    }

    public boolean setString(String key, String value) {
        if (Obj != null) {
            SharedPreferences.Editor editor = Obj.edit();
            editor.putString(key, value);
            return editor.commit();
        }
        return false;
    }

    public boolean setMap(String key, HashMap<String, String> map) {
        if (Obj != null) {
            SharedPreferences.Editor editor = Obj.edit();
            editor.putString(key, map.toString());
            return editor.commit();
        }
        return false;
    }

    public boolean clearString(String key) {
        if (Obj != null) {
            SharedPreferences.Editor editor = Obj.edit();
            editor.remove(key);
            return editor.commit();
        }
        return false;
    }

    public boolean contains(String key) {
        if (Obj != null) {
            return Obj.contains(key);
        }
        return false;
    }
}
