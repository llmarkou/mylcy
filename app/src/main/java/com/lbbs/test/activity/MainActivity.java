package com.lbbs.test.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import com.com.lbb.test.R;
import com.lbbs.test.fragment.BaseFragment;
import com.lbbs.test.fragment.HomeFragment;
import com.lbbs.test.fragment.MyFragment;
import com.lbbs.test.fragment.WorkFragment;
import com.lbbs.test.global.App;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.ll_main_tab1)
    LinearLayout llMainTab1;
    @BindView(R.id.ll_main_tab2)
    LinearLayout llMainTab2;
    @BindView(R.id.ll_main_tab3)
    LinearLayout llMainTab3;
    private int mPageIndex = 0;
    private FragmentManager mFragmentManager;
    private ArrayList<BaseFragment> mPageFragmentList;
    private BaseFragment curFragment;Handler mH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initFragment();
    }

    /**
     * 初始化view
     */
    private void initView() {
        llMainTab1.setSelected(true);
        initFragment();
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        mFragmentManager = getSupportFragmentManager();
        mPageFragmentList = new ArrayList<BaseFragment>();
        mPageFragmentList.add(new HomeFragment());
        mPageFragmentList.add(new WorkFragment());
        mPageFragmentList.add(new MyFragment());
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        for (BaseFragment every : mPageFragmentList) {
            fragmentTransaction.add(R.id.fl_fragment_layout, every);
        }
        fragmentTransaction.commitAllowingStateLoss();
        selectedFragment(mPageFragmentList.get(0));
    }

    /**
     * 切换tab
     *
     * @param pageIndex page索引
     */
    private void selectTab(int pageIndex) {
        switch (pageIndex) {
            case 0:
                mPageIndex = 0;
                llMainTab1.setSelected(true);
                llMainTab2.setSelected(false);
                llMainTab3.setSelected(false);
                break;
            case 1:
                mPageIndex = 1;
                llMainTab1.setSelected(false);
                llMainTab2.setSelected(true);
                llMainTab3.setSelected(false);
                break;
            case 2:
                mPageIndex = 2;
                llMainTab1.setSelected(false);
                llMainTab2.setSelected(false);
                llMainTab3.setSelected(true);
                break;
        }
        selectedFragment(mPageFragmentList.get(mPageIndex));
    }

    /**
     *选中fragment
     *
     * @param f
     */
    private void selectedFragment(BaseFragment f) {
        if (f == null || f == curFragment) {
            return;
        }
        //使用show hide 提高运行速度
        if (!mFragmentManager.isDestroyed()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (!isDestroyed()) {
                    showAndHideFragment(f);
                } else {
                    return;
                }
            } else {
                showAndHideFragment(f);
            }
        }
        curFragment = f;
    }

    /**
     * 显示并隐藏Fragment
     *
     * @param f fragment
     */
    private void showAndHideFragment(BaseFragment f) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        for (BaseFragment every : mPageFragmentList) {
            fragmentTransaction.hide(every);
        }
        fragmentTransaction.show(f);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @OnClick({R.id.ll_main_tab1, R.id.ll_main_tab2, R.id.ll_main_tab3})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_main_tab1: {
                selectTab(0);
                break;
            }
            case R.id.ll_main_tab2: {
                selectTab(1);
                break;
            }
            case R.id.ll_main_tab3: {
                selectTab(2);
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        App.getInstance().exitBy2Click(this);
    }
}
