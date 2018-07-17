package com.lbbs.test.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.com.lbb.test.R;
import com.lbbs.test.entity.PostQueryInfo;
import com.lbbs.test.entity.Student;
import com.lbbs.test.global.UrlConfig;
import com.lbbs.test.http.HttpUtil;
import com.lbbs.test.http.ResultCallBack;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_test)
    TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        String content = "\"合同类5—7项\"、\"其他类10-12项\"、\"上传文件请务必命名!!!\"";
        tvTest.setTextKeepState(content);
        tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                toActivity(WebViewActivity.class);
                testNewHttpUtil();
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
}

