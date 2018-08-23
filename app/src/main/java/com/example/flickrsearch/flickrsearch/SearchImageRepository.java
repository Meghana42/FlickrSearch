package com.example.flickrsearch.flickrsearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Pair;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

/**
 * Created by Meghana Mokashi
 * Copyright (c) 2018. All rights reserved.
 */
public class SearchImageRepository {

    private SearchImageDataLoader mSearchImageDataLoader;

    SearchImageRepository(@NonNull Context context, @NonNull Observer observer) {
        mSearchImageDataLoader = Provider.get().getSearchImageDataLoader(context, observer);
    }

    void searchImages(@NonNull String queryString) {
        mSearchImageDataLoader.searchImages(queryString);
    }

    void clearLoader() {
        mSearchImageDataLoader.clearLoader();
    }

    public List<Pair<String, ImageLoader>> getImageDataList() {
        return mSearchImageDataLoader.getImageLoaderList();
    }
}
