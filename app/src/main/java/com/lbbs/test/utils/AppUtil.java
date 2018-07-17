package com.lbbs.test.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

import com.lbbs.test.global.App;

import java.io.File;

/**
 * Created by liubingbing on 2017/7/3.
 */

public class AppUtil {
    public static boolean isNetwordReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return current.isAvailable();
    }

    /**
     * 获取缓存路径
     *
     * @param uniqueName
     * @return
     */
    public static File getDiskCacheDir(String uniqueName) {
        Context context = App.getInstance().getApplicationContext();
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            //如果SD卡存在通过getExternalCacheDir()获取路径，
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            //如果SD卡不存在通过getCacheDir()获取路径，
            cachePath = context.getCacheDir().getPath();
        }
        //放在路径 /.../data/<application package>/cache/uniqueName
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName() {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = App.getInstance().getApplicationContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(App.getInstance().getApplicationContext().getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }
}
