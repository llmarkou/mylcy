package com.lbbs.test.global;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.lbbs.test.utils.ChannelUtil;
import com.lbbs.test.utils.LogUtils;
import com.lbbs.test.utils.ToolUtil;
import com.squareup.leakcanary.LeakCanary;
import com.com.lbb.test.BuildConfig;
import com.lbbs.test.http.HttpUtil;

import java.util.Timer;
import java.util.TimerTask;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;

/**
 * Application类
 */
public class App extends MultiDexApplication {
    private static App appInstance;

    public static App getInstance() {
        return appInstance;
    }
    public static String channel;
    // 双击退出标志位
    private static Boolean isExit = false;
    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        channel = ChannelUtil.getChannel(this.getApplicationContext(), "guanwang");
        LogUtils.LOG_D(getClass(), "channel *** = "+ channel);
        if (BuildConfig.DEBUG) {
            //崩溃弹框显示
            CustomActivityOnCrash.install(this);
            //内存泄漏检测工具
        }
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        HttpUtil.initHttpService();
    }

    ;

    /**
     * app退出
     */
    public void exit(Context context) {
        try {
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 双击退出app
     *
     * @param activity
     */
    public boolean exitBy2Click(Activity activity) {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            ToolUtil.showToast(activity, "再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

            return false;
        } else {
            //退出app
            exit(activity);
            return true;
        }
    }

}
