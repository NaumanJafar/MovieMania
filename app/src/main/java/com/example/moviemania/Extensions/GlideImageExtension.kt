package com.example.moviemania.Extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

object GlideImageExtension {
    fun ImageView.loadImage(url:String?){
        Glide.with(this).load("https://image.tmdb.org/t/p/w92/${url}").into(this);
    }
}