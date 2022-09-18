package com.example.moviemania.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.moviemania.models.Movie


@Dao
interface MovieDao {

@Insert
suspend fun insertMovie(movie: Movie)

@Query("SELECT * from movie")
suspend fun getAllMovies():List<Movie>

@Delete
suspend fun removeMovieFromFavorites(movie: Movie) : Int
}