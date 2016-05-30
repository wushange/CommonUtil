package com.connxun.demoprogrect;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.wushange.commsdk.base.BaseSwipeBackActivity;
import com.wushange.commsdk.customview.actionsheet.ActionSheetDialog.OnSheetItemClickListener;
import com.wushange.commsdk.util.DialogUtil;
import com.wushange.commsdk.util.DialogUtil.SVProgressHUDStyle;
import com.wushange.commsdk.util.ShowToast;
import com.wushange.commsdk.util.TipUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

public class MainActivity extends BaseSwipeBackActivity {

    private Context context;
    @ViewInject(R.id.bt1)
    Button bt1;
    @ViewInject(R.id.bt2)
    Button bt2;
    @ViewInject(R.id.bt3)
    Button bt3;
    @ViewInject(R.id.bt4)
    Button bt4;
    @ViewInject(R.id.bt5)
    Button bt5;
    @ViewInject(R.id.bt6)
    Button bt6;

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void doBusiness(Context mContext) {
        context = getContext();
    }


    @Event(R.id.bt1)
    private void bt1(View v) {
        DialogUtil.showCustomDialog(MainActivity.this, true, "哈哈", "nihao ",
                "a", null, "a", null);

    }

    @Event(R.id.bt2)
    private void bt2(View v) {
        String[] items = {"男", "女"};
        DialogUtil.showActionSheetDialog(context, "选择性别", items,
                new OnSheetItemClickListener() {

                    @Override
                    public void onClick(int which) {
                        ShowToast.showToast(context, "哈哈");
                    }
                });

    }

    @Event(R.id.bt3)
    private void bt3(View v) {

        TipUtil.showTip(context, v, "haha", "测试常用工具类");
    }

    @Event(R.id.bt4)
    private void bt4(View v) {
        DialogUtil.showSVProgress(context, "我是等待狂",
                SVProgressHUDStyle.INFOWITHMSG);

    }

    @Event(R.id.bt5)
    private void bt5(View v) {
        ShowToast.showToast(context, "我是toast");

    }

    @Event(R.id.bt6)
    private void bt6(View v) {

    }

}