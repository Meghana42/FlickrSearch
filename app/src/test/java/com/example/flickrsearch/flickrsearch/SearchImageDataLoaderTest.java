package com.example.flickrsearch.flickrsearch;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Pair;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.ResponseDelivery;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Meghana Mokashi
 * Copyright (c) 2018. All rights reserved.
 */
@RunWith(MockitoJUnitRunner.class)
public class SearchImageDataLoaderTest {
    @Mock
    private Application mContext;

    private boolean mIsDataSetChanged = false;
    private SearchImageDataLoader mSearchImageDataLoader;

    @Test
    public void test_dataLoader() {
        Provider.set(new MockProvider());
        mSearchImageDataLoader = new SearchImageDataLoader(mContext, mObserver);
        assertTrue(mSearchImageDataLoader.mObservers.size() == 1);
    }

    @Test
    public void test_clearLoader() {
        Provider.set(new MockProvider());
        assertFalse(mIsDataSetChanged);
        mSearchImageDataLoader = new SearchImageDataLoader(mContext, mObserver);
        mSearchImageDataLoader.mImageLoaderList.add(new Pair<String, ImageLoader>("test", null));
        mSearchImageDataLoader.clearLoader();
        assertTrue(mSearchImageDataLoader.mImageLoaderList.size() == 0);
        assertTrue(mIsDataSetChanged);
    }

    private Observer mObserver = new Observer() {
        @Override
        public void notifyDataSetChanged(@NonNull String queryString) {
            mIsDataSetChanged = true;
        }
    };

    public class MockProvider extends Provider {
        private SearchImageDataLoader mSearchDataLoader;
        private SearchImageRepository mSearchImageRepository;
        private RequestQueue mRequestQueue;

        @Override
        SearchImageDataLoader getSearchImageDataLoader(@NonNull Context context, @NonNull Observer observer) {
            if (mSearchDataLoader == null) {
                mSearchDataLoader = Mockito.mock(SearchImageDataLoader.class);
            }
            return mSearchDataLoader;
        }

        @Override
        SearchImageRepository getSearchImageRepository(@NonNull Context context, @NonNull Observer observer) {
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
