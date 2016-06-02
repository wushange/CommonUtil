package com.connxun.demoprogrect.module.two;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.connxun.demoprogrect.R;
import com.connxun.demoprogrect.module.activity.CenterTabMainActivity;
import com.wushange.commsdk.base.BaseFragmentV4;
import com.wushange.commsdk.util.ACache;
import com.wushange.commsdk.util.DialogUtil;
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
    @ViewInject(R.id.two_3)
    Button two3;
    @ViewInject(R.id.two_4)
    Button two4;
    @ViewInject(R.id.two_5)
    Button two5;
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
        showLoading();
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

    @Event(R.id.two_3)
    private void setTwo3(View v) {
        startActivity(new Intent(getContext(), CenterTabMainActivity.class));
    }

    @Event(R.id.two_4)
    private void setTwo4(View v) {
        ACache.get(getContext()).put("TEST", "我是acahe存储进来的");
        DialogUtil.showSVProgress(getContext(), "添加成功", DialogUtil.SVProgressHUDStyle.SUCCESSWHITMSG);
    }

    @Event(R.id.two_5)
    private void setTwo5(View v) {
        TextView vv = (TextView) v;
        vv.setText(ACache.get(getContext()).getAsString("TEST"));
        DialogUtil.showSVProgress(getContext(), "测试成功", DialogUtil.SVProgressHUDStyle.SUCCESSWHITMSG);
    }
}