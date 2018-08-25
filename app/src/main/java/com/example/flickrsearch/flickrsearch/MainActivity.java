package com.example.flickrsearch.flickrsearch;

/**
 * Created by Meghana Mokashi
 * Copyright (c) 2018. All rights reserved.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

/**
 * MainActivity is the UI controller,
 * communicates with the {@link SearchImageRepository} to get the data source, based on the search query string
 */
public class MainActivity extends AppCompatActivity implements ImageListListener, SearchView.OnQueryTextListener {
    private static final int NUM_GRID_COLUMNS = 3;
    private SearchImageRepository mRepository;
    private ImageLoaderAdapter mAdapter;
    private SearchView mSearchView;
    private TextView mResultText;
    private LinearLayout mProgressBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = getApplicationContext();
        setContentView(R.layout.activity_main);

        mSearchView = findViewById(R.id.search_box);
        mSearchView.setOnQueryTextListener(this);

        mProgressBarLayout = findViewById(R.id.progress_bar_layout);
        mProgressBarLayout.setVisibility(View.GONE);

        mResultText = findViewById(R.id.result_text);
        mResultText.setVisibility(View.INVISIBLE);

        RecyclerView recyclerView = findViewById(R.id.image_holder);
        mAdapter = new ImageLoaderAdapter(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(context, NUM_GRID_COLUMNS));

        mRepository = Provider.get().getSearchImageRepository(context);
        mRepository.setListener(this);
    }

    @Override
    public void onListUpdated(@NonNull String queryParam) {
        mProgressBarLayout.setVisibility(View.GONE);
        mAdapter.setImageHolderList(mRepository.getImageDataHolders());
        if (mRepository.getImageDataHolders().isEmpty()) {
            mResultText.setVisibility(View.INVISIBLE);
        } else {
            mResultText.setText(String.format(getString(R.string.result_string), queryParam));
            mResultText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String input) {
        mSearchView.clearFocus();
        if (!input.isEmpty()) {
            mProgressBarLayout.setVisibility(View.VISIBLE);
            mRepository.searchImages(input);
        } else {
            mRepository.clearLoader();
            mResultText.setVisibility(View.INVISIBLE);
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String input) {
        if (input.isEmpty()) {
            mProgressBarLayout.setVisibility(View.GONE);
            mRepository.clearLoader();
            mResultText.setVisibility(View.INVISIBLE);
        }
        return false;
    }
}
