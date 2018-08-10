package com.garner.malauzaicodechallenge.activities;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import com.garner.malauzaicodechallenge.R;
import com.garner.malauzaicodechallenge.adapters.CustomFragmentStatePagerAdapter;
import com.garner.malauzaicodechallenge.managers.AdapterManager;
import com.garner.malauzaicodechallenge.managers.LayoutManager;
import com.garner.malauzaicodechallenge.tasks.FetchImageFromFlickrAsyncTask;
import com.viewpagerindicator.LinePageIndicator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the SwipeRefreshLayout and set the OnRefreshListener
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Create an instance of the FetchImageFromFlickr AsyncTask
                FetchImageFromFlickrAsyncTask task = new FetchImageFromFlickrAsyncTask();
                // Execute the task
                task.execute();
            }
        });

        // Instantiate the CustomFragmetStatePagerAdapter
        CustomFragmentStatePagerAdapter customFragmentStatePagerAdapter = new CustomFragmentStatePagerAdapter(getSupportFragmentManager());

        // Get the ViewPager and set the instance of the CustomFragmentStatePagerAdapter as its' adapter.
        ViewPager viewPager = (ViewPager)findViewById(R.id.view_pager);
        viewPager.setAdapter(customFragmentStatePagerAdapter);

        // Attach the Line Page Indicator to the View Pager
        LinePageIndicator linePageIndicator = (LinePageIndicator) findViewById(R.id.indicator);
        linePageIndicator.setViewPager(viewPager);

        // Add the SwipeRefreshLayout to the singletom layout manager so that it can easily be accessed
        // from any task that would need to act on it.
        LayoutManager.getInstance().setSwipeRefreshLayout(swipeRefreshLayout);

        // Add the CuatomFragmentStatePagerAdapter to the singleton adapter manager so that it can easily be accessed
        // from any task that would need to act on it.
        AdapterManager.getInstance().setCustomFragmentStatePagerAdapter(customFragmentStatePagerAdapter);

        // Add the ViewPager to the singleton adapter manager so that it can be easily accessed from any task that
        // would need to act on it.
        AdapterManager.getInstance().setViewPager(viewPager);
    }
}
