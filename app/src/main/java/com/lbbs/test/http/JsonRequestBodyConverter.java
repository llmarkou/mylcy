package com.lbbs.test.http;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Description:
 * Data：2018/3/26-12:06
 * Author: 刘兵兵
 */

public class JsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private final Gson gson;
    private final TypeAdapter<T> adapter;
    public JsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
//        String string = value.string();
//        System.out.println("#解密前#" + string);
//        string = AesEncryptionUtil.decrypt(string);
//        System.out.println("#解密后#" + string);
//        return adapter.fromJson(string);
        return null;
    }
}
