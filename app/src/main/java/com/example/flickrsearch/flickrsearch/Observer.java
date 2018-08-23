package com.example.flickrsearch.flickrsearch;

/**
 * Created by Meghana Mokashi
 * Copyright (c) 2018. All rights reserved.
 */
import android.support.annotation.NonNull;
import android.util.Pair;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

/**
 * The UI controller {@link MainActivity} implements the interface,
 * {@link MainActivity#notifyDataSetChanged(String)} updates the UI, based on the data source change
 * {@link SearchImageDataLoader} notifies the observers about data source changes
 */
public interface Observer {
    void notifyDataSetChanged(@NonNull String queryString);
}
