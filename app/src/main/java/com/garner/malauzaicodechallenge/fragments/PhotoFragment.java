package com.garner.malauzaicodechallenge.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.garner.malauzaicodechallenge.R;

public class PhotoFragment extends Fragment {
    private static final String ARG_SCOUT_KEY = "scout_key";

    private String title = null;
    private Bitmap bitmap = null;

    private TextView textView = null;
    private ImageView imageView = null;

    public static PhotoFragment newInstance(int key, String title) {//String title, byte[] imageBytes) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SCOUT_KEY, key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photo, container, false);

        this.textView = (TextView)rootView.findViewById(R.id.section_title);
        this.textView.setText(this.title);

        this.imageView = (ImageView)rootView.findViewById(R.id.section_image);
        this.imageView.setImageBitmap(this.bitmap);

        return rootView;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() { return this.title; }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}

