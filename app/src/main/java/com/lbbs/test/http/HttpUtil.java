package com.lbbs.test.http;

import com.com.lbb.test.BuildConfig;
import com.lbbs.test.utils.AppUtil;

import java.io.File;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Http请求工具
 */

public class HttpUtil {
    public static final String BASE_URL = BuildConfig.BASE_URL;
    public static final HttpUtil INSTANCE = new HttpUtil();
    private static final int DEFAULT_TIMEOUT = 15;
    private static RetrofitHttpService mRetrofitHttpService;
    private static Map<String, Call> CALL_MAP = new HashMap<>();
    private Retrofit retrofit;

    /**
     * 单例初始化
     */
    private HttpUtil() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        // Log信息拦截器:设置 Debug Log 模式
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(logging);
        }
        //设置缓存
        File cacheFile = AppUtil.getDiskCacheDir("WuNingCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);       //50M缓存
        CacheInterceptor cacheInterceptor = new CacheInterceptor();
        httpClientBuilder.cache(cache);
        httpClientBuilder.addInterceptor(cacheInterceptor);
        httpClientBuilder.addNetworkInterceptor(cacheInterceptor);
        httpClientBuilder
                .addInterceptor(new CustomInterceptor())      //自定义拦截器设置公共参数
                .addInterceptor(new RequestHeaderInterceptor())     //设置请求头信息
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        //证书添加
//        httpClientBuilder.certificatePinner(new CertificatePinner.Builder()
//                .add("YOU API.com", "sha1/DmxUShsZuNiqPQsX2Oi9uv2sCnw=")
//                .add("YOU API..com", "sha1/SXxoaOSEzPC6BgGmxAt/EAcsajw=")
//                .add("YOU API..com", "sha1/blhOM3W9V/bVQhsWAcLYwPU6n24=")
//                .add("YOU API..com", "sha1/T5x9IXmcrQ7YuQxXnxoCmeeQ84c=")
//                .build());

        //错误重连
        httpClientBuilder.retryOnConnectionFailure(true);
        //cookie设置
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        httpClientBuilder.cookieJar(new JavaNetCookieJar(cookieManager));
        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    /**
     * 初始化http请求服务,放在application调用
     */
    public static void initHttpService() {
        mRetrofitHttpService = createService(RetrofitHttpService.class);
    }

    /**
     * 服务对象
     *
     * @param cls 服务对象实体
     * @param <T> 实体
     * @return 服务对象
     */
    public static <T> T createService(Class<T> cls) {
        T service = INSTANCE.retrofit.create(cls);
        return service;
    }

    /**
     * 取消某个界面都所有请求，或者是取消某个tag的所有请求
     * 如果要取消某个tag单独请求，tag需要转入tag+url
     *
     * @param tag 目标对象
     */
    public static synchronized void cancel(Object tag) {
        if (tag == null)
            return;
        List<String> list = new ArrayList<>();
        synchronized (CALL_MAP) {
            for (String key : CALL_MAP.keySet()) {
                if (key.startsWith(tag.toString())) {
                    CALL_MAP.get(key).cancel();
                    list.add(key);
                }
            }
        }
        for (String s : list) {
            removeCall(s);
        }
    }

    /**
     * 移除某个请求
     *
     * @param url 移除的url
     */
    private static synchronized void removeCall(String url) {
        synchronized (CALL_MAP) {
            for (String key : CALL_MAP.keySet()) {
                if (key.contains(url)) {
                    url = key;
                    break;
                }
            }
            CALL_MAP.remove(url);
        }
    }

    /**
     * 发送post请求
     *
     * @param tag            目标对象，直接可以传递activity、fragment等等
     * @param url            请求的url地址
     * @param paramObject    请求的参数对象
     * @param resultCallBack 请求的回调返回
     * @param <T>            返回的具体对象
     */
    public static <T> void post(Object tag, String url, Object paramObject, ResultCallBack<T> resultCallBack) {
        Call call = mRetrofitHttpService.post(url, paramObject);
        putCallToMap(tag, url, call);
        call.enqueue(resultCallBack);
    }

    /**
     * 将call添加进map
     *
     * @param tag  目标tag
     * @param url  请求url
     * @param call 请求call
     */
    private static synchronized void putCallToMap(Object tag, String url, Call call) {
        if (tag == null)
            return;
        synchronized (CALL_MAP) {
            CALL_MAP.put(tag.toString() + url, call);
        }
    }

    /**
     * 发送get请求
     *
     * @param tag            目标对象，直接可以传递activity、fragment等等
     * @param url            请求的url地址
     * @param paramObject    请求的参数对象
     * @param resultCallBack 请求的回调返回
     * @param <T>            返回的具体对象
     */
    public static <T> void get(Object tag, String url, Object paramObject, ResultCallBack<T> resultCallBack) {
        Call call = mRetrofitHttpService.get(url, paramObject);
        putCallToMap(tag, url, call);
        call.enqueue(resultCallBack);
    }

    /**
     * 上传文件
     *
     * @param tag            目标对象，直接可以传递activity、fragment等等
     * @param url            请求的url地址
     * @param paramMap       请求的参数对象
     * @param resultCallBack 请求的回调返回
     * @param <T>            返回的具体对象
     */
    public static <T> void uploadMorefile(Object tag, String url, Map<String, RequestBody> paramMap, ResultCallBack<T> resultCallBack) {
        Call call = mRetrofitHttpService.uploadMorefile(url, paramMap);
        putCallToMap(tag, url, call);
        call.enqueue(resultCallBack);
    }


//    //创建接口对象
//    public static <T> T createService(Class<T> cls) {
//        T service;
//        Object serviceObject = cacheService.get(cls);
//        if (serviceObject != null) {
//            service = cls.cast(serviceObject);
//        } else {
//            service = INSTANCE.retrofit.create(cls);
//            cacheService.put(cls, service);
//        }
//        return service;
//    }
}
