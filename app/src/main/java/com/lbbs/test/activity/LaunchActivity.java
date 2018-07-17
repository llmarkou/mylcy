package com.lbbs.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.lbbs.test.utils.AppUtil;
import com.lbbs.test.utils.SharedPreUtil;
import com.lbbs.test.utils.ToolUtil;


/**
 * 启动页
 */
public class LaunchActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkNetwork();
        checkIsShowIntroducePage();
    }

    /**
     *
     * 检查是否显示介绍页面
     */
    private void checkIsShowIntroducePage() {
        if (!SharedPreUtil.getInstance(this).getBoolean(SharedPreUtil.bFirstUse, false)) {
            Intent intent = new Intent(this,IntroduceActivity.class);
            startActivity(intent);
            finish();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    toActivity(LoginActivity.class);
                    finish();
                }
            }, 2000);
        }
    }

    /**
     *  判断用户是否已开启网络，未开始则弹出对话框让用户开启
     */
    private void checkNetwork(){
        // 检测网络是否正常
        if(!AppUtil.isNetwordReachable(this)) {
            ToolUtil.showToast(this, "请检查您的网络！");
        }
    }
}
