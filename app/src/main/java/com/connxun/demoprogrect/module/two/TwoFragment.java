package com.connxun.demoprogrect.module.two;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.connxun.demoprogrect.R;
import com.wushange.commsdk.base.BaseFragmentV4;
import com.wushange.commsdk.util.ToolDateTimePicker;
import com.wushange.commsdk.view.AsyncTaskBase;
import com.wushange.commsdk.view.LoadingView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

public class TwoFragment extends BaseFragmentV4 {
    @ViewInject(R.id.two_1)
    Button two1;
    @ViewInject(R.id.two_2)
    Button two2;
    @ViewInject(R.id.loading)
    LoadingView mLoadingView;

    @Override
    public int bindLayout() {
        return R.layout.two;
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
    public void lazyInitBusiness(Context mContext) {
        new AsyncTaskLoading(mLoadingView).execute(0);
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

    @Event(R.id.two_1)
    private void setTwo1(View v) {
        ToolDateTimePicker dateTimePicker = new ToolDateTimePicker(getContext(),
                (TextView) v, 1);
        dateTimePicker.show();
    }

    @Event(R.id.two_2)
    private void setTwo2(View v) {
        ToolDateTimePicker dateTimePicker = new ToolDateTimePicker(getContext(),
                (TextView) v, 2);
        dateTimePicker.show();
    }
}