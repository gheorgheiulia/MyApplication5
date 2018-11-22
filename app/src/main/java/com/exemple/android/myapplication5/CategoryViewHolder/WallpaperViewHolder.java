package com.exemple.android.myapplication5.CategoryViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.exemple.android.myapplication5.R;

public class WallpaperViewHolder extends RecyclerView.ViewHolder {

     public ImageView imageView;

    public WallpaperViewHolder(View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.imageViewWallpaper);
    }
}
