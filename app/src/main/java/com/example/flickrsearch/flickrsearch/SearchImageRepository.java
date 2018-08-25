package com.example.flickrsearch.flickrsearch;

/**
 * Created by Meghana Mokashi
 * Copyright (c) 2018. All rights reserved.
 */
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * SearchImageRepository acts as a mediator between the UI components and the data layer
 */
public class SearchImageRepository {

    private SearchImageDataLoader mSearchImageDataLoader;

    SearchImageRepository(@NonNull Context context) {
        mSearchImageDataLoader = Provider.get().getSearchImageDataLoader(context);
    }

    void searchImages(@NonNull String queryString) {
        mSearchImageDataLoader.searchImages(queryString);
    }

    void clearLoader() {
        mSearchImageDataLoader.clearLoader();
    }

    public List<ImageDataHolder> getImageDataHolders() {
        return mSearchImageDataLoader.getImageDataHolders();
    }

    void setListener(@NonNull ImageListListener listener) {
        mSearchImageDataLoader.setListener(listener);
    }
}
