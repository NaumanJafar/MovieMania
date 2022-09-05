package com.example.moviemania.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.moviemania.Models.Movie


@Dao
interface MovieDao {

@Insert
suspend fun insertMovie(movie: Movie)

@Query("SELECT * from movie")
suspend fun getAllMovies():List<Movie>

@Delete
suspend fun removeMovieFromFavorites(movie: Movie)
}