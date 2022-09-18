package com.example.moviemania.network

import com.example.moviemania.models.MovieData
import com.example.moviemania.models.Movies
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("3/movie/popular")
    suspend fun getResults(@Query("api_key") api_key: String = "e5ea3092880f4f3c25fbc537e9b37dc1"): MovieData<List<Movies>>

    @GET("3/search/movie")
    suspend fun getMovieSearchResults(
        @Query("api_key") api_key: String = "e5ea3092880f4f3c25fbc537e9b37dc1",
        @Query("query") query: String,
        @Query("page") page: String = "1"
    ): MovieData<List<Movies>>?
}