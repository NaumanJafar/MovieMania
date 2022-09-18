package com.example.moviemania.repositories

import com.example.moviemania.models.MovieData
import com.example.moviemania.models.Movies
import com.example.moviemania.network.NetworkManager

class MovieRepository private constructor() {
    companion object {
        private var INSTANCE: MovieRepository? = null

        @JvmStatic
        fun getInstance(): MovieRepository {
            return INSTANCE ?: MovieRepository().also {
                INSTANCE = it
            }
        }
    }

    suspend fun getMovieResults(api_key: String): MovieData<List<Movies>> {
        val result = NetworkManager.retrofitService.getResults(api_key)
        return result
    }

    suspend fun getMovieSearchResults(movieName: String): MovieData<List<Movies>>? {
        val searchResult = NetworkManager.retrofitService.getMovieSearchResults(query = movieName)
        if (searchResult?.results.isNullOrEmpty().not())
            return searchResult
        return null
    }

}