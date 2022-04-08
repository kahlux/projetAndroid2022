package com.example.projetandroid2022.adapters;

import android.widget.ImageView;

import com.example.projetandroid2022.entities.Resource;

public interface ResourceItemClickListener {
    //méthode pour pouvoir passer en extra la ressource qui est clickée :
    void onResourceClick(Resource resource, ImageView movieImageView);
}
