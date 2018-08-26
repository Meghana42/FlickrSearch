package com.example.flickrsearch.flickrsearch;
/**
 * Created by Meghana Mokashi
 * Copyright (c) 2018. All rights reserved.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * ImageLoaderAdapter loads the images to the recycler view
 * On change of data source {@link MainActivity#onListUpdated(String)} invokes {@link #setImageHolderList(List)},
 * which is responsible to refresh the recycler view data
 */
public class ImageLoaderAdapter extends RecyclerView.Adapter<ImageLoaderAdapter.ViewHolder> {
    private static final int CACHE_SIZE = 20;
    private Context mContext;
    private List<ImageDataHolder> mImageDataHolders;
    private ImageLoader mImageLoader;

    ImageLoaderAdapter(@NonNull Context context) {
        mContext = context;

        mImageLoader = new ImageLoader(Provider.get().getRequestQueue(context), new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap>
                    cache = new LruCache<>(CACHE_SIZE);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });

        mImageDataHolders = new ArrayList<>();
    }

    @NonNull
    @Override
    public ImageLoaderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_recyclerview_items, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.mImageView.setDefaultImageResId(R.drawable.outline_image_white_48);
        viewHolder.mImageView.setImageUrl(mImageDataHolders.get(position).getUrl(), mImageLoader);
    }

    @Override
    public int getItemCount() {
        if (mImageDataHolders != null) {
            return mImageDataHolders.size();
        }
        return 0;
    }

    void setImageHolderList(List<ImageDataHolder> imageHolders){
        mImageDataHolders = imageHolders;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private NetworkImageView mImageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.img_loader);
        }
    }
}
