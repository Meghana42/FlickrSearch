package com.example.flickrsearch.flickrsearch;

/**
 * Created by Meghana Mokashi
 * Copyright (c) 2018. All rights reserved.
 */

import android.support.annotation.NonNull;

/**
 * The UI controller {@link MainActivity} implements the interface,
 * {@link MainActivity#onListUpdated(String)} updates the UI, based on the data source change
 * {@link SearchImageDataLoader} notifies the listener about data source changes
 */
public interface ImageListListener {
    void onListUpdated(@NonNull String queryString);
}
