package com.lbbs.test.activity;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.com.lbb.test.R;
import com.lbbs.test.adapter.IntroduceAdapter;
import com.lbbs.test.utils.SharedPreUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 介绍页面
 */
public class IntroduceActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    // 图片切换容器
    @BindView(R.id.intr_viewpager)
    ViewPager intrViewpager;

    // 图片控件1
    @BindView(R.id.intr_view1)
    View intrView1;

    // 图片控件2
    @BindView(R.id.intr_view2)
    View intrView2;

    // 图片控件3
    @BindView(R.id.intr_view3)
    View intrView3;

    //图片控件4
    @BindView(R.id.intr_view4)
    View intrView4;

    // 进入按钮
    @BindView(R.id.intr_btn_close)
    Button intrBtnClose;

    // 滑动切换的图片列表
    private int[] RES = new int[]{R.mipmap.start_loading, R.mipmap.start_loading, R.mipmap.start_loading, R.mipmap.start_loading};

    // 页面指示小圆点 列表
    private List<View> viewList = new ArrayList<>();

    // 当前位置点
    private int cache = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        ButterKnife.bind(this);
        initView();
    }

    // 初始化界面
    private void initView() {
        viewList.add(intrView1);
        viewList.add(intrView2);
        viewList.add(intrView3);
        viewList.add(intrView4);

        IntroduceAdapter adapter = new IntroduceAdapter(this, RES, getIntent());
        intrViewpager.setAdapter(adapter);
        intrViewpager.addOnPageChangeListener(this);
    }

    // 实现了ViewPager.OnPageChangeListener接口的相关方法，完成小圆点与点击进入等元素的切换
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        line(cache, position);
        cache = position;
        intrBtnClose.setVisibility(position != (RES.length - 1) ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void line(int previous, int current) {
        if (previous >= 0)
            viewList.get(previous).setBackgroundResource(R.drawable.shape_dotted_white_stroke_orange);
        if (current >= 0)
            viewList.get(current).setBackgroundResource(R.drawable.shape_dotted_orange);
    }

    @OnClick(R.id.intr_btn_close)
    public void onClick(View v) {
        toActivity(LoginActivity.class);
        SharedPreUtil.getInstance(this).setBoolean(SharedPreUtil.bFirstUse, true);
        finish();
    }
}
