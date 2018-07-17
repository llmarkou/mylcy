package com.lbbs.test.http;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Description:
 * Data：2018/3/26-12:05
 * Author: 刘兵兵
 */

public class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;
    public JsonResponseBodyConverter(Gson  gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody responseBody) throws IOException {
//        String string = responseBody.string();
//        System.out.println("#解密前#" + string);
//        string = AesEncryptionUtil.decrypt(string);
//        System.out.println("#解密后#" + string);
//        return adapter.fromJson(string);
        //解密处理
        return null;
    }
}
