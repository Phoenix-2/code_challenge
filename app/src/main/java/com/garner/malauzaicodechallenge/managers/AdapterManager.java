package com.garner.malauzaicodechallenge.managers;

import android.support.v4.view.ViewPager;

import com.garner.malauzaicodechallenge.adapters.CustomFragmentStatePagerAdapter;

public class AdapterManager {
    private static AdapterManager instance = null;

    private CustomFragmentStatePagerAdapter customFragmentStatePagerAdapter = null;
    private ViewPager viewPager = null;

    public static AdapterManager getInstance() {
        if(instance == null) {
            instance = new AdapterManager();
        }

        return instance;
    }

    private AdapterManager() { }

    public void setCustomFragmentStatePagerAdapter(CustomFragmentStatePagerAdapter adapter) {
        this.customFragmentStatePagerAdapter = adapter;
    }

    public CustomFragmentStatePagerAdapter getCustomFragmentStatePagerAdapter() {
        return customFragmentStatePagerAdapter;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {this.viewPager = viewPager;}
}
