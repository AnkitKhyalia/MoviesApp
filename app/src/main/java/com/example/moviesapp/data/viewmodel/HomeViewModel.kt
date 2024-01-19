package com.example.moviesapp.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.data.entity.ListofMovies
import com.example.moviesapp.data.entity.MovieDetail
import com.example.moviesapp.data.repository.GetMoviesRepository
import com.example.moviesapp.data.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepository: GetMoviesRepository
) : ViewModel() {

    private val _moviesList = MutableStateFlow<Resource<ListofMovies>>(Resource.Unspecified())
//    val moviesList: LiveData<Resource<ListofMovies>> get() = _moviesList
    val moviesList= _moviesList.asStateFlow()


    private val _movieDetail = MutableStateFlow<Resource<MovieDetail>>(Resource.Unspecified())
    val movieDetail = _movieDetail.asStateFlow()

//    private val _movieDetail = MutableLiveData<Resource<MovieDetail>>()
//    val movieDetail: LiveData<Resource<MovieDetail>> get() = _movieDetail

    // Function to get a list of movies

    fun getMoviesList(search: String) {
        viewModelScope.launch {
            _moviesList.value = Resource.Loading()
            _moviesList.value = movieRepository.getMoviesList(search)
        }

    }

    // Function to get details for a specific movie
    fun getMovieDetails(apiKey: String, imdbId: String) {

        viewModelScope.launch {
            _movieDetail.value = Resource.Loading()
            _movieDetail.value = movieRepository.getMovieDetails(apiKey, imdbId)
        }
    }
}
