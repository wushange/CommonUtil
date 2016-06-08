5# 快速开发常用 工具控件封装
封装了自己平时快速开发的常用工具类和轻量级框架
源码Demo下载:https://github.com/wushge11/CommonUtil
eclipse和studio的点击这里：http://download.csdn.net/detail/wushge11/9514499
 
使用方法：   compile 'com.wushange:commutilSdk:2.0.229@aar'


欢迎大家加入我的分享交流群 
希望和大家一起成长进步。
QQ群：74406192
这个就是我自己平时项目中经常用到的一些控件和工具，不是原创，只是我觉得方便，优化一下封装起来。每次做项目的时候直接导入就可以了。本文主要介绍几个常用的方法，具体还请下载源码看
![这里写图片描述](http://img.blog.csdn.net/20160531174549747)

更新日志：
	2016-6-6号跟新， 缩减库大小，删除其他第三方类库，fastjson等等，开发时请开发者自行在自己的项目中加入，避免发生版本冲突。添加loading动画跳跃效果
	2016-6-2号更新在Base中添加显示loading 和消失dismiss
	在继承了base类中可直接调用方法 showloading和dissmiss
	效果图
	![这里写图片描述](http://img.blog.csdn.net/20160602174928012)
	2016-5-27号BaseFragment支持懒加载，在继承BaseFragemnt的fragmenr中实现 lazyInitBusiness方法 ，这个方法会在用户可见状态时才会加载。适合viewpage+fragment实现tab方式的懒加载。主要是用fragment的setUserVisibleHint 方法来实现懒加载效果。
	2016-5-13号添加banner库
智能banner 手指放上就自动暂停循环播放松开继续
支持加载本地图片和网络图片集成简单
使用方法请[点击这里](https://github.com/saiwu-bigkoo/Android-ConvenientBanner%20%E4%BD%BF%E7%94%A8%E6%96%B9%E6%B3%95)

功能介绍：


----------


1，自定义弹出对话框
	使用方法：

```
  DialogUtil.showCustomDialog(getContext(), true, "自定义对话框", "提示信息",
                "取消", null, "确定", null);
```


----------


2，弹出ActionSheetDialog
	使用方法:
```
  String[] items = {"男", "女"};
        DialogUtil.showActionSheetDialog(getContext(), "选择性别", items,
                new ActionSheetDialog.OnSheetItemClickListener() {

                    @Override
                    public void onClick(int which) {
                        ShowToast.showToast(getContext(), "哈哈");
                    }
                });
```
----------


3,用户引导提示
	使用方法：
	

```
  TipUtil.showTip(getContext(), v, "提示工具类", "点击我测试提示工具类");
```


----------


4，HUD风格的提示框
	使用方法：
	   SVProgressHUDStyle 说明
         * ERRORWITHMSG 错误信息提示
         * INFOWITHMSG 单纯信息提示
         * SUCCESSWHITMSG 完成的信息提示
         * SHOW 直接显示带进度条的。
        
```
  DialogUtil.showSVProgress(getContext(), "HUD风格提示框",
                DialogUtil.SVProgressHUDStyle.INFOWITHMSG);
```


----------


5，Toast工具类，toast显示不累加，不重叠。可显示屏幕中间的toast
	使用方法：
	

```
  ShowToast.showToast(getContext(), "我是toast");
   ShowToast.showToastInCenter(getContext(), "我是中间显示的toast");
```

----------


6，日期和时间选择工具类，调用系统选择器：
	使用方法：
	

```
v：文本控件
1：日期
2：时间
 ToolDateTimePicker dateTimePicker = new ToolDateTimePicker(getContext(),
                (TextView) v, 1);
        dateTimePicker.show();
```


----------


7，Acahe存储
	使用方法：
	http://blog.csdn.net/zhoubin1992/article/details/46379055
	
----------

8，手势返回的Activity和Fragmetn
	使用方法：
	Activity 直接继承  extends BaseSwipeBackActivity即可 默认左滑返回 如需定制手势 
	EDGE_ALL,EDGE_LEFT,EDGE_BOTTOM
	Fragment 或者 是一些特殊的activity或者没有集成base类的可以手动实现 首先 ` implements SwipeBackActivityBase`
	然后初始化 
	`mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();`
最后在实现这几个方法就ok
```
   private SwipeBackActivityHelper mHelper;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
```


----------


9，常用底部导航栏实现（viewpage+fragment）
	实现方法：
	FragmentMainActivity实现：
	

```
package com.connxun.demoprogrect;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.connxun.demoprogrect.module.first.FirstFragment;
import com.connxun.demoprogrect.module.three.ThreeFragment;
import com.connxun.demoprogrect.module.two.TwoFragment;
import com.wushange.commsdk.base.BaseFragmentV4;
import com.wushange.commsdk.viewpageindicator.IconPagerAdapter;
import com.wushange.commsdk.viewpageindicator.IconTabPageIndicator;

import java.util.ArrayList;
import java.util.List;

public class FragmentMainActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private IconTabPageIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initViews();
    }

    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mIndicator = (IconTabPageIndicator) findViewById(R.id.indicator);
        List<BaseFragmentV4> fragments = initFragments();
        FragmentAdapter adapter = new FragmentAdapter(fragments, getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mIndicator.setViewPager(mViewPager);
    }

    private List<BaseFragmentV4> initFragments() {
        List<BaseFragmentV4> fragments = new ArrayList<BaseFragmentV4>();

        FirstFragment userFragment = new FirstFragment();
        userFragment.initIconWithText("模块1", R.drawable.tab_user_selector);
        TwoFragment rFragment = new TwoFragment();
        rFragment.initIconWithText("模块2", R.drawable.tab_user_selector);
        ThreeFragment brFragment = new ThreeFragment();
        brFragment.initIconWithText("模块3", R.drawable.tab_user_selector);
        fragments.add(userFragment);
        fragments.add(rFragment);
        fragments.add(brFragment);

        return fragments;
    }

    class FragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
        private List<BaseFragmentV4> mFragments;

        public FragmentAdapter(List<BaseFragmentV4> fragments, FragmentManager fm) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int i) {
            return mFragments.get(i);
        }

        @Override
        public int getIconResId(int index) {
            return mFragments.get(index).getIconId();
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragments.get(position).getTitle();
        }
    }

}
```
布局文件：

```
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".FragmentMainActivity">

    <android.support.v4.view.ViewPager
        android:id="@+id/view_pager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_above="@+id/divider" />

    <View
        android:id="@+id/divider"
        android:layout_width="match_parent"
        android:layout_height="1px"
        android:layout_above="@+id/indicator"
        android:background="#ababab" />

    <com.wushange.commsdk.viewpageindicator.IconTabPageIndicator
        android:id="@+id/indicator"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true" />


</RelativeLayout>

```


----------


10 ,中间凸起的底部导航栏
	实现方法：
MainActivity实现

```
public class MainActivity extends AppCompatActivity {

    private static final String TAG_PAGE_HOME = "消息";
    private static final String TAG_PAGE_CITY = "客户";
    private static final String TAG_PAGE_PUBLISH = " ";
    private static final String TAG_PAGE_MESSAGE = "项目";
    private static final String TAG_PAGE_PERSON = "我";


    private MainNavigateTabBar mNavigateTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigateTabBar = (MainNavigateTabBar) findViewById(R.id.mainTabBar);

        mNavigateTabBar.onRestoreInstanceState(savedInstanceState);

        mNavigateTabBar.addTab(MessageFragment.class, new MainNavigateTabBar.TabParam(R.mipmap.comui_tab_home, R.mipmap.comui_tab_home_selected, TAG_PAGE_HOME));
        mNavigateTabBar.addTab(CustomerFragment.class, new MainNavigateTabBar.TabParam(R.mipmap.comui_tab_city, R.mipmap.comui_tab_city_selected, TAG_PAGE_CITY));
        mNavigateTabBar.addTab(null, new MainNavigateTabBar.TabParam(0, 0, TAG_PAGE_PUBLISH));
        mNavigateTabBar.addTab(ProjectFragment.class, new MainNavigateTabBar.TabParam(R.mipmap.comui_tab_message, R.mipmap.comui_tab_message_selected, TAG_PAGE_MESSAGE));
        mNavigateTabBar.addTab(MeFragment.class, new MainNavigateTabBar.TabParam(R.mipmap.comui_tab_person, R.mipmap.comui_tab_person_selected, TAG_PAGE_PERSON));
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mNavigateTabBar.onSaveInstanceState(outState);
    }


    public void onClickPublish(View v) {
        Toast.makeText(this, "发布", Toast.LENGTH_LONG).show();
    }
}
```

MainActivity布局文件
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">


    <FrameLayout
        android:id="@+id/main_container"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_above="@+id/mainTabBar" />

    <com.wushange.commsdk.centertab.MainNavigateTabBar
        android:id="@+id/mainTabBar"
        android:layout_width="match_parent"
        android:layout_height="50.0dip"
        android:layout_alignParentBottom="true"
        android:background="@android:color/white"
        app:containerId="@+id/main_container"
        app:navigateTabSelectedTextColor="@color/comui_tab_text_color"
        app:navigateTabTextColor="@color/comui_tab_text_color" />


    <ImageView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_above="@+id/mainTabBar"
        android:background="@mipmap/comui_bar_top_shadow" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="76.0dip"
        android:layout_alignParentBottom="true"
        android:background="@android:color/transparent"
        android:gravity="center|top"
        android:orientation="vertical">

        <ImageView
            android:id="@+id/tab_post_icon"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:onClick="onClickPublish"
            android:src="@mipmap/comui_tab_post" />
    </LinearLayout>
</RelativeLayout>

```


