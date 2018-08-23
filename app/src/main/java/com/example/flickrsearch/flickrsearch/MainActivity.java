package com.example.flickrsearch.flickrsearch;

/**
 * Created by Meghana Mokashi
 * Copyright (c) 2018. All rights reserved.
 */

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

/**
 * MainActivity is the UI controller,
 * communicates with the {@link SearchImageRepository} to get the data source, based on the search query string
 */
public class MainActivity extends AppCompatActivity implements Observer, SearchView.OnQueryTextListener {

    private SearchImageRepository mRepository;
    private ImageLoaderAdapter mAdapter;
    private SearchView mSearchView;
    @VisibleForTesting
    TextView mResultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchView = findViewById(R.id.search_box);
        mSearchView.setOnQueryTextListener(this);

        mResultText = findViewById(R.id.result_text);
        mResultText.setVisibility(View.INVISIBLE);

        RecyclerView recyclerView = findViewById(R.id.image_holder);
        mAdapter = new ImageLoaderAdapter(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));

        mRepository = Provider.get().getSearchImageRepository(getApplicationContext(),  this);
    }

    @Override
    public void notifyDataSetChanged(@NonNull String queryParam) {
        List<Pair<String, ImageLoader>> imageLoaderList = mRepository.getImageDataList();
        mAdapter.setImageLoaderList(imageLoaderList);
        if (imageLoaderList.isEmpty()) {
            mResultText.setVisibility(View.INVISIBLE);
        } else {
            mResultText.setText(String.format(getString(R.string.result_string), queryParam));
            mResultText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String input) {
        mSearchView.clearFocus();
        if (input != null) {
            mRepository.searchImages(input);
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String input) {
        if (input.isEmpty()) {
            mRepository.clearLoader();
        }
        return false;
    }
}
