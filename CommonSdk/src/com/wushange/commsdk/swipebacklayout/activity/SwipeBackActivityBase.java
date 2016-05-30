package com.wushange.commsdk.swipebacklayout.activity;

import com.wushange.commsdk.swipebacklayout.SwipeBackLayout;
public interface SwipeBackActivityBase {
    public abstract SwipeBackLayout getSwipeBackLayout();

    public abstract void setSwipeBackEnable(boolean enable);
    public abstract void scrollToFinishActivity();

}
