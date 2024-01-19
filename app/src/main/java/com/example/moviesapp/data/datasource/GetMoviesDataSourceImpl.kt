package com.example.moviesapp.data.datasource

import com.example.moviesapp.data.AppConstants
import com.example.moviesapp.data.apiservice.ApiService
import com.example.moviesapp.data.entity.ListofMovies
import com.example.moviesapp.data.entity.MovieDetail
import retrofit2.Response
import javax.inject.Inject

class GetMoviesDataSourceImpl @Inject constructor(
    private val apiService: ApiService
):GetMoviesDataSource {
//    override suspend fun getMoviesList(search: String): Response<ListofMovies> {
//        // Use the modified getMovies method with the search parameter
//
//    }

    override suspend fun getMoviesList(
        apiKey: String,
        search: String,
        page: String
    ): Response<ListofMovies> {
        val apiKey = AppConstants.Api_Key
        val page = 1
        return apiService.getMovies(apiKey,search,page)
    }

    override suspend fun getMovieDetails(apiKey: String , imdbId: String): Response<MovieDetail> {
        // Use the modified getMovieDetails method with the IMDb ID parameter

        return apiService.getMovieDetails(AppConstants.Api_Key, imdbId)
    }

}