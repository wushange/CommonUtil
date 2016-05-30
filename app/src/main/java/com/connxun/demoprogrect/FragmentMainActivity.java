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