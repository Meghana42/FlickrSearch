package com.example.flickrsearch.flickrsearch;

/**
 * Created by Meghana Mokashi
 * Copyright (c) 2018. All rights reserved.
 */
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Provider {
    private static Provider sInstance = new Provider();

    public static Provider get() {
        return sInstance;
    }

    @VisibleForTesting
    public static void set(@NonNull Provider provider) {
        sInstance = provider;
    }

    SearchImageRepository getSearchImageRepository(@NonNull Context context) {
        return new SearchImageRepository(context);
    }

    SearchImageDataLoader getSearchImageDataLoader(@NonNull Context context) {
        return new SearchImageDataLoader(context);
    }

    RequestQueue getRequestQueue(@NonNull Context context) {
        return Volley.newRequestQueue(context);
    }
}
