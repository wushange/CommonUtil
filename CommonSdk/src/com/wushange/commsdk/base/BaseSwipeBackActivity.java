package com.wushange.commsdk.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.wushange.commsdk.customview.svprogresshud.SVProgressHUD;
import com.wushange.commsdk.swipebacklayout.activity.SwipeBackActivity;
import com.wushange.commsdk.util.HideInputUtils;

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
        mSVProgressHUD.showWithStatus("Wait...");

    }

    protected void showLoading(String text) {
        mSVProgressHUD = new SVProgressHUD(getContext());
        mSVProgressHUD.showWithStatus(text);

    }

    protected void showLoadingCanCancelable() {
        mSVProgressHUD = new SVProgressHUD(getContext());
        mSVProgressHUD.showWithStatusCanCancelable("Wait...");

    }

    protected void showLoadingCanCancelable(String text) {
        mSVProgressHUD = new SVProgressHUD(getContext());
        mSVProgressHUD.showWithStatusCanCancelable(text);

    }

    protected void dissLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mSVProgressHUD != null) {
                    mSVProgressHUD.dismiss();
                }
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (HideInputUtils.isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public SVProgressHUD getmSVProgressHUD() {
        return mSVProgressHUD;
    }
}
