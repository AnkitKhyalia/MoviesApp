package com.example.moviesapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviesapp.data.entity.Movie
import com.example.moviesapp.data.entity.MovieDetail
import com.example.moviesapp.data.util.Resource
import com.example.moviesapp.data.viewmodel.MoviesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(viewModel: MoviesViewModel = hiltViewModel()) {
    var searchText by remember { mutableStateOf("") }
    val moviesList by viewModel.moviesList.collectAsState()
    val movieDetail by viewModel.movieDetail.collectAsState()
    var dialogState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search bar
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search Movies") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Search button
        Button(
            onClick = {
                viewModel.getMoviesList(searchText)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Search")
        }

        // Movies list
        when (val result = moviesList) {
            is Resource.Loading -> {
                // Loading state
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is Resource.Success -> {
                // Display movies list
                LazyColumn {
                    result.data?.Search?.let {
                        items(it.size) { movie ->
                            MovieCard(movie = result.data.Search[movie]) {
                                // Handle movie card click
                                dialogState.value = true
                                viewModel.getMovieDetails(apiKey ="2ffdda10", imdbId = result.data.Search[movie].imdbID)

                            }
                        }
                    }
                }
            }
            is Resource.Error -> {
                // Display error message
                Text(text = "Error: ${result.message}")
            }
            else -> Unit
        }
    }
    if(dialogState.value == true){
        MovieDetailsDialog(
            movie =  movieDetail.data ,
            visible = dialogState,
            onGetDetailsClick = { /*TODO*/ }) {

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieCard(movie: Movie, onMovieClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onMovieClick.invoke() }
            .padding(8.dp),
        onClick = onMovieClick
//        elevation = 8.dp
    ) {
        // Display movie details in the card
        // You can customize the content based on your requirements
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = movie.Title, fontWeight = FontWeight.Bold)
            Text(text = "Year: ${movie.Year}")
            // Add more movie details as needed
        }
    }
}

@Composable
fun MovieDetailsDialog(
    movie: MovieDetail?,
    visible: MutableState<Boolean>,
    onGetDetailsClick: () -> Unit,
    onClose: () -> Unit
) {
    if (visible.value==true) {
        AlertDialog(
            onDismissRequest = { visible.value = false },
            title = { Text(text = "Movie Details") },
            text = {

                Column {
                    Text(text = "Title: ${movie?.Title}")
                    Text(text = "Year: ${movie?.Year}")
                    Text(text = "Type: ${movie?.Type}")
                    Text(text = "Actors: ${movie?.Actors}")
                    Text(text = "Awards: ${movie?.Awards}")
                }


            },
            confirmButton = {

            }
        )
    }
}

