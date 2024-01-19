package com.example.moviesapp.data.datasource

import com.example.moviesapp.data.entity.ListofMovies
import com.example.moviesapp.data.entity.MovieDetail
import retrofit2.Response

interface GetMoviesDataSource {
//    suspend fun getMoviesList(): Response<ListofMovies>
//    suspend fun getMovieDetails():Response<MovieDetail>

    // Add a parameter for the search query
    suspend fun getMoviesList(apiKey: String,search: String,page:String): Response<ListofMovies>

    // Add parameters for API key and IMDb ID
    suspend fun getMovieDetails(apiKey: String, imdbId: String): Response<MovieDetail>
}