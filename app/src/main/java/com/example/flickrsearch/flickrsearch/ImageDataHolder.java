package com.example.flickrsearch.flickrsearch;

/**
 * Created by Meghana Mokashi
 * Copyright (c) 2018. All rights reserved.
 */
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ImageDataHolder holds the image data, constructs from the JSONObject
 */
public class ImageDataHolder {
    private String mFarm;
    private String mServer;
    private String mId;
    private String mSecret;

    ImageDataHolder(@NonNull final JSONObject result) {
        try {
            mFarm = result.getString("farm");
            mServer = result.getString("server");
            mId = result.getString("id");
            mSecret = result.getString("secret");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * The API constructs the URL using ImageDataHolder object
     * @return String in form of //http://farm{farm}.static.flickr.com/{server}/{id}_{secret}.jpg
     */
    @NonNull
    String getUrl() {
        return "http://farm"+ mFarm + ".static.flickr.com/"
                + mServer + "/" + mId + "_" + mSecret + ".jpg";
    }
}
