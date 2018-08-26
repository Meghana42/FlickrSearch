package com.example.flickrsearch.flickrsearch;

/**
 * Created by Meghana Mokashi
 * Copyright (c) 2018. All rights reserved.
 */

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

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
}
