package com.wushange.commsdk.base;

import android.content.Context;
import android.view.View;

public interface IBaseActivity {

    public int bindLayout();

    public void initView(final View view);

    public void doBusiness(Context mContext);


}
