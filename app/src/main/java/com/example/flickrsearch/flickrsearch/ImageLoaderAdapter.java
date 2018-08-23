package com.example.flickrsearch.flickrsearch;
/**
 * Created by Meghana Mokashi
 * Copyright (c) 2018. All rights reserved.
 */
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * ImageLoaderAdapter loads the images to the recyclerview
 * On change of data source {@link MainActivity#notifyDataSetChanged(String)} invokes {@link #setImageLoaderList(List)},
 * which is responsible to refresh the recyclerview data
 */
public class ImageLoaderAdapter extends RecyclerView.Adapter<ImageLoaderAdapter.ViewHolder> {
    private Context mContext;
    private List<Pair<String, ImageLoader>> mImageLoaderList;

    ImageLoaderAdapter(@NonNull Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ImageLoaderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_recyclerview_items, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String url =  mImageLoaderList.get(position).first;
        ImageLoader imageLoader =  mImageLoaderList.get(position).second;

        imageLoader.get(url, ImageLoader.getImageListener(viewHolder.mImageView,
                0, android.R.drawable
                        .ic_dialog_alert));
        viewHolder.mImageView.setImageUrl(url, imageLoader);
    }


    @Override
    public int getItemCount() {
        if (mImageLoaderList != null) {
            return mImageLoaderList.size();
        }
        return 0;
    }

    void setImageLoaderList(List<Pair<String, ImageLoader>> imageLoaderList){
        mImageLoaderList = imageLoaderList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        NetworkImageView mImageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.img_loader);
        }
    }
}
