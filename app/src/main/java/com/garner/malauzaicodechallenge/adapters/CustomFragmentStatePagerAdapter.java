package com.garner.malauzaicodechallenge.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.garner.malauzaicodechallenge.fragments.PhotoFragment;

import java.util.ArrayList;

public class CustomFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<PhotoFragment> fragmentList = new ArrayList<PhotoFragment>();

    public CustomFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public PhotoFragment getItem(int position) {
        return this.fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return this.fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = this.fragmentList.get(position).getTitle();
        return "Title: " + title;
    }

    public void add(int position, PhotoFragment fragment) {
        this.fragmentList.add(position, fragment);
        this.notifyDataSetChanged();
    }
}
