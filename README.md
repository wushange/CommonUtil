# CommonSdk
快速开发框架集成（极轻）
# 快速开发常用框架封装（极轻）
封装了自己平时快速开发的常用工具类和轻量级框架

源码Demo下载:https://github.com/wushge11/CommonSdk

eclipse和studio的点击这里：http://download.csdn.net/detail/wushge11/9514499

studio项目也可以在gradle中直接

    compile 'com.wushange:commutilSdk:1.0.13'

有仿ios的对话框，底部弹出框，
svprogress提示框
还有一些常用的util工具类 具体看源码就可以

本身已经集成了xutil3 ，fastjson，glide，imageloader,eventbus3.0,gson,nineoldandroids-2.4.0.jar
所以开发项目时就不需要添加这几个了 
如果需要更新直接更改module里的就可以
我是觉得这样比较简洁
使用前下载lirbary导入 项目引用这个


2016-5-9号更新 module中BaseActivity 和 BaseSwipeBackActivity 还有一些基类；
主要是用来实现 类似微信左滑关闭activity，带阴影效果。
使用方法 直接继承baseswipebackactivity
如果不习惯用基类的话
也可以直接继承swipebackactivity


2016-5-10号更新 IconTabPageIndicator 
项目需要四大天王的 3s集成 
icon的选中状态和颜色自己定义好

2016-5-13号添加banner库 
智能banner 手指放上就自动暂停循环播放松开继续 
支持加载本地图片和网络图片集成简单 
使用方法请点击这里 

IconTabPageIndicator 控件使用方法:底部四大天王

布局文件
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
MianActivity主要实现  
fragment直接集成Basefragment

```
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

![自定义弹出对话框] (http://img.blog.csdn.net/20160509131419714)
使用方法：

```
DialogUtil.showCustomDialog(MainActivity.this, true,"哈哈", "nihao ","a", null, "a", null);
```

![ActionSheetItemDialog](http://img.blog.csdn.net/20160509131534152)
使用方法：

```
String[] items = { "男", "女" };
		DialogUtil.showActionSheetDialog(context, "选择性别", items,
				new OnSheetItemClickListener() {

					@Override
					public void onClick(int which) {
						ShowToast.showToast(context, "哈哈");
					}
				});
```
![Tip提示信息](http://img.blog.csdn.net/20160509131608667)
使用方法：

```
TipUtil.showTip(context, v, "haha", "测试常用工具类");
```
![SVPROGRESS提示框](http://img.blog.csdn.net/20160509131731867)
使用方法：

```
DialogUtil.showSVProgress(context, "请输入标签，不超过10个字",
				SVProgressHUDStyle.INFOWITHMSG);
```
![SVPROGRESS提示框](http://img.blog.csdn.net/20160509131800477)
使用方法：

```
DialogUtil.showSVProgress(context, "请输入标签，不超过10个字",SVProgressHUDStyle.INFOWITHMSG);
```
