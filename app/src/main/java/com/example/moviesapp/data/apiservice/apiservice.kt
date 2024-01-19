package com.example.moviesapp.data.apiservice

import com.example.moviesapp.data.AppConstants
import com.example.moviesapp.data.entity.ListofMovies
import com.example.moviesapp.data.entity.MovieDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService{


//    @GET("apikey=${AppConstants.Api_Key}")
//suspend fun getMovies(@Query("s") search: String): Response<ListofMovies>
@GET("/")
suspend fun getMovies(
    @Query("apikey") apiKey: String = AppConstants.Api_Key,
    @Query("s") search: String ="Impossible",
    @Query("page") page: Int=1
): Response<ListofMovies>

    @GET("/")
    suspend fun getMovieDetails(
        @Query("apikey") apiKey: String,
        @Query("i") imdbId: String
    ): Response<MovieDetail>
    // Use @Query("i") for the IMDb ID parameter
//    @GET("apikey={API_KEY}&i={IMDB_ID}")
//    suspend fun getMovieDetails(@Query("apikey") apiKey: String, @Query("i") imdbId: String): Response<MovieDetail>

}