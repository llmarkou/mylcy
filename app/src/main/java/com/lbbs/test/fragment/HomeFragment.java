package com.lbbs.test.fragment;

import android.widget.TextView;

import com.com.lbb.test.R;

import butterknife.BindView;

/**
 *
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initTitle() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
