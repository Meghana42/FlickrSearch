package com.example.flickrsearch.flickrsearch;
/**
 * Created by Meghana Mokashi
 * Copyright (c) 2018. All rights reserved.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.LruCache;
import android.util.Pair;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * The class is responsible to load data using REST API, based on the query string
 * On change of data, notifies the observers {@link Observer#notifyDataSetChanged(String)}
 */
public class SearchImageDataLoader {

    private static final String QUERY_URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736& format=json&nojsoncallback=1&safe_search=1&text=";

    private String mQueryParam;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    @VisibleForTesting
    List<Pair<String, ImageLoader>> mImageLoaderList;
    @VisibleForTesting
    List<Observer> mObservers = new ArrayList<>();

    SearchImageDataLoader(@NonNull Context context, @NonNull Observer observer) {
        mRequestQueue = Provider.get().getRequestQueue(context);

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
        mImageLoaderList = new ArrayList<>();
        mObservers.add(observer);
    }

    private void notifyAllObservers(){
        for (Observer observer : mObservers) {
            observer.notifyDataSetChanged(mQueryParam);
        }
    }

    void searchImages(@NonNull String queryParam) {
        mQueryParam = queryParam;
        final String url = QUERY_URL + mQueryParam;

        final StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final JSONObject responseObject = new JSONObject(response);
                    System.out.println(responseObject);
                    final JSONArray results = responseObject.getJSONObject("photos").getJSONArray("photo");

                    populateImages(results);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(request);
    }

    //http://farm{farm}.static.flickr.com/{server}/{id}_{secret}.jpg
    private void populateImages(@NonNull JSONArray results) throws JSONException {
        for (int i = 0; i < results.length(); i++) {
            final JSONObject jsonObject = results.getJSONObject(i);
            final String imgUrl = "http://farm"+ jsonObject.getString("farm") + ".static.flickr.com/"
                    + jsonObject.getString("server") + "/" + jsonObject.getString("id") + "_" + jsonObject.getString("secret") + ".jpg";

            sendImageRequest(imgUrl);
        }
    }

    private void sendImageRequest(@NonNull final String imageUrl) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, imageUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Pair<String, ImageLoader> pair = new Pair<>(imageUrl, mImageLoader);
                        mImageLoaderList.add(pair);
                        notifyAllObservers();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });
        stringRequest.setTag(mQueryParam);
        mRequestQueue.add(stringRequest);
    }

    void clearLoader() {
        mImageLoaderList.clear();
        mRequestQueue.cancelAll(mQueryParam);
        notifyAllObservers();
    }

    List<Pair<String, ImageLoader>> getImageLoaderList() {
        return mImageLoaderList;
    }
}
