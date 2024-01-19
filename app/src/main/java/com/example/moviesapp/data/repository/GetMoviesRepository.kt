package com.example.moviesapp.data.repository

import com.example.moviesapp.data.AppConstants
import com.example.moviesapp.data.datasource.GetMoviesDataSource
import com.example.moviesapp.data.entity.ListofMovies
import com.example.moviesapp.data.entity.Movie
import com.example.moviesapp.data.entity.MovieDetail
import com.example.moviesapp.data.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
//import kotlinx.coroutines.flow.internal.NopCollector.emit
//import kotlinx.coroutines.flow.internal.NopCollector.emit
import javax.inject.Inject

class GetMoviesRepository @Inject constructor(
    private val getMoviesDataSource: GetMoviesDataSource
) {

suspend fun getMoviesList(search: String): Resource<ListofMovies> {
    return try {
        val apiKey = AppConstants.Api_Key
        val page ="1"
        val response = getMoviesDataSource.getMoviesList(apiKey,search,page)
        if (response.isSuccessful) {
            Resource.Success(response.body() ?: ListofMovies(listOf(Movie("","","","","")),"",""))
        } else {
            Resource.Error("Failed to fetch movies: ${response.message()}")
        }
    } catch (e: Exception) {
        Resource.Error("Error getting movies: ${e.message}")
    }
}
suspend fun getMovieDetails(apiKey: String, imdbId: String): Resource<MovieDetail> {
    return try {
        // Make the API call using getMoviesDataSource.getMovieDetails
        val movieDetailResponse = getMoviesDataSource.getMovieDetails(apiKey, imdbId)

        // Check if the response is successful
        if (movieDetailResponse.isSuccessful) {
            // If successful, extract the movie details from the response body
            val movie = movieDetailResponse.body()

            // Check if the movie details are not null
            if (movie != null) {
                // Return the movie details as a success resource
                Resource.Success(movie)
            } else {
                // Handle the case where the movie details are null
                Resource.Error("Null response body")
            }
        } else {
            // Handle the case where the API call was not successful
            Resource.Error("Error getting movie details: ${movieDetailResponse.message()}")
        }
    } catch (e: Exception) {
        // Handle any exceptions that may occur during the API call
        Resource.Error("Error getting movie details: ${e.message}")
    }
}

}