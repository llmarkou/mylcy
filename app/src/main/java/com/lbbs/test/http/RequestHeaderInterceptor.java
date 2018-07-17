package com.lbbs.test.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liubingbing on 2017/6/23.
 * 设置请求头拦截器
 */

public class RequestHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder requestBuilder = originalRequest.newBuilder()
                .header("AppType", "TPOS")
                .header("Content-Type", "application/json;charset=UTF-8")   //设置请求参数为json
                .header("Accept", "application/json")       //设置接收参数为json
                .method(originalRequest.method(), originalRequest.body());
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
