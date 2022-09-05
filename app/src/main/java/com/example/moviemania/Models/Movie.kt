package com.example.moviemania.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Movie")
data class Movie(
@PrimaryKey(autoGenerate = true)
var movieId :Int = 0,

@ColumnInfo(name = "movie_name")
val movieName : String?,

val moviePoster: String?,

val movieReleaseDate : String?,

val movieBanner : String?,

val movieOverview:String?,

var fav : Boolean = true


)
