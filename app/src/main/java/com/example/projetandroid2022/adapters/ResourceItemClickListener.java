package com.example.projetandroid2022.adapters;

import android.widget.ImageView;

import com.example.projetandroid2022.entities.Resource;

public interface ResourceItemClickListener {
    abstract void onResourceClick(Resource resource, ImageView movieImageView); // we will need the imageview to make the shared animation between the two activity
}
