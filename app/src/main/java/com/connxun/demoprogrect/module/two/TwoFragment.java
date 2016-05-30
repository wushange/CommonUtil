package com.connxun.demoprogrect.module.two;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.connxun.demoprogrect.R;
import com.wushange.commsdk.base.BaseFragmentV4;
import com.wushange.commsdk.util.TipUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

public class TwoFragment extends BaseFragmentV4 {
    @ViewInject(R.id.two_1)
    Button two1;

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

    }

    @Event(R.id.two_1)
    private void setTwo1(View v) {
        TipUtil.showToolTipView(getContext(), v, Gravity.RIGHT, "我是简介提示",
                ContextCompat.getColor(getActivity(), R.color.blue));
    }
}