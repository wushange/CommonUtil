package com.wushange.commsdk.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.wushange.commsdk.customview.svprogresshud.SVProgressHUD;
import com.wushange.commsdk.swipebacklayout.activity.SwipeBackActivity;

import org.xutils.x;

import java.lang.ref.WeakReference;


/**
 * android  Activity基类
 *
 * @author wushange
 * @version 1.0
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public abstract class BaseSwipeBackActivity extends SwipeBackActivity implements IBaseActivity {

    private WeakReference<Activity> context = null;
    private View mContextView = null;
    private SVProgressHUD mSVProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
        setContentView(mContextView);
        context = new WeakReference<Activity>(this);
        x.view().inject(this);
        initView(mContextView);
        doBusiness(this);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected Activity getContext() {
        if (null != context)
            return context.get();
        else
            return null;
    }

    protected void showLoading() {
        mSVProgressHUD = new SVProgressHUD(getContext());
        mSVProgressHUD.showWithStatus("请稍后...");

    }

    protected void showLoading(String text) {
        mSVProgressHUD = new SVProgressHUD(getContext());
        mSVProgressHUD.showWithStatus(text);

    }

    protected void showLoadingCanCancelable() {
        mSVProgressHUD = new SVProgressHUD(getContext());
        mSVProgressHUD.showWithStatusCanCancelable("请稍后...");

    }

    protected void showLoadingCanCancelable(String text) {
        mSVProgressHUD = new SVProgressHUD(getContext());
        mSVProgressHUD.showWithStatusCanCancelable(text);

    }

    protected void dissLoading() {
        if (mSVProgressHUD != null) {
            mSVProgressHUD.dismiss();
        }
    }


}
