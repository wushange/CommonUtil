package com.connxun.demoprogrect.module.activity;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import com.connxun.demoprogrect.R;
import com.wushange.commsdk.base.BaseSwipeBackActivity;

/**
 * Created by wushange on 2016/05/30.
 */
public class TestSwipeBackActivity extends BaseSwipeBackActivity {
    @Override
    public int bindLayout() {
        return R.layout.activity_test_view;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void doBusiness(Context mContext) {
        showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dissLoading();
            }
        }, 5000);
    }
}
