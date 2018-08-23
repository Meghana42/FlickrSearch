package com.example.flickrsearch.flickrsearch;

import android.support.annotation.NonNull;
import android.util.Pair;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

/**
 * Created by Meghana Mokashi
 * Copyright (c) 2018. All rights reserved.
 */
public interface Observer {
    void notifyDataSetChanged(@NonNull String queryString);
}
