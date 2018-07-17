package com.lbbs.test.http;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liubingbing on 2017/6/30.
 */

public abstract class ResultCallBack<T> implements Callback<HttpResult<T>> {

    public abstract void onSuccess(T data);

    public abstract void onFailure(String msg);

    public void onAutoLogin() {
        //去重新登录页面
    }

    @Override
    public void onResponse(Call<HttpResult<T>> call, Response<HttpResult<T>> response) {
        HttpResult httpResult = response.body();
        if (httpResult == null) {
            onFailure("data == null");
            return;
        }
        if ("000".equals(httpResult.getResultCode())) {
            onSuccess((T) httpResult.getData());
        } else if ("007".equals(httpResult.getResultCode())) {
            //重新登录
            onAutoLogin();
        }
    }

    @Override
    public void onFailure(Call<HttpResult<T>> call, Throwable t) {
        if (t instanceof SocketTimeoutException) {
            //
        } else if (t instanceof ConnectException) {
            //
        } else if (t instanceof RuntimeException) {
            //
        }
        onFailure(t.getMessage());
    }
}
