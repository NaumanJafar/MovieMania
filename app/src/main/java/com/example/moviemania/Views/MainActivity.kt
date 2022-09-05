package com.example.moviemania.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.navigation.findNavController
import com.example.moviemania.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun navigateToSearchMoviesFragment() {
        findNavController(R.id.fcv_mainActivity).navigate(R.id.action_onlineMovieListFragment_to_searchMoviesFragment)
    }
    fun navigateToMovieDetailFragment(){
        findNavController(R.id.fcv_mainActivity).navigate(R.id.action_onlineMovieListFragment_to_movieDetailFragment)
    }
    fun navigateToFavoritesFragment(){
        findNavController(R.id.fcv_mainActivity).navigate(R.id.action_onlineMovieListFragment_to_favoritesFragment)
    }
}