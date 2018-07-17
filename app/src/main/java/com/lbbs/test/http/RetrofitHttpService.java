package com.lbbs.test.http;

import com.lbbs.test.entity.PostQueryInfo;
import com.lbbs.test.entity.Student;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Description:
 * Data：2018/3/15-9:55
 * Author: 刘兵兵
 */

public interface RetrofitHttpService {
    @GET("user/{user}/repos")
    Call<HttpResult<ResponseBody>> listRepos(@Path("user") String user);

    @POST("query")
    Call<HttpResult<PostQueryInfo>> search(@Query("type") String type, @Query("postid") String postid);

    //单文件上传携带参数
    @Multipart
    @POST("UploadServlet")
    Call<HttpResult<ResponseBody>> uploadfile(@Part MultipartBody.Part photo, @Part("username") RequestBody username, @Part("password") RequestBody password);

    //多文件上传携带参数
    @Multipart
    @POST("UploadServlet")
    Call<HttpResult<ResponseBody>> uploadMorefiles(@PartMap Map<String, RequestBody> params, @Part("password") RequestBody password);

    @POST("user/user")
    Call<HttpResult<ResponseBody>> user(@Body Student student, @Query("IsGay") boolean isGay);

    //上面测试用例

    //通用的post请求
    @POST()
    Call<HttpResult<ResponseBody>> post(@Url String url, @Body Object paramObject);

    //通用的post请求
    @GET()
    Call<HttpResult<ResponseBody>> get(@Url String url, @Body Object paramObject);

    //多文件上传携带参数
    @Multipart
    @POST()
    Call<HttpResult<ResponseBody>> uploadMorefile(@Url String url, @PartMap Map<String, RequestBody> params);
}
