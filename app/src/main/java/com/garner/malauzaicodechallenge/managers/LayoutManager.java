package com.garner.malauzaicodechallenge.managers;

import android.support.v4.widget.SwipeRefreshLayout;

public class LayoutManager {
    private static LayoutManager instance = null;

    private SwipeRefreshLayout swipeRefreshLayout = null;

    public static LayoutManager getInstance() {
        if(instance == null) {
            instance = new LayoutManager();
        }

        return instance;
    }

    private LayoutManager() { }

    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return this.swipeRefreshLayout;
    }
}
