package com.connxun.demoprogrect.module.three;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.connxun.demoprogrect.R;
import com.wushange.commsdk.base.BaseFragmentV4;
import com.wushange.commsdk.view.AsyncTaskBase;
import com.wushange.commsdk.view.LoadingView;

import org.xutils.view.annotation.ViewInject;

public class ThreeFragment extends BaseFragmentV4 {
    @ViewInject(R.id.loading)
    LoadingView mLoadingView;

    @Override
    public int bindLayout() {
        return R.layout.three;
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(View view) {
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void lazyInitBusiness(Context mContext) {new AsyncTaskLoading(mLoadingView).execute(0);

    }

    private class AsyncTaskLoading extends AsyncTaskBase {
        public AsyncTaskLoading(LoadingView loadingView) {
            super(loadingView);
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            int result = -1;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = 1;
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}