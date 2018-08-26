package com.example.flickrsearch.flickrsearch;
/**
 * Created by Meghana Mokashi
 * Copyright (c) 2018. All rights reserved.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * The class is responsible to load data using REST API, based on the query string
 * On change of data, notifies the observers {@link ImageListListener#onListUpdated(String)}
 */
public class SearchImageDataLoader {
    private static final String TAG = "SearchImageDataLoader";
    private String mQueryParam;
    private RequestQueue mRequestQueue;
    private Context mContext;
    private List <ImageDataHolder> mImageDataHolders;
    @Nullable
    private ImageListListener mListener;

    SearchImageDataLoader(@NonNull Context context) {
        mContext = context;
        mRequestQueue = Provider.get().getRequestQueue(context);
        mImageDataHolders = new ArrayList<>();
    }

    void setListener(@NonNull ImageListListener imageListListener) {
        mListener = imageListListener;
    }

    private String constructQueryUrl(@NonNull final String queryParam) {
        return String.format(mContext.getResources().getString(R.string.query_url),
                mContext.getResources().getString(R.string.api_key), queryParam);
    }

    void searchImages(@NonNull final String queryParam) {
        mQueryParam = queryParam;
        final String url = constructQueryUrl(queryParam);

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        final JSONArray results;
                        try {
                            results = response.getJSONObject("photos").getJSONArray("photo");

                            for (int i = 0; i < results.length(); i++) {
                                final JSONObject jsonObject = results.getJSONObject(i);
                                final ImageDataHolder imageDataHolder = new ImageDataHolder(jsonObject);
                                mImageDataHolders.add(imageDataHolder);
                            }

                            if (mListener != null) {
                                mListener.onListUpdated(queryParam);
                            }
                        } catch (JSONException e) {
                            Log.d(TAG, "JSONException");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(request);
    }

    void clearLoader() {
        mImageDataHolders.clear();
        mRequestQueue.cancelAll(mQueryParam);
    }

    @NonNull
    List<ImageDataHolder> getImageDataHolders() {
        return mImageDataHolders;
    }
}
