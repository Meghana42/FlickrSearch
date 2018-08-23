package com.example.flickrsearch.flickrsearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Pair;

import com.android.volley.toolbox.ImageLoader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Created by Meghana Mokashi
 * Copyright (c) 2018. All rights reserved.
 */
@RunWith(AndroidJUnit4.class)
public class SearchImageDataLoaderTest {

    private SearchImageDataLoader mSearchImageDataLoader;
    private Context mContext;

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getTargetContext();
        mSearchImageDataLoader = SearchImageDataLoader.getInstance(mContext, mObserver);
    }

    @Test
    public void test_searchImages() {
        mSearchImageDataLoader.searchImages("kite");
    }

    private Observer mObserver = new Observer() {
        @Override
        public void notifyDataSetChanged(@NonNull String queryString, @NonNull List<Pair<String, ImageLoader>> imageLoaderList) {

        }
    };

}
