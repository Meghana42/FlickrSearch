package com.example.flickrsearch.flickrsearch;

/**
 * Created by Meghana Mokashi
 * Copyright (c) 2018. All rights reserved.
 */
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.Display;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class SearchImageDataLoaderTest {
    @Mock
    private Application mContext;
    private boolean mIsDataSetChanged = false;
    private SearchImageDataLoader mSearchImageDataLoader;
    private final JSONObject mResult = new JSONObject();

    @Before
    public void setUp() {
        try {
            mResult.put("farm", "farm");
            mResult.put("server", "server");
            mResult.put("id", "id");
            mResult.put("secret", "secret");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test data loader is initialized and observers are attached
     */
    @Test
    public void test_dataLoader() throws InterruptedException {
        //-------Given-------------------

        Provider.set(new MockProvider());

        //-------When-------------------

        mSearchImageDataLoader = new ImageDataLoader(mContext);
        assertTrue(mSearchImageDataLoader.getImageDataHolders().isEmpty());
        mSearchImageDataLoader.setListener(mImageListListener);
        assertFalse(mIsDataSetChanged);

        mSearchImageDataLoader.searchImages("test");

        Thread.sleep(15000);

        //-------Then-------------------

        assertFalse(mSearchImageDataLoader.getImageDataHolders().isEmpty());
    }

    /**
     * Test after clearing data loader - observers are notified
     */
    @Test
    public void test_clearLoader() {
        //-------Given-------------------

        Provider.set(new MockProvider());
        assertFalse(mIsDataSetChanged);
        mSearchImageDataLoader = new SearchImageDataLoader(mContext);
        mSearchImageDataLoader.setListener(mImageListListener);

        //-------When-------------------

        mSearchImageDataLoader.getImageDataHolders().add(new ImageDataHolder(mResult));
        mSearchImageDataLoader.clearLoader();

        //-------Then-------------------

        assertTrue(mSearchImageDataLoader.getImageDataHolders().size() == 0);
    }

    private ImageListListener mImageListListener = new ImageListListener() {
        @Override
        public void onListUpdated(@NonNull String queryString) {
            mIsDataSetChanged = true;
        }
    };

    public class MockProvider extends Provider {
        private SearchImageRepository mSearchImageRepository;
        private RequestQueue mRequestQueue;

        @Override
        SearchImageRepository getSearchImageRepository(@NonNull Context context) {
            if (mSearchImageRepository == null) {
                mSearchImageRepository = Mockito.mock(SearchImageRepository.class);
            }
            return mSearchImageRepository;
        }

        @Override
        RequestQueue getRequestQueue(@NonNull Context context) {
            if (mRequestQueue == null) {
                mRequestQueue = Mockito.mock(RequestQueue.class);
            }
            return mRequestQueue;
        }
    }

    private class ImageDataLoader extends SearchImageDataLoader {
        ImageDataLoader(@NonNull Context context) {
            super(context);
        }

        @Override
        String constructQueryUrl(@NonNull String queryParam) {
            return "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736& format=json&nojsoncallback=1&safe_search=1&text=test";
        }
    }
}
