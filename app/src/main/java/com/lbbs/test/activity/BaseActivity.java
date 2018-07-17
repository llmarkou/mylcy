package com.lbbs.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.lbbs.test.http.HttpUtil;

/**
 * activity基类
 */
public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void toActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpUtil.cancel(this);
    }
}
