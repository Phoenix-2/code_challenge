package com.garner.malauzaicodechallenge.models;

import android.graphics.Bitmap;

import org.jdom2.Element;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class Photo {
    private String id = null;
    private String owner = null;
    private String secret = null;
    private String server = null;
    private int farm = -1;
    private String title = null;
    private int isPublic = -1;
    private int isFriend = -1;
    private int isFamily = -1;

    private Bitmap bitmap = null;

    public Photo(Element photoElement) {
        this.id = photoElement.getAttribute("id").getValue();
        this.owner = photoElement.getAttribute("owner").getValue();
        this.secret = photoElement.getAttribute("secret").getValue();
        this.server = photoElement.getAttribute("server").getValue();
        this.farm = Integer.parseInt(photoElement.getAttribute("farm").getValue());
        this.title = photoElement.getAttribute("title").getValue();
        this.isPublic = Integer.parseInt(photoElement.getAttribute("ispublic").getValue());
        this.isFriend = Integer.parseInt(photoElement.getAttribute("isfriend").getValue());
        this.isFamily = Integer.parseInt(photoElement.getAttribute("isfamily").getValue());
    }

    public Photo(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getString("id");
        this.owner = jsonObject.getString("owner");
        this.secret = jsonObject.getString("secret");
        this.server = jsonObject.getString("server");
        this.farm = jsonObject.getInt("farm");
        this.title = jsonObject.getString("title");
        this.isPublic = jsonObject.getInt("ispublic");
        this.isFriend = jsonObject.getInt("isfriend");
        this.isFamily = jsonObject.getInt("isfamily");
    }

    public URL getAsUrl() {
        URL url = null;

        try {
            String urlString = "https://farm" + this.farm + ".static.flickr.com/"+ this.server + "/" + this.id + "_" + this.secret + ".jpg";
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public String getTitle() { return this.title; }

    public void setBitmap(Bitmap bitmap) { this.bitmap = bitmap; }
    public Bitmap getBitmap() { return this.bitmap; }
}
