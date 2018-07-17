package com.lbbs.test.http;

import com.lbbs.test.utils.AppUtil;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liubingbing on 2017/6/23.
 */

public class CustomInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //addQueryParameter就是添加请求参数的具体代码，
        // 这种方式比较适用于所有的请求都需要添加的参数，
        // 一般现在的网络请求都会添加token作为用户标识，
        // 那么这种方式就比较适合。
        HttpUrl httpUrl = request.url().newBuilder()
                .addQueryParameter("token", "tokenValue")
                .addQueryParameter("platform", "android")
                .addQueryParameter("version", AppUtil.getAppVersionName())
                .build();
        request = request.newBuilder().url(httpUrl).build();
        return chain.proceed(request);
    }
}
