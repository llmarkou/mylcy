package com.lbbs.test.activity;

import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.com.lbb.test.R;
import com.lbbs.test.entity.PostQueryInfo;
import com.lbbs.test.entity.Student;
import com.lbbs.test.global.App;
import com.lbbs.test.global.UrlConfig;
import com.lbbs.test.http.RetrofitHttpService;
import com.lbbs.test.http.HttpUtil;
import com.lbbs.test.http.HttpResult;
import com.lbbs.test.http.ResultCallBack;
import com.lbbs.test.utils.DownloadUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityTest extends BaseActivity {
    public static final String TAP = "APP*****";
    private DownloadUtils downloadUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        downloadUtils = new DownloadUtils(this);
        downloadUtils.downloadAPK("http://192.168.1.104:8080/XXX.apk", "XXX.apk");

    }
    void onClick() {
        String url = "http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg";
        Glide.with(this).load(url)
                .placeholder(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new ImageView(this));
//        get();
        post();
    }

    private void get() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitHttpService service = retrofit.create(RetrofitHttpService.class);
        Call<HttpResult<ResponseBody>> repos = service.listRepos("octocat");
        repos.enqueue(new ResultCallBack<ResponseBody>() {

            @Override
            public void onSuccess(ResponseBody data) {

            }

            @Override
            public void onFailure(String msg) {

            }
        });repos.cancel();
    }

    private void post() {
        RetrofitHttpService service = HttpUtil.createService(RetrofitHttpService.class);
        Call<HttpResult<PostQueryInfo>> repos = service.search("yuantong","500379523313");
        repos.enqueue(new ResultCallBack<PostQueryInfo>() {

            @Override
            public void onSuccess(PostQueryInfo data) {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    void uploadOneImage() {
        RetrofitHttpService service = HttpUtil.createService(RetrofitHttpService.class);
        File file = new File(Environment.getExternalStorageDirectory()+"/Pictures", "xuezhiqian.png");
        //设置Content-Type:application/octet-stream
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        //设置Content-Disposition:form-data; name="photo"; filename="xuezhiqian.png"
        MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", file.getName(), photoRequestBody);
        //添加参数用户名和密码，并且是文本类型
        RequestBody userName = RequestBody.create(MediaType.parse("text/plain"), "abc");
        RequestBody passWord = RequestBody.create(MediaType.parse("text/plain"), "123");
        Call<HttpResult<ResponseBody>> loadCall = service.uploadfile(photo, userName,passWord);
        loadCall.enqueue(new ResultCallBack<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody data) {

            }

            @Override
            public void onFailure(String msg) {

            }

        });


    }

    void uploadMoreImage() {
        RetrofitHttpService service = HttpUtil.createService(RetrofitHttpService.class);
        File file = new File(Environment.getExternalStorageDirectory()+"/Pictures", "xuezhiqian.png");
        File file2 = new File(Environment.getExternalStorageDirectory()+"/Pictures", "xuezhiqian2.png");
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        RequestBody photoRequestBody2 = RequestBody.create(MediaType.parse("application/octet-stream"), file2);
        RequestBody userName = RequestBody.create(MediaType.parse("text/plain"), "abc");
        RequestBody passWord = RequestBody.create(MediaType.parse("text/plain"), "123");
        Map<String,RequestBody> photos = new HashMap<>();
        //添加文件一
        photos.put("photos\"; filename=\""+file.getName(), photoRequestBody);
        //添加文件二
        photos.put("photos\"; filename=\""+file2.getName(), photoRequestBody2);
        //添加用户名参数
        photos.put("username",  userName);
        Call<HttpResult<ResponseBody>> loadCall = service.uploadMorefiles(photos, passWord);
        loadCall.enqueue(new ResultCallBack<ResponseBody>() {

            @Override
            public void onSuccess(ResponseBody data) {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }
    void testHttp() {
        RetrofitHttpService service = HttpUtil.createService(RetrofitHttpService.class);
        Call<HttpResult<PostQueryInfo>> repos = service.search("yuantong","500379523313");
        repos.enqueue(new ResultCallBack<PostQueryInfo>() {

            @Override
            public void onSuccess(PostQueryInfo data) {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    void testUser() {
        RetrofitHttpService service = HttpUtil.createService(RetrofitHttpService.class);
        Student stu=new Student();
        stu.setId("20103177");
        stu.setName("zpm");
        stu.setAge(18);
        Call<HttpResult<ResponseBody>> repos = service.user(stu, true);
        repos.enqueue(new ResultCallBack<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody data) {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    void testNewHttpUtil()  {
        Student student = new Student();
        student.setId("20103177");
        student.setName("zpm");
        student.setAge(18);
        HttpUtil.post(this, UrlConfig.GET_USER_INFO, student, new ResultCallBack<PostQueryInfo>() {
            @Override
            public void onSuccess(PostQueryInfo data) {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }



    @Override
    public void onBackPressed() {
        App.getInstance().exitBy2Click(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpUtil.cancel(this);  //切记要移除请求
    }
}
