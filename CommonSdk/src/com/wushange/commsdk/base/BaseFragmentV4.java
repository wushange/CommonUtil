package com.wushange.commsdk.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wushange.commsdk.customview.svprogresshud.SVProgressHUD;

import org.xutils.x;

public abstract class BaseFragmentV4 extends Fragment implements IBaseFragment {
    private View mContextView = null;
    protected Activity mContext = null;
    protected final String TAG = this.getClass().getSimpleName();
    private String title;
    private int iconId;
    private boolean isVisible;
    private boolean isPrepared;
    private boolean isFirstLoad = true;
    private SVProgressHUD mSVProgressHUD;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        Log.d(TAG, "BaseFragmentV4-->onAttach()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "BaseFragmentV4-->onCreate()");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "BaseFragmentV4-->onCreateView()");
        if (null == mContextView) {
            initParms(getArguments());
            View mView = bindView();
            if (null == mView) {
                isFirstLoad = true;
                mContextView = inflater.inflate(bindLayout(), container, false);
            } else {
                mContextView = mView;
            }
            x.view().inject(this, mContextView);
            initView(mContextView);
            isPrepared = true;
            doBusiness(getActivity());
            lazyLoad();

        }

        return mContextView;
    }

    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad) {
            return;
        }
        isFirstLoad = false;

        lazyInitBusiness(getActivity());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(TAG, "BaseFragmentV4-->onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "BaseFragmentV4-->onSaveInstanceState()");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "BaseFragmentV4-->onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "BaseFragmentV4-->onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "BaseFragmentV4-->onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "BaseFragmentV4-->onStop()");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "BaseFragmentV4-->onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "BaseFragmentV4-->onDetach()");
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mContextView != null && mContextView.getParent() != null) {
            ((ViewGroup) mContextView.getParent()).removeView(mContextView);
        }
    }

    public Activity getContext() {
        return getActivity();
    }

    public void initIconWithText(String text, int iconId) {
        this.title = text;
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;

    }

    public int getIconId() {
        return iconId;
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
