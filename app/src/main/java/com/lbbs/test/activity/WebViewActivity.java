package com.lbbs.test.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.com.lbb.test.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * webview页面
 */
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;
    @BindView(R.id.my_webview)
    WebView myWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        init();
        myWebview.loadUrl("http://www.baidu.com");
    }

    private void init() {
        myWebview.setWebViewClient(new WebViewClient() {
            //覆写shouldOverrideUrlLoading实现内部显示网页
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings seting = myWebview.getSettings();
        seting.setJavaScriptEnabled(true);//设置webview支持javascript脚本
        myWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar1.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    progressBar1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar1.setProgress(newProgress);//设置进度值
                }

            }
        });

    }

    //设置返回键动作（防止按返回键直接退出程序)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (myWebview.canGoBack()) {
                myWebview.goBack();
                return true;
            } else {
                finish();
            }


        }
        return super.onKeyDown(keyCode, event);
    }
}

