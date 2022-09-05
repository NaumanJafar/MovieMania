package com.example.moviemania.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.example.moviemania.AppApplication
import com.example.moviemania.Database.MovieDao
import com.example.moviemania.Database.MovieDatabase
import com.example.moviemania.Models.Movie
import com.example.moviemania.Models.Movies
import kotlinx.coroutines.launch

class FavViewModel() : ViewModel() {
    private lateinit var movieDao: MovieDao
    var favMovieList: List<Movie>? = null
    private val _favList = MutableLiveData<List<Movie>>()
    val favList: LiveData<List<Movie>>
        get() = _favList

    init {
        movieDao = MovieDatabase.getInstance(AppApplication.getInstance()).movieDao()
    }

    fun insertMovie(movie: Movie) {
        viewModelScope.launch {
            movieDao.insertMovie(movie)
        }}
        fun getAllFavMovies() {
            viewModelScope.launch {
                favMovieList = movieDao.getAllMovies()
                _favList.postValue(favMovieList)
            }
        }

        fun removeMovie(movie: Movie) {
            viewModelScope.launch {
                movieDao.removeMovieFromFavorites(movie)
                favMovieList = movieDao.getAllMovies()
                _favList.postValue(favMovieList)

            }
        }

}