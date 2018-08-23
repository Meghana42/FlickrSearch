package com.example.flickrsearch.flickrsearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

/**
 * Created by Meghana Mokashi
 * Copyright (c) 2018. All rights reserved.
 */
public class Provider {
    private SearchImageDataLoader mDataLoader;

    private static Provider sInstance = new Provider();

    public static Provider get() {
        return sInstance;
    }

    @VisibleForTesting
    public static void set(@NonNull Provider provider) {
        sInstance = provider;
    }

    SearchImageRepository getSearchImageRepository(@NonNull Context context, @NonNull Observer observer) {
        return new SearchImageRepository(context, observer);
    }

    SearchImageDataLoader getSearchImageDataLoader(@NonNull Context context, @NonNull Observer observer) {
        if (mDataLoader == null) {
            return new SearchImageDataLoader(context, observer);
        }
        return mDataLoader;
    }
}
