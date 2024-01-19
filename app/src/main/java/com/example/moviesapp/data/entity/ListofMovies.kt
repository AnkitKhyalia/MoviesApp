package com.example.moviesapp.data.entity

data class ListofMovies(
    val Search: List<Movie>,
    val totalResults: String,
    val Response: String
)

data class Movie(
    val Title: String,
    val Year: String,
    val imdbID: String,
    val Type: String,
    val Poster: String
)