package com.wushange.commsdk.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.wushange.commsdk.customview.svprogresshud.SVProgressHUD;

import org.xutils.x;

import java.lang.ref.WeakReference;


@TargetApi(Build.VERSION_CODES.KITKAT)
public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {

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


    protected void showLoading(String text) {
        mSVProgressHUD = new SVProgressHUD(getContext());
        mSVProgressHUD.showWithStatus("«Î…‘∫Û...");

    }

    protected void showLoadingCanCancelable(String text) {
        mSVProgressHUD = new SVProgressHUD(getContext());
        mSVProgressHUD.showWithStatusCanCancelable("«Î…‘∫Û...");

    }

    protected void dissLoading() {
        if (mSVProgressHUD != null) {
            mSVProgressHUD.dismiss();
        }
    }

}
