package com.garner.malauzaicodechallenge.configurations;

public class Configurations {
    // "https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=e683a38d10653bdd0466130cd61e2420&per_page=1&page=1";
    public static final FlickrResultStreamMethod RESULTS_STREAM_METHOD = FlickrResultStreamMethod.JSON;

    public static final String API_KEY_FLICKR = "e683a38d10653bdd0466130cd61e2420";
    public static final String METHOD_GET_RECENT_PHOTOS = "flickr.photos.getRecent";
    public static final String PER_PAGE = "1";
    public static final String PAGE = "1";

    public static final String URL_FLICKR_BASE = "https://api.flickr.com/services/rest/";
    public static final String PARAMETER_METHOD = "method=" + METHOD_GET_RECENT_PHOTOS;
    public static final String PARAMETER_API_KEY = "api_key=" + API_KEY_FLICKR;
    public static final String PARAMETER_PER_PAGE = "per_page=" + PER_PAGE;
    public static final String PARAMETER_PAGE = "page=" + PAGE;

    public static final String URL_FLICKR_FULL = URL_FLICKR_BASE + "?"
                                               + PARAMETER_METHOD + "&"
                                               + PARAMETER_API_KEY + "&"
                                               + PARAMETER_PER_PAGE + "&"
                                               + PARAMETER_PAGE;
}
