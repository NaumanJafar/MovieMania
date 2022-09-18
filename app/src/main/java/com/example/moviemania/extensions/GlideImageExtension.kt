package com.example.moviemania.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

object GlideImageExtension {
    fun ImageView.loadImage(url:String?){
        Glide.with(this).load("https://image.tmdb.org/t/p/w92/${url}").into(this);
    }
}