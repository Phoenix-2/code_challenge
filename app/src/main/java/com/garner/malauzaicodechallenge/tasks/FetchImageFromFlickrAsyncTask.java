package com.garner.malauzaicodechallenge.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.garner.malauzaicodechallenge.adapters.CustomFragmentStatePagerAdapter;
import com.garner.malauzaicodechallenge.fragments.PhotoFragment;
import com.garner.malauzaicodechallenge.models.Photo;
import com.garner.malauzaicodechallenge.configurations.Configurations;
import com.garner.malauzaicodechallenge.configurations.FlickrResultStreamMethod;
import com.garner.malauzaicodechallenge.managers.AdapterManager;
import com.garner.malauzaicodechallenge.managers.LayoutManager;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchImageFromFlickrAsyncTask extends AsyncTask<String, Photo, ArrayList<Photo>> {

    public FetchImageFromFlickrAsyncTask() {

    }

    @Override
    protected ArrayList<Photo> doInBackground(String... strings) {
        ArrayList<Photo> photos = new ArrayList<Photo>();

        HttpURLConnection httpURLConnection = null;

        try {
            if(Configurations.RESULTS_STREAM_METHOD == FlickrResultStreamMethod.XML) {
                URL url = new URL(Configurations.URL_FLICKR_FULL);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                photos.addAll(this.buildListFromXML(httpURLConnection));
            } else if(Configurations.RESULTS_STREAM_METHOD == FlickrResultStreamMethod.JSON) {
                URL url = new URL(Configurations.URL_FLICKR_FULL + "&format=json");
                httpURLConnection = (HttpURLConnection) url.openConnection(); //Open the connection

                photos.addAll(this.buildListFromJSON(httpURLConnection));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) { httpURLConnection.disconnect(); }
        }

        return photos;
    }

    private Bitmap pullImageAsBitmapFromUrl(URL photoUrl) {
        Bitmap bitmap = null;

        try {
            InputStream inputStream = photoUrl.openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }

        return bitmap;
    }

    // Parse all results from JSNO stream into a list of Photo objects
    private ArrayList<Photo> buildListFromJSON(HttpURLConnection httpURLConnection) {
        ArrayList<Photo> photos = new ArrayList<Photo>();
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputStr);
            }

            String jsonString = stringBuilder.toString();
            if(jsonString.startsWith("jsonFlickrApi")) {
                // Remove the "jsonFlickrApi" tag from the begining of the string
                jsonString = jsonString.substring(13);
                // Remove the enclosing parenthesis from the JSON object
                jsonString = jsonString.substring(1, (jsonString.length() - 1));
                // Remove the response status
            }

            JSONObject jsonRoot = new JSONObject(jsonString);
            JSONObject jsonPhotoResult = jsonRoot.getJSONObject("photos");
            JSONArray jsonPhotos = jsonPhotoResult.getJSONArray("photo");

            Photo photo = null;
            for(int index = 0; index < jsonPhotos.length(); index++) {
                try {
                    photo = new Photo(jsonPhotos.getJSONObject(index));
                    photo.setBitmap(this.pullImageAsBitmapFromUrl(photo.getAsUrl()));

                    photos.add(photo);
                    Log.i("Success", "Photo successfully added to photos list");
                } catch(JSONException e) {
                    Log.e("ParsingError", "There was an error when trying to create a Photo from the JSONObject");
                    Log.e("JSONException", e.getMessage());
                }
            }
        } catch(IOException e) {
            Log.e("IOException", e.getMessage());
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        } finally {
            if(bufferedReader != null) {
                try { bufferedReader.close(); }
                catch(IOException e) {}
            }
        }

        return photos;
    }

    // Parse all results from XML stream into a list of Photo objects
    private ArrayList<Photo> buildListFromXML(HttpURLConnection httpURLConnection) {
        ArrayList<Photo> photos = new ArrayList<Photo>();

        try {
            SAXBuilder builder = new SAXBuilder();
            Document document = (Document)builder.build(httpURLConnection.getInputStream());
            Element responseElement = document.getRootElement();
            Element photosElement = responseElement.getChild("photos");
            List<Element> photoElements = photosElement.getChildren("photo");

            Photo photo = null;
            for(Element photoElement : photoElements) {
                photo = new Photo(photoElement);
                photo.setBitmap(this.pullImageAsBitmapFromUrl(photo.getAsUrl()));

                photos.add(photo);
                Log.i("Success", "Photo successfully added to photos list");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }

        return photos;
    }

    @Override
    protected void onProgressUpdate(Photo... photos) {

    }

    @Override
    protected void onPostExecute(ArrayList<Photo> photos) {
        CustomFragmentStatePagerAdapter adapter = AdapterManager.getInstance().getCustomFragmentStatePagerAdapter();
        ViewPager viewPager = AdapterManager.getInstance().getViewPager();

        // Add all photos to the view pager and set the current item to be the last one added
        for(Photo photo : photos) {
            PhotoFragment photoFragment = new PhotoFragment();
            photoFragment.setTitle(photo.getTitle());
            photoFragment.setBitmap(photo.getBitmap());
            adapter.add(adapter.getCount(), photoFragment);
        }

        viewPager.setCurrentItem(adapter.getCount());

        LayoutManager.getInstance().getSwipeRefreshLayout().setRefreshing(false);
    }
}
