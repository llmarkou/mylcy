package com.lbbs.test.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.com.lbb.test.R;

/**
 * 引导页适配器
 */
public class IntroduceAdapter extends PagerAdapter {

    private Context mContext;
    private int[] mRes;
    private Intent intent;

    public IntroduceAdapter(Context context, int[] res, Intent intent) {
        mContext = context;
        mRes = res;
        this.intent = intent;
    }

    @Override
    public int getCount() {
        return mRes.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_introduce, container,false);
        ((ImageView) view.findViewById(R.id.introduce_img))
                .setImageResource(mRes[position]);
        container.addView(view);
        return view;
    }
}
