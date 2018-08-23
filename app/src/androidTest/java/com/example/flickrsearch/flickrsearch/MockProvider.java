package com.example.flickrsearch.flickrsearch;

import android.content.Context;
import android.support.annotation.NonNull;

import org.mockito.Mockito;

/**
 * Created by Meghana Mokashi
 * Copyright (c) 2018. All rights reserved.
 */
public class MockProvider extends Provider {
    private SearchImageDataLoader mSearchDataLoader;
    private SearchImageRepository mSearchImageRepository;

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
}
