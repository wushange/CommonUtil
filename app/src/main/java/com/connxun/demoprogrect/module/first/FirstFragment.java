package com.connxun.demoprogrect.module.first;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.connxun.demoprogrect.R;
import com.connxun.demoprogrect.module.activity.TestSwipeBackActivity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.wushange.commsdk.banner.ConvenientBanner;
import com.wushange.commsdk.banner.holder.CBViewHolderCreator;
import com.wushange.commsdk.banner.holder.NetworkImageHolderView;
import com.wushange.commsdk.banner.listener.OnItemClickListener;
import com.wushange.commsdk.base.BaseFragmentV4;
import com.wushange.commsdk.customview.actionsheet.ActionSheetDialog;
import com.wushange.commsdk.util.DialogUtil;
import com.wushange.commsdk.util.ShowToast;
import com.wushange.commsdk.util.TipUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Arrays;
import java.util.List;

public class FirstFragment extends BaseFragmentV4 implements OnItemClickListener {
    private List<String> networkImages;
    private String[] images = {"http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://d.3987.com/sqmy_131219/001.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"
    };
    @ViewInject(R.id.convenientBanner)
    private ConvenientBanner convenientBanner;//顶部广告栏控件
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
    @ViewInject(R.id.bt7)
    Button bt7;

    @ViewInject(R.id.bt8)
    Button bt8;

    @Override
    public int bindLayout() {
        return R.layout.first;
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
        initImageLoader();
    }

    @Override
    public void lazyInitBusiness(Context mContext) {
        networkImages = Arrays.asList(images);
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, networkImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this);
//              convenientBanner.setManualPageable(false);//设置不能手动影响
        if (convenientBanner != null)
            convenientBanner.startTurning(3000);
    }

    @Event(R.id.bt1)
    private void bt1(View v) {
        DialogUtil.showCustomDialog(getContext(), true, "自定义对话框", "提示信息",
                "取消", null, "确定", null);

    }

    @Event(R.id.bt2)
    private void bt2(View v) {
        String[] items = {"男", "女"};
        DialogUtil.showActionSheetDialog(getContext(), "选择性别", items,
                new ActionSheetDialog.OnSheetItemClickListener() {

                    @Override
                    public void onClick(int which) {
                        ShowToast.showToast(getContext(), "哈哈");
                    }
                });

    }

    @Event(R.id.bt3)
    private void bt3(View v) {
        TipUtil.showTip(getContext(), v, "提示工具类", "点击我测试提示工具类");
    }

    @Event(R.id.bt7)
    private void bt7(View v) {
        TipUtil.showToolTipView(getContext(), v, Gravity.END, "aaa",
                ContextCompat.getColor(getActivity(), R.color.blue));
    }

    @Event(R.id.bt4)
    private void bt4(View v) {
        DialogUtil.showSVProgress(getContext(), "HUD风格提示框",
                DialogUtil.SVProgressHUDStyle.INFOWITHMSG);

    }

    @Event(R.id.bt5)
    private void bt5(View v) {
        ShowToast.showToast(getContext(), "我是toast");

    }

    @Event(R.id.bt6)
    private void bt6(View v) {
        ShowToast.showToastInCenter(getContext(), "我是中间显示的toast");
    }

    @Event(R.id.bt8)
    private void setBt8(View v) {
        startActivity(new Intent(getContext(), TestSwipeBackActivity.class));

    }

    //初始化网络图片缓存库
    @Override
    public void onItemClick(int position) {
        ShowToast.showToast(getContext(), "点击了第" + position + "个" + position + "了");
    }

    private void initImageLoader() {
        //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.drawable.ic_svstatus_loading)
                .cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getContext()).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

}