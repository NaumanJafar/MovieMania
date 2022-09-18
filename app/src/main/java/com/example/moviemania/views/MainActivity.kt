package com.example.moviemania.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.moviemania.R
import com.example.moviemania.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun navigateToSearchMoviesFragment() {
        findNavController(binding.fcvMainActivity.id).navigate(R.id.action_onlineMovieListFragment_to_searchMoviesFragment)
    }
    fun navigateToMovieDetailFragment(){
        findNavController(binding.fcvMainActivity.id).navigate(R.id.action_onlineMovieListFragment_to_movieDetailFragment)
    }
    fun navigateToFavoritesFragment(){
        findNavController(binding.fcvMainActivity.id).navigate(R.id.action_onlineMovieListFragment_to_favoritesFragment)
    }
}