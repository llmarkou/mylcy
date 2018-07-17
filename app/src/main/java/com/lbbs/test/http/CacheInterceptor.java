package com.lbbs.test.http;

import com.lbbs.test.global.App;
import com.lbbs.test.utils.AppUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liubingbing on 2017/6/23.
 * 缓存拦截器配置
 * ps:只有get请求才具备http的缓存功能，post没有
 */

public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //没网强制从缓存读取(必须得写，不然断网状态下，退出应用，或者等待一分钟后，就获取不到缓存）
        if (!AppUtil.isNetwordReachable(App.getInstance().getApplicationContext())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);
        Response responseLatest;
        if (AppUtil.isNetwordReachable(App.getInstance().getApplicationContext())) {
            int maxAge = 60; //有网失效一分钟  也可以设置为0
            responseLatest = response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("")   //清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build();
        } else {
            // 无网络时，设置超时为4周
            int maxStale = 60 * 60 * 24 * 28;
            responseLatest = response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("")
                    .build();
        }
        return responseLatest;
    }
}
